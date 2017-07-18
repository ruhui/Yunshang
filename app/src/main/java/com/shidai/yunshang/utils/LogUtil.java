package com.shidai.yunshang.utils;

import android.util.Log;

/**
 * Created by Administrator on 2017/3/21.
 */

public class LogUtil {

    private static boolean showlog = true;

    public static void E(String key,Object object){
        if (showlog){
            Log.e(key, object.toString());
        }
    }
}
