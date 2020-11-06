package com.yuanhy.library_tools.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.yuanhy.library_tools.app.AppFramentUtil;
import com.yuanhy.library_tools.util.SystemUtile;

public class RewriteRadioButton extends android.support.v7.widget.AppCompatRadioButton {
      float textSiziFinal = 0;//布局文件中字体大小
    public RewriteRadioButton(Context context) {
        super(context);
    }

    public RewriteRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RewriteRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTextSiziFinal(float textSiziFinal) {
        this.textSiziFinal = textSiziFinal;
    }
    int difference=2;

    public void setDifference(int difference) {
        this.difference = difference;
    }

    @Override
    public void setChecked(boolean checked) {
        if (textSiziFinal==0){
            textSiziFinal = 16;
        }
        AppFramentUtil.logCatUtil.v("RewriteRadioButton:textsizi:"+textSiziFinal);
        if (checked){
            setTextSize(textSiziFinal+difference);
        }else {
            setTextSize(textSiziFinal);
        }
        super.setChecked(checked);
    }
}
