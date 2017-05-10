package com.xqd.meizhi.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import com.xqd.meizhi.Constants;
import com.xqd.meizhi.activity.WebActivity;

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

    /**
     * 打开网页
     * @param context
     * @param url
     * @param title
     */
    public static void openLink(Context context, String url, String title) {
        Intent intent = new Intent(context, WebActivity.class);
        if (!TextUtils.isEmpty(title)) {
            intent.putExtra(Constants.IntentKeys.TITLE, title);
        }
        intent.putExtra(Constants.IntentKeys.URL, url);
        context.startActivity(intent);
    }
}
