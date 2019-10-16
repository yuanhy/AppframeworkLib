package cc.zenking.edu.zhjx.ui.activity;

import androidx.annotation.AnyThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yuanhy.library_tools.activity.BaseActivity;
import com.yuanhy.library_tools.app.AppAcitivityUtile;
import com.yuanhy.library_tools.file.FileDownUtile;
import com.yuanhy.library_tools.file.FileUtil;
import com.yuanhy.library_tools.http.ProgressListener;
import com.yuanhy.library_tools.presenter.BasePresenter;
import com.yuanhy.library_tools.util.AppCommonTool;
import com.yuanhy.library_tools.util.SystemUtile;
import com.yuanhy.library_tools.util.YCallBack;
import com.yuanhy.library_tools.view.ListViewNestingListView;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import cc.zenking.edu.zhjx.R;
import cc.zenking.edu.zhjx.adapter.HolidayMsgTimeAdapter;
import cc.zenking.edu.zhjx.api.LeaveApi;
import cc.zenking.edu.zhjx.enty.Date;
import cc.zenking.edu.zhjx.enty.LeaveInfoEnty;
import cc.zenking.edu.zhjx.enty.Result;
import cc.zenking.edu.zhjx.enty.homeEnty.Data;
import cc.zenking.edu.zhjx.presenter.LeavePresenter;
import cc.zenking.edu.zhjx.utils.CachUtile;
import cc.zenking.edu.zhjx.utils.ZhjxAppFramentUtil;
import cc.zenking.edu.zhjx.view.ShowPicViewPager;

public class HolidayMsgActivity extends BaseActivity {
	@BindView(R.id.title_name_tv)
	TextView title_name_tv;
	@BindView(R.id.tv_start_time)
	ListViewNestingListView tv_start_time;
	@BindView(R.id.ll_view_parent)
	LinearLayout ll_view_parent;

	@BindView(R.id.tv_type)
	TextView tv_type;
	@BindView(R.id.tv_content)
	TextView tv_content;
	@BindView(R.id.tv_tipmsg)
	TextView tv_tipmsg;
	@BindView(R.id.tv_backout)
	TextView tv_backout;//tv_start_time
	@BindView(R.id.ll_backout)
	LinearLayout ll_backout;
	private PopupWindow pop;
	private boolean isClick = true;

	private LeaveInfoEnty data;
	@BindView(R.id.scrollview)
	ScrollView scrollview;

	@BindView(R.id.rl_noNet)
	RelativeLayout rl_noNet;
	@BindView(R.id.rl_load)
	RelativeLayout rl_load;
	@BindView(R.id.iv_load)
	ImageView iv_load;
	@BindView(R.id.ll_pic)
	LinearLayout ll_pic;
	@BindView(R.id.tv_out_time)
	TextView tv_out_time;
	@BindView(R.id.tv_type_tag)
	TextView tv_type_tag;

	@BindView(R.id.rl_out_time)
	RelativeLayout rl_out_time;

	@BindView(R.id.title_chaild_lout)
	LinearLayout title_chaild_lout;

	String id;
	private HolidayMsgTimeAdapter holidayMsgTimeAdapter;
	ArrayList<String> dateTimeShow = new ArrayList<>();
	private LeavePresenter leavePresenter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_holiday_msg);
		id = getIntent().getStringExtra("id");
		initUnbinder();
		try {
			title_chaild_lout.setVisibility(View.GONE);
			title_name_tv.setText(ZhjxAppFramentUtil.getSelterChild(context).name);
		} catch (NullPointerException e) {
		}
		getDate();
	}

	@OnClick(R.id.tv_backout)
	public void onClicks(View v) {
		switch (v.getId()) {
			case R.id.tv_backout://撤销申请
				setPopWindow();
				break;
		}
	}

	@Override
	public void setTransparent() {

	}

	@Override
	public BasePresenter createPresenter() {
		return leavePresenter = new LeavePresenter(context, new YCallBack() {
			@Override
			public void requestSuccessful(Object o) {
				loadPopupWindow.dismiss();
				if (o instanceof LeaveInfoEnty){
					data = (LeaveInfoEnty) o;
					setViewContent();
					addCachImage();
				}else 	if (o instanceof Result){
setResult(Activity.RESULT_OK);
finish();
				}

			}

			@Override
			public void requestFail(Object o) {
				loadPopupWindow.dismiss();
				if (o instanceof LeaveInfoEnty){
					toastPopWindow.setMessge("获取详情失败").show();
				}else 	if (o instanceof Result){
					toastPopWindow.setMessge("撤销失败").show();
				}
			}
		});
	}

	private void notifyTimeAdapter(Date[] times) {
		if (times == null) {
			return;
		}
		if (holidayMsgTimeAdapter == null) {
			holidayMsgTimeAdapter = new HolidayMsgTimeAdapter(this, times);
			tv_start_time.setAdapter(holidayMsgTimeAdapter);
		}
		holidayMsgTimeAdapter.setDataArrayList(times, (ArrayList<String>) dateTimeShow,
				1, !TextUtils.isEmpty(data.label));
	}


	/**
	 * 加载中的动画
	 */
	void setamin(int type) {
		iv_load.setBackgroundResource(R.drawable.load_anim);
		AnimationDrawable animation = (AnimationDrawable) iv_load
				.getBackground();
		if (type == 1) {
			rl_load.setVisibility(View.VISIBLE);
			animation.setOneShot(false);
			animation.start();
		} else {
			if (animation != null) {
				animation.stop();
			}
			rl_load.setVisibility(View.GONE);
		}
	}

	void getDate() {
		setamin(1);
		// 根据id请求网络，获取该请假信息，回显
		if (TextUtils.isEmpty(id)) {
			rl_noNet.setVisibility(View.VISIBLE);
			return;
		}
		leavePresenter.getLeaveInfo(id);
	}

	private final String mPageName = "HolidayMsgActivity";


	void setViewContent() {
		setamin(2);
		if (TextUtils.isEmpty(data.label)) {
			tv_type_tag.setVisibility(View.GONE);
		} else {
			tv_type_tag.setVisibility(View.VISIBLE);
		}
		scrollview.setVisibility(View.VISIBLE);
		tv_type.setText(data.type.value);
		tv_content.setText(data.description);
		if (!TextUtils.isEmpty(data.lastOutTime)) {
			rl_out_time.setVisibility(View.VISIBLE);
			tv_out_time.setText(data.lastOutTime);
		} else {
			rl_out_time.setVisibility(View.GONE);
		}
		StringBuilder sb = new StringBuilder();
		if (data.times != null && data.times.length != 0) {
			for (int i = 0; i < data.times.length; i++) {
				String t1 = data.times[i].startTime;
				String t2 = data.times[i].endTime.split(" ")[1];
				if (i == data.times.length - 1) {
					sb.append(t1 + "-" + t2);
					dateTimeShow.add(t1 + "-" + t2);
				} else {
					dateTimeShow.add(t1 + "-" + t2);
					sb.append(t1 + "-" + t2 + "\n");
				}
			}

		} else {
			dateTimeShow.add(data.actTime);
		}
		notifyTimeAdapter(data.times);
		// CONFIRM为已经确认,UNPROCESS待确认，APPLY_CANCEL取消，AUDIT_CANCEL没有通过
		// System.out.println("==================="+data.adminable);
		if (!data.adminable) {
			// 不可以撤销
			ll_backout.setVisibility(View.GONE);
			tv_tipmsg.setVisibility(View.GONE);
			isClick = false;
		} else {
			ll_backout.setVisibility(View.VISIBLE);
			tv_tipmsg.setVisibility(View.VISIBLE);
		}
		if (!TextUtils.isEmpty(data.actionFile)) {
			setPic(data.actionFile);
		} else {
			if (data.partake != null) {
				setPic(data.partake.pictureUrl);
			}
		}
	}

	void setPic(String fileString) {
		if (!TextUtils.isEmpty(fileString) && !"empty".equals(fileString)) {
			ll_pic.setVisibility(View.VISIBLE);
			ll_pic.removeAllViews();
			String[] files = fileString.split("#");
			final Data[] data = new Data[files.length];
			for (int i = 0; i < files.length; i++) {
				Data d = new Data();
				d.url = files[i];
				data[i] = d;
			}
			for (int i = 0; i < files.length; i++) {
				ImageView imageView = new ImageView(this);// 调用百分比布局的根据TextView的像素值计算实际的像素值
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) getResources().getDimensionPixelSize(R.dimen.dp_55),
						 (int) getResources().getDimensionPixelSize(R.dimen.dp_55));
				params.setMargins(0, 0,  (int) getResources().getDimensionPixelSize(R.dimen.dp_7), 0);
				imageView.setLayoutParams(params);
				imageView.setScaleType(ImageView.ScaleType.FIT_XY);
				Glide.with(this).load(files[i]).dontAnimate()
						.diskCacheStrategy(DiskCacheStrategy.ALL)
						.error(R.drawable.failure)
						.into(imageView);
//				imageView.setTag(i);
				int finalI = i;
				imageView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						initPop(finalI, data);
					}
				});
				ll_pic.addView(imageView);
			}
		} else {
			ll_pic.setVisibility(View.GONE);
		}
	}


	private void setPopWindow() {
		View view = LayoutInflater.from(this)
				.inflate(R.layout.popwindow_complete, null);
		TextView update = (TextView) view.findViewById(R.id.update);
		TextView cancel = (TextView) view.findViewById(R.id.cancel);
		pop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		pop.showAtLocation(ll_view_parent, Gravity.CENTER, 0, 0);
		update.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				pop.dismiss();
				loadPopupWindow.showPopWindowCenter();
				leavePresenter.cancelLeave(id, data.casflag);
			}
		});
		cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				pop.dismiss();
				isClick = true;
			}
		});
	}
	HashMap<String,String > cachMap= new HashMap<>( );
	/**
	 * 获取手机下载的图片
	 */
	@AnyThread
	private void addCachImage(){
		File file= new File( CachUtile.getCachImagePtah(context));
		File[] files = file.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				cachMap .put(pathname.getName(),pathname.getAbsolutePath());
				return false;
			}
		});
}

	private PopupWindow imagePop;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (pop != null && pop.isShowing()) {
				pop.dismiss();
				isClick = true;
				return true;
			}
			return super.onKeyDown(keyCode, event);
		}
		return super.onKeyDown(keyCode, event);
	}

	void initPop(int posi, Data[] f) {
		View view = View.inflate(this, R.layout.popupwindow_zoompic, null);
		ShowPicViewPager viewPager = (ShowPicViewPager) view.findViewById(R.id.viewPager);
		imagePop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		ViewPagerAdapter adapter = new ViewPagerAdapter(f, this, viewPager);
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(posi, false);
		viewPager.setOnPageChangeListener(adapter);
		imagePop.setBackgroundDrawable(new BitmapDrawable());
		imagePop.setAnimationStyle(R.style.AnimationFadePop);
		imagePop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		imagePop.setClippingEnabled(false);
		imagePop.setOutsideTouchable(true);
		imagePop.setFocusable(true);
		imagePop.setTouchable(true);
		imagePop.showAtLocation(ll_view_parent, Gravity.CENTER, 0, 0);
	}

	public class ViewPagerAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {
		private Data[] files;
		HolidayMsgActivity activity;
		ShowPicViewPager viewPager;

		public ViewPagerAdapter(Data[] files, HolidayMsgActivity activity, ShowPicViewPager viewPager) {
			this.files = files;
			this.activity = activity;
			this.viewPager = viewPager;
		}

		@Override
		public int getCount() {
			return files != null ? files.length : 0;
		}

		@Override
		public Object instantiateItem(ViewGroup container, final int position) {
			View view = View.inflate(activity, R.layout.homework_viewpager_item, null);
			final RelativeLayout rl_save = (RelativeLayout) view.findViewById(R.id.rl_save);
			final TextView tv_save = (TextView) view.findViewById(R.id.tv_save);
			final TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
			final ImageView iv_download_hint = (ImageView) view.findViewById(R.id.iv_download_hint);
			if (cachMap.get(files[position].getFileCachName()) !=null) {
				iv_download_hint.setVisibility(View.GONE);
			}else {
				iv_download_hint.setVisibility(View.VISIBLE);
			}
			iv_download_hint.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					iv_download_hint.setVisibility(View.GONE);
					FileDownUtile.fileDown(context, files[position].url, CachUtile.getCachImagePtah(context)  ,
							files[position].getFileCachName(),
							new ProgressListener() {
								@Override
								public void onProgress(Object o, Object o2) {

								}

								@Override
								public void onOk(Object o) {

									runOnUiThread(new Runnable() {
										@Override
										public void run() {
											String path = (String) o;
											cachMap.put( files[position].getFileCachName(),path);
									toastPopWindow.setMessge("图片保存路径：" + path).show();
											iv_download_hint.setVisibility(View.GONE);
										}
									});
								}

								@Override
								public void onError(Object o) {
									runOnUiThread(new Runnable() {
										@Override
										public void run() {
											toastPopWindow.setMessge("图片保存失败").show();
											iv_download_hint.setVisibility(View.VISIBLE);
										}
									});
//
								}
							});
				}
			});
			PhotoView photoView = (PhotoView) view.findViewById(R.id.photoView);
			photoView.enable();
			photoView.setMaxScale(3);
			TextView tv_page = (TextView) view.findViewById(R.id.tv_page);
			tv_page.setText(position + 1 + "/" + files.length);
			rl_save.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					viewPager.setTouch(true);
					rl_save.setVisibility(View.GONE);
				}
			});
			tv_save.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

					viewPager.setTouch(true);
					rl_save.setVisibility(View.GONE);

				}
			});
			tv_cancel.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					viewPager.setTouch(true);
					rl_save.setVisibility(View.GONE);
				}
			});
			view.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (imagePop != null) {
						imagePop.dismiss();
					}
				}
			});
			photoView.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					iv_download_hint.setVisibility(View.GONE);
					rl_save.setVisibility(View.VISIBLE);
					viewPager.setTouch(false);
					return true;
				}
			});
			photoView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (imagePop != null) {
						imagePop.dismiss();
					}
				}
			});
			photoView.setVisibility(View.VISIBLE);
			Log.i("TAG", files[position].url);
			Glide.with(activity).load(files[position].url).dontAnimate()
					.placeholder(R.drawable.loadgif)
					.error(R.drawable.failure)

					.into(photoView);
			container.addView(view);
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

		}

		@Override
		public void onPageSelected(int position) {

		}

		int posi = -1;

		@Override
		public void setPrimaryItem(ViewGroup container, int position, Object object) {
//            Log.i("TAG", "position====" + position);
//			if (posi != position) {
//				ImageView imageView = (ImageView) ((View) object).findViewById(R.id.iv_download_hint);
//				imageView.setVisibility(View.VISIBLE);
//				setImageloaderHint(imageView);
//				posi = position;
//			}
		}

		@Override
		public void onPageScrollStateChanged(int state) {

		}
	}

}
