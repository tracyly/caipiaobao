package com.fenghuang.caipiaobao.ui.widget.popup

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.fenghuang.baselib.widget.popup.BasePopupWindow
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.manager.ImageManager

/**
 *  author : Peter
 *  date   : 2019/10/14 15:34
 *  desc   : 直播间拆红包
 */
class OpenRedEnvelopePopup(context: Context) : BasePopupWindow(context, R.layout.popup_live_room_open_red_envelope) {

    private var mOpenRed: RelativeLayout
    private var mOpenRedMoney: LinearLayout
    private var mRedOver: LinearLayout
    private var mOpenRedLogo: ImageView
    //    private lateinit var mRedDelete: ImageView
    private lateinit var mOpenRedKnew: ImageView
    private var mRedContent: TextView
    private var mUserName: TextView
    private var mRedMoney: TextView

    init {
//        width = ViewUtils.getScreenWidth()
        addBackground()
        isFocusable = true
        isOutsideTouchable = true
        mOpenRed = findView(R.id.rlOpenRed)
        mOpenRedMoney = findView(R.id.layoutOpenRedMoney)
        mOpenRedLogo = findView(R.id.ivOpenRedLogo)
//        mRedDelete = findView<ImageView>(R.id.ivRedDelete)
        mRedContent = findView(R.id.tvRedContent)
        mUserName = findView(R.id.tvRedName)
        mRedMoney = findView(R.id.tvRedMoney)

        mRedOver = findView(R.id.llRedOver)
        findView<ImageView>(R.id.ivOpenRedKnew).setOnClickListener {
            dismiss()
        }
        findView<ImageView>(R.id.ivOpenRed).setOnClickListener {
            mListener?.invoke()
        }
//        mRedDelete.setOnClickListener {
//            dismiss()
//        }
    }

    private var mListener: (() -> Unit)? = null
    fun setOnOpenClickListener(listener: () -> Unit) {
        mListener = listener
    }

    private fun addBackground() {
        val attributes = (context as Activity).window.attributes
        attributes.alpha = 0.7f
        (context as Activity).window.attributes = attributes
        this.setOnDismissListener {
            val attributes = (context as Activity).window.attributes
            attributes.alpha = 1f
            (context as Activity).window.attributes = attributes
        }
    }

    fun setRedLogo(url: String) {
        if (url.isNotEmpty()) ImageManager.loadRoundLogo(url, mOpenRedLogo)
    }

    fun setRedUserName(userName: String) {
        mUserName.text = userName
    }

    fun setRedContent(content: String) {
        mRedContent.text = content
    }

    fun setRedMoney(money: String) {
        mRedMoney.text = money
    }

    fun isShowRedLogo(isShowLogo: Boolean) {
        if (isShowLogo) {
            mOpenRed.visibility = View.GONE
            mOpenRedMoney.visibility = View.VISIBLE
        }
    }

    fun showRedOver() {
        mRedOver.visibility = View.VISIBLE
    }
}