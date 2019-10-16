package cc.zenking.edu.zhjx.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.yuanhy.library_tools.activity.BaseFragment;
import com.yuanhy.library_tools.image.GlideUtil;
import com.yuanhy.library_tools.modular.rotary_planting_map.RotaryPlantingMapView;
import com.yuanhy.library_tools.util.ApiObsoleteUtil;
import com.yuanhy.library_tools.util.SystemUtile;
import com.yuanhy.library_tools.util.YCallBack;
import com.yuanhy.library_tools.view.RewriteGridViewHeight;
import com.yuanhy.library_tools.view.RewriteListViewHeight;
import com.yuanhy.library_tools.view.RewriteViewPageHeight;

import java.util.ArrayList;

import butterknife.BindView;
import cc.zenking.edu.zhjx.R;
import cc.zenking.edu.zhjx.adapter.HomeMenuViewPageAdapter;
import cc.zenking.edu.zhjx.adapter.HomeMeunViewPageGridViewAdapter;
import cc.zenking.edu.zhjx.enty.homeEnty.Menu;
import cc.zenking.edu.zhjx.enty.homeEnty.MenuList;
import cc.zenking.edu.zhjx.enty.homeEnty.NewsListResultEnty;
import cc.zenking.edu.zhjx.enty.homeEnty.RotaryPlantingEnty;
import cc.zenking.edu.zhjx.presenter.HomeFragmentPresenter;
import cc.zenking.edu.zhjx.ui.activity.WebViewUtilActivity;

public class HomeFragment extends BaseFragment implements View.OnClickListener {

	View view;
	RotaryPlantingMapView<RotaryPlantingEnty> rotaryPlantingMapView;

	HomeFragmentPresenter homeFragmentPresenter;
	ArrayList<RotaryPlantingEnty> rotaryPlantingEntyArrayList = new ArrayList<>();

	@BindView(R.id.rotary_planting_viewpage)
	ViewPager rotary_planting_viewpage;
	@BindView(R.id.state_rdgroup)
	RadioGroup state_rdgroup;

	@BindView(R.id.rotary_planting_name_tv)
	TextView rotary_planting_name_tv;

	@BindView(R.id.menuList_viewpage)
	ViewPager menuList_viewpage;

	private HomeMenuViewPageAdapter homeMenuViewPageAdapter;


	@BindView(R.id.title_home)
	TextView title_home;


	@BindView(R.id.menuList_radiogroup)
	RadioGroup menuList_radiogroup;

	@BindView(R.id.iv_icon)
	ImageView iv_icon;
	@BindView(R.id.tv_name)
	TextView tv_name;

	@BindView(R.id.rl_ad)
	RelativeLayout rl_ad;

	/**
	 * 菜单列表数据
	 */
	ArrayList<Menu> menuArrayList = new ArrayList<>();

	public HomeFragment() {
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_home, container, false);
		}

		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		initRotaryPlantingMap();
		initMenuView();
		initPresenter();
		getData();
	}

	private void initView() {
		iv_icon.setOnClickListener(this);
		tv_name.setOnClickListener(this);
		rl_ad.setOnClickListener(this);
	}

	private void initRotaryPlantingMap() {
		if (rotaryPlantingMapView == null) {
			rotaryPlantingMapView = new RotaryPlantingMapView<RotaryPlantingEnty>(context, rotary_planting_viewpage,
					3, state_rdgroup, rotaryPlantingEntyArrayList) {
				@Override
				public void onImageView(ImageView imageView, RotaryPlantingEnty enty) {
//				GlideUtil.getGlideImageViewUtil().setImageView(context, enty.getHeadPortrait(), imageView);
					GlideUtil.getGlideImageViewUtil().setImageTransform(context, enty.getHeadPortrait(), imageView, 10,
							false, false, false, false);

				}

				@Override
				public void onItemView(ViewPager rotary_planting_viewpage, RadioGroup state_rdgroup, int rotaryPlantingIndex) {
					rotary_planting_name_tv.setText(rotaryPlantingEntyArrayList.get(rotaryPlantingIndex).getTitle());
				}

				@Override
				public void onClickView(int rotaryPlantingIndex) {
					WebViewUtilActivity.openWebViewUtilActivity(context, rotaryPlantingEntyArrayList.get(rotaryPlantingIndex).getUrl(),
							R.drawable.title_back_black,rotaryPlantingEntyArrayList.get(rotaryPlantingIndex).title,true,true);
				}
			};
			rotaryPlantingMapView.setDatas(rotaryPlantingEntyArrayList);
		}

	}

	private void initPresenter() {
		homeFragmentPresenter = new HomeFragmentPresenter(context, new YCallBack() {
			@Override
			public void requestSuccessful(Object o) {
				if (o instanceof NewsListResultEnty) {
					NewsListResultEnty newsListResultEnty = (NewsListResultEnty) o;

					if (newsListResultEnty.type == 0) {
						rotaryPlantingEntyArrayList.clear();
						if (newsListResultEnty.data != null && newsListResultEnty.data.getData() != null) {
							for (RotaryPlantingEnty rotaryPlantingEnty : newsListResultEnty.data.getData()) {
								rotaryPlantingEntyArrayList.add(rotaryPlantingEnty);
							}
						}
						rotaryPlantingMapView.setDatas(rotaryPlantingEntyArrayList);
					} else {
						if (newsListResultEnty.data != null && newsListResultEnty.data.getData() != null && newsListResultEnty.data.getData().length > 0) {
							initRecommendView(newsListResultEnty.data.getData()[0]);
						}
					}

				} else if (o instanceof MenuList) {
					MenuList menuList = (MenuList) o;
					menuArrayList.clear();
					menuArrayList.addAll(menuList.data);
					setMeunData();
				}
			}

			@Override
			public void onError(Object o) {
				if (o instanceof NewsListResultEnty) {
					rotaryPlantingEntyArrayList.clear();
					rotaryPlantingMapView.setDatas(rotaryPlantingEntyArrayList);
				} else if (o instanceof MenuList) {

				}
			}
		});
	}

	ArrayList<View> menuViews = new ArrayList<>();

	private void initMenuView() {


		homeMenuViewPageAdapter = new HomeMenuViewPageAdapter();
		homeMenuViewPageAdapter.setmViewList(menuViews);
		menuList_viewpage.setAdapter(homeMenuViewPageAdapter);
		menuList_viewpage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				RadioButton radioButton = (RadioButton) menuList_radiogroup.getChildAt(position);
				radioButton.setChecked(true);
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
		setMeunData();
	}

	private void addRad(int id) {
		RadioButton radio_bt = new RadioButton(context);
		RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(SystemUtile.dp2px(8), SystemUtile.dp2px(8));
		layoutParams.leftMargin = SystemUtile.dp2px(3);
		layoutParams.rightMargin = SystemUtile.dp2px(3);
		radio_bt.setLayoutParams(layoutParams);
		ApiObsoleteUtil.setBackground(radio_bt, context, R.drawable.menu_rb_state_bj);
		radio_bt.setButtonDrawable(null);
		menuList_radiogroup.addView(radio_bt, id);
	}

	private void setMeunData() {
		menuViews.clear();

		menuList_radiogroup.removeAllViews();
		LayoutInflater inflater = getLayoutInflater();
		if (menuArrayList.size() > 12) {

			ArrayList<Menu> menuArrayListThem0 = new ArrayList<>();
			for (int i = 0; i < 12; i++) {
				menuArrayListThem0.add(menuArrayList.get(i));
			}
			RewriteGridViewHeight gridview_item = (RewriteGridViewHeight) inflater.inflate(R.layout.menu_gridview_item, menuList_viewpage, false);
			GridView menu_gridview = gridview_item.findViewById(R.id.menu_gridview);
			HomeMeunViewPageGridViewAdapter homeMeunViewPageGridViewAdapter = new HomeMeunViewPageGridViewAdapter(context, menuArrayListThem0);
			menu_gridview.setAdapter(homeMeunViewPageGridViewAdapter);
			menuViews.add(gridview_item);

			addRad(0);
			ArrayList<Menu> menuArrayListThem = new ArrayList<>();
			for (int i = 12; i < menuArrayList.size(); i++) {
				menuArrayListThem.add(menuArrayList.get(i));
			}
			RewriteGridViewHeight gridview_item2 = (RewriteGridViewHeight) inflater.inflate(R.layout.menu_gridview_item, menuList_viewpage, false);
			GridView menu_gridview2 = gridview_item2.findViewById(R.id.menu_gridview);
			HomeMeunViewPageGridViewAdapter homeMeunViewPageGridViewAdapter2 = new HomeMeunViewPageGridViewAdapter(context, menuArrayListThem);
			menu_gridview2.setAdapter(homeMeunViewPageGridViewAdapter2);
			menuViews.add(gridview_item2);

			addRad(1);

		} else if (menuArrayList.size() > 0 && menuArrayList.size() <= 12) {
			RewriteGridViewHeight gridview_item = (RewriteGridViewHeight) inflater.inflate(R.layout.menu_gridview_item, menuList_viewpage, false);
//			RewriteGridViewHeight menu_gridview = gridview_item.findViewById(R.id.menu_gridview);
			HomeMeunViewPageGridViewAdapter homeMeunViewPageGridViewAdapter = new HomeMeunViewPageGridViewAdapter(context, menuArrayList);
			gridview_item.setAdapter(homeMeunViewPageGridViewAdapter);
			menuViews.add(gridview_item);
			addRad(0);
		}

		homeMenuViewPageAdapter.setmViewList(menuViews);
		homeMenuViewPageAdapter.notifyDataSetChanged();
		if (menuViews.size() > 0) {
			//设置viewPager的初始界面为第一个界面
			menuList_viewpage.setCurrentItem(0);
			RadioButton radioButton = (RadioButton) menuList_radiogroup.getChildAt(0);
			radioButton.setChecked(true);
		}

	}


	RotaryPlantingEnty rotaryPlantingEnty;

	private void initRecommendView(RotaryPlantingEnty rotaryPlantingEnty) {
		this.rotaryPlantingEnty = rotaryPlantingEnty;
		tv_name.setText(rotaryPlantingEnty.getTitle());
		GlideUtil.getGlideImageViewUtil().setImageView(context, rotaryPlantingEnty.headPortrait, iv_icon);

	}


	private void getData() {
		homeFragmentPresenter.intervalWork();
	}

	@Override
	public void onDestroy() {
		rotaryPlantingMapView.onCloss();
		super.onDestroy();
	}

	@Override
	public void onClick(View view) {
		WebViewUtilActivity.openWebViewUtilActivity(context, rotaryPlantingEnty.getUrl(),R.drawable.title_back_black,rotaryPlantingEnty.title,true,false);
	}

}
