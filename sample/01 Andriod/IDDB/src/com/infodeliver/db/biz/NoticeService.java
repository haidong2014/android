package com.infodeliver.db.biz;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.infodeliver.db.NSDataBaseOpenHelper;
import com.infodeliver.db.domain.NoticeBizBean;

/**
 * PersonService
 * 
 * @author yujian
 * @version 1.0
 * @createDate 2014-07-29
 * @lastUpdate 2014-07-29
 */
public class NoticeService {

	private NSDataBaseOpenHelper dbOpenHelper;

	public NoticeService(Context context) {
		dbOpenHelper = new NSDataBaseOpenHelper(context);
	}

	public void save(NoticeBizBean notice) {
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		database.beginTransaction();
		database.execSQL(
				"insert into notice(CATEGORY_ID,SUBJECT,TEXT,IMPORTANCE,"
						+ "RECIPIENT_ARRAY,SEND_DATE,REPLY_FLAG,"
						+ "user_id,create_time,delete_flag,update_time)"
						+ "values(?,?,?,?,?,?,?,?,datetime(),'0',datetime())",
				new Object[] { notice.getCategoryId(), notice.getSubjectText(),
						notice.getText(), notice.getImportance(),
						notice.getRecipientArray(), notice.getSendDate(),
						notice.getReplyFlag(), notice.getUserId() });
		database.setTransactionSuccessful();
		database.endTransaction();

	}

//	public void update(Person person) {
//		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
//		database.execSQL(
//				"update person set name=?,age=? where personid=?",
//				new Object[] { person.getName(), person.getAge(),
//						person.getId() });
//	}
//
//	public Person find(Integer id) {
//		SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
//		Cursor cursor = database.rawQuery(
//				"select * from person where personid=?",
//				new String[] { String.valueOf(id) });
//		if (cursor.moveToNext()) {
//			return new Person(cursor.getInt(0), cursor.getString(1),
//					cursor.getShort(2));
//		}
//		return null;
//	}

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
			database.delete("person", "personid in(" + sb.toString() + ")",
					strIds);
		}
	}

//	public List<Person> getScrollData(int startResult, int maxResult) {
//		List<Person> persons = new ArrayList<Person>();
//		SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
//		Cursor cursor = database.rawQuery(
//				"select * from person limit ?,?",
//				new String[] { String.valueOf(startResult),
//						String.valueOf(maxResult) });
//		while (cursor.moveToNext()) {
//			persons.add(new Person(cursor.getInt(0), cursor.getString(1),
//					cursor.getShort(2)));
//		}
//		return persons;
//	}

	// SimpleCursorAdapter。
	public Cursor getRawScrollData(int startResult, int maxResult) {
		SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
		return database.rawQuery(
				"select personid as _id ,name,age from person limit ?,?",
				new String[] { String.valueOf(startResult),
						String.valueOf(maxResult) });

	}

	public long getCount() {
		SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
		Cursor cursor = database.rawQuery("select count(*) from person", null);
		if (cursor.moveToNext()) {
			return cursor.getLong(0);
		}
		return 0;
	}

}
