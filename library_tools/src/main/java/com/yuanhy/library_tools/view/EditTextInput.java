package com.yuanhy.library_tools.view;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.Toast;

import com.yuanhy.library_tools.util.StringUtil;

import java.nio.charset.Charset;

/**
 * 限制字符数，非字符的长度，而是占位
 */
public class EditTextInput extends android.support.v7.widget.AppCompatEditText {
    Context context;

    public EditTextInput(Context context) {
        super(context);
        this.context = context;
    }

    public EditTextInput(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public EditTextInput(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public void setFilenrs(int input_max_length) {
        INPUT_MAX_LENGTH = input_max_length;
        setFilters(new InputFilter[]{
                new InputLengthFilter(context)});
    }

    public void setFilenrType() {
        setFilters(new InputFilter[]{
                new InputTypeFilter()});
    }

    private static int INPUT_MAX_LENGTH = 20;

    /**
     * 限制输入的字节数量。不是长度。。汉字占位2-3个跟系统得编码方式有关
     */
    private static class InputLengthFilter implements InputFilter {
        Context context;

        public InputLengthFilter(Context context) {
            this.context = context;

        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            int srcLen = StringUtil.String_length(source.toString());//.getBytes(Charset.defaultCharset()).length;
            int destLen = StringUtil.String_length(dest.toString());// dest.toString().getBytes(Charset.defaultCharset()).length;//此处判断的 字节数 是根据系统编码有关   GBK 站三个字符，手机默认的GBK，
            // UTF-8是2个   如果想固定汉字是两个可以用  StringUtil.String_length()


            if (srcLen + destLen > INPUT_MAX_LENGTH) {
                Toast.makeText(context, "超过最大长度", Toast.LENGTH_SHORT).show();
                if (source.toString().length() == 1) {
                    return "";
                }
                return source.subSequence(0, INPUT_MAX_LENGTH - destLen);
            }
            return source;
        }
    }


    /**
     * 限制输入的长度。。
     */
    public static class InputMaxLengthFilter implements InputFilter {
        Context context;

        public InputMaxLengthFilter(Context context) {
            this.context = context;

        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            int srcLen = source.toString().length();
            int destLen = dest.toString().length();
            if (srcLen + destLen > INPUT_MAX_LENGTH) {
                Toast.makeText(context, "超过最大长度", Toast.LENGTH_SHORT).show();
                if (source.toString().length() == 1) {
                    return "";
                }
                return source.subSequence(0, INPUT_MAX_LENGTH - destLen);
            }
            return source;
        }
    }


    /**
     * 判断字符串的输入类型 此处只允许输入汉字和英文
     */
    private static class InputTypeFilter implements InputFilter {
        Context context;

        public void InputLengthFilter(Context context) {
            this.context = context;

        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            if (StringUtil.isChineseCharacterEnglish(source.toString())) {
                return source;
            } else {
                return "";
            }
        }
    }

    /**
     * 判断字符串的输入类型 此处只允许输入汉字和英文,数字
     */
    public static class InputTypeNuberEngChaFilter implements InputFilter {
        Context context;
        int length = 20;

        public InputTypeNuberEngChaFilter(Context context, int length) {
            this.length = length;
            this.context = context;

        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            if (StringUtil.isChineseCharacterEnglish(source.toString()) || StringUtil.isNubm(source.toString())) {
                int srcLen = source.toString().length();
                int destLen = dest.toString().length();
                if (srcLen + destLen > length) {
                    Toast.makeText(context, "超过最大长度", Toast.LENGTH_SHORT).show();
                    if (source.toString().length() == 1) {
                        return "";
                    }
                    return source.subSequence(0, length - destLen);
                }
                return source;
            } else {
                return "";
            }
        }
    }

    /**
     * 判断字符串的输入类型 此处只允许输入数字和英文
     */
    public static class InputTypeNumberAndEinglishFilter implements InputFilter {//isChina
        Context context;
        int length = 100;

        public InputTypeNumberAndEinglishFilter() {
        }

        public InputTypeNumberAndEinglishFilter(Context context, int length) {
            this.length = length;
            this.context = context;

        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            if (StringUtil.letter(source.toString()) || StringUtil.isNumeric(source.toString())) {
                int srcLen = source.toString().length();
                int destLen = dest.toString().length();
                if (srcLen + destLen > length) {
                    Toast.makeText(context, "超过最大长度", Toast.LENGTH_SHORT).show();
                    if (source.toString().length() == 1) {
                        return "";
                    }
                    return source.subSequence(0, length - destLen);
                }
                return source;
            } else {
                return "";
            }
        }
    }
    /**
     * 判断字符串的输入类型 不允许输入中文
     */
    public static class InputTypeNoChinaFilter implements InputFilter {//is
        Context context;
        int length = 100;

        public InputTypeNoChinaFilter() {
        }

        public InputTypeNoChinaFilter(Context context, int length) {
            this.length = length;
            this.context = context;

        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            if (!StringUtil.isChina(source.toString())) {
                int srcLen = source.toString().length();
                int destLen = dest.toString().length();
                if (srcLen + destLen > length) {
                    Toast.makeText(context, "超过最大长度", Toast.LENGTH_SHORT).show();
                    if (source.toString().length() == 1) {
                        return "";
                    }
                    return source.subSequence(0, length - destLen);
                }
                return source;
            } else {
                return "";
            }
        }
    }

    /**
     * 限制小说点后的位数
     */
    public static class InputTypeNumberDecimalFilter implements InputFilter {
        Context context;
        int endlenth = 0;

        public InputTypeNumberDecimalFilter(Context context, int end) {
            this.context = context;
            this.endlenth = end;
        }

        public void setEnd(int end) {
            this.endlenth = end;
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            // source:当前输入的字符
            // start:输入字符的开始位置
            // end:输入字符的结束位置
            // dest：当前已显示的内容
            // dstart:当前光标开始位置
            // dent:当前光标结束位置
            if (dest == null) {
                return null;
            }
            if (dest.length() == 0 && source.equals(".")) {
                return "0.";
            }
            String dValue = dest.toString();
            String destStr = dest.toString();
            if (source.equals(".")) {//输入的字符串是小数点
                if (dstart == 0) {//最开头
                    if (!TextUtils.isEmpty(dValue) && dValue.length() > endlenth) {
//                        String s=source.subSequence(0, endlenth).toString();
//                       S s1+"."+s;
                        Toast.makeText(context, "超过" + endlenth + "位精确度", Toast.LENGTH_SHORT).show();
                        return "";
                    } else {
                        return null;
                    }
                } else {
                    String s1 = dValue.substring(0, dstart);
                    String s2 = dValue.substring(dend, dValue.length());
                    if (!TextUtils.isEmpty(s2) && s2.length() > endlenth) {
//                        String s=source.subSequence(0, endlenth).toString();
//                       S s1+"."+s;
                        Toast.makeText(context, "超过" + endlenth + "位精确度", Toast.LENGTH_SHORT).show();
                        return "";
                    } else {
                        return null;
                    }
                }
            }
            String[] splitArray = dValue.split("\\.");
            if (splitArray.length > 1) {
                if (splitArray[0].length() < dstart) {
                    String dotValue = splitArray[1];
                    if (dotValue.length() >= endlenth) {//输入框小数的位数
                        return "";
                    }
                }
            }
            return null;

        }
    }


}
