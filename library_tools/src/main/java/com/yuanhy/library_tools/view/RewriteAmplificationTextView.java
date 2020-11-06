package com.yuanhy.library_tools.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.yuanhy.library_tools.R;
import com.yuanhy.library_tools.app.AppFramentUtil;
import com.yuanhy.library_tools.util.ApiObsoleteUtil;

public class RewriteAmplificationTextView extends android.support.v7.widget.AppCompatTextView{
      float textSiziFinal = 12;//布局文件中字体大小
    public RewriteAmplificationTextView(Context context) {
        super(context);
    }

    public RewriteAmplificationTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RewriteAmplificationTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTextSiziFinal(float textSiziFinal) {
        this.textSiziFinal = textSiziFinal;
        setTextSize(textSiziFinal);
    }
    int difference=4;

    public void setDifference(int difference) {
        this.difference = difference;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        AppFramentUtil.logCatUtil.v(getClass().getName(),"event.getAction():"+event.getAction());
        if (event.getAction()==MotionEvent.ACTION_DOWN){
            if (textSiziFinal==0){
                textSiziFinal = 12;
            }
            AppFramentUtil.logCatUtil.v(getClass().getName(),"RewriteRadioButton:textSiziFinal+difference:"+textSiziFinal+difference);
            setTextSize(textSiziFinal+difference);
            ApiObsoleteUtil.setTextColor(getContext(),this, R.color.rescolor_ff5143);
        } else {
            AppFramentUtil.logCatUtil.v(getClass().getName(),"RewriteRadioButton:textsizi2:"+textSiziFinal);
            ApiObsoleteUtil.setTextColor(getContext(),this, R.color.rescolor_b9bac8);
            setTextSize(textSiziFinal);
            if (onClick!=null)
            onClick.onClick(this);

        }
        return true;
    }
    OnClick onClick;

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }

    public interface OnClick{
        void onClick(View view);
}
}
