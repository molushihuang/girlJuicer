package com.xqd.meizhi.utils;

import android.view.View;

/**
 * 防止点击太快
 * Created by Administrator on 2016/9/26 0026.
 */
public class DoubleClickUtil {

    static int lastClickId;

    static long lastClickTime;

    public static synchronized boolean isFastClick(View view) {
        long currentTime = System.currentTimeMillis();
        if (view.getId() == lastClickId && currentTime - lastClickTime < 1000) {
            lastClickTime = currentTime;
            return true;
        }
        lastClickTime = currentTime;
        lastClickId = view.getId();
        return false;
    }


}
