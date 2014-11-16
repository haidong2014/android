package com.infodeliver.db.domain;

public class DiaryBizBean {
	private int id;
	private int gid;
	private int blogType;
	private String memo;
	private int vsible;
	
	private String userId;
	private String createTime;
	private String deleteFlag;
	private String updateTime;
	

	public DiaryBizBean() {
	}

	public DiaryBizBean(int gid,int blogType,String memo,int visible,String userId) {
		this.gid = gid;
		this.blogType = blogType;
		this.memo = memo;
		this.vsible = visible;
		this.userId = userId;
	}

	public DiaryBizBean(int id, int gid,int blogType,String memo,int visible,String userId) {
		super();
		this.id = id;
		this.gid = gid;
		this.blogType = blogType;
		this.memo = memo;
		this.vsible = visible;
		this.userId = userId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public int getBlogType() {
		return blogType;
	}

	public void setBlogType(int blogType) {
		this.blogType = blogType;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public int getVsible() {
		return vsible;
	}

	public void setVsible(int vsible) {
		this.vsible =vsible;
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
