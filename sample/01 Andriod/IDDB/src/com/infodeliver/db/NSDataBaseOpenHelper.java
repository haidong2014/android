package com.infodeliver.db;

import com.infodeliver.utils.NSLog;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * DataBaseOpenHelper
 * 
 * @author yujian
 * @version 1.0
 * @createDate 2014-07-29
 * @lastUpdate 2014-07-29
 */
public class NSDataBaseOpenHelper extends SQLiteOpenHelper {

	private static String dbname = "info";
	private static int version = 1;

	public NSDataBaseOpenHelper(Context context) {

		/**
		 * 
		 * @param context
		 * @param dbname
		 * @param null is default factory
		 * @param db
		 *            version > 0
		 */
		super(context, dbname, null, version);
	}

	public NSDataBaseOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		NSLog.d("NSDataBaseOpenHelper->onCreate");
		//user info
		
		//alarm info
		db.execSQL("CREATE TABLE IF NOT EXISTS alarm (notice_id integer primary key, alarm_type integer default 0 ,show_time varchar(20), show_status integer default 0,visible integer default 0 ,user_id varchar(20),create_time timestamp,delete_flag integer,update_time timestamp)");
		//
		// db.execSQL("CREATE TABLE IF NOT EXISTS blog (id integer primary key autoincrement, gid integer, blog_type integer ,memo varchar(1000), visible integer,user_id varchar(20),create_time timestamp,delete_flag integer,update_time timestamp)");
		// db.execSQL("CREATE TABLE IF NOT EXISTS t_resource (id integer primary key autoincrement, gid integer, pid integer ,ptype varchar(2),group integer,category integer,thumb,varchar(200),actual varchar(200) ,user_id varchar(20),create_time timestamp,delete_flag integer,update_time timestamp)");
		// db.execSQL("CREATE TABLE IF NOT EXISTS t_menber (id integer primary key autoincrement, gid integer, pid integer ,ptype varchar(2), member_id varchar(20),read_state varchar(2),user_id varchar(20),create_time timestamp,delete_flag integer,update_time timestamp)");

		// notice
		db.execSQL("CREATE TABLE IF NOT EXISTS notice "
				+ "(ID integer primary key autoincrement, "
				+ "SERVER_ID integer NOT NULL DEFAULT -1,"
				+ "CATEGORY_ID integer, " + "SUBJECT varchar(11) ,"
				+ "TEXT varchar(256), " + "IMPORTANCE char, "
				+ "RECIPIENT_ARRAY varchar(1000)," + "SEND_DATE timestamp, "
				+ "REPLY_FLAG char ," + "user_id varchar(20),"
				+ "create_time timestamp," + "delete_flag integer,"
				+ "update_time timestamp)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL("DROP TABLE IF EXISTS person");
		onCreate(db);
	}

}
