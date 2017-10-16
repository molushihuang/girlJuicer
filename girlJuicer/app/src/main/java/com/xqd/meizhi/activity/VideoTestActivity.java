package com.xqd.meizhi.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import butterknife.Bind;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import com.bumptech.glide.Glide;
import com.xqd.meizhi.R;
import com.xqd.meizhi.activity.base.BaseActivity;

import java.util.LinkedHashMap;

/**
 * Created by Administrator on 2017/10/13.
 * 视频播放测试
 */

public class VideoTestActivity extends BaseActivity {

    @Bind(R.id.video_player)
    JZVideoPlayerStandard videoPlayer;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_test;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        LinkedHashMap linkedHashMap=new LinkedHashMap();
        linkedHashMap.put(0,"http://182.140.132.183/vod/sample1_1500kbps.f4v");
        linkedHashMap.put(1,"http://182.140.132.183/vod/sample1_1500kbps.f4v");
        linkedHashMap.put(2,"http://182.140.132.183/vod/sample1_1500kbps.f4v");
        videoPlayer.setUp(linkedHashMap,0, JZVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "测试视频");
        Glide.with(this).load("https://ws1.sinaimg.cn/large/610dc034ly1fk05lf9f4cj20u011h423.jpg").asBitmap().centerCrop().into(videoPlayer.thumbImageView);


    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    @OnClick(R.id.btn_full)
    public void full(){

        videoPlayer.startWindowFullscreen();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//横屏
    }

}
