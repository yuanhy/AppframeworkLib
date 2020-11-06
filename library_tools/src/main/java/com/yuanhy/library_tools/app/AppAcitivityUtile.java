package com.yuanhy.library_tools.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.ClipboardManager;
import android.widget.Toast;

import com.yuanhy.library_tools.R;


/**
 * Created by yuanhy on 2018/1/22.
 */

public class AppAcitivityUtile {
    /**
     * 获取系統通知欄高度
     *
     * @param context
     * @return
     */
    public static int getTitlebarHith(Context context) {
        /**
         * 获取状态栏高度——方法1
         * */
        int statusBarHeight1 = 0;
        //获取status_bar_height资源的ID
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight1;
    }

    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    @SuppressLint("MissingPermission")
    public static void callPhone(Context context, String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        context.startActivity(intent);
    }

    /**
     * 复制到系统剪切板
     *
     * @param str
     */
    public static void saveToClipboard(Context context,String str) {
        // 从API11开始android推荐使用android.content.ClipboardManager
        // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
        ClipboardManager cm = (ClipboardManager)  context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(str);

        Toast.makeText(context,"复制到剪切板",Toast.LENGTH_LONG).show();
    }

    /**
     * 复制到系统剪切板
     *
     */
    public static String getToClipboard(Context context) {
        // 从API11开始android推荐使用android.content.ClipboardManager
        // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
        ClipboardManager cm = (ClipboardManager)  context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        if (cm==null||cm.getText( )==null){
            return "";
        }
      return cm.getText( ).toString();


    }
}
