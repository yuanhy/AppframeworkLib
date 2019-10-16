package com.yuanhy.library_tools.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class RewriteGridViewHeight extends GridView {
	public RewriteGridViewHeight(Context context) {
		super(context);
	}

	public RewriteGridViewHeight(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public RewriteGridViewHeight(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpaec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpaec);
	}
}
