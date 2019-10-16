package cc.zenking.edu.zhjx.enty.homeEnty;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/10.
 */

public class MenuList implements Serializable {
    public int status;
    public ArrayList<Menu> data;
    public String upgradeHint;
}
