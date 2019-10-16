package cc.zenking.edu.zhjx.enty.habitEnty;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/6.
 */

public class HabitList implements Serializable {
    public int id;
    public int habitId;
    public String studentName;
    public String habitName;
    public String createTime;
//    public int creatorId;
    public String creator;
    public String habitDate;
    public int isDelete;
    public String icon;
    public String description;
    public String picture;
    public int isSpread = 1;
}
