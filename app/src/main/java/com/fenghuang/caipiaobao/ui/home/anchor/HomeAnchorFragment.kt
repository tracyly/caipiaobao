package com.fenghuang.caipiaobao.ui.home.anchor

import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.fenghuang.baselib.base.adapter.BaseFragmentPageAdapter
import com.fenghuang.baselib.base.fragment.BaseFragment
import com.fenghuang.baselib.base.mvp.BaseMvpFragment
import com.fenghuang.baselib.utils.StatusBarUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.constant.IntentConstant
import com.fenghuang.caipiaobao.manager.ImageManager
import com.fenghuang.caipiaobao.ui.home.data.HomeLiveAnchorInfoBean
import com.fenghuang.caipiaobao.utils.GobalExceptionDialog.ExceptionDialog
import com.fenghuang.caipiaobao.utils.UserInfoSp
import kotlinx.android.synthetic.main.fragment_home_anchor_information.*


/**
 *  author : Peter
 *  date   : 2019/10/19 16:19
 *  desc   : 主播详情页
 */
class HomeAnchorFragment : BaseMvpFragment<HomeAnchorPresenter>() {


    private var liveSate = -1
    private var avatar = "-1"


    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = HomeAnchorPresenter()

    override fun getLayoutResID() = R.layout.fragment_home_anchor_information

    override fun initContentView() {
        anchorTabView.setRankingTab()
    }

    override fun initEvent() {
        ivAnchorBack.setOnClickListener { pop() }
        anchorTabView.setOnSelectListener {
            viewPager.currentItem = it
        }
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                anchorTabView.setTabSelect(position)
            }
        })
        tvAnchorAddAttentions.setOnClickListener {
            if (UserInfoSp.getIsLogin()) {
                mPresenter.getMoney()
                mPresenter.setAttention(arguments?.getInt(IntentConstant.HOME_LIVE_CHAT_ANCHOR_ID)
                        ?: 0, 0)
            } else ExceptionDialog.showExpireDialog(getPageActivity())

        }
        tvAnchorNoAttentions.setOnClickListener {
            if (UserInfoSp.getIsLogin()) {
                mPresenter.setAttention(arguments?.getInt(IntentConstant.HOME_LIVE_CHAT_ANCHOR_ID)
                        ?: 0, 1)
            } else ExceptionDialog.showExpireDialog(getPageActivity())
        }

        anchorLiveStatusLayout.setOnClickListener {
            //            if (liveSate != -1 && avatar != "-1")
//                LaunchUtils.startFragment(getPageActivity(), HomeLiveDetailsFragment.newInstance(arguments?.getInt(IntentConstant.HOME_LIVE_CHAT_ANCHOR_ID)
//                        ?: 0, "", liveSate, avatar))
            pop()
        }

    }

    override fun initData() {
        mPresenter.loadData(arguments?.getInt(IntentConstant.HOME_LIVE_CHAT_ANCHOR_ID) ?: 0)
        showContent(placeholder)

    }

    fun updateInfo(it: HomeLiveAnchorInfoBean) {
        liveSate = it.liveStatus
        avatar = it.avatar
        ImageManager.loadRoundFrameUserLogo(it.avatar, findView(R.id.ivAnchorLogo), 10, getColor(R.color.white))
        ImageManager.loadImage(it.avatar, findView(R.id.imgAnchorBg))

        setText(R.id.tvAnchorUsername, it.nickname)
        if (it.age != 0) setText(R.id.tvAnchorAge, it.age.toString())
        setImageResource(findView(R.id.ivAnchorSex), if (it.sex == 0) R.mipmap.ic_anchor_girl else R.mipmap.ic_anchor_boy)
//        setAnchorLevel(it.level)
        setText(R.id.tvAnchorSignature, it.sign)
        setText(R.id.tvAnchorAttention, it.follow_num.toString())
        setText(R.id.tvAnchorFan, it.fans.toString())
        setText(R.id.tvAnchorZan, it.level.toString())


        if (it.isFollow) {
            setGone(tvAnchorAddAttentions)
            setVisibility(tvAnchorNoAttentions, true)
        } else {
            setGone(tvAnchorNoAttentions)
            setVisibility(tvAnchorAddAttentions, true)
        }
        if (it.liveStatus == 0) {
            ivAnchorLiveStatus.background = getDrawable(R.mipmap.ic_anchor_no_live)
        } else {
            if (ivAnchorLiveStatus != null) {
                ivAnchorLiveStatus.setGifResource(R.drawable.ic_home_live_gif)
                ivAnchorLiveStatus.play(-1)
            }
        }
        setFragmentViewPager(it)


    }

    private fun setFragmentViewPager(it: HomeLiveAnchorInfoBean) {
        val fragments = arrayListOf<BaseFragment>(
                HomeAnchorDataFragment.newInstance(it),
                HomeAnchorDynamicFragment.newInstance(it.anchor_id)
        )
        val adapter = BaseFragmentPageAdapter(childFragmentManager, fragments)
        viewPager.adapter = adapter
        viewPager.currentItem = 0
        viewPager.offscreenPageLimit = fragments.size
    }

    private fun setAnchorLevel(level: Int) {
        when (level) {
            1 -> setImageResource(findView(R.id.ivAnchorVip), R.mipmap.ic_live_chat_vip_1)
            2 -> setImageResource(findView(R.id.ivAnchorVip), R.mipmap.v2)
            3 -> setImageResource(findView(R.id.ivAnchorVip), R.mipmap.v3)
            4 -> setImageResource(findView(R.id.ivAnchorVip), R.mipmap.v4)
            5 -> setImageResource(findView(R.id.ivAnchorVip), R.mipmap.v5)
            6 -> setImageResource(findView(R.id.ivAnchorVip), R.mipmap.v6)
            7 -> setImageResource(findView(R.id.ivAnchorVip), R.mipmap.v7)
        }
    }


    companion object {
        fun newInstance(anchorId: Int): HomeAnchorFragment {
            val fragment = HomeAnchorFragment()
            val bundle = Bundle()
            bundle.putInt(IntentConstant.HOME_LIVE_CHAT_ANCHOR_ID, anchorId)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), true)
    }
}