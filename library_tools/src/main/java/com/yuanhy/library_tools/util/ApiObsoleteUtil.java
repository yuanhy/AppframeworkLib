package com.yuanhy.library_tools.util;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * 兼容系统API过时的方法
 */
public class ApiObsoleteUtil {
    /**
     * 设置setBackground
     * @param view
     * @param context
     * @param drawableId
     */
    public static void setBackground(View view, Context context, int drawableId){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(context.getResources().getDrawable(drawableId));
        }else {
            view.setBackgroundDrawable(context.getResources().getDrawable(drawableId));
        }
    }

    /**
     * 设置setBackground
     * @param view
     * @param context
     * @param colorId
     */
    public static void BackgroundColor(View view, Context context, int colorId){
        view.setBackgroundColor(context.getResources().getColor(colorId));
    }

    /**
     * 设置字体颜色
     * @param context
     * @param textView
     * @param coolorId R.color.xxxx
     */
    public static void setTextColor(Context context, TextView textView,int coolorId){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textView.setTextColor(context.getColor(coolorId));
        }else {
            textView.setTextColor(context.getResources().getColor(coolorId));
        }
    }

}
