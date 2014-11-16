package com.infodeliver.client.ui;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.infodeliver.client.edu.R;
import com.infodeliver.db.domain.AlarmBizBean;
import com.infodeliver.db.domain.NoticeBizBean;
import com.infodeliver.ui.NSOnClickListener;
import com.infodeliver.utils.NSLog;
import com.infodeliver.utils.NSResourceManager;

/**
 * for notice adapter
 * 
 * @author yujian
 * @version 1.0
 * @createDate 2014-07-22
 * @lastUpdate 2014-07-22
 */
public class NoticeListAdapter extends BaseAdapter {

	public static final int TYPE_NOTICE = 0;
	public static final int TYPE_DATE = 1;
	private static final int TYPE_COUNT = 2;

	private NSOnClickListener callback;
	private ArrayList<NoticeBizBean> mNoticeList;
	private HashMap<Integer, AlarmBizBean> mAlarmMap;
	private LayoutInflater mInflater;
	
	private Context mContext;

	public class MsgCell {

		ImageView msgType;
		TextView memo;
		ImageButton msgRemindBtn;
		ImageButton msgEditBtn;
		ImageButton msgReadBtn;
	}

	public class DateCell {

		TextView dateTime;
	}

	public NoticeListAdapter(Context context, ArrayList<NoticeBizBean> messageList) {
		this.mContext = context;
		mNoticeList = messageList;
		mInflater = LayoutInflater.from(context);
		this.callback = (NSOnClickListener) context;
	}

	@Override
	public int getCount() {
		return mNoticeList.size();
	}

	@Override
	public Object getItem(int position) {
		mNoticeList.get(position);
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		
		NoticeBizBean tempMsg = mNoticeList.get(position);
		if (tempMsg.getLayoutType() == TYPE_DATE) {
			return TYPE_DATE;
		} else {
			return TYPE_NOTICE;
		}
	}

	@Override
	public int getViewTypeCount() {
		return TYPE_COUNT;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MsgCell msgCell = null;
		DateCell dateCell = null;
		View viewitem = null;
		int type = getItemViewType(position);
		if (null == convertView) {

			switch (type) {
			case TYPE_DATE:
				viewitem = mInflater.inflate(R.layout.notice_msg_date_item, null);
				dateCell = new DateCell();
				dateCell.dateTime = (TextView) viewitem
						.findViewById(R.id.date_text);

				viewitem.setTag(dateCell);
				break;
			case TYPE_NOTICE:
				viewitem = mInflater.inflate(R.layout.notice_msg_item, null);
				msgCell = new MsgCell();
				msgCell.msgType = (ImageView) viewitem
						.findViewById(R.id.msg_type_image);
				msgCell.memo = (TextView) viewitem.findViewById(R.id.msg_memo);
				msgCell.msgRemindBtn = (ImageButton) viewitem.findViewById(R.id.msg_remind);
				msgCell.msgEditBtn = (ImageButton) viewitem.findViewById(R.id.msg_edit);
				msgCell.msgReadBtn = (ImageButton) viewitem.findViewById(R.id.msg_read);

				viewitem.setTag(msgCell);
				break;
			}

		} else {
			viewitem = convertView;
			switch (type) {
			case TYPE_DATE:
				dateCell = (DateCell) convertView.getTag();
				break;
			case TYPE_NOTICE:
				msgCell = (MsgCell) convertView.getTag();
				break;
			}
		}

		NoticeBizBean notice = mNoticeList.get(position);
		switch (type) {
		case TYPE_DATE:
			dateCell.dateTime.setText(notice.getSendDay());
			break;

		case TYPE_NOTICE:
			// by type
			if (!TextUtils.isEmpty(notice.getCategoryId() + "") && notice.getCategoryId() != -1) {
				msgCell.msgType.setBackgroundResource(NSResourceManager.getResourceID("iconfont_type_" + notice.getCategoryId() + "_64", "drawable"));
			} else {
				msgCell.msgType.setBackgroundResource(NSResourceManager.getResourceID("iconfont_subject_default", "drawable"));
			}

			msgCell.memo.setText(notice.getText());
			final View view = convertView;
	        final int p = position;
	        final int editBtnID = msgCell.msgEditBtn.getId();
			msgCell.msgEditBtn.setOnClickListener(new OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	NSLog.i("adapter edit");
	                callback.onClick(view, p, editBtnID);
	            }
	        });
			
			final int noticeId = notice.getId();
			final int remindBtnID = msgCell.msgRemindBtn.getId();
			if (null != mAlarmMap && mAlarmMap.containsKey(noticeId)) {
				msgCell.msgRemindBtn.setVisibility(View.VISIBLE);
				msgCell.msgRemindBtn.setOnClickListener(new OnClickListener() {
		            @Override
		            public void onClick(View v) {
		            	NSLog.d("adapter alarm time is " + mAlarmMap.get(noticeId).getShowTime());
//		                callback.onClick(view, p, remindBtnID);
		                callback.onPrivateEvent(p, remindBtnID, mAlarmMap.get(noticeId).getShowTime());
		            }
		        });
			} else {
				msgCell.msgRemindBtn.setVisibility(View.INVISIBLE);
			}
			
			
			if (notice.getReadFlag() == 0) {
				msgCell.msgReadBtn.setVisibility(View.VISIBLE);
			} else {
				msgCell.msgReadBtn.setVisibility(View.INVISIBLE);
			}
			
			break;
			
		}

		return viewitem;
	}

	public HashMap<Integer, AlarmBizBean> getmAlarmMap() {
		return mAlarmMap;
	}

	public void setmAlarmMap(HashMap<Integer, AlarmBizBean> mAlarmMap) {
		this.mAlarmMap = mAlarmMap;
	}
	
	
}
