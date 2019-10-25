package com.fenghuang.caipiaobao.ui.quiz

import android.content.Context
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fenghuang.baselib.base.recycler.BaseRecyclerAdapter
import com.fenghuang.baselib.base.recycler.BaseViewHolder
import com.fenghuang.baselib.base.recycler.decorate.GridItemSpaceDecoration
import com.fenghuang.baselib.base.recycler.multitype.MultiTypeAdapter
import com.fenghuang.baselib.base.recycler.multitype.MultiTypeViewHolder
import com.fenghuang.baselib.utils.ViewUtils
import com.fenghuang.caipiaobao.R
import com.fenghuang.caipiaobao.manager.ImageManager
import com.fenghuang.caipiaobao.ui.quiz.data.QuizResponse
import com.fenghuang.caipiaobao.ui.quiz.data.QuizTitleBean
import com.fenghuang.caipiaobao.ui.quiz.data.QuizTopImageBean
import java.util.*

/**
 *  author : Peter
 *  date   : 2019/10/17 20:43
 *  desc   : java类作用描述
 */
class QuizHolder : MultiTypeViewHolder<QuizResponse, QuizHolder.ViewHolder>() {

    internal var mMultiTypeAdapter: MultiTypeAdapter? = null
    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(parent)

    inner class ViewHolder(parent: ViewGroup) : BaseViewHolder<QuizResponse>(context, parent, R.layout.holder_quiz_item) {

        private var adapter: QuizHolderImageAdapter? = null
        override fun onBindView(context: Context?) {
            mMultiTypeAdapter = getAdapter()
            context?.apply {
                val recyclerView = findView<RecyclerView>(R.id.recyclerView)
                adapter = QuizHolderImageAdapter(context)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = GridLayoutManager(context, 3)
                recyclerView.addItemDecoration(GridItemSpaceDecoration(3, itemSpace = ViewUtils.dp2px(6), startAndEndSpace = ViewUtils.dp2px(6)))
            }
        }

        override fun onBindData(data: QuizResponse) {
            setText(R.id.tvQuizDate, data.created)
            setText(R.id.tvQuizTitle, data.title)
            setText(R.id.tvQuizName, data.nickname)
            setText(R.id.tvQuizGameName, data.lottery_name)
            setText(R.id.tvQuizPeriod, data.issue)
            setText(R.id.tvQuizLike, data.like.toString())
            setOnClick(R.id.quizLikeLayout)
            adapter?.setData(data.images)
            val findView = findView<TextView>(R.id.tvQuizLike)
            if (data.is_like == 1) {
                setTextColor(findView, getColor(R.color.color_FF513E))
                setImageResource(findView(R.id.ivQuizLike), R.mipmap.ic_quiz_like_select)
            } else {
                setTextColor(findView, getColor(R.color.color_bfc8d9))
                setImageResource(findView(R.id.ivQuizLike), R.mipmap.ic_quiz_like_normal)
            }
        }

        override fun onClick(id: Int) {
            if (id == R.id.quizLikeLayout) {
                mListener?.invoke(getData()?.id!!, getData()!!)

            }
        }
    }

    private var mListener: ((id: Int, data: QuizResponse) -> Unit)? = null
    fun setOnSendLikeClickListener(listener: (id: Int, data: QuizResponse) -> Unit) {
        mListener = listener
    }

    inner class QuizHolderImageAdapter(context: Context) : BaseRecyclerAdapter<String>(context) {

        override fun onCreateHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<String> {
            return QuizHolderImageHolder(parent)
        }

        inner class QuizHolderImageHolder(parent: ViewGroup) : BaseViewHolder<String>(getContext(), parent, R.layout.holder_quiz_image_item) {
            override fun onBindData(data: String) {
                ImageManager.loadQuizImageRes(data, findView(R.id.ivQuizImage))
                setOnClick(R.id.ivQuizImage)
            }

            override fun onClick(id: Int) {
                if (id == R.id.ivQuizImage) {
                    startFragment(QuizCheckImgFragment.newInstance(getAllData() as ArrayList<String>, getDataPosition()))
                }
            }
        }
    }
}

class QuizTitleHolder : MultiTypeViewHolder<QuizTitleBean, QuizTitleHolder.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(parent)

    inner class ViewHolder(parent: ViewGroup) : BaseViewHolder<QuizTitleBean>(context, parent, R.layout.holder_quiz_title) {
        override fun onBindData(data: QuizTitleBean) {
            setText(R.id.tvQuizListTitle, data.title)
        }
    }
}

class QuizTopImageHolder : MultiTypeViewHolder<QuizTopImageBean, QuizTopImageHolder.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(parent)

    inner class ViewHolder(parent: ViewGroup) : BaseViewHolder<QuizTopImageBean>(context, parent, R.layout.holder_quiz_top_image) {
        override fun onBindData(data: QuizTopImageBean) {
            setImageResource(findView(R.id.ivQuizTop), data.logo)
        }
    }
}