package com.fenghuang.caipiaobao.ui.quiz

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fenghuang.baselib.base.recycler.BaseRecyclerAdapter
import com.fenghuang.baselib.base.recycler.BaseViewHolder
import com.fenghuang.baselib.base.recycler.decorate.GridItemSpaceDecoration
import com.fenghuang.baselib.utils.TimeUtils
import com.fenghuang.baselib.utils.ViewUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.manager.ImageManager
import com.fenghuang.caipiaobao.ui.quiz.checkphoto.CheckPhotoImgFragment
import com.fenghuang.caipiaobao.ui.quiz.data.QuizResponse
import com.fenghuang.caipiaobao.utils.GobalExceptionDialog.ExceptionDialog
import com.fenghuang.caipiaobao.utils.UserInfoSp
import java.util.*

/**
 *
 * @ Author  QinTian
 * @ Date  2019/12/10- 17:14
 * @ Describe
 *
 */

class QuizAdapter(context: Context, val mPresenter: QuizPresenter) : BaseRecyclerAdapter<QuizResponse>(context) {


    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<QuizResponse> {
        return QuiztHolder(parent)
    }


    inner class QuiztHolder(parent: ViewGroup) : BaseViewHolder<QuizResponse>(getContext(), parent, R.layout.holder_quiz_item) {
        override fun onBindData(data: QuizResponse) {
            if (data.created != -100000000000) {
                setText(R.id.tvQuizDate, TimeUtils.formatFriendly(data.created))
            } else setText(R.id.tvQuizDate, "1小时前")
            setText(R.id.tvQuizTitle, data.title)
            setText(R.id.tvQuizName, data.nickname)
            setText(R.id.tvQuizGameName, data.lottery_name)
            setText(R.id.tvQuizPeriod, data.issue)
            setText(R.id.tvQuizLike, data.like.toString())
            ImageManager.loadRoundLogo(data.avatar, findView(R.id.ivQuizLogo))
            setOnClick(R.id.quizLikeLayoutItem)
            val tvQuizLike = findView<TextView>(R.id.tvQuizLike)
            if (data.is_like == 1) {
                setTextColor(tvQuizLike, getColor(R.color.color_FF513E))
                setImageResource(findView(R.id.ivQuizLike), R.mipmap.ic_quiz_like_select)
            } else {
                setTextColor(tvQuizLike, getColor(R.color.color_bfc8d9))
                setImageResource(findView(R.id.ivQuizLike), R.mipmap.ic_quiz_like_normal)
            }
            val recyclerView = findView<RecyclerView>(R.id.recyclerViewQuiz)
            val adapter = QuizHolderImageAdapter(getContext()!!)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = GridLayoutManager(getContext()!!, 3)
            if (recyclerView.itemDecorationCount == 0) {
                recyclerView.addItemDecoration(GridItemSpaceDecoration(3, itemSpace = ViewUtils.dp2px(6), startAndEndSpace = ViewUtils.dp2px(6)))
            }
            adapter.addAll(data.images)

            findView<LinearLayout>(R.id.quizLikeLayoutItem).setOnClickListener {
                if (UserInfoSp.getIsLogin()) {
                    if (data.is_like == 0) {
                        data.is_like = 1
                        setTextColor(tvQuizLike, getColor(R.color.color_FF513E))
                        setImageResource(findView(R.id.ivQuizLike), R.mipmap.ic_quiz_like_select)
                        setText(R.id.tvQuizLike, (tvQuizLike.text.toString().toInt() + 1).toString())
                    } else {
                        data.is_like = 0
                        setText(R.id.tvQuizLike, (tvQuizLike.text.toString().toInt() - 1).toString())
                        setTextColor(tvQuizLike, getColor(R.color.color_bfc8d9))
                        setImageResource(findView(R.id.ivQuizLike), R.mipmap.ic_quiz_like_normal)
                    }
                    mPresenter.postQuizLike(getData()!!, getData()?.id!!, getDataPosition())
                } else ExceptionDialog.showExpireDialog(getContext()!!)
            }
        }
    }


    inner class QuizHolderImageAdapter(context: Context) : BaseRecyclerAdapter<String>(context) {
        override fun onCreateHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<String> {
            return QuizHolderImageHolder(parent)
        }

        inner class QuizHolderImageHolder(parent: ViewGroup) : BaseViewHolder<String>(getContext(), parent, R.layout.holder_quiz_image_item) {
            override fun onBindData(data: String) {
                if (data != "-1") {
                    ImageManager.loadQuizImageRes(data, findView(R.id.ivQuizImage))
                } else findView<ImageView>(R.id.ivQuizImage).setImageResource(R.mipmap.ic_placeholder)
                setOnClick(R.id.ivQuizImage)
            }

            override fun onClick(id: Int) {
                if (id == R.id.ivQuizImage) {
                    startFragment(CheckPhotoImgFragment.newInstance(getAllData() as ArrayList<String>, getDataPosition()))
                }
            }
        }
    }


}