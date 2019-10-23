package com.yuanhy.library_tools.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.yuanhy.library_tools.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;


public abstract class ListViewAdapter<T> extends BaseAdapter {
	ArrayList<T> dataArrayList = new ArrayList<>();
	Context mContext;
	private int layoutId;
	private int headerLayoutId;
	public ListViewAdapter(Context context, int layoutId, ArrayList<T> arrayList) {
		mContext = context;
		this.layoutId = layoutId;
		dataArrayList.addAll(arrayList);
	}

	public void setDataArrayList(ArrayList<T> dataArrayList) {
		this.dataArrayList.clear();
		this.dataArrayList.addAll(dataArrayList);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		if (headerLayoutId>0){
			return dataArrayList.size()+1;
		}else {
			return dataArrayList.size();
		}

	}

	@Override
	public T getItem(int position) {
		if (headerLayoutId>0){
			if (position==0){
				return  null;
			}
			return dataArrayList.get(position-1);
		}else {
			return dataArrayList.get(position);
		}

	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 获取itenmView中需要实例化的 id对象
	 * @return
	 */
	public abstract int[]   getConvertFindViewById();
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHodle viewHodle = null;
		if (headerLayoutId ==0) {
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
				viewHodle = new ViewHodle();
				viewHodle.itemView = convertView;
				int[] ids=getConvertFindViewById();
				if (ids!=null&&ids.length>0){
					for (int id:ids){
						viewHodle.viewHashMap.put(id,convertView.findViewById(id));
					}
				}
				convertView.setTag(viewHodle);
			} else {
				viewHodle = (ViewHodle) convertView.getTag();
			}
			convert(viewHodle, getItem(position), position);
			return convertView;
		} else {
			if (position == 0) {
				HeaderViewHodle hideViewHodle = null;
				convertView = LayoutInflater.from(mContext).inflate(headerLayoutId, parent, false);
				hideViewHodle = new HeaderViewHodle();
				hideViewHodle.itemView = convertView;
				convertView.setTag(hideViewHodle);
				convertHideView(hideViewHodle, null, 0);
				return convertView;
			} else {

				if (convertView == null) {
					convertView = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
					viewHodle = new ViewHodle();
					viewHodle.itemView = convertView;
					int[] ids=getConvertFindViewById();
					if (ids!=null&&ids.length>0){
						for (int id:ids){
							viewHodle.viewHashMap.put(id,convertView.findViewById(id));
						}
					}
					convertView.setTag(viewHodle);
				} else {
					Object object = convertView.getTag();
					if (object instanceof ViewHodle) {
						viewHodle = (ViewHodle) convertView.getTag();
					} else if (object instanceof HeaderViewHodle) {
						convertView = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
						viewHodle = new ViewHodle();
						viewHodle.itemView = convertView;
						int[] ids=getConvertFindViewById();
						if (ids!=null&&ids.length>0){
							for (int id:ids){
								viewHodle.viewHashMap.put(id,convertView.findViewById(id));
							}
						}
						convertView.setTag(viewHodle);
					}
				}
				convert(viewHodle, getItem(position), position);
				return convertView;

			}
		}

	}


	/**
	 * 添加头部View
	 */
	public void addHeaderView(int mHeaderLayoutId) {
		headerLayoutId=mHeaderLayoutId;
	}
	/**
	 * 添加头部View
	 */
	public void reMoveHeaderView() {
		headerLayoutId=-1;
	}
	public static class ViewHodle {
		public View itemView;
		public HashMap<Integer,Object> viewHashMap =new HashMap<>();
		public  <Y extends View> Y  getFindViewById(int id){//获取的类型需要自己强转例如（TextView）
			if (viewHashMap.get(id)==null){
				return null;
			}
			return (Y) viewHashMap.get(id);
		}
	}

	public static class HeaderViewHodle {
		public View itemView;
	}


	// 将 var2的数据 显示在ViewHolder 的对应 View 中
	public abstract void convert(ViewHodle var1, T var2, int position);

	// 将 var2的数据 显示在ViewHolder 的对应 View 中
	public abstract void convertHideView(HeaderViewHodle var1, T var2, int position);
}
