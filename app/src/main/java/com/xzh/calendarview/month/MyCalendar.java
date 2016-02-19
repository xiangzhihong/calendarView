package com.xzh.calendarview.month;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xzh.calendarview.R;
import com.xzh.calendarview.adapter.CalGridViewAdapter;

public class MyCalendar extends LinearLayout {

    private static Context context;
    private Date theInDay;
    private String inday = "", outday = "";
    private List<String> gvList;//存放天
    private CalGridViewAdapter calAdapter;
    private OnDaySelectListener callBack;//回调函数


    public MyCalendar(Context context) {
        super(context);
        this.context = context;
    }

    public MyCalendar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void setInDay(String inday) {
        this.inday = inday;
    }

    public void setOutDay(String outday) {
        this.outday = outday;
    }

    public void setTheDay(Date dateIn) {
        this.theInDay = dateIn;
        init();
    }

    /**
     * 初始化日期以及view等控件
     */
    private void init() {
        gvList = new ArrayList<String>();//存放天
         Calendar cal = Calendar.getInstance();//获取日历实例
        cal.setTime(theInDay);//cal设置为当天的
        cal.set(Calendar.DATE, 1);//cal设置当前day为当前月第一天
        int tempSum = countNeedHowMuchEmpety(cal);//获取当前月第一天为星期几
        int dayNumInMonth = getDayNumInMonth(cal);//获取当前月有多少天
        setGvListData(tempSum, dayNumInMonth, cal.get(Calendar.YEAR) + "-" + getMonth((cal.get(Calendar.MONTH) + 1)));

        View view = LayoutInflater.from(context).inflate(R.layout.comm_calendar, this, true);//获取布局，开始初始化
        TextView tv_year = (TextView) view.findViewById(R.id.tv_year);
        if (cal.get(Calendar.YEAR) > new Date().getYear()) {
            tv_year.setVisibility(View.VISIBLE);
            tv_year.setText(cal.get(Calendar.YEAR) + "年");
        }
        TextView tv_month = (TextView) view.findViewById(R.id.tv_month);
        tv_month.setText(String.valueOf(theInDay.getMonth() + 1) + "月");
        MyGridView gv = (MyGridView) view.findViewById(R.id.gv_calendar);

        calAdapter  = new CalGridViewAdapter(context,gvList, inday, outday);
        gv.setAdapter(calAdapter);
        gv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
                String choiceDay = (String) adapterView.getAdapter().getItem(position);
                String[] date = choiceDay.split(",");
                String day = date[1];
                if (!" ".equals(day)) {
                    if (Integer.parseInt(day) < 10) {
                        day = "0" + date[1];
                    }
                    choiceDay = date[0] + "-" + day;
                    if (callBack != null) {//调用回调函数回调数据
                        callBack.onDaySelectListener(arg1, choiceDay);
                    }
                }
            }
        });
    }

    /**
     * 为gridview中添加需要展示的数据
     *
     * @param tempSum
     * @param dayNumInMonth
     */
    private void setGvListData(int tempSum, int dayNumInMonth, String YM) {
       if (gvList!=null){
           gvList.clear();
           for (int i = 0; i < tempSum; i++) {
               gvList.add(" , ");
           }
           for (int j = 1; j <= dayNumInMonth; j++) {
               gvList.add(YM + "," + String.valueOf(j));
           }
       }
    }

    private String getMonth(int month) {
        String mon = "";
        if (month < 10) {
            mon = "0" + month;
        } else {
            mon = "" + month;
        }
        return mon;
    }

    /**
     * 获取当前月的总共天数
     *
     * @param cal
     * @return
     */
    private int getDayNumInMonth(Calendar cal) {
        return cal.getActualMaximum(Calendar.DATE);
    }

    /**
     * 获取当前月第一天在第一个礼拜的第几天，得出第一天是星期几
     *
     * @param cal
     * @return
     */
    private int countNeedHowMuchEmpety(Calendar cal) {
        int firstDayInWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
        return firstDayInWeek;
    }

   public  void setDefaultInView(int color,int textColor){
       if (calAdapter!=null){
           calAdapter.viewIn.setBackgroundColor(color);
           ((TextView)calAdapter.viewIn.findViewById(R.id.tv_calendar_day)).setTextColor(textColor);
       }
   }

    public  void setDefaultOutView(int color,int textColor){
       if (calAdapter!=null){
           calAdapter.viewOut.setBackgroundColor(color);
           ((TextView)calAdapter.viewOut.findViewById(R.id.tv_calendar_day)).setTextColor(textColor);
       }
    }

    //监听是否点击接口
    public interface OnDaySelectListener {
        void onDaySelectListener(View view, String date);
    }

    public void setOnDaySelectListener(OnDaySelectListener o) {
        callBack = o;
    }
}
