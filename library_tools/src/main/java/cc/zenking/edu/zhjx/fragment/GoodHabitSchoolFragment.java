package cc.zenking.edu.zhjx.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yuanhy.library_tools.activity.BaseFragment;
import com.yuanhy.library_tools.util.YCallBack;

import butterknife.BindView;
import cc.zenking.edu.zhjx.R;
import cc.zenking.edu.zhjx.enty.childEnty.Child;
import cc.zenking.edu.zhjx.enty.habitEnty.HabitType_Result;
import cc.zenking.edu.zhjx.presenter.HabitPresenter;
import cc.zenking.edu.zhjx.ui.activity.GoodHabitActivity;
import cc.zenking.edu.zhjx.utils.ZhjxAppFramentUtil;

/**
 * 好习惯 校
 */
public class GoodHabitSchoolFragment extends BaseFragment {
	@BindView(R.id.listView)
	PullToRefreshListView listView;
	GoodHabitActivity activity;
	String type;//用来区分是家还是校
	public int selectTime = 1;//枚举，1：今日，2：本周，3：上周，4：本月，5：上月
	@BindView(R.id.rl_noNet)
	RelativeLayout rl_noNet;

	@BindView(R.id.rl_load)
	RelativeLayout rl_load;
	@BindView(R.id.iv_load)
	ImageView iv_load;
	public int selectType = 1;// 2是选中家按钮 1是选择学校按钮
	Child child;
	//    public String rado_key;
	View view;

	HabitPresenter habitPresenter ;
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
				if (o instanceof HabitType_Result){
					habitType_result = (HabitType_Result) o;
				}
			}

			@Override
			public void requestFail(Object o) {

				if (o instanceof HabitType_Result){

				}
			}
		});
	}

	private void initView() {
		child = ZhjxAppFramentUtil.getSelterChild(context);
	}



}
