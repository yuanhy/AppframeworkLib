package com.yuanhy.library_tools.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

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
