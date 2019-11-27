package com.fenghuang.caipiaobao.ui.mine

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fenghuang.baselib.base.mvp.BaseMvpPresenter
import com.fenghuang.caipiaobao.constant.IntentConstant.MINE_USER_BAMK_LIST
import com.fenghuang.caipiaobao.ui.mine.data.MineUserBankList
import com.fenghuang.caipiaobao.utils.JsonUtils

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/13- 13:29
 * @ Describe
 *
 */

class MineUserBankCardListPresenter : BaseMvpPresenter<MineUserBankCardList>() {


    fun initList(context: Context, recyclerView: RecyclerView) {
        val mineItemAdapter = MineUserBankCardListAdapter(context)
        mineItemAdapter.addAll(JsonUtils.fromJson(mView.arguments?.getString(MINE_USER_BAMK_LIST).toString(), Array<MineUserBankList>::class.java))
        recyclerView.adapter = mineItemAdapter
        val value = object : LinearLayoutManager(context) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        recyclerView.layoutManager = value
    }
}


