package com.fenghuang.caipiaobao.ui.mine

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fenghuang.baselib.base.mvp.BaseMvpPresenter
import com.fenghuang.caipiaobao.ui.mine.data.MineBankCardBean

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/13- 13:29
 * @ Describe
 *
 */

class MineBankCardListPresenter : BaseMvpPresenter<MineBankCardList>() {

    private val newResults = arrayListOf<MineBankCardBean>()


    fun initList(context: Context, recyclerView: RecyclerView) {
        newResults.add(MineBankCardBean("1", "31065464013163", "中国工商银行"))
        newResults.add(MineBankCardBean("2", "3406354063410341", "中国建设银行"))
        newResults.add(MineBankCardBean("3", "0324034646046046046", "上海浦发银行"))
        newResults.add(MineBankCardBean("4", "014604650460465406", "招商银行"))
        newResults.add(MineBankCardBean("1", "0345304054064640", "中国工商银行"))
        newResults.add(MineBankCardBean("1", "31065464013163", "中国工商银行"))
        newResults.add(MineBankCardBean("2", "3406354063410341", "中国建设银行"))
        newResults.add(MineBankCardBean("3", "0324034646046046046", "上海浦发银行"))
        val mineItemAdapter = MineBankCardListAdapter(context)
        mineItemAdapter.addAll(newResults)
        recyclerView.adapter = mineItemAdapter
        val value = object : LinearLayoutManager(context) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        recyclerView.layoutManager = value
    }
}


