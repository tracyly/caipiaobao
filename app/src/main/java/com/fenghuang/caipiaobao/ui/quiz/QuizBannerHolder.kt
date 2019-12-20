package com.fenghuang.caipiaobao.ui.quiz

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.manager.ImageManager
import com.fenghuang.caipiaobao.ui.quiz.data.QuizTopImageBean
import com.pingerx.banner.holder.BaseBannerHolder

/**
 *
 * @ Author  QinTian
 * @ Date  2019/12/11- 11:18
 * @ Describe
 *
 */

class QuizBannerHolder(private var isNormalBanner: Boolean) : BaseBannerHolder<QuizTopImageBean> {

    override fun getHolderResId(): Int {
        return if (isNormalBanner) {
            R.layout.holder_banner_normal
        } else R.layout.holder_banner
    }

    override fun onBindData(itemView: View, data: QuizTopImageBean) {
        if (TextUtils.isEmpty(data.img)) {
            itemView.findViewById<ImageView>(R.id.imageView).setImageResource(R.mipmap.ic_placeholder)
        } else {
            ImageManager.loadBannerImageRes(data.img, itemView.findViewById(R.id.imageView))
        }
//        itemView.findViewById<TextView>(R.id.textView)?.text = data.title
    }


}