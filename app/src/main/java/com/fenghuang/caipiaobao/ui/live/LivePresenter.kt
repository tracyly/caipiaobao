package com.fenghuang.caipiaobao.ui.live

import com.fenghuang.baselib.base.recycler.BaseRecyclerPresenter
import com.fenghuang.caipiaobao.ui.live.data.LiveDataBean
import com.fenghuang.caipiaobao.ui.live.data.LiveTitleDataBean

class LivePresenter : BaseRecyclerPresenter<LiveFragment>() {

    private val newResults = arrayListOf<LiveTitleDataBean>()
    var url = "https://c-ssl.duitang.com/uploads/item/201708/26/20170826165627_fcxVE.jpeg"
    override fun loadData(page: Int) {
        var e = arrayListOf<LiveDataBean>()
        for (i in 0..3) {
            e.add(LiveDataBean(url, "我是主播$i", "打你个大西瓜"))
        }
        newResults.add(LiveTitleDataBean("", url, e))

        var e1 = arrayListOf<LiveDataBean>()
        for (i in 0..5) {
            e1.add(LiveDataBean(url, "我是主播$i", "打你个大西瓜"))
        }
        newResults.add(LiveTitleDataBean("美女主播", "", e1))
//        newResults.add(LiveDataBean(url, "张三", "1236455"))
//        newResults.add(LiveDataBean(url, "李四", "1236455"))
//        newResults.add(LiveDataBean(url, "王五", "1236455"))
//        newResults.add(LiveDataBean(url, "赵留", "1236455"))
//        newResults.add(LiveDataBean(url, "关羽", "1236455"))
//        newResults.add(LiveDataBean(url, "张飞", "1236455"))
//        newResults.add(LiveDataBean(url, "曹操", "1236455"))
//        newResults.add(LiveDataBean(url, "刘备", "1236455"))
//        newResults.add(LiveDataBean(url, "吕布", "1236455"))
//        newResults.add(LiveDataBean(url, "张辽", "1236455"))
//        newResults.add(LiveDataBean(url, "赵云", "1236455"))
//        newResults.add(LiveDataBean(url, "马超", "1236455"))
//        newResults.add(LiveDataBean(url, "貂蝉", "1236455"))
//        newResults.add(LiveDataBean(url, "王昭君", "1236455"))
//        newResults.add(LiveDataBean(url, "安其拉", "1236455"))
        mView.showDatas(newResults)
    }


}