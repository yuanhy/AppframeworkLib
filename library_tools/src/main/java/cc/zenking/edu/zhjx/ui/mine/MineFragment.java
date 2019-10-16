package cc.zenking.edu.zhjx.ui.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yuanhy.library_tools.activity.BaseFragment;
import com.yuanhy.library_tools.image.GlideUtil;
import com.yuanhy.library_tools.util.YCallBack;
import com.yuanhy.library_tools.view.RewriteListViewHeight;

import java.util.ArrayList;

import butterknife.BindView;
import cc.zenking.edu.zhjx.R;
import cc.zenking.edu.zhjx.db.dbenty.UserEnty;
import cc.zenking.edu.zhjx.enty.childEnty.Child;
import cc.zenking.edu.zhjx.presenter.ChildPresenter;
import cc.zenking.edu.zhjx.utils.ZhjxAppFramentUtil;

public class MineFragment extends BaseFragment {
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	public RewriteListViewHeight MLPersonListView;
    private ChildPresenter childPresenter;
    public  MLAdapter adapter;

	public MineFragment() {
	}

	public static MineFragment newInstance(String param1, String param2) {
		MineFragment fragment = new MineFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}


	}

	public View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_mine, container, false);
		}
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		configureHeadView();
		configure();
		initPresenter();
		getAppFunction();

	}

	private void configureHeadView(){
		UserEnty userEnty = ZhjxAppFramentUtil.getUserInfo();
		ImageView iconView = view.findViewById(R.id.iconImage);
		TextView iconName = view.findViewById(R.id.iconName);
		ImageView collectImageView = view.findViewById(R.id.collectIcon);
		ImageView settingImageView = view.findViewById(R.id.settingIcon);
		ImageView feedBackImageView = view.findViewById(R.id.feedbackIcon);

		GlideUtil.getGlideImageViewUtil().setUserIco(getContext(),userEnty.personpic,iconView);
		iconName.setText(userEnty.username);
		collectImageView.setImageResource(R.drawable.mycollectors);
		settingImageView.setImageResource(R.drawable.setting);
		feedBackImageView.setImageResource(R.drawable.feedback2);
	}


	public void configure() {
		MLPersonListView = (RewriteListViewHeight) view.findViewById(R.id.MLPersonListView);
		adapter = new MLAdapter(getContext());
		MLPersonListView.setAdapter(adapter);
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}


	private void initPresenter() {

		childPresenter = new ChildPresenter(context, new YCallBack() {
			@Override
			public void requestSuccessful(Object o) {
				ArrayList<Child> childArrayList = (ArrayList<Child>) o;
				adapter.setDataSourse(childArrayList);

			}

			@Override
			public void onError(Object o) {

			}
		});
	}

	private void getAppFunction() {
		childPresenter.getBindStudentList();
	}

}
