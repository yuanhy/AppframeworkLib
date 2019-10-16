// Copyright 2013 Square, Inc.

package cc.zenking.edu.zhjx.timessquare;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;


import cc.zenking.edu.zhjx.R;


public class CalendarCellView extends FrameLayout {
	private static final int[] STATE_SELECTABLE = { R.attr.tsquare_state_selectable };
	private static final int[] STATE_CURRENT_MONTH = { R.attr.tsquare_state_current_month };
	private static final int[] STATE_TODAY = { R.attr.tsquare_state_today };
	private static final int[] STATE_UN_SELECT = { R.attr.tsquare_state_unselect };
	private static final int[] STATE_HIGHLIGHTED = { R.attr.tsquare_state_highlighted };
	private static final int[] STATE_RANGE_FIRST = { R.attr.tsquare_state_range_first };
	private static final int[] STATE_RANGE_MIDDLE = { R.attr.tsquare_state_range_middle };
	private static final int[] STATE_RANGE_LAST = { R.attr.tsquare_state_range_last };
	private static final int[] STATE_ONLY_SHOW = { R.attr.tsquare_state_onlyshow };

	private boolean isSelectable = false;
	private boolean isCurrentMonth = false;
	private boolean isToday = false;
	private boolean isHighlighted = false;
	private boolean isOnlyShow = false;
	private MonthCellDescriptor.RangeState rangeState = MonthCellDescriptor.RangeState.NONE;
	private TextView dayOfMonthTextView;
	private boolean isUnSelect = false;

	@SuppressWarnings("UnusedDeclaration")
	//
	public CalendarCellView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setSelectable(boolean isSelectable) {
		if (this.isSelectable != isSelectable) {
			this.isSelectable = isSelectable;
			refreshDrawableState();
		}
	}

	public void setCurrentMonth(boolean isCurrentMonth) {
		if (this.isCurrentMonth != isCurrentMonth) {
			this.isCurrentMonth = isCurrentMonth;
			refreshDrawableState();
		}
	}

	public void setToday(boolean isToday) {
		if (this.isToday != isToday) {
			this.isToday = isToday;
			refreshDrawableState();
		}
	}

	public void setOnlyShow(boolean isOnlyShow) {
		if (this.isOnlyShow != isOnlyShow) {
			this.isOnlyShow = isOnlyShow;
			refreshDrawableState();
		}
	}

	public void setRangeState(MonthCellDescriptor.RangeState rangeState) {
		if (this.rangeState != rangeState) {
			this.rangeState = rangeState;
			refreshDrawableState();
		}
	}

	public void setHighlighted(boolean isHighlighted) {
		if (this.isHighlighted != isHighlighted) {
			this.isHighlighted = isHighlighted;
			refreshDrawableState();
		}
	}

	public boolean isCurrentMonth() {
		return isCurrentMonth;
	}

	public boolean isToday() {
		return isToday;
	}

	public boolean isSelectable() {
		return isSelectable;
	}

	@Override
	protected int[] onCreateDrawableState(int extraSpace) {
		final int[] drawableState = super.onCreateDrawableState(extraSpace + 7);

		if (isSelectable) {
			mergeDrawableStates(drawableState, STATE_SELECTABLE);
		}

		if (isCurrentMonth) {
			mergeDrawableStates(drawableState, STATE_CURRENT_MONTH);
		}

		if (isUnSelect) {
			mergeDrawableStates(drawableState, STATE_UN_SELECT);
		}

		if (isToday) {
			mergeDrawableStates(drawableState, STATE_TODAY);
		}

		if (isOnlyShow) {
			mergeDrawableStates(drawableState, STATE_ONLY_SHOW);
		}

		if (isHighlighted) {
			mergeDrawableStates(drawableState, STATE_HIGHLIGHTED);
		}

		if (rangeState == MonthCellDescriptor.RangeState.FIRST) {
			mergeDrawableStates(drawableState, STATE_RANGE_FIRST);
		} else if (rangeState == MonthCellDescriptor.RangeState.MIDDLE) {
			mergeDrawableStates(drawableState, STATE_RANGE_MIDDLE);
		} else if (rangeState == MonthCellDescriptor.RangeState.LAST) {
			mergeDrawableStates(drawableState, STATE_RANGE_LAST);
		}

		return drawableState;
	}

	public void setDayOfMonthTextView(TextView textView) {
		dayOfMonthTextView = textView;
	}

	public TextView getDayOfMonthTextView() {
		if (dayOfMonthTextView == null) {
			throw new IllegalStateException(
					"You have to setDayOfMonthTextView in your custom DayViewAdapter.");
		}
		return dayOfMonthTextView;
	}

	public boolean isUnSelect() {
		return isUnSelect;
	}

	public void setUnSelect(boolean isUnSelect) {
		if (this.isUnSelect != isUnSelect) {
			this.isUnSelect = isUnSelect;
			refreshDrawableState();
		}
	}
}
