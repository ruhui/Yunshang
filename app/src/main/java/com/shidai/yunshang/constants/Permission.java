package com.shidai.yunshang.constants;

import android.Manifest;

/**
 * Created by Administrator on 2017/2/21.
 */

public class Permission {
    /* 危险权限对照表 */

     /* 读取联系人 */
    public static final String READ_CONTACTS =  Manifest.permission.READ_CONTACTS;
    /* 写入联系人 */
    public static final  String WRITE_CONTACTS =  Manifest.permission.WRITE_CONTACTS;
    /* 访问账户Gmail列表 */
    public static final  String GET_ACCOUNTS =  Manifest.permission.GET_ACCOUNTS;
    /* 读取电话状态 */
    public static final  String READ_PHONE_STATE =  Manifest.permission.READ_PHONE_STATE;
    /* 拨打电话 */
    public static final  String CALL_PHONE =  Manifest.permission.CALL_PHONE;
    /* 使用SIP视频 */
    public static final  String USE_SIP =  Manifest.permission.USE_SIP;
    /* 读取日程提醒 */
    public static final  String READ_CALENDAR =  Manifest.permission.READ_CALENDAR;
    /* 获取精确位置 */
    public static final  String ACCESS_FINE_LOCATION =  Manifest.permission.ACCESS_FINE_LOCATION;
    /* 拍照 */
    public static final  String CAMERA =  Manifest.permission.CAMERA;
    /* 获取粗略位置*/
    public static final  String ACCESS_COARSE_LOCATION =  Manifest.permission.ACCESS_COARSE_LOCATION;
    /* 读取存储卡 */
    public static final  String READ_EXTERNAL_STORAGE =  Manifest.permission.READ_EXTERNAL_STORAGE;
    /* 写入存储卡 */
    public static final  String WRITE_EXTERNAL_STORAGE =  Manifest.permission.WRITE_EXTERNAL_STORAGE;
    /* 录音 */
    public static final  String RECORD_AUDIO =  Manifest.permission.RECORD_AUDIO;
    /* 读取短信内容 */
    public static final  String READ_SMS =  Manifest.permission.READ_SMS;
    /* 发送短信 */
    public static final  String SEND_SMS =  Manifest.permission.SEND_SMS;
}
