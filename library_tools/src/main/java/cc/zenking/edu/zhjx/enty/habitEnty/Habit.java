package cc.zenking.edu.zhjx.enty.habitEnty;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/6.
 */

public class Habit implements Serializable {
    public int studentId;
    public String studentName;
    public HabitCount[] map;
    public HabitList[] list;
}
