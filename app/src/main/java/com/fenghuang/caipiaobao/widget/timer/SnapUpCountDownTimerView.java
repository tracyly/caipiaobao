
package com.fenghuang.caipiaobao.widget.timer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fenghuang.caipiaobao.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 倒计时
 */
@SuppressLint("HandlerLeak")
public class SnapUpCountDownTimerView extends LinearLayout {

    private TextView tv_hour_decade;
    private TextView tv_hour_unit;
    private TextView tv_min_decade;
    private TextView tv_min_unit;
    private TextView tv_sec_decade;
    private TextView colon_hour;
    private TextView tv_sec_unit;

    private Context context;

    private Timer timer;

    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            countDown();
        }
    };

    public SnapUpCountDownTimerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_countdowntimer, this);

        tv_hour_decade = view.findViewById(R.id.tv_hour_decade);
        tv_hour_unit = view.findViewById(R.id.tv_hour_unit);
        tv_min_decade = view.findViewById(R.id.tv_min_decade);
        tv_min_unit = view.findViewById(R.id.tv_min_unit);
        tv_sec_decade = view.findViewById(R.id.tv_sec_decade);
        tv_sec_unit = view.findViewById(R.id.tv_sec_unit);
        colon_hour = view.findViewById(R.id.colon_hour);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SnapUpCountDownTimerView);
        int size = array.getInteger(R.styleable.SnapUpCountDownTimerView_viewSize, 12);


        tv_hour_decade.setTextSize(size);
        tv_hour_unit.setTextSize(size);
        tv_min_decade.setTextSize(size);
        tv_min_unit.setTextSize(size);
        tv_sec_decade.setTextSize(size);
        tv_sec_unit.setTextSize(size);
        ((TextView) view.findViewById(R.id.colon_minute)).setTextSize(size);
        ((TextView) view.findViewById(R.id.colon_hour)).setTextSize(size);
    }


    public void start() {
        if (timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.sendEmptyMessage(0);
                }
            }, 0, 1000);
        }
    }


    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @SuppressLint("SetTextI18n")
    public void setMinTime(int min, int sec) {

//        if (hour >= 60 || min >= 60 || sec >= 60 || hour < 0 || min < 0 || sec < 0) {
//            throw new RuntimeException("时间格式错误,请检查你的代码");
//        }
        tv_hour_decade.setVisibility(GONE);
        tv_hour_unit.setVisibility(GONE);
        colon_hour.setVisibility(GONE);
        int mMinDecade = min / 10;
        int mMinUnit = min - mMinDecade * 10;

        int mSecDecade = sec / 10;
        int mSecUnit = sec - mSecDecade * 10;

        tv_min_decade.setText(mMinDecade + "");
        tv_min_unit.setText(mMinUnit + "");
        tv_sec_decade.setText(mSecDecade + "");
        tv_sec_unit.setText(mSecUnit + "");
    }


    @SuppressLint("SetTextI18n")
    public void setTime(int hour, int min, int sec) {

//        if (hour >= 60 || min >= 60 || sec >= 60 || hour < 0 || min < 0 || sec < 0) {
//            throw new RuntimeException("时间格式错误,请检查你的代码");
//        }

        int mHourDecade = hour / 10;
        int mHourUnit = hour - mHourDecade * 10;

        int mMinDecade = min / 10;
        int mMinUnit = min - mMinDecade * 10;

        int mSecDecade = sec / 10;
        int mSecUnit = sec - mSecDecade * 10;

        tv_hour_decade.setText(mHourDecade + "");
        tv_hour_unit.setText(mHourUnit + "");
        tv_min_decade.setText(mMinDecade + "");
        tv_min_unit.setText(mMinUnit + "");
        tv_sec_decade.setText(mSecDecade + "");
        tv_sec_unit.setText(mSecUnit + "");
    }


    private void countDown() {
        if (isCarry4Unit(tv_sec_unit)) {
            if (isCarry4Decade(tv_sec_decade)) {
                if (isCarry4Unit(tv_min_unit)) {
                    if (isCarry4Decade(tv_min_decade)) {
                        if (isCarry4Unit(tv_hour_unit)) {
                            if (isCarry4Decade(tv_hour_decade)) {
                                Toast.makeText(context, "计数完成",
                                        Toast.LENGTH_SHORT).show();
                                stop();
                                setTime(0, 0, 0);//重置为0
                            }
                        }
                    }
                }
            }
        }
    }


    @SuppressLint("SetTextI18n")
    private boolean isCarry4Decade(TextView tv) {

        int time = Integer.valueOf(tv.getText().toString());
        time = time - 1;
        if (time < 0) {
            time = 5;
            tv.setText(time + "");
            return true;
        } else {
            tv.setText(time + "");
            return false;
        }
    }


    @SuppressLint("SetTextI18n")
    private boolean isCarry4Unit(TextView tv) {

        int time = Integer.valueOf(tv.getText().toString());
        time = time - 1;
        if (time < 0) {
            time = 9;
            tv.setText(time + "");
            return true;
        } else {
            tv.setText(time + "");
            return false;
        }
    }
}
