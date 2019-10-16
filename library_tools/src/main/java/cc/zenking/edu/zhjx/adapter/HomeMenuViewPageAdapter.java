package cc.zenking.edu.zhjx.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class HomeMenuViewPageAdapter extends PagerAdapter {
	ArrayList<View> mViewList = new ArrayList<>();
	public HomeMenuViewPageAdapter(){
	}
	public void setmViewList(ArrayList<View> mViewList){
		this.mViewList.clear();
		this.mViewList.addAll(mViewList);

	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {//必须实现，实例化
		container.addView(mViewList.get(position));
		return mViewList.get(position);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {//必须实现，销毁
		container.removeView(mViewList.get(position));
	}


	@Override
	public int getCount() {
		if (mViewList!=null&&mViewList.size()>0){
			return mViewList.size();
		}
		return 0;
	}

	@Override
	public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
		return 	  view==object;
	}
}
