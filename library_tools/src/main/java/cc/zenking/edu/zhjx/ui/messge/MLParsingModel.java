package cc.zenking.edu.zhjx.ui.messge;

import java.io.Serializable;
import java.util.ArrayList;

import cc.zenking.edu.zhjx.enty.childEnty.Child;

public class MLParsingModel implements Serializable {

    private static final long serialVersionUID = -853839516825929452L;
    private int result;
    private ArrayList<MLMessageModel> notifys;
    private String upgradeHint;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public ArrayList<MLMessageModel> getNotifys() {
        return notifys;
    }

    public void setNotifys(ArrayList<MLMessageModel> notifys) {
        this.notifys = notifys;
    }

    public String getUpgradeHint() {
        return upgradeHint;
    }

    public void setUpgradeHint(String upgradeHint) {
        this.upgradeHint = upgradeHint;
    }
}
