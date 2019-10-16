package cc.zenking.edu.zhjx;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.yuanhy.library_tools.activity.BaseFragmentActivity;
import com.yuanhy.library_tools.app.AppAcitivityUtile;

import butterknife.BindView;
import cc.zenking.edu.zhjx.db.dbenty.UserEnty;
import cc.zenking.edu.zhjx.ui.home.HomeFragment;
import cc.zenking.edu.zhjx.ui.messge.MessgeFragment;
import cc.zenking.edu.zhjx.ui.mine.MineFragment;
import cc.zenking.edu.zhjx.utils.ZhjxAppFramentUtil;

public class MainActivity extends BaseFragmentActivity {
	HomeFragment homeFragment;
	MessgeFragment messgeFragment;
	MineFragment mineFragment;

	@BindView(R.id.tag_view)
	View tag_view;

	@BindView(R.id.rad_group)
	RadioGroup rad_group;
	UserEnty userEnty;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initUnbinder();
		initTittleBar(tag_view);
		userEnty = ZhjxAppFramentUtil.getUserInfo();
		rad_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup radioGroup, int childId) {
				switch (childId) {
					case R.id.home_radbt:
						setTabSelection(0);
						break;
					case R.id.message_radbt:
						setTabSelection(1);
						break;
					case R.id.mine_radbt:
						setTabSelection(2);
						break;

				}
			}
		});
		setTabSelection(0);
//		setActivityTittleBar();
	}



	@Override
	public void setTransparent() {
	}

	public void setTabSelection(int index) {
		// 开启一个Fragment事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
		hideFragments(transaction);
		Bundle bundle = new Bundle();
		switch (index) {
			case 0:
				if (homeFragment == null) {
					homeFragment = new HomeFragment( );
					transaction.add(R.id.main_frameLayout, homeFragment);
				} else {
					transaction.show(homeFragment);
				}
				setTitleBarBackgroundColor(R.color.color_ffffff,1);
				break;
			case 1:
				if (messgeFragment == null) {
					messgeFragment = MessgeFragment.newInstance("parame", "");
					transaction.add(R.id.main_frameLayout, messgeFragment);
				} else {
					transaction.show(messgeFragment);
				}
				setTitleBarBackgroundColor(R.color.color_ffffff,1);
				break;
			case 2:
				if (mineFragment == null) {
					mineFragment = MineFragment.newInstance("parame", "");
					transaction.add(R.id.main_frameLayout, mineFragment);
				} else {
					transaction.show(mineFragment);
				}
				setTitleBarBackgroundColor(R.color.color_ffffff,1);
				break;

		}
		transaction.commitAllowingStateLoss();
	}

	@Override
	public void onAttachFragment(@NonNull Fragment fragment) {
		if (homeFragment == null && fragment instanceof HomeFragment)
			homeFragment = (HomeFragment) fragment;
		if (messgeFragment == null && fragment instanceof MessgeFragment)
			messgeFragment = (MessgeFragment) fragment;
		if (mineFragment == null && fragment instanceof MineFragment)
			mineFragment = (MineFragment) fragment;
	}

	/**
	 * 将所有的Fragment都置为隐藏状态。
	 *
	 * @param transaction 用于对Fragment执行操作的事务
	 */
	private void hideFragments(
			FragmentTransaction transaction) {
		if (homeFragment != null) {
			transaction.hide(homeFragment);
		}
		if (messgeFragment != null) {
			transaction.hide(messgeFragment);
		}
		if (mineFragment != null) {
			transaction.hide(mineFragment);
		}

	}
}
