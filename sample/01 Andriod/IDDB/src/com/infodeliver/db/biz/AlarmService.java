package com.infodeliver.db.biz;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.infodeliver.db.NSDataBaseOpenHelper;
import com.infodeliver.db.domain.AlarmBizBean;

/**
 * AlarmService
 * 
 * @author yujian
 * @version 1.0
 * @createDate 2014-08-15
 * @lastUpdate 2014-08-15
 */
public class AlarmService {

	private NSDataBaseOpenHelper dbOpenHelper;

	public AlarmService(Context context) {
		dbOpenHelper = new NSDataBaseOpenHelper(context);
	}

	public Cursor getAllData() {
		SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
		return database.rawQuery(
				"select notice_id,show_time from alarm where delete_flag = 0",
				null);
	}
	
	public void save(AlarmBizBean alarm) {
		delete(alarm.getNoticeId());
		insert(alarm);
	}

	public void insert(AlarmBizBean alarm) {
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		database.beginTransaction();
		database.execSQL(
				"insert into alarm (notice_id,show_time,user_id,create_time,delete_flag,update_time)values(?,?,?,datetime(),'0',datetime())",
				new Object[] { alarm.getNoticeId(), alarm.getShowTime(),
						alarm.getUserId() });
		database.setTransactionSuccessful();
		database.endTransaction();
	}

	public AlarmBizBean find(Integer id) {
		SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
		Cursor cursor = database
				.rawQuery(
						"select notice_id,show_time from alarm where notice_id=? and show_status = 0",
						new String[] { String.valueOf(id) });
		if (cursor.moveToNext()) {
			AlarmBizBean alarm = new AlarmBizBean();
			alarm.setNoticeId(cursor.getInt(0));
			alarm.setShowTime(cursor.getString(1));
			return alarm;
		}
		return null;
	}

	public void updateShowStatus(int noticeId) {
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		database.execSQL("update alarm set show_status=1 where notice_id=?",
				new Object[] { noticeId });
	}

	public long getCount() {
		SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
		Cursor cursor = database.rawQuery(
				"select count(id) as count from alarm", null);
		if (cursor.moveToNext()) {
			return cursor.getLong(0);
		}
		return 0;
	}

	public void delete(Integer... ids) {
		if (ids.length > 0) {
			StringBuffer sb = new StringBuffer();
			String[] strIds = new String[ids.length];
			for (int i = 0; i < strIds.length; i++) {
				sb.append('?').append(',');
				strIds[i] = String.valueOf(ids[i]);
			}
			sb.deleteCharAt(sb.length() - 1);
			SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
			database.delete("alarm", "notice_id in(" + sb.toString() + ")",
					strIds);
		}
	}

}
