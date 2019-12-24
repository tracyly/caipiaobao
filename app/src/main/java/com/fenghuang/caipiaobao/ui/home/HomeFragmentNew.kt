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
import com.fenghuang.baselib.utils.NetWorkUtils
import com.fenghuang.baselib.utils.StatusBarUtils
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.baselib.utils.ViewUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.manager.ImageManager
import com.fenghuang.caipiaobao.ui.home.data.*
import com.fenghuang.caipiaobao.ui.home.live.liveroom.HomeLiveDetailsFragment
import com.fenghuang.caipiaobao.ui.login.data.LoginSuccess
import com.fenghuang.caipiaobao.ui.lottery.data.UserChangePhoto
import com.fenghuang.caipiaobao.ui.mine.MineAnchorGetFragment
import com.fenghuang.caipiaobao.ui.mine.data.MineIsAnchorLive
import com.fenghuang.caipiaobao.utils.FastClickUtils
import com.fenghuang.caipiaobao.utils.GobalExceptionDialog.ExceptionDialog
import com.fenghuang.caipiaobao.utils.LaunchUtils
import com.fenghuang.caipiaobao.utils.UserInfoSp
import com.fenghuang.caipiaobao.widget.cardview.LCardView
import com.fenghuang.caipiaobao.widget.dialog.guide.HomeGuideDialog
import com.fenghuang.caipiaobao.widget.gif.GifImageView
import com.hwangjr.rxbus.RxBus
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.thread.EventThread
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

    override fun isShowTitleRightLogo() = false

    override fun isOverridePage() = false

    override fun isShowBackIcon() = false

    override fun isRegisterRxBus() = true

    override fun initContentView() {
        super.initContentView()
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), true)
        ImageManager.loadRoundLogo(UserInfoSp.getUserPhoto(), findView(R.id.ivTitleLeft))
        setImageResource(findView(R.id.ivTitleRight), R.mipmap.ic_home_top_notice)
        smartRefreshLayout.setOnRefreshListener {
            mPresenter.loadData()
        }
        if (!UserInfoSp.getMainGuide()) {
            HomeGuideDialog(getPageActivity(), getPageActivity()).show()
            UserInfoSp.putMainGuide(true)
        }
        tvErrorRetry.setOnClickListener {
            //            mPresenter.loadCache()
        }
    }

    override fun initData() {
        //先加载默认视图
        initBaseView()
    }

    private fun initBaseView() {
        baseBanner()
        baseGameList()
        baseNotice()
        baseHotLive()
        baseLivePop()
        baseExpertLive()
        baseExpertRecommend()
        mPresenter.loadData()
    }


    override fun initEvent() {
        super.initEvent()
        findView<ImageView>(R.id.ivTitleLeft).setOnClickListener {
            if (UserInfoSp.getIsLogin()) {
                RxBus.get().post(HomeClickMine(isClick = true))
            } else ExceptionDialog.showExpireDialog(getPageActivity())
        }

        linHotMore.setOnClickListener {
            if (FastClickUtils.isFastClick()) {
                LaunchUtils.startFragment(getPageActivity(), HomeMoreLiveFragment.newInstance(1))
            }
        }

        linExpertMore.setOnClickListener {
            if (FastClickUtils.isFastClick()) {
                LaunchUtils.startFragment(getPageActivity(), HomeMoreLiveFragment.newInstance(2))
            }
        }
        imgHomeAd.setOnClickListener {
            if (FastClickUtils.isFastClick()) {
                LaunchUtils.startFragment(getPageActivity(), MineAnchorGetFragment())
            }
        }

    }
//------------------------------直播预告------------------------------------------------------------------------------------------------------------------------
    /**
     * 默认直播预告
     */
    private fun baseLivePop() {
        val list: List<HomeLivePopResponse>
        list = ArrayList()
        for (index in 1..3) {
            list.add(HomeLivePopResponse(0, "null", false, "加载中..", "加载中.", -1, -1, 0))
        }
        rvLivePre.layoutManager = LinearLayoutManager(getPageActivity(), LinearLayoutManager.HORIZONTAL, false)
        val homeLiveNoticeAdapter = HomeLiveNoticeAdapter(getPageActivity())
        homeLiveNoticeAdapter.addAll(list)
        rvLivePre.adapter = homeLiveNoticeAdapter
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
//------------------------------热门直播------------------------------------------------------------------------------------------------------------------------
    /**
     * 默认热门直播
     */
    private fun baseHotLive() {
        val list: List<HomeLiveListResponse>
        list = ArrayList()
        for (index in 1..6) {
            list.add(HomeLiveListResponse(0, "", "", 0, "", "", "", 0, arrayListOf()))
        }
        val hotLiveAdapter = object : BaseRecyclerAdapter<HomeLiveListResponse>(getPageActivity()) {
            override fun onCreateHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<HomeLiveListResponse> {
                return HotLiveHolder(parent)
            }

            inner class HotLiveHolder(parent: ViewGroup) : BaseViewHolder<HomeLiveListResponse>(getContext(), parent, R.layout.holder_home_hot_live) {
                override fun onBindData(data: HomeLiveListResponse) {
                    setText(R.id.tvHotLiveTitle, "加载中...")
                    findView<ImageView>(R.id.ivHotLiveLogo).setImageResource(R.mipmap.ic_placeholder)
                    setText(R.id.tvHotLiveName, "加载中...")
                    setText(R.id.tvHotLiveTag, "加载中...")
                }
            }
        }
        rvHotLive.adapter = hotLiveAdapter

        val gridLayoutManager = object : GridLayoutManager(context, 2) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        hotLiveAdapter.addAll(list)
        rvHotLive.layoutManager = gridLayoutManager

        if (rvHotLive.itemDecorationCount == 0) {
            rvHotLive.addItemDecoration(GridItemSpaceDecoration(2, itemSpace = ViewUtils.dp2px(10), startAndEndSpace = ViewUtils.dp2px(6)))
        }
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
                        setText(R.id.tvHotLiveNumber, data.online.toString())
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
                            if (FastClickUtils.isFastClick()) {
                                startLiveRoom(data.live_status, data.anchor_id, data.live_intro, data.avatar)
                            }
                        }
                    }
                }
            }
            if (data.size >= 6) {
                val list = data.subList(0, 6)
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
//------------------------------专家直播------------------------------------------------------------------------------------------------------------------------------
    /**
     * 默认专家直播
     */
    private fun baseExpertLive() {
        val list: List<HomeLiveListResponse>
        list = ArrayList()
        for (index in 1..6) {
            list.add(HomeLiveListResponse(0, "", "", 0, "", "", "", 0, arrayListOf()))
        }
        val mExpertLiveAdapter = object : BaseRecyclerAdapter<HomeLiveListResponse>(getPageActivity()) {
            override fun onCreateHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<HomeLiveListResponse> {
                return ExpertLiveHolder(parent)
            }

            inner class ExpertLiveHolder(parent: ViewGroup) : BaseViewHolder<HomeLiveListResponse>(getContext(), parent, R.layout.holder_home_hot_live) {
                override fun onBindData(data: HomeLiveListResponse) {
                    setText(R.id.tvHotLiveTitle, "加载中...")
                    findView<ImageView>(R.id.ivHotLiveLogo).setImageResource(R.mipmap.ic_placeholder)
                    setText(R.id.tvHotLiveName, "加载中...")
                    setText(R.id.tvHotLiveTag, "加载中...")
                }
            }
        }

        mExpertLiveAdapter.addAll(list)

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
     *  专家直播
     */
    fun updateExpertLive(data: List<HomeLiveListResponse>) {
        val mExpertLiveAdapter = object : BaseRecyclerAdapter<HomeLiveListResponse>(getPageActivity()) {
            override fun onCreateHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<HomeLiveListResponse> {
                return ExpertLiveHolder(parent)
            }

            inner class ExpertLiveHolder(parent: ViewGroup) : BaseViewHolder<HomeLiveListResponse>(getContext(), parent, R.layout.holder_home_hot_live) {
                override fun onBindData(data: HomeLiveListResponse) {
                    setText(R.id.tvHotLiveTitle, data.name)
                    setText(R.id.tvHotLiveIntro, data.live_intro)
                    setText(R.id.tvHotLiveName, data.nickname)
                    setText(R.id.tvHotLiveNumber, data.online.toString())
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
                        if (FastClickUtils.isFastClick()) {
                            startLiveRoom(data.live_status, data.anchor_id, data.live_intro, data.avatar)
                        }
                    }
                }
            }
        }

        if (data.size >= 6) {
            val list = data.subList(0, 6)
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

//------------------------------游戏榜------------------------------------------------------------------------------------------------------------------------------
    /**
     * 默认专家推荐
     */
    private fun baseExpertRecommend() {
        val list: List<HomeExpertRecommendResponse>
        list = ArrayList()
        for (index in 1..6) {
            list.add(HomeExpertRecommendResponse(0, "", "加载中..", "加载中..", "0"))
        }
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
                    findView<ImageView>(R.id.ivExpertRecommendLogo).setImageResource(R.mipmap.ic_mine_base_user)
                }
            }
        }
        mExpertAdapter.addAll(list)
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

//------------------------------游戏榜------------------------------------
    /**
     * 默认游戏榜
     */

    private fun baseGameList() {
        gameTypeGridPager.setDataAllCount(12)
                .setItemBindDataListener { imageView, tvTitle, ivLiveStatus, _, _, position ->
                    imageView.setImageResource(R.mipmap.ic_placeholder)
                    ivLiveStatus.visibility = View.GONE
                    tvTitle.text = ""
                }
                .show()
    }

    /**
     * 更新游戏榜
     */
    fun updateGameList(it: List<HomeGameListResponse>) {
        if (it.isNotEmpty()) {
            RxBus.get().post(HomeClickVideo(it))
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
                        if (FastClickUtils.isFastClick()) {
                            if (it.isNotEmpty() && it[position].live_status == 1) {
                                startLiveRoom(it[position].live_status, it[position].anchor_id, it[position].name, it[position].image)
                            } else ToastUtils.showNormal("此彩种暂无直播")
                        }
                    }
                    .show()
        }
    }
//------------------------------BannerView------------------------------------
    /**
     * 默认BannerView
     */
    private fun baseBanner() {
        val list: List<HomeBannerResponse>
        list = ArrayList()
        for (index in 1..3) {
            list.add(HomeBannerResponse("", "", "", "", 1, ""))
        }
        val mBannerView = findView<BannerView<HomeBannerResponse>>(R.id.mBannerViews)
        mBannerView.setPages(list, object : BannerHolderCreator<HomeBannerResponse, HomeBannerHolder> {
            override fun onCreateBannerHolder(): HomeBannerHolder {
                return HomeBannerHolder(mBannerView.getPageMode() == BannerView.PageMode.FAR)
            }
        })
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
//------------------------------公告------------------------------------
    /**
     * 默认公告
     */
    private fun baseNotice() {
        tvNoticeMassages.setText("暂无公告。")
        tvNoticeMassages.setTextColor(getColor(R.color.color_notice_message))
    }

    /**
     * 更新公告
     */
    fun updateNotice(message: String?) {
        tvNoticeMassages.setText(message!! + "")
        tvNoticeMassages.setTextColor(getColor(R.color.color_notice_message))
    }
//--------------------------------------------------------------------

    /**
     * 跳转直播间
     */
    private fun startLiveRoom(status: Int, anchorId: Int, name: String, photo: String) {
//        if (status == 1) {
        if (NetWorkUtils.isNetworkConnected()) {

            LaunchUtils.startFragment(getPageActivity(), HomeLiveDetailsFragment.newInstance(anchorId, name, status, photo))
//            val intent = Intent(getPageActivity(), HomeLiveDetailsActivity::class.java)
//            intent.putExtra(IntentConstant.HOME_LIVE_CHAT_ANCHOR_ID,anchorId)
//            intent.putExtra(IntentConstant.HOME_LIVE_CHAT_TITLE,name)
//            intent.putExtra(    IntentConstant.HOME_LIVE_CHAT_STATUE,status)
//            intent.putExtra("status",anchorId)
//            startActivity(intent)
        } else ToastUtils.showError("网络连接已断开")
//        } else ToastUtils.showToast("主播正在赶来的路上...")
    }


    /**
     * 接收头像变化
     */
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun onReciveID(eventBean: UserChangePhoto) {
        if (eventBean.isUpLoad) ImageManager.loadRoundLogo(eventBean.photo, findView(R.id.ivTitleLeft))
    }

    /**
     * 更新直播预告
     */
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun onReciveID(eventBean: MineIsAnchorLive) {
        mPresenter.liveFaber(false)
        if (eventBean.isLive == "GoUp") mPresenter.liveFaber(true)
    }

    /**
     * 退出登录
     */
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun onEditUserInfo(eventBean: LoginSuccess) {
        if (eventBean.loginOrExit) {
            ImageManager.loadRoundLogo(eventBean.avatar, findView(R.id.ivTitleLeft))
        } else {
            findView<ImageView>(R.id.ivTitleLeft).setImageResource(R.mipmap.ic_mine_base_user)
        }
    }

}