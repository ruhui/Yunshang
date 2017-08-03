package com.shidai.yunshang.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v13.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import com.shidai.yunshang.R;

/**
 * 单个权限的控制
 * Created by Administrator on 2017/3/3.
 */

public class PermissionUtili {
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermission(final Context context, final String[] PERMITION, String title, String content)
    {
        boolean checked = false;
        String permission = "";
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if(currentAPIVersion>= Build.VERSION_CODES.M)
        {
            for (int i=0;i<PERMITION.length; i++){
                String permissions = PERMITION[i];
                if (ContextCompat.checkSelfPermission(context, permissions) != PackageManager.PERMISSION_GRANTED) {
                    checked = true;
                    permission = permissions;
                }
            }
            if (checked){
                //需要权限
                //是否sdk>23,是的话需要提醒权限
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permission)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle(title);
                    alertBuilder.setMessage(content);
                    alertBuilder.setPositiveButton(R.string.submit_sure, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, PERMITION, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity) context, PERMITION, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            }else{
                return  true;
            }
        } else {
            return true;
        }
    }
}
