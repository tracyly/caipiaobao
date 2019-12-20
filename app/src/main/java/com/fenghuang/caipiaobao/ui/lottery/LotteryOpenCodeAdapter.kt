package com.fenghuang.caipiaobao.ui.lottery

import android.content.Context
import android.view.ViewGroup
import com.fenghuang.baselib.base.recycler.BaseRecyclerAdapter
import com.fenghuang.baselib.base.recycler.BaseViewHolder
import com.fenghuang.baselib.utils.LogUtils
import com.fenghuang.baselib.utils.ViewUtils
import com.fenghuang.baselib.widget.round.RoundTextView
import com.fenghuang.caipiaobao.R

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/4- 13:55
 * @ Describe 开奖号码
 *
 */

class LotteryOpenCodeAdapter(context: Context) : BaseRecyclerAdapter<String>(context) {

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<String> {
        return LotteryTypeHolder(parent)
    }


    inner class LotteryTypeHolder(val parent: ViewGroup) : BaseViewHolder<String>(getContext(), parent, R.layout.holder_lottery_opencode) {


        override fun onBindData(data: String) {
            val textView = findView<RoundTextView>(R.id.tvOpenCode)
            val params = textView.layoutParams
            params.width = (ViewUtils.getScreenWidth() - ViewUtils.dp2px(80)) / getAllData().size
            params.height = (ViewUtils.getScreenWidth() - ViewUtils.dp2px(80)) / getAllData().size
            textView.layoutParams = params
            LogUtils.e("----------->" + textView.width.toString() + "--" + getAllData().size.toString())
            when (data) {
                "1" -> textView.delegate.backgroundColor = getColor(R.color.color_F8E96B)
                "2" -> textView.delegate.backgroundColor = getColor(R.color.color_73CCF9)
                "3" -> textView.delegate.backgroundColor = getColor(R.color.color_888888)
                "4" -> textView.delegate.backgroundColor = getColor(R.color.color_FAA056)
                "5" -> textView.delegate.backgroundColor = getColor(R.color.color_5EF0BA)
                "6" -> textView.delegate.backgroundColor = getColor(R.color.color_6653DD)
                "7" -> textView.delegate.backgroundColor = getColor(R.color.color_BBBBBB)
                "8" -> textView.delegate.backgroundColor = getColor(R.color.color_F8767A)
                "9" -> textView.delegate.backgroundColor = getColor(R.color.color_E03940)
                "10" -> textView.delegate.backgroundColor = getColor(R.color.color_84EC7F)
            }
            setText(R.id.tvOpenCode, data)
        }

    }


}

