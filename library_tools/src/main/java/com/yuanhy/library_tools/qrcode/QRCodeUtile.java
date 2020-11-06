package com.yuanhy.library_tools.qrcode;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import com.google.zxing.WriterException;
import com.yuanhy.library_tools.R;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;
import com.yzq.zxinglibrary.encode.CodeCreator;

public class QRCodeUtile {

    public static void openQcCode(Activity activity,int request_code){
        Intent intent = new Intent(activity, CaptureActivity.class);
        /*ZxingConfig是配置类  可以设置是否显示底部布局，闪光灯，相册，是否播放提示音  震动等动能
         * 也可以不传这个参数
         * 不传的话  默认都为默认不震动  其他都为true
         * */

        ZxingConfig config = new ZxingConfig();
        config.setShowbottomLayout(true);//底部布局（包括闪光灯和相册）
        //config.setPlayBeep(true);//是否播放提示音
        //config.setShake(true);//是否震动
        config.setShowAlbum(true);//是否显示相册
        //config.setShowFlashLight(true);//是否显示闪光灯
//        intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
        activity.  startActivityForResult(intent, request_code);

    }
    public Bitmap a(Context context,String contentEtString){
            /*
             * contentEtString：字符串内容
             * w：图片的宽
             * h：图片的高
             * logo：不需要logo的话直接传null
             * */

            Bitmap logo = BitmapFactory.decodeResource(context.getResources(), R.mipmap.lib_update_app_close);
            Bitmap        bitmap = CodeCreator.createQRCode(contentEtString, 400, 400, logo);
            return bitmap;
    }


}
