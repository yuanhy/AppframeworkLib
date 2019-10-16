package com.yuanhy.library_tools.popwindows;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuanhy.library_tools.R;
import com.yuanhy.library_tools.http.okhttp3.AndroidOkHttp3;
import com.yuanhy.library_tools.rxjava.RxjavaUtilInterval;
import com.yuanhy.library_tools.util.YCallBack;

public class ToastPopWindow extends PopupWindow {

	RxjavaUtilInterval rxjavaUtilInterval;
	View contentView;
	Context activity;
	TextView content_tv;
private static 	ToastPopWindow toastPopWindow;
	public static ToastPopWindow getInstance(Context context) {
		if (toastPopWindow == null) {
			synchronized (ToastPopWindow.class) {
				toastPopWindow = new ToastPopWindow(context);
			}
		}
		return toastPopWindow;
	}

	public ToastPopWindow(Context context) {
		activity = context;
		contentView = LayoutInflater.from(context).inflate(R.layout.toastpopwindow_lout, null);
		setContentView(contentView);
		setOutsideTouchable(false);
		setFocusable(false);
		setTouchable(true);

		setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
		setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
		content_tv = (TextView) contentView.findViewById(R.id.content_tv);
		rxjavaUtilInterval = new RxjavaUtilInterval(new YCallBack() {
			@Override
			public void requestSuccessful(Object o) {
				rxjavaUtilInterval.cancelDisposable();
				dismiss();
			}

			@Override
			public void requestFail(Object o) {

			}
		});
	}

	public ToastPopWindow setMessge(String messge) {
		rxjavaUtilInterval.cancelDisposable();
		content_tv.setText(messge);
		rxjavaUtilInterval.interval(3);
		return this;
	}

	public void show() {
		if (isShowing()) {
			return;
		}
		if (activity instanceof Activity) {
			showAtLocation(((Activity) activity).getWindow().getDecorView(), Gravity.CENTER, 0, 0);
		}

	}

	@Override
	public void dismiss() {
		super.dismiss();
		toastPopWindow =null;
	}
}
