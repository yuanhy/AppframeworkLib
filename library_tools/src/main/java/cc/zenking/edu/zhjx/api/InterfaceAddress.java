package cc.zenking.edu.zhjx.api;

public interface InterfaceAddress {

	/**
	 * app帮助
	 */
	public static final String ASSISTANCE = "/static/help/family/zhjxparentappuserhelp.html";
	/**
	 * 资讯列表
	 */
	public static final String INGORMATION = "/info/getAllInfo?method=getAllInfo&pageSize=15&flag=1&currentPage=";
	/**
	 * 写实记录情
	 */
	public static final String RECORDDETAIL = "/zhcp/app/record/recordDetailFamily.html?";

	/**
	 * 添加写实记录
	 */
	public static final String ADDRECORD = "/zhcp/app/record/recordAddFamily.html?";
	/**
	 * 用户协议
	 */
	public static final String AGREEMENT = "/zhjx/static/view/agreement.html";
	/**
	 * 忘记密码
	 */
	public static final String FORGOTPSW = "/sso/view/phone/findPwd.html?sys=family";
	/**
	 * 学期报告
	 */
	public static final String TERM_REPORT = "/zhjx/app/page/termReport/termReport.html";
	/**
	 * 获取孩子
	 */
	public static final String GETBINDSTUDENTLIST="/family/family/getBindStudentList.htm?";

	/**
	 * 获取新闻列表接口
	 */
	public static final String GETALLINFO="/info/getAllInfo?";

	/**
	 * 获取首页功能菜单
	 */
	public static final String GETAPPFUNCTION="/zkscapp/appfunction/family/getAppFunction?";

	/**
	 * App检查更新接口
	 */
	public static final String APP_UPDATA="/admin/web/app/check.htm?";
	/**
	 * 获取孩子请假列表
	 */
	public static final String ASKFORLEAVE_LIST="/family/askforleave/list.htm?";
	/**
	 * 提交孩子请假
	 */
	public static final String ASKFORLEAVE_ADD="/family/askforleave/add.htm";


	/**
	 * 获取请假类型的列表
	 */
	public static final String USERDICT_LIST="/family/userdict/list.htm?";
	/**
	 * 获取请假类型参数
	 */
	public static final String ASKFORLEAVE_TYPE="action.askforleave";
	/**
	 * 获取请假的详情
	 */
	public static final String ASKFORLEAVE_VIEW="/family/askforleave/view.htm?";

	/**
	 * 取消请假
	 */
	public static final String ASKFORLEAVE_CANCEL="/family/askforleave/cancel.htm?";

	/**
	 * 获取好习惯类型列表
	 */
	public static final String HABITGETHABITLIST="/zkscapp/habit/habitGetHabitList?";
	/**
	 * 添加好习惯
	 */
	public static final String HABITADD="/zkscapp/habit/habitAdd";

	/**
	 * 删除好习惯
	 */
	public static final String HABITDELETE ="/zkscapp/habit/habitDelete";

	/**
	 * 获取学生好习惯统计
	 */
	public static final String HABITSTATISTICSSTUDENT ="/zkscapp/habit/habitStatisticsStudent?";



}
