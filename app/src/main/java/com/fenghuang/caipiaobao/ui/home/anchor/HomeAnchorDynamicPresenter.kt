package com.fenghuang.caipiaobao.ui.home.anchor

import com.fenghuang.baselib.base.recycler.BaseRecyclerPresenter
import com.fenghuang.baselib.utils.SpUtils
import com.fenghuang.caipiaobao.constant.UserConstant
import com.fenghuang.caipiaobao.ui.home.data.HomeApi

/**
 *  author : Peter
 *  date   : 2019/10/19 17:57
 *  desc   :
 */
class HomeAnchorDynamicPresenter(val anchorId: Int) : BaseRecyclerPresenter<HomeAnchorDynamicFragment>() {

    override fun loadData(page: Int) {

        HomeApi.getHomeLiveAnchorDynammicInfo(SpUtils.getInt(UserConstant.USER_ID), 100010) {
            onSuccess {
                if (it.isNotEmpty()) {
                    mView.showDatas(it)
                } else mView.showPageEmpty()
            }

            onFailed {
                mView.showPageError(it.getMsg())
            }
        }

//        newResults.add(HomeLiveAnchorDynamicBean("2019.1213", 1, arrayListOf("http://154.206.43.213:18308/forum/avatar3.jpg", "http://154.206.43.213:18308/forum/avatar3.jpg"), 11, "指法大仙", "测试动态数据1", 1))
//        newResults.add(HomeLiveAnchorDynamicBean("2019.1213", 2, arrayListOf("http://154.206.43.213:18308/forum/avatar3.jpg"), 10, "指法大仙", "测试动态数据2", 0))
//        newResults.add(HomeLiveAnchorDynamicBean("2019.1213", 3, arrayListOf("http://154.206.43.213:18308/forum/avatar4.jpg"), 1, "指法大仙", "测试动态数据4", 0))
//        newResults.add(HomeLiveAnchorDynamicBean("2019.1213", 4, arrayListOf("http://154.206.43.213:18308/forum/avatar4.jpg", "http://154.206.43.213:18308/forum/avatar4.jpg"), 12, "指法大仙", "测试动态数据3", 1))
//        mView.addDatas(newResults)
    }
}