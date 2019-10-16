package cc.zenking.edu.zhjx.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * Created by John on 2018/6/5.
 */

public class ShowPicViewPager extends ViewPager {
    private boolean isTouch = true;

    public ShowPicViewPager(Context context) {
        super(context);
    }

    public ShowPicViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!isTouch) {
            return true;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!isTouch) {
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    public void setTouch(boolean isTouch) {
        this.isTouch = isTouch;
    }
}
