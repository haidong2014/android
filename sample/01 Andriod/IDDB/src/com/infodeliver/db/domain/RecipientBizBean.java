package com.infodeliver.db.domain;

public class RecipientBizBean {
	private int id;
	private int noticeId;
	private String readFlag;
	private String pushFlag;
	
	private String recipientId;
	private String recipientName;
//	private String createTime;
	private String deleteFlag;
//	private String updateTime;

	public RecipientBizBean() {
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getNoticeId() {
		return noticeId;
	}


	public void setNoticeId(int noticeId) {
		this.noticeId = noticeId;
	}


	public String getReadFlag() {
		return readFlag;
	}


	public void setReadFlag(String readFlag) {
		this.readFlag = readFlag;
	}


	public String getPushFlag() {
		return pushFlag;
	}


	public void setPushFlag(String pushFlag) {
		this.pushFlag = pushFlag;
	}


	public String getRecipientId() {
		return recipientId;
	}


	public void setRecipientId(String recipientId) {
		this.recipientId = recipientId;
	}


	public String getDeleteFlag() {
		return deleteFlag;
	}


	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}


	public String getRecipientName() {
		return recipientName;
	}


	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}
	
}
