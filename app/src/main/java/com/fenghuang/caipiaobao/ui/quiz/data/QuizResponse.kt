package com.fenghuang.caipiaobao.ui.quiz.data

// 竞猜
data class QuizResponse(var created: String,
                        var id: Int,
                        var images: List<String>,
                        var issue: String,
                        var like: Int,
                        var lottery_name: String,
                        var nickname: String,
                        var title: String)

data class QuizTitleBean(var title: String)
data class QuizTopImageBean(var logo: Int)