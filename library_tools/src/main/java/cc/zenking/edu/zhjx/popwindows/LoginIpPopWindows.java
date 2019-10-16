package cc.zenking.edu.zhjx.popwindows;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yuanhy.library_tools.adapter.OnItemClickListener;
import com.yuanhy.library_tools.adapter.UniversalAdapter;
import com.yuanhy.library_tools.adapter.ViewHolder;
import com.yuanhy.library_tools.popwindows.BasePopWindows;
import com.yuanhy.library_tools.util.ApiObsoleteUtil;

import java.util.ArrayList;

import cc.zenking.edu.zhjx.R;
import cc.zenking.edu.zhjx.api.AppUrl;
import cc.zenking.edu.zhjx.enty.HttpContentEnty;

import static cc.zenking.edu.zhjx.api.AppUrl.BASE_URL;


public class LoginIpPopWindows extends BasePopWindows {
	RecyclerView mRecycleListView;
	ArrayList<HttpContentEnty> httpContentEntyArrayList;
	private final UniversalAdapter universalAdapter;
	public LoginIpPopWindows(Context context, ArrayList<HttpContentEnty> httpContentEntyArrayList) {
		super((Activity) context);
		mRecycleListView = view.findViewById(R.id.mRecycleListView);
		setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
		setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);


		this.httpContentEntyArrayList = httpContentEntyArrayList;
//		listview_popwindows_lout_item
		mRecycleListView.setLayoutManager(new LinearLayoutManager(context));
		universalAdapter = new UniversalAdapter<HttpContentEnty>(context, httpContentEntyArrayList, R.layout.listview_popwindows_lout_item) {
			@Override
			public void convert(ViewHolder viewHolder, HttpContentEnty schoolCultureEnty) {
				TextView name_tv = viewHolder.itemView.findViewById(R.id.name_tv);
				if (BASE_URL.equals(schoolCultureEnty.ip)){
					ApiObsoleteUtil.BackgroundColor(name_tv,context,R.color.color_2692ff);
				}else {
					ApiObsoleteUtil.BackgroundColor(name_tv,context,R.color.color_ffffff);
				}
				name_tv.setText(schoolCultureEnty.name);
			}
		};
		universalAdapter.setOnItemClickListener(new OnItemClickListener<HttpContentEnty>() {

			@Override
			public void onItemClick(ViewGroup var1, View var2, HttpContentEnty var3, int var4) {
				BASE_URL=var3.ip;
				dismiss();
			}

			@Override
			public boolean onItemLongClick(ViewGroup var1, View var2, HttpContentEnty var3, int var4) {
				return false;
			}
		});
		mRecycleListView.setAdapter(universalAdapter);

	}

	@Override
	public int getLoutResourceId() {
		return R.layout.listview_popwindows_lout;
	}
}
