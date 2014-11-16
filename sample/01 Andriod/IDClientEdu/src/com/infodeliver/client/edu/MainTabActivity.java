package com.infodeliver.client.edu;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;

import com.infodeliver.client.edu.list.GroupListActivity;
import com.infodeliver.client.edu.notice.NoticeDetailActivity;
import com.infodeliver.client.utils.NSBizConstant;
import com.infodeliver.utils.NSLog;
import com.infodeliver.utils.NSSharePreferenceKeeper;

/**
 * tab
 * 
 * @author yujian
 * @version 1.0
 * @createDate 2014-07-01
 * @lastUpdate 2014-07-01
 */
public class MainTabActivity extends TabActivity implements
		OnCheckedChangeListener {
	
	private static final String TAG = "MainTabActivity";
	
	private RadioGroup mainTab;
	private TabHost mTabHost;

	private Intent mNoticeIntent;
	private Intent mDiaryIntent;
//	private Intent mAddIntent;
	private Intent mGroupIntent;
	private Intent mMoreIntent;

//	private final static String TAB_TAG_DIARY = "tab_tag_diary";
	private final static String TAB_TAG_NOTICE = "tab_tag_notice";
	private final static String TAB_TAG_CALENDAR = "tab_tag_calendar";
//	private final static String TAB_TAG_ADD = "tab_tag_add";
	private final static String TAB_TAG_GROUP = "tab_tag_group";
	private final static String TAB_TAG_MORE = "tab_tag_more";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		NSLog.i(TAG,"MainTabActivity>>>onCreate");
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main_tab);
		mainTab = (RadioGroup) findViewById(R.id.main_tab);
		mainTab.setOnCheckedChangeListener(this);
		initTabIntent();
		setupIntent();
		initView();
	}
	
	private void initView() {
		this.mTabHost.setCurrentTab(0);
	}

	private void initTabIntent() {
		mNoticeIntent = new Intent(this, NoticeActivity.class);
		mDiaryIntent = new Intent(this, CalendarActivity.class);
//		mAddIntent = new Intent(this, AddActivity.class);
		mGroupIntent = new Intent(this, GroupListActivity.class);
		mMoreIntent = new Intent(this, MoreActivity.class);
	}

	private void setupIntent() {
		NSLog.d(TAG,"setupIntent");
		this.mTabHost = getTabHost();
		TabHost localTabHost = this.mTabHost;
		localTabHost.addTab(buildTabSpec(TAB_TAG_NOTICE, R.string.tab_notice,
				R.drawable.icon_tab_news, mNoticeIntent));
		localTabHost.addTab(buildTabSpec(TAB_TAG_CALENDAR, R.string.tab_calendar,
				R.drawable.icon_tab_diary, mDiaryIntent));
//		localTabHost.addTab(buildTabSpec(TAB_TAG_ADD, R.string.tab_add,
//				R.drawable.icon_tab_add, mAddIntent));
		localTabHost.addTab(buildTabSpec(TAB_TAG_GROUP, R.string.tab_group,
				R.drawable.icon_tab_push, mGroupIntent));
		localTabHost.addTab(buildTabSpec(TAB_TAG_MORE, R.string.tab_more,
				R.drawable.icon_tab_more, mMoreIntent));
	}

	/**
	 * 
	 * @param tag
	 * @param resLabel
	 * @param resIcon
	 * @param content
	 * @return tab
	 */
	private TabHost.TabSpec buildTabSpec(String tag, int resLabel, int resIcon,
			final Intent content) {
		
		NSLog.d(TAG,"buildTabSpec");
		return this.mTabHost
				.newTabSpec(tag)
				.setIndicator(getString(resLabel),
						getResources().getDrawable(resIcon))
				.setContent(content);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		NSLog.d(TAG,"onCheckedChanged->" + checkedId);
		switch (checkedId) {
		case R.id.radio_button0:
			this.mTabHost.setCurrentTabByTag(TAB_TAG_NOTICE);
			break;
		case R.id.radio_button1:
			this.mTabHost.setCurrentTabByTag(TAB_TAG_CALENDAR);
			break;
//		case R.id.radio_button2:
//			this.mTabHost.setCurrentTabByTag(TAB_TAG_ADD);
//			break;
		case R.id.radio_button2:
			this.mTabHost.setCurrentTabByTag(TAB_TAG_GROUP);
			break;
		case R.id.radio_button3:
			this.mTabHost.setCurrentTabByTag(TAB_TAG_MORE);
			break;
		}
	}
	
	@Override
	protected void onResume() {
		NSLog.i(TAG, "onResume");
		super.onResume();
	}
//	public void doAddShow (View v) {
//		startActivity(mAddIntent);
//	}

}