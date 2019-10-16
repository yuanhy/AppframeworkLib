package cc.zenking.edu.zhjx.enty;

import android.os.Parcel;
import android.os.Parcelable;

public class AddLeaveQequesEnty implements Parcelable {

//	{"status":1,"successCount":0,"failCount":0,"reason":"添加成功","importResult":null,"data":"","objData":"","url":""}
String status;
	String successCount;
	String failCount;
	String reason;
	String importResult;
	String data;
	String objData;
	String url;

	protected AddLeaveQequesEnty(Parcel in) {
		status = in.readString();
		successCount = in.readString();
		failCount = in.readString();
		reason = in.readString();
		importResult = in.readString();
		data = in.readString();
		objData = in.readString();
		url = in.readString();
	}

	public static final Creator<AddLeaveQequesEnty> CREATOR = new Creator<AddLeaveQequesEnty>() {
		@Override
		public AddLeaveQequesEnty createFromParcel(Parcel in) {
			return new AddLeaveQequesEnty(in);
		}

		@Override
		public AddLeaveQequesEnty[] newArray(int size) {
			return new AddLeaveQequesEnty[size];
		}
	};

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(String successCount) {
		this.successCount = successCount;
	}

	public String getFailCount() {
		return failCount;
	}

	public void setFailCount(String failCount) {
		this.failCount = failCount;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getImportResult() {
		return importResult;
	}

	public void setImportResult(String importResult) {
		this.importResult = importResult;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getObjData() {
		return objData;
	}

	public void setObjData(String objData) {
		this.objData = objData;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(status);
		dest.writeString(successCount);
		dest.writeString(failCount);
		dest.writeString(reason);
		dest.writeString(importResult);
		dest.writeString(data);
		dest.writeString(objData);
		dest.writeString(url);
	}
}
