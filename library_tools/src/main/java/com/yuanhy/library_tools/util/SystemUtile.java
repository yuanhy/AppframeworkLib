package com.yuanhy.library_tools.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

import com.yuanhy.library_tools.R;

/**
 * 关于手机和系统的相关信息工具类
 */
public class SystemUtile {
	public static int dp2px(int dp){
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp, Resources.getSystem().getDisplayMetrics());
	}
	public static int dpToPx(int dp){
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,dp, Resources.getSystem().getDisplayMetrics());
	}

	/**
	 * 将适配的dp转换成px
	 * @param context
	 * @param dpId R.dimens.xx
	 * @return
	 */
	public static int getPX(Context context,int dpId){
		return (int) context.getResources().getDimensionPixelSize(dpId);
	}

	public static int getScreenHeight(Context context){
		WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}

	public static Bitmap getBitmapByWidth(Resources resources, int resId, int width){
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(resources,resId,options);
		options.inJustDecodeBounds = false;
		options.inTargetDensity = width;
		return  BitmapFactory.decodeResource(resources,resId,options);
	}
}
