package com.yuanhy.library_tools.view;

import android.content.res.Resources;
import android.util.Log;
import android.widget.TextView;

public class TextViewUtile {
    public static void setTextWight(TextView textWight, String text, float maxSizi, float minSizi) {
        maxSizi = maxSizi / Resources.getSystem().getDisplayMetrics().scaledDensity;
        int contentLenth = 0;
        float w = textWight.getPaint().measureText("W");//当前一个文字的宽度
        int themLength = text.toString().length()+1;//输入文本的长度 +1为了预留一个字符，减小误差
        float wS = w * themLength;//计算输入内容的宽度
        int width = textWight.getWidth();//view 的总宽度
        float textSizi = maxSizi;
        int deviation = 1;
        Log.v("111111", "  textSizi：" + textSizi + "  minSizi:" + minSizi + "  deviation:" + deviation);
        if (wS > width) {

            while (wS > width) {
                if (textSizi > minSizi) {//文字超过  布局的宽度  字体 -2
                    textSizi = textSizi - deviation;
                    Log.v("1111112", " -设置字体：" + textSizi);
                    textWight.setTextSize(textSizi);
                    w = textWight.getPaint().measureText("W");//当前一个文字的宽度

                    wS = w * themLength;//计算输入内容的宽度
                } else {
                    break;
                }
            }
        } else if (wS < width//内容文本  小于布局文件的宽度
                && getNextWitgh(wS, textSizi, deviation) < width) {//判断变大一个偏差值之后是否也小于
            while (wS < width//内容文本  小于布局文件的宽度
                    && getNextWitgh(wS,textSizi,deviation) < width){
                if (textSizi < maxSizi//如果  字体比最大的字体小。 那么字体+2
                        ) {//如果  上次的字体变小导致长度 变小 再次缩减文字不可变大
                    textSizi = textSizi + deviation;
                    Log.v("111111", " +设置字体：" + textSizi);
                    textWight. setTextSize(textSizi);
                    w = textWight.getPaint().measureText("W");//当前一个文字的宽度

                    wS = w * themLength;//计算输入内容的宽度
                }else {
                    break;
                }
            }
        }


        contentLenth = text.toString().length();


    }

    /**
     * @param width       现在的宽度
     * @param currentSizi 当前字体
     * @param deviation   误差字体值
     * @return
     */
    private static int getNextWitgh(float width, float currentSizi, int deviation) {
        int widthS = (int) (width * (currentSizi + deviation) / currentSizi);
        return widthS;
    }
}
