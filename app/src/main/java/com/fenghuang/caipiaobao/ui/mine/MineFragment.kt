package com.fenghuang.caipiaobao.ui.mine

import com.fenghuang.baselib.base.mvp.BaseMvpFragment
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.login.LoginFragment
import com.fenghuang.caipiaobao.utils.LaunchUtils.startFragment
import kotlinx.android.synthetic.main.fragment_mine.*
import kotlinx.android.synthetic.main.fragment_mine_child_view.*


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


    override fun initData() {
        mPresenter.initList(getPageActivity(), listItem)
    }


    override fun initEvent() {

        tvLogin.setOnClickListener {
            //            noLogin.visibility = View.GONE
//            isLogin.visibility = View.VISIBLE
//            userInfoItemLogin.visibility = View.GONE
//            userInfoItem.visibility = View.VISIBLE
//            ImageManager.loadRoundFrameUserLogo("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3677209778,3519789803&fm=26&gp=0.jpg", findView(R.id.userPhoto), getColor(R.color.white))
            startFragment(context, LoginFragment())
        }

        userPhoto.setOnClickListener {
            startFragment(context, LoginFragment())
        }

        layoutMineSaveMoney.setOnClickListener {
            startFragment(context, MineRechargeFragment())
        }

        btExitLogin.setOnClickListener {
            //            dialog = ExitDialog(getPageActivity(), "确认是否退出?", "确认", "取消", View.OnClickListener {
//                startFragment(context, LoginFragment())
//                dialog.dismiss()
//            }, View.OnClickListener {
//                dialog.dismiss()
//            })
//            dialog.show()
        }

    }

}