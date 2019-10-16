package cc.zenking.edu.zhjx.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yuanhy.library_tools.activity.BaseActivity;
import com.yuanhy.library_tools.presenter.BasePresenter;
import com.yuanhy.library_tools.util.YCallBack;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cc.zenking.edu.zhjx.R;
import cc.zenking.edu.zhjx.enty.Dict;
import cc.zenking.edu.zhjx.popwindows.LeaveTypePopWindows;
import cc.zenking.edu.zhjx.presenter.LeavePresenter;
import cc.zenking.edu.zhjx.timessquare.SampleTimesSquareActivity;
import cc.zenking.edu.zhjx.utils.ZhjxAppFramentUtil;

public class ApplyForLeave extends BaseActivity {
	@BindView(R.id.tv_time_hint)
	TextView tv_time_hint;
	@BindView(R.id.tv_type)
	TextView tv_type;
	@BindView(R.id.title_name_tv)
	TextView title_name_tv;
	@BindView(R.id.title_chaild_lout)
	LinearLayout title_chaild_lout;
	@BindView(R.id.tag_view)
	View tag_view;
	@BindView(R.id.et_text)
	EditText et_text;

	ArrayList<Dict> dictArrayList = new ArrayList<>();
	private final String mPageName = "AskForLeaveActivity";
	public static final String PAGENAME = "AskForLeaveActivity";
	private ArrayList<Date> dates;
	final int REQUESTCODE_TIMES = 1;
	private List<String> dateStrings_start = new ArrayList<String>();
	private List<String> dateStrings_end = new ArrayList<String>();
	private String starttime = null;
	private String endtime = null;
	private String selecttype = SampleTimesSquareActivity.STARTEND;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apply_for_leave);
		initUnbinder();
		initTittleBarLinearLayout(tag_view);
		title_chaild_lout.setVisibility(View.GONE);

		try {
			title_name_tv.setText(ZhjxAppFramentUtil.getSelterChild(context).name);
		} catch (NullPointerException e) {
		}

		initLeaveTypePopWindows();
	}

	String leave_type_id = "";

	private void initLeaveTypePopWindows() {
		leaveTypePopWindows = new LeaveTypePopWindows(context);
		leaveTypePopWindows.setyCallBack(new YCallBack() {
			@Override
			public void onOk(Object o) {
				String name = (String) o;
				for (Dict dict : dictArrayList) {
					if (dict.value.equals(name)) {
						leave_type_id = dict.id;
						tv_type.setText(name);
						break;
					}
				}
			}
		});
		leaveTypePopWindows.setOnDismissListener(new PopupWindow.OnDismissListener() {
			@Override
			public void onDismiss() {
				leaveTypePopWindows.setBackgroundAlpha(activity, 1f);
			}
		});
	}

	private void showLeaveTypePopWindows() {
		leaveTypePopWindows.setBackgroundAlpha(activity, 0.5f);
		leaveTypePopWindows.showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
	}

	@OnClick({R.id.rl_leave_type, R.id.tv_type, R.id.iv_type, R.id.tv_time_hint, R.id.submit})
	public void viewClick(View v) {
		switch (v.getId()) {
			case R.id.rl_leave_type:
			case R.id.tv_type:
			case R.id.iv_type:
				if (dictArrayList.size() == 0) {
					leavePresenter.getAskForLeaveDict();
				} else {
					showLeaveTypePopWindows();
				}
				break;
			case R.id.tv_time_hint:
				tv_start_time();
				break;
			case R.id.submit:
				addLeave();
				break;
		}
	}

	void tv_start_time() {
		Intent intent = new Intent(this, SampleTimesSquareActivity.class);
		// 此时还要传入原来已经选择的日期
		if (dates == null) {
			dates = new ArrayList<Date>();
		}
		// 当用户选完时间但是想修改已经选中的时间的时候传入已经选中的日期用于回显
		intent.putExtra("selected_dates", (Serializable) dates);
		intent.putExtra("selecttype", selecttype);
		intent.putExtra("type", "multi");
		// 传入选择的开始时间和结束时间
		intent.putExtra("starttime", starttime);
		intent.putExtra("endtime", endtime);
		intent.putExtra("page", "AskForLeaveActivity");
		startActivityForResult(intent, REQUESTCODE_TIMES);
	}

	/**
	 * 添加请假
	 */
	public void addLeave() {
		if(TextUtils.isEmpty(leave_type_id)){
			toastPopWindow.setMessge("请选择请假类型").show();
			return;
		}
		if (dateStrings_start.size()==0||dateStrings_end.size()==0){
			toastPopWindow.setMessge("请选择请假时间").show();
			return;
		}
		if(TextUtils.isEmpty(et_text.getText().toString())){
			toastPopWindow.setMessge("请输入请假详情").show();
			return;
		}
		loadPopupWindow.showPopWindowCenter();
		leavePresenter.add_Askforleave(leave_type_id, et_text.getText().toString(), dateStrings_start, dateStrings_end);
	}

	@Override
	public void setTransparent() {
		setTransparent(true);
	}

	private LeaveTypePopWindows leaveTypePopWindows;
	LeavePresenter leavePresenter;

	@Override
	public BasePresenter createPresenter() {
		return leavePresenter = new LeavePresenter(this, new YCallBack() {

			@Override
			public void requestSuccessful(Object o) {
				if (o instanceof ArrayList) {
					dictArrayList = (ArrayList<Dict>) o;
					showLeaveTypePopWindows();
				} else if (o instanceof String) {
					loadPopupWindow.dismiss();
					setResult(Activity.RESULT_OK);
					finish();
				}

			}

			@Override
			public void requestFail(Object o) {
				if (o instanceof Dict) {
					toastPopWindow.setMessge("获取请假信息失败").show();
				} else if (o instanceof String) {
					loadPopupWindow.dismiss();
				}

			}
		});
	}

	/**
	 * 清楚原有的集合数据
	 */
	void clearTimeData() {
		dateStrings_start.clear();
		dateStrings_end.clear();
	}

	/**
	 * 格式化回显日期
	 */
	void setTextTime() {
		String startDateString = dateToString(dates.get(0), "yyyy-MM-dd");
		String msgString = startDateString + " " + starttime + " 起";
		tv_time_hint.setText(msgString);
	}

	private String dateToString(Date date, String dateandtime) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateandtime);
		String str = sdf.format(date);
		return str;
	}

	/**
	 * // 当用户的时间选择为多天 整天的时候为后台传送的数据参数格式为 每一天0点到每一天24点
	 */
	void formatTime() {

		for (int i = 0; i < dates.size(); i++) {
			String dateString = dateToString(dates.get(i), "yyyy-MM-dd");
			if (i == 0) {
				dateStrings_start.add(dateString + " " + starttime + ":00");
				dateStrings_end.add(dateString + " 23:59:59");
			} else if (i == dates.size() - 1) {
				dateStrings_start.add(dateString + " 00:00:00");
				dateStrings_end.add(dateString + " " + endtime + ":00");
			} else {
				dateStrings_start.add(dateString + " 00:00:00");
				dateStrings_end.add(dateString + " 23:59:59");
			}
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int responseCode,
	                                Intent intent) {
		if (requestCode == REQUESTCODE_TIMES
				&& responseCode == SampleTimesSquareActivity.SAMPLETIMESSQUARERESULTCODE) {
			// 家长请假多选日期
			ArrayList<Date> dates = null;
			if (intent != null) {
				// 接收日期界面返回值，用于用户修改已经选完日期后 再次修改选的的日期的回显
				dates = (ArrayList<Date>) intent.getSerializableExtra("dates");
				selecttype = intent.getStringExtra("selecttype");
				starttime = intent.getStringExtra("starttime");
				endtime = intent.getStringExtra("endtime");
			}
			if (dates != null && dates.size() != 0) {
				this.dates = dates;
				// 先清空一下历史数据
				clearTimeData();
				// 当请假日期以整天为单位的时候
				if (SampleTimesSquareActivity.STARTEND.equals(selecttype)) {
					// 当用户只选择一天的时候 显示为今天0点到今天24点
					if (dates.size() == 1) {
						String dateString = dateToString(dates.get(0),
								"yyyy-MM-dd");
						dateStrings_start
								.add(dateString + " " + starttime + ":00");
						dateStrings_end.add(dateString + " " + endtime + ":00");
					} else {
						formatTime();
					}
					// 用户选择的请假日期为 一天中的时间段时
				} else if (SampleTimesSquareActivity.QUANTUM
						.equals(selecttype)) {
					for (int i = 0; i < dates.size(); i++) {
						String dateString = dateToString(dates.get(i),
								"yyyy-MM-dd");
						dateStrings_start
								.add(dateString + " " + starttime + ":00");
						dateStrings_end.add(dateString + " " + endtime + ":00");
					}
				}
				// 在app端提示用户已经选择的请假时间的显示
				if (dates.size() == 1) {
					setTextTime();
				} else {
					if (SampleTimesSquareActivity.STARTEND.equals(selecttype)
							|| SampleTimesSquareActivity.QUANTUM
							.equals(selecttype)) {
						// TODO 开始结束时间
						setTextTime();
					} else {
						this.dates = null;
						clearTimeData();
					}
				}
			} else {
				this.dates = null;
				clearTimeData();
				tv_time_hint.setText("请选择时间");
			}
		}
		super.onActivityResult(requestCode, responseCode, intent);
	}
}
