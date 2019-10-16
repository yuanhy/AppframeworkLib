package cc.zenking.edu.zhjx.enty;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/24.
 */

public class NotifyResult implements Serializable {
    public String content;
    public int id;
    public String time;
    public String title;
    public String action;
    public int unRead;
    public int flag;
    public String pic;
    public String url;
    public String stuId;
    public String saveDate;
//    public String subAction;//综合测评通知类型(1:申诉，2:查看报告，3:查看公示，4:代办任务项)
}
