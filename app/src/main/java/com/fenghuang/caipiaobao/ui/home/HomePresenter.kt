package com.fenghuang.caipiaobao.ui.home

import android.annotation.SuppressLint
import android.util.Log
import com.fenghuang.baselib.base.mvp.BaseMvpPresenter
import com.fenghuang.caipiaobao.ui.home.data.HomeApi
import com.pingerx.rxnetgo.rxcache.CacheMode

/**
 *  author : Peter
 *  date   : 2019/9/5 13:40
 *  desc   : 首页P层
 */
@Suppress("UNCHECKED_CAST")
class HomePresenter : BaseMvpPresenter<HomeFragmentNew>() {

    private var sb: StringBuffer = StringBuffer()
    private var mCount = 0

    fun loadCache() {
        getBannerList(CacheMode.ONLY_CACHE)
    }

    fun loadData() {
        getBannerList(CacheMode.FIRST_REMOTE_THEN_CACHE)
    }

    private fun getBannerList(cacheMode: CacheMode) {
        loadBannerListInfo(cacheMode)
    }

    private fun loadBannerListInfo(cacheMode: CacheMode) {
        mCount = 0
        getNoticeInfo(cacheMode)
        getGameListInfo(cacheMode)
        getLiveIsListInfo(cacheMode)
        HomeApi.getHomeBannerResult(cacheMode) {
            onSuccess {
                if (mView.isActive()) mView.updateBanner(it)
            }
            onFailed {
                mView.showPageError()
            }
        }
    }

    private fun getNoticeInfo(cacheMode: CacheMode) {
        HomeApi.getHomeNoticeResult(cacheMode) {
            onSuccess {
                it.forEach { i ->
                    val s = i.title + ":" + i.content + "    "
                    sb.append(s)
                }
                mView.updateNotice(sb.toString())

            }

            onFailed {

            }
        }
    }

    /**
     *  获取游戏榜
     */
    private fun getGameListInfo(cacheMode: CacheMode) {
        HomeApi.getHomeGameListResult(cacheMode) {
            onSuccess {
                mView.updateGameList(it)

            }

            onFailed {
                Log.i("getGameListInfo", "失败")
            }
        }
    }

    /**
     * 热门直播
     */

    @SuppressLint("CheckResult")
    private fun getLiveIsListInfo(cacheMode: CacheMode) {
//        Flowable.concat(HomeApi.getHomeHotLiveListResult(cacheMode),
//                HomeApi.getHomeLivePopResult(cacheMode),
//                HomeApi.getHomeExpertListResult(cacheMode),
//                HomeApi.getHomeExpertRecommendResult(cacheMode)).subscribe {
//            mView.hidePageLoading()
//            when (mCount) {
//                0 -> {
//                    mView.updateHotLive(it as List<HomeLiveListResponse>)
//                }
//                1 -> {
//                    mView.updateLivePop(it as List<HomeLivePopResponse>)
//                }
//                2 -> {
//                    mView.updateExpertLive(it as List<HomeLiveListResponse>)
//                }
//                3 -> {
//                    mView.updateExpertRecommend(it as List<HomeExpertRecommendResponse>)
//                }
//            }
//            mCount++
//        }
        HomeApi.getHomeHotLiveListResult(cacheMode) {
            onSuccess {
                if (mView.isActive() && it.isNotEmpty()) {
                    mView.updateHotLive(it)
                }

            }
        }
        HomeApi.getHomeLivePopResult(cacheMode) {
            onSuccess {
                if (mView.isActive() && it.isNotEmpty()) {
                    mView.updateLivePop(it)
                }
            }
        }
        HomeApi.getHomeExpertListResult(cacheMode) {
            onSuccess {
                if (mView.isActive() && it.isNotEmpty()) {
                    mView.updateExpertLive(it)
                }

            }
        }
        HomeApi.getHomeExpertRecommendResult(cacheMode) {
            onSuccess {
                if (mView.isActive() && it.isNotEmpty()) {
                    mView.updateExpertRecommend(it)
                }
            }
        }
    }
}