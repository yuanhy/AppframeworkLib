package cc.zenking.edu.zhjx.enty;

import java.io.Serializable;

/**
 * @author Administrator
 */
public class Result implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -9187505924367515299L;
    public int id;
    public int status = -1;
    public String reason;
    public String url;
    public String school;
    public String value;
    public int silence = -1;
    public int result;
    public String data;
    public String name;
    public String key;
    public String content;
    public NotifyResult[] notifys;
    //    public int total;
    public ExamList[] list;
    public int count;
    public String upgradeHint;
    public boolean upload;
    public boolean updating;
}
