package com.fenghuang.caipiaobao.ui.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fenghuang.caipiaobao.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @ Author  QinTian
 * @ Date  2019/12/18- 14:59
 * @ Describe
 */
public class ToastCommom {


    private static TextView text;
    private static Toast toast;

    /**
     * 显示Toast
     *
     * @param context
     * @param tvString
     * @param cntime
     */
    public static void ToastShow(Context context, String tvString, int cntime) {

        if (toast == null) {
            toast = new Toast(context);
            toast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL, 0, -50);
            toast.setDuration(Toast.LENGTH_LONG);
            View layout = LayoutInflater.from(context).inflate(R.layout.item_home_entrance, null);
//            ImageView mImageView = (ImageView) layout.findViewById(R.id.iv);
//            mImageView.setBackgroundResource(R.drawable.ic_launcher);
            text = layout.findViewById(R.id.entrance_name);
            toast.setView(layout);
            showMyToast(toast, cntime);
        }
        text.setText(tvString);
        text.setTextColor(0xFFFFFFFF);
        text.setTextSize(16);
        if (toast != null) {
            toast.show();
        }

    }


    //自定义停留时间
    private static void showMyToast(final Toast toast, final int cnt) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
            }

        }, 0, 3000);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                timer.cancel();
            }

        }, cnt);

    }


}
