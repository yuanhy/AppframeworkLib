package com.yuanhy.library_tools.dbflow.dbbase;//package com.yuanhy.library_tools.dbflow.dbbase;
//
//import com.raizlabs.android.dbflow.annotation.Migration;
//import com.raizlabs.android.dbflow.sql.SQLiteType;
//import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration;
//import com.yuanhy.library_tools.dbflow.db_enty.TextDb;
//
///**
// * 数据库自动升级
// * Created by yuanhy on 2018/11/8.
// */
//@Migration(version = TextBase.VERSION, database = TextBase.class)
//public class TextDbUpData extends AlterTableMigration<TextDb> {
//    public TextDbUpData(Class<TextDb> table) {
//        super(table);
//    }
//
//    @Override
//    public void onPreMigrate() {
//
////        addColumn(SQLiteType.TEXT,"content");   这里添加数据库改动的字段
//    }
//}
