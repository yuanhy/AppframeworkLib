package cc.zenking.edu.zhjx.ui.messge;

import cc.zenking.edu.zhjx.enty.childEnty.OwnChilds;
import cc.zenking.edu.zhjx.http.HTTPConstants;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface MLMesageApi {
    @GET(MLInterfaceAddress.MESSAGELIST)
    Observable<MLParsingModel> requestMessageList(@Header(HTTPConstants.HEADER_VERSION) String appVersion,
                                                  @Header(HTTPConstants.HEADER_RESULTMD5) String resultMd5,
                                                  @Query("userId") String userId, @Query("stuId") String stuId,
                                                  @Query("version") String version, @Query("schoolId") String schoolId);

}
