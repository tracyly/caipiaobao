package com.fenghuang.caipiaobao.ui.mine

import com.fenghuang.baselib.base.recycler.BaseRecyclerPresenter
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.mine.data.MineDataBean

class MineItemPresenter(private var isLogin: Boolean) : BaseRecyclerPresenter<MineItemFragment>() {

    private val newResults = arrayListOf<MineDataBean>()
    override fun loadData(page: Int) {
        newResults.add(MineDataBean("个人资料", R.mipmap.ic_mine_preson))
        newResults.add(MineDataBean("投注记录", R.mipmap.ic_mine_bet_record))
        newResults.add(MineDataBean("打赏记录", R.mipmap.ic_mine_ds))
        newResults.add(MineDataBean("玩法说明", R.mipmap.ic_mine_play_info))
        newResults.add(MineDataBean("代理合作", R.mipmap.ic_mine_dl))
        newResults.add(MineDataBean("新手教程", R.mipmap.ic_mine_jc))
        newResults.add(MineDataBean("意见反馈", R.mipmap.ic_mine_advice))
        newResults.add(MineDataBean("联系客服", R.mipmap.ic_mine_contact))
        mView.showDatas(newResults)

    }
}