package com.fenghuang.caipiaobao.ui.lottery

import android.content.Context
import android.graphics.Typeface
import android.view.ViewGroup
import android.widget.TextView
import com.fenghuang.baselib.base.recycler.BaseRecyclerAdapter
import com.fenghuang.baselib.base.recycler.BaseViewHolder
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.manager.ImageManager
import com.fenghuang.caipiaobao.ui.lottery.data.LotteryTypeResponse

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/4- 13:55
 * @ Describe 彩种
 *
 */

class LotteryTypeAdapter(context: Context) : BaseRecyclerAdapter<LotteryTypeResponse>(context) {

    private var clickPosition: Int = 0

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<LotteryTypeResponse> {
        return LotteryTypeHolder(parent)
    }

    inner class LotteryTypeHolder(parent: ViewGroup) : BaseViewHolder<LotteryTypeResponse>(getContext(), parent, R.layout.holder_lottery_type_item) {
        override fun onBindData(data: LotteryTypeResponse) {
            setText(R.id.tvLotteryType, data.cname)
            ImageManager.loadHomeGameListLogo(data.logo_url, findView(R.id.imgLotteryType))
            if (clickPosition == getDataPosition()) {
                findView<TextView>(R.id.tvLotteryType).setTextColor(getColor(R.color.color_333333))
                findView<TextView>(R.id.tvLotteryType).typeface = Typeface.defaultFromStyle(Typeface.BOLD)
            } else {
                findView<TextView>(R.id.tvLotteryType).setTextColor(getColor(R.color.color_999999))
                findView<TextView>(R.id.tvLotteryType).typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
            }
        }
    }

    fun changeBackground(position: Int) {
        clickPosition = position
        notifyDataSetChanged()
    }

}

