package com.fenghuang.caipiaobao.ui.mine

import ExceptionDialog
import ExceptionDialog.showExpireDialog
import android.content.Context
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fenghuang.baselib.base.mvp.BaseMvpPresenter
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.mine.data.MineApi
import com.fenghuang.caipiaobao.ui.mine.data.MineDataBean
import com.fenghuang.caipiaobao.utils.UserInfoSp
import kotlinx.android.synthetic.main.fragment_mine_child_view_login.*

class MinePresenter : BaseMvpPresenter<MineFragment>() {

    private val newResults = arrayListOf<MineDataBean>()
    private lateinit var rotateAnimation: Animation

    fun initList(context: Context, listItem: RecyclerView) {
        newResults.add(MineDataBean("个人资料", R.mipmap.ic_mine_preson))
        newResults.add(MineDataBean("我的关注", R.mipmap.ic_mine_attention))
        newResults.add(MineDataBean("打赏记录", R.mipmap.ic_mine_ds))
        newResults.add(MineDataBean("意见反馈", R.mipmap.ic_mine_advice))
        newResults.add(MineDataBean("联系客服", R.mipmap.ic_mine_contact))
        newResults.add(MineDataBean("设置", R.mipmap.ic_mine_setting))
        val mineItemAdapter = MineAdapter(context)
        mineItemAdapter.addAll(newResults)
        listItem.adapter = mineItemAdapter
        val value = object : LinearLayoutManager(context) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        listItem.layoutManager = value
    }

    //获取用户信息
    fun getUserInfo() {
        if (UserInfoSp.getIsLogin()) {
            MineApi.getUserInfo {
                onSuccess {
                    UserInfoSp.putUserName(it.username)
                    UserInfoSp.putUserPhoto(it.avatar)
                    UserInfoSp.putUserNickName(it.nickname)
                    mView.setUserInfo(it.nickname, it.avatar, it.gender, it.profile)
                    getUserBalance()
                }
                onFailed {
                    showExpireDialog(mView.requireContext(), it)
                    mView.finishRefresh()
                }
            }
        } else {
            mView.finishRefresh()
            showExpireDialog(mView.requireContext())
        }
    }

    //获取余额
    fun getUserBalance() {
        rotateAnimation = AnimationUtils.loadAnimation(mView.requireContext(), R.anim.anim_circle_rotate)
        val interpolator = LinearInterpolator()
        rotateAnimation.interpolator = interpolator
        mView.imgBalance.startAnimation(rotateAnimation)
        MineApi.getUserBalance {
            onSuccess {
                mView.imgBalance.clearAnimation()
                mView.setUserBalance(it.balance.toString())
                getUserDiamond()
            }
            onFailed {
                mView.imgBalance.clearAnimation()
                ExceptionDialog.showExpireDialog(mView.requireContext(), it)
            }
        }
    }

    //获取钻石
    private fun getUserDiamond() {
        MineApi.getUserDiamond {
            onSuccess {
                mView.setUserDiamond(it.diamond)
                mView.finishRefresh()
            }
            onFailed {
                ExceptionDialog.showExpireDialog(mView.requireContext(), it)
            }
        }

    }
}