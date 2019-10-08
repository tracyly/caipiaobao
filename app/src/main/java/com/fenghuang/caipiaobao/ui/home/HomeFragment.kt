package com.fenghuang.caipiaobao.ui.home

import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.alibaba.android.vlayout.layout.GridLayoutHelper
import com.alibaba.android.vlayout.layout.LinearLayoutHelper
import com.fenghuang.baselib.base.mvp.BaseMvpFragment
import com.fenghuang.baselib.base.recycler.header.material.MaterialHeader
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.baselib.utils.ViewUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.constant.HomeViewTypeConstant
import com.fenghuang.caipiaobao.manager.ImageManager
import com.fenghuang.caipiaobao.ui.home.data.*
import com.fenghuang.caipiaobao.ui.home.live.HomeLiveDetailsFragment
import com.fenghuang.caipiaobao.utils.LaunchUtils
import com.fenghuang.caipiaobao.widget.MarqueeTextView
import com.fenghuang.caipiaobao.widget.cardview.LCardView
import com.fenghuang.caipiaobao.widget.pagegridview.PageGridView
import com.hwangjr.rxbus.RxBus
import com.pingerx.banner.BannerView
import com.pingerx.banner.holder.BannerHolderCreator
import com.yc.cn.ycbaseadapterlib.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_home_new.*
import java.util.*


/**
 * 首页
 */
class HomeFragment : BaseMvpFragment<HomePresenter>() {

    // 存放各个模块的适配器
    private var mAdapters: MutableList<DelegateAdapter.Adapter<*>>? = null
    private var mDelegateAdapter: DelegateAdapter? = null

    override fun getPageTitle() = getString(R.string.app_name)

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = HomePresenter()

    override fun getContentResID() = R.layout.fragment_home_new
    override fun isShowTitleLeftLogo() = true
    override fun isShowTitleRightLogo() = true

    override fun isOverridePage() = false
    override fun isShowBackIcon() = false

    override fun initContentView() {
        super.initContentView()
        mAdapters = LinkedList()
        initRecycler()
        setImageResource(findView(R.id.ivTitleLeft), R.mipmap.ic_home_top_user)
        setImageResource(findView(R.id.ivTitleRight), R.mipmap.ic_home_top_notice)
        smartRefreshLayout.setRefreshHeader(MaterialHeader(context!!))
        mPresenter.loadCache()
        smartRefreshLayout.setOnRefreshListener {
            showPageLoading()
            mAdapters?.clear()
            mDelegateAdapter?.clear()
            mPresenter.loadData()
        }
    }

    override fun initEvent() {
        super.initEvent()
//        setOnClick(findView<ImageView>(R.id.ivTitleLeft))
        findView<ImageView>(R.id.ivTitleLeft).setOnClickListener {
            RxBus.get().post(HomeClickMine(isClick = true))
        }
    }


    override fun initData() {
        super.initData()
        smartRefreshLayout.autoRefresh()
    }

    /**
     * 直播预告
     */
    fun updateLivePop(it: List<HomeLivePopResponse>) {
        iniTitleView(2)
        val mLivePopAdapter = object : HomeDelegateAdapter(getPageActivity(), LinearLayoutHelper(), R.layout.holder_home_live_trail_notice, 1, HomeViewTypeConstant.TYPE_LIVE_NOTICE) {
            override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
                super.onBindViewHolder(holder, position)
                val recyclerView = holder.getView<RecyclerView>(R.id.recyclerView)
                recyclerView.layoutManager = LinearLayoutManager(getPageActivity(), LinearLayoutManager.HORIZONTAL, false)
                val homeLiveNoticeAdapter = HomeLiveNoticeAdapter(getPageActivity())
                homeLiveNoticeAdapter.addAll(it)
                recyclerView.adapter = homeLiveNoticeAdapter
            }
        }
        mAdapters?.add(mLivePopAdapter)
    }

    /**
     * 热门直播
     */
    fun updateHotLive(it: List<HomeLiveListResponse>) {
        iniTitleView(1)
        val mHotLiveAdapter = object : HomeDelegateAdapter(getPageActivity(), getGridLayoutHelper(2, 4, 4), R.layout.holder_home_hot_live, 6, HomeViewTypeConstant.TYPE_HOT_LIVE_LIST) {
            override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
                super.onBindViewHolder(holder, position)
                val ivHotLiveLogo = holder.getView<ImageView>(R.id.ivHotLiveLogo)
                val ivHotLiveTag = holder.getView<ImageView>(R.id.ivHotLiveTag)
                val cardView = holder.getView<LCardView>(R.id.cardView)
                holder.setText(R.id.tvHotLiveTitle, it[position].name)
                holder.setText(R.id.tvHotLiveIntro, it[position].live_intro)
                holder.setText(R.id.tvHotLiveName, it[position].nickname)
                holder.setText(R.id.tvHotLiveTag, it[position].tags[0].title)
                ImageManager.loadHomeHotLive(it[position].avatar, ivHotLiveLogo)
                ImageManager.loadHomeGameListLogo(it[position].tags[0].icon, ivHotLiveTag)
                cardView.setOnClickListener {
                    ToastUtils.showToast("点的热门第" + position + "个")
                    LaunchUtils.startFragment(getPageActivity(), HomeLiveDetailsFragment())
                }
            }
        }
        mAdapters?.add(mHotLiveAdapter)
    }

    /**
     *  专家直播
     */
    fun updateExpertLive(it: List<HomeLiveListResponse>) {
        iniTitleView(3)
        val mExpertLiveAdapter = object : HomeDelegateAdapter(getPageActivity(), getGridLayoutHelper(2, 4, 4), R.layout.holder_home_hot_live, 6, HomeViewTypeConstant.TYPE_HOT_LIVE_LIST) {
            override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
                super.onBindViewHolder(holder, position)
                val ivHotLiveLogo = holder.getView<ImageView>(R.id.ivHotLiveLogo)
                val ivHotLiveTag = holder.getView<ImageView>(R.id.ivHotLiveTag)
                val cardView = holder.getView<LCardView>(R.id.cardView)
                holder.setText(R.id.tvHotLiveTitle, it[position].name)
                holder.setText(R.id.tvHotLiveIntro, it[position].live_intro)
                holder.setText(R.id.tvHotLiveName, it[position].nickname)
                holder.setText(R.id.tvHotLiveTag, it[position].tags[0].title)
                ImageManager.loadHomeHotLive(it[position].avatar, ivHotLiveLogo)
                ImageManager.loadHomeGameListLogo(it[position].tags[0].icon, ivHotLiveTag)
                cardView.setOnClickListener {
                    ToastUtils.showToast("点的专家第" + position + "个")
                    LaunchUtils.startFragment(getPageActivity(), HomeLiveDetailsFragment())
                }
            }
        }
        mAdapters?.add(mExpertLiveAdapter)
    }

    /**
     * 专家推荐
     */
    fun updateExpertRecommend(list: List<HomeExpertRecommendResponse>) {
        iniTitleView(4)
        val mExpertAdapter = object : HomeDelegateAdapter(getPageActivity(), getGridLayoutHelper(2, 4, 4), R.layout.holder_home_expert_recommend, 6, HomeViewTypeConstant.TYPE_EXPERT_RECOMMEND) {
            override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
                super.onBindViewHolder(holder, position)
                holder.setText(R.id.tvExpertRecommendName, list[position].pro_name)
                holder.setText(R.id.tvExpertRecommendGameName, list[position].pro_type)
                holder.setText(R.id.tvRecommendProfit, list[position].profit)
                ImageManager.loadRoundLogo(list[position].pro_avatar, holder.getView(R.id.ivExpertRecommendLogo))
            }
        }
        mAdapters?.add(mExpertAdapter)
        initMoreView()

        mDelegateAdapter?.setAdapters(mAdapters)
    }

    /**
     * 更新游戏榜
     */
    fun updateGameList(it: List<HomeGameListResponse>) {
        val mGameListAdapter = object : HomeDelegateAdapter(getPageActivity(), LinearLayoutHelper(), R.layout.holder_home_game_viewpager, 1, HomeViewTypeConstant.TYPE_GAME_LIST) {
            override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
                super.onBindViewHolder(holder, position)
                val mPagerGrid = holder.getView<PageGridView<HomeGameListResponse>>(R.id.pagerGrid)
                mPagerGrid.setData(it)
            }
        }
        mAdapters?.add(mGameListAdapter)
    }

    /**
     * 更新BannerView
     */
    fun updateBanner(data: List<HomeBannerResponse>?) {
        smartRefreshLayout.finishRefresh()
        val mBannerAdapter = object : HomeDelegateAdapter(getPageActivity(), LinearLayoutHelper(), R.layout.holder_home_banner, 1, HomeViewTypeConstant.TYPE_BANNER) {
            override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
                super.onBindViewHolder(holder, position)
                val mBannerView = holder.getView<BannerView<HomeBannerResponse>>(R.id.mBannerView)
                mBannerView?.setPages(data, object : BannerHolderCreator<HomeBannerResponse, HomeBannerHolder> {
                    override fun onCreateBannerHolder(): HomeBannerHolder {
                        return HomeBannerHolder(mBannerView.getPageMode() == BannerView.PageMode.FAR)
                    }
                })
            }
        }
        mAdapters?.add(mBannerAdapter)
    }

    /**
     * 更新公告
     */
    fun updateNotice(message: String?) {
        val mNoticeAdapter = object : HomeDelegateAdapter(getPageActivity(), LinearLayoutHelper(), R.layout.holder_home_notice, 1, HomeViewTypeConstant.TYPE_NOTICE) {
            override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
                super.onBindViewHolder(holder, position)
                val mNoticeMessage = holder.getView<MarqueeTextView>(R.id.tvNoticeMassage)
                mNoticeMessage.setText(message!!)
                mNoticeMessage.setTextColor(getColor(R.color.color_notice_message))
            }
        }
        mAdapters?.add(mNoticeAdapter)
    }

    /**
     * 初始化Recycler
     */
    private fun initRecycler() {
        val layoutManager = VirtualLayoutManager(getPageActivity())
        recyclerView.layoutManager = layoutManager
        // 设置回收复用池大小，（如果一屏内相同类型的 View 个数比较多，需要设置一个合适的大小，防止来回滚动时重新创建 View）
        val viewPool = RecyclerView.RecycledViewPool()
        recyclerView.setRecycledViewPool(viewPool)
        viewPool.setMaxRecycledViews(0, 20)
        mDelegateAdapter = DelegateAdapter(layoutManager, true)
        recyclerView.adapter = mDelegateAdapter
    }

    private fun iniTitleView(type: Int) {
        val titleAdapter = object : HomeDelegateAdapter(getPageActivity(), LinearLayoutHelper(), R.layout.holder_home_title, 1, HomeViewTypeConstant.TYPE_TITLE) {
            override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
                super.onBindViewHolder(holder, position)
                when (type) {
                    1 -> {
                        holder.setText(R.id.tvHomeListTitle, "热门直播")
                    }
                    2 -> {
                        holder.setText(R.id.tvHomeListTitle, "直播预告")
                    }
                    3 -> {
                        holder.setText(R.id.tvHomeListTitle, "专家直播")
                    }
                    4 -> {
                        holder.setText(R.id.tvHomeListTitle, "专家推荐")
                    }
                }
            }

        }
        mAdapters?.add(titleAdapter)
    }

    private fun initMoreView() {
        val moreAdapter = object : HomeDelegateAdapter(getPageActivity(), LinearLayoutHelper(), R.layout.holder_home_more, 1, HomeViewTypeConstant.TYPE_MORE) {
            override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
                super.onBindViewHolder(holder, position)
                holder.setText(R.id.tvHomeMore, getString(R.string.home_list_more))
            }
        }
        mAdapters?.add(moreAdapter)
    }

    private fun getGridLayoutHelper(span: Int, vertical: Int, horizontally: Int): GridLayoutHelper {
        val gridLayoutHelper = GridLayoutHelper(span)
        gridLayoutHelper.setPadding(ViewUtils.dp2px(6), ViewUtils.dp2px(10), ViewUtils.dp2px(6), 0)
        // 设置垂直间距
        gridLayoutHelper.vGap = ViewUtils.dp2px(vertical)
        // 设置水平间距
        gridLayoutHelper.hGap = ViewUtils.dp2px(horizontally)
        return gridLayoutHelper
    }
}