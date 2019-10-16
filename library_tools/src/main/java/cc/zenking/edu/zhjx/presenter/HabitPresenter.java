package cc.zenking.edu.zhjx.presenter;

import android.content.Context;

import com.yuanhy.library_tools.http.retrofit.RetrofitUtile;
import com.yuanhy.library_tools.presenter.BasePresenter;
import com.yuanhy.library_tools.rxjava.ObServerBean;
import com.yuanhy.library_tools.util.YCallBack;

import java.util.ArrayList;
import java.util.HashMap;

import cc.zenking.edu.zhjx.api.AppUrl;
import cc.zenking.edu.zhjx.api.GoodHabitApi;
import cc.zenking.edu.zhjx.api.LeaveApi;
import cc.zenking.edu.zhjx.db.dbenty.UserEnty;
import cc.zenking.edu.zhjx.enty.Result;
import cc.zenking.edu.zhjx.enty.childEnty.Child;
import cc.zenking.edu.zhjx.enty.habitEnty.Delete_Habit_Result;
import cc.zenking.edu.zhjx.enty.habitEnty.HabitType_Result;
import cc.zenking.edu.zhjx.enty.habitEnty.Habit_Result;
import cc.zenking.edu.zhjx.utils.ZhjxAppFramentUtil;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class HabitPresenter extends BasePresenter {
	GoodHabitApi goodHabitApi;
	YCallBack yCallBack;
	Context context;
	UserEnty userEnty;
	private Child child;
	private String lastId = "";

	public HabitPresenter(Context context, YCallBack yCallBack) {
		this.yCallBack = yCallBack;
		this.context = context;
		goodHabitApi = RetrofitUtile.getInstanceSSL(AppUrl.BASE_URL).createSSL(GoodHabitApi.class);
		userEnty = ZhjxAppFramentUtil.getUserInfo();
		child = ZhjxAppFramentUtil.getSelterChild(context);
	}
	public void upChild(){
		child = ZhjxAppFramentUtil.getSelterChild(context);
	}
	/**
	 * 获取好习惯类型列表
	 *
	 * @return
	 */
	public void getBindStudentList() {
		goodHabitApi.getBindStudentList(userEnty.session, userEnty.userid, "zhjx", child.schoolId, userEnty.userid)
				.compose(io_uiMain())
				.subscribe(new ObServerBean<HabitType_Result>() {
					@Override
					public void onSuccees(HabitType_Result habitType_result) {
yCallBack.requestSuccessful(habitType_result);
					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
						yCallBack.requestSuccessful(new HabitType_Result());
					}
				});
	}

	/**
	 * 添加好习惯
	 * @param description
	 * @param classifyId  好习惯类型中的
	 * @param id          好习惯类型中的
	 * @param files
	 */
	public void habitadd(String description, String classifyId, String id, ArrayList<String> files) {
		HashMap<String, String> params = new HashMap<>();
		params.put("studentIds", child.studentId);
		params.put("classifyId", classifyId);
		params.put("id", id);
		params.put("from", "zhjx");
		params.put("userId", userEnty.userid);
		params.put("schoolId", child.schoolId);
		params.put("picture", files.toString());
		params.put("description", description);
		goodHabitApi.habitadd(userEnty.session, userEnty.userid, "", "", params)
				.compose(io_uiMain()).subscribe(new ObServerBean<Result>() {
			@Override
			public void onSuccees(Result result) {
yCallBack.requestSuccessful(result);
			}

			@Override
			protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
				yCallBack.requestSuccessful(new Result());
			}
		});
	}

	/**
	 * 删除好习惯
	 * @param id 单个习惯的id
	 */
	public void habitDelete(int id) {

		HashMap<String, String> params = new HashMap<>();
		params.put("userId", userEnty.userid);
		params.put("schoolId", child.schoolId);
		params.put("ids", id + "");
		goodHabitApi.habitDelete(userEnty.session, userEnty.userid, "", "", params)
				.compose(io_uiMain())
				.subscribe(new ObServerBean<Delete_Habit_Result>() {
					@Override
					public void onSuccees(Delete_Habit_Result delete_habit_result) {

					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

					}
				});
	}

	/**
	 *获取学生好习惯统计
	 * @param selectType  2是选中家按钮 1是选择学校按钮
	 * @param selectTime 1：今日，2：本周，3：上周，4：本月，5：上月
	 * @param lastId 第一页"",下一页传上页数据中的最后一条Id
	 */
	public void getHabitStatisticsStudent(String selectType,String selectTime ,String lastId ){
		goodHabitApi.getHabitStatisticsStudent(userEnty.session, userEnty.userid,"zhjx",
				selectType,selectTime,child.studentId,child.schoolId,lastId,userEnty.userid)
				.compose(io_uiMain())
				.subscribe(new ObServerBean<Habit_Result>() {
			@Override
			public void onSuccees(Habit_Result habit_result) {

			}

			@Override
			protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

			}
		});
	}
}
