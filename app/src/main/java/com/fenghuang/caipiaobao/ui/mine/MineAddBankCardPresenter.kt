package com.fenghuang.caipiaobao.ui.mine

import android.content.Context
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatSpinner
import com.fenghuang.baselib.base.mvp.BaseMvpPresenter
import com.fenghuang.caipiaobao.R

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/11- 14:42
 * @ Describe 添加银行卡
 *
 */

class MineAddBankCardPresenter : BaseMvpPresenter<MineAddBankCardFragment>() {


    fun initSpinnerCardList(spinner: AppCompatSpinner, context: Context) {
        val titles = arrayListOf("中国工商银行", "中国建设银行", "上海浦发银行", "招商银行")
        val adapter = ArrayAdapter<String>(context, R.layout.mine_spinner, titles)
        spinner.adapter = adapter
    }


}