package com.fenghuang.caipiaobao.widget.grildscroll;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

/**
 * @ Author  QinTian
 * @ Date  2019/11/25- 18:25
 * @ Describe
 */
public class ViewPagerAdapter extends PagerAdapter {
    private List<View> mViewList;
    private Context mContext;

    public ViewPagerAdapter(List<View> mViewList, Context context) {
        this.mViewList = mViewList;
        this.mContext = context;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mViewList.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        if (mViewList == null)
            return 0;
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
