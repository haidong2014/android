/**
 * 
 */
package com.infodeliver.client.edu.list;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.infodeliver.client.base.NSActivityBase;
import com.infodeliver.client.edu.R;
import com.infodeliver.client.utils.NSBizConstant;
import com.infodeliver.db.domain.MemberBizBean;
import com.infodeliver.utils.NSLog;

/**
 * Member list
 * 
 * @author yujian
 * @version 1.0
 * @createDate 2014-08-12
 * @lastUpdate 2014-08-12
 */
public class NoticeSubjectImagerListActivity extends NSActivityBase {
	private static final String TAG = "NoticeSubjectImagerListActivity";

	private TextView mTitleLeftBtn;
	private GridView mNoticeSubjectImageGridView;

	ArrayList<HashMap<String, Object>> mSubjectImageItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notice_subject_image_list);
		
		initTitle();
		initView();
		initData();
	}

	private void initView() {
		mNoticeSubjectImageGridView = (GridView) findViewById(R.id.notice_subject_image_gv);
	}

	private void initTitle() {
		mTitleLeftBtn = (TextView) findViewById(R.id.title_left_btn);
		mTitleLeftBtn.setClickable(true);
	}

	private void initData() {

		mSubjectImageItem = new ArrayList<HashMap<String, Object>>();
//		for (int i = 0; i < 10; i++) {
//			HashMap<String, Object> map = new HashMap<String, Object>();
//			map.put("ItemImage", R.drawable.iconfont_subject_default);
//			map.put("ItemText", "NO." + String.valueOf(i));
//			lstImageItem.add(map);
//		}
		
		HashMap<String, Object> map0 = new HashMap<String, Object>();
		map0.put("ItemImage", R.drawable.iconfont_type_0_64);
		map0.put("ItemText", "休息");
		mSubjectImageItem.add(map0);
		HashMap<String, Object> map1 = new HashMap<String, Object>();
		map1.put("ItemImage", R.drawable.iconfont_type_1_64);
		map1.put("ItemText", "吃");
		mSubjectImageItem.add(map1);
		HashMap<String, Object> map2 = new HashMap<String, Object>();
		map2.put("ItemImage", R.drawable.iconfont_type_2_64);
		map2.put("ItemText", "喝");
		mSubjectImageItem.add(map2);
		HashMap<String, Object> map3 = new HashMap<String, Object>();
		map3.put("ItemImage", R.drawable.iconfont_type_3_64);
		map3.put("ItemText", "玩");
		mSubjectImageItem.add(map3);
		HashMap<String, Object> map4 = new HashMap<String, Object>();
		map4.put("ItemImage", R.drawable.iconfont_type_4_64);
		map4.put("ItemText", "乐");
		mSubjectImageItem.add(map4);
		HashMap<String, Object> map5 = new HashMap<String, Object>();
		map5.put("ItemImage", R.drawable.iconfont_type_5_64);
		map5.put("ItemText", "生日");
		mSubjectImageItem.add(map5);
		

		SimpleAdapter gridData = new SimpleAdapter(this, 
				mSubjectImageItem,
				R.layout.notice_subject_image_list_item,
				new String[] { "ItemImage", "ItemText" },
				new int[] { R.id.subject_image, R.id.subject_text });
		
		mNoticeSubjectImageGridView.setAdapter(gridData);
		mNoticeSubjectImageGridView.setOnItemClickListener(new ItemClickListener());
	}

	class ItemClickListener implements OnItemClickListener {
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			//back with id image
			Intent backIntent = new Intent();
			NSLog.d(TAG, "index:" + arg3);
			NSLog.d(TAG, "image_id:" + mSubjectImageItem.get((int)arg3).get("ItemImage"));
			int imageId = Integer.parseInt(mSubjectImageItem.get((int)arg3).get("ItemImage").toString());
			backIntent.putExtra(NSBizConstant.KEY_NOTICE_SUBJECT, (int)arg3);
//			backIntent.putExtra(NSBizConstant.KEY_NOTICE_SUBJECT_IMAGE_ID, imageId);
			setResult(RESULT_OK, backIntent);
			finish();
			
		}
	}
	
	@Override
	public void onError(int requestID, String errorCode, String errorDesc) {
		NSLog.i(TAG, "onError");
		NSLog.e(errorCode);
		showLoadFailView();
	}

	@Override
	public void onResult(int requestID, String result) {
		NSLog.i(TAG, "onResult");
		NSLog.d("result json", result);
		showContentView();
	}

	@Override
	protected void onReloadContent() {
		NSLog.i(TAG, "onReloadContent");
		super.onReloadContent();
	}

	public void doLeftClick(View v) {
		finish();
	}

	@Override
	protected void onResume() {
		NSLog.i(TAG, "onResume");
		super.onResume();
	}
}
