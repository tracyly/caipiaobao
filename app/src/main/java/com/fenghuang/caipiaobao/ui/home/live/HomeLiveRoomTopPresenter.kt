package com.fenghuang.caipiaobao.ui.home.live

import android.widget.ImageView
import android.widget.TextView
import com.fenghuang.baselib.base.recycler.BaseRecyclerPresenter
import com.fenghuang.baselib.utils.ToastUtils.showToast
import com.fenghuang.caipiaobao.manager.ImageManager
import com.fenghuang.caipiaobao.ui.home.data.HomeApi

/**
 *  author : Peter
 *  date   : 2019/10/8 11:32
 *  desc   :
 */
class HomeLiveRoomTopPresenter(private val anchorId: Int) : BaseRecyclerPresenter<HomeLiveRoomTopFragment>() {

    override fun loadData(page: Int) {
        mView.hidePageLoading()
        HomeApi.getHomeLiveChatRewardResult(anchorId) {
            onSuccess {
                mView.showDatas(it)
            }
            onFailed {
                showToast("获取打赏榜失败")
            }
        }
    }

    /**
     * 获取主播信息：头像 id 在线人数
     */
    fun getAnchorInfo(image: ImageView, userName: TextView, people: TextView) {
        ImageManager.loadRoundLogo("https://c-ssl.duitang.com/uploads/item/201704/03/20170403231614_5fcmC.jpeg", image)
        userName.text = "美丽可爱的小主播"
        people.text = "12458"
    }
}