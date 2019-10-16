package cc.zenking.edu.zhjx.enty.childEnty;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 请求孩子家长列表时使用
 *
 * @author nzh
 */
public class OwnChilds implements Serializable {
    private static final long serialVersionUID = -853839516825929452L;
    public int status;
    public ArrayList<Child> objData;
    public String reason;
}
