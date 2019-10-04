package com.fenghuang.caipiaobao.ui.lottery

import com.fenghuang.baselib.base.mvp.BaseMvpPresenter
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.ui.lottery.data.LotteryDataBean
import com.fenghuang.caipiaobao.ui.lottery.data.LotteryOpenCodeDataBean
import com.fenghuang.caipiaobao.ui.lottery.data.LotterySettingChooseDataBean

/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/3- 15:23
 * @ Describe
 *
 */

class LotteryPresenter : BaseMvpPresenter<LotteryFragment>() {


    fun getLotteryType() {
        val newResults = arrayListOf<LotteryDataBean>()
        newResults.add(LotteryDataBean("幸运飞艇", R.mipmap.logo))
        newResults.add(LotteryDataBean("欢乐赛车", R.mipmap.logo))
        newResults.add(LotteryDataBean("福彩3D", R.mipmap.logo))
        newResults.add(LotteryDataBean("江苏快三", R.mipmap.logo))
        newResults.add(LotteryDataBean("重庆时时彩", R.mipmap.logo))
        newResults.add(LotteryDataBean("重庆时时彩", R.mipmap.logo))
        val codeResults = arrayListOf<LotteryOpenCodeDataBean>()
        codeResults.add(LotteryOpenCodeDataBean("09"))
        codeResults.add(LotteryOpenCodeDataBean("10"))
        codeResults.add(LotteryOpenCodeDataBean("01"))
        codeResults.add(LotteryOpenCodeDataBean("02"))
        codeResults.add(LotteryOpenCodeDataBean("03"))
        codeResults.add(LotteryOpenCodeDataBean("04"))
        codeResults.add(LotteryOpenCodeDataBean("05"))
        codeResults.add(LotteryOpenCodeDataBean("06"))
        codeResults.add(LotteryOpenCodeDataBean("07"))
        codeResults.add(LotteryOpenCodeDataBean("08"))
        val settingResults = arrayListOf<LotterySettingChooseDataBean>()
        settingResults.add(LotterySettingChooseDataBean("历史统计", R.mipmap.logo))
        settingResults.add(LotterySettingChooseDataBean("露珠分析", R.mipmap.logo))
        settingResults.add(LotterySettingChooseDataBean("冷热分析", R.mipmap.logo))
        settingResults.add(LotterySettingChooseDataBean("闯关计划", R.mipmap.logo))
        settingResults.add(LotterySettingChooseDataBean("今日号码", R.mipmap.logo))
        settingResults.add(LotterySettingChooseDataBean("遗漏数据", R.mipmap.logo))
        settingResults.add(LotterySettingChooseDataBean("开奖视频", R.mipmap.logo))
        settingResults.add(LotterySettingChooseDataBean("长龙统计", R.mipmap.logo))

        mView.initLotteryType(newResults)
        mView.initLotteryInfo(codeResults, settingResults)
    }

}