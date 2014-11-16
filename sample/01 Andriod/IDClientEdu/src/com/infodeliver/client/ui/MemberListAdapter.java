package com.infodeliver.client.ui;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.infodeliver.client.edu.R;
import com.infodeliver.db.domain.MemberBizBean;

/**
 * for member adapter
 * 
 * @author yujian
 * @version 1.0
 * @createDate 2014-07-28
 * @lastUpdate 2014-07-28
 */
public class MemberListAdapter extends BaseAdapter {

	private ArrayList<MemberBizBean> mMemberList;
	private LayoutInflater mInflater;

	public boolean mCheckShowFlag = false;
	private class GroupCell {

		String memberId;
		
		ImageView memberImage;
		TextView memberName;
		
		CheckBox memberCheck;
	}

	public MemberListAdapter(Context context, ArrayList<MemberBizBean> memberList) {
		mMemberList = memberList;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return mMemberList.size();
	}

	@Override
	public Object getItem(int position) {
		mMemberList.get(position);
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		GroupCell cell = null;
		View viewitem = null;
		if (null == convertView) {
			viewitem = mInflater.inflate(R.layout.member_list_item, null);
			if (mCheckShowFlag) {
				viewitem.findViewById(R.id.member_cb).setVisibility(View.VISIBLE);
			}
			cell = new GroupCell();
			cell.memberName = (TextView) viewitem
					.findViewById(R.id.member_name);
			cell.memberImage = (ImageView) viewitem
					.findViewById(R.id.member_image);
			cell.memberCheck = (CheckBox) viewitem.findViewById(R.id.member_cb);
			viewitem.setTag(cell);

		} else {
			viewitem = convertView;
			cell = (GroupCell) convertView.getTag();
		}

		// BEAN
		MemberBizBean memberList = mMemberList.get(position);
		cell.memberName.setText(memberList.getMemberName());
		cell.memberCheck.setChecked(memberList.isChecked());
		
		cell.memberCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mMemberList.get(position).setChecked(isChecked);
			}
		});

		return viewitem;
	}
}
