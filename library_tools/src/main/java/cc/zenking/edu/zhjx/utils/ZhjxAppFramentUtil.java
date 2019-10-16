package cc.zenking.edu.zhjx.utils;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.yuanhy.library_tools.app.AppFramentUtil;
import com.yuanhy.library_tools.util.SharedPreferencesUtil;
import com.yuanhy.library_tools.util.YCallBack;

import java.util.ArrayList;

import cc.zenking.edu.zhjx.db.dbdata.UserDbData;
import cc.zenking.edu.zhjx.db.dbenty.UserEnty;
import cc.zenking.edu.zhjx.enty.childEnty.Child;
import cc.zenking.edu.zhjx.enty.childEnty.OwnChilds;
import cc.zenking.edu.zhjx.view.imge.PicassoImageLoader;
import io.reactivex.internal.operators.flowable.FlowableOnErrorReturn;

public class ZhjxAppFramentUtil extends AppFramentUtil {
	public static UserDbData userDbData;
	private static UserEnty userEnty;
	private static OwnChilds ownChilds;
	private static ImagePickerUtils imagePickerUtils;
	public static void initZhjxAppFramentUtil(Context context) {
		initAppFramentUtil(context);
		userDbData = UserDbData.getDbDate();
		imagePickerUtils = ImagePickerUtils.getInstance();
	}

	public static ImagePickerUtils getImagePickerUtils() {
		return imagePickerUtils;
	}

	/**
	 * 获取孩子列表 （不保证最新）
	 *
	 * @param context
	 * @return
	 */
	public static OwnChilds getChilds(Context context) {
		String childStrings = SharedPreferencesUtil.getSharedPreferencesUtil(context).getString(CachUtile.getUser_childsKey());
		if (childStrings.length() == 0) {
			return new OwnChilds();
		}
		Gson gson = new Gson();
		ownChilds = gson.fromJson(childStrings, OwnChilds.class);
		return ownChilds;
	}

	/**
	 * 获取当前选择的孩子 （不保证最新）
	 *
	 * @param context
	 * @return
	 */
	public static Child getSelterChild(Context context) {
		if (ownChilds == null) {
			ownChilds = getChilds(context);
		}
		Child child = null;
		for (Child child1 : ownChilds.objData) {
			if (child1.isSelter) {
				child = child1;
				return child;
			}
		}
		if (ownChilds.objData != null && ownChilds.objData.size() > 0) {
			child = ownChilds.objData.get(0);
			ownChilds.objData.get(0).isSelter=true;
			Gson gson = new Gson();
			SharedPreferencesUtil.getSharedPreferencesUtil(context).putString(CachUtile.getUser_childsKey(), gson.toJson(ownChilds));
		}
		return child;
	}

	public static void upDataChilds(Context context, Child selterChild) {
		if (ownChilds == null) {
			ownChilds = getChilds(context);
		}
		for (int i = 0; i < ownChilds.objData.size(); i++) {
			if (ownChilds.objData.get(i).studentId.equals(selterChild.studentId)) {
				ownChilds.objData.get(i).isSelter = true;
			} else {
				ownChilds.objData.get(i).isSelter = false;
			}
		}
		Gson gson = new Gson();
		SharedPreferencesUtil.getSharedPreferencesUtil(context).putString(CachUtile.getUser_childsKey(), gson.toJson(ownChilds));
	}



	public static UserEnty getUserInfo() {
		if (userEnty != null&& !TextUtils.isEmpty(userEnty.session)) {
			return userEnty;
		}
		userDbData.queryList(new YCallBack() {
			@Override
			public void onOk(Object o) {

				ArrayList<UserEnty> userEntyArrayList = (ArrayList<UserEnty>) o;
				if (userEntyArrayList.size()>0){
					userEnty = userEntyArrayList.get(0);
				}else {
					userEnty =new UserEnty();
				}

			}

			@Override
			public void onError(Object o) {

				userEnty = new UserEnty();
			}
		});
		return userEnty;
	}

	public static void delteUser() {
		userDbData.delter(null, true, null);
	}

}
