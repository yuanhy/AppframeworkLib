package cc.zenking.edu.zhjx.enty.homeEnty;

import java.io.Serializable;

/**
 * ITEM�Ķ�Ӧ���򻯶�������
 */
public class ChannelItem implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -6465237897027410019L;
    /**
     * ��Ŀ��ӦNAME
     */
    /**
     * ID
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;
    public String url;
    /**
     * 订阅id
     */
    private Integer subscriptionId;

    /**
     * 订阅顺序
     */
    private Integer orderNo;

    /**
     * 创建时间
     */
    private String createTime;
    private String name;
    /**
     * ��Ŀ�������е�����˳�� rank
     */
    public Integer orderId;
    /**
     * ��Ŀ�Ƿ�ѡ��
     */
    public Integer selected;

    private String portrait;

    public ChannelItem() {
    }

    public ChannelItem(int id, String name, int orderId, int selected, int subscriptionId, String portrait) {
        this.id = Integer.valueOf(id);
        this.name = name;
        this.orderId = Integer.valueOf(orderId);
        this.selected = Integer.valueOf(selected);
        this.subscriptionId = subscriptionId;
        this.portrait = portrait;
    }

    public int getId() {
        return this.id.intValue();
    }

    public String getName() {
        return this.name;
    }

    public int getOrderId() {
        return this.orderId.intValue();
    }

    public Integer getSelected() {
        return this.selected;
    }

    public void setId(int paramInt) {
        this.id = Integer.valueOf(paramInt);
    }

    public void setName(String paramString) {
        this.name = paramString;
    }

    public void setOrderId(int paramInt) {
        this.orderId = Integer.valueOf(paramInt);
    }

    public void setSelected(Integer paramInteger) {
        this.selected = paramInteger;
    }

    public String toString() {
        return "ChannelItem [id=" + this.id + ", name=" + this.name + ", selected=" + this.selected + "]";
    }

    public Integer getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Integer subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }
}