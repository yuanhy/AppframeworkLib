package cc.zenking.edu.zhjx;

import android.app.Application;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.yuanhy.library_tools.app.AppFramentUtil;

import cc.zenking.edu.zhjx.utils.ZhjxAppFramentUtil;
import cc.zenking.edu.zhjx.view.imge.PicassoImageLoader;

public class ZhjxApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		FlowManager.init(this);
		ZhjxAppFramentUtil.initZhjxAppFramentUtil(this);
	}

}
