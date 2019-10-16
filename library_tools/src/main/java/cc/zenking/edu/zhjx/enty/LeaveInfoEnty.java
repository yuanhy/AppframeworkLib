package cc.zenking.edu.zhjx.enty;

import java.io.Serializable;

public class LeaveInfoEnty implements Serializable {

    public int id;
    public String createTime;
    public String confirmTime;
    public String status;
    public String description;
    public String actTime;
    public Partake partake;
    public boolean adminable;
    public String picUrl;
    public Date[] times;
    public String casflag;
    public String actionFile;
    public String lastOutTime;
public String label;
    public class Partake implements Serializable {

        public String cls;
        public String student;
        //		public String clsId;
        public int studentId;
        public String pic;
        public String pictureUrl;
    }

    public Type type;

    public class Type implements Serializable {

        public int id;
        public String value;

    }
}
