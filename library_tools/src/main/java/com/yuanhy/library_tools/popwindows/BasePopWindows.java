package com.yuanhy.library_tools.popwindows;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import androidx.annotation.FloatRange;

import com.yuanhy.library_tools.R;
import com.yuanhy.library_tools.util.YCallBack;

import java.util.LinkedList;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BasePopWindows extends PopupWindow implements View.OnClickListener {
	public Activity activity;
	public View view;
	public LayoutInflater layoutInflater;
	public Context context;
	public YCallBack yCallBack;

	public BasePopWindows(Context content) {
		this.context = content;
		layoutInflater = LayoutInflater.from(content);
		view = (View) layoutInflater.inflate(getLoutResourceId(), null);
		setContentView(view);
		try {
			this.activity = (Activity) content;
		}catch (Exception e){

		}
		setFocusable(true);
		setTouchable(true);
		setOutsideTouchable(true);
	}


	@Override
	public void dismiss() {
		super.dismiss();
	}

	@Override
	public void onClick(View v) {

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
}
