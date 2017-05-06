package com.xqd.meizhi.activity.base;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.anthole.quickdev.QAppManager;
import com.anthole.quickdev.QFragmentActivity;
import com.anthole.quickdev.http.base.AsyncHttpClientUtil;
import com.anthole.quickdev.ui.IProgressDialog;
import com.anthole.quickdev.ui.QProgressDialog;
import com.bumptech.glide.Glide;
import com.xqd.meizhi.activity.GirlListViewActivity;

import java.util.List;

public abstract class BaseActivity extends QFragmentActivity {

	static boolean isActive = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(savedInstanceState!=null){

			QAppManager.getAppManager().finishAllActivity();
			jump2Activity(GirlListViewActivity.class);
			return;
		}

		if(!isTaskRoot()){
            Intent intent = getIntent();
            String action = intent.getAction();
            if(intent.hasCategory(Intent.CATEGORY_LAUNCHER) && action.equals(Intent.ACTION_MAIN)){
                finish();
                return;
            }
        }

	}
	
	@Override
	protected void onResume() {
		super.onResume();

	}
	
	@Override
	protected void onPause() {
		super.onPause();

	}

	@Override
	protected void onDestroy() {
		Glide.get(this).clearMemory();
		AsyncHttpClientUtil.getInstance(this).cancelRequests(this,true);
		super.onDestroy();
	}


	@Override
	protected void onStop() {
		super.onStop();
		if (!isAppOnForeground()) {
            //app 进入后台  
			isActive = false;
            //全局变量isActive = false 记录当前已经进入后台  
		}
	}


	@Override
	protected IProgressDialog createIProgressDialog() {
		return new QProgressDialog(this) ;
	}
	
	/** 
     * 程序是否在前台运行 
     *  
     * @return 
     */  
    public boolean isAppOnForeground() {  
            // Returns a list of application processes that are running on the  
            // device  
               
            ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);  
            String packageName = getApplicationContext().getPackageName();  

            List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
            if (appProcesses == null)  
                    return false;  

            for (RunningAppProcessInfo appProcess : appProcesses) {  
                    // The name of the process that this object is associated with.  
                    if (appProcess.processName.equals(packageName) && appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                            return true;  
                    }  
            }  

            return false;  
    } 
    


}
