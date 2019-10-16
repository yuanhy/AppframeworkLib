package cc.zenking.edu.zhjx.enty.homeEnty;

import java.io.Serializable;

public class NewsListResultEnty implements Serializable {
    public int status;
    public String reason;
    public Data_News data;
    public int type;//0 轮播图  1 推荐的看看新闻
}
