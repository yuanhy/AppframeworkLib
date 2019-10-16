// Copyright 2012 Square, Inc.
package cc.zenking.edu.zhjx.timessquare;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cc.zenking.edu.zhjx.R;


public class MonthView extends LinearLayout {
	TextView title, tv_title;
	CalendarGridView grid;
	private Listener listener;
	private List<CalendarCellDecorator> decorators;
	private boolean isRtl;
	private Locale locale;

	public static MonthView create(ViewGroup parent, LayoutInflater inflater,
	                               DateFormat weekdayNameFormat, Listener listener, Calendar today,
	                               int dividerColor, int dayBackgroundResId, int dayTextColorResId,
	                               int titleTextColor, boolean displayHeader, int headerTextColor,
	                               Locale locale, DayViewAdapter adapter) {
		return create(parent, inflater, weekdayNameFormat, listener, today,
				dividerColor, dayBackgroundResId, dayTextColorResId,
				titleTextColor, displayHeader, headerTextColor, null, locale,
				adapter);
	}

	public static MonthView create(ViewGroup parent, LayoutInflater inflater,
	                               DateFormat weekdayNameFormat, Listener listener, Calendar today,
	                               int dividerColor, int dayBackgroundResId, int dayTextColorResId,
	                               int titleTextColor, boolean displayHeader, int headerTextColor,
	                               List<CalendarCellDecorator> decorators, Locale locale,
	                               DayViewAdapter adapter) {
		final MonthView view = (MonthView) inflater.inflate(R.layout.month,
				parent, false);
		view.setDayViewAdapter(adapter);
		view.setDividerColor(dividerColor);
		view.setDayTextColor(dayTextColorResId);
		view.setTitleTextColor(titleTextColor);
		view.setDisplayHeader(displayHeader);
		view.setHeaderTextColor(headerTextColor);

		if (dayBackgroundResId != 0) {
			view.setDayBackground(dayBackgroundResId);
		}

		final int originalDayOfWeek = today.get(Calendar.DAY_OF_WEEK);

		view.isRtl = isRtl(locale);
		view.locale = locale;
		int firstDayOfWeek = today.getFirstDayOfWeek();
		final CalendarRowView headerRow = (CalendarRowView) view.grid
				.getChildAt(0);
		for (int offset = 0; offset < 7; offset++) {
			today.set(Calendar.DAY_OF_WEEK,
					getDayOfWeek(firstDayOfWeek, offset, view.isRtl));
			final TextView textView = (TextView) headerRow.getChildAt(offset);
			// TODO 更改周一为一，周二为二，周日为日
			if (weekdayNameFormat.format(today.getTime()).contains("周")) {
				textView.setText(weekdayNameFormat.format(today.getTime())
						.replace("周", ""));
			} else if (weekdayNameFormat.format(today.getTime()).contains("星期")) {
				textView.setText(weekdayNameFormat.format(today.getTime())
						.replace("星期", ""));
			}
			textView.setTextColor(Color.parseColor("#1b1b1b"));
			// 调用百分比布局的根据TextView的像素值计算实际的像素值
			int pxVal= parent.getContext().getResources().getDimensionPixelSize(R.dimen.sp_16);
			textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, pxVal);
		}
		today.set(Calendar.DAY_OF_WEEK, originalDayOfWeek);
		view.listener = listener;
		view.decorators = decorators;
		return view;
	}

	private static int getDayOfWeek(int firstDayOfWeek, int offset,
			boolean isRtl) {
		int dayOfWeek = firstDayOfWeek + offset;
		if (isRtl) {
			return 8 - dayOfWeek;
		}
		return dayOfWeek;
	}

	private static boolean isRtl(Locale locale) {
		final int directionality = Character.getDirectionality(locale
				.getDisplayName(locale).charAt(0));
		return directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT
				|| directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC;
	}

	public MonthView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setDecorators(List<CalendarCellDecorator> decorators) {
		this.decorators = decorators;
	}

	public List<CalendarCellDecorator> getDecorators() {
		return decorators;
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		title = (TextView) findViewById(R.id.title);
		grid = (CalendarGridView) findViewById(R.id.calendar_grid);
		tv_title = (TextView) findViewById(R.id.tv_title);
	}

	public void init(MonthDescriptor month,
	                 List<List<MonthCellDescriptor>> cells, boolean displayOnly,
	                 Typeface titleTypeface, Typeface dateTypeface,
	                 Map<Integer, List<Date>> dates_private, boolean onlyShow) {
		Logr.d("Initializing MonthView (%d) for %s",
				System.identityHashCode(this), month);
		long start = System.currentTimeMillis();
		// TODO 更改年份月份显示
		title.setText(month.getYear() + "-" + (month.getMonth() + 1));
		tv_title.setText(month.getYear() + "-" + (month.getMonth() + 1));
		NumberFormat numberFormatter = NumberFormat.getInstance(locale);

		final int numRows = cells.size();
		grid.setNumRows(numRows);
		for (int i = 0; i < 6; i++) {
			CalendarRowView weekRow = (CalendarRowView) grid.getChildAt(i + 1);
			weekRow.setListener(listener);
			if (i < numRows) {
				weekRow.setVisibility(VISIBLE);
				List<MonthCellDescriptor> week = cells.get(i);
				for (int c = 0; c < week.size(); c++) {
					MonthCellDescriptor cell = week.get(isRtl ? 6 - c : c);
					CalendarCellView cellView = (CalendarCellView) weekRow
							.getChildAt(c);
					String cellDate = numberFormatter.format(cell.getValue());
					cellView.setToday(cell.isToday());
					if (cell.isToday()) {
                        cellView.getDayOfMonthTextView().setText("今");
                    } else {
						cellView.getDayOfMonthTextView().setText(cellDate);
					}
					cellView.setUnSelect(false);
					cellView.setEnabled(cell.isCurrentMonth());
					cellView.setClickable(!displayOnly);
					if (cell.isCurrentMonth()) {
						// TODO 模拟不可点击的日期
						if (dates_private != null
								&& dates_private
										.containsKey(month.getMonth() + 1)) {
							List<Date> dates = dates_private.get(month
									.getMonth() + 1);
							for (Date date : dates) {
								int day = date.getDate();
								if (String.valueOf(day).equals(cellDate)) {
									cellView.setToday(false);
									cellView.setUnSelect(true);
									cellView.setClickable(false);
									cellView.setEnabled(false);
								}
							}
						} else {
							cellView.setUnSelect(false);
							cellView.setEnabled(cell.isCurrentMonth());
							cellView.setClickable(!displayOnly);
						}
					}
					if (onlyShow) {
						cellView.setToday(false);
						cellView.setOnlyShow(true);
						cellView.setClickable(false);
						cellView.setEnabled(false);
					}
					cellView.setSelectable(cell.isSelectable());
					cellView.setSelected(cell.isSelected());
					cellView.setCurrentMonth(cell.isCurrentMonth());
					// cellView.setToday(cell.isToday());暂时不需要显示是否是今天
					cellView.setRangeState(cell.getRangeState());
					cellView.setHighlighted(cell.isHighlighted());
					cellView.setTag(cell);

					if (null != decorators) {
						for (CalendarCellDecorator decorator : decorators) {
							decorator.decorate(cellView, cell.getDate());
						}
					}
				}
			} else {
				weekRow.setVisibility(GONE);
			}
		}

		if (titleTypeface != null) {
			title.setTypeface(titleTypeface);
		}
		if (dateTypeface != null) {
			grid.setTypeface(dateTypeface);
		}

		Logr.d("MonthView.init took %d ms", System.currentTimeMillis() - start);
	}

	public void setDividerColor(int color) {
		grid.setDividerColor(color);
	}

	public void setDayBackground(int resId) {
		grid.setDayBackground(resId);
	}

	public void setDayTextColor(int resId) {
		grid.setDayTextColor(resId);
	}

	public void setDayViewAdapter(DayViewAdapter adapter) {
		grid.setDayViewAdapter(adapter);
	}

	public void setTitleTextColor(int color) {
		title.setTextColor(color);
	}

	public void setDisplayHeader(boolean displayHeader) {
		grid.setDisplayHeader(displayHeader);
	}

	public void setHeaderTextColor(int color) {
		grid.setHeaderTextColor(color);
	}

	public interface Listener {
		void handleClick(MonthCellDescriptor cell);
	}
}
