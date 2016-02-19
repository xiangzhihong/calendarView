package com.xzh.calendarview.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xzh.calendarview.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by xiangzhihong on 2016/2/1 on 14:52.
 * 日历cal
 */
public class CalGridViewAdapter extends BaseAdapter {

    private Context context;
    private long nd = 1000 * 24 * 60 * 60;//一天的毫秒数
    private List<String> gvList;//存放天
    private String inday, outday;//开始，结束日期
    public  static View viewIn;
    public  static View viewOut;
    public  String positionIn;
    public  String positionOut;
    private static String nowday = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//日期格式化

    public CalGridViewAdapter(Context context, List<String> gvList, String inday, String outday) {
        super();
        this.context = context;
        this.gvList = gvList;
        this.inday = inday;
        this.outday = outday;
    }

    @Override
    public int getCount() {
        return gvList.size();
    }

    @Override
    public String getItem(int position) {
        return gvList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        {
            GrideViewHolder holder;
            if (convertView == null) {
                holder = new GrideViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.common_calendar_gridview_item, null);
                holder.tv = (TextView) convertView.findViewById(R.id.tv_calendar);
                holder.tvDay = (TextView) convertView.findViewById(R.id.tv_calendar_day);
                convertView.setTag(holder);
            } else {
                holder = (GrideViewHolder) convertView.getTag();
            }
            String[] date = getItem(position).split(",");
            holder.tvDay.setText(date[1]);
            if ((position + 1) % 7 == 0 || (position) % 7 == 0) {
                holder.tvDay.setTextColor(context.getResources().getColor(R.color.color_default));
            }
            if (!date[1].equals(" ")) {
                String day = date[1];
                if (Integer.parseInt(date[1]) < 10) {
                    day = "0" + date[1];
                }
                //今天背景
                if ((date[0] + "-" + day).equals(nowday)) {
                    holder.tvDay.setTextColor(context.getResources().getColor(R.color.color_orange));
                    holder.tvDay.setTextSize(15);
                    holder.tvDay.setText("今天");
                }
                if (!"".equals(inday) && (date[0] + "-" + day).equals(inday)) {
                    convertView.setBackgroundColor(context.getResources().getColor(R.color.cal_choose_bg));
                    holder.tvDay.setTextColor(Color.WHITE);
                    holder.tvDay.setText(date[1]);
                    holder.tv.setText("入住");
                    viewIn = convertView;
                    positionIn = date[1];
                }
                if (!"".equals(outday) && (date[0] + "-" + day).equals(outday)) {
                    convertView.setBackgroundColor(context.getResources().getColor(R.color.cal_choose_bg));
                    holder.tvDay.setTextColor(Color.WHITE);
                    holder.tvDay.setText(date[1]);
                    holder.tv.setText("离开");
                    viewOut = convertView;
                    positionOut = date[1];
                }
                try {
                    //若日历日期<当前日期，则不能选择
                    if (dateFormat.parse(date[0] + "-" + day).getTime() < dateFormat.parse(nowday).getTime()) {
                        holder.tvDay.setTextColor(context.getResources().getColor(R.color.color_gray));
                    }
                    //若日历日期-当前日期>90天，则不能选择
                    long dayxc = (dateFormat.parse(date[0] + "-" + day).getTime() - dateFormat.parse(nowday).getTime()) / nd;
                    if (dayxc > 90) {
                        holder.tvDay.setTextColor(context.getResources().getColor(R.color.color_gray));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            return convertView;
        }
    }

    static class GrideViewHolder {
        TextView tvDay;
        TextView tv;
    }



}
