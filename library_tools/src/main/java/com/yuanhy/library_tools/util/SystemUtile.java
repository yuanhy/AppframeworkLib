package com.yuanhy.library_tools.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

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

	public static int getsp(int sp){
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp, Resources.getSystem().getDisplayMetrics());
	}

	public static float convertDpToPixel(float dp) {
		DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics() ;//可以查看下里面的相关属性打印
		return dp * (metrics.densityDpi / 160f);
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


	public static int getViewHeight(View view){
		int width = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		int height = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		view.measure(width, height);
		return view.getMeasuredHeight();

	}
	public static int getViewWidth(View view){
		int width = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		int height = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		view.measure(width, height);
		return view.getMeasuredWidth();

	}
//	/**
//	 * 将适配的dp转换成px
//	 * @param context
//	 * @param dpId R.dimens.xx
//	 * @return
//	 */
//	public static int getPX(Context context,int pxId){
//		return (int) context.getResources().getDimensionPixelOffset()getDimensionPixelSize(dpId);
//	}
	public static int getScreenHeight(Context context){
		WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}
	public static int getScreenWidth(Context context){
		WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}
	public static Bitmap getBitmapByWidth(Resources resources, int resId, int width){
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(resources,resId,options);
		options.inJustDecodeBounds = false;
		options.inTargetDensity = width;
		return  BitmapFactory.decodeResource(resources,resId,options);
	}

	/**
	 * 相对于整个屏幕
	 * @param view
	 * @return
	 */
	public static int getViewY(View view){
		int[] position = new int[2];
		view.getLocationInWindow(position);
		System.out.println("getLocationInWindow:x:" + position[0] + ",y:" + position[1]);
		return position[1];
	}
	public static int getViewX(View view){
		int[] position = new int[2];
		view.getLocationInWindow(position);
		System.out.println("getLocationInWindow:x:" + position[0] + ",y:" + position[1]);
		return position[0];
	}
}
