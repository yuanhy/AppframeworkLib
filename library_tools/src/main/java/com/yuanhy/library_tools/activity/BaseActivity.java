package com.yuanhy.library_tools.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.yuanhy.library_tools.popwindows.LoadPopupWindow;
import com.yuanhy.library_tools.popwindows.ToastPopWindow;
import com.yuanhy.library_tools.presenter.BasePresenter;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public abstract class BaseActivity<T extends BasePresenter> extends BaseActicity2 implements View.OnClickListener {
	public	T basePresenter;
	private Unbinder mUnbinder;
	public LoadPopupWindow loadPopupWindow;
	public ToastPopWindow toastPopWindow;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		basePresenter = createPresenter();
		if (basePresenter != null)
			basePresenter.onBindView(this);
		initPopWindow();
	}

	private void initPopWindow() {
		loadPopupWindow = new LoadPopupWindow((Activity) context);
		toastPopWindow = new ToastPopWindow((Activity) context);
	}

	public void initUnbinder() {
		mUnbinder = ButterKnife.bind(this);

	}

	public abstract T createPresenter();

	@Override
	protected void onDestroy() {

		if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
			mUnbinder.unbind();
			this.mUnbinder = null;
		}
		if (basePresenter != null) {
			basePresenter.onDestroy();
			this.basePresenter = null;
		}
		if (loadPopupWindow != null && loadPopupWindow.isShowing()) {
			loadPopupWindow.dismiss(true);
		}
		if (toastPopWindow != null && toastPopWindow.isShowing()) {
			toastPopWindow.dismiss();
		}
		super.onDestroy();
	}



	@Override
	public void onClick(View v) {

	}
}
