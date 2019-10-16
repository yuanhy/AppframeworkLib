package cc.zenking.edu.zhjx.api;

import java.util.HashMap;

import cc.zenking.edu.zhjx.enty.habitEnty.Delete_Habit_Result;
import cc.zenking.edu.zhjx.enty.habitEnty.HabitType_Result;
import cc.zenking.edu.zhjx.enty.Result;
import cc.zenking.edu.zhjx.enty.habitEnty.Habit_Result;
import cc.zenking.edu.zhjx.http.HTTPConstants;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface GoodHabitApi {
	/**
	 * 获取好习惯类型列表
	 *
	 * @return
	 */
	@GET(InterfaceAddress.HABITGETHABITLIST)
	Observable<HabitType_Result> getBindStudentList(@Header(HTTPConstants.HEADER_SESSION) String session, @Header(HTTPConstants.HEADER_USER) String userid,
	                                                @Query("from") String from, @Query("schoolId") String schoolId, @Query("userId") String userId);


	/**
	 * 添加好习惯
	 *
	 * @return
	 */
	@POST(InterfaceAddress.HABITADD)
	Observable<Result> habitadd(@Header(HTTPConstants.HEADER_SESSION) String session, @Header(HTTPConstants.HEADER_USER) String userid,
	                            @Header(HTTPConstants.HEADER_RESULTMD5) String ResultMD5, @Header(HTTPConstants.HEADER_VERSION) String app_version,
	                            @QueryMap HashMap<String, String> paramsMap);


	/**
	 * 删除好习惯
	 *
	 * @return
	 */
	@POST(InterfaceAddress.HABITDELETE)
	Observable<Delete_Habit_Result> habitDelete(@Header(HTTPConstants.HEADER_SESSION) String session, @Header(HTTPConstants.HEADER_USER) String userid,
	                                            @Header(HTTPConstants.HEADER_RESULTMD5) String ResultMD5, @Header(HTTPConstants.HEADER_VERSION) String app_version,
	                                            @QueryMap HashMap<String, String> paramsMap);

	/**
	 * 获取学生好习惯统计
	 *
	 * @return
	 */
	@GET(InterfaceAddress.HABITSTATISTICSSTUDENT)
	Observable<Habit_Result> getHabitStatisticsStudent(@Header(HTTPConstants.HEADER_SESSION) String session, @Header(HTTPConstants.HEADER_USER) String userid,
	                                                   @Query("from") String from, @Query("type") String type, @Query("dateType") String dateType,
	                                                   @Query("studentId") String studentId, @Query("schoolId") String schoolId, @Query("lastId") String lastId,
	                                                   @Query("userId") String userId);


}
