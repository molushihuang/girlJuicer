package com.xqd.meizhi.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import com.xqd.meizhi.Constants;
import com.xqd.meizhi.activity.WebActivity;
import com.xqd.meizhi.bean.GirlBean;

import java.io.InputStream;
import java.io.Serializable;

import static com.xqd.meizhi.Constants.IntentKeys.GIRLBEAN;

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

    public static void openLink(Context context, String url, String title,GirlBean item) {
        Intent intent = new Intent(context, WebActivity.class);
        if (!TextUtils.isEmpty(title)) {
            intent.putExtra(Constants.IntentKeys.TITLE, title);
        }
        intent.putExtra(Constants.IntentKeys.URL, url);
        intent.putExtra(GIRLBEAN, (Serializable) item);
        context.startActivity(intent);
    }

    /**
     * 实现文本复制功能 add by wangqianzhou
     *
     * @param content
     */
    public static void clipboardCopy(Context context,String content) {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content);
    }

    /**
     * 从asset里面读取数据
     *
     * @param assetName
     * @param context
     * @return
     */
    public static String readTextFromAsset(String assetName, Context context) {
        try {
            InputStream is = context.getAssets().open(assetName);  //获得AssetManger 对象, 调用其open 方法取得  对应的inputStream对象
            int size = is.available();//取得数据流的数据大小
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer);
        } catch (Exception e) {
        }
        return null;
    }
}
