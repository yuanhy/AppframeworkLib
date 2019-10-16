package cc.zenking.edu.zhjx.timessquare;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

import cc.zenking.edu.zhjx.MainActivity;
import cc.zenking.edu.zhjx.R;


public class DefaultDayViewAdapter implements DayViewAdapter {
	@Override
	public void makeCellView(CalendarCellView parent) {
		// TextView textView = new TextView(new ContextThemeWrapper(
		// parent.getContext(), R.style.CalendarCell_CalendarDate));
		// 上边的方式有加粗效果，换成下边两行
		TextView textView = new TextView(parent.getContext());
		textView.setGravity(Gravity.CENTER);
		textView.setDuplicateParentStateEnabled(true);
		int width = 0;
		if (SampleTimesSquareActivity.SCREENWIDTH != 0) {
			width = SampleTimesSquareActivity.SCREENWIDTH;
		} else {
			WindowManager wm = (WindowManager) parent.getContext()
					.getSystemService(Context.WINDOW_SERVICE);
			width = wm.getDefaultDisplay().getWidth();
		}
		textView.setPadding(0, 0, width / 49, width / 49);
		// 调用百分比布局的根据TextView的像素值计算实际的像素值
		int sizi= parent.getContext().getResources().getDimensionPixelSize(R.dimen.sp_16);
		textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizi);
		parent.addView(textView);
		parent.setDayOfMonthTextView(textView);
	}
}
