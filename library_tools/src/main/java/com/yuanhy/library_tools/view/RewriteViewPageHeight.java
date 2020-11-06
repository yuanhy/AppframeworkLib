package com.yuanhy.library_tools.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;


public class RewriteViewPageHeight extends ViewPager {
	public RewriteViewPageHeight(@NonNull Context context) {
		super(context);
	}

	public RewriteViewPageHeight(@NonNull Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpaec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpaec);
	}
}
