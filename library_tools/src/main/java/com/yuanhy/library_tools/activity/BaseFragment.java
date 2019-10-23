package com.yuanhy.library_tools.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yuanhy.library_tools.interfaces.ListViewInterface;
import com.yuanhy.library_tools.popwindows.LoadPopupWindow;
import com.yuanhy.library_tools.popwindows.ToastPopWindow;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BaseFragment extends Fragment   implements ListViewInterface {
	private Unbinder mUnbinder;
	public Context context;
	public LoadPopupWindow loadPopupWindow;
	public ToastPopWindow toastPopWindow;

	public BaseFragment() {

	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initUnbinder();
		initPopWindow();
	}
	private void initPopWindow() {
		loadPopupWindow = new LoadPopupWindow((Activity) context);
		toastPopWindow = new ToastPopWindow((Activity) context);
	}
	public  void initUnbinder(){
	mUnbinder = (Unbinder) ButterKnife.bind(this,getView());
}
	@Override
	public void onAttach(Context context) {
		this.context = context;
		super.onAttach(context);
	}

	@Override
	public void onDestroy() {
		if (mUnbinder != Unbinder.EMPTY) {
			mUnbinder.unbind();
			this.mUnbinder = null;
		}
		super.onDestroy();
	}

	@Override
	public void refreshData() {

	}

	@Override
	public void loadMoveData() {

	}
}
