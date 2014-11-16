package com.infodeliver.ui;

public interface NSOnPullDownListener {

	public static final int WHAT_DID_LOAD_DATA = 0;
	public static final int WHAT_DID_REFRESH = 1;
	public static final int WHAT_DID_MORE = 2;
	
	void onRefresh();

	void onMore();
}
