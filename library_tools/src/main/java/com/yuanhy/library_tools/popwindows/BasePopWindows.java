package com.yuanhy.library_tools.popwindows;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.FloatRange;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.PopupWindow;


import com.yuanhy.library_tools.R;
import com.yuanhy.library_tools.util.TimeUtil;
import com.yuanhy.library_tools.util.YCallBack;

import java.util.LinkedList;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BasePopWindows extends PopupWindow implements View.OnClickListener , ViewTreeObserver.OnGlobalLayoutListener{
	public Activity activity;
	public View view;
	public LayoutInflater layoutInflater;
	public Context context;
	public YCallBack yCallBack;
	private Unbinder mUnbinder;
	int moveHeight = 0;
//	public	ToastPopWindow toastPopWindow;
	public BasePopWindows(Context content) {
//		toastPopWindow = new ToastPopWindow(context);
		this.context = content;
		layoutInflater = LayoutInflater.from(content);
		view = (View) layoutInflater.inflate(getLoutResourceId(), null);
		setContentView(view);
		mUnbinder = ButterKnife.bind(this,view);
		try {
			this.activity = (Activity) content;
		}catch (Exception e){

		}
		//点击就消失
		setFocusable(true);
		setTouchable(true);
		setOutsideTouchable(true);
//		//点击外部不消失
//		setFocusable(false);
//		setTouchable(true);
//		setOutsideTouchable(false);
	}

	@Override
	public void dismiss() {
		super.dismiss();
	}

	@Override
	public void onClick(View v) {
		if (!TimeUtil.invalidTime(1000)){
return;
		}

	}

	public void setyCallBack(YCallBack yCallBack) {
		this.yCallBack = yCallBack;
	}

	public abstract int getLoutResourceId();

	/**
	 * @param isSetBackgroundAlpha true 屏幕恢复正常
	 */
	public void dismiss(boolean isSetBackgroundAlpha) {
		dismiss();
		if (isSetBackgroundAlpha) {
			setBackgroundAlpha(activity, 1f);
		}
	}

	/**
	 * 设置页面的透明度
	 *
	 * @param bgAlpha 1表示不透明 恢复高亮,0.5半透明
	 */
	public void setBackgroundAlpha(Activity activity, @FloatRange(from = 0f, to = 1f) float bgAlpha) {
		WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
		lp.alpha = bgAlpha;
		activity.getWindow().setAttributes(lp);
	}
	public void initDataView(){}
	@Override
	public void onGlobalLayout() {
		Rect r = new Rect();
		//获取当前界面可视部分
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
		//获取屏幕的高度
		int screenHeight = activity.getWindow().getDecorView().getRootView().getHeight();
		//此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
		int heightDifference = screenHeight - r.bottom;
		Log.d("Keyboard Size", "Size: " + heightDifference);
		if (heightDifference != 0) {
			moveHeight = heightDifference;
//			view.scrollBy(0, moveHeight);
		} else {
			view.scrollBy(0, -moveHeight);
		}

	}
}
