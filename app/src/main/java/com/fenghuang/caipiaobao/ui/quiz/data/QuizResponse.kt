package com.fenghuang.caipiaobao.ui.quiz.data

// 竞猜
data class QuizResponse(var created: Long,
                        var id: Int,
                        var images: ArrayList<String>,
                        var issue: String,
                        var like: Int,
                        var avatar: String,
                        var lottery_name: String,
                        var nickname: String,
                        var title: String,
                        var is_like: Int)

data class QuizTitleBean(var title: String)
data class QuizTopImageBean(var img: String)