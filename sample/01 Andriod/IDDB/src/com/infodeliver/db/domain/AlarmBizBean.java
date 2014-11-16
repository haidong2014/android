package com.infodeliver.db.domain;

public class AlarmBizBean {
	private int noticeId;
	private int alarmType;
	private String showTime;
	private int showStatus;
	private int visible;
	
	private String userId;
	private String createTime;
	private String deleteFlag;
	private String updateTime;
	

	public AlarmBizBean() {
	}

	public int getNoticeId() {
		return noticeId;
	}


	public void setNoticeId(int noticeId) {
		this.noticeId = noticeId;
	}


	public int getAlarmType() {
		return alarmType;
	}


	public void setAlarmType(int alarmType) {
		this.alarmType = alarmType;
	}


	public String getShowTime() {
		return showTime;
	}


	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}


	public int getShowStatus() {
		return showStatus;
	}


	public void setShowStatus(int showStatus) {
		this.showStatus = showStatus;
	}


	public int getVisible() {
		return visible;
	}


	public void setVisible(int visible) {
		this.visible = visible;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getCreateTime() {
		return createTime;
	}


	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}


	public String getDeleteFlag() {
		return deleteFlag;
	}


	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}


	public String getUpdateTime() {
		return updateTime;
	}


	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}


}
