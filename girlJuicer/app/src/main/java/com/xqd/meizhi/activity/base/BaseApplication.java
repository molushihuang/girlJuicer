package com.xqd.meizhi.activity.base;

import android.content.Context;
import android.os.Build;
import com.anthole.quickdev.QApplication;
import com.example.aaron.library.MLog;

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

        applicationContext = this;
        instance = this;

        MLog.init(true);
        MLog.d("手机信息++MANUFACTURER", Build.MANUFACTURER);
        MLog.d("手机信息++BOARD", Build.BOARD);
        MLog.d("手机信息++BRAND", Build.BRAND);
        MLog.d("手机信息++PRODUCT", Build.PRODUCT);


    }


    @Override
    public String getAppDir() {
        return "girlJucicer";
    }


}
