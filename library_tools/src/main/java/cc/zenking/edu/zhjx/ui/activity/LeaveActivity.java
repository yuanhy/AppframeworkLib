package cc.zenking.edu.zhjx.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.raizlabs.android.dbflow.list.IFlowCursorIterator;
import com.yuanhy.library_tools.activity.BaseActivity;
import com.yuanhy.library_tools.image.GlideUtil;
import com.yuanhy.library_tools.popwindows.LoadPopupWindow;
import com.yuanhy.library_tools.popwindows.ToastPopWindow;
import com.yuanhy.library_tools.presenter.BasePresenter;
import com.yuanhy.library_tools.util.YCallBack;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cc.zenking.edu.zhjx.R;

import com.yuanhy.library_tools.adapter.ListViewAdapter;

import cc.zenking.edu.zhjx.enty.AskForLeaveList;
import cc.zenking.edu.zhjx.enty.DataLeave;
import cc.zenking.edu.zhjx.enty.childEnty.Child;
import cc.zenking.edu.zhjx.popwindows.ChildPopWindows;
import cc.zenking.edu.zhjx.presenter.LeavePresenter;
import cc.zenking.edu.zhjx.utils.ZhjxAppFramentUtil;

/**
 * 请假
 */
public class LeaveActivity extends BaseActivity {
	@BindView(R.id.listview)
	PullToRefreshListView listview;
	private ArrayList<Child> childArrayList;
	private ChildPopWindows childPopWindows;
	@BindView(R.id.lins_tv)
	TextView lins_tv;
	@BindView(R.id.title_chaild_imge)
	ImageView title_chaild_imge;
	@BindView(R.id.title_chaild_tv)
	TextView title_chaild_tv;
	private String studname;
	@BindView(R.id.tag_view)
	View tag_view;
	@BindView(R.id.title_chaild_lout)
	LinearLayout title_chaild_lout;

	@BindView(R.id.left_tv)
	TextView left_tv;

	private ListViewAdapter<DataLeave> listViewAdapter;
	LeavePresenter leavePresenter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_leave);
		initUnbinder();
		initTittleBarLinearLayout(tag_view);
		left_tv .setText("请假");
		childArrayList = ZhjxAppFramentUtil.getChilds(context).objData;
		try {
			studname = ZhjxAppFramentUtil.getSelterChild(context).name;
			setTileName(studname, false);
		} catch (NullPointerException e) {
		}

		initListView();
		getdata(true);
	}

	@Override
	protected void onResume() {
		super.onResume();
//		if (askForLeaveList == null) {//只有第一次進
//			getdata(true);
//		}
	}

	AskForLeaveList askForLeaveList;

	public ArrayList<DataLeave> dataLeaveArrayList = new ArrayList<>();

	private void getdata(boolean isRefresh) {
		if (isRefresh) {
			dataLeaveArrayList.clear();
			listViewAdapter.setDataArrayList(dataLeaveArrayList);
			leavePresenter.getRefreshChildLeaveList();
		} else {
			leavePresenter.getChildLeaveList();
		}
	}

	private void initListView() {

//
		listview.setOnPullEventListener(new PullToRefreshBase.OnPullEventListener<ListView>() {
			@Override
			public void onPullEvent(PullToRefreshBase<ListView> refreshView, PullToRefreshBase.State state, PullToRefreshBase.Mode direction) {
				Log.v("TAG", "state:" + state + "   direction: " + direction);
			}
		});
		listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

				Log.v("TAG", "onPullDownToRefresh111111111");

				getdata(true);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				Log.v("TAG", "onPullUpToRefresh222222222222222222222222");
				getdata(false);
			}
		});
		listViewAdapter = new ListViewAdapter<DataLeave>(context, R.layout.list_item_leave, dataLeaveArrayList) {
			@Override
			public void convert(ViewHodle var1, DataLeave var2, int position) {
				TextView tv_time = var1.itemView.findViewById(R.id.tv_time);
				tv_time.setText(var2.createTime);

				TextView tv_type = var1.itemView.findViewById(R.id.tv_type);
				tv_type.setText(var2.type.value);

				ImageView iv_leave_icon = var1.itemView.findViewById(R.id.iv_leave_icon);
				GlideUtil.getGlideImageViewUtil().setUserIco(context, var2.picUrl, iv_leave_icon);

				TextView tv_status = var1.itemView.findViewById(R.id.tv_status);
				tv_status.setVisibility(View.VISIBLE);
				if ("APPLY_CANCEL".equals(var2.status)) {
					tv_status.setText("已撤销");
				} else if ("CONFIRM".equals(var2.status)) {
					tv_status.setText("已确认");
				} else if ("UNPROCESS".equals(var2.status)) {
					tv_status.setText("待处理");
				} else if ("AUDIT_CANCEL".equals(var2.status)) {
					tv_status.setText("审核拒绝");
				} else if ("AUDIT_PASSED".equals(var2.status)) {
					tv_status.setText("审核通过");
				} else if ("CANCEL".equals(var2.status)) {
					tv_status.setText("撤销提交");
				} else if ("UNAUDIT".equals(var2.status)) {
					tv_status.setText("待审核");
				} else {
					tv_status.setVisibility(View.INVISIBLE);
				}
			}

			@Override
			public void convertHideView(HeaderViewHodle var1, DataLeave var2, int position) {
				TextView tv_apply_leave = var1.itemView.findViewById(R.id.tv_apply_leave);
				tv_apply_leave.setOnClickListener(LeaveActivity.this);
			}

		};
		listViewAdapter.addHeaderView(R.layout.list_leave_header);
		/*设置pullToRefreshListView的刷新模式，BOTH代表支持上拉和下拉，PULL_FROM_END代表上拉,PULL_FROM_START代表下拉 */
		listview.setMode(PullToRefreshBase.Mode.BOTH);
		listview.setAdapter(listViewAdapter);
		listViewAdapter.setDataArrayList(dataLeaveArrayList);
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (position>0){
				Intent intent = new Intent(context,HolidayMsgActivity.class);
					intent.putExtra("id",
							dataLeaveArrayList.get(position - 2).id);
				startActivityForResult(intent,HolidayMsgActivity_CODE);
				}
			}
		});
	}

final 	int APPLYFORLEAVE_CODE = 0;
	final 	int HolidayMsgActivity_CODE = APPLYFORLEAVE_CODE+1;
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
			case R.id.tv_apply_leave:
				Intent intent = new Intent(context, ApplyForLeave.class);
				startActivityForResult(intent, APPLYFORLEAVE_CODE);

				ToastPopWindow.getInstance(context).setMessge("111111111111").show();
				break;
		}
	}

	@OnClick({R.id.title_chaild_tv, R.id.title_chaild_imge, R.id.title_chaild_lout, R.id.image_back})
	public void onClickViews(View v) {
		;
		switch (v.getId()) {
			case R.id.image_back:
				finish();
				break;
			case R.id.title_chaild_tv:
			case R.id.title_chaild_imge:
			case R.id.title_chaild_lout:
				childArrayList = ZhjxAppFramentUtil.getChilds(context).objData;
				if (childPopWindows == null) {
					childPopWindows = new ChildPopWindows(context, childArrayList, new YCallBack() {
						@Override
						public void onOk(Object o) {
							childPopWindows.dismiss();
							Child child = (Child) o;
							studname = child.name;
							setTileName(studname, false);
							ZhjxAppFramentUtil.upDataChilds(context, child);
							getdata(true);
						}
					});
					childPopWindows.setOnDismissListener(new PopupWindow.OnDismissListener() {
						@Override
						public void onDismiss() {
							setTileName(studname, false);
						}
					});
				}
				if (childArrayList == null || childArrayList.size() == 1) {
					break;
				}
				if (childPopWindows.isShowing()) {
					setTileName(studname, false);
					childPopWindows.dismiss();
				} else {
					setTileName(studname, true);
					childPopWindows.setChildArrayList(childArrayList);
					int yoxx = (int) title_chaild_lout.getX();
					int a = title_chaild_lout.getWidth();
					int b = title_chaild_lout.getMeasuredWidth();
					childPopWindows.showAsDropDown(title_chaild_lout, 0, 0);
				}
				break;
		}
	}

	/**
	 * 切换孩子标题的名字改变
	 *
	 * @param name
	 * @param isUp
	 */
	private void setTileName(String name, boolean isUp) {
		if (isUp) {
			title_chaild_imge.setImageResource(R.drawable.selter_up);
		} else {
			title_chaild_imge.setImageResource(R.drawable.selter_down);
		}
		if (!TextUtils.isEmpty(name)) {
			studname = name;
			title_chaild_tv.setText(name);
		}
	}

	@Override
	public void setTransparent() {

	}


	@Override
	public BasePresenter createPresenter() {
		return leavePresenter = new LeavePresenter(context, new YCallBack() {
			@Override
			public void requestSuccessful(Object o) {
				listview.onRefreshComplete();
				askForLeaveList = (AskForLeaveList) o;
				dataLeaveArrayList.addAll(askForLeaveList.data);
				listViewAdapter.setDataArrayList(dataLeaveArrayList);
			}

			@Override
			public void requestFail(Object o) {
				listview.onRefreshComplete();
			}

		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode){
			case APPLYFORLEAVE_CODE:
				if (resultCode== Activity.RESULT_OK){
					getdata(true);
				}
				break;
			case HolidayMsgActivity_CODE:
				if (resultCode== Activity.RESULT_OK){
					getdata(true);
				}
				break;
		}
	}
}
