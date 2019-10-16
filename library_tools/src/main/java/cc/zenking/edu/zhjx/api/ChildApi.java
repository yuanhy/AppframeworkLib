package cc.zenking.edu.zhjx.api;

import cc.zenking.edu.zhjx.enty.childEnty.OwnChilds;
import cc.zenking.edu.zhjx.http.HTTPConstants;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ChildApi {
	@GET(InterfaceAddress.GETBINDSTUDENTLIST)
	Observable<OwnChilds> getBindStudentList(@Header(HTTPConstants.HEADER_SESSION) String appVersion, @Header(HTTPConstants.HEADER_USER) String userid,
	                                         @Query("userId") String userId);

}
