package cc.zenking.edu.zhjx.utils;

import android.content.Context;

import com.yuanhy.library_tools.util.AppCommonTool;

import java.io.File;

public class CachUtile {
	/**
	 * 保存的孩子
	 */
	private static String user_childs="user_childs";
	/**
	 * 登录的账号
	 */
	public static String account_number="account_number";
	/**
	 *  登录的密码
	 */
	public static String account_password="account_password";
	/**
	 * 所有手动下载的图片目录
	 */
	public static  String imageDownPath="zhjxDownImagePath";
	public static String getUser_childsKey(){
		return ZhjxAppFramentUtil.getUserInfo().userid+user_childs;
	}

public static String getCachImagePtah(Context context){
	File file = new File(AppCommonTool.getDiskCacheDir(context,"zhjxImageFiles").getAbsolutePath());
		return  file.getAbsolutePath();
}

}
