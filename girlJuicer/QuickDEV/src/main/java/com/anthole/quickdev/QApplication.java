package com.anthole.quickdev;

import android.support.multidex.MultiDexApplication;
import com.anthole.quickdev.invoke.FileInvoke;

public abstract class QApplication extends MultiDexApplication {
	
	private static QApplication application;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		application = this;
		FileInvoke.getInstance().init(this, getAppDir());

	}
	
	/**
	 * 获取Application
	 * 
	 * @return
	 */
	public static QApplication getApplication()
	{
		return application;
	}
	
	/**
	 * 配置  app dir 如 qiugou
	 * @return
	 */
	public abstract String getAppDir();

}
