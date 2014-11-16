package com.infodeliver.client.ui;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.infodeliver.client.edu.R;
import com.infodeliver.db.domain.ReplyBizBean;

/**
 * for reply adapter
 * 
 * @author yujian
 * @version 1.0
 * @createDate 2014-08-21
 * @lastUpdate 2014-08-21
 */
public class ReplyListAdapter extends BaseAdapter {

	private ArrayList<ReplyBizBean> mReplyList;
	private LayoutInflater mInflater;

	private class ReplyCell {

		ImageView userImage;
		TextView replyText;
		TextView userName;
		TextView createTime;
	}

	public ReplyListAdapter(Context context, ArrayList<ReplyBizBean> replyList) {
		mReplyList = replyList;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return mReplyList.size();
	}

	@Override
	public Object getItem(int position) {
		mReplyList.get(position);
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ReplyCell cell = null;
		View viewitem = null;
		if (null == convertView) {
			viewitem = mInflater.inflate(R.layout.reply_list_item, null);
			cell = new ReplyCell();
			cell.userName = (TextView) viewitem.findViewById(R.id.user_name);
			cell.replyText = (TextView) viewitem.findViewById(R.id.reply_text);
			cell.createTime = (TextView) viewitem
					.findViewById(R.id.create_time);
			viewitem.setTag(cell);

		} else {
			viewitem = convertView;
			cell = (ReplyCell) convertView.getTag();
		}

		// BEAN
		ReplyBizBean replyList = mReplyList.get(position);
		cell.userName.setText(replyList.getReplyUserName());
		cell.replyText.setText(replyList.getReplyText());
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date d = new Date();
		d.setTime(Long.parseLong(replyList.getCreateTime()));
		String tempTime = df.format(d);
		cell.createTime
				.setText(tempTime);
		return viewitem;
	}
}
