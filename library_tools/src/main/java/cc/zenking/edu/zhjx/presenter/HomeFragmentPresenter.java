package cc.zenking.edu.zhjx.presenter;

import android.content.Context;

import com.yuanhy.library_tools.app.AppAcitivityUtile;
import com.yuanhy.library_tools.app.AppFramentUtil;
import com.yuanhy.library_tools.app.AppUpdataUtil;
import com.yuanhy.library_tools.http.retrofit.RetrofitUtile;
import com.yuanhy.library_tools.presenter.BasePresenter;
import com.yuanhy.library_tools.rxjava.ObServerBean;
import com.yuanhy.library_tools.util.AppCommonTool;
import com.yuanhy.library_tools.util.YCallBack;

import java.util.ArrayList;

import cc.zenking.edu.zhjx.api.AppUrl;
import cc.zenking.edu.zhjx.api.HomeFragmentApi;
import cc.zenking.edu.zhjx.db.dbenty.UserEnty;
import cc.zenking.edu.zhjx.enty.childEnty.Child;
import cc.zenking.edu.zhjx.enty.homeEnty.MenuList;
import cc.zenking.edu.zhjx.enty.homeEnty.NewsListResultEnty;
import cc.zenking.edu.zhjx.enty.homeEnty.RotaryPlantingEnty;
import cc.zenking.edu.zhjx.utils.ZhjxAppFramentUtil;

public class HomeFragmentPresenter extends BasePresenter {
	YCallBack yCallBack;
	HomeFragmentApi homeFragmentApi;
	String TAG = "HomePresenter";
	Context context;
	UserEnty userEnty;
	ChildPresenter childPresenter;

	public HomeFragmentPresenter(Context context, YCallBack yCallBack) {
		this.yCallBack = yCallBack;
		this.context = context;
		homeFragmentApi = RetrofitUtile.getInstanceSSL(AppUrl.BASE_URL).createSSL(HomeFragmentApi.class);
		userEnty = ZhjxAppFramentUtil.getUserInfo();
		childPresenter = new ChildPresenter(context, new YCallBack() {
			@Override
			public void requestSuccessful(Object o) {
				ArrayList<Child> childArrayList = (ArrayList<Child>) o;
				StringBuffer studIds = new StringBuffer();

				for (Child child : childArrayList) {
					studIds.append(child.schoolId + ":" + child.studentId + ",");
				}
				if (studIds.toString().endsWith(",")) {
					studIds.deleteCharAt(studIds.length() - 1);
				}
				homeFragmentApi.getAppFunction(userEnty.session, userEnty.userid, userEnty.userid, AppCommonTool.getAppVersionName(context), studIds.toString())
						.compose(io_uiMain()).subscribe(new ObServerBean<MenuList>() {
					@Override
					public void onSuccees(MenuList menuList) {
						AppFramentUtil.logCatUtil.v("长度："+menuList.data.size());
						yCallBack.requestSuccessful(menuList);
					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
						yCallBack.onError(new MenuList());
					}
				});
			}

			@Override
			public void onError(Object o) {

			}
		});
	}

	/**
	 * 获取轮播图
	 */
	private void getNewsList() {
		homeFragmentApi.getNewsList(userEnty.session, userEnty.userid, "getAllInfo", 5, 1, "0", "1")
				.compose(io_uiMain())
				.subscribe(new ObServerBean<NewsListResultEnty>() {
					@Override
					public void onSuccees(NewsListResultEnty newsListResultEnty) {
						newsListResultEnty.type=0;
						yCallBack.requestSuccessful(newsListResultEnty);
					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
						yCallBack.onError(new NewsListResultEnty());
					}
				});
	}

	/**
	 * 获取看看类型的新闻
	 */
	private void getRecommend() {
		homeFragmentApi.getNewsList(userEnty.session, userEnty.userid, "getAllInfo", 1, 1, "1", "1")
				.compose(io_uiMain())
				.subscribe(new ObServerBean<NewsListResultEnty>() {
					@Override
					public void onSuccees(NewsListResultEnty newsListResultEnty) {
						newsListResultEnty.type=1;
						yCallBack.requestSuccessful(newsListResultEnty);
					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
						yCallBack.onError(new NewsListResultEnty());
					}
				});
	}
	/**
	 * 先获取孩子，在获取相关的功能模块信息
	 */
	private void getAppFunction() {
		childPresenter.getBindStudentList();
	}

	@Override
	public void intervalWork() {
		postData();
	}

	private void postData() {
		getNewsList();
		getAppFunction();
		getRecommend();
	}

}
