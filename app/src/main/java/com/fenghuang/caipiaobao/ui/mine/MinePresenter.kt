package com.fenghuang.caipiaobao.ui.mine

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fenghuang.baselib.base.mvp.BaseMvpPresenter
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.mine.data.MineDataBean

class MinePresenter : BaseMvpPresenter<MineFragment>() {

    private val newResults = arrayListOf<MineDataBean>()


    fun initList(context: Context, listItem: RecyclerView) {
        newResults.add(MineDataBean("个人资料", R.mipmap.ic_mine_preson))
        newResults.add(MineDataBean("我的关注", R.mipmap.ic_mine_attention))
//        newResults.add(MineDataBean("投注记录", R.mipmap.ic_mine_bet_record))
        newResults.add(MineDataBean("打赏记录", R.mipmap.ic_mine_ds))
//        newResults.add(MineDataBean("玩法说明", R.mipmap.ic_mine_play_info))
//        newResults.add(MineDataBean("代理合作", R.mipmap.ic_mine_dl))
//        newResults.add(MineDataBean("新手教程", R.mipmap.ic_mine_jc))
        newResults.add(MineDataBean("意见反馈", R.mipmap.ic_mine_advice))
        newResults.add(MineDataBean("联系客服", R.mipmap.ic_mine_contact))
        newResults.add(MineDataBean("设置", R.mipmap.ic_mine_setting))
        val mineItemAdapter = MineAdapter(context)
        mineItemAdapter.addAll(newResults)
        listItem.adapter = mineItemAdapter
        val value = object : LinearLayoutManager(context) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        listItem.layoutManager = value
    }
}