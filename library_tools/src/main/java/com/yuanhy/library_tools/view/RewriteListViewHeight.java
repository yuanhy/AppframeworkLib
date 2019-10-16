package com.yuanhy.library_tools.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class RewriteListViewHeight extends ListView {
	public RewriteListViewHeight(Context context) {
		super(context);
	}

	public RewriteListViewHeight(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public RewriteListViewHeight(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpaec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpaec);
	}
}
