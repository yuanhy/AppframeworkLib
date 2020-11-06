package com.yuanhy.library_tools.dbflow.dbdata;//package com.yuanhy.library_tools.dbflow.dbdata;
//
//
//import com.raizlabs.android.dbflow.sql.language.SQLite;
//import com.yuanhy.library_tools.dbflow.db_enty.TextDb;
//import com.yuanhy.library_tools.dbflow.db_enty.TextDb_Table;
//import com.yuanhy.library_tools.dbflow.dbinterfase.DbInterfase;
//import com.yuanhy.library_tools.util.YCallBack;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by yuanhy on 2018/7/30.
// */
//
//public class TextDate implements DbInterfase<TextDb> {
//    private TextDate() {
//
//    }
//
//    ;
//    public static TextDate dbDate;
//
//    public static TextDate getDbDate() {
//        if (dbDate == null) {
//            dbDate = new TextDate();
//        }
//        return dbDate;
//    }
//
//
//
//    @Override
//    public void query(String condition, YCallBack YCallBack) {
//
//        List<TextDb> organizationList = SQLite.select().from(TextDb.class).where(TextDb_Table.name.is(condition)).queryList();
//        ;
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
//    }
//
//    @Override
//    public void queryList(YCallBack YCallBack) {
//
//        List<TextDb> organizationList = SQLite.select().
//                from(TextDb.class).queryList();
//        if (YCallBack != null)
//            YCallBack.onOk(organizationList);
//        else
//            YCallBack.onError("error");
//    }
//
//    @Override
//    public void update(String condition, String target, boolean isAll, YCallBack YCallBack) {
//        if (isAll) {
//            SQLite.update(TextDb.class).set(TextDb_Table.name.is(target)).where(TextDb_Table.name.is(condition)).execute();//满足条件的全部修改
//        } else
//            SQLite.update(TextDb.class).set(TextDb_Table.name.is(target)).where(TextDb_Table.name.is(condition));//满足条件修改第一个
//    }
//
//    @Override
//    public void update(TextDb enty, String condition, YCallBack YCallBack) {
//
//    }
//
//    //指定字段升降序查询
//    private void selectBaseModleOrderBy(Object o) {
//        //true为'ASC'正序, false为'DESC'反序
//        List<TextDb> list = SQLite.select().from(TextDb.class)
//                .where()
//                .orderBy(TextDb_Table.name, true)
//                .queryList();
//    }
//
//    //分组查询--以年龄+名字分组查询：先排序后分组
//    private void selectBaseModleGroupBy() {
//        List<TextDb> list = SQLite.select()
//                .from(TextDb.class)
//                .groupBy(TextDb_Table.age, TextDb_Table.name)
//                .queryList();
//
//    }
//
//    //分页查询--每页查询3条--》limit后面跟的是3条数据，offset：是从第（page*3）条开始读取
//    public void selectPageBaseModle(int page, YCallBack YCallBack) {
//
//        List<TextDb> list = SQLite.select()
//                .from(TextDb.class)
//                .limit(3)//条数-》3
//                .offset(page * 3)//当前页数
//                .queryList();
//        YCallBack.onOk(list);
//    }
//
//    @Override
//    public void add(TextDb enty, YCallBack YCallBack) {
//        enty.save();
//    }
//
//    @Override
//    public void delter(TextDb enty, boolean isAll,YCallBack YCallBack) {
//        if (isAll){
//            SQLite.delete().from(TextDb.class).execute();//全部删除
//        }else {
//            enty.delete();
//        }
//
//    }
//
//    @Override
//    public void selectPageBaseModle(int page, int nubs, YCallBack YCallBack) {
//        List<TextDb> list = SQLite.select()
//                .from(TextDb.class)
//                .limit(nubs)//条数-》3
//                .offset(page * nubs)//当前页数
//                .queryList();
//        YCallBack.onOk(list);
//    }
//}
