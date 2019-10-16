package cc.zenking.edu.zhjx.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuanhy.library_tools.image.GlideUtil;

import java.util.ArrayList;

import cc.zenking.edu.zhjx.R;
import cc.zenking.edu.zhjx.api.AppUrl;
import cc.zenking.edu.zhjx.api.InterfaceAddress;
import cc.zenking.edu.zhjx.enty.homeEnty.Menu;
import cc.zenking.edu.zhjx.ui.activity.GoodHabitActivity;
import cc.zenking.edu.zhjx.ui.activity.LeaveActivity;
import cc.zenking.edu.zhjx.ui.activity.WebViewUtilActivity;
import cc.zenking.edu.zhjx.utils.ZhjxAppFramentUtil;

public class HomeMeunViewPageGridViewAdapter extends BaseAdapter {
	Context context;
	LayoutInflater layoutInflater;
	ArrayList<Menu> menuArrayList = new ArrayList<>();


	public HomeMeunViewPageGridViewAdapter(Context context, ArrayList<Menu> menuArrayList) {
		this.context = context;
		this.menuArrayList.addAll(menuArrayList);
		layoutInflater = LayoutInflater.from(context);
	}

	public void setMenuArrayList(ArrayList<Menu> menuArrayList) {
		this.menuArrayList.clear();
		this.menuArrayList.addAll(menuArrayList);
	}

	@Override
	public int getCount() {
		return menuArrayList.size();
	}

	@Override
	public Object getItem(int i) {
		return menuArrayList.get(i);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {//recycle_item_homepageViewHolde
		ViewHolde viewHolde = null;
		Menu menu = menuArrayList.get(i);
		if (view == null) {
			view = layoutInflater.inflate(R.layout.recycle_item_homepage, viewGroup, false);

			viewHolde = new ViewHolde();
			viewHolde.tv_name = view.findViewById(R.id.tv_name);
			viewHolde.iv_icon = view.findViewById(R.id.iv_icon);
			viewHolde.tv_red_point = view.findViewById(R.id.tv_red_point);
			viewHolde.rl_click_child = view.findViewById(R.id.rl_click_child);
			view.setTag(viewHolde);
		} else {
			viewHolde = (ViewHolde) view.getTag();
		}
		viewHolde.iv_icon.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				openWebView(menu);
			}
		});
		GlideUtil.getGlideImageViewUtil().setUserIco(context, menu.icon, viewHolde.iv_icon);
		viewHolde.tv_name.setText(menu.functionName);

		if (menu.hintNotify == 0) {
			viewHolde.tv_red_point.setVisibility(View.GONE);
		} else {
			viewHolde.tv_red_point.setVisibility(View.VISIBLE);
		}
		return view;
	}

	public void openWebView(Menu menu) {
		String url = menu.url;
		if (menu.functionName.equals("奖励")) {
			url = AppUrl.BASE_URL + "/parentapp/reward/childId/" + ZhjxAppFramentUtil.getSelterChild(context).studentId +
					"/userId/" + ZhjxAppFramentUtil.getUserInfo().userid;
			WebViewUtilActivity.openWebViewUtilActivity(context, url, R.drawable.title_back_black, menu.functionName, true, false);
		} else if (menu.functionName.equals("违规")) {
			url = AppUrl.BASE_URL + "/parentapp/outofline/childId/" + ZhjxAppFramentUtil.getSelterChild(context).studentId +
					"/userId/" + ZhjxAppFramentUtil.getUserInfo().userid;
			WebViewUtilActivity.openWebViewUtilActivity(context, url, R.drawable.title_back_black, menu.functionName, true, false);
		} else if (menu.functionName.equals("处罚")) {
			url = AppUrl.BASE_URL + "/parentapp/punish/childId/" + ZhjxAppFramentUtil.getSelterChild(context).studentId +
					"/userId/" + ZhjxAppFramentUtil.getUserInfo().userid;
			WebViewUtilActivity.openWebViewUtilActivity(context, url, R.drawable.title_back_black, menu.functionName, true, false);
		} else if (menu.functionName.equals("成绩")) {
			url = AppUrl.BASE_URL + "/parentapp/score/childId/" + ZhjxAppFramentUtil.getSelterChild(context).studentId +
					"/userId/" + ZhjxAppFramentUtil.getUserInfo().userid;
			WebViewUtilActivity.openWebViewUtilActivity(context, url, R.drawable.title_back_black, menu.functionName, true, false);
		} else if (menu.functionName.equals("课程表")) {
			url = AppUrl.BASE_URL + "/parentapp/timetable/childId/" + ZhjxAppFramentUtil.getSelterChild(context).studentId +
					"/userId/" + ZhjxAppFramentUtil.getUserInfo().userid;
			WebViewUtilActivity.openWebViewUtilActivity(context, url, R.drawable.title_back_black, menu.functionName, true, false);
		} else if (menu.functionName.equals("行为轨迹")) {
			url = AppUrl.BASE_URL + "/parentapp/behaviorTrack/childId/" + ZhjxAppFramentUtil.getSelterChild(context).studentId +
					"/userId/" + ZhjxAppFramentUtil.getUserInfo().userid;
			WebViewUtilActivity.openWebViewUtilActivity(context, url, R.drawable.title_back_black, menu.functionName, true, false);
		} else if (menu.functionName.equals("学期报告")) {
			url = AppUrl.BASE_URL + "/parentapp/termReport/childId/" + ZhjxAppFramentUtil.getSelterChild(context).studentId +
					"/userId/" + ZhjxAppFramentUtil.getUserInfo().userid;
			WebViewUtilActivity.openWebViewUtilActivity(context, url, R.drawable.title_back_black, menu.functionName, true, false);
		} else if (menu.functionName.equals("班级表现")) {
			url = AppUrl.BASE_URL + "/parentapp/classPerformance/childId/" + ZhjxAppFramentUtil.getSelterChild(context).studentId +
					"/userId/" + ZhjxAppFramentUtil.getUserInfo().userid;
			WebViewUtilActivity.openWebViewUtilActivity(context, url, R.drawable.title_back_black, menu.functionName, true, false);
		} else if (menu.functionName.equals("投票")) {
			url = AppUrl.BASE_URL + "/parentapp/voting/childId/" + ZhjxAppFramentUtil.getSelterChild(context).studentId +
					"/userId/" + ZhjxAppFramentUtil.getUserInfo().userid;
			WebViewUtilActivity.openWebViewUtilActivity(context, url, R.drawable.title_back_black, menu.functionName, true, false);
		} else if (menu.functionName.equals("请假")) {
			Intent intent = new Intent(context, LeaveActivity.class);
			context.startActivity(intent);
		}else if (menu.functionName.equals("好习惯")) {
			Intent intent = new Intent(context, GoodHabitActivity.class);
			context.startActivity(intent);
		} else {//听一听、
			WebViewUtilActivity.openWebViewUtilActivity(context, url, R.drawable.title_back_black, menu.functionName, true, true);
		}
	}

	public static class ViewHolde {
		ImageView iv_icon;
		TextView tv_name, tv_red_point;
		RelativeLayout rl_click_child;
	}


}
