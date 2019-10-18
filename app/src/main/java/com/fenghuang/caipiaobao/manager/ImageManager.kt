package com.fenghuang.caipiaobao.manager

import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.fenghuang.baselib.utils.ViewUtils
import com.fenghuang.caipiaobao.R
import com.pingerx.imagego.core.strategy.loadCircle
import com.pingerx.imagego.core.strategy.loadRound
import com.pingerx.imagego.core.utils.RoundType

/**
 * 图片加载管理器，业务层管理
 */
object ImageManager {

    /**
     * 加载圆形图片不带边框
     */
    fun loadRoundLogo(url: String?, imageView: ImageView) {
        // 使用圆形图片，设置不可以动画
        loadCircle(url, imageView, placeHolder = R.mipmap.ic_placeholder_avatar, errorHolder = R.mipmap.ic_home_top_user)
    }

    /**
     * 加载圆形图片带有边框
     */
    fun loadRoundFrameLogo(url: String?, imageView: ImageView, color: Int) {
        loadCircle(url, imageView, placeHolder = R.mipmap.ic_placeholder_avatar, errorHolder = R.mipmap.ic_home_top_user, borderWidth = 6, borderColor = color)
    }

    /**
     * 加载圆形图片带有边框（用户头像）
     */
    fun loadRoundFrameUserLogo(url: String?, imageView: ImageView, color: Int) {
        loadCircle(url, imageView, placeHolder = R.mipmap.ic_placeholder_avatar, errorHolder = R.mipmap.ic_mine_base_user, borderWidth = 6, borderColor = color)
    }


    /**
     * 加载圆形图片带有边框（Bitmap）
     */
    fun loadRoundFromBitmap(url: Bitmap?, imageView: ImageView, color: Int) {
        loadCircle(url, imageView, placeHolder = R.mipmap.ic_placeholder_avatar, errorHolder = R.mipmap.ic_mine_base_user, borderWidth = 16, borderColor = color)
    }

    /**
     * 加载直播图标
     */
    fun loadLiveImageRes(url: String?, imageView: ImageView, roundType: RoundType) {
        loadRound(url, imageView, 10, roundType)
    }

    /**
     * 加载游戏榜logo
     */

    fun loadHomeGameListLogo(url: String?, imageView: ImageView) {
        Glide.with(imageView.context).load(url).placeholder(R.mipmap.ic_launcher_round).into(imageView)
    }

    /**
     * 加载热门直播图片
     */
    fun loadHomeHotLive(url: String?, imageView: ImageView) {
        loadRound(url, imageView, ViewUtils.dp2px(6), RoundType.TOP)
    }

    /**
     * 加载Banner图标
     */
    fun loadBannerImageRes(url: String?, imageView: ImageView) {
        loadRound(url, imageView, ViewUtils.dp2px(10), RoundType.ALL)
    }

    /**
     * 加载竞猜热门讨论图片
     */
    fun loadQuizImageRes(url: String?, imageView: ImageView) {
        loadRound(url, imageView, ViewUtils.dp2px(6), RoundType.ALL)
    }


}