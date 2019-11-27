package com.fenghuang.caipiaobao.widget.grildscroll;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fenghuang.caipiaobao.R;
import com.fenghuang.caipiaobao.manager.ImageManager;
import com.fenghuang.caipiaobao.ui.home.data.HomeLiveChatGifBean;

import java.util.List;


/**
 * @ Author  QinTian
 * @ Date  2019/11/25- 18:17
 * @ Describe
 */
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

    private String itemName = "";

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
//        return mDatas.size() > (curIndex + 1) * pageSize ? pageSize : (mDatas.size() - curIndex * pageSize);
        return mDatas.size();
    }

    @Override
    public HomeLiveChatGifBean getItem(int position) {
//        return mDatas.get(position + curIndex * pageSize);
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position + curIndex * pageSize;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_home_entrance, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tv = convertView.findViewById(R.id.entrance_name);
            viewHolder.iv = convertView.findViewById(R.id.entrance_image);
            viewHolder.item_layout = convertView.findViewById(R.id.itemRootView);
            viewHolder.entrance_prise = convertView.findViewById(R.id.entrance_prise);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        HomeLiveChatGifBean model = getItem(position);
        viewHolder.tv.setText(model.getName());
        ImageManager.INSTANCE.loadImage(model.getIcon(), viewHolder.iv);
        viewHolder.entrance_prise.setText(model.getAmount());
        if (model.isSelect() && model.getName().equals(itemName)) {//被选中
            viewHolder.item_layout.setBackgroundResource(R.drawable.shape_home_live_chat_gif_selected_bg);
        } else {
            viewHolder.item_layout.setBackgroundResource(R.drawable.shape_home_live_chat_gif_normal_bg);
        }
        return convertView;
    }

    public void notificationItem(String name) {
        this.itemName = name;
        notifyDataSetChanged();
    }


    class ViewHolder {
        RelativeLayout item_layout;
        TextView tv, entrance_prise;
        ImageView iv;
    }
}
