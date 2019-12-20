package com.fenghuang.caipiaobao.ui.home

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.manager.ImageManager
import com.fenghuang.caipiaobao.ui.home.data.HomeBannerResponse
import com.pingerx.banner.holder.BaseBannerHolder


class HomeBannerHolder(private var isNormalBanner: Boolean) : BaseBannerHolder<HomeBannerResponse> {

    override fun getHolderResId(): Int {
        return if (isNormalBanner) {
            R.layout.holder_banner_normal
        } else R.layout.holder_banner
    }

    override fun onBindData(itemView: View, data: HomeBannerResponse) {
        if (TextUtils.isEmpty(data.image_url)) {
            itemView.findViewById<ImageView>(R.id.imageView).setImageResource(R.mipmap.ic_placeholder)
        } else {
            ImageManager.loadBannerImageRes(data.image_url, itemView.findViewById(R.id.imageView))
        }
//        itemView.findViewById<TextView>(R.id.textView)?.text = data.title
    }

    override fun onPageClick(itemView: View, position: Int, data: HomeBannerResponse) {
//        Toast.makeText(itemView.context, data.title, Toast.LENGTH_SHORT).show()
    }
}