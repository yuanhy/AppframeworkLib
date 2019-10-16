package cc.zenking.edu.zhjx.db.dbenty;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;

import cc.zenking.edu.zhjx.db.dbbase.UserBase;

@Table(database = UserBase.class)
public class UserEnty extends BaseModel implements Serializable {
	@PrimaryKey(autoincrement = true)
	@Column
	public long index;
	@Column
	public int result;
	@Column
	public String reason;
	@Column
	public String session;
	@Column
	public String username;
	//    public String nickname;
	@Column
	public String personpic;
	@Column
	public String portrait;
	@Column
	public String url;
	@Column
	public String socket;
	//    public String backpic;
//    public String logo;
	@Column
	public String userid;
	//    public boolean reLogin;
//    public String sys;
	@Column
	public String code;
	@Column
	public int roles;
	@Column
	public int loginErrorStatus;
	@Column
	public String uuidChange;
	@Column
	public int status;
}
