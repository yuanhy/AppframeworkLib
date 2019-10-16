package com.yuanhy.library_tools.modular.rotary_planting_map;

import java.io.Serializable;

/**
 * 单个 轮播图 相册照片
 */
public class RotaryPlantingEnty implements Serializable {

	String publishTime;
	String fileName;
	String filePath;
	String id;

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
