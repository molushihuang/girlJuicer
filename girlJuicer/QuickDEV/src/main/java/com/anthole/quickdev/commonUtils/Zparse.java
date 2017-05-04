package com.anthole.quickdev.commonUtils;

import android.text.TextUtils;

public class Zparse {
	
	/**
	 * 转为int  如果转失败 返回-1
	 * @param str
	 * @return
	 */
	public static int toInt(String str){
		if(TextUtils.isEmpty(str)){
			return -1;
		}
		int i = 0;
		try {
			i = Integer.parseInt(str);
			return i;
		} catch (Exception e) {
			// : handle exception
			return -1;
		}
	}
	
	/**
	 * 转为int  如果转失败 返回-1
	 * @param str
	 * @return
	 */
	public static int toInt(String str,int defaultValue){
		if(TextUtils.isEmpty(str)){
			return defaultValue;
		}
		int i = 0;
		try {
			i = Integer.parseInt(str);
			return i;
		} catch (Exception e) {
			// : handle exception
			return defaultValue;
		}
	}
	
	/**
	 * 转为int  如果转失败 返回-1
	 * @param str
	 * @return
	 */
	public static long toLong(String str){
		if(TextUtils.isEmpty(str)){
			return -1;
		}
		long l = 0l;
		try {
			l = Long.parseLong(str);
			return l;
		} catch (Exception e) {
			// : handle exception
			return -1;
		}
	}
	
	/**
	 * 转为int  如果转失败 返回-1
	 * @param str
	 * @return
	 */
	public static float toFloat(String str){
		if(TextUtils.isEmpty(str)){
			return 0;
		}
		float i = 0;
		try {
			i = Float.parseFloat(str);
			return i;
		} catch (Exception e) {
			// : handle exception
			return 0;
		}
	}

	public static double toDouble(String str){
		if(TextUtils.isEmpty(str)){
			return 0;
		}
		double i = 0;
		try {
			i = Double.parseDouble(str);
			return i;
		} catch (Exception e) {
			// : handle exception
			return 0;
		}
	}

}
