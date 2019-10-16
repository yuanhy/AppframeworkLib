package cc.zenking.edu.zhjx.presenter;

import android.content.Context;

import com.yuanhy.library_tools.app.AppFramentUtil;
import com.yuanhy.library_tools.http.retrofit.RetrofitUtile;
import com.yuanhy.library_tools.presenter.BasePresenter;
import com.yuanhy.library_tools.rxjava.ObServerBean;
import com.yuanhy.library_tools.util.YCallBack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cc.zenking.edu.zhjx.api.AppUrl;
import cc.zenking.edu.zhjx.api.ChildApi;
import cc.zenking.edu.zhjx.api.InterfaceAddress;
import cc.zenking.edu.zhjx.api.LeaveApi;
import cc.zenking.edu.zhjx.db.dbenty.UserEnty;
import cc.zenking.edu.zhjx.enty.AddLeaveQequesEnty;
import cc.zenking.edu.zhjx.enty.AskForLeaveList;
import cc.zenking.edu.zhjx.enty.Dict;
import cc.zenking.edu.zhjx.enty.LeaveInfoEnty;
import cc.zenking.edu.zhjx.enty.Result;
import cc.zenking.edu.zhjx.enty.childEnty.Child;
import cc.zenking.edu.zhjx.utils.ZhjxAppFramentUtil;
import retrofit2.http.Query;

public class LeavePresenter extends BasePresenter {
	LeaveApi leaveApi;
	YCallBack yCallBack;
	Context context;
	UserEnty userEnty;
	private Child child;
	private String lastId = "";

	public LeavePresenter(Context context, YCallBack yCallBack) {
		this.yCallBack = yCallBack;
		this.context = context;
		leaveApi = RetrofitUtile.getInstanceSSL(AppUrl.BASE_URL).createSSL(LeaveApi.class);
		userEnty = ZhjxAppFramentUtil.getUserInfo();
		child = ZhjxAppFramentUtil.getSelterChild(context);
	}

	private void getChildLeaveListData() {
		leaveApi.childLeaveList(userEnty.session, userEnty.userid, child.fusId, child.studentId, lastId)
				.compose(io_uiMain()).subscribe(new ObServerBean<AskForLeaveList>() {

			@Override
			public void onSuccees(AskForLeaveList askForLeaveList) {
				if (askForLeaveList.data.size() > 0) {
					lastId = askForLeaveList.data.get(askForLeaveList.data.size() - 1).id;
				}
				yCallBack.requestSuccessful(askForLeaveList);
			}

			@Override
			protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
				yCallBack.requestFail(null);
			}
		});
	}

	/**
	 * 添加请假信息
	 *  @param leave_type_id // 请假类型的Id
	 * @param content
	 * @param dateStrings_start
	 * @param dateStrings_end
	 */
	public void add_Askforleave(String leave_type_id, String content, List<String> dateStrings_start, List<String> dateStrings_end) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("actionType.id", leave_type_id);
		params.put("description", content);
		params.put("clazz", child.clazzId);
		params.put("student", child.studentId);
		params.put("fus", child.fusId);
		for (int i = 0; i < dateStrings_start.size(); i++) {
			params.put("times[" + i + "].startTime",
					dateStrings_start.get(i));
			params.put("times[" + i + "].endTime", dateStrings_end.get(i));
		}

		leaveApi.add_Askforleave(userEnty.session, userEnty.userid, child.fusId, params).compose(io_uiMain()).subscribe(new ObServerBean<AddLeaveQequesEnty>() {
			@Override
			public void onSuccees(AddLeaveQequesEnty s) {
				if (s.getStatus().equals("1")){
					yCallBack.requestSuccessful("");
					ZhjxAppFramentUtil.logCatUtil.i("请假成功");
				}else {
					yCallBack.requestFail("");
					ZhjxAppFramentUtil.logCatUtil.i("请假失败");
				}

			}

			@Override
			protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
				yCallBack.requestFail("");
				ZhjxAppFramentUtil.logCatUtil.i("请假失败");
			}
		});

	}

	/**
	 * 获取请假类型
	 */
	public void getAskForLeaveDict() {
		leaveApi.getAskForLeaveDict(userEnty.session, userEnty.userid, InterfaceAddress.ASKFORLEAVE_TYPE, child.fusId)
				.compose(io_uiMain())
				.subscribe(new ObServerBean<ArrayList<Dict>>() {
					@Override
					public void onSuccees(ArrayList<Dict> dicts) {
						yCallBack.requestSuccessful(dicts);
					}

					@Override
					protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
						yCallBack.requestFail(new Dict());
					}
				});
	}
public void getLeaveInfo(String  id){
	leaveApi.getLeaveInfo(userEnty.session, userEnty.userid,id,child.fusId,child.studentId).compose(io_uiMain()).subscribe(new ObServerBean<LeaveInfoEnty>() {
		@Override
		public void onSuccees(LeaveInfoEnty leaveInfoEnty) {
			yCallBack.requestSuccessful(leaveInfoEnty);
		}

		@Override
		protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
			yCallBack.requestFail(new LeaveInfoEnty());
		}
	});
}
	public void cancelLeave(String  id,String casflag){
		leaveApi.cancelLeave(userEnty.session, userEnty.userid,id,child.fusId,casflag).compose(io_uiMain()).subscribe(new ObServerBean<Result>() {
			@Override
			public void onSuccees(Result result) {
				yCallBack.requestSuccessful(result);
			}

			@Override
			protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
				yCallBack.requestFail(new Result());
			}
		});
	}

	/**
	 * 获取更多
	 */
	public void getChildLeaveList() {
		getChildLeaveListData();
	}

	public void upChild() {
		child = ZhjxAppFramentUtil.getSelterChild(context);
	}

	/**
	 * 刷新列表
	 */
	public void getRefreshChildLeaveList() {
		lastId = "";
		upChild();
		getChildLeaveListData();
	}
}
