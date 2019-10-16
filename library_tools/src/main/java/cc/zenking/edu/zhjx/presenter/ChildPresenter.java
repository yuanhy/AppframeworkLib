package cc.zenking.edu.zhjx.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.yuanhy.library_tools.http.retrofit.RetrofitUtile;
import com.yuanhy.library_tools.presenter.BasePresenter;
import com.yuanhy.library_tools.rxjava.ObServerBean;
import com.yuanhy.library_tools.util.SharedPreferencesUtil;
import com.yuanhy.library_tools.util.YCallBack;

import cc.zenking.edu.zhjx.api.AppUrl;
import cc.zenking.edu.zhjx.api.ChildApi;
import cc.zenking.edu.zhjx.api.HomeFragmentApi;
import cc.zenking.edu.zhjx.db.dbenty.UserEnty;
import cc.zenking.edu.zhjx.enty.childEnty.Child;
import cc.zenking.edu.zhjx.enty.childEnty.OwnChilds;
import cc.zenking.edu.zhjx.utils.CachUtile;
import cc.zenking.edu.zhjx.utils.ZhjxAppFramentUtil;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import okhttp3.Cache;

public class ChildPresenter extends BasePresenter {
	YCallBack yCallBack;
	ChildApi childApi;
	String TAG = "ChildPresenter";
	Context context;
	UserEnty userEnty;
	Gson gson ;
	public ChildPresenter(Context context, YCallBack yCallBack) {
		this.yCallBack = yCallBack;
		this.context = context;
		childApi = RetrofitUtile.getInstanceSSL(AppUrl.BASE_URL).createSSL(ChildApi.class);
		userEnty = ZhjxAppFramentUtil.getUserInfo();
		gson = new Gson();
	}

	/**
	 * 先读缓存，在请求网络
	 */
	public void getBindStudentList(){
		Observable.create(new ObservableOnSubscribe<OwnChilds>() {
			@Override
			public void subscribe(ObservableEmitter<OwnChilds> emitter) throws Exception {
				String childStrings=		SharedPreferencesUtil.getSharedPreferencesUtil(context).getString(CachUtile.getUser_childsKey());
				OwnChilds ownChilds = gson.fromJson(childStrings,OwnChilds.class);
				emitter.onNext(ownChilds);
			}
		}).compose(io_uiMain()).subscribe(new ObServerBean<OwnChilds>() {
			@Override
			public void onSuccees(OwnChilds ownChilds) {
				yCallBack.requestSuccessful(ownChilds.objData);
				getBindStudentListNetWork();
			}

			@Override
			protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
				getBindStudentListNetWork();
			}
		});
	}
	/**
	 * 请求服务器的信息
	 */
	public void getBindStudentListNetWork() {
		childApi.getBindStudentList(userEnty.session, userEnty.userid, userEnty.userid).compose(io_uiMain())
				.subscribe(new ObServerBean<OwnChilds>() {
					@Override
					public void onSuccees(OwnChilds ownChilds) {
						if (ownChilds.objData != null && ownChilds.objData.size() > 0) {
							String childStrings = gson.toJson(ownChilds);
							SharedPreferencesUtil.getSharedPreferencesUtil(context).putString(CachUtile.getUser_childsKey(), childStrings);
							yCallBack.requestSuccessful(ownChilds.objData);
						} else {
							String childStrings=		SharedPreferencesUtil.getSharedPreferencesUtil(context).getString(CachUtile.getUser_childsKey());
							if (childStrings.length()==0){
								yCallBack.onError(null);
								return;
							}
							ownChilds = gson.fromJson(childStrings,OwnChilds.class);
							yCallBack.requestSuccessful(ownChilds.objData);
						}
					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
						String childStrings=		SharedPreferencesUtil.getSharedPreferencesUtil(context).getString(CachUtile.getUser_childsKey());
						if (childStrings.length()==0){
							yCallBack.onError(null);
							return;
						}
						OwnChilds	ownChilds = gson.fromJson(childStrings,OwnChilds.class);
						yCallBack.requestSuccessful(ownChilds.objData);
					}
				});
	}

	/**
	 * 获取本地缓存
	 */
	public void getBindStudentListCach(){
		Observable.create(new ObservableOnSubscribe<OwnChilds>() {
			@Override
			public void subscribe(ObservableEmitter<OwnChilds> emitter) throws Exception {
				String childStrings=		SharedPreferencesUtil.getSharedPreferencesUtil(context).getString(CachUtile.getUser_childsKey());
				OwnChilds ownChilds = gson.fromJson(childStrings,OwnChilds.class);
				emitter.onNext(ownChilds);
			}
		}).compose(io_uiMain()).subscribe(new ObServerBean<OwnChilds>() {
			@Override
			public void onSuccees(OwnChilds ownChilds) {
				yCallBack.requestSuccessful(ownChilds.objData);
			}

			@Override
			protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
				yCallBack.onError(null);
			}
		});
	}
}
