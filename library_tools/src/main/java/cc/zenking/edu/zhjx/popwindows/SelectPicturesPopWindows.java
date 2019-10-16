package cc.zenking.edu.zhjx.popwindows;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuanhy.library_tools.popwindows.BasePopWindows;

import butterknife.BindView;
import butterknife.OnClick;
import cc.zenking.edu.zhjx.R;

public class SelectPicturesPopWindows extends BasePopWindows  {
	TextView tv_cancle;
	TextView tv_pic;
	TextView tv_camera;

	public SelectPicturesPopWindows(Context content) {
		super(content);
		setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
		setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
		setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		view.findViewById(R.id.tv_cancle).setOnClickListener(v -> dismiss());

		view.findViewById(R.id.tv_pic).setOnClickListener(v -> yCallBack.onOk(picType));
		view.findViewById(R.id.tv_camera).setOnClickListener(v -> yCallBack.onOk(cameraType));
	}
public String cameraType="camera";
	public String picType="pic";

	@Override
	public int getLoutResourceId() {
		return R.layout.popwindow_updatehead;
	}
}
