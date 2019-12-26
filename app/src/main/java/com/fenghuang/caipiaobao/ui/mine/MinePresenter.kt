package com.fenghuang.caipiaobao.ui.mine

import android.annotation.SuppressLint
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import com.fenghuang.baselib.base.mvp.BaseMvpPresenter
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.home.data.HomeApi
import com.fenghuang.caipiaobao.ui.login.data.LoginApi
import com.fenghuang.caipiaobao.ui.mine.data.MineApi
import com.fenghuang.caipiaobao.utils.GobalExceptionDialog.ExceptionDialog.showExpireDialog
import com.fenghuang.caipiaobao.utils.UserInfoSp
import com.fenghuang.caipiaobao.widget.dialog.DiamondDialog
import com.fenghuang.caipiaobao.widget.dialog.TipsConfirmDialog
import kotlinx.android.synthetic.main.fragment_mine.*
import kotlinx.android.synthetic.main.fragment_mine_child_view_login.*
import org.json.JSONObject
import java.math.BigDecimal
import java.math.BigDecimal.ROUND_DOWN

class MinePresenter : BaseMvpPresenter<MineFragment>() {

    //    private val newResults = arrayListOf<MineDataBean>()
    private lateinit var rotateAnimation: Animation
//    var mineItemAdapter: MineAdapter? = null
//    fun initList(context: Context, listItem: RecyclerView) {
//        newResults.add(MineDataBean("个人资料", R.mipmap.ic_mine_preson))
//        newResults.add(MineDataBean("我的关注", R.mipmap.ic_mine_attention))
//        newResults.add(MineDataBean("打赏记录", R.mipmap.ic_mine_ds))
//        newResults.add(MineDataBean("意见反馈", R.mipmap.ic_mine_advice))
//        newResults.add(MineDataBean("联系客服", R.mipmap.ic_mine_contact))
//        newResults.add(MineDataBean("设置", R.mipmap.ic_mine_setting))
//        mineItemAdapter = MineAdapter(context)
//        mineItemAdapter?.addAll(newResults)
//        mineItemAdapter?.setHasStableIds(true)
//        listItem.adapter = mineItemAdapter
//        val value = object : LinearLayoutManager(context) {
//            override fun canScrollVertically(): Boolean {
//                return false
//            }
//        }
//        listItem.layoutManager = value
//    }

    //获取用户信息
    fun getUserInfo() {
        if (UserInfoSp.getIsLogin()) {
            MineApi.getUserInfo {
                onSuccess {
                    if (mView.isActive()) {
                        UserInfoSp.putUserName(it.username)
                        UserInfoSp.putUserPhoto(it.avatar)
                        UserInfoSp.putUserNickName(it.nickname)
                        mView.setUserInfo(it.nickname, it.avatar, it.gender, it.profile)
                        getUserBalance()
                        getUserDiamond()
                    }
                }
                onFailed {
                    showExpireDialog(mView.requireContext(), it)
                    mView.finishRefresh()
                }
            }
            getUserVip()
        } else {
            mView.finishRefresh()
            showExpireDialog(mView.requireContext())
        }
    }


    //获取Vip
    fun getUserVip() {
        MineApi.getUserVip {
            onSuccess {
                when {
                    it.vip == 0 -> mView.setGone(mView.imgVip)
                    it.vip == 1 -> {
                        mView.setVisible(mView.imgVip)
                        mView.imgVip.setImageResource(R.mipmap.vip1)
                    }
                    it.vip == 2 -> {
                        mView.imgVip.setImageResource(R.mipmap.vip4)
                        mView.imgVip.setImageResource(R.mipmap.vip2)
                    }
                    it.vip == 3 -> {
                        mView.imgVip.setImageResource(R.mipmap.vip4)
                        mView.imgVip.setImageResource(R.mipmap.vip3)
                    }
                    it.vip == 4 -> {
                        mView.imgVip.setImageResource(R.mipmap.vip4)
                        mView.imgVip.setImageResource(R.mipmap.vip4)
                    }
                    it.vip == 5 -> {
                        mView.imgVip.setImageResource(R.mipmap.vip4)
                        mView.imgVip.setImageResource(R.mipmap.vip5)
                    }
                    it.vip == 6 -> {
                        mView.imgVip.setImageResource(R.mipmap.vip4)
                        mView.imgVip.setImageResource(R.mipmap.vip6)
                    }
                    it.vip == 7 -> {
                        mView.imgVip.setImageResource(R.mipmap.vip4)
                        mView.imgVip.setImageResource(R.mipmap.vip7)
                    }
                }

                UserInfoSp.setVipLevel(it.vip)
            }
            onFailed { mView.setGone(mView.imgVip) }
        }
    }

    //获取余额
    @SuppressLint("SetTextI18n")
    fun getUserBalance() {
        if (mView.isActive()) {
            rotateAnimation = AnimationUtils.loadAnimation(mView.requireContext(), R.anim.anim_circle_rotate)
            val interpolator = LinearInterpolator()
            rotateAnimation.interpolator = interpolator
            mView.imgBalance.startAnimation(rotateAnimation)
            MineApi.getUserBalance {
                onSuccess {
                    if (it.balance.compareTo(BigDecimal(10000000)) > -1) {
                        mView.tvUserBalance.text = it.balance.divide(BigDecimal(1000), ROUND_DOWN).setScale(0, ROUND_DOWN).toString() + "k"
                    } else mView.tvUserBalance.text = it.balance.toString()
                    mView.setBalance(it.balance.toString())
                }
                onFailed {
                    showExpireDialog(mView.requireContext(), it)
                }
            }
            //是否设置支付密码
            HomeApi.isSetPassWord {
                onSuccess {
                    if (mView.isActive()) {
                        UserInfoSp.putIsSetPayPassWord(true)
                    }
                }
                onFailed {
                    if (mView.isActive()) {
                        UserInfoSp.putIsSetPayPassWord(false)
                    }
                }
            }
        }

    }

    //检测是否设置支付密码
    fun isSetPayPass() {
        HomeApi.isSetPassWord {
            onSuccess {
                if (mView.isActive()) {
                    UserInfoSp.putIsSetPayPassWord(true)
                    MineApi.getUserBalance {
                        onSuccess {
                            val dialog = DiamondDialog(mView.requireActivity(), mView.balanceReal)
                            dialog.show()
                        }
                        onFailed { showExpireDialog(mView.requireActivity(), it) }
                    }
                }
            }
            onFailed {
                if (mView.isActive()) {
                    if (it.getCode() == 10) {
                        val dialog = TipsConfirmDialog(mView.requireActivity(), "钱包已被冻结,请联系客服", "我知道了", "取消", "")
                        dialog.show()
                    } else showExpireDialog(mView.requireActivity(), it)
                }
            }
        }
    }


    //获取钻石
    fun getUserDiamond() {
        MineApi.getUserDiamond {
            onSuccess {
                if (mView.isActive()) {
                    mView.tvUserDiamond.text = it.diamond
                    mView.finishRefresh()
                }
            }
            onFailed {
                showExpireDialog(mView.requireContext(), it)
                mView.finishRefresh()
            }
        }
        isAnchorLive()
    }

    //是否有主播在直播
    fun isAnchorLive() {
        MineApi.isAnchorLive {
            onSuccess {
                if (mView.isActive()) {
//                    mineItemAdapter?.notifyIsLive(it)
                    mView.setVisible(mView.tvHasAnchorLive)
                    userIsFirstRecharge(UserInfoSp.getUserId(), UserInfoSp.getToken()!!)
                }
//                if (UserInfoSp.getIsFirstRecharge())

            }
            onFailed {
                if (mView.isActive()) {
//                    mineItemAdapter?.notifyIsLive("")
                    mView.setGone(mView.tvHasAnchorLive)
                    userIsFirstRecharge(UserInfoSp.getUserId(), UserInfoSp.getToken()!!)
                }
            }
//            RxBus.get().post(MineIsAnchorLive(it))
        }

    }

    /**
     * 是否首冲
     */
    private fun userIsFirstRecharge(user_id: Int, token: String) {
        LoginApi.isFirstRecharge(user_id, token) {
            onSuccess {
                if (mView.isActive()) {
                    if (JSONObject(it).optString("isfirst") == "0") {
                        UserInfoSp.putIsFirstRecharge(true)
                    } else UserInfoSp.putIsFirstRecharge(false)
                }
            }
            onFailed {

            }
        }
    }


}