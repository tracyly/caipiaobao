package com.fenghuang.caipiaobao.ui.home.live

import com.fenghuang.baselib.base.mvp.BaseMvpPresenter
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.caipiaobao.ui.home.data.HomeApi
import java.util.*

/**
 *  author : Peter
 *  date   : 2019/8/13 14:55
 *  desc   : 直播详情页的P层
 */
class HomeLiveDetailsPresenter : BaseMvpPresenter<HomeLiveDetailsFragment>() {

    private val mVideos = LinkedHashMap<String, String>()

    fun loadLiveInfo(anchorId: Int, userId: Int) {
        HomeApi.getHomeLiveRoomResult(anchorId, userId) {
            onSuccess {
                it.liveInfo.forEachIndexed { _, homeLiveRoomListBean ->
                    if (homeLiveRoomListBean.type == "RTMP") {
                        mVideos["标清"] = homeLiveRoomListBean.liveUrl.originPullUrl
                    }
                }
                mView.setLogoInfo(it)
                mView.startLive(mVideos)
            }
            onFailed {
                ToastUtils.showToast("主播数据异常，请稍后重试")
            }
        }
    }
}