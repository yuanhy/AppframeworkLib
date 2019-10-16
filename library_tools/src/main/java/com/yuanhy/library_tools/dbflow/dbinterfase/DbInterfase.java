package com.yuanhy.library_tools.dbflow.dbinterfase;

import com.yuanhy.library_tools.util.YCallBack;

/**
 * Created by yuanhy on 2018/7/23.
 */

public interface DbInterfase<T> {
    /**
     * 添加一个新数据
     * @param enty
     * @param callBack
     */
    public void add(T enty, YCallBack callBack);

    /**
     * 删除数据
     * @param enty
     * @param isAll 是否删除全部类型
     */
    public void delter(T enty, boolean isAll, YCallBack callBack);

    /**
     * 单个查询
     * @param condition  查询的筛选词
     * @param callBack
     */
    public void query(String condition, YCallBack callBack);
    public void queryList(YCallBack callBack);

    /**
     *
     * @param condition 条件（更改前）
     * @param target 目标（更改后）
     * @param isAll 是否全部修改
     * @param callBack
     */
    public void update(String condition, String target, boolean isAll, YCallBack callBack);
    /**
     *更新单个数据 满足条件condition
     * @param condition 条件
     * @param callBack
     */
    public void update(T enty, String condition, YCallBack callBack);

    /**
     * 分页查询
     * @param page 页数
     * @param nubs 每页数量
     * @param callBack
     */
    public void selectPageBaseModle(int page, int nubs, YCallBack callBack);
}
