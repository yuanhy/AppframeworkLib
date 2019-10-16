package cc.zenking.edu.zhjx.db.dbdata;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.yuanhy.library_tools.app.AppFramentUtil;
import com.yuanhy.library_tools.dbflow.dbinterfase.DbInterfase;
import com.yuanhy.library_tools.util.YCallBack;

import java.util.ArrayList;
import java.util.List;

import cc.zenking.edu.zhjx.db.dbbase.UserBase;
import cc.zenking.edu.zhjx.db.dbenty.UserEnty;
import cc.zenking.edu.zhjx.db.dbenty.UserEnty_Table;

public class UserDbData implements DbInterfase<UserEnty>{
String TAG ="UserDbData" + UserBase.NAME+" 版本："+UserBase.VERSION;
    private UserDbData() {

    }
    ;
    public static UserDbData dbDate;

    public static UserDbData getDbDate() {
        if (dbDate == null) {
            dbDate = new UserDbData();
        }
        return dbDate;
    }

    @Override
    public void query(String condition, YCallBack YCallBack) {
//
//        List<UserEnty> organizationList = SQLite.select().from(UserEnty.class).where(UserEnty_Table.userid.is(condition)).queryList();
//
//        if (YCallBack != null) {
//            if (organizationList == null) {
//                organizationList = new ArrayList<>();
//            }
//            YCallBack.onOk(organizationList);
//        } else {
//
//        }
//        YCallBack.onError("error");
    }

    @Override
    public void queryList(YCallBack yCallBack) {

        List<UserEnty> organizationList = SQLite.select().
                from(UserEnty.class).queryList();
        if (yCallBack != null)
            yCallBack.onOk(organizationList);
    }

    @Override
    public void update(String condition, String target, boolean isAll, YCallBack YCallBack) {
//        if (isAll) {
//            SQLite.update(UserEnty.class).set(UserEnty_Table.userid.is(target)).where(UserEnty_Table.name.is(condition)).execute();//满足条件的全部修改
//        } else
//            SQLite.update(UserEnty.class).set(TextDb_Table.name.is(target)).where(TextDb_Table.name.is(condition));//满足条件修改第一个
    }

    @Override
    public void update(UserEnty  enty, String condition, YCallBack YCallBack) {

    }

    //指定字段升降序查询
    private void selectBaseModleOrderBy(Object o) {
        //true为'ASC'正序, false为'DESC'反序
        List<UserEnty> list = SQLite.select().from(UserEnty.class)
                .where()
                .orderBy(UserEnty_Table.username, true)
                .queryList();
    }

    //分组查询--以年龄+名字分组查询：先排序后分组
    private void selectBaseModleGroupBy() {
        List<UserEnty> list = SQLite.select()
                .from(UserEnty.class)
                .groupBy(UserEnty_Table.reason, UserEnty_Table.username)
                .queryList();

    }

    //分页查询--每页查询3条--》limit后面跟的是3条数据，offset：是从第（page*3）条开始读取
    public void selectPageBaseModle(int page, YCallBack YCallBack) {

        List<UserEnty> list = SQLite.select()
                .from(UserEnty.class)
                .limit(3)//条数-》3
                .offset(page * 3)//当前页数
                .queryList();
        YCallBack.onOk(list);
    }

    @Override
    public void add(UserEnty enty, YCallBack YCallBack) {
        boolean isSuee= enty.save();
        AppFramentUtil.logCatUtil.v(TAG, "添加 add :"+isSuee);
    }

    @Override
    public void delter(UserEnty enty, boolean isAll,YCallBack YCallBack) {
        if (isAll){
            SQLite.delete().from(UserEnty.class).execute();//全部删除
        }else {
            enty.delete();
        }

    }

    @Override
    public void selectPageBaseModle(int page, int nubs, YCallBack YCallBack) {
        List<UserEnty> list = SQLite.select()
                .from(UserEnty.class)
                .limit(nubs)//条数-》3
                .offset(page * nubs)//当前页数
                .queryList();
        YCallBack.onOk(list);
    }

}