package cc.zenking.edu.zhjx.api;

import java.util.ArrayList;
import java.util.HashMap;

import cc.zenking.edu.zhjx.enty.AddLeaveQequesEnty;
import cc.zenking.edu.zhjx.enty.AskForLeaveList;
import cc.zenking.edu.zhjx.enty.Dict;
import cc.zenking.edu.zhjx.enty.LeaveInfoEnty;
import cc.zenking.edu.zhjx.enty.Result;
import cc.zenking.edu.zhjx.http.HTTPConstants;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

import static cc.zenking.edu.zhjx.http.HTTPConstants.HEADER_SESSION;

public interface LeaveApi {

	/**
	 * 获取孩子请假列表
	 * @param session
	 * @param user
	 * @param studentId 学生ID
	 * @param lastId 第几页
	 * @param fus 学生的fusID
	 * @return
	 */
	@GET(InterfaceAddress.ASKFORLEAVE_LIST)
	Observable<AskForLeaveList> childLeaveList(@Header(HTTPConstants.HEADER_SESSION) String session, @Header(HTTPConstants.HEADER_USER) String user,
	                                           @Header(AppUrl.CHILDFUSID) String fus,
	                                           @Query("studentId") String studentId, @Query("lastId") String lastId);

	/**
	 * 家长添加请假信息
	 * @param session
	 * @param user
	 * @param fus
	 * @param qmap
	 * @return
	 */
	@POST(InterfaceAddress.ASKFORLEAVE_ADD)
	Observable<AddLeaveQequesEnty> add_Askforleave(@Header(HTTPConstants.HEADER_SESSION) String session, @Header(HTTPConstants.HEADER_USER) String user,
	                                               @Header(AppUrl.CHILDFUSID) String fus,
	                                               @QueryMap HashMap<String, String> qmap);

	/**
	 * 获取请假类型
	 *
	 * @param type
	 * @return
	 */
	@GET(InterfaceAddress.USERDICT_LIST)
	Observable<ArrayList<Dict>> getAskForLeaveDict(@Header(HTTPConstants.HEADER_SESSION) String session, @Header(HTTPConstants.HEADER_USER) String user,
	                                               @Query("type") String type, @Query("fus") String fus);



	/**
	 *  请假详情页面
	 * @param session
	 * @param user
	 * @param id
	 * @param fus
	 * @param studentId
	 * @return
	 */
	@GET(InterfaceAddress.ASKFORLEAVE_VIEW)
	Observable<LeaveInfoEnty> getLeaveInfo(@Header(HTTPConstants.HEADER_SESSION) String session, @Header(HTTPConstants.HEADER_USER) String user,
	                                       @Query("id") String id, @Query("fus") String fus, @Query("studentId") String studentId);

	/**
	 *  请假详情页面
	 * @param session
	 * @param user
	 * @param id
	 * @param fus
	 * @param casflag 详情中的 casflag
	 * @return
	 */
	@GET(InterfaceAddress.ASKFORLEAVE_CANCEL)
	Observable<Result> cancelLeave(@Header(HTTPConstants.HEADER_SESSION) String session, @Header(HTTPConstants.HEADER_USER) String user,
	                               @Query("id") String id, @Query("fus") String fus, @Query("casflag") String casflag);


}
