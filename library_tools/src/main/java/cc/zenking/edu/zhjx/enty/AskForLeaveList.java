package cc.zenking.edu.zhjx.enty;

import java.io.Serializable;
import java.util.ArrayList;

public class AskForLeaveList implements Serializable {

	public Boolean audit;
	public ArrayList<DataLeave> data;

	public Boolean getAudit() {
		return audit;
	}

	public void setAudit(Boolean audit) {
		this.audit = audit;
	}

	public ArrayList<DataLeave> getData() {
		return data;
	}

	public void setData(ArrayList<DataLeave> data) {
		this.data = data;
	}
}
