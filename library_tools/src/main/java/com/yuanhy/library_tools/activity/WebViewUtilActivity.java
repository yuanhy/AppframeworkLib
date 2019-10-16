package com.yuanhy.library_tools.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.gyf.barlibrary.ImmersionBar;
import com.yuanhy.library_tools.R;
import com.yuanhy.library_tools.app.AppAcitivityUtile;
import com.yuanhy.library_tools.app.AppFramentUtil;
import com.yuanhy.library_tools.image.GlideUtil;
import com.yuanhy.library_tools.util.LogCatUtil;
import com.yuanhy.library_tools.util.YCallBack;

public class WebViewUtilActivity extends BaseWebView {
	private String TAG = "WebViewUtilActivity";
	String url = "";
	String title;
	BridgeWebView bdwebview;
	View tag_view;
	ImageView image_back;
	TextView title_name_tv;
	int backImageId ;
	boolean isDarkFont;// true 字体黑色   false 字体白色
	boolean isAddTitile;
	RelativeLayout titile_main_lout;
	public static void openWebViewUtilActivity(Context context, String url) {
		Intent intent = new Intent(context, WebViewUtilActivity.class);
		intent.putExtra("url", url);
		context.startActivity(intent);
	}
	public static void openWebViewUtilActivity(Context context, String url,int backImageId, String title,boolean isDarkFont,boolean isAddTitile) {
		Intent intent = new Intent(context, WebViewUtilActivity.class);
		intent.putExtra("url", url);
		intent.putExtra("isDarkFont", isDarkFont);
		intent.putExtra("title", title);
		intent.putExtra("backImageId", backImageId);
		intent.putExtra("isAddTitile", isAddTitile);
		context.startActivity(intent);
	}
	public static void openWebViewUtilActivity(Context context, String url,int backImageId, String title,boolean isDarkFont,boolean isAddTitile,YCallBack yCallBack) {
		Intent intent = new Intent(context, WebViewUtilActivity.class);
		intent.putExtra("url", url);
		intent.putExtra("isDarkFont", isDarkFont);
		intent.putExtra("title", title);
		intent.putExtra("backImageId", backImageId);
		intent.putExtra("isAddTitile", isAddTitile);
		context.startActivity(intent);
	}

	public static void openWebViewUtilActivity(Context context, String url, String title) {
		Intent intent = new Intent(context, WebViewUtilActivity.class);
		intent.putExtra("url", url);
		intent.putExtra("title", title);
		context.startActivity(intent);
	}

	public static void openWebViewUtilActivityForResult(Activity context, String url, String title, int code) {
		Intent intent = new Intent(context, WebViewUtilActivity.class);
		intent.putExtra("url", url);
		intent.putExtra("title", title);
		context.startActivityForResult(intent, code);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_view_util);
		url = getIntent().getStringExtra("url");
		title = getIntent().getStringExtra("title");
		isDarkFont = getIntent().getBooleanExtra("isDarkFont",true);
		backImageId = getIntent().getIntExtra("backImageId",0);
		isAddTitile= getIntent().getBooleanExtra("isAddTitile",true);
		titile_main_lout =findViewById(R.id.titile_main_lout);
		if (isAddTitile){
			titile_main_lout.setVisibility(View.VISIBLE);
		}else
			titile_main_lout.setVisibility(View.GONE);

		tag_view = findViewById(R.id.tag_view);
		initTittleBarLinearLayout(tag_view);
		image_back= findViewById(R.id.image_back);
		title_name_tv =findViewById(R.id.title_name_tv);
		title_name_tv.setText(title);
		setActivityTittleBar();
		if (backImageId>0){
			image_back.setOnClickListener((v)-> finish());
		}
		GlideUtil.getGlideImageViewUtil().setImageView(context,backImageId,image_back);
		bdwebview = findViewById(R.id.bdwebview);
		initWebView(bdwebview, url);
		bdwebview.setWebChromeClient(new MyWebCromeClient());


		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			bdwebview.setWebContentsDebuggingEnabled(true);
		}

		progressBar = findViewById(R.id.progressBarHorizontal);


		// 通过addJavascriptInterface()将Java对象映射到JS对象 //参数1：Javascript对象名 //参数2：Java对象名
		bdwebview.addJavascriptInterface(new AndroidtoJs(), "android");//AndroidtoJS类对象映射到js的test对象
//		//  //注册submitFromWeb方法  用来接收js/h5 发送来的数据   onCallBack 是给js发送消息
//		bdwebview.registerHandler("changeChild", new BridgeHandler() {
//			@Override
//			public void handler(String data, CallBackFunction function) {
//				if (!TextUtils.isEmpty(data)) {
//					String s = "yuan通过调用Native方法接收数据:\\n" + data;
//				}
////                function.onCallBack("yuanNative已经接收到数据：" + data + "，请确认！");
//				function.onCallBack("android已经收到消息");
//				LogCatUtil.getInstance(WebViewUtilActivity.this).d(TAG, "yuan通过调用Native方法接收数据:\\n" + data);
//			}
//		});
//		//用来向 h5/js 传递数据
//		bdwebview.setDefaultHandler(new DefaultHandler() {
//			@Override
//			public void handler(String data, CallBackFunction function) {
////                function.onCallBack("yuanNative已收到消息！");
//				LogCatUtil.getInstance(WebViewUtilActivity.this).d(TAG, "ndroid向js/h5传递数据");
//				function.onCallBack("android向js/h5传递数据");
//			}
//		});
	}

	ProgressBar progressBar;

	private class MyWebCromeClient extends WebChromeClient {
		@Override
		public void onProgressChanged(WebView view, int newProgress) {

			if (newProgress == 100) { //加载完毕进度条消失
				progressBar.setVisibility(View.GONE);
			} else {
				//更新进度
				if (progressBar.getVisibility() != View.VISIBLE) {
					progressBar.setVisibility(View.VISIBLE);
				}
				progressBar.setProgress(newProgress);
			}
			super.onProgressChanged(view, newProgress);
		}
	}


	// 继承自Object类
	public class AndroidtoJs extends Object {
		// 定义JS需要调用的方法 // 被JS调用的方法必须加入@JavascriptInterface注解
//		@JavascriptInterface
//		public void hello(String msg) {
//			LogCatUtil.getInstance(WebViewUtilActivity.this).d(TAG, "JS调用了Android的hello方法");
//		}
		@JavascriptInterface
		public void changeChild(String studentID) {
			if (yCallBack!=null)
			yCallBack.onOk(studentID);
			LogCatUtil.getInstance(WebViewUtilActivity.this).d(TAG, "JS调用了Android的hello方法:"+studentID);
		}

	}
	YCallBack yCallBack;
public void setCallBack(YCallBack yCallBack){
	this.yCallBack=yCallBack;
}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (bdwebview != null)
			bdwebview.destroy();
	}



	public void setActivityTittleBar() {
		int h = AppAcitivityUtile.getTitlebarHith(context);
		tag_view.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, h));// 设置布局);
		tag_view.setVisibility(View.VISIBLE);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// Check if the key event was the Back button.
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			// If there is a history to move back
			if (bdwebview.canGoBack()){
				bdwebview.goBack();
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
}

