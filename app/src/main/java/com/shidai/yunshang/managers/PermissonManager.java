package com.shidai.yunshang.managers;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * 权限控制管理器
 * Created by Administrator on 2017/2/22.
 */

public class PermissonManager {

    private Context mContext;

    public PermissonManager(Context context){
        mContext = context.getApplicationContext();
    }

    // 判断权限集合
     public boolean lacksPermissions(String... permissions) {
         for (String permission : permissions) {
             if (lacksPermission(permission)) {
                 return true;
             }
         }
         return false;
     }

    // 判断是否缺少权限
    private boolean lacksPermission(String permission) {
        return ContextCompat.checkSelfPermission(mContext, permission) == PackageManager.PERMISSION_DENIED;
    }

}
