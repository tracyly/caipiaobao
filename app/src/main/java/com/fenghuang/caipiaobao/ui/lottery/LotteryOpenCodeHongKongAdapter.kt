package com.fenghuang.caipiaobao.ui.lottery

import android.content.Context
import android.view.ViewGroup
import com.fenghuang.baselib.base.recycler.BaseRecyclerAdapter
import com.fenghuang.baselib.base.recycler.BaseViewHolder
import com.fenghuang.baselib.widget.round.RoundTextView
import com.fenghuang.caipiaobao.R

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/4- 13:55
 * @ Describe 开奖号码  香港六合彩
 *
 */

class LotteryOpenCodeHongKongAdapter(context: Context) : BaseRecyclerAdapter<String>(context) {
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<String> {
        return LotteryTypeHolder(parent)
    }

    inner class LotteryTypeHolder(parent: ViewGroup) : BaseViewHolder<String>(getContext(), parent, R.layout.holder_lottery_opencode) {
        override fun onBindData(data: String) {
            if (data == "+") {
                findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.white)
                findView<RoundTextView>(R.id.tvOpenCode).setTextColor(getColor(R.color.grey_e6))
                findView<RoundTextView>(R.id.tvOpenCode).textSize = 20F
            } else
                when (data) {
                    "1" -> findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.text_red)
                    "2" -> findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.text_red)
                    "3" -> findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.colorBlue)
                    "4" -> findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.colorBlue)
                    "5" -> findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.colorGreen)
                    "6" -> findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.colorGreen)
                    "7" -> findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.text_red)
                    "8" -> findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.text_red)
                    "9" -> findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.colorBlue)
                    "10" -> findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.colorBlue)
                    "11" -> findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.colorGreen)
                    "12" -> findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.text_red)
                    "13" -> findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.text_red)
                    "14" -> findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.colorBlue)
                    "15" -> findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.colorBlue)
                    "16" -> findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.colorGreen)
                    "17" -> findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.colorGreen)
                    "18" -> findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.text_red)
                    "19" -> findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.text_red)
                    "20" -> findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.colorBlue)
                    "21" -> findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.colorGreen)
                    "22" -> findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.colorGreen)
                    "23" -> findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.text_red)
                    "24" -> findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.text_red)
                    "25" -> findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.colorBlue)
                    "26" -> findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.colorBlue)
                    "27" -> findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.colorGreen)
                    "28" -> findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.colorGreen)
                    "29" -> findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.text_red)
                    "30" -> findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.text_red)
                    "31" -> findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.colorBlue)
                    "32" -> findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.colorGreen)
                    "33" -> findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.colorGreen)
                    "34" -> findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.text_red)
                    "35" -> findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.text_red)
                    "36" -> findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.colorBlue)
                    "37" -> findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.colorBlue)
                    "38" -> findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.colorGreen)
                    "39" -> findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.colorGreen)
                    "40" -> findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.text_red)
                    "41" -> findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.colorBlue)
                    "42" -> findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.colorBlue)
                    "43" -> findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.colorGreen)
                    "44" -> findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.colorGreen)
                    "45" -> findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.text_red)
                    "46" -> findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.text_red)
                    "47" -> findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.colorBlue)
                    "48" -> findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.colorBlue)
                    "49" -> findView<RoundTextView>(R.id.tvOpenCode).delegate.backgroundColor = getColor(R.color.colorGreen)
                }
            setText(R.id.tvOpenCode, data)
        }

    }


}

