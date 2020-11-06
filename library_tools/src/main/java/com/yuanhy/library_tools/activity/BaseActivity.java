package com.yuanhy.library_tools.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;

import com.yuanhy.library_tools.app.AppFramentUtil;
import com.yuanhy.library_tools.popwindows.LoadPopupWindow;
import com.yuanhy.library_tools.popwindows.ToastPopWindow;
import com.yuanhy.library_tools.presenter.BasePresenter;
import com.yuanhy.library_tools.util.SystemUtile;
import com.yuanhy.library_tools.util.TimeUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity<T extends BasePresenter> extends BaseActicity2 implements View.OnClickListener {
    public T basePresenter;
    private Unbinder mUnbinder;
    public LoadPopupWindow loadPopupWindow;
    public ToastPopWindow toastPopWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutResourceId() != 0) {
            setContentView(getLayoutResourceId());
            initUnbinder();
        }
        AppFramentUtil.logCatUtil.i("进入：" + TAG);
        basePresenter = createPresenter();
        if (basePresenter != null)
            basePresenter.onBindView(this);
        initPopWindow();
    }

    public int getLayoutResourceId() {
        return 0;
    }

    ;

    private void initPopWindow() {
        loadPopupWindow = new LoadPopupWindow((Activity) context);
        toastPopWindow = new ToastPopWindow((Activity) context);
    }

    public void initUnbinder() {
        if (mUnbinder == null)
            mUnbinder = ButterKnife.bind(this);

    }

    public T createPresenter() {
        return null;
    }

    ;

    @Override
    protected void onDestroy() {

        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
            this.mUnbinder = null;
        }
        if (basePresenter != null) {
            basePresenter.onDestroy();
            this.basePresenter = null;
        }
        if (loadPopupWindow != null && loadPopupWindow.isShowing()) {
            loadPopupWindow.dismiss(true);
        }
        if (toastPopWindow != null && toastPopWindow.isShowing()) {
            toastPopWindow.dismiss();
        }
        super.onDestroy();
    }


    @Override
    public void refreshData() {

    }

    @Override
    public void loadMoveData() {

    }



    private long lastClickTime = 0L;
    // 两次点击间隔不能少于1000ms
    private static final int FAST_CLICK_DELAY_TIME = 2000;
    @Override
    public void onClick(View v) {
        if ( !TimeUtil.invalidTime(2000)){
            AppFramentUtil.logCatUtil.v("无效：");
            return;
        }
        AppFramentUtil.logCatUtil.v("有效：");

//        if (System.currentTimeMillis() - lastClickTime <= FAST_CLICK_DELAY_TIME) {//小于等于1秒
//            return;
//        }
//        lastClickTime = System.currentTimeMillis();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (getSupportFragmentManager().getFragments()!=null&&getSupportFragmentManager().getFragments().size()>0){
            for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
//        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
//            fragment.onActivityResult(requestCode, resultCode, data);
//        }
//
//        try {
//            FragmentManager fm = getSupportFragmentManager();
//            int index = requestCode >> 16;
//            if (index != 0) {
//                index--;
//                if (fm.getFragments() == null || index < 0
//                        || index >= fm.getFragments().size()) {
//                    Log.w(TAG, "Activity result fragment index out of range: 0x"
//                            + Integer.toHexString(requestCode));
//                    super.onActivityResult(requestCode, resultCode, data);
//                    return;
//                }
//                Fragment frag = fm.getFragments().get(index);
//                if (frag == null) {
//                    Log.w(TAG, "Activity result no fragment exists for index: 0x"
//                            + Integer.toHexString(requestCode));
//                    super.onActivityResult(requestCode, resultCode, data);
//                } else {
//                    handleResult(frag, requestCode, resultCode, data);
//                }
//                return;
//            } else {
//                super.onActivityResult(requestCode, resultCode, data);
//            }
//        } catch (NullPointerException e) {
//            super.onActivityResult(requestCode, resultCode, data);
//        } catch (IndexOutOfBoundsException e) {
//            super.onActivityResult(requestCode, resultCode, data);
//        }


    }

    /**
     * 递归调用，对所有子Fragement生效
     *
     * @param frag
     * @param requestCode
     * @param resultCode
     * @param data
     */
    private void handleResult(Fragment frag, int requestCode, int resultCode,
                              Intent data) {
        frag.onActivityResult(requestCode & 0xffff, resultCode, data);
        List<Fragment> frags = frag.getChildFragmentManager().getFragments();
        if (frags != null) {
            for (Fragment f : frags) {
                if (f != null)
                    handleResult(f, requestCode, resultCode, data);
            }
        }
    }
}
