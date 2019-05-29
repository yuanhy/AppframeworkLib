package com.yuanhy.appframework;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yuanhy.library_tools.activity.BaseActivity;
import com.yuanhy.library_tools.presenter.BasePresenter;

public class MainActivity extends BaseActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void setTransparent() {
    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }
}
