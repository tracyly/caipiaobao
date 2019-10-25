package com.fenghuang.caipiaobao.ui.widget.CheckPhoto

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.PagerAdapter
import com.fenghuang.caipiaobao.manager.ImageManager
import com.github.chrisbanes.photoview.PhotoView
import java.util.*


/**
 *
 * @ Author  QinTian
 * @ Date  2019/10/24- 15:08
 * @ Describe
 *
 */

class CheckPhotoImageAdapter(private val fragment: Fragment, private val imageUrls: ArrayList<String>, private val clickListener: View.OnClickListener) : PagerAdapter() {


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val url = imageUrls[position]
        val photoView = PhotoView(fragment.context)
        ImageManager.loadImage(url, photoView)
        container.addView(photoView)
        photoView.setOnClickListener(clickListener)
        return photoView
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getCount(): Int {
        return imageUrls.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }
}