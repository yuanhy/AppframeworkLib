package com.yuanhy.library_tools.popwindows;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.yuanhy.library_tools.R;

public class LoadPopupWindow extends PopupWindow {


	View contentView;
	Activity activity;
	public LoadPopupWindow(Activity context) {
		activity =context;
		contentView = LayoutInflater.from(context).inflate(R.layout.load_popup_window, null);
		setContentView(contentView);
		setOutsideTouchable(false);
		setFocusable(false);
		setTouchable(true);

		setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
		setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);


	}

	/**
	 * 半透明
	 */
	public void showPopWindowCenterBackgroundAlpha(){
		showAtLocation(activity.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
		setBackgroundAlpha(activity,0.5f);
	}
	public void showPopWindowCenter(){
		if (isShowing())
			return;
		showAtLocation(activity.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
	}

	/**
	 *
	 * @param isSetBackgroundAlpha  true 屏幕恢复正常
	 */
	public void dismiss(boolean isSetBackgroundAlpha) {
		dismiss();
		if (isSetBackgroundAlpha){
			setBackgroundAlpha(activity,1f);
		}
	}
	/**
	 * 设置页面的透明度
	 *
	 * @param bgAlpha 1表示不透明 恢复高亮
	 */
	public void setBackgroundAlpha(Activity activity, float bgAlpha) {
		WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
		lp.alpha = bgAlpha;
		activity.getWindow().setAttributes(lp);
	}
}
