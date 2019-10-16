package cc.zenking.edu.zhjx.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yuanhy.library_tools.activity.BaseActivity;
import com.yuanhy.library_tools.activity.BaseFragmentActivity;
import com.yuanhy.library_tools.presenter.BasePresenter;
import com.yuanhy.library_tools.util.YCallBack;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cc.zenking.edu.zhjx.R;
import cc.zenking.edu.zhjx.enty.childEnty.Child;
import cc.zenking.edu.zhjx.fragment.GoodHabitFragment;
import cc.zenking.edu.zhjx.fragment.GoodHabitSchoolFragment;
import cc.zenking.edu.zhjx.popwindows.ChildPopWindows;
import cc.zenking.edu.zhjx.ui.home.HomeFragment;
import cc.zenking.edu.zhjx.ui.messge.MessgeFragment;
import cc.zenking.edu.zhjx.ui.mine.MineFragment;
import cc.zenking.edu.zhjx.utils.ZhjxAppFramentUtil;

/**
 * 好习惯
 */
public class GoodHabitActivity extends BaseFragmentActivity {
	@BindView(R.id.title_chaild_imge)
	ImageView title_chaild_imge;
	@BindView(R.id.left_tv)
	TextView left_tv;
	@BindView(R.id.title_chaild_tv)
	TextView title_chaild_tv;
	@BindView(R.id.title_chaild_lout)
	LinearLayout title_chaild_lout;

	private ArrayList<Child> childArrayList;
	private String studname;
	private ChildPopWindows childPopWindows;
	GoodHabitSchoolFragment goodHabitSchoolFragment;
	GoodHabitFragment goodHabitFragment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_good_habit);
		initUnbinder();
		initTitleView();
		setTabSelection(0);
	}

	void initTitleView() {
		left_tv.setText("好习惯");
		childArrayList = ZhjxAppFramentUtil.getChilds(context).objData;
		try {
			studname = ZhjxAppFramentUtil.getSelterChild(context).name;
			setTileName(studname, false);
		} catch (NullPointerException e) {
		}

	}

	@OnClick({R.id.title_chaild_tv, R.id.title_chaild_imge, R.id.title_chaild_lout, R.id.image_back})
	public void onClickViews(View v) {
		;
		switch (v.getId()) {
			case R.id.image_back:
				finish();
				break;
			case R.id.title_chaild_tv:
			case R.id.title_chaild_imge:
			case R.id.title_chaild_lout:
				childArrayList = ZhjxAppFramentUtil.getChilds(context).objData;
				if (childPopWindows == null) {
					childPopWindows = new ChildPopWindows(context, childArrayList, new YCallBack() {
						@Override
						public void onOk(Object o) {
							childPopWindows.dismiss();
							Child child = (Child) o;
							studname = child.name;
							setTileName(studname, false);
							ZhjxAppFramentUtil.upDataChilds(context, child);
//							getdata(true);
						}
					});
					childPopWindows.setOnDismissListener(new PopupWindow.OnDismissListener() {
						@Override
						public void onDismiss() {
							setTileName(studname, false);
						}
					});
				}
				if (childArrayList == null || childArrayList.size() == 1) {
					break;
				}
				if (childPopWindows.isShowing()) {
					setTileName(studname, false);
					childPopWindows.dismiss();
				} else {
					setTileName(studname, true);
					childPopWindows.setChildArrayList(childArrayList);
					childPopWindows.showAsDropDown(title_chaild_lout, 0, 0);
				}
				break;
		}
	}

	/**
	 * 切换孩子标题的名字改变
	 *
	 * @param name
	 * @param isUp
	 */
	private void setTileName(String name, boolean isUp) {
		if (isUp) {
			title_chaild_imge.setImageResource(R.drawable.selter_up);
		} else {
			title_chaild_imge.setImageResource(R.drawable.selter_down);
		}
		if (!TextUtils.isEmpty(name)) {
			studname = name;
			title_chaild_tv.setText(name);
		}
	}

	@Override
	public void setTransparent() {

	}

	public void setTabSelection(int index) {
		// 开启一个Fragment事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
		hideFragments(transaction);
		switch (index) {
			case 0:
				if (goodHabitFragment == null) {
					goodHabitFragment = new GoodHabitFragment();
					transaction.add(R.id.fl_fragment, goodHabitFragment);
				} else {
					transaction.show(goodHabitFragment);
				}
				break;
			case 1:
				if (goodHabitSchoolFragment == null) {
					goodHabitSchoolFragment = new GoodHabitSchoolFragment();
					transaction.add(R.id.fl_fragment, goodHabitSchoolFragment);
				} else {
					transaction.show(goodHabitSchoolFragment);
				}
				break;
		}
		transaction.commitAllowingStateLoss();
	}

	@Override
	public void onAttachFragment(@NonNull Fragment fragment) {
		if (goodHabitSchoolFragment == null && fragment instanceof GoodHabitSchoolFragment)
			goodHabitSchoolFragment = (GoodHabitSchoolFragment) fragment;
		if (goodHabitFragment == null && fragment instanceof GoodHabitFragment)
			goodHabitFragment = (GoodHabitFragment) fragment;
	}

	/**
	 * 将所有的Fragment都置为隐藏状态。
	 *
	 * @param transaction 用于对Fragment执行操作的事务
	 */
	private void hideFragments(
			FragmentTransaction transaction) {
		if (goodHabitFragment != null) {
			transaction.hide(goodHabitFragment);
		}
		if (goodHabitSchoolFragment != null) {
			transaction.hide(goodHabitSchoolFragment);
		}

	}
}
