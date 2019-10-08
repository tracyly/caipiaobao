package com.fenghuang.caipiaobao.ui.home.live

import com.fenghuang.baselib.base.mvp.BaseMvpPresenter
import java.util.*

/**
 *  author : Peter
 *  date   : 2019/8/13 14:55
 *  desc   : 直播详情页的P层
 */
class HomeLiveDetailsPresenter : BaseMvpPresenter<HomeLiveDetailsFragment>() {

    private val mVideos = LinkedHashMap<String, String>()

    fun loadLiveInfo() {
        mVideos["标清"] = "http://192.240.127.34:1935/live/cs19.stream/playlist.m3u8"
//        mVideos["标清"] = "http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8"
//        mVideos["高清"] = "http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8"
        mVideos["高清"] = "http://192.240.127.34:1935/live/cs19.stream/playlist.m3u8"
    }

    fun getViewDeo(): LinkedHashMap<String, String> {
        return mVideos
    }

}