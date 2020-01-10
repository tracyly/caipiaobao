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
class ChatGifTabView : FrameLayout {

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
    private val tv4: TextView by lazy<TextView> { findViewById(R.id.tv4) }
    private val tv5: TextView by lazy<TextView> { findViewById(R.id.tv5) }
    private val roundOrdinaryLine: RoundTextView by lazy<RoundTextView> { findViewById(R.id.roundOrdinaryLine) }
    private val roundConfessionLine: RoundTextView by lazy<RoundTextView> { findViewById(R.id.roundConfessionLine) }
    private val roundLotteryLine: RoundTextView by lazy<RoundTextView> { findViewById(R.id.roundLotteryLine) }
    private val lin4: RoundTextView by lazy<RoundTextView> { findViewById(R.id.lin4) }
    private val lin5: RoundTextView by lazy<RoundTextView> { findViewById(R.id.lin5) }
    private val rlChatTabLottery: RelativeLayout by lazy<RelativeLayout> { findViewById(R.id.rlChatTabLottery) }
    private val rlChatGifOrdinary: RelativeLayout by lazy<RelativeLayout> { findViewById(R.id.rlChatGifOrdinary) }
    private val rlChatTabConfession: RelativeLayout by lazy<RelativeLayout> { findViewById(R.id.rlChatTabConfession) }
    private val rl4: RelativeLayout by lazy<RelativeLayout> { findViewById(R.id.rl4) }
    private val rl5: RelativeLayout by lazy<RelativeLayout> { findViewById(R.id.rl5) }

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_dialog_tab_view, this)
    }

    /**
     * 选中状态，前面的选中，后面的未选中
     */
    private fun setTabSelectStyle(selectView: TextView, unSelectView: TextView, unSelectView2: TextView, unSelectView3: TextView, unSelectView4: TextView) {
        selectView.setTextColor(ViewUtils.getColor(R.color.text_red))
        selectView.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
        selectView.textSize = 17f
        unSelectView.setTextColor(ViewUtils.getColor(R.color.color_333333))
        unSelectView.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
        unSelectView.textSize = 15f
        unSelectView2.setTextColor(ViewUtils.getColor(R.color.color_333333))
        unSelectView2.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
        unSelectView2.textSize = 15f
        unSelectView3.setTextColor(ViewUtils.getColor(R.color.color_333333))
        unSelectView3.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
        unSelectView3.textSize = 15f
        unSelectView4.setTextColor(ViewUtils.getColor(R.color.color_333333))
        unSelectView4.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
        unSelectView4.textSize = 15f
    }

    /**
     * 选中状态，前面的选中，后面的未选中
     */
    private fun setTabSelectLineVisib(selectView: RoundTextView, unSelectView: RoundTextView, unSelectView2: RoundTextView, unSelectView3: RoundTextView, unSelectView4: RoundTextView) {
        selectView.visibility = View.VISIBLE
        unSelectView.visibility = View.GONE
        unSelectView2.visibility = View.GONE
        unSelectView3.visibility = View.GONE
        unSelectView4.visibility = View.GONE
    }

    fun setOnSelectListener(listener: (position: Int) -> Unit) {
        this.mListener = listener
    }


    fun setChatGifTab() {
        initEvent()
        tvTabOrdinary.textSize = 15f
        tvTabConfession.textSize = 15f
        tvTabLottery.textSize = 15f
        setTabSelectStyle(tvTabOrdinary, tvTabConfession, tvTabLottery, tv4, tv5)
        setTabSelectLineVisib(roundOrdinaryLine, roundConfessionLine, roundLotteryLine, lin4, lin5)
    }


    fun setTabSelect(position: Int) {
        this.position = position
        when (position) {
            0 -> {
                setTabSelectStyle(tvTabOrdinary, tvTabConfession, tvTabLottery,tv4,tv5)
                setTabSelectLineVisib(roundOrdinaryLine, roundConfessionLine, roundLotteryLine,lin4,lin5)
            }
            1 -> {
                setTabSelectStyle(tvTabConfession, tvTabOrdinary, tvTabLottery,tv4,tv5)
                setTabSelectLineVisib(roundConfessionLine, roundOrdinaryLine, roundLotteryLine,lin4,lin5)
            }
            2 -> {
                setTabSelectStyle(tvTabLottery, tvTabConfession, tvTabOrdinary,tv4,tv5)
                setTabSelectLineVisib(roundLotteryLine, roundConfessionLine, roundOrdinaryLine,lin4,lin5)
            }
            3 -> {
                setTabSelectStyle(tv4,tv5,tvTabLottery, tvTabConfession, tvTabOrdinary)
                setTabSelectLineVisib(lin4,lin5,roundLotteryLine, roundConfessionLine, roundOrdinaryLine)
            }
            4 -> {
                setTabSelectStyle(tv5,tv4,tvTabLottery, tvTabConfession, tvTabOrdinary)
                setTabSelectLineVisib(lin5,lin4,roundLotteryLine, roundConfessionLine, roundOrdinaryLine)
            }
        }
    }


    private fun initEvent() {
        rlChatGifOrdinary.setOnClickListener {
            if (position != 0) {
                setTabSelectStyle(tvTabOrdinary, tvTabConfession, tvTabLottery,tv4,tv5)
                setTabSelectLineVisib(roundOrdinaryLine, roundConfessionLine, roundLotteryLine,lin4,lin5)
                position = 0
                mListener?.invoke(position)
            }
        }
        rlChatTabConfession.setOnClickListener {
            if (position != 1) {
                setTabSelectStyle(tvTabConfession, tvTabOrdinary, tvTabLottery,tv4,tv5)
                setTabSelectLineVisib(roundConfessionLine, roundOrdinaryLine, roundLotteryLine,lin4,lin5)
                position = 1
                mListener?.invoke(position)
            }
        }
        rlChatTabLottery.setOnClickListener {
            if (position != 2) {
                setTabSelectStyle(tvTabLottery, tvTabConfession, tvTabOrdinary,tv4,tv5)
                setTabSelectLineVisib(roundLotteryLine, roundConfessionLine, roundOrdinaryLine,lin4,lin5)
                position = 2
                mListener?.invoke(position)
            }
        }
        rl4.setOnClickListener {
            if (position != 3) {
                setTabSelectStyle(tv4,tv5,tvTabLottery, tvTabConfession, tvTabOrdinary)
                setTabSelectLineVisib(lin4,lin5,roundLotteryLine, roundConfessionLine, roundOrdinaryLine)
                position = 3
                mListener?.invoke(position)
            }
        }
        rl5.setOnClickListener {
            if (position != 4) {
                setTabSelectStyle(tv5,tv4,tvTabLottery, tvTabConfession, tvTabOrdinary)
                setTabSelectLineVisib(lin5,lin4,roundLotteryLine, roundConfessionLine, roundOrdinaryLine)
                position = 4
                mListener?.invoke(position)
            }
        }
    }
}