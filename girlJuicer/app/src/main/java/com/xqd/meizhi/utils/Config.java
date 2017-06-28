package com.xqd.meizhi.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import girlJuicer.dao.DaoMaster;
import girlJuicer.dao.DaoSession;

/**
 * Created by Administrator on 2017/6/28.
 */

public class Config {

    private final static String DB_NAME = "girl_db";
    private DaoMaster mDaoMaster;
    private DaoMaster.DevOpenHelper mDevOpenHelper;
    private DaoSession mDaoSession;
    private SQLiteDatabase mSQLiteDatabase;

    private static Config instance;

    private Context mContext;

    public static Config getInstance() {
        if (instance == null) {
            synchronized (Config.class) {
                if (instance == null) {
                    instance = new Config();

                }
            }
        }
        return instance;
    }

    private Config() {

    }

    public void init(Context context) {
        this.mContext = context;
        this.mDevOpenHelper = new DaoMaster.DevOpenHelper(mContext, DB_NAME, null);
        this.mSQLiteDatabase = mDevOpenHelper.getWritableDatabase(); //获取可写数据库
        this.mDaoMaster = new DaoMaster(mSQLiteDatabase);
        this.mDaoSession = mDaoMaster.newSession();


    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getSQLiteDatabase() {
        return mSQLiteDatabase;
    }

    /**
     * 关闭数据连接
     */
    public void close() {
        if (mDevOpenHelper != null) {
            mDevOpenHelper.close();
        }
    }
}
