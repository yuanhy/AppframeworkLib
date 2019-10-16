package cc.zenking.edu.zhjx.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.AnyThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yuanhy.library_tools.activity.BaseFragment;
import com.yuanhy.library_tools.adapter.ListViewAdapter;
import com.yuanhy.library_tools.file.FileDownUtile;
import com.yuanhy.library_tools.http.ProgressListener;
import com.yuanhy.library_tools.image.GlideUtil;
import com.yuanhy.library_tools.popwindows.ToastPopWindow;
import com.yuanhy.library_tools.util.SystemUtile;
import com.yuanhy.library_tools.util.YCallBack;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import butterknife.BindView;
import cc.zenking.edu.zhjx.R;
import cc.zenking.edu.zhjx.enty.childEnty.Child;
import cc.zenking.edu.zhjx.enty.habitEnty.HabitCount;
import cc.zenking.edu.zhjx.enty.habitEnty.HabitType;
import cc.zenking.edu.zhjx.enty.habitEnty.HabitType_Result;
import cc.zenking.edu.zhjx.enty.homeEnty.Data;
import cc.zenking.edu.zhjx.presenter.HabitPresenter;
import cc.zenking.edu.zhjx.ui.activity.AddGoodHabitResultActivity;
import cc.zenking.edu.zhjx.ui.activity.GoodHabitActivity;
import cc.zenking.edu.zhjx.ui.home.HomeFragment;
import cc.zenking.edu.zhjx.ui.messge.MessgeFragment;
import cc.zenking.edu.zhjx.ui.mine.MineFragment;
import cc.zenking.edu.zhjx.utils.CachUtile;
import cc.zenking.edu.zhjx.utils.ZhjxAppFramentUtil;
import cc.zenking.edu.zhjx.view.RadoView;
import cc.zenking.edu.zhjx.view.ShowPicViewPager;

/**
 * 好习惯 家
 */
public class GoodHabitFragment extends BaseFragment {
	@BindView(R.id.listView)
	PullToRefreshListView listView;
	String type;//用来区分是家还是校
	public int selectTime = 1;//枚举，1：今日，2：本周，3：上周，4：本月，5：上月
	@BindView(R.id.rl_noNet)
	RelativeLayout rl_noNet;

	@BindView(R.id.rl_load)
	RelativeLayout rl_load;
	@BindView(R.id.iv_load)
	ImageView iv_load;
	public int selectType = 2;// 2是选中家按钮 1是选择学校按钮
	Child child;
	//    public String rado_key;
	View view;
	HabitPresenter habitPresenter;
	private ListViewAdapter<HabitType> listViewAdapter;
	private ArrayList<HabitType> habitTypeArrayList = new ArrayList<>();
	private PopupWindow pop_add;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_goodhabit, container, false);
		}
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		initPresenter();
		getData();
	}

	private void getData() {
		habitPresenter.getBindStudentList();
	}

	/**
	 * 好习惯类型列表
	 */
	HabitType_Result habitType_result;

	private void initPresenter() {
		habitPresenter = new HabitPresenter(context, new YCallBack() {
			@Override
			public void requestSuccessful(Object o) {
				if (o instanceof HabitType_Result) {
					habitType_result = (HabitType_Result) o;
				}
			}

			@Override
			public void requestFail(Object o) {

				if (o instanceof HabitType_Result) {

				}
			}
		});
	}

	private void initView() {
		child = ZhjxAppFramentUtil.getSelterChild(context);
		initListView();
	}

	private void initListView() {
		listViewAdapter = new ListViewAdapter<HabitType>(context, R.layout.list_item_leave, habitTypeArrayList) {
			@Override
			public void convert(ViewHodle var1, HabitType var2, int position) {

			}

			@Override
			public void convertHideView(HeaderViewHodle var1, HabitType var2, int position) {
				TextView tv_add_goodhabit = var1.itemView.findViewById(R.id.tv_add_goodhabit);
				tv_add_goodhabit.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						initPopAdd();
					}
				});
			}
		};

		listViewAdapter.addHeaderView(R.layout.list_goodhabit_header);

		listView.setAdapter(listViewAdapter);
	}

	//初始化添加好习惯弹出框
	void initPopAdd() {
		if (pop_add == null) {
			View view = LayoutInflater.from(getActivity())
					.inflate(R.layout.popwindow_goodhabit_add, null);
			GridView gridView = (GridView) view.findViewById(R.id.gridView);
			ListViewAdapter<HabitType> habitTypeListViewAdapter = new ListViewAdapter<HabitType>(context, R.layout.grid_item_add_habit_type, habitType_result.data) {
				@Override
				public void convert(ViewHodle var1, HabitType var2, int position) {
					ImageView iv_habit_icon = var1.itemView.findViewById(R.id.iv_habit_icon);
					TextView tv_habit_name = var1.itemView.findViewById(R.id.tv_habit_name);
					GlideUtil.getGlideImageViewUtil().setUserIco(context, var2.icon, iv_habit_icon);
					tv_habit_name.setText(var2.name);
				}

				@Override
				public void convertHideView(HeaderViewHodle var1, HabitType var2, int position) {

				}
			};
			gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					HabitType habitType = ((HabitType) parent.getAdapter().getItem(position));
					Intent intent = new Intent(getActivity(), AddGoodHabitResultActivity.class);
//				intent.putExtra("studentId", activity.child.studentId);
//				intent.putExtra("classifyId", habitType.classifyId + "");
//				intent.putExtra("id", habitType.id + "");
//				intent.putExtra("schoolId", activity.child.schoolId);
//				intent.putExtra("studentName", activity.child.name);HabitType
					intent .putExtra("habitType",habitType);
					startActivityForResult(intent, AddGoodHabitResultActivity_code);
					getActivity().overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.fake_anim);
					pop_add.dismiss();
				}
			});
			gridView.setAdapter(habitTypeListViewAdapter);
			ImageView iv_close_add = (ImageView) view.findViewById(R.id.iv_close_add);
			TextView tv_hint_title_add = (TextView) view.findViewById(R.id.tv_hint_title_add);
			iv_close_add.setOnClickListener(v -> pop_add.dismiss());
			tv_hint_title_add.setText(child.name);
			pop_add = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT);
			pop_add.setBackgroundDrawable(new BitmapDrawable());
			pop_add.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
			pop_add.setOutsideTouchable(true);
			pop_add.setClippingEnabled(false);
			pop_add.setFocusable(true);
			pop_add.setTouchable(true);
			pop_add.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.CENTER, 0, 0);
		} else {
			pop_add.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.CENTER, 0, 0);
		}
	}

	final int AddGoodHabitResultActivity_code = 666;

	@Override
	public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		ZhjxAppFramentUtil.logCatUtil.i("onActivityResult:");
	}
}
