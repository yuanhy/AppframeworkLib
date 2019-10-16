package cc.zenking.edu.zhjx.api;

import cc.zenking.edu.zhjx.enty.homeEnty.MenuList;
import cc.zenking.edu.zhjx.enty.homeEnty.NewsListResultEnty;
import cc.zenking.edu.zhjx.http.HTTPConstants;
import cc.zenking.edu.zhjx.utils.ZhjxAppFramentUtil;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface HomeFragmentApi {


	/**
	 * 获取推荐的新聞列表 新接口
	 *
	 * @return
	 */
	@GET(InterfaceAddress.GETALLINFO)
	Observable<NewsListResultEnty> getNewsList(@Header(HTTPConstants.HEADER_SESSION) String session, @Header(HTTPConstants.HEADER_USER) String userid,
	                                           @Query("method") String method, @Query("pageSize") int pageSize, @Query("currentPage") int currentPage,
	                                           @Query("pushType") String pushType, @Query("flag") String flag);

	/**
	 * 首页菜单接口
	 *
	 * @return
	 */
	@GET(InterfaceAddress.GETAPPFUNCTION)
	Observable<MenuList> getAppFunction(@Header(HTTPConstants.HEADER_SESSION) String session, @Header(HTTPConstants.HEADER_USER) String userid,
	                                    @Query("userId") String userId, @Query("version") String version, @Query("schoolStuId") String schoolStuId);

}
