package com.yuanhy.library_tools.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import com.gyf.barlibrary.ImmersionBar;
import com.yuanhy.library_tools.R;
import com.yuanhy.library_tools.app.AppAcitivityUtile;
import com.yuanhy.library_tools.popwindows.LoadPopupWindow;
import com.yuanhy.library_tools.popwindows.ToastPopWindow;
import com.yuanhy.library_tools.util.StatusBarUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseFragmentActivity extends FragmentActivity
		implements View.OnClickListener {

	boolean transparent = true;
	public Context context;
	public Unbinder mUnbinder;
	public FragmentManager fragmentManager;
	private View titleBarView;
	public LoadPopupWindow loadPopupWindow;
	public ToastPopWindow toastPopWindow;
	@Override
	public void onCreate(
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 取消标题
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setTransparent();
		setTitleBar();
		context = this;
		ActivityManagerUtile.getInstance().addActivity(this);
		fragmentManager = getSupportFragmentManager();
		initPopWindow();
	}

	private void initPopWindow() {
		loadPopupWindow = new LoadPopupWindow((Activity) context);
		toastPopWindow = new ToastPopWindow((Activity) context);
	}

	/**
	 * 设置状态栏 字体
	 *  transparent true 黑色字体,false 白色字体。背景全透明
	 */
	public void setTitleBar(){
		ImmersionBar.with(this).statusBarDarkFont(transparent).init();
	}
	/**
	 * 设置状态栏 字体
	 *  transparent true 黑色字体,false 白色字体。背景全透明
	 */
	public void setTitleBar(boolean transparent){
		ImmersionBar.with(this).statusBarDarkFont(transparent).init();
	}
	public   void initUnbinder( ){
		mUnbinder = ButterKnife.bind((Activity) context);
	};



	public abstract void setTransparent();

	/**
	 * true 黑色字体，白色背景
	 * false 白色字体
	 *
	 * @param transparent
	 */
	public void setTransparent(boolean transparent) {
		this.transparent = transparent;
	}
	public void initTittleBar(View view ) {
		titleBarView = view;
		int h = AppAcitivityUtile.getTitlebarHith(context);
		titleBarView.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, h));// 设置布局);
		titleBarView.setVisibility(View.VISIBLE);

	}

	/**
	 *
	 * @param colco
	 * @param ratio 透明度  0全透明。1不透明
	 */
	public void setTitleBarBackgroundColor(int colco, @FloatRange(from = 0.0, to = 1.0) float ratio){
		titleBarView.setBackgroundColor(colco);
		titleBarView	.setBackgroundColor(ColorUtils.blendARGB(Color.TRANSPARENT
				, ContextCompat.getColor(context, R.color.color_ffffff), ratio));
	}
	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void onClick(View v) {
	}

	public void initView() {
	}

	@Override
	protected void onPause() {
		super.onPause();
	}







	@Override
	protected void onDestroy() {
		ActivityManagerUtile.getInstance().remove(this);
		if (mUnbinder!=null&&mUnbinder != Unbinder.EMPTY) {
			mUnbinder.unbind();
			this.mUnbinder = null;
		}
		if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
			mUnbinder.unbind();
			this.mUnbinder = null;
		}
		if (loadPopupWindow != null && loadPopupWindow.isShowing()) {
			loadPopupWindow.dismiss(true);
		}
		if (toastPopWindow != null && toastPopWindow.isShowing()) {
			toastPopWindow.dismiss();
		}
		super.onDestroy();
	}

}
