package com.yuanhy.library_tools.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.FloatRange;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;

import com.gyf.barlibrary.ImmersionBar;
import com.yuanhy.library_tools.R;
import com.yuanhy.library_tools.app.AppAcitivityUtile;
import com.yuanhy.library_tools.interfaces.ListViewInterface;
import com.yuanhy.library_tools.util.PermissionUtil;
import com.yuanhy.library_tools.util.StatusBarUtil;

/**
 * Created by yuanhy on 2018/1/23.
 */

public abstract class BaseActicity2 extends Activity implements View.OnClickListener, ListViewInterface {
    public Context context;
    StatusBarUtil statusBarUtil;
    //加载view
    public String TAG=this.getClass().getName();
	private View titleBarView;
	public Activity activity;
	@Override
    public void onCreate(  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 取消标题
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		context = this;
		activity=this;
        statusBarUtil = new StatusBarUtil();
        setTransparent();
        setTitleBar();



        ActivityManagerUtile.getInstance().addActivity(this);
    }

    /**
     * 设置状态栏 字体
     *  transparent true 黑色字体,false 白色字体。背景全透明
     */
    public void setTitleBar(){
        ImmersionBar.with(this).statusBarDarkFont(transparent).init();
    }
	/**
	 * 设置状态栏 字体
	 *  transparent true 黑色字体,false 白色字体。背景全透明
	 */
	public void setTitleBar(boolean transparent){
		ImmersionBar.with(this).statusBarDarkFont(transparent).init();
	}
    /**
     * true 黑色字体，白色背景
     * false 白色字体
     *
     * @param transparent
     */
    public void setTransparent(boolean transparent) {
        this.transparent = transparent;
    }

    /**
     * 不调用 默认 true
     * true 黑色字体， 背景透明
     *  false 白色字体
     *
     * 具体使用 ：setTransparent(boolean transparent)
     */
    public abstract void setTransparent();
    boolean transparent = true;


	public void initTittleBarRelativeLayout(View view) {
	 titleBarView = view;
		int h = AppAcitivityUtile.getTitlebarHith(context);
		titleBarView.setLayoutParams(new RelativeLayout .LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT, h));// 设置布局);
		titleBarView.setVisibility(View.VISIBLE);
	}
	public void initTittleBarLinearLayout(View view) {
		int h = AppAcitivityUtile.getTitlebarHith(context);
		view.setLayoutParams(new LinearLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT, h));// 设置布局);
		view.setVisibility(View.VISIBLE);
	}
	/**
	 *
	 * @param colco
	 * @param ratio 透明度  0全透明。1不透明
	 */
	public void setTitleBarBackgroundColor(int colco, @FloatRange(from = 0.0, to = 1.0) float ratio){
		titleBarView.setBackgroundColor(colco);
		titleBarView	.setBackgroundColor(ColorUtils.blendARGB(Color.TRANSPARENT
				, ContextCompat.getColor(context, R.color.color_ffffff), ratio));
	}

	@Override
    public void onClick(View v) {
        int id = v.getId();
    }
    @Override
    protected void onDestroy() {
        ActivityManagerUtile.getInstance().remove(this);
        super.onDestroy();
    }

}
