package com.shidai.yunshang.utils;

import android.content.Context;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 关于时间和数字的一些工具方法
 */

public class Utils {

	static final String LOG_TAG = "PullToRefresh";

	public static void warnDeprecation(String depreacted, String replacement) {
		Log.w(LOG_TAG, "You're using the deprecated " + depreacted + " attr, please switch over to " + replacement);
	}

	public static int dip2px(Context context, float dipValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}


	public static int px2dip(Context context, float pxValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/*
     * 将时间转换为时间戳
     */
	public static String date2TimeStamp(String date_str,String format){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return String.valueOf(sdf.parse(date_str).getTime()/1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/*
    * 将时间戳转换为时间
    */
	public static String stampToDate(String s){
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
		long lt = new Long(s);
		long lcc_time = Long.valueOf(lt);
		res = simpleDateFormat.format(new Date(lcc_time * 1000L));
		return res;
	}

	/**
	 *  获取当前时间戳
	 */
	public static int getTimestamp(Date date){
		String timestamp = String.valueOf(date.getTime());
		int length = timestamp.length();
		if (length > 3) {
			return Integer.valueOf(timestamp.substring(0,length-3));
		} else {
			return 0;
		}
	}

	/**
	 * 获取16位随机数
	 */
	public static String getStringRandom(int length) {

		String val = "";
		Random random = new Random();

		//参数length，表示生成几位随机数
		for(int i = 0; i < length; i++) {

			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			//输出字母还是数字
			if( "char".equalsIgnoreCase(charOrNum) ) {
				//输出是大写字母还是小写字母
				int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
				val += (char)(random.nextInt(26) + temp);
			} else if( "num".equalsIgnoreCase(charOrNum) ) {
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val;
	}

	/**
	 * 获取当前时间精确到毫秒
	 */
	public static String getMillSecond(){
		return new SimpleDateFormat("yyyyMMddHHmmssSSS") .format(new Date() );
	}

	/**
	 * 获取设备ID，此处为自己生成的
	 * 用户按装app时生成的随机串  格式如下
	 * 年份+日期+时间（精确到毫秒）+15位随机字符串
	 */
	public static String getDeviceId(){
		return  getMillSecond() + getStringRandom(15);
	}

	// 校验Tag Alias 只能是数字,英文字母和中文
	public static boolean isValidTagAndAlias(String s) {
		Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_!@#$&*+=.|]+$");
		Matcher m = p.matcher(s);
		return m.matches();
	}

}
