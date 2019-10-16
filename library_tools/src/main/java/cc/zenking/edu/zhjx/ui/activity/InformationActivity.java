package cc.zenking.edu.zhjx.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.yuanhy.library_tools.activity.BaseActivity;
import com.yuanhy.library_tools.presenter.BasePresenter;

import cc.zenking.edu.zhjx.R;

/**
 * 咨询
 */
public class InformationActivity extends BaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_information);
	}

	@Override
	public void setTransparent() {

	}

	@Override
	public BasePresenter createPresenter() {
		return null;
	}
}
