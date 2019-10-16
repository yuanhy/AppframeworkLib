package cc.zenking.edu.zhjx.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cc.zenking.edu.zhjx.R;
import cc.zenking.edu.zhjx.enty.Date;

/**
 * Created by Administrator on 2019/4/17.
 */

public class HolidayMsgTimeAdapter extends BaseAdapter {
    Context context;
    Date[] times ;
    public HolidayMsgTimeAdapter(Context context , Date[] times){
        this.context=context;
        this.times=times;
    };
    ArrayList<String> dateTimeShow = new ArrayList<>() ;
    int studens=1;
    boolean isLeave;
    public void setDataArrayList(Date[] times, ArrayList<String> dateTimeShow , int studens, boolean isLeave){
        this.times=times;
        this.dateTimeShow=dateTimeShow;
        this.studens=studens;
        this.isLeave=isLeave;
        notifyDataSetChanged();
    }
    boolean isOneLine=false;
    public void setIsOneLine(){
        isOneLine=!isOneLine;
        notifyDataSetChanged();
    }
    public boolean getIsOneLine(){
        return isOneLine;
    }
    @Override
    public int getCount() {
        if (isOneLine){
            return 1;
        }else {
            return  times.length;
        }
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Date holiday=times[position];
        convertView = LayoutInflater.from(context).inflate(R.layout.head_holiday_time_item,parent,false);

       TextView tv_time = (TextView) convertView.findViewById(R.id.tv_time);
        tv_time.setText(dateTimeShow.get(position));
        TextView tv_out_time_tag= (TextView) convertView.findViewById(R.id.tv_out_time_tag);
        if (TextUtils.isEmpty(holiday.lastOutTime)||studens>1){
            tv_out_time_tag.setVisibility(View.GONE);
        }else {
            if (isLeave){
                tv_out_time_tag.setVisibility(View.VISIBLE);
            }else {
                tv_out_time_tag.setVisibility(View.GONE);
            }
        }
        return convertView;
    }
}
