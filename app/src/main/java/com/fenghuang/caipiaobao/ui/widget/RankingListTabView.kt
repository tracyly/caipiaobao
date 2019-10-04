package com.fenghuang.caipiaobao.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.fenghuang.baselib.utils.ViewUtils
import com.fenghuang.baselib.widget.round.RoundTextView
import com.fenghuang.caipiaobao.R

/**
 *  author : Peter
 *  排行榜的List TabView自定义样式
 *  日榜 周榜 月榜 总榜
 */
class RankingListTabView : FrameLayout {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(
            context: Context, attrs: AttributeSet?,
            defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)

    private var mListener: ((position: Int) -> Unit)? = null
    private var position = 0

    private val tvDayList: RoundTextView  by lazy<RoundTextView> { findViewById(R.id.tvDayList) }
    private val tvWeekList: RoundTextView  by lazy<RoundTextView> { findViewById(R.id.tvWeekList) }
    private val tvMonthList: RoundTextView  by lazy<RoundTextView> { findViewById(R.id.tvMonthList) }
    private val tvAllList: RoundTextView  by lazy<RoundTextView> { findViewById(R.id.tvAllList) }

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_ranking_list_tab_view, this)
    }

    /**
     * 选中状态
     */
    private fun setTabSelectStyle(view: RoundTextView) {
        when (view) {
            tvDayList -> {
                tvDayList.delegate.backgroundColor = ViewUtils.getColor(R.color.color_CAAB8B)
                tvDayList.setTextColor(ViewUtils.getColor(R.color.white))
                tvWeekList.delegate.backgroundColor = ViewUtils.getColor(R.color.color_f1eff0)
                tvWeekList.setTextColor(ViewUtils.getColor(R.color.color_999999))
                tvMonthList.delegate.backgroundColor = ViewUtils.getColor(R.color.color_f1eff0)
                tvMonthList.setTextColor(ViewUtils.getColor(R.color.color_999999))
                tvAllList.delegate.backgroundColor = ViewUtils.getColor(R.color.color_f1eff0)
                tvAllList.setTextColor(ViewUtils.getColor(R.color.color_999999))
            }
            tvWeekList -> {
                tvWeekList.delegate.backgroundColor = ViewUtils.getColor(R.color.color_CAAB8B)
                tvWeekList.setTextColor(ViewUtils.getColor(R.color.white))
                tvDayList.delegate.backgroundColor = ViewUtils.getColor(R.color.color_f1eff0)
                tvDayList.setTextColor(ViewUtils.getColor(R.color.color_999999))
                tvMonthList.delegate.backgroundColor = ViewUtils.getColor(R.color.color_f1eff0)
                tvMonthList.setTextColor(ViewUtils.getColor(R.color.color_999999))
                tvAllList.delegate.backgroundColor = ViewUtils.getColor(R.color.color_f1eff0)
                tvAllList.setTextColor(ViewUtils.getColor(R.color.color_999999))
            }
            tvMonthList -> {
                tvMonthList.delegate.backgroundColor = ViewUtils.getColor(R.color.color_CAAB8B)
                tvMonthList.setTextColor(ViewUtils.getColor(R.color.white))
                tvDayList.delegate.backgroundColor = ViewUtils.getColor(R.color.color_f1eff0)
                tvDayList.setTextColor(ViewUtils.getColor(R.color.color_999999))
                tvWeekList.delegate.backgroundColor = ViewUtils.getColor(R.color.color_f1eff0)
                tvWeekList.setTextColor(ViewUtils.getColor(R.color.color_999999))
                tvAllList.delegate.backgroundColor = ViewUtils.getColor(R.color.color_f1eff0)
                tvAllList.setTextColor(ViewUtils.getColor(R.color.color_999999))
            }
            tvAllList -> {
                tvAllList.delegate.backgroundColor = ViewUtils.getColor(R.color.color_CAAB8B)
                tvAllList.setTextColor(ViewUtils.getColor(R.color.white))
                tvDayList.delegate.backgroundColor = ViewUtils.getColor(R.color.color_f1eff0)
                tvDayList.setTextColor(ViewUtils.getColor(R.color.color_999999))
                tvWeekList.delegate.backgroundColor = ViewUtils.getColor(R.color.color_f1eff0)
                tvWeekList.setTextColor(ViewUtils.getColor(R.color.color_999999))
                tvMonthList.delegate.backgroundColor = ViewUtils.getColor(R.color.color_f1eff0)
                tvMonthList.setTextColor(ViewUtils.getColor(R.color.color_999999))
            }
        }
    }


    fun setOnSelectListener(listener: (position: Int) -> Unit) {
        this.mListener = listener
    }


    fun setRankingTab() {
        initEvent()
        setTabSelectStyle(tvDayList)
    }

    fun setTabSelect(position: Int) {
        this.position = position
        when (position) {
            0 -> setTabSelectStyle(tvDayList)
            1 -> setTabSelectStyle(tvWeekList)
            2 -> setTabSelectStyle(tvMonthList)
            else -> setTabSelectStyle(tvAllList)
        }
    }


    private fun initEvent() {
        tvDayList.setOnClickListener {
            if (position != 0) {
                setTabSelectStyle(tvDayList)
                position = 0
                mListener?.invoke(position)
            }
        }
        tvWeekList.setOnClickListener {
            if (position != 1) {
                setTabSelectStyle(tvWeekList)
                position = 1
                mListener?.invoke(position)
            }
        }
        tvMonthList.setOnClickListener {
            if (position != 2) {
                setTabSelectStyle(tvMonthList)
                position = 2
                mListener?.invoke(position)
            }
        }
        tvAllList.setOnClickListener {
            if (position != 3) {
                setTabSelectStyle(tvAllList)
                position = 3
                mListener?.invoke(position)
            }
        }
    }
}