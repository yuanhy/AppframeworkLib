package cc.zenking.edu.zhjx.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.style.UpdateAppearance;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yuanhy.library_tools.activity.BaseActivity;
import com.yuanhy.library_tools.app.AppAcitivityUtile;
import com.yuanhy.library_tools.app.AppFramentUtil;
import com.yuanhy.library_tools.file.FileDownUtile;
import com.yuanhy.library_tools.file.FileUtil;
import com.yuanhy.library_tools.http.ProgressListener;
import com.yuanhy.library_tools.http.okhttp3.AndroidOkHttp3;
import com.yuanhy.library_tools.image.GlideUtil;
import com.yuanhy.library_tools.presenter.BasePresenter;
import com.yuanhy.library_tools.util.AppCommonTool;
import com.yuanhy.library_tools.util.SdCardUtil;
import com.yuanhy.library_tools.util.SharedPreferencesUtil;
import com.yuanhy.library_tools.util.SystemUtile;
import com.yuanhy.library_tools.util.YCallBack;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import cc.zenking.edu.zhjx.BuildConfig;
import cc.zenking.edu.zhjx.MainActivity;
import cc.zenking.edu.zhjx.R;
import cc.zenking.edu.zhjx.api.AppIntenface;
import cc.zenking.edu.zhjx.api.AppUrl;
import cc.zenking.edu.zhjx.api.InterfaceAddress;
import cc.zenking.edu.zhjx.db.dbenty.UserEnty;
import cc.zenking.edu.zhjx.enty.HttpContentEnty;
import cc.zenking.edu.zhjx.enty.LoginResult;
import cc.zenking.edu.zhjx.enty.UpdateResult;
import cc.zenking.edu.zhjx.popwindows.LoginIpPopWindows;
import cc.zenking.edu.zhjx.presenter.AppAudataPresenter;
import cc.zenking.edu.zhjx.ui.activity.WebViewUtilActivity;
import cc.zenking.edu.zhjx.utils.CachUtile;
import cc.zenking.edu.zhjx.utils.DeviceUtils;
import cc.zenking.edu.zhjx.utils.ZhjxAppFramentUtil;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LogonActivity extends BaseActivity {
	@BindView(R.id.portrait)
	ImageView portrait;

	@BindView(R.id.loginButton)
	TextView loginButton;

	@BindView(R.id.loginName)
	EditText loginName;

	@BindView(R.id.loginPassword)
	EditText loginPassword;

	@BindView(R.id.forgetpwd)
	TextView forgetpwd;

	@BindView(R.id.tv_assistance)
	TextView tv_assistance;

	@BindView(R.id.tv_agree_content)
	TextView tv_agree_content;
	AppAudataPresenter appAudataPresenter;
	private String account_password;
	private String account_number;
	private UpdateResult updateResult;
	private PopupWindow pop;
	private ArrayList<HttpContentEnty> httpContentEntyArrayList;
	LoginIpPopWindows loginIpPopWindows;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 取消标题
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_logon);
		initUnbinder();

//		loginButton.setOnClickListener(this);
//		forgetpwd.setOnClickListener(this);
		account_number = SharedPreferencesUtil.getSharedPreferencesUtil(context).getString(CachUtile.account_number);
		account_password = SharedPreferencesUtil.getSharedPreferencesUtil(context).getString(CachUtile.account_password);
		loginName.setText(account_number);
		loginPassword.setText(account_password);


		upDataInfo();
	}

	private void upDataInfo() {
		appAudataPresenter.getAppUpdataInfo();
	}

	@OnClick({R.id.forgetpwd, R.id.loginButton, R.id.tv_assistance, R.id.tv_agree_content, R.id.portrait})
	public void onClick2(View v) {
		super.onClick(v);
		switch (v.getId()) {
			case R.id.loginButton:
//				if (!isPsw( loginPassword.getText().toString().trim())){
//					toastPopWindow.setMessge("")
//					return;
//				}
				if (TextUtils.isEmpty(loginPassword.getText().toString().trim())) {
					toastPopWindow.setMessge("请输入密码").show();
					return;
				}
				loadPopupWindow.showPopWindowCenterBackgroundAlpha();
				AppFramentUtil.tasks.postRunnable(new Runnable() {
					@Override
					public void run() {
						login(loginName.getText().toString().trim(), loginPassword.getText().toString().trim());
					}
				});
				break;
			case R.id.forgetpwd:
				WebViewUtilActivity.openWebViewUtilActivity(this, AppUrl.LOGIN_SERVER + InterfaceAddress.FORGOTPSW,
						R.drawable.title_back_black, forgetpwd.getText().toString(), true,true);
				break;
			case R.id.tv_assistance:
				WebViewUtilActivity.openWebViewUtilActivity(this, AppUrl.LOGIN_SERVER + InterfaceAddress.ASSISTANCE,
						R.drawable.title_back_black, tv_assistance.getText().toString(), true,true);
				break;
			case R.id.tv_agree_content:
				WebViewUtilActivity.openWebViewUtilActivity(this, AppUrl.LOGIN_SERVER + InterfaceAddress.AGREEMENT,
						R.drawable.title_back_black, tv_agree_content.getText().toString(), true,true);
				break;
			case R.id.portrait:
				if (loginIpPopWindows != null) {
					if (loginIpPopWindows.isShowing()) {
						loginIpPopWindows.dismiss();
						break;
					}
				}
				initHttpContent();
				break;
		}
	}

	void initHttpContent() {
		if (httpContentEntyArrayList == null)
			httpContentEntyArrayList = new ArrayList<>();
		httpContentEntyArrayList.clear();
		HttpContentEnty data = new HttpContentEnty();
		data.name = "测试环境9.105";
		data.ip = "https://192.168.9.105";
		httpContentEntyArrayList.add(data);

		HttpContentEnty data1 = new HttpContentEnty();
		data1.name = "开发环境9.100";
		data1.ip = "https://192.168.9.100";
		httpContentEntyArrayList.add(data1);

		HttpContentEnty data2 = new HttpContentEnty();
		data2.name = "公网测试环境198.130";
		data2.ip = "https://211.154.198.130:8090";
		httpContentEntyArrayList.add(data2);

		HttpContentEnty data3 = new HttpContentEnty();
		data3.name = "线上测试环境edutest";
		data3.ip = "https://edutest.zenking.cc";
		httpContentEntyArrayList.add(data3);

		HttpContentEnty data4 = new HttpContentEnty();
		data4.name = "线上生产环境";
		data4.ip = "http://edu.zenking.cc";
		httpContentEntyArrayList.add(data4);

		HttpContentEnty data5 = new HttpContentEnty();
		data5.name = "外网环境9.105";
		data5.ip = "https://105.zenking.cc:8090";
		httpContentEntyArrayList.add(data5);

		HttpContentEnty data6 = new HttpContentEnty();
		data6.name = "外网环境9.100";
		data6.ip = "https://devtest.zenking.cc:8100";
		httpContentEntyArrayList.add(data6);

		HttpContentEnty data7 = new HttpContentEnty();
		data7.name = " 环境9.34";
		data7.ip = "https://192.168.9.34";
		httpContentEntyArrayList.add(data7);
		if (loginIpPopWindows != null)
			loginIpPopWindows = null;

		loginIpPopWindows = new LoginIpPopWindows(this, httpContentEntyArrayList);
		loginIpPopWindows.showAsDropDown(portrait, -portrait.getMeasuredWidth() / 2, 0);
	}

	void login(String account, String password) {
//		setButton(0);
//		if (!NetworkUtil.isNetworkConnected(this)) {
//			util.toast("无可用网络！", -1);
//			setButton(1);
//			return;
//		}
		String type;
		if (account.contains("@")) {
			type = "2";// 2是邮箱
		} else if (account.startsWith("1") && account.length() == 11) {
			type = "1";// 1是手机号
		} else {
			type = "0";// 用户名
		}
		//appType=1 是安卓 2是ios ；appBusType=1 是家长  2是教师
		String url = AppUrl.BASE_URL + "/sso/app/user/login";
		try {
			FormBody.Builder formbody = new FormBody.Builder();
			formbody.add("isNew", "true");
			formbody.add("appBusType", "1");
			formbody.add("appType", "1");
			formbody.add("sys", "zksc");
			formbody.add("type", type);
			formbody.add("loginId", account);
			formbody.add("password", password);
			formbody.add("device", DeviceUtils.getUUID(this));
			formbody.add("version", String.valueOf(AppCommonTool.getAppVersion(this)));
			//创建请求体
			FormBody body = formbody.build();
			//1.创建OkHttpClient对象
			OkHttpClient okHttpClient = new OkHttpClient.Builder()
					.sslSocketFactory(AndroidOkHttp3.getInstance(this).getSSLSocketFactory()
							, AndroidOkHttp3.getInstance(this).getTrustManager())
					.hostnameVerifier(AndroidOkHttp3.getInstance(this).getHostnameVerifier())
					.connectTimeout(30, TimeUnit.SECONDS)//设置连接超时时间
					.readTimeout(30, TimeUnit.SECONDS).build();//设置读取超时时间;
			//2.创建Request对象，设置一个url地址,设置请求方式。
			Request request = new Request.Builder()
					.url(url)
					.method("POST", body)
					.build();
			//3.创建一个call对象,参数就是Request请求对象
			final Call call = okHttpClient.newCall(request);
			Response response = call.execute();
			if (response.isSuccessful()) {
				String data = response.body().string();
				UserEnty lr = new Gson().fromJson(data, UserEnty.class);
				if (lr.result == 1) {
//					setButton(1);
					AppFramentUtil.logCatUtil.v(TAG, lr.reason);
					if (lr.loginErrorStatus == 0) {
						AppFramentUtil.logCatUtil.v(TAG, lr.reason);
					} else if (lr.loginErrorStatus == 1) {
						//这种情况下reason的值是 uuid
						AppFramentUtil.logCatUtil.v(TAG, lr.reason);
//						uuid = lr.uuidChange;
//						initUpdatePswPop();
					} else if (lr.loginErrorStatus == 2) {
//						initJudgePswPop(lr.reason);
					} else if (lr.loginErrorStatus == 3) {
//						initJudgePswPop(lr.reason);
					} else if (lr.loginErrorStatus == 4) {
//						initBanAccountPop(lr.reason);
					}
				} else {
					if (lr.roles == 8 || lr.roles == 2) {//lr.roles == 8||lr.roles == 2   登录成功
//						storeConfig(account, password, lr);
						AppFramentUtil.logCatUtil.v(TAG, "偷着笑");
						SharedPreferencesUtil.getSharedPreferencesUtil(context).putString(CachUtile.account_number, account);
						SharedPreferencesUtil.getSharedPreferencesUtil(context).putString(CachUtile.account_password, password);
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								loadPopupWindow.dismiss(true);
								ZhjxAppFramentUtil.userDbData.delter(null,true,null);
								ZhjxAppFramentUtil.userDbData.add(lr, null);
								Intent intent = new Intent(context, MainActivity.class);
								startActivity(intent);
								finish();
							}
						});
					} else {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								loadPopupWindow.dismiss(true);
								toastPopWindow.setMessge("用户名或密码不正确！").show();
							}
						});
						AppFramentUtil.logCatUtil.v(TAG, "用户名或密码不正确！");
//						setButton(1);
//						util.toast("用户名或密码不正确！", -1);
					}
				}
			} else {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						loadPopupWindow.dismiss(true);
						toastPopWindow.setMessge("请求服务器失败！").show();
					}
				});
				AppFramentUtil.logCatUtil.v(TAG, "请求服务器失败！");
//				util.toast("请求服务器失败！", -1);
//				setButton(1);
			}
		} catch (Exception e) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					loadPopupWindow.dismiss(true);
					toastPopWindow.setMessge("请求服务器失败！").show();
				}
			});
			AppFramentUtil.logCatUtil.v(TAG, "请求服务器失败！");
//			util.toast("请求服务器失败！", -1);
//			setButton(1);
			e.printStackTrace();
		}

	}

	@Override
	public void setTransparent() {

	}


	@Override
	public BasePresenter createPresenter() {

		return appAudataPresenter = new AppAudataPresenter(context, new YCallBack() {
			@Override
			public void requestSuccessful(Object o) {
				updateResult = (UpdateResult) o;
				if (updateResult.isUpdate()) {
					setPopWindow();
				} else {
					if (!TextUtils.isEmpty(account_password)) {
						AppFramentUtil.tasks.postRunnable(new Runnable() {
							@Override
							public void run() {
								login(loginName.getText().toString().trim(), loginPassword.getText().toString().trim());
							}
						});
					}
				}
			}

			@Override
			public void onError(Object o) {
				if (!TextUtils.isEmpty(account_password)) {
					AppFramentUtil.tasks.postRunnable(new Runnable() {
						@Override
						public void run() {
							login(loginName.getText().toString().trim(), loginPassword.getText().toString().trim());
						}
					});
				}
			}
		});
	}

	void setPopWindow() {
		View view = LayoutInflater.from(this)
				.inflate(R.layout.popwindow_update_app_new, null);
		TextView iv_now_update = (TextView) view
				.findViewById(R.id.iv_now_update);
		RelativeLayout iv_cancle_lout = (RelativeLayout) view.findViewById(R.id.iv_cancle_lout);
		if (updateResult.force) {
			iv_cancle_lout.setVisibility(View.GONE);
		}
		TextView tv_desc1 = (TextView) view.findViewById(R.id.tv_desc1);
		TextView tv_desc2 = (TextView) view.findViewById(R.id.tv_desc2);
		tv_desc1.setTextSize(TypedValue.COMPLEX_UNIT_PX, SystemUtile.dp2px(20));
		tv_desc2.setTextSize(TypedValue.COMPLEX_UNIT_PX, SystemUtile.dp2px(18));
		tv_desc2.setText(updateResult.getMemo());
		iv_now_update.setTextSize(TypedValue.COMPLEX_UNIT_PX, SystemUtile.dp2px(18));
		if (updateResult.desc != null && updateResult.desc.length() != 0) {
			if (updateResult.desc.contains(":")) {
				String[] des = updateResult.desc.split(":");
				tv_desc1.setText(des[0]);
				tv_desc2.setText(des[1]);
			}
		}
		pop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT, false);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		pop.setOutsideTouchable(false);
		pop.setFocusable(false);
		// 自动化测试UiAutomator识别popwindow需要以下两句代码
		view.setFocusable(true);
		view.setFocusableInTouchMode(true); // 设置view能够接听事件
		//监听Back返回键
		view.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					if (pop != null && pop.isShowing()) {
						System.exit(0);
						return true;
					}
				}
				return false;
			}
		});
		try {
			pop.showAtLocation(this.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		iv_now_update.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (ActivityCompat.checkSelfPermission(LogonActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
					ActivityCompat.requestPermissions(LogonActivity.this,
							new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,},
							102);
				} else {
					AppFramentUtil.tasks.postRunnable(new Runnable() {
						@Override
						public void run() {
							FileDownUtile.fileDownBreakpoint(context, updateResult.path, SdCardUtil.getAppDataPath(context),
									BuildConfig.APPLICATION_ID + updateResult.version + ".apk", new ProgressListener() {


										@Override
										public void onOk(Object o) {
											AppFramentUtil.logCatUtil.i("下载完成");
											installAPK(new File((String) o));
										}

										@Override
										public void onError(Object o) {
											AppFramentUtil.logCatUtil.i("下载失败");
										}

										@Override
										public void onProgress(Object o, Object o2) {
											AppFramentUtil.logCatUtil.i("下载进度:" + o);
										}
									});
						}
					});

					pop.dismiss();
				}
			}
		});
		iv_cancle_lout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				pop.dismiss();
				if (!updateResult.force) {
					pop.dismiss();
				} else {
					System.exit(0);
				}
			}
		});
	}

	void installAPK(File t) {
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		if (Build.VERSION.SDK_INT >= 24) { //判读版本是否在7.0以上
			//参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            Log.i("TAG", "t==========" + t.getPath());
			Uri apkUri =
					FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", t);
			//添加这一句表示对目标应用临时授权该Uri所代表的文件
			intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
		} else {
			intent.setDataAndType(Uri.fromFile(t),
					"application/vnd.android.package-archive");
		}
		startActivity(intent);
		finish();
	}

}
