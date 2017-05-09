package com.xqd.meizhi.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.ActivityCompat;

/**
 * Created by pherson on 2017-5-8.
 */

public class Invoke {

    /**
     * 获取6.0权限
     *
     * @param context
     * @param requestCode
     */
    public static void phonePermission(Context context, int requestCode) {
        String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_LOGS,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.SET_DEBUG_APP,
                Manifest.permission.SYSTEM_ALERT_WINDOW,
                Manifest.permission.GET_ACCOUNTS,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.RECEIVE_BOOT_COMPLETED,
                Manifest.permission.PROCESS_OUTGOING_CALLS,
                Manifest.permission.WRITE_APN_SETTINGS};


        ActivityCompat.requestPermissions((Activity) context, mPermissionList, requestCode);

    }
}
