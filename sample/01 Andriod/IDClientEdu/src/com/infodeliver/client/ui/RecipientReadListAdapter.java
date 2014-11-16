package com.infodeliver.client.ui;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.infodeliver.client.edu.R;
import com.infodeliver.db.domain.RecipientBizBean;

/**
 * for recipient read adapter
 * 
 * @author yujian
 * @version 1.0
 * @createDate 2014-08-07
 * @lastUpdate 2014-08-07
 */
public class RecipientReadListAdapter extends BaseAdapter {

	private ArrayList<RecipientBizBean> mRecipientList;
	private LayoutInflater mInflater;

	private class RecipientCell {

		String recipientId;
		ImageView recipientImage;
		TextView recipientName;
		ImageView readImage;
	}

	/**
	 * from local db
	 */
	public RecipientReadListAdapter(Context context, ArrayList<RecipientBizBean> groupList) {
		mRecipientList = groupList;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return mRecipientList.size();
	}

	@Override
	public Object getItem(int position) {
		mRecipientList.get(position);
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		RecipientCell cell = null;
		View viewitem = null;
		if (null == convertView) {
			viewitem = mInflater.inflate(R.layout.recipient_read_list_item, null);
			cell = new RecipientCell();
			cell.recipientName = (TextView) viewitem
					.findViewById(R.id.recipient_read_name);
			cell.readImage = (ImageView) viewitem
					.findViewById(R.id.right_image);
			viewitem.setTag(cell);

		} else {
			viewitem = convertView;
			cell = (RecipientCell) convertView.getTag();
		}

		// BEAN
		RecipientBizBean recipientList = mRecipientList.get(position);
		cell.recipientName.setText(recipientList.getRecipientName());
//		cell.groupImage.setBackgroundDrawable(d);
		if ("1".equals(recipientList.getReadFlag())) {
			cell.readImage.setBackgroundResource(R.drawable.iconfont_read_on);
		} else {
			cell.readImage.setBackgroundResource(R.drawable.iconfont_read_off);
		}

		return viewitem;
	}
}
