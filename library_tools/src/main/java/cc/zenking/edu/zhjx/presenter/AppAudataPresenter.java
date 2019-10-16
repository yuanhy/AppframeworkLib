package cc.zenking.edu.zhjx.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.yuanhy.library_tools.activity.BaseFragment;
import com.yuanhy.library_tools.app.AppAcitivityUtile;
import com.yuanhy.library_tools.http.retrofit.RetrofitUtile;
import com.yuanhy.library_tools.presenter.BasePresenter;
import com.yuanhy.library_tools.rxjava.ObServerBean;
import com.yuanhy.library_tools.util.AppCommonTool;
import com.yuanhy.library_tools.util.YCallBack;

import cc.zenking.edu.zhjx.api.AppUpdataApi;
import cc.zenking.edu.zhjx.api.AppUrl;
import cc.zenking.edu.zhjx.api.ChildApi;
import cc.zenking.edu.zhjx.api.InterfaceAddress;
import cc.zenking.edu.zhjx.db.dbenty.UserEnty;
import cc.zenking.edu.zhjx.enty.UpdateResult;
import cc.zenking.edu.zhjx.utils.ZhjxAppFramentUtil;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class AppAudataPresenter extends BasePresenter {

	YCallBack yCallBack;
	AppUpdataApi appUpdataApi;
	String TAG =getClass().getName();
	Context context;
	UserEnty userEnty;
	Gson gson ;
	public AppAudataPresenter(Context context, YCallBack yCallBack) {
		this.yCallBack = yCallBack;
		this.context = context;
		appUpdataApi = RetrofitUtile.getInstanceSSL(AppUrl.BASE_URL).createSSL(AppUpdataApi.class);
		userEnty = ZhjxAppFramentUtil.getUserInfo();
		gson = new Gson();
	}
public void getAppUpdataInfo(){
	appUpdataApi.updateApp("zhjx", AppCommonTool.getAppVersionName(context), System.currentTimeMillis())
			.compose(io_uiMain()).subscribe(new ObServerBean<UpdateResult>() {
		@Override
		public void onSuccees(UpdateResult updateResult) {
			updateResult.update=true;
			if ( updateResult.isUpdate()){
				yCallBack.requestSuccessful(updateResult);
			}
		}

		@Override
		protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
			yCallBack.onError(null);
		}
	});
}
//	ResponseEntity<UpdateResult> updateApp(String key, String version, long t);
}
