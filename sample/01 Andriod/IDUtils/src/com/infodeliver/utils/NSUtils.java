package com.infodeliver.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.os.Environment;
import android.text.TextUtils;

/**
 * Normal utils used for application.
 * 
 * @author yujian
 * @version 1.0
 * @createDate 2014-06-23
 * @lastUpdate 2014-06-23
 */
public class NSUtils {

	public static final int PATH_SDCARD = 0;
	public static final int PATH_DATA = 1;
	private static long lastClickTime;
	private static final String PATTERN_CODER = "(?<!\\d)\\d{4}(?!\\d)";

	public static String getWorkFolder(int path) {
		if (PATH_DATA == path) {
			return Environment.getDataDirectory()
					+ "/data/com.infodeliver.client/";
		} else {
			return Environment.getExternalStorageDirectory()
					+ "/Android/data/com.infodeliver.client/";
		}
	}

	public static String formatDate(String srcFormat, String destFormat,
			String dateString) {
		SimpleDateFormat src = new SimpleDateFormat(srcFormat, Locale.CHINA);
		SimpleDateFormat dest = new SimpleDateFormat(destFormat, Locale.CHINA);
		try {
			return dest.format(src.parse(dateString));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if (0 < timeD && timeD < 1000) {
			return true;
		}
		lastClickTime = time;
		return false;
	}

	private static List<Activity> activityList = new ArrayList<Activity>();

	public static void addActivity(Activity activity) {
		activityList.add(activity);
	}

	public static void finishActivity() {
		for (Activity activity : activityList) {
			activity.finish();
		}
	}

	/**
	 * Encrypt byte array.
	 */
	public static byte[] encrypt(byte[] source, String algorithm)
			throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance(algorithm);
		md.reset();
		md.update(source);
		return md.digest();
	}

	/**
	 * Encrypt string
	 */
	public static String encrypt(String source, String algorithm)
			throws NoSuchAlgorithmException {
		byte[] resByteArray = encrypt(source.getBytes(), algorithm);
		return toHexString(resByteArray);
	}

	public static String toHexString(byte[] res) {
		StringBuffer sb = new StringBuffer(res.length << 1);
		for (int i = 0; i < res.length; i++) {
			String digit = Integer.toHexString(0xFF & res[i]);
			if (digit.length() == 1) {
				digit = '0' + digit;
			}
			sb.append(digit);
		}
		return sb.toString().toUpperCase(Locale.US);
	}

	/**
	 * Encrypt string using MD5 algorithm
	 */
	public static String encryptMD5(String source) {
		if (source == null) {
			source = "";
		}

		String result = "";
		try {
			result = encrypt(source, "MD5");
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
		return result;
	}

	/**
	 * 匹配短信中间的6个数字（验证码等）
	 * 
	 * @param patternContent
	 * @return
	 */
	public static String patternCode(String patternContent) {
		if (TextUtils.isEmpty(patternContent)) {
			return null;
		}
		Pattern p = Pattern.compile(PATTERN_CODER);
		Matcher matcher = p.matcher(patternContent);
		if (matcher.find()) {
			return matcher.group();
		}
		return null;
	}

	public static boolean isFutureDayByDay(Date startDate, Date endDate) {
		if (startDate == null || endDate == null) {
			return false;
		}
		long timeLong = endDate.getTime() - startDate.getTime();
		NSLog.d("isFutureDayByDay->timeLong:" + timeLong);
		if (timeLong > 60 * 60 * 24 * 1000) {
			return true;
		} else {
			return false;
		}
	}

}
