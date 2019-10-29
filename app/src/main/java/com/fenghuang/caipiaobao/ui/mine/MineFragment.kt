package com.fenghuang.caipiaobao.ui.mine

import ExceptionDialog.showExpireDialog
import android.annotation.SuppressLint
import com.fenghuang.baselib.base.mvp.BaseMvpFragment
import com.fenghuang.baselib.base.recycler.header.material.MaterialHeader
import com.fenghuang.baselib.utils.SpUtils
import com.fenghuang.baselib.utils.StatusBarUtils
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.baselib.utils.ViewUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.manager.ImageManager
import com.fenghuang.caipiaobao.ui.login.LoginFragment
import com.fenghuang.caipiaobao.ui.login.data.LoginSuccess
import com.fenghuang.caipiaobao.utils.LaunchUtils.startFragment
import com.fenghuang.caipiaobao.utils.UserInfoSp
import com.fenghuang.caipiaobao.widget.dialog.DiamondDialog
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.thread.EventThread
import kotlinx.android.synthetic.main.fragment_mine.*
import kotlinx.android.synthetic.main.fragment_mine_child_view.*
import kotlinx.android.synthetic.main.fragment_mine_child_view_login.*


/**
 *
 * @ Author  QinTian
 * @ Date  2019/9/30- 12:51
 * @ Describe 我的界面
 *
 */

class MineFragment : BaseMvpFragment<MinePresenter>() {


    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = MinePresenter()

    override fun getLayoutResID() = R.layout.fragment_mine

    override fun isRegisterRxBus() = true


    override fun initContentView() {
        StatusBarUtils.setStatusBarForegroundColor(getPageActivity(), false)
        smartRefreshLayout.setRefreshHeader(MaterialHeader(context!!))
        initUser()
        smartRefreshLayout.setOnRefreshListener {
            mPresenter.getUserInfo()
        }
    }

    //初始化用户信息
    private fun initUser() {
        if (UserInfoSp.getIsLogin()) {
            setGone(R.id.noLogin)
            setVisible(R.id.isLogin)
            ImageManager.loadRoundFrameUserLogo(UserInfoSp.getUserPhoto(), userPhoto, 12, getColor(R.color.white))
            userName.text = UserInfoSp.getUserNickName()
        } else {
            setGone(R.id.isLogin)
            setVisible(R.id.noLogin)
            ImageManager.loadRoundFromBitmap(R.mipmap.ic_home_top_user, userPhoto, getColor(R.color.white))
        }
    }

    override fun initData() {
        mPresenter.initList(getPageActivity(), listItem)
        smartRefreshLayout.autoRefresh()
    }

    override fun initEvent() {

        tvLogin.setOnClickListener {
            startFragment(context, LoginFragment())
        }
        //头像
        userPhoto.setOnClickListener {
            if (UserInfoSp.getIsLogin()) {
                startFragment(context, MinePersonalFragment())
            } else startFragment(context, LoginFragment())
        }
        //存款
        layoutMineSaveMoney.setOnClickListener {
            if (UserInfoSp.getIsLogin()) {
                startFragment(context, MineRechargeFragment.newInstance(tvUserBalance.text.toString()))
            } else showExpireDialog(getPageActivity())
        }
        //钻石兑换
        linChangeDiamond.setOnClickListener {
            if (UserInfoSp.getIsLogin()) {
                val dialog = DiamondDialog(getPageActivity())
                dialog.setConfirmClickListener {
                    ToastUtils.showSuccess("成功")
                    dialog.dismiss()
                }
                dialog.show()
            } else showExpireDialog(getPageActivity())
        }
        //充值
        linRecharge.setOnClickListener {
            if (UserInfoSp.getIsLogin()) {
                startFragment(context, MineRechargeFragment.newInstance(tvUserBalance.text.toString()))
            } else showExpireDialog(getPageActivity())
        }
        //提现
        linDrawMoney.setOnClickListener {
            if (UserInfoSp.getIsLogin()) {

            } else showExpireDialog(getPageActivity())
        }
        //更新余额

        linBalance.setOnClickListener {
            if (UserInfoSp.getIsLogin()) {
                mPresenter.getUserBalance()
            } else showExpireDialog(getPageActivity())
        }
    }

    //保存用户信息
    fun setUserInfo(nickName: String, userAvatar: String, sex: Int, profile: String) {
        ImageManager.loadRoundFrameUserLogo(userAvatar, userPhoto, 12, ViewUtils.getColor(R.color.white))
        userName.text = nickName
        UserInfoSp.putUserNickName(nickName)
        UserInfoSp.putUserSex(sex)
        UserInfoSp.putUserProfile(profile)
    }

    //获取用户余额,钻石
    @SuppressLint("SetTextI18n")
    fun setUserBalance(balance: String) {
        tvUserBalance.text = "￥$balance"
    }

    fun setUserDiamond(diamond: String) {
        tvUserDiamond.text = diamond
    }

    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun onEditUserInfo(eventBean: LoginSuccess) {
        if (eventBean.loginOrExit) {
            UserInfoSp.putIsLogin(true)
            setGone(R.id.noLogin)
            setVisible(R.id.isLogin)
            mPresenter.getUserInfo()
        } else {
            SpUtils.clearAll()
            initUser()
            tvUserBalance.text = "0"
            tvUserDiamond.text = "0"
        }
    }


    fun finishRefresh() {
        smartRefreshLayout.finishRefresh()
    }

}