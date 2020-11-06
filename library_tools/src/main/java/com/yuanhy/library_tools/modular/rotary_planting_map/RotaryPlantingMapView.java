package com.yuanhy.library_tools.modular.rotary_planting_map;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import com.bumptech.glide.Glide;
import com.yuanhy.library_tools.R;
import com.yuanhy.library_tools.adapter.ViewHolder;
import com.yuanhy.library_tools.app.AppFramentUtil;
import com.yuanhy.library_tools.image.GlideUtil;
import com.yuanhy.library_tools.rxjava.RxjavaUtilInterval;
import com.yuanhy.library_tools.util.ApiObsoleteUtil;
import com.yuanhy.library_tools.util.SystemUtile;
import com.yuanhy.library_tools.util.YCallBack;
import com.yuanhy.library_tools.view.RoundImageView;

import java.util.ArrayList;

/**
 * 轮播图
 */
public abstract class RotaryPlantingMapView<T> {
    String TAG = "RotaryPlantingMapView";
    Context context;
    private ArrayList<View> viewArrayList;
    private RotaryPlantingAdapter rotaryPlantingAdapter;
    private ViewPager rotary_planting_viewpage;
    private RxjavaUtilInterval rotaryPlantingPresenter;
    private long period;
    private RadioGroup state_rdgroup;
    ArrayList<T> rotaryPlantingEntyArrayList;
    private int rotaryPlantingIndex;
    private int mBorderRadius = 10;
//ViewHolder viewHolder;

    /**
     * @param context
     * @param rotary_planting_viewpage
     * @param period                      多少秒后执行
     * @param state_rdgroup
     * @param rotaryPlantingEntyArrayList
     */
    public RotaryPlantingMapView(Context context, ViewPager rotary_planting_viewpage, long period,
                                 RadioGroup state_rdgroup, ArrayList<T> rotaryPlantingEntyArrayList) {
        this.rotary_planting_viewpage = rotary_planting_viewpage;
        this.period = period;
        this.state_rdgroup = state_rdgroup;
        this.rotaryPlantingEntyArrayList = rotaryPlantingEntyArrayList;
        this.context = context;
        initData();
    }

    private void initData() {
        viewArrayList = new ArrayList<>();
        rotaryPlantingAdapter = new RotaryPlantingAdapter();
        rotary_planting_viewpage.setAdapter(rotaryPlantingAdapter);
        rotary_planting_viewpage.setPageTransformer(true, new ZoomOutPageTransformer());//设置切换动画
        rotary_planting_viewpage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

                AppFramentUtil.logCatUtil.i(TAG, "onPageScrolled i: " + i + " index: " + i1);
            }

            @Override
            public void onPageSelected(int i) {
                int index = i % rotaryPlantingEntyArrayList.size();
                rotaryPlantingIndex = index;
                if (context == null)
                    return;
                RadioButton radioButton;
                if (state_rdgroup != null) {
                    radioButton = (RadioButton) state_rdgroup.getChildAt(index);
                    radioButton.setChecked(true);
                }
                onItemView(rotary_planting_viewpage, state_rdgroup, rotaryPlantingIndex);
                AppFramentUtil.logCatUtil.i(TAG, "i: " + i + " index: " + index);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        rotaryPlantingPresenter = new RxjavaUtilInterval(new YCallBack() {
            @Override
            public void requestSuccessful(Object o) {
                if (viewArrayList.size() > 1) {
                    int index = rotary_planting_viewpage.getCurrentItem() + 1;
                    AppFramentUtil.logCatUtil.i(TAG, "轮询的位置 index: " + index);
                    rotary_planting_viewpage.setCurrentItem(index);
                }

            }
        });
        rotaryPlantingPresenter.interval(period);
    }

    //切换动画
    class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MAX_SCALE = 1.0f;
        private static final float MIN_SCALE = 0.8f;

        @Override
        public void transformPage(View view, float position) {
            if (position < -1) {
//                view.setScaleX(MIN_SCALE);
                view.setScaleY(MIN_SCALE);
            } else if (position <= 1) {
                float scaleFactor = MIN_SCALE + (1 - Math.abs(position)) * (MAX_SCALE - MIN_SCALE);
//                view.setScaleX(scaleFactor);
//                if (position > 0) {
//                    view.setTranslationX(-scaleFactor * 2);
//                } else if (position < 0) {
//                    view.setTranslationX(scaleFactor * 2);
//                }
                view.setScaleY(scaleFactor);
            } else {
//                view.setScaleX(MIN_SCALE);
                view.setScaleY(MIN_SCALE);
            }
        }
    }

    public void setDatas(ArrayList<T> rotaryPlantingEntyArrayList) {
        this.rotaryPlantingEntyArrayList = rotaryPlantingEntyArrayList;
        rotary_planting_viewpage.removeAllViews();
        upRotaryPlantingDataView();
    }

    public void setRoundDp(Context context, int mBorderRadius) {
        this.mBorderRadius = mBorderRadius;
    }

    private void upRotaryPlantingDataView() {
        boolean isAddChild = false;
        AppFramentUtil.logCatUtil.i(TAG, "upRotaryPlantingDataView ()");
        viewArrayList.clear();
        if (state_rdgroup != null) {
            int childCount = state_rdgroup.getChildCount();
            if (childCount != 0 && childCount != rotaryPlantingEntyArrayList.size()) {
                AppFramentUtil.logCatUtil.i(TAG, "upRotaryPlantingDataView ()-->state_rdgroup.removeAllViews");
                state_rdgroup.removeAllViews();
                isAddChild = true;
            }
            if (childCount == 0) {
                isAddChild = true;
            }
        }

        for (int i = 0; i < rotaryPlantingEntyArrayList.size(); i++) {
            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setBackgroundColor(Color.WHITE);


            ImageView imageView = new ImageView(context);
//			imageView.setRoundDp(context,mBorderRadius);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickView(rotaryPlantingIndex);
                }
            });
            LinearLayout.LayoutParams imageViewLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            imageViewLp.leftMargin = SystemUtile.dp2px(6);
            imageViewLp.rightMargin = SystemUtile.dp2px(6);
            imageViewLp.topMargin = SystemUtile.dp2px(10);
//			imageViewLp.setMargins(SystemUtile.dp2px(12), SystemUtile.dp2px(6), SystemUtile.dp2px(12), 0);

            imageView.setId(i);
            imageView.setScaleType(ImageView.ScaleType. FIT_XY);
            imageView.setLayoutParams(imageViewLp);
            onImageView(imageView, rotaryPlantingEntyArrayList.get(i));
            linearLayout.addView(imageView);
            viewArrayList.add(linearLayout);
            if (isAddChild) {
                RadioButton radio_bt = new RadioButton(context);
                RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(SystemUtile.dp2px(5), SystemUtile.dp2px(5));
                layoutParams.leftMargin = SystemUtile.dp2px(3);
                layoutParams.rightMargin = SystemUtile.dp2px(3);
                radio_bt.setLayoutParams(layoutParams);
                ApiObsoleteUtil.setBackground(radio_bt, context, R.drawable.rotary_planting_rb_state_bj);
                radio_bt.setButtonDrawable(null);
                state_rdgroup.addView(radio_bt, i);
            }
        }//radio_bt
        rotaryPlantingAdapter.setmViewList(viewArrayList);
        rotaryPlantingAdapter.notifyDataSetChanged();

        if (viewArrayList.size() > 0)
            rotary_planting_viewpage.setCurrentItem(0);
    }

    public abstract void onImageView(ImageView imageView, T t);

    public abstract void onItemView(ViewPager rotary_planting_viewpage, RadioGroup state_rdgroup, int rotaryPlantingIndex);

    public abstract void onClickView(int rotaryPlantingIndex);

    public void onCloss() {
        rotaryPlantingPresenter.cancelDisposable();
    }

    public void onInterval(long period) {
        onCloss();
        rotaryPlantingPresenter.interval(period);
    }

}
