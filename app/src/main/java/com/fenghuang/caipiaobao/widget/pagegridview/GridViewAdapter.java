package com.fenghuang.caipiaobao.widget.pagegridview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fenghuang.caipiaobao.R;
import com.fenghuang.caipiaobao.ui.home.data.HomeLiveChatGifBean;

import java.util.List;

public class GridViewAdapter extends BaseAdapter {
    private List<HomeLiveChatGifBean> mDatas;
    private LayoutInflater inflater;
    private Context mContext;
    /**
     * 页数下标,从0开始(当前是第几页)
     */
    private int curIndex;
    /**
     * 每一页显示的个数
     */
    private int pageSize = 8;

    public GridViewAdapter(Context context, List<HomeLiveChatGifBean> mDatas, int curIndex) {
        inflater = LayoutInflater.from(context);
        this.mDatas = mDatas;
        this.curIndex = curIndex;
        this.mContext = context;
    }

    /**
     * 先判断数据集的大小是否足够显示满本页？mDatas.size() > (curIndex+1)*pageSize,
     * 如果够，则直接返回每一页显示的最大条目个数pageSize,
     * 如果不够，则有几项返回几,(mDatas.size() - curIndex * pageSize);(也就是最后一页的时候就显示剩余item)
     */
    @Override
    public int getCount() {
        return mDatas.size() > (curIndex + 1) * pageSize ? pageSize : (mDatas.size() - curIndex * pageSize);
    }

    @Override
    public HomeLiveChatGifBean getItem(int position) {
        return mDatas.get(position + curIndex * pageSize);
    }

    @Override
    public long getItemId(int position) {
        return position + curIndex * pageSize;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_gif_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.title = convertView.findViewById(R.id.item_title);
            viewHolder.titleHint = convertView.findViewById(R.id.item_title_hint);
            viewHolder.logo = convertView.findViewById(R.id.item_image);
            viewHolder.item_layout = convertView.findViewById(R.id.ll_layout);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        /**
         * 在给View绑定显示的数据时，计算正确的position = position + curIndex * pageSize，
         */
        HomeLiveChatGifBean model = getItem(position);
        viewHolder.title.setText(model.getTitle());
        viewHolder.titleHint.setText(String.valueOf(model.getGold()));
        viewHolder.logo.setImageResource(model.getGifUrl());
        if (model.isSelect()) {//被选中
            viewHolder.item_layout.setBackground(mContext.getResources().getDrawable(R.drawable.shape_home_live_chat_gif_selected_bg));
        } else {
            viewHolder.item_layout.setBackground(mContext.getResources().getDrawable(R.drawable.shape_home_live_chat_gif_normal_bg));
        }
        return convertView;
    }


    class ViewHolder {
        public LinearLayout item_layout;
        public TextView title;
        public ImageView logo;
        public TextView titleHint;
    }

}