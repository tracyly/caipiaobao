package com.fenghuang.caipiaobao.ui.home.anchor

import com.fenghuang.baselib.base.recycler.BaseRecyclerPresenter
import com.fenghuang.baselib.utils.SpUtils
import com.fenghuang.baselib.utils.ToastUtils
import com.fenghuang.caipiaobao.constant.UserConstant
import com.fenghuang.caipiaobao.ui.home.data.HomeApi

/**
 *  author : Peter
 *  date   : 2019/10/19 17:57
 *  desc   :
 */
class HomeAnchorDynamicPresenter(private val mAnchorId: Int) : BaseRecyclerPresenter<HomeAnchorDynamicFragment>() {

    override fun loadData(page: Int) {

        HomeApi.getHomeLiveAnchorDynamicInfo(SpUtils.getInt(UserConstant.USER_ID), mAnchorId, page) {
            onSuccess {
                if (it.isNotEmpty()) {
                    mView.showDatas(it)
                } else mView.showPageEmpty()
            }

            onFailed {
                mView.showPageError(it.getMsg())
            }
        }
    }

    fun getAnchorDynamicLike(anchorId: Int, position: Int) {
        HomeApi.getAnchorDynamicLike(SpUtils.getInt(UserConstant.USER_ID), anchorId) {
            onSuccess {
                mView.notifyChanged(position)
            }

            onFailed {
                ToastUtils.showError(it.getMsg())
            }
        }
    }
}