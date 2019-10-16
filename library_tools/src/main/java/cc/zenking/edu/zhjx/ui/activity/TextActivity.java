package cc.zenking.edu.zhjx.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.yuanhy.library_tools.activity.BaseActivity;
import com.yuanhy.library_tools.presenter.BasePresenter;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import cc.zenking.edu.zhjx.R;

public class TextActivity extends BaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_text);
		View titlebar =findViewById(R.id.titlebar);
		initUnbinder();
		initTittleBarRelativeLayout(titlebar);
	}

	@Override
	public void setTransparent() {
setTransparent(true);
	}
	@OnClick({R.id.bt1,R.id.bt2,R.id.bt3,R.id.bt4,R.id.bt5,R.id.bt6,R.id.bt7})
	public void onClicks(View v) {
		switch (v.getId()){
			case R.id.bt1:
				setTitleBarBackgroundColor(0,0f);
				break;
			case R.id.bt2:
				setTitleBarBackgroundColor(0,0.3f);
				break;
			case R.id.bt3:
				setTitleBarBackgroundColor(0,0.7f);
				break;
			case R.id.bt4:
				setTitleBarBackgroundColor(0,1f);
				break;
			case R.id.bt5:
				break;
			case R.id.bt6:
				break;
			case R.id.bt7:
				break;
		}

	}

	@Override
	public BasePresenter createPresenter() {
		return null;
	}
}
