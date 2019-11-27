package com.fenghuang.caipiaobao.ui.widget.popup

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.fenghuang.baselib.utils.ViewUtils.getColor
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.manager.ImageManager
import com.fenghuang.caipiaobao.ui.home.data.HomeLiveRedReceiveBean

/**
 *  author : QinTian
 *  date   : 2019/10/14 15:34
 *  desc   : 直播间拆红包
 */
class OpenRedEnvelopeFullDialog(context: Context) : Dialog(context) {

    private var mOpenRed: RelativeLayout
    private var mOpenRedMoney: LinearLayout
    private var mRedOver: RelativeLayout
    private var mOpenRedLogo: ImageView
    private var ivOpenNoRedLogo: ImageView
    private lateinit var mOpenRedKnew: ImageView
    private var mRedContent: TextView
    private var mUserName: TextView
    private var mRedMoney: TextView
    private var tvRedTitle: TextView
    private var tvRedNoName: TextView
    private var tvRedNoContent: TextView
    var ivOpenRedEnvelope: ImageView

    init {
//        addBackground()
        setContentView(R.layout.popup_live_room_open_red_envelope_fullscreen)
        setCanceledOnTouchOutside(false)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window!!.setGravity(Gravity.CENTER or Gravity.CENTER)
        mOpenRed = findViewById(R.id.rlOpenRed)
        mOpenRedMoney = findViewById(R.id.layoutOpenRedMoney)
        mOpenRedLogo = findViewById(R.id.ivOpenRedLogo)
        mRedContent = findViewById(R.id.tvRedContent)
        mUserName = findViewById(R.id.tvRedName)
        mRedMoney = findViewById(R.id.tvRedMoney)
        mRedOver = findViewById(R.id.llRedOver)
        tvRedTitle = findViewById(R.id.tvRedTitle)
        ivOpenNoRedLogo = findViewById(R.id.ivOpenNoRedLogo)
        tvRedNoName = findViewById(R.id.tvRedNoName)
        tvRedNoContent = findViewById(R.id.tvRedNoContent)
        ivOpenRedEnvelope = findViewById(R.id.ivOpenRedEnvelope)
//        findView<ImageView>(R.id.ivOpenRedKnew).setOnClickListener {
//            dismiss()
//        }
        ivOpenRedEnvelope.setOnClickListener {
            mListener?.invoke()
        }
//        mRedDelete.setOnClickListener {
//            dismiss()
//        }
        findViewById<ImageView>(R.id.relRedClose).setOnClickListener {
            dismiss()
        }
    }

    private var mListener: (() -> Unit)? = null
    fun setOnOpenClickListener(listener: () -> Unit) {
        mListener = listener
    }

    fun setOnOpenClickListener(listener: View.OnClickListener) {
        ivOpenRedEnvelope.setOnClickListener(listener)
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
        if (url.isNotEmpty()) ImageManager.redUserPhoto(url, mOpenRedLogo, getColor(R.color.color_F3BC88))
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

    fun showRedOver(bean: HomeLiveRedReceiveBean) {
        mRedOver.visibility = View.VISIBLE
        ImageManager.redUserPhoto(bean.send_user_avatar, ivOpenNoRedLogo, getColor(R.color.color_F3BC88))
        tvRedNoName.text = bean.send_user_name
        tvRedNoContent.text = bean.send_text

    }

    fun setRedTitle(gift_text: String) {
        tvRedTitle.text = gift_text
    }


}