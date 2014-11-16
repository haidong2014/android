package com.infodeliver.db.domain;

import java.io.Serializable;

public class NoticeBizBean implements Serializable {
	
	private static final long serialVersionUID = 3254996126241239391L;
	private int id;
	private int categoryId;
	private String subjectText;
	private String text;
	private int groupId;
	private String groupName;
	private int importance;
	private String sendDate;
	private int replyFlag;
	
	private int recipinetType;
	private String recipientArray;
	
	private String userId;
	private String userName;
	private String createTime;
	private String deleteFlag;
	private String updateTime;
	
	private int sender;
	private int pushFlag;
	private int readFlag;
	
	private int getAll;
	private int recipientAll;
	
	//for layout
	private int layoutType;
	//edit date
	private String sendDay;

	public NoticeBizBean() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getSubjectText() {
		return subjectText;
	}

	public void setSubjectText(String subjectText) {
		this.subjectText = subjectText;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getImportance() {
		return importance;
	}

	public void setImportance(int importance) {
		this.importance = importance;
	}

	public String getSendDate() {
		return sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	public int getReplyFlag() {
		return replyFlag;
	}

	public void setReplyFlag(int replyFlag) {
		this.replyFlag = replyFlag;
	}

	public String getRecipientArray() {
		return recipientArray;
	}

	public void setRecipientArray(String recipientArray) {
		this.recipientArray = recipientArray;
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

	public int getLayoutType() {
		return layoutType;
	}

	public void setLayoutType(int layoutType) {
		this.layoutType = layoutType;
	}

	public String getSendDay() {
		return sendDay;
	}

	public void setSendDay(String sendDay) {
		this.sendDay = sendDay;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getSender() {
		return sender;
	}

	public void setSender(int sender) {
		this.sender = sender;
	}

	public int getPushFlag() {
		return pushFlag;
	}

	public void setPushFlag(int pushFlag) {
		this.pushFlag = pushFlag;
	}

	public int getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(int readFlag) {
		this.readFlag = readFlag;
	}

	public int getGetAll() {
		return getAll;
	}

	public void setGetAll(int getAll) {
		this.getAll = getAll;
	}

	public int getRecipientAll() {
		return recipientAll;
	}

	public void setRecipientAll(int recipientAll) {
		this.recipientAll = recipientAll;
	}

	public int getRecipinetType() {
		return recipinetType;
	}

	public void setRecipinetType(int recipinetType) {
		this.recipinetType = recipinetType;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	
}
