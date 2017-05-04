package com.xqd.meizhi.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import com.anthole.quickdev.commonUtils.T;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.xqd.meizhi.Constants;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.UUID;

/**
 * Created by pherson on 2017-5-4.
 */

public class PictureUtils {

    public static String getFileName(String url)
            throws UnsupportedEncodingException {
        String filename = url.substring(url.lastIndexOf('/') + 1);//根据url的名字截取文件名
        if (filename == null || "".equals(filename.trim()))
            filename = UUID.randomUUID() + ".tmp";//  随机获取文件名
        filename = URLDecoder.decode(filename, "utf-8");
        return filename;
    }

    public static String generateFileName(String url) {
        String filename = url.substring(url.lastIndexOf('/') + 1);
        if(filename == null){
            filename = new MyMd5FileNameGenerator().generate(url);
        }
        return filename;
    }

    /**
     * 通过url保存图片
     * Created by csonezp on 16-1-12.
     */
    public static class SaveImageTask extends AsyncTask<String, Void, File> {
        private final Context context;
        private String url;

        public SaveImageTask(Context context) {
            this.context = context;
        }

        @Override
        protected File doInBackground(String... params) {
            this.url = params[0]; // should be easy to extend to share multiple images at once
            try {
                return Glide
                        .with(context.getApplicationContext())
                        .load(url)
                        .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get() // needs to be called on background thread
                        ;
            } catch (Exception ex) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(File result) {
            if (result == null) {
                return;
            }
            File targetFile = null;
            targetFile = new File(Constants.File.getSavePath(), PictureUtils.generateFileName(url));

            if (targetFile.exists()) {

                T.showLong(context,"文件已经存在");
                return;
            }
            String path = result.getPath();
            com.anthole.quickdev.commonUtils.fileUtils.FileUtils.copyFile(path, targetFile.getPath());//把下载好的图片复制到目标目录

            T.showLong(context,"保存成功,图片路径" + targetFile.getPath());
            //系统广播，通知图库更新
            PictureUtils.scanFileAsync(context, targetFile.getPath());
            Log.e("保存路径",targetFile.getPath());

        }
    }

    //通知广播更新系统文件
    public static void scanFileAsync(Context ctx, String filePath) {
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(Uri.fromFile(new File(filePath)));
        ctx.sendBroadcast(scanIntent);
    }
}
