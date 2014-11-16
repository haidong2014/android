package com.infodeliver.db.domain;

public class ReplyBizBean {
	private int id;
	private int noticeId;
	private String replyText;
	private String replyUserId;
	private String replyUserName;
	
	private String userId;
	private String createTime;
	private String deleteFlag;
	private String updateTime;

	public ReplyBizBean() {
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


	public String getReplyUserId() {
		return replyUserId;
	}


	public void setReplyUserId(String replyUserId) {
		this.replyUserId = replyUserId;
	}


	public String getReplyUserName() {
		return replyUserName;
	}


	public void setReplyUserName(String replyUserName) {
		this.replyUserName = replyUserName;
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

	public String getReplyText() {
		return replyText;
	}

	public void setReplyText(String replyText) {
		this.replyText = replyText;
	}
	
}
