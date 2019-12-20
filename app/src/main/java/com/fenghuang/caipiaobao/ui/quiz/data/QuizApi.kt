package com.fenghuang.caipiaobao.ui.quiz.data

import com.fenghuang.caipiaobao.data.api.ApiSubscriber
import com.fenghuang.caipiaobao.data.api.BaseApi
import com.fenghuang.caipiaobao.data.api.EmptySubscriber
import com.fenghuang.caipiaobao.utils.UserInfoSp

/**
 *  author : Peter
 *  date   : 2019/10/18 12:59
 *  desc   : 竞猜模块的所有接口
 */
object QuizApi : BaseApi {

    private const val QUIZ_ARTICLE = "/article/index"
    private const val QUIZ_ARTICLE_LIKE = "/article/like"
    private const val QUIZ_BANNER = "/article/banner"


    /**
     * 获取竞猜列表
     */
    fun getQuizArticleListResult(limit: Int, page: Int, function: ApiSubscriber<List<QuizResponse>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<QuizResponse>>() {}
        subscriber.function()
        getApiOther()
                .get<List<QuizResponse>>("/forum/" + QUIZ_ARTICLE)
                .headers("Authorization", UserInfoSp.getTokenWithBearer())
                .params("user_id", UserInfoSp.getUserId())
                .params("limit", limit)
                .params("page", page)
                .subscribe(subscriber)
    }

    /**
     * 点赞
     */
    fun getQuizArticleLikeResult(articleId: Int, userId: Int, function: EmptySubscriber.() -> Unit) {
        val subscriber = EmptySubscriber()
        subscriber.function()
        getApiOther()
                .post<String>("/forum/" + QUIZ_ARTICLE_LIKE)
                .headers("Authorization", UserInfoSp.getTokenWithBearer())
                .params("article_id", articleId)
                .params("user_id", userId)
                .subscribe(subscriber)
    }


    /**
     * banner
     */
    fun getQuizTopBanner(function: ApiSubscriber<List<QuizTopImageBean>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<QuizTopImageBean>>() {}
        subscriber.function()
        getApiOther()
                .get<List<QuizTopImageBean>>("/forum/" + QUIZ_BANNER)
                .headers("Authorization", UserInfoSp.getTokenWithBearer())
                .subscribe(subscriber)
    }
}