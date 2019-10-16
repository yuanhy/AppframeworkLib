package cc.zenking.edu.zhjx.http;

/**
 * HTTP服务常量
 *
 * @author lj
 */

public interface HTTPConstants {

    /**
     * app接口路径
     */
    String APP_ROOT_URL = "";
    // String APP_UPDATE_URL = "http://123.57.51.33";

    /**
     * 服务器URL根路径
     */
    String HEADER_USER = "user";
    String HEADER_SESSION = "session";
    String HEADER_VERSION = "app-version";
    String HEADER_RESULTMD5 = "ResultMD5";
    /**
     * 上传时的文件参数,FileSystemResource
     */
    String FILE = "file";

    /**
     * 以下是图片上传参数及值
     */
    String UPLOAD_KEY = "key";
    String CHAT_IMG = "chat_photo";
    String USER_PORTRAIT = "teacher_portrait";
    String CHAT_VOICE = "chat_voice";
    String ACTIVITY_THEME = "activity_theme";
    String NOTICE_FILE = "notice_file";
    String CP_FILE = "cp_file";
    String FEED_BACK = "feedback_img";
}
