package com.yuanhy.library_tools.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.github.lzyzsd.jsbridge.BridgeWebView;

public class WebView extends BridgeWebView {

    public WebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public WebView(Context context) {
        super(context);
    }

}
