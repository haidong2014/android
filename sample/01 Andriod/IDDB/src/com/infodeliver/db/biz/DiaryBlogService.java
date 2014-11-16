package com.infodeliver.db.biz;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.infodeliver.db.NSDataBaseOpenHelper;
import com.infodeliver.db.domain.DiaryBizBean;

/**
 * DiaryBlogService
 * 
 * @author yujian
 * @version 1.0
 * @createDate 2014-07-11
 * @lastUpdate 2014-07-11
 */
public class DiaryBlogService {

	private NSDataBaseOpenHelper dbOpenHelper;

	public DiaryBlogService(Context context) {
		dbOpenHelper = new NSDataBaseOpenHelper(context);
	}

	public void save(DiaryBizBean diaryBlog) {
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		database.beginTransaction();
		database.execSQL("insert into blog (gid,blog_type,memo,visible,user_id,create_time,delete_flag,update_time)values(?,?,?,?,?,datetime(),'0',datetime())",
				new Object[] { diaryBlog.getGid(),diaryBlog.getBlogType(),diaryBlog.getMemo(),diaryBlog.getVsible(),diaryBlog.getUserId() });
		database.setTransactionSuccessful();
		database.endTransaction();
	}


	public Cursor getRawScrollData(int startResult, int maxResult) {
		SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
		return database.rawQuery(
				"select id,gid,blog_type,memo,visible,user_id from blog order by update_time desc limit ?,? ",
				new String[] { String.valueOf(startResult),
						String.valueOf(maxResult) });

	}

	public long getCount() {
		SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
		Cursor cursor = database.rawQuery("select count(*) from blog", null);
		if (cursor.moveToNext()) {
			return cursor.getLong(0);
		}
		return 0;
	}

}
