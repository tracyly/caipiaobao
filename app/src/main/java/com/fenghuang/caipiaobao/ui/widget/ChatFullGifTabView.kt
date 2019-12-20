package com.fenghuang.caipiaobao.ui.widget

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.fenghuang.baselib.utils.ViewUtils
import com.fenghuang.baselib.widget.round.RoundTextView
import com.fenghuang.caipiaobao.R


/**
 * 聊天室礼物列表TabView自定义样式
 */
class ChatFullGifTabView : FrameLayout {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(
            context: Context, attrs: AttributeSet?,
            defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)

    private var mListener: ((position: Int) -> Unit)? = null
    private var position = 0
    private var isAnchorTab = true

    private val tvTabOrdinary: TextView by lazy<TextView> { findViewById(R.id.tvTabOrdinary) }
    private val tvTabConfession: TextView by lazy<TextView> { findViewById(R.id.tvTabConfession) }
    private val tvTabLottery: TextView by lazy<TextView> { findViewById(R.id.tvTabLottery) }
    private val roundOrdinaryLine: RoundTextView by lazy<RoundTextView> { findViewById(R.id.roundOrdinaryLine) }
    private val roundConfessionLine: RoundTextView by lazy<RoundTextView> { findViewById(R.id.roundConfessionLine) }
    private val roundLotteryLine: RoundTextView by lazy<RoundTextView> { findViewById(R.id.roundLotteryLine) }
    private val rlChatTabLottery: RelativeLayout by lazy<RelativeLayout> { findViewById(R.id.rlChatTabLottery) }
    private val rlChatGifOrdinary: RelativeLayout by lazy<RelativeLayout> { findViewById(R.id.rlChatGifOrdinary) }
    private val rlChatTabConfession: RelativeLayout by lazy<RelativeLayout> { findViewById(R.id.rlChatTabConfession) }

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_dialog_tab_view, this)
        tvTabConfession.setTextColor(context.resources.getColor(R.color.white))
        tvTabLottery.setTextColor(context.resources.getColor(R.color.white))
    }

    /**
     * 选中状态，前面的选中，后面的未选中
     */
    private fun setTabSelectStyle(selectView: TextView, unSelectView: TextView, unSelectView2: TextView) {
        selectView.setTextColor(ViewUtils.getColor(R.color.color_FF513E))
        selectView.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
        selectView.textSize = 17f
        unSelectView.setTextColor(ViewUtils.getColor(R.color.white))
        unSelectView.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
        unSelectView.textSize = 15f
        unSelectView2.setTextColor(ViewUtils.getColor(R.color.white))
        unSelectView2.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
        unSelectView2.textSize = 15f
    }

    /**
     * 选中状态，前面的选中，后面的未选中
     */
    private fun setTabSelectLineVisib(selectView: RoundTextView, unSelectView: RoundTextView, unSelectView2: TextView) {
        selectView.visibility = View.VISIBLE
        unSelectView.visibility = View.GONE
        unSelectView2.visibility = View.GONE
    }

    fun setOnSelectListener(listener: (position: Int) -> Unit) {
        this.mListener = listener
    }


    fun setChatGifTab() {
        initEvent()
        tvTabOrdinary.textSize = 15f
        tvTabConfession.textSize = 15f
        tvTabLottery.textSize = 15f
        setTabSelectStyle(tvTabOrdinary, tvTabConfession, tvTabLottery)
        setTabSelectLineVisib(roundOrdinaryLine, roundConfessionLine, roundLotteryLine)
    }


    fun setTabSelect(position: Int) {
        this.position = position
        when (position) {
            0 -> {
                setTabSelectStyle(tvTabOrdinary, tvTabConfession, tvTabLottery)
                setTabSelectLineVisib(roundOrdinaryLine, roundConfessionLine, roundLotteryLine)
            }
            1 -> {
                setTabSelectStyle(tvTabConfession, tvTabOrdinary, tvTabLottery)
                setTabSelectLineVisib(roundConfessionLine, roundOrdinaryLine, roundLotteryLine)
            }
            2 -> {
                setTabSelectStyle(tvTabLottery, tvTabConfession, tvTabOrdinary)
                setTabSelectLineVisib(roundLotteryLine, roundConfessionLine, roundOrdinaryLine)
            }
        }
    }


    private fun initEvent() {
        rlChatGifOrdinary.setOnClickListener {
            if (position != 0) {
                setTabSelectStyle(tvTabOrdinary, tvTabConfession, tvTabLottery)
                setTabSelectLineVisib(roundOrdinaryLine, roundConfessionLine, roundLotteryLine)
                position = 0
                mListener?.invoke(position)
            }
        }
        rlChatTabConfession.setOnClickListener {
            if (position != 1) {
                setTabSelectStyle(tvTabConfession, tvTabOrdinary, tvTabLottery)
                setTabSelectLineVisib(roundConfessionLine, roundOrdinaryLine, roundLotteryLine)
                position = 1
                mListener?.invoke(position)
            }
        }
        rlChatTabLottery.setOnClickListener {
            if (position != 2) {
                setTabSelectStyle(tvTabLottery, tvTabOrdinary, tvTabConfession)
                setTabSelectLineVisib(roundLotteryLine, roundOrdinaryLine, roundConfessionLine)
                position = 2
                mListener?.invoke(position)
            }
        }
    }
}