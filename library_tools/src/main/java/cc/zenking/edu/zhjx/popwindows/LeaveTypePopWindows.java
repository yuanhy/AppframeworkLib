package cc.zenking.edu.zhjx.popwindows;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuanhy.library_tools.popwindows.BasePopWindows;

import cc.zenking.edu.zhjx.R;

public class LeaveTypePopWindows extends BasePopWindows {
	RelativeLayout main_lout;
	TextView tv_leave_type1;
	TextView tv_leave_type2;
	TextView tv_leave_type3;

	public LeaveTypePopWindows(Context content) {
		super(content);
		setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
		setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
		main_lout = view.findViewById(R.id.main_lout);
		main_lout.setOnClickListener(this);

		tv_leave_type1 = view.findViewById(R.id.tv_leave_type1);
		tv_leave_type1.setOnClickListener(this);

		tv_leave_type2 = view.findViewById(R.id.tv_leave_type2);
		tv_leave_type2.setOnClickListener(this);

		tv_leave_type3 = view.findViewById(R.id.tv_leave_type3);
		tv_leave_type3.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
//			case R.id.main_lout:
//				break;
			case R.id.tv_leave_type1:
				yCallBack.onOk("事假");
				break;
			case R.id.tv_leave_type2:
				yCallBack.onOk("病假");
				break;

			case R.id.tv_leave_type3:
				yCallBack.onOk("其它");
				break;
		}
		dismiss();
	}

	@Override
	public int getLoutResourceId() {
		return R.layout.popwindow_leave_type;
	}
}
