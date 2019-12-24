package com.fenghuang.caipiaobao.ui.mine

import com.fenghuang.baselib.base.mvp.BaseMvpFragment
import com.fenghuang.baselib.base.recycler.header.material.MaterialHeader
import com.fenghuang.baselib.utils.SpUtils
import com.fenghuang.baselib.utils.StatusBarUtils
import com.fenghuang.baselib.utils.ViewUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.manager.ImageManager
import com.fenghuang.caipiaobao.ui.login.LoginFragment
import com.fenghuang.caipiaobao.ui.login.data.LoginExitStart
import com.fenghuang.caipiaobao.ui.login.data.LoginSuccess
import com.fenghuang.caipiaobao.ui.lottery.data.UserChangePhoto
import com.fenghuang.caipiaobao.ui.mine.data.MineIsAnchorLive
import com.fenghuang.caipiaobao.ui.mine.data.MineUpDateUser
import com.fenghuang.caipiaobao.utils.FastClickUtils
import com.fenghuang.caipiaobao.utils.LaunchUtils.startFragment
import com.fenghuang.caipiaobao.utils.UserInfoSp
import com.fenghuang.caipiaobao.widget.lighter.Lighter
import com.fenghuang.caipiaobao.widget.lighter.parameter.Direction
import com.fenghuang.caipiaobao.widget.lighter.parameter.LighterParameter
import com.fenghuang.caipiaobao.widget.lighter.parameter.MarginOffset
import com.fenghuang.caipiaobao.widget.lighter.shape.CircleShape
import com.hwangjr.rxbus.RxBus
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.thread.EventThread
import kotlinx.android.synthetic.main.fragment_mine.*
import kotlinx.android.synthetic.main.fragment_mine_child_view_login.*

/**
 * @ Author  QinTian
 * @ Date  2019/9/30- 12:51
 * @ Describe 我的界面
 */

class MineFragment : BaseMvpFragment<MinePresenter>() {

    var balanceReal = "0.00"

    private lateinit var lighter: Lighter

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = MinePresenter()

    override fun getLayoutResID() = R.layout.fragment_mine

    override fun isRegisterRxBus() = true


    override fun initContentView() {
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), false)
        smartRefreshLayout.setRefreshHeader(MaterialHeader(context!!))
    }

    override fun initData() {
        initUser()
//        mPresenter.initList(getPageActivity(), listItem)
        smartRefreshLayout.setOnRefreshListener {
            mPresenter.getUserInfo()
        }
        if (!UserInfoSp.getMineGuide()) {
            initGuide()
            UserInfoSp.putMineGuide(true)
        }
    }

    //初始化用户信息
    private fun initUser() {
        if (UserInfoSp.getIsLogin()) {
            setGone(R.id.noLogin)
            setVisible(R.id.isLogin)
            ImageManager.loadRoundFrameUserLogo(UserInfoSp.getUserPhoto(), userPhoto, 10, getColor(R.color.white))
            userName.text = UserInfoSp.getUserNickName()
            RxBus.get().post(UserChangePhoto(UserInfoSp.getUserPhoto()!!, "", "", false))

        } else {
            setGone(R.id.isLogin)
            setVisible(R.id.noLogin)
            ImageManager.loadRoundFromBitmap(R.mipmap.ic_home_top_user, userPhoto, getColor(R.color.white))

        }
    }

    override fun initEvent() {
        noLogin.setOnClickListener {
            if (FastClickUtils.isFastClick()) {
                startFragment(context, LoginFragment())
            }
        }
        //头像
        userPhoto.setOnClickListener {
            if (FastClickUtils.isFastClick()) {
                if (UserInfoSp.getIsLogin()) {
                    startFragment(context, MinePersonalFragment())
                } else startFragment(context, LoginFragment())
            }
        }
        //钻石兑换
        linChangeDiamond.setOnClickListener {
            if (FastClickUtils.isFastClick()) {
                if (UserInfoSp.getIsLogin()) {
                    mPresenter.isSetPayPass()
                } else startFragment(context, LoginFragment())
            }
        }
        //充值
        linRecharge.setOnClickListener {
            if (UserInfoSp.getIsLogin()) {
                startFragment(context, MineRechargeFragment.newInstance(balanceReal, 0))
            } else startFragment(context, LoginFragment())
        }
        //提现
        linDrawMoney.setOnClickListener {
            if (UserInfoSp.getIsLogin()) {
                startFragment(context, MineRechargeFragment.newInstance(balanceReal, 1))
            } else startFragment(context, LoginFragment())
        }
        //更新余额
        linBalance.setOnClickListener {
            if (UserInfoSp.getIsLogin()) {
                if (FastClickUtils.isFastClick()) {
                    mPresenter.getUserBalance()
                }
            } else startFragment(context, LoginFragment())

        }
//        //消息图片
//        imgMessage.setOnClickListener {
//
//        }
//
        //主播招募
        linAnchorGet.setOnClickListener {
            if (FastClickUtils.isFastClick()) {
                startFragment(getPageActivity(), MineAnchorGetFragment())
            }
        }
        //豪礼相送
        linSendGift.setOnClickListener {
            if (FastClickUtils.isFastClick()) {
                startFragment(getPageActivity(), MineGiftSendFragment())
            }
        }

        //对应设置跳转
        linSetting1.setOnClickListener {
            if (FastClickUtils.isFastClick()) {
                if (UserInfoSp.getIsLogin()) {
                    startFragment(getPageActivity(), MinePersonalFragment())
                } else startFragment(context, LoginFragment())
            }
        }
        linSetting2.setOnClickListener {
            if (FastClickUtils.isFastClick()) {
                if (UserInfoSp.getIsLogin()) {
                    startFragment(getPageActivity(), MineMyAttentionFragment())
                } else startFragment(context, LoginFragment())
            }
        }
        linSetting3.setOnClickListener {
            if (FastClickUtils.isFastClick()) {
                if (UserInfoSp.getIsLogin()) {
                    startFragment(getPageActivity(), MineRewardRecordFragment())
                } else startFragment(context, LoginFragment())
            }

        }
        linSetting4.setOnClickListener {
            if (FastClickUtils.isFastClick()) {
                if (UserInfoSp.getIsLogin()) {
                    startFragment(getPageActivity(), MineFeedBackFragment())
                } else startFragment(context, LoginFragment())
            }
        }
        linSetting5.setOnClickListener {
            if (FastClickUtils.isFastClick()) {
                if (UserInfoSp.getIsLogin()) {
                    startFragment(getPageActivity(), MineContactCustomerFragment())
                } else startFragment(context, LoginFragment())
            }
        }
        linSetting6.setOnClickListener {
            if (FastClickUtils.isFastClick()) {
                if (UserInfoSp.getIsLogin()) {
                    startFragment(getPageActivity(), MineSettingFragment())
                } else startFragment(context, LoginFragment())
            }

        }
    }

    //保存用户信息
    fun setUserInfo(nickName: String, userAvatar: String, sex: Int, profile: String) {
        ImageManager.loadRoundFrameUserLogo(userAvatar, userPhoto, 10, ViewUtils.getColor(R.color.white))
        userName.text = nickName
        tvDescription.text = profile
        UserInfoSp.putUserNickName(nickName)
        UserInfoSp.putUserSex(sex)
        UserInfoSp.putUserProfile(profile)
    }

    //设置余额
    fun setBalance(data: String) {
        balanceReal = data
    }

    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun onEditUserInfo(eventBean: LoginSuccess) {
        if (eventBean.loginOrExit) {
            UserInfoSp.putIsLogin(true)
            setGone(R.id.noLogin)
            setVisible(R.id.isLogin)
            mPresenter.getUserInfo()
            setVisible(linSetting6)
        } else {
            //清除所有Sp保存的值，除去已经显示过的guide
            spClear()
            initUser()
            tvUserBalance.text = "0.00"
            tvUserDiamond.text = "0"
            setGone(linSetting6)
            setGone(tvHasAnchorLive)
        }
    }


    //是否有主播在直播
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun onIsAnchorLive(eventBean: MineIsAnchorLive) {
        mPresenter.isAnchorLive()
    }

    override fun isSupportVisible() = true


    fun finishRefresh() {
        smartRefreshLayout.finishRefresh()
    }


    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun upDateUser(eventBean: MineUpDateUser) {
        if (eventBean.upDateAll) mPresenter.getUserInfo()
        if (eventBean.upDateMoney) mPresenter.getUserBalance()
        if (eventBean.upDateDiamond) mPresenter.getUserDiamond()
        if (eventBean.upDateDiamond) mPresenter.getUserVip()
    }

    private fun initGuide() {

        lighter = Lighter.with(activity)
        lighter.setAutoNext(true)
                .addHighlight(
                        LighterParameter.Builder()
                                .setHighlightedViewId(R.id.imgBalance)
                                .setTipLayoutId(R.layout.dialog_guide_mine_balance)
                                .setLighterShape(CircleShape(20f))
                                .setTipViewRelativeDirection(Direction.TOP)
                                .setTipViewRelativeOffset(MarginOffset(0, -330, 0, 50))
                                .build(),
                        LighterParameter.Builder()
                                .setHighlightedViewId(R.id.imgDiamond)
                                .setTipLayoutId(R.layout.dialog_guide_mine_diamond)
                                .setLighterShape(CircleShape(20f))
                                .setTipViewRelativeDirection(Direction.BOTTOM)
                                .setTipViewRelativeOffset(MarginOffset(0, -138, 30, 0))
                                .build()).setBackgroundColor(getColor(R.color.transparent_82))
                .show()
    }

    /**
     * 接收头像变化
     */
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun onReciveID(eventBean: UserChangePhoto) {
        if (eventBean.isUpLoad) {
            ImageManager.loadRoundFrameUserLogo(eventBean.photo, userPhoto, 10, getColor(R.color.white))
            userName.text = eventBean.name
            tvDescription.text = eventBean.sign
        }
        if (eventBean.loadAll) {
            mPresenter.getUserInfo()
        }
    }

    /**
     * 打开个人信息
     */
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun onStartPresonal(eventBean: LoginExitStart) {
        startFragment(getPageActivity(), MinePersonalFragment())
    }


    //清除所有Sp保存的值，除去已经显示过的guide
    private fun spClear() {
        val one = UserInfoSp.getMainGuide()
        val second = UserInfoSp.getOpenCodeGuide()
        val third = UserInfoSp.getMineGuide()
        val four = UserInfoSp.getRewardnGuide()
        val five = UserInfoSp.getAttentionGuide()
        SpUtils.clearAll()
        if (one) UserInfoSp.putMainGuide(true)
        if (second) UserInfoSp.putOpenCodeGuide(true)
        if (third) UserInfoSp.putMineGuide(true)
        if (four) UserInfoSp.putRewardnGuide(true)
        if (five) UserInfoSp.putAttentionGuide(true)
    }
}