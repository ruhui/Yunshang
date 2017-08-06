package com.shidai.yunshang.networks.responses;

/**
 * 创建时间： 2017/8/6.
 * 作者：黄如辉
 * 功能描述：
 */

public class VersionResponst {
    private int android_version_num;
    private String android_version;
    private String android_update_content;
    private String android_update_url;
    private boolean is_force_update;
    private long file_size;
    private int region_version;
    private String service_phone;
    private String min_transfer;

    public VersionResponst(int android_version_num, String android_version, String android_update_content, String android_update_url, boolean is_force_update, long file_size, int region_version, String service_phone, String min_transfer) {
        this.android_version_num = android_version_num;
        this.android_version = android_version;
        this.android_update_content = android_update_content;
        this.android_update_url = android_update_url;
        this.is_force_update = is_force_update;
        this.file_size = file_size;
        this.region_version = region_version;
        this.service_phone = service_phone;
        this.min_transfer = min_transfer;
    }

    public int getAndroid_version_num() {
        return android_version_num;
    }

    public void setAndroid_version_num(int android_version_num) {
        this.android_version_num = android_version_num;
    }

    public String getAndroid_version() {
        return android_version;
    }

    public void setAndroid_version(String android_version) {
        this.android_version = android_version;
    }

    public String getAndroid_update_content() {
        return android_update_content;
    }

    public void setAndroid_update_content(String android_update_content) {
        this.android_update_content = android_update_content;
    }

    public String getAndroid_update_url() {
        return android_update_url;
    }

    public void setAndroid_update_url(String android_update_url) {
        this.android_update_url = android_update_url;
    }

    public boolean is_force_update() {
        return is_force_update;
    }

    public void setIs_force_update(boolean is_force_update) {
        this.is_force_update = is_force_update;
    }

    public long getFile_size() {
        return file_size;
    }

    public void setFile_size(long file_size) {
        this.file_size = file_size;
    }

    public int getRegion_version() {
        return region_version;
    }

    public void setRegion_version(int region_version) {
        this.region_version = region_version;
    }

    public String getService_phone() {
        return service_phone;
    }

    public void setService_phone(String service_phone) {
        this.service_phone = service_phone;
    }

    public String getMin_transfer() {
        return min_transfer;
    }

    public void setMin_transfer(String min_transfer) {
        this.min_transfer = min_transfer;
    }
}
//"ios_version_num": 1,
//        "ios_version": "sample string 2",
//        "ios_update_content": "sample string 3",
//        "ios_update_url": "sample string 4",

//        "android_version_num": 5,
//        "android_version": "sample string 6",
//        "android_update_content": "sample string 7",
//        "android_update_url": "sample string 8",
//        "is_force_update": true,
//        "file_size": 10,
//        "region_version": 11,
//        "service_phone": "sample string 12",
//        "min_transfer": 13.0