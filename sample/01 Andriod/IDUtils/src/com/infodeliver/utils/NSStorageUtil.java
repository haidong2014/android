package com.infodeliver.utils;

import java.io.File;
import android.os.Environment;
import android.os.StatFs;

public class NSStorageUtil {

	private static final int ERROR = -1;
	public static int save_dir = 1;

	// memory available size
	public static long getAvailableInternalMemorySize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return availableBlocks * blockSize;
	}

	// memory total size
	public static long getTotalInternalMemorySize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long totalBlocks = stat.getBlockCount();
		return totalBlocks * blockSize;
	}

	// check SDCard exist
	public static boolean isSDCardExist() {
		return android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
	}
	
	// SDCard available size
	public static long getAvailableExternalMemorySize() {
		if (isSDCardExist()) {
			File path = Environment.getExternalStorageDirectory();
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long availableBlocks = stat.getAvailableBlocks();
			return availableBlocks * blockSize;
		} else {
			return ERROR;
		}
	}

	// SDCard total size
	public static long getTotalExternalMemorySize() {
		if (isSDCardExist()) {
			File path = Environment.getExternalStorageDirectory();
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long totalBlocks = stat.getBlockCount();
			return totalBlocks * blockSize;
		} else {
			return ERROR;
		}
	}

	// SDCard external_sd total size
	public static long getTotalExternal_SDMemorySize() {
		if (isSDCardExist()) {
			File path = Environment.getExternalStorageDirectory();
			File externalSD = new File(path.getPath() + "/external_sd");
			if (externalSD.exists() && externalSD.isDirectory()) {
				StatFs stat = new StatFs(path.getPath() + "/external_sd");
				long blockSize = stat.getBlockSize();
				long totalBlocks = stat.getBlockCount();
				if (getTotalExternalMemorySize() != -1
						&& getTotalExternalMemorySize() != totalBlocks
								* blockSize) {
					return totalBlocks * blockSize;
				} else {
					return ERROR;
				}
			} else {
				return ERROR;
			}

		} else {
			return ERROR;
		}
	}

	// SDCard external_sd available size
	public static long getAvailableExternal_SDMemorySize() {
		if (isSDCardExist()) {
			File path = Environment.getExternalStorageDirectory();
			File externalSD = new File(path.getPath() + "/external_sd");
			if (externalSD.exists() && externalSD.isDirectory()) {
				StatFs stat = new StatFs(path.getPath() + "/external_sd");
				long blockSize = stat.getBlockSize();
				long availableBlocks = stat.getAvailableBlocks();
				if (getAvailableExternalMemorySize() != -1
						&& getAvailableExternalMemorySize() != availableBlocks
								* blockSize) {
					return availableBlocks * blockSize;
				} else {
					return ERROR;
				}

			} else {
				return ERROR;
			}

		} else {
			return ERROR;
		}
	}
}