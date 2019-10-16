package cc.zenking.edu.zhjx.ui.activity;

import androidx.annotation.AnyThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.yuanhy.library_tools.activity.BaseActivity;
import com.yuanhy.library_tools.adapter.ListViewAdapter;
import com.yuanhy.library_tools.file.FileDownUtile;
import com.yuanhy.library_tools.http.ProgressListener;
import com.yuanhy.library_tools.image.GlideUtil;
import com.yuanhy.library_tools.presenter.BasePresenter;
import com.yuanhy.library_tools.util.SdCardUtil;
import com.yuanhy.library_tools.util.YCallBack;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import cc.zenking.edu.zhjx.R;
import cc.zenking.edu.zhjx.enty.Result;
import cc.zenking.edu.zhjx.enty.habitEnty.HabitType;
import cc.zenking.edu.zhjx.enty.homeEnty.Data;
import cc.zenking.edu.zhjx.popwindows.SelectPicturesPopWindows;
import cc.zenking.edu.zhjx.presenter.HabitPresenter;
import cc.zenking.edu.zhjx.utils.CachUtile;
import cc.zenking.edu.zhjx.utils.ZhjxAppFramentUtil;
import cc.zenking.edu.zhjx.view.ShowPicViewPager;
import cc.zenking.edu.zhjx.view.dragview.DragGridView;
import cc.zenking.edu.zhjx.view.imge.PicassoImageLoader;

/**
 * 添加好习惯
 */
public class AddGoodHabitResultActivity extends BaseActivity {
	HabitPresenter habitPresenter;
	HabitType habitType;
	@BindView(R.id.tvRight_tv)
	TextView tvRight_tv;
	@BindView(R.id.title_chaild_lout)
	LinearLayout title_chaild_lout;
	@BindView(R.id.title_name_tv)
	TextView title_name_tv;
	@BindView(R.id.gridview)
	DragGridView gridview;
	private ListViewAdapter<String> listViewAdapter;
	ArrayList<String> imageArrayList = new ArrayList<>();
	String addImage = "addImage";


	private static final int PHOTO_REQUEST_TAKEPHOTO = 0x33;// 拍照
	private static final int PHOTO_REQUEST_GALLERY = 0x34;// 从相册中选择
	private static final int MY_PERMISSIONS_REQUEST_SELECT_PHOTO = 7;
	private static final int MY_PERMISSIONS_REQUEST_TAKE_PHOTO = 6;
	private SelectPicturesPopWindows selectPicturesPopWindows;
	private ArrayList<ImageItem> imageItems = new ArrayList<>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		habitType = (HabitType) getIntent().getSerializableExtra("habitType");
		setContentView(R.layout.activity_add_good_habit_result);
		initUnbinder();
		initView();

	}

	@OnClick({R.id.tvRight_tv, R.id.image_back})
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
			case R.id.image_back:
				finish();
			case R.id.tvRight_tv:
				addGoodHabit();
				break;
			case R.id.iv_delete_file:


				break;
			case R.id.iv_upload_file:
				break;
		}
	}

	private void initView() {
		title_chaild_lout.setVisibility(View.GONE);
		title_name_tv.setText(ZhjxAppFramentUtil.getSelterChild(context).name);
		initGridView();
	}


	void initGridView() {
		imageArrayList.add(addImage);
		listViewAdapter = new ListViewAdapter<String>(context, R.layout.grid_item_add_pic, imageArrayList) {
			@Override
			public void convert(ViewHodle var1, String var2, int position) {
				ImageView iv_upload_file = var1.itemView.findViewById(R.id.iv_upload_file);
				iv_upload_file.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if (imageArrayList.get(position).equals(addImage)) {
							if (selectPicturesPopWindows == null)
								selectPicturesPopWindows = new SelectPicturesPopWindows(context);
							selectPicturesPopWindows.setyCallBack(new YCallBack() {
								@Override
								public void onOk(Object o) {
									String type = (String) o;
									selectPicturesPopWindows.dismiss();
									if (selectPicturesPopWindows.cameraType.equals(type)) {
										if (ContextCompat.checkSelfPermission(context,
												Manifest.permission.CAMERA)
												!= PackageManager.PERMISSION_GRANTED) {
											ActivityCompat.requestPermissions((Activity) context,
													new String[]{Manifest.permission.CAMERA},
													MY_PERMISSIONS_REQUEST_TAKE_PHOTO);
										} else {
											Intent intent = new Intent(context, ImageGridActivity.class);
											ZhjxAppFramentUtil.getImagePickerUtils().getImagePicker().setSelectLimit(1);
											ZhjxAppFramentUtil.getImagePickerUtils().getImagePicker().setCrop(false);
											ZhjxAppFramentUtil.getImagePickerUtils().getImagePicker().setShowCamera(true);
											intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
											startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);

//											startCamera(activity,PHOTO_REQUEST_TAKEPHOTO);
										}
									} else if (selectPicturesPopWindows.picType.equals(type)) {
										Intent intent = new Intent(context, ImageGridActivity.class);
										ZhjxAppFramentUtil.getImagePickerUtils().getImagePicker().setSelectLimit(1);
										startActivityForResult(intent, 201);
									}
								}
							});
							selectPicturesPopWindows.showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
						} else {//查看
							initPop(0,imageItems);
//							Intent intent = new Intent(context, ImagePreviewActivity.class);
//
//							intent.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, 0);
//							intent.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS,imageItems);
////										intent.putExtra(ImagePreviewActivity.ISORIGIN, true);
//							intent.putExtra(ImagePicker.EXTRA_FROM_ITEMS, false);

//
//							intent.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, imageItems); // 是否是直接打开相机
////							ZhjxAppFramentUtil.getImagePickerUtils().getImagePicker().setSelectedImages(imageItems);
//							startActivityForResult(intent, 201);
						}
					}
				});
				ImageView iv_delete_file = var1.itemView.findViewById(R.id.iv_delete_file);
				iv_delete_file.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						imageArrayList.remove(position);
						imageItems.remove(position);
						imageArrayList.add(addImage);
						listViewAdapter.setDataArrayList(imageArrayList);
					}
				});
				if (var2.equals(addImage)) {
					GlideUtil.getGlideImageViewUtil().setImageView(context, R.drawable.addfile, iv_upload_file);
					iv_delete_file.setVisibility(View.GONE);
				} else {
					GlideUtil.getGlideImageViewUtil().setImageView(context, var2, iv_upload_file);
					iv_delete_file.setVisibility(View.VISIBLE);
				}

			}

			@Override
			public void convertHideView(HeaderViewHodle var1, String var2, int position) {

			}
		};
		gridview.setAdapter(listViewAdapter);
	}

	@Override
	public void setTransparent() {

	}

	@Override
	public BasePresenter createPresenter() {
		return habitPresenter = new HabitPresenter(context, new YCallBack() {
			@Override
			public void requestSuccessful(Object o) {
				super.requestSuccessful(o);
			}

			@Override
			public void requestFail(Object o) {
				super.requestFail(o);
			}
		});
	}

	public void addGoodHabit() {
		ArrayList<String> imagePath = new ArrayList<>();
		habitPresenter.habitadd("conetn", habitType.classifyId, habitType.id, imagePath);
	}


	@Override
	protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
		switch (requestCode) {
			case PHOTO_REQUEST_TAKEPHOTO:// 当选择拍照时调用
				if (intent != null && responseCode == ImagePicker.RESULT_CODE_ITEMS) {
					ArrayList<ImageItem> images = (ArrayList<ImageItem>) intent.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
					imageArrayList.clear();
					imageItems.clear();
					for (ImageItem image : images) {
						imageArrayList.add(image.path);
						imageItems.add(image);
					}
					listViewAdapter.setDataArrayList(imageArrayList);
				}

//				String pathString = ZhjxAppFramentUtil.getImagePickerUtils().getImagePicker().getCameraPicPath(responseCode, intent);
//				if (pathString != null && new java.io.File(pathString).exists()) {
//					imageList.clear();
//					Result result = new Result();
//					result.url = pathString;
//					result.upload = false;
//					result.updating = false;
//					imageList.add(result);
//					adapter.notifyDataSetChanged();
//				} else {
//					util.toast("照片保存失败", -1);
//				}
				break;
			case 201:// 当选择从本地获取图片时
				if (intent != null && responseCode == ImagePicker.RESULT_CODE_ITEMS) {


					ArrayList<ImageItem> images = (ArrayList<ImageItem>) intent.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
					imageArrayList.clear();
					imageItems.clear();
					for (ImageItem image : images) {
						imageArrayList.add(image.path);
						imageItems.add(image);
					}
					listViewAdapter.setDataArrayList(imageArrayList);
				}

				break;
		}
		super.onActivityResult(requestCode, responseCode, intent);
	}

	/**
	 * 打开相机
	 *
	 * @param activity
	 * @param requestCode
	 */
	public void startCamera(Activity activity, int requestCode) {
		this.activity = activity;
		takePhoto(requestCode);
	}

	private void takePhoto(int requestCode) {
//        Intent cameraintent = new Intent(
//                MediaStore.ACTION_IMAGE_CAPTURE);
//        activity.startActivityForResult(cameraintent,
//                requestCode);
		Intent cameraintent = new Intent(
				MediaStore.ACTION_IMAGE_CAPTURE);
		// 指定调用相机拍照后照片的储存路径
//		cameraintent.putExtra(MediaStore.EXTRA_OUTPUT,
//				Uri.fromFile(file));
		activity.startActivityForResult(cameraintent,
				requestCode);
	}

	////////==================================


	PopupWindow imagePop;
	void initPop(int posi,	ArrayList<ImageItem> images) {
		View view = View.inflate(this, R.layout.popupwindow_zoompic, null);
		ShowPicViewPager viewPager = (ShowPicViewPager) view.findViewById(R.id.viewPager);
		imagePop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		 ViewPagerAdapter adapter = new ViewPagerAdapter(images, this, viewPager);
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
		imagePop.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
	}

	public class ViewPagerAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {
		ArrayList<ImageItem> images;
		Activity activity;
		ShowPicViewPager viewPager;

		public ViewPagerAdapter(	ArrayList<ImageItem> images,  Activity activity, ShowPicViewPager viewPager) {
			this.images = images;
			this.activity = activity;
			this.viewPager = viewPager;
		}

		@Override
		public int getCount() {
			return images != null ? images.size() : 0;
		}

		@Override
		public Object instantiateItem(ViewGroup container, final int position) {
			View view = View.inflate(activity, R.layout.homework_viewpager_item, null);
			final RelativeLayout rl_save = (RelativeLayout) view.findViewById(R.id.rl_save);
			final TextView tv_save = (TextView) view.findViewById(R.id.tv_save);
			final TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
			final ImageView iv_download_hint = (ImageView) view.findViewById(R.id.iv_download_hint);
				iv_download_hint.setVisibility(View.GONE);
			PhotoView photoView = (PhotoView) view.findViewById(R.id.photoView);
			photoView.enable();
			photoView.setMaxScale(3);
			TextView tv_page = (TextView) view.findViewById(R.id.tv_page);
			tv_page.setText(position + 1 + "/" + images.size());
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
			GlideUtil.getGlideImageViewUtil().setImageView(context,images.get(position).path,photoView);
//			Glide.with(activity).load(images.get(position)).dontAnimate()
//					.placeholder(R.drawable.loadgif)
//					.error(R.drawable.failure)
//					.into(photoView);
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
}
