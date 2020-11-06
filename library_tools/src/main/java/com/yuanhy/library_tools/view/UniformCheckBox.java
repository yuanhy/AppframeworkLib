package com.yuanhy.library_tools.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.CheckBox;

import com.yuanhy.library_tools.util.SystemUtile;

public class UniformCheckBox extends android.support.v7.widget.AppCompatCheckBox {
    float maxSizi = 14;//最大字体
    float minSizi = 8;//最小字体
    float textSizi = 14;//当前文字的大小
    float width;//布局文件的宽度

    public UniformCheckBox(Context context) {
        super(context);
    }

    public UniformCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UniformCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setMyTextSizi(float textSizi) {
        this.textSizi = textSizi;
    }


    public void setMaxSizi(float maxSizi) {

        maxSizi= maxSizi/ Resources.getSystem().getDisplayMetrics().scaledDensity;
        this.maxSizi = maxSizi;
        setTextSize(maxSizi);
        setMyTextSizi(maxSizi);
        if (TextUtils.isEmpty(getText().toString())){
            setText("");
        }else {
            setText(getText().toString());
        }

    }

    public void setMinSizi(int minSizi) {
        this.minSizi = minSizi;
    }

    int contentLenth = 0;
    int deviation = 1;//每次变化的偏差值
    float density;

    public void setDensity(float density) {
        this.density = density;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        String text = getText().toString();
        if (TextUtils.isEmpty(text)) {
            return;
        }
        int themLength = text.length();//输入文本的长度
        float wS = 0;//w * themLength;//计算输入内容的宽度

        for (int i = 0; i < themLength; i++) {
            wS = wS + getPaint().measureText(String.valueOf(text.charAt(i)));
        }
        wS=   wS+ 26*Resources.getSystem().getDisplayMetrics().scaledDensity;
        width = getWidth();//view 的总宽度

        Log.v("111111", "布局文件宽度：" + width + "  计算输入内容的宽度：" + wS);

        if (wS > width) {
            bijiaoJIan(text);
        } else if (wS < width//内容文本  小于布局文件的宽度
                && getNextWitgh(wS, textSizi, deviation) < width) {//判断变大一个偏差值之后是否也小于
            bijiaoJIa(text);
        }
        contentLenth = text.length();
    }
    private void bijiaoJIan(CharSequence text) {
        int themLength = text.toString().length();//输入文本的长度
        float wS = 0;//w * themLength;//计算输入内容的宽度
        for (int i = 0; i < themLength; i++) {
            wS = wS + getPaint().measureText(String.valueOf(text.charAt(i)));
        }
        wS=   wS+ 26*Resources.getSystem().getDisplayMetrics().scaledDensity;
        if (width == 0) {//获取一次就不用在获取了
            width = getWidth();//view 的总宽度
        }
        while (wS > width) {
            if (textSizi > minSizi) {//文字超过  布局的宽度  字体 -2
                textSizi = textSizi - deviation;
                Log.v("1111112", " -设置字体：" + textSizi);
                setTextSize(textSizi);

                wS = 0;//w * themLength;//计算输入内容的宽度
                for (int i = 0; i < themLength; i++) {
                    wS = wS + getPaint().measureText(String.valueOf(text.charAt(i)));
                }
                wS=   wS+ 26*Resources.getSystem().getDisplayMetrics().scaledDensity;
            } else {
                break;
            }
        }
    }

    private void bijiaoJIa(CharSequence text) {

        int themLength = text.toString().length();//输入文本的长度
        float wS = 0;
        for (int i = 0; i < themLength; i++) {
            wS = wS + getPaint().measureText(String.valueOf(text.charAt(i)));
        }
        wS=   wS+ 26*Resources.getSystem().getDisplayMetrics().scaledDensity;
        if (width == 0) {//获取一次就不用在获取了
            width = getWidth();//view 的总宽度
        }
        while (wS < width//内容文本  小于布局文件的宽度
                && getNextWitgh(wS, textSizi, deviation) < width) {
            if (textSizi < maxSizi//如果  字体比最大的字体小。 那么字体+2
                    && themLength < contentLenth) {//如果  上次的字体变小导致长度 变小 再次缩减文字不可变大
                textSizi = textSizi + deviation;
                Log.v("111111", " +设置字体：" + textSizi);
                setTextSize(textSizi);

                wS = 0;//w * themLength;//计算输入内容的宽度
                for (int i = 0; i < themLength; i++) {
                    wS = wS + getPaint().measureText(String.valueOf(text.charAt(i)));
                }
                wS=   wS+ 26*Resources.getSystem().getDisplayMetrics().scaledDensity;
            } else {
                break;
            }
        }
    }
    /**
     *
     * @param width 现在的宽度
     * @param currentSizi 当前字体
     * @param deviation 误差字体值
     * @return
     */
    private int getNextWitgh(float width,float currentSizi,int deviation){
        int   widthS= (int) ((int) (width*(currentSizi+deviation)/currentSizi)  );

        return widthS;
    }
}
