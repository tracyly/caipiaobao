package com.fenghuang.caipiaobao.widget.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.Gravity
import com.fenghuang.baselib.utils.ViewUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.mine.data.MineApi
import com.fenghuang.caipiaobao.ui.mine.data.MinePassWordTime
import com.fenghuang.caipiaobao.ui.mine.data.MineUpDateUser
import com.fenghuang.caipiaobao.utils.GobalExceptionDialog.ExceptionDialog.showExpireDialog
import com.fenghuang.caipiaobao.utils.JsonUtils
import com.hwangjr.rxbus.RxBus
import kotlinx.android.synthetic.main.dialog_diamond.*
import java.math.BigDecimal

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/24- 19:51
 * @ Describe 钻石兑换
 *
 */

class DiamondDialog(context: Context, var balance: String) : Dialog(context) {

    var loadDialog: LoadingDialog? = null

    init {
        setContentView(R.layout.dialog_diamond)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window!!.setGravity(Gravity.CENTER or Gravity.CENTER)
        val lp = window!!.attributes
        lp.width = ViewUtils.dp2px(316) // 宽度
        lp.height = ViewUtils.dp2px(238)  // 高度
//      lp.alpha = 0.7f // 透明度
        window!!.attributes = lp
        setCanceledOnTouchOutside(false)
        initDialog()
    }

    @SuppressLint("SetTextI18n")
    private fun initDialog() {


        tvDiamondCount.text = balance

        if (imgClose !== null) {
            imgClose.setOnClickListener {
                dismiss()
            }
        }
        etChangeMoney.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!TextUtils.isEmpty(s.toString()) && BigDecimal(s.toString()).compareTo(BigDecimal(1)) > -1) {
                    if (BigDecimal(s.toString()).compareTo(BigDecimal(balance)) < 1) {
                        tvConfirmChange.delegate.backgroundColor = ViewUtils.getColor(R.color.color_FF3F30)
                        tvConfirmChange.setTextColor(ViewUtils.getColor(R.color.white))
                        tvConfirmChange.text = "兑换"
                        if (tvConfirmChange !== null) {
                            tvConfirmChange.setOnClickListener {
                                //                                mListener?.invoke()
                                initPassWordDialog()
                            }
                        }
                    } else {
                        tvConfirmChange.delegate.backgroundColor = ViewUtils.getColor(R.color.color_DDDDDD)
                        tvConfirmChange.setTextColor(ViewUtils.getColor(R.color.white))
                        tvConfirmChange.text = "余额不足"
                        tvConfirmChange.setOnClickListener(null)
                        tvConfirmChange.isClickable = false
                    }
                } else {
                    tvConfirmChange.delegate.backgroundColor = ViewUtils.getColor(R.color.color_f5f5f5)
                    tvConfirmChange.setTextColor(ViewUtils.getColor(R.color.color_DDDDDD))
                    tvConfirmChange.setOnClickListener(null)
                    tvConfirmChange.isClickable = false
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        tvAllExchange.setOnClickListener {
            if (balance.contains(".")) {
                etChangeMoney.setText(balance.substring(0, balance.indexOf(".")))
            } else etChangeMoney.setText(balance)
        }
    }

    var passWordDialog: PassWordDialog? = null
    private fun initPassWordDialog() {
        passWordDialog = PassWordDialog(context, ViewUtils.getScreenWidth(), ViewUtils.dp2px(156))
        passWordDialog!!.setTextWatchListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s?.length == 6) {
                    //验证支付密码
                    MineApi.verifyPayPassWord(s.toString()) {
                        onSuccess {
                            //兑换钻石
                            exChangeDiamond(s.toString())
                        }
                        onFailed {
                            if (it.getCode() == 1002) {
                                passWordDialog!!.showTipsText(it.getMsg().toString() + "," + "您还有" + JsonUtils.fromJson(it.getDataCode().toString(), MinePassWordTime::class.java).remain_times.toString() + "次机会")
                                passWordDialog!!.clearText()
                            } else {
                                passWordDialog!!.showTipsText(it.getMsg().toString())
                            }
                        }
                    }
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        passWordDialog!!.show()
        this.dismiss()
    }

    private fun exChangeDiamond(passWord: String) {
        passWordDialog?.dismiss()
        loadDialog = LoadingDialog(context)
        loadDialog?.show()
        MineApi.getUserChangeDiamond(etChangeMoney.text.toString(), passWord) {
            onSuccess {
                RxBus.get().post(MineUpDateUser(true, upDateAll = false, upDateDiamond = true))
                dismiss()
                loadDialog?.dismiss()
            }
            onFailed {
                showExpireDialog(context, it)
                loadDialog?.dismiss()
            }

        }
    }


//    private var mListener: (() -> Unit)? = null
//    fun setConfirmClickListener(listener: () -> Unit) {
//        mListener = listener
//    }
}