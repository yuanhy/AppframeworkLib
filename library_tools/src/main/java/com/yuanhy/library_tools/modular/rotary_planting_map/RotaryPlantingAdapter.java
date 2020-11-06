package com.yuanhy.library_tools.modular.rotary_planting_map;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;

import io.reactivex.annotations.NonNull;

public class RotaryPlantingAdapter extends PagerAdapter {
    ArrayList<View> mViewList = new ArrayList<>();

    public RotaryPlantingAdapter() {
    }

    public void setmViewList(ArrayList<View> mViewList) {
        this.mViewList.clear();
        this.mViewList.addAll(mViewList);
    }

    @Override
    public int getCount() {




        if (mViewList != null && mViewList.size() > 0) {
            if (mViewList.size() == 1) {
                return 1;
            }
            return Integer.MAX_VALUE;
        }
        return 0;

    }

//    @Override
//    public int getItemPosition(@android.support.annotation.NonNull Object object) {
//        return super.getItemPosition(object);
//    }
//
//    @Override
//    public T getItem(int position) {
//        return mDatas.get(position % mDatas.size());
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return (position % mDatas.size());
//    }
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {//必须实现，实例化
        position = position % mViewList.size();
//======
        ViewGroup parentViewGroup = (ViewGroup) mViewList.get(position).getParent();
        if (parentViewGroup != null ) {
            parentViewGroup.removeView(mViewList.get(position));
        }
//===============

        container.addView(mViewList.get(position));
        return mViewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {//必须实现，销毁
        position = position % mViewList.size();
        container.removeView(mViewList.get(position));
    }


}
