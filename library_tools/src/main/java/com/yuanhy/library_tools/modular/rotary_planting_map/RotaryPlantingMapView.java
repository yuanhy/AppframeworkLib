package com.yuanhy.library_tools.modular.rotary_planting_map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.viewpager.widget.ViewPager;

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

	public RotaryPlantingMapView(Context context, ViewPager rotary_planting_viewpage, long period, RadioGroup state_rdgroup, ArrayList<T> rotaryPlantingEntyArrayList) {
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
		rotary_planting_viewpage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int i, float v, int i1) {
				AppFramentUtil.logCatUtil.i(TAG, "onPageScrolled i: " + i + " index: " + i1);
			}

			@Override
			public void onPageSelected(int i) {
				int index = i % rotaryPlantingEntyArrayList.size();
				rotaryPlantingIndex = index;
				RadioButton radioButton = (RadioButton) state_rdgroup.getChildAt(index);
				radioButton.setChecked(true);
				if (context == null)
					return;
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
				if (viewArrayList.size() > 0)
					rotary_planting_viewpage.setCurrentItem(rotary_planting_viewpage.getCurrentItem() + 1);
			}
		});
		rotaryPlantingPresenter.interval(period);
	}

	public void setDatas(ArrayList<T> rotaryPlantingEntyArrayList) {
		this.rotaryPlantingEntyArrayList = rotaryPlantingEntyArrayList;
		upRotaryPlantingDataView();
	}

	public void setRoundDp(Context context, int mBorderRadius) {
		this.mBorderRadius = mBorderRadius;
	}

	private void upRotaryPlantingDataView() {
//		rotaryPlantingPresenter.cancelDisposableInterval();
		boolean isAddChild = false;
		AppFramentUtil.logCatUtil.i(TAG, "upRotaryPlantingDataView ()");
		viewArrayList.clear();
		int childCount = state_rdgroup.getChildCount();
		if (childCount != 0 && childCount != rotaryPlantingEntyArrayList.size()) {
			AppFramentUtil.logCatUtil.i(TAG, "upRotaryPlantingDataView ()-->state_rdgroup.removeAllViews");
			state_rdgroup.removeAllViews();
			isAddChild = true;
		}
		if (childCount == 0) {
			isAddChild = true;
		}
		for (int i = 0; i < rotaryPlantingEntyArrayList.size(); i++) {

			ImageView imageView = new ImageView(context);
//			imageView.setRoundDp(context,mBorderRadius);
			imageView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					onClickView(rotaryPlantingIndex);
				}
			});
			imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
			imageView.setId(i);
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			onImageView(imageView, rotaryPlantingEntyArrayList.get(i));

			viewArrayList.add(imageView);
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
	}

	//	public static class ViewHolder{
//		View itemView;
//	}
	public abstract void onImageView(ImageView imageView, T t);

	public abstract void onItemView(ViewPager rotary_planting_viewpage, RadioGroup state_rdgroup, int rotaryPlantingIndex);

	public abstract void onClickView(int rotaryPlantingIndex);

	//	public void onBindViewHolder(ViewHolder holder, int position) {
//		this.setListener(position, holder);
//		this.convert(holder, this.rotaryPlantingEntyArrayList.get(position));
//	}
//
//
//	// 将 var2的数据 显示在ViewHolder 的对应 View 中
//	public abstract void convert(ViewHolder var1, T var2);
//
//	// 将 var2的数据 显示在ViewHolder 的对应 View 中
//	public abstract void setListener(int position, ViewHolder var2);
	public void onCloss() {
		rotaryPlantingPresenter.cancelDisposable();
	}
	public void onInterval(long period) {
		rotaryPlantingPresenter.interval(period);
	}

}
