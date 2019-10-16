package cc.zenking.edu.zhjx.enty.habitEnty;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2018/3/7.
 */

public class HabitType_Result implements Serializable {
    public int status;
    public String reason;
//    public int amount;
//    public int pages;
    public ArrayList <HabitType>  data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ArrayList<HabitType> getData() {
        return data;
    }

    public void setData(ArrayList<HabitType> data) {
        this.data = data;
    }
}
