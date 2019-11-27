package com.fenghuang.caipiaobao.ui.home

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.fenghuang.baselib.base.mvp.BaseMvpFragment
import com.fenghuang.baselib.base.recycler.BaseRecyclerAdapter
import com.fenghuang.baselib.base.recycler.BaseViewHolder
import com.fenghuang.baselib.base.recycler.decorate.GridItemSpaceDecoration
import com.fenghuang.baselib.base.recycler.header.material.MaterialHeader
import com.fenghuang.baselib.utils.NetWorkUtils
import com.fenghuang.baselib.utils.StatusBarUtils
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.baselib.utils.ViewUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.manager.ImageManager
import com.fenghuang.caipiaobao.ui.home.data.*
import com.fenghuang.caipiaobao.ui.home.live.liveroom.HomeLiveDetailsFragment
import com.fenghuang.caipiaobao.utils.LaunchUtils
import com.fenghuang.caipiaobao.utils.UserInfoSp
import com.fenghuang.caipiaobao.widget.cardview.LCardView
import com.fenghuang.caipiaobao.widget.gif.GifImageView
import com.hwangjr.rxbus.RxBus
import com.pingerx.banner.BannerView
import com.pingerx.banner.holder.BannerHolderCreator
import kotlinx.android.synthetic.main.fragment_home_new.*


/**
 * 首页
 */
class HomeFragmentNew : BaseMvpFragment<HomePresenter>() {

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
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), true)
        ImageManager.loadRoundLogo(UserInfoSp.getUserPhoto(), findView(R.id.ivTitleLeft))
        setImageResource(findView(R.id.ivTitleRight), R.mipmap.ic_home_top_notice)
        smartRefreshLayout.setRefreshHeader(MaterialHeader(context!!))
        mPresenter.loadCache()
        smartRefreshLayout.setOnRefreshListener {
            mPresenter.loadData()
        }
//        FirstTipsDialog(getPageActivity()).show()

    }

    override fun initEvent() {
        super.initEvent()
        findView<ImageView>(R.id.ivTitleLeft).setOnClickListener {
            RxBus.get().post(HomeClickMine(isClick = true))
        }
    }

    /**
     * 直播预告
     */
    fun updateLivePop(it: List<HomeLivePopResponse>) {
        rvLivePre.layoutManager = LinearLayoutManager(getPageActivity(), LinearLayoutManager.HORIZONTAL, false)
        val homeLiveNoticeAdapter = HomeLiveNoticeAdapter(getPageActivity())
        homeLiveNoticeAdapter.addAll(it)
        rvLivePre.adapter = homeLiveNoticeAdapter
    }

    /**
     * 热门直播
     */
    fun updateHotLive(data: List<HomeLiveListResponse>) {
        if (data.isNotEmpty()) {
            val hotLiveAdapter = object : BaseRecyclerAdapter<HomeLiveListResponse>(getPageActivity()) {
                override fun onCreateHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<HomeLiveListResponse> {
                    return HotLiveHolder(parent)
                }

                inner class HotLiveHolder(parent: ViewGroup) : BaseViewHolder<HomeLiveListResponse>(getContext(), parent, R.layout.holder_home_hot_live) {
                    override fun onBindData(data: HomeLiveListResponse) {
                        setText(R.id.tvHotLiveTitle, data.name)
                        setText(R.id.tvHotLiveIntro, data.live_intro)
                        setText(R.id.tvHotLiveName, data.nickname)
                        if (data.tags.isNotEmpty()) setText(R.id.tvHotLiveTag, data.tags[0].title)
                        ImageManager.loadHomeHotLive(data.avatar, findView(R.id.ivHotLiveLogo))
                        if (data.tags.isNotEmpty()) ImageManager.loadHomeGameListLogo(data.tags[0].icon, findView(R.id.ivHotLiveTag))
                        if (data.live_status == 1) {
                            val ivHotLiveStatus = findView<GifImageView>(R.id.ivHotLiveStatus)
                            ivHotLiveStatus.setGifResource(R.drawable.ic_home_live_gif)
                            ivHotLiveStatus.play(-1)
                            setVisibility(R.id.ivHotLiveStatus, true)
                        } else {
                            setVisibility(R.id.ivHotLiveStatus, false)
                        }
                        findView<LCardView>(R.id.cardViewItem).setOnClickListener {
                            startLiveRoom(data.live_status, data.anchor_id, data.live_intro)
                        }
                    }
                }
            }
            if (data.size > 4) {
                val list = data.subList(0, 4)
                hotLiveAdapter.addAll(list)
            } else hotLiveAdapter.addAll(data)

            rvHotLive.adapter = hotLiveAdapter

            val gridLayoutManager = object : GridLayoutManager(context, 2) {
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }

            rvHotLive.layoutManager = gridLayoutManager

            if (rvHotLive.itemDecorationCount == 0) {
                rvHotLive.addItemDecoration(GridItemSpaceDecoration(2, itemSpace = ViewUtils.dp2px(10), startAndEndSpace = ViewUtils.dp2px(6)))
            }
        }
    }

    /**
     *  专家直播
     */
    fun updateExpertLive(data: List<HomeLiveListResponse>) {
        val mExpertLiveAdapter = object : BaseRecyclerAdapter<HomeLiveListResponse>(getPageActivity()) {
            override fun onCreateHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<HomeLiveListResponse> {
                return ExpertLiveHolder(parent)
            }

            inner class ExpertLiveHolder(parent: ViewGroup) : com.fenghuang.baselib.base.recycler.BaseViewHolder<HomeLiveListResponse>(getContext(), parent, R.layout.holder_home_hot_live) {
                override fun onBindData(data: HomeLiveListResponse) {
                    setText(R.id.tvHotLiveTitle, data.name)
                    setText(R.id.tvHotLiveIntro, data.live_intro)
                    setText(R.id.tvHotLiveName, data.nickname)
                    if (data.tags.isNotEmpty()) setText(R.id.tvHotLiveTag, data.tags[0].title)
                    ImageManager.loadHomeHotLive(data.avatar, findView(R.id.ivHotLiveLogo))
                    if (data.tags.isNotEmpty()) ImageManager.loadHomeGameListLogo(data.tags[0].icon, findView(R.id.ivHotLiveTag))
                    if (data.live_status == 1) {
                        val ivHotLiveStatus = findView<GifImageView>(R.id.ivHotLiveStatus)
                        ivHotLiveStatus.setGifResource(R.drawable.ic_home_live_gif)
                        ivHotLiveStatus.play(-1)
                        setVisibility(R.id.ivHotLiveStatus, true)
                    } else {
                        setVisibility(R.id.ivHotLiveStatus, false)
                    }
                    findView<LCardView>(R.id.cardViewItem).setOnClickListener {
                        startLiveRoom(data.live_status, data.anchor_id, data.live_intro)
                    }
                }
            }
        }

        if (data.size > 4) {
            val list = data.subList(0, 4)
            mExpertLiveAdapter.addAll(list)
        } else mExpertLiveAdapter.addAll(data)

        rvExpertLive.adapter = mExpertLiveAdapter
        val gridLayoutManager = object : GridLayoutManager(context, 2) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        rvExpertLive.layoutManager = gridLayoutManager
        if (rvExpertLive.itemDecorationCount == 0) {
            rvExpertLive.addItemDecoration(GridItemSpaceDecoration(2, itemSpace = ViewUtils.dp2px(10), startAndEndSpace = ViewUtils.dp2px(6)))
        }
    }

    /**
     * 专家推荐
     */
    fun updateExpertRecommend(data: List<HomeExpertRecommendResponse>) {
        val mExpertAdapter = object : BaseRecyclerAdapter<HomeExpertRecommendResponse>(getPageActivity()) {
            override fun onCreateHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<HomeExpertRecommendResponse> {
                return ExpertLiveHolder(parent)
            }

            inner class ExpertLiveHolder(parent: ViewGroup) : com.fenghuang.baselib.base.recycler.BaseViewHolder<HomeExpertRecommendResponse>(getContext(), parent, R.layout.holder_home_expert_recommend) {
                override fun onBindData(data: HomeExpertRecommendResponse) {
                    setText(R.id.tvExpertRecommendName, data.pro_name)
                    setText(R.id.tvExpertRecommendGameName, data.pro_type)
                    setText(R.id.tvRecommendProfit, data.profit)
                    ImageManager.loadRoundLogo(data.pro_avatar, findView(R.id.ivExpertRecommendLogo))
                }
            }
        }
        mExpertAdapter.addAll(data)
        rvExpertRecommendLive.adapter = mExpertAdapter
        val gridLayoutManager = object : GridLayoutManager(context, 2) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        rvExpertRecommendLive.layoutManager = gridLayoutManager
        if (rvExpertRecommendLive.itemDecorationCount == 0) {
            rvExpertRecommendLive.addItemDecoration(GridItemSpaceDecoration(2, itemSpace = ViewUtils.dp2px(4), startAndEndSpace = ViewUtils.dp2px(4)))
        }
    }

    /**
     * 更新游戏榜
     */
    fun updateGameList(it: List<HomeGameListResponse>) {
        if (it.isNotEmpty()) {
            gameTypeGridPager.setDataAllCount(it.size)
                    .setItemBindDataListener { imageView, tvTitle, ivLiveStatus, _, _, position ->
                        if (it.isNotEmpty()) ImageManager.loadHomeGameListLogo(it[position].image, imageView!!)
                        if (it.isNotEmpty()) tvTitle.text = it[position].name
                        if ((it.isNotEmpty()) && it[position].live_status == 1) {
                            ivLiveStatus.setGifResource(R.drawable.ic_home_live_gif)
                            ivLiveStatus.play(-1)
                            ivLiveStatus.visibility = View.VISIBLE
                        } else {
                            ivLiveStatus.visibility = View.GONE
                        }
                    }
                    .setGridItemClickListener { position, _ ->
                        if (it.isNotEmpty() && it[position].live_status == 1) {
                            startLiveRoom(it[position].live_status, it[position].anchor_id, it[position].name)
                        }
                    }
                    .show()
        }
    }

    /**
     * 更新BannerView
     */
    fun updateBanner(data: List<HomeBannerResponse>?) {
        if (smartRefreshLayout != null) {
            smartRefreshLayout.finishRefresh()
        }
        val mBannerView = findView<BannerView<HomeBannerResponse>>(R.id.mBannerViews)
        mBannerView.setPages(data, object : BannerHolderCreator<HomeBannerResponse, HomeBannerHolder> {
            override fun onCreateBannerHolder(): HomeBannerHolder {
                return HomeBannerHolder(mBannerView.getPageMode() == BannerView.PageMode.FAR)
            }
        })
    }

    /**
     * 更新公告
     */
    fun updateNotice(message: String?) {
        tvNoticeMassages.setText(message!! + "")
        tvNoticeMassages.setTextColor(getColor(R.color.color_notice_message))
    }


    /**
     * 跳转直播间
     */
    private fun startLiveRoom(status: Int, anchorId: Int, name: String) {
//        if (status == 1) {
        if (NetWorkUtils.isNetworkConnected()) {
            LaunchUtils.startFragment(getPageActivity(), HomeLiveDetailsFragment.newInstance(anchorId, name, status))
        } else ToastUtils.showError("网络连接已断开")
//        } else ToastUtils.showToast("主播正在赶来的路上...")
    }

}