package cc.zenking.edu.zhjx.enty.homeEnty;

import com.yuanhy.library_tools.util.StringUtil;

import java.io.Serializable;

public class Data implements Serializable {
    public int id;
    public String createTime;
    public String key;
    public Action[] action;
    public String status;
    public String value;
    public String name;
    public int isactive;
    public String starttime;
    public String endtime;
    public String title;
    public String description;
    public int flag;
    public String url;
    public ChannelItem data[];
    public String reason;
    public String type;
    public String asktime;
    public String picUrl;
    public String headPortrait;
    public int browse;
    public int count;
    public String instName;
    public String userId;
    public String userName;
    public String relation;
    public String objId;
    public String userType;
    public String reportDictName;
    //    public int classMaster;
//    public String role;
    public int creator;
    //    public String activityName;
//    public String createTimeStr;
    public String termReportName;
    public String visitId;
    public String updateTime;
    public String termReportId;
    public String currentTermId;
public boolean isCach=false;//本地是否已经下载过
    public String getFileCachName(){
        return  url.replaceAll("/","_");
    }
}
