package cc.zenking.edu.zhjx.db.dbbase;

import com.raizlabs.android.dbflow.annotation.Migration;
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration;

import cc.zenking.edu.zhjx.db.dbenty.UserEnty;

/**
 * 数据库自动升级
 * Created by yuanhy on 2019/9/8.
 */
@Migration(version = UserBase.VERSION, database = UserBase.class)
public class UserDbUpdataUtile extends AlterTableMigration<UserEnty> {
	public UserDbUpdataUtile(Class<UserEnty> table) {
		super(table);
	}

	@Override
	public void onPreMigrate() {

//        addColumn(SQLiteType.TEXT,"content");   这里添加数据库改动的字段
	}
}