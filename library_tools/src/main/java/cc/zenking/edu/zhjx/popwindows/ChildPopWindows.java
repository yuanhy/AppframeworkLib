package cc.zenking.edu.zhjx.popwindows;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yuanhy.library_tools.adapter.OnItemClickListener;
import com.yuanhy.library_tools.adapter.UniversalAdapter;
import com.yuanhy.library_tools.adapter.ViewHolder;
import com.yuanhy.library_tools.popwindows.BasePopWindows;
import com.yuanhy.library_tools.util.YCallBack;

import java.util.ArrayList;
import java.util.List;

import cc.zenking.edu.zhjx.R;
import cc.zenking.edu.zhjx.adapter.ChildAdapter;
import cc.zenking.edu.zhjx.enty.childEnty.Child;

public class ChildPopWindows extends BasePopWindows {
	RecyclerView child_listView;
//	ChildAdapter childAdapter;
	ArrayList<Child> childArrayList = new ArrayList<>();
	YCallBack yCallBack;
	private final UniversalAdapter<Child> universalAdapter;

	public ChildPopWindows(Context context, ArrayList<Child> childArrayList, YCallBack yCallBack) {
		super(context);
		setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
		setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
		this.yCallBack = yCallBack;
		this.childArrayList.addAll(childArrayList);
		child_listView = view.findViewById(R.id.child_RecyclerView);
		child_listView.setLayoutManager(new LinearLayoutManager(context));
		universalAdapter = new UniversalAdapter<Child>(context, childArrayList, R.layout.childadapter_item) {
			@Override
			public void convert(ViewHolder viewHolder, Child var2) {
				TextView tv_child_name = viewHolder.itemView.findViewById(R.id.tv_child_name);
				if (var2.isSelter) {
					tv_child_name.setTextColor(Color.WHITE);
					tv_child_name.setBackgroundColor(Color.parseColor("#2692ff"));
				} else {
					tv_child_name.setTextColor(Color.parseColor("#161616"));
					tv_child_name.setBackgroundColor(Color.WHITE);
				}
				tv_child_name.setText(var2.name);
			}
		};
		child_listView.setAdapter(universalAdapter);
		universalAdapter.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(ViewGroup var1, View var2, Object var3, int var4) {
				yCallBack.onOk(childArrayList.get(var4));
			}

			@Override
			public boolean onItemLongClick(ViewGroup var1, View var2, Object var3, int var4) {
				return false;
			}
		});

	}

	public void setChildArrayList(ArrayList<Child> childArrayList) {
		this.childArrayList.clear();
		this.childArrayList.addAll(childArrayList);
		universalAdapter.setDatas(this.childArrayList);
	}

	public void show() {

	}

	@Override
	public int getLoutResourceId() {
		return R.layout.child_popwindow_lout;
	}
}
