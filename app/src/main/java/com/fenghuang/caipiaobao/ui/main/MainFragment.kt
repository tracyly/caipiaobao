package com.fenghuang.caipiaobao.ui.main

import android.os.CountDownTimer
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.fenghuang.baselib.base.fragment.BaseFragment
import com.fenghuang.baselib.base.fragment.BasePageFragment
import com.fenghuang.baselib.utils.StatusBarUtils
import com.fenghuang.baselib.utils.ViewUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.bet.BetFragment
import com.fenghuang.caipiaobao.ui.home.HomeFragmentNew
import com.fenghuang.caipiaobao.ui.home.data.HomeApi
import com.fenghuang.caipiaobao.ui.home.data.HomeClickMine
import com.fenghuang.caipiaobao.ui.login.data.LoginSuccess
import com.fenghuang.caipiaobao.ui.login.data.LoginToMain
import com.fenghuang.caipiaobao.ui.lottery.LotteryFragment
import com.fenghuang.caipiaobao.ui.lottery.data.UserChangePhoto
import com.fenghuang.caipiaobao.ui.mine.MineFragment
import com.fenghuang.caipiaobao.ui.quiz.QuizFragment
import com.fenghuang.caipiaobao.utils.GobalExceptionDialog.ExceptionDialog
import com.fenghuang.caipiaobao.utils.UserInfoSp
import com.fenghuang.caipiaobao.widget.dialog.NewVisionDialog
import com.hwangjr.rxbus.RxBus
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.thread.EventThread
import kotlinx.android.synthetic.main.activity_main.*


class MainFragment : BasePageFragment() {

    private val mFragments = arrayListOf<BaseFragment>()

    override fun getLayoutResID() = R.layout.activity_main
    override fun isRegisterRxBus() = true

    override fun initView() {


        mFragments.add(HomeFragmentNew())
        mFragments.add(LotteryFragment())
        mFragments.add(BetFragment())
//        mFragments.add(WebNavFragment.newInstance("https://www.h5682.com","1111", webBack = true, swipeBack = true))
        mFragments.add(QuizFragment())
        mFragments.add(MineFragment())
        loadMultipleRootFragment(R.id.mainContainer, 0,
                mFragments[0], mFragments[1], mFragments[2], mFragments[3], mFragments[4])


    }

    override fun initEvent() {
        tabHome.setOnClickListener {
            StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), true)
            tabHome.isChecked = true
            tabLive.isChecked = false
            tabRanking.isChecked = false
            tabMine.isChecked = false
            tabBetting.isFocusable = false
            showHideFragment(mFragments[0])
        }
        tabLive.setOnClickListener {
            StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), true)
            tabHome.isChecked = false
            tabRanking.isChecked = false
            tabMine.isChecked = false
            tabLive.isChecked = true
            tabBetting.isFocusable = false
            showHideFragment(mFragments[1])
        }
        tabBetting.setOnClickListener {
            StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), true)
            tabHome.isChecked = false
            tabRanking.isChecked = false
            tabLive.isChecked = false
            tabMine.isChecked = false
            tabBetting.isChecked = true
            showHideFragment(mFragments[2])
        }
        tabRanking.setOnClickListener {
            StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), true)
            tabRanking.isChecked = true
            tabHome.isChecked = false
            tabLive.isChecked = false
            tabMine.isChecked = false
            tabBetting.isFocusable = false
            showHideFragment(mFragments[3])
        }
        tabMine.setOnClickListener {
            StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), false)
            tabMine.isChecked = true
            tabHome.isChecked = false
            tabLive.isChecked = false
            tabRanking.isChecked = false
            tabBetting.isFocusable = false
            showHideFragment(mFragments[4])
            if (UserInfoSp.getIsLogin())
                RxBus.get().post(UserChangePhoto(UserInfoSp.getUserPhoto()!!, "", "", false, loadAll = true))
        }
        imgLotteryBuyTips.setOnClickListener {
            setVisibility(R.id.imgLotteryBuyTips, false)
        }
    }


    override fun initData() {

        getUpDate()


    }

    private fun getUpDate() {
        HomeApi.getVersion {
            onSuccess {
                if (it.version_data != null) {
                    val dialog = NewVisionDialog(getPageActivity())
                    dialog.setContent(it.version_data?.upgradetext!!)
                    if (it.version_data?.enforce == 1) {
                        dialog.setCanceledOnTouchOutside(false)
                    } else dialog.setCanceledOnTouchOutside(true)
                    dialog.setJum(it.version_data?.downloadurl!!)
                    dialog.show()
                }

            }
        }
    }


    /**
     * 接收Home头像点击事件
     */
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun onClickMine(clickMine: HomeClickMine) {
        if (UserInfoSp.getIsLogin()) {
            StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), false)
            tabMine.isChecked = true
            tabHome.isChecked = false
            tabLive.isChecked = false
            tabRanking.isChecked = false
            tabBetting.isFocusable = false
            showHideFragment(mFragments[4])
        } else ExceptionDialog.showExpireDialog(getPageActivity())
    }


    /**
     * 接收Home头像点击事件
     */
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun onClickMain(clickMine: LoginToMain) {
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), true)
        tabMine.isChecked = false
        tabHome.isChecked = true
        tabLive.isChecked = false
        tabRanking.isChecked = false
        tabBetting.isFocusable = false
        showHideFragment(mFragments[0])
    }


    private fun showNotification(textString: String) {
        //自定义Toast控件
        val toastView = LayoutInflater.from(getPageActivity()).inflate(R.layout.pop_anchor_push, null)
        val relativeLayout = toastView.findViewById(R.id.rootContent) as LinearLayout
        val layoutParams = FrameLayout.LayoutParams(ViewUtils.getScreenWidth() - ViewUtils.dp2px(40), ViewUtils.dp2px(40))
        relativeLayout.layoutParams = layoutParams
        val textView = toastView.findViewById(R.id.pushContent) as TextView
        textView.text = textString
        val toast = Toast(getPageActivity())
        toast.duration = Toast.LENGTH_SHORT
        toast.view = toastView
        toast.setGravity(Gravity.TOP, 0, 0)
        toast.view.systemUiVisibility = SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN//设置Toast可以布局到系统状态栏的下面
        toast.show()
    }


    private val pushTime: Long = 300000
    var timer: CountDownTimer? = null
    private fun anchorNotify() {
        //初始化主播开播提醒
        timer = object : CountDownTimer(pushTime, 1000) {
            //根据间隔时间来不断回调此方法，这里是每隔1000ms调用一次
            override fun onTick(millisUntilFinished: Long) {
                //todo millisUntilFinished为剩余时间，也就是30000 - n*1000

            }

            //结束倒计时调用
            override fun onFinish() {
                anchorPush()
            }
        }
        timer?.start()
    }

    private fun anchorPush() {
        HomeApi.anchorPush {
            onSuccess {
                showNotification("您关注的" + "'" + it.anchor_nickname + "'主播已开播")
                timer?.start()
            }
            onFailed {
                //                showNotification(it.getMsg()!!)
                timer?.start()
            }
        }
    }

    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun onEditUserInfo(eventBean: LoginSuccess) {
        if (eventBean.loginOrExit) {
            anchorNotify()
        } else {
            timer?.cancel()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()

    }
}