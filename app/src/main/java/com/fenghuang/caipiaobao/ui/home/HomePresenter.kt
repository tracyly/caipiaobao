package com.fenghuang.caipiaobao.ui.home

import android.annotation.SuppressLint
import android.util.Log
import com.fenghuang.baselib.base.mvp.BaseMvpPresenter
import com.fenghuang.caipiaobao.manager.ImageManager
import com.fenghuang.caipiaobao.ui.home.data.HomeApi
import com.fenghuang.caipiaobao.ui.home.data.HomeExpertRecommendResponse
import com.fenghuang.caipiaobao.ui.home.data.HomeLiveListResponse
import com.pingerx.rxnetgo.rxcache.CacheMode
import io.reactivex.Flowable
import kotlinx.android.synthetic.main.fragment_home_new.*


/**
 *  author : Peter
 *  date   : 2019/9/5 13:40
 *  desc   : 首页P层
 */
@Suppress("UNCHECKED_CAST")
class HomePresenter : BaseMvpPresenter<HomeFragmentNew>() {

    private var sb: StringBuffer = StringBuffer()
    private var mCount = 0

//    fun loadCache() {
////        if (NetWorkUtils.isNetworkNotConnected()) {
////            mView.setVisible(mView.errorContainer)
////        }
////        else {
//        mView.setGone(mView.errorContainer)
//        getBannerList(CacheMode.NONE)
////        }
//    }

    fun loadData() {
        //直播预告
        liveFaber(true)
        getBannerList(CacheMode.NONE)
    }

    private fun getBannerList(cacheMode: CacheMode) {
        loadBannerListInfo(cacheMode)
    }

    private fun loadBannerListInfo(cacheMode: CacheMode) {
        mCount = 0
        HomeApi.getHomeBannerResult(cacheMode) {
            onSuccess {
                if (mView.isActive()) mView.updateBanner(it)
                getNoticeInfo(cacheMode)
            }
            onFailed {
                //                                mView.showPageError()
            }
        }
    }

    private fun getNoticeInfo(cacheMode: CacheMode) {
        HomeApi.getHomeNoticeResult(cacheMode) {
            onSuccess {
                if (mView.isActive() && it.isNotEmpty()) {
                    it.forEachIndexed { index, value ->
                        val s = (index + 1).toString() + "." + value.content + "        "
                        sb.append(s)
                    }
                    mView.updateNotice(sb.toString())
                }
                getGameListInfo(cacheMode)

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
                if (mView.isActive() && it.isNotEmpty()) {
                    mView.updateGameList(it)
                }
                getLiveIsListInfo(cacheMode)
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
        Flowable.concat(HomeApi.getHomeHotLiveListResult(6, 0, cacheMode),
                HomeApi.getHomeExpertListResult(6, 0, cacheMode),
                HomeApi.getHomeExpertRecommendResult(cacheMode)).subscribe {
            mView.hidePageLoading()
            when (mCount) {
                0 -> {
                    mView.updateHotLive(it as List<HomeLiveListResponse>)
                }
                1 -> {
                    mView.updateExpertLive(it as List<HomeLiveListResponse>)
                }
                2 -> {
                    mView.updateExpertRecommend(it as List<HomeExpertRecommendResponse>)
                }

            }
            mCount++
        }
//
//        HomeApi.getHomeHotLiveListResult(6, 0, cacheMode) {
//            onSuccess {
//                if (mView.isActive() && it.isNotEmpty()) {
//                    mView.updateHotLive(it)
//                }
//            }
//        }
//


//        HomeApi.getHomeExpertListResult(6, 0, cacheMode) {
//            onSuccess {
//                if (mView.isActive() && it.isNotEmpty()) {
//                    mView.updateExpertLive(it)
//                }
//
//            }
//        }
//        HomeApi.getHomeExpertRecommendResult(cacheMode) {
//            onSuccess {
//                if (mView.isActive() && it.isNotEmpty()) {
//                    mView.updateExpertRecommend(it)
//                }
//            }
//        }
    }

    /**
     * 直播预告
     */
    fun liveFaber(isUpDate: Boolean) {
        //直播预告
        HomeApi.getHomeLivePopResult(CacheMode.NONE) {
            onSuccess {
                if (mView.isActive() && it.isNotEmpty()) {
                    if (isUpDate) {
                        if (it.isNotEmpty()) mView.updateLivePop(it)
                    }
                }
                HomeApi.getAdUrl {
                    onSuccess {
                        if (mView.isActive() && it.isNotEmpty()) {
                            ImageManager.loadBannerImageRes(it[0].image_url.replace("\\", "/"), mView.imgHomeAd)
                        }
                    }
                }
            }
        }
    }


}