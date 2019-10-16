package cc.zenking.edu.zhjx.api;

import cc.zenking.edu.zhjx.enty.UpdateResult;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AppUpdataApi {
	@GET(InterfaceAddress.APP_UPDATA)
	Observable<UpdateResult> updateApp(@Query("key") String key, @Query("version") String version, @Query("t") long t);
}
