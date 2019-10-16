package cc.zenking.edu.zhjx.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cc.zenking.edu.zhjx.R;
import cc.zenking.edu.zhjx.enty.childEnty.Child;

public class ChildAdapter extends BaseAdapter {
	LayoutInflater mInflater;
	List<Child> list = new ArrayList<>();
	Context context;

	public ChildAdapter(Context context, List<Child> list) {
		mInflater = LayoutInflater.from(context);
		this.list.addAll(list);
		this.context = context;
	}

	public void setData(List<Child> list) {
		this.list.clear();
		this.list.addAll(list);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Child getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = mInflater.inflate(R.layout.childadapter_item,
				parent, false);
		ViewHolder viewHolder = new ViewHolder();
		viewHolder.tv_child_name = (TextView) convertView
				.findViewById(R.id.tv_child_name);
		viewHolder.main_lout = (LinearLayout) convertView
				.findViewById(R.id.main_lout);
		if (getItem(position).isSelter) {
			viewHolder.tv_child_name.setTextColor(Color.WHITE);
			viewHolder.tv_child_name.setBackgroundColor(Color.parseColor("#2692ff"));
		} else {
			viewHolder.tv_child_name.setTextColor(Color.parseColor("#161616"));
			viewHolder.tv_child_name.setBackgroundColor(Color.WHITE);
		}
		viewHolder.tv_child_name.setText(getItem(position).name);
		return convertView;
	}

	public static class ViewHolder {
		TextView tv_child_name;
		LinearLayout main_lout;

	}
}


