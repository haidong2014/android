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
import com.infodeliver.db.domain.GroupBizBean;

/**
 * for group adapter
 * 
 * @author yujian
 * @version 1.0
 * @createDate 2014-07-28
 * @lastUpdate 2014-07-28
 */
public class GroupListAdapter extends BaseAdapter {

	private ArrayList<GroupBizBean> mGroupList;
	private LayoutInflater mInflater;

	private class GroupCell {

		String groupId;
		
		ImageView groupImage;
		TextView groupName;
	}

	public GroupListAdapter(Context context, ArrayList<GroupBizBean> groupList) {
		mGroupList = groupList;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return mGroupList.size();
	}

	@Override
	public Object getItem(int position) {
		mGroupList.get(position);
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		GroupCell cell = null;
		View viewitem = null;
		if (null == convertView) {
			viewitem = mInflater.inflate(R.layout.group_list_item, null);
			cell = new GroupCell();
			cell.groupName = (TextView) viewitem
					.findViewById(R.id.group_name);
			cell.groupImage = (ImageView) viewitem
					.findViewById(R.id.group_image);
			viewitem.setTag(cell);

		} else {
			viewitem = convertView;
			cell = (GroupCell) convertView.getTag();
		}

		// BEAN
		GroupBizBean groupList = mGroupList.get(position);
		cell.groupName.setText(groupList.getGroupName());

		return viewitem;
	}
}
