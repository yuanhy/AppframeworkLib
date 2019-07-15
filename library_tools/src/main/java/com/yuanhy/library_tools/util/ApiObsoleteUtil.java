package com.yuanhy.library_tools.util;

import android.content.Context;
import android.os.Build;
import android.view.View;
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
    public static void setTextColor(TextView view, Context context, int color){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.setTextColor(context.getColor(color));
        }else {
            view.setTextColor(context.getResources().getColor(color));
        }
    }

}
