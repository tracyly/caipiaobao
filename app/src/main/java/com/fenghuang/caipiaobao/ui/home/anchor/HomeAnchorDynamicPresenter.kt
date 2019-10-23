package com.fenghuang.caipiaobao.ui.home.anchor

import com.fenghuang.baselib.base.recycler.BaseRecyclerPresenter
import com.fenghuang.caipiaobao.ui.home.data.HomeLiveAnchorDynamicBean

/**
 *  author : Peter
 *  date   : 2019/10/19 17:57
 *  desc   :
 */
class HomeAnchorDynamicPresenter : BaseRecyclerPresenter<HomeAnchorDynamicFragment>() {

    private val newResults = arrayListOf<HomeLiveAnchorDynamicBean>()
    override fun loadData(page: Int) {

        newResults.add(HomeLiveAnchorDynamicBean("2019.1213", 1, arrayListOf("http://154.206.43.213:18308/forum/avatar3.jpg", "http://154.206.43.213:18308/forum/avatar3.jpg"), 11, "指法大仙", "测试动态数据1", 1))
        newResults.add(HomeLiveAnchorDynamicBean("2019.1213", 2, arrayListOf("http://154.206.43.213:18308/forum/avatar3.jpg"), 10, "指法大仙", "测试动态数据2", 0))
        newResults.add(HomeLiveAnchorDynamicBean("2019.1213", 3, arrayListOf("http://154.206.43.213:18308/forum/avatar4.jpg"), 1, "指法大仙", "测试动态数据4", 0))
        newResults.add(HomeLiveAnchorDynamicBean("2019.1213", 4, arrayListOf("http://154.206.43.213:18308/forum/avatar4.jpg", "http://154.206.43.213:18308/forum/avatar4.jpg"), 12, "指法大仙", "测试动态数据3", 1))
        mView.addDatas(newResults)
    }
}