package com.xqd.meizhi.activity.base;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import com.anthole.quickdev.QApplication;

/**
 * Created by UJU105 on 2016/6/2.
 */
public class BaseApplication extends QApplication {

    public static Context applicationContext;
    private static BaseApplication instance;

    public static BaseApplication getInstance() {
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();

        Log.e("手机信息++MANUFACTURER", Build.MANUFACTURER);
        Log.e("手机信息++BOARD", Build.BOARD);
        Log.e("手机信息++BRAND", Build.BRAND);
        Log.e("手机信息++PRODUCT", Build.PRODUCT);

        applicationContext = this;
        instance = this;




    }


    @Override
    public String getAppDir() {
        return "appName";
    }


}
