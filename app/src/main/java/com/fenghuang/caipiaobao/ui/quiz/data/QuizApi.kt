package com.fenghuang.caipiaobao.ui.quiz.data

import com.fenghuang.caipiaobao.data.api.ApiSubscriber
import com.fenghuang.caipiaobao.data.api.BaseApi
import com.fenghuang.caipiaobao.data.api.EmptySubscriber

/**
 *  author : Peter
 *  date   : 2019/10/18 12:59
 *  desc   : 竞猜模块的所有接口
 */
object QuizApi : BaseApi {

    private const val QUIZ_ARTICLE = "/article/index"
    private const val QUIZ_ARTICLE_LIKE = "/article/like"

    /**
     * 获取竞猜列表
     */
    fun getQuizArticleListResult(limit: Int, page: Int, function: ApiSubscriber<List<QuizResponse>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<QuizResponse>>() {}
        subscriber.function()
        QuizApi.getAipQuizUrl()
                .get<List<QuizResponse>>(QUIZ_ARTICLE)
//                .headers("Authorization", "Bearer " + SpUtils.getString(UserConstant.TOKEN))
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
        QuizApi.getAipQuizUrl()
                .post<String>(QUIZ_ARTICLE_LIKE)
//                .headers("Authorization", "Bearer " + SpUtils.getString(UserConstant.TOKEN))
                .params("article_id", articleId)
                .params("user_id", userId)
                .subscribe(subscriber)
    }
}