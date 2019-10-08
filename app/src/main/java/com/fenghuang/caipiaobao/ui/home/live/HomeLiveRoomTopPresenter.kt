package com.fenghuang.caipiaobao.ui.home.live

import android.widget.ImageView
import android.widget.TextView
import com.fenghuang.baselib.base.recycler.BaseRecyclerPresenter
import com.fenghuang.caipiaobao.manager.ImageManager
import com.fenghuang.caipiaobao.ui.home.live.data.HomeLiveRoomTitleBean

/**
 *  author : Peter
 *  date   : 2019/10/8 11:32
 *  desc   :
 */
class HomeLiveRoomTopPresenter : BaseRecyclerPresenter<HomeLiveRoomTopFragment>() {

    private val newResults = arrayListOf<HomeLiveRoomTitleBean>()
    override fun loadData(page: Int) {

        newResults.add(HomeLiveRoomTitleBean("https://c-ssl.duitang.com/uploads/item/201411/04/20141104214413_ivUjv.jpeg"))
        newResults.add(HomeLiveRoomTitleBean("https://c-ssl.duitang.com/uploads/item/201705/23/20170523183434_H4zT8.png"))
        newResults.add(HomeLiveRoomTitleBean("https://c-ssl.duitang.com/uploads/item/201705/26/20170526080003_un5mU.thumb.700_0.jpeg"))
        newResults.add(HomeLiveRoomTitleBean("https://c-ssl.duitang.com/uploads/item/201706/18/20170618022619_mrnQw.thumb.700_0.jpeg"))
        newResults.add(HomeLiveRoomTitleBean("https://c-ssl.duitang.com/uploads/item/201706/15/20170615103936_nmjte.thumb.700_0.jpeg"))
        newResults.add(HomeLiveRoomTitleBean("https://c-ssl.duitang.com/uploads/item/201806/09/20180609205947_xvyeg.jpg"))
        newResults.add(HomeLiveRoomTitleBean("https://c-ssl.duitang.com/uploads/item/201412/06/20141206225547_RBtTh.jpeg"))
        mView.showDatas(newResults)
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