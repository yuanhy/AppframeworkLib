package com.yuanhy.library_tools.image;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.yuanhy.library_tools.util.SdCardUtil;
import com.yuanhy.library_tools.util.StringUtil;
import com.yuanhy.library_tools.util.TimeUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtil {
    /**
     *
     * @param directoryPath 文件夹路径
     * @param imageName 文件名字  不要携带后缀 ，自动补充URL中的后缀，默认 .jpg
     * @param url  图片的URL 通过
     * @return
     */
    protected static File newImageFile(String directoryPath,String imageName, String url) {
        String suffix = ".jpg";
        if (StringUtil.isNotNull(url)) {
            String[] str = url.split("\\.");
            if (str != null) {
                if (str[str.length - 1].length()<5){
                    suffix = "." + str[str.length - 1];
                }
            }
        }
        File fileDirectoryPath = new File(directoryPath );
        if (!fileDirectoryPath.exists()) {
            fileDirectoryPath.mkdirs();
        }
        File file = new File(directoryPath + "/" + imageName + suffix);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return file;
    }

    /**
     * 截取屏幕
     * @param activity
     * @return
     */
    public static Bitmap activityShot(Activity activity) {
        /*获取windows中最顶层的view*/
        View view = activity.getWindow().getDecorView();

        //允许当前窗口保存缓存信息
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();

        //获取状态栏高度
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        int statusBarHeight = rect.top;

        WindowManager windowManager = activity.getWindowManager();

        //获取屏幕宽和高
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;

        //去掉状态栏
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache(), 0, statusBarHeight, width,
                height - statusBarHeight);

        //销毁缓存信息
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(false);

        return bitmap;
    }
    public  static Bitmap createViewBitmap(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }

    public static String saveBitmap(Bitmap bitmap, Context context) {


        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "image");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = "image" + TimeUtil.getDate("yyyy_MM_dd_HH_mm_ss") + ".png";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        // 把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        // 通知图库更新
//        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + "/sdcard/namecard/")));

        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(file.getPath()))));

        return file.getAbsolutePath();
    }
}
