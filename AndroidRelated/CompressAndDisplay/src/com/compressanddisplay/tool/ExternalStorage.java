package com.compressanddisplay.tool;

import java.io.File;

import android.os.Environment;

/**
 * 
 * 外部存储器管理方面
 */
public class ExternalStorage {

	public static String imgType = Environment.DIRECTORY_PICTURES;

	/**
	 * 检查外部存储器是否可用(可读写；有外部存储器) 如果可用，则返回true；不可用则返回false
	 * 
	 */

	public ExternalStorage() {

	}

	public boolean checkExternalStorage() {

		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * 返回外部存储器的所指向的一个外部目录 //// *@param type--根据类型，Android会为你自动生成；在本次项目中， //
	 * *可以使用Environment.DIRECTORY_PICTURES类型
	 * 不使用这种方式，有些系统未必会为你自动生成。采用直接获取外部存储器的绝对路径的方式。
	 * 
	 */
	public File getExterDir() {
		if (checkExternalStorage()) {
			// 使用API 1.0的方法。
			File file = Environment.getExternalStorageDirectory();
			return file;

		} else {
			return null;
		}
	}

}
