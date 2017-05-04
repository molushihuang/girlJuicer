package com.anthole.quickdev;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Looper;
import android.widget.Toast;
import com.anthole.quickdev.commonUtils.BasicUtils;
import com.anthole.quickdev.commonUtils.fileUtils.PackageUtils;
import com.anthole.quickdev.commonUtils.logUtils.Logs;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: MyUncaughtExceptionHandler
 * @Description: catch the exception
 */
public class CrashHandler implements UncaughtExceptionHandler {

    public static String TAG = "Chen";
    String showMessage = "很抱歉,程序出现异常,正在重新启动";

    // Default UncaughtException
    private UncaughtExceptionHandler mDefaultHandler;
    // CrashHandler Instance
    private static CrashHandler INSTANCE = new CrashHandler();
    // Context
    private Context mContext;
    // private Object systemServiceObject;
    // to store error info
    private Map<String, String> infos = new HashMap<String, String>();

    // As part of file name
    private SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd-HH-mm-ss");
    String crashFilePath = "/crash/";

    private OnCrashListener onCrashListener;

    private CrashHandler() {
    }

    /**
     * Singleton
     */
    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    /**
     * Initialize
     *
     * @param context
     * @param crashFilePath
     */
    public void init(Context context, String crashFilePath, String showMessage,OnCrashListener onCrashListener) {
        mContext = context;

        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        this.onCrashListener = onCrashListener;

        Thread.setDefaultUncaughtExceptionHandler(this);
        // this.systemServiceObject = systemServiceObject;
        this.crashFilePath = crashFilePath;
        if (BasicUtils.judgeNotNull(showMessage)) {
            this.showMessage = showMessage;
        }
    }

    /**
     * Initialize
     *
     * @param context
     */
    public void init(Context context,OnCrashListener onCrashListener) {
        init(context, "/crash/",onCrashListener);
    }

    /**
     * Initialize
     *
     * @param context
     */
    public void init(Context context, String crashFilePath,OnCrashListener onCrashListener) {
        init(context, crashFilePath, "", onCrashListener);
    }

    /**
     *
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
            }
            // 退出程序
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }

        if (ex == null) {
            return false;
        }
        //使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, showMessage, Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();
        final QCrashBean qCrashBean = new QCrashBean();
        try {
            // 收集设备参数信息
            collectDeviceInfo(mContext);

            qCrashBean.setVersionName(PackageUtils.getAppVersionName(mContext));
            qCrashBean.setVersionCode(PackageUtils.getAppVersionCode(mContext)+"");
            qCrashBean.setExceptionInfo(getCrashInfo(ex));
            qCrashBean.setIp(getLocalIpAddress());

            // 保存日志文件
//			String filemameString = saveCrashInfo2File(ex);
        } catch (Exception e) {
        } finally {
            new Thread() {
                @Override
                public void run() {
                    Looper.prepare();
                    if(onCrashListener!=null){
                        onCrashListener.onCrash(qCrashBean);
                    }else{
                        QAppManager.getAppManager().AppExit(mContext,false);
                        PackageManager tmxx = mContext.getPackageManager();
                        Intent intent = tmxx.getLaunchIntentForPackage(mContext
                                .getPackageName());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    }
                    Looper.loop();

                }
            }.start();

        }
        return true;
    }

    /**
     * Collect Device info
     *
     * @param ctx
     */
    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null"
                        : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);

            }
        } catch (NameNotFoundException e) {
            Logs.e("an error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                Logs.d(field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Logs.e("an error occured when collect crash info", e);
            }
        }
    }

    /**
     * 获取异常信息
     * @param ex
     * @return
     */
    private String getCrashInfo(Throwable ex){
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        return result;
    }

    public String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()&&inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
        }
        return null;
    }


//	/**
//	 * Save Info to files
//	 *
//	 * @param ex
//	 * @return filename
//	 */
//	private String saveCrashInfo2File(Throwable ex) {
//
//		StringBuffer sb = new StringBuffer();
//		for (Map.Entry<String, String> entry : infos.entrySet()) {
//			String key = entry.getKey();
//			String value = entry.getValue();
//			sb.append(key + "=" + value + "\n");
//		}
//
//		Writer writer = new StringWriter();
//		PrintWriter printWriter = new PrintWriter(writer);
//		ex.printStackTrace(printWriter);
//		Throwable cause = ex.getCause();
//		while (cause != null) {
//			cause.printStackTrace(printWriter);
//			cause = cause.getCause();
//		}
//		printWriter.close();
//		String result = writer.toString();
//		sb.append(result);
//		try {
//			long timestamp = System.currentTimeMillis();
//			String time = formatter.format(new Date());
//			String fileName = "crash-" + time + "-" + timestamp + ".log";
//			if (Environment.getExternalStorageState().equals(
//					Environment.MEDIA_MOUNTED)) {
//
//				String path =
//				// StorageUtils.getCacheDirectory(mContext) +
//				FileInvoke.getInstance().getAppDir()
//						+ (BasicUtils.judgeNotNull(crashFilePath) ? crashFilePath
//								: "/crash/");
//				Logs.d("path----" + path);
//				File dir = new File(path);
//				if (!dir.exists()) {
//					dir.mkdirs();
//				}
//				FileOutputStream fos = new FileOutputStream(path + fileName);
//				fos.write(sb.toString().getBytes());
//				fos.close();
//			}
//			return fileName;
//		} catch (Exception e) {
//			Logs.e("an error occured while writing file...", e);
//		}
//		return null;
//	}


    public static class QCrashBean implements Serializable{
        String exceptionInfo;
        String brand = Build.BRAND;
        String model = Build.MODEL;
        String sdkVersion = Build.VERSION.RELEASE;
        String versionName;
        String versionCode;
        String ip;

        public String getExceptionInfo() {
            return exceptionInfo;
        }

        public void setExceptionInfo(String exceptionInfo) {
            this.exceptionInfo = exceptionInfo;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getSdkVersion() {
            return sdkVersion;
        }

        public void setSdkVersion(String sdkVersion) {
            this.sdkVersion = sdkVersion;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public String getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(String versionCode) {
            this.versionCode = versionCode;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }
    }

}
