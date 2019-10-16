package cc.zenking.edu.zhjx.enty.homeEnty;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/10.
 * 首页菜单
 */

public class Menu implements Serializable {
    public int id;
    public String functionKey;
    public String functionName;
    public String url;
    public String icon;
    public int hintNotify;
//    public int unreadMessage;
    public Data[] urls;
//    public int sort;
    public int isMsgHint;
    public String key;
}
