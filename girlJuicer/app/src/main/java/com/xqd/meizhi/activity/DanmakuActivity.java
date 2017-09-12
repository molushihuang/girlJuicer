package com.xqd.meizhi.activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import butterknife.Bind;
import butterknife.OnClick;
import com.anthole.quickdev.invoke.SystemBarTintInvoke;
import com.xqd.meizhi.R;
import com.xqd.meizhi.activity.base.BaseActivity;
import com.xqd.meizhi.adapter.DanmuControl;
import com.xqd.meizhi.bean.Danmu;
import master.flame.danmaku.ui.widget.DanmakuView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2017/9/12.
 * 弹幕测试
 */

public class DanmakuActivity extends BaseActivity {

    @Bind(R.id.main_toolbar)
    Toolbar toolbar;
    @Bind(R.id.danmakuView)
    DanmakuView mDanmakuView;

    private DanmuControl mDanmuControl;
    Thread thread;
    private boolean isStop;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_danmaku;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        SystemBarTintInvoke.apply(this, R.color.blue, true);

        toolbar.setTitle("弹幕测试");

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true); // 决定左上角图标的右侧是否有向左的小箭头, true
        // 有小箭头，并且图标可以点击
        actionBar.setDisplayShowHomeEnabled(false);
//        actionBar.setTitle("返回");

        mDanmuControl = new DanmuControl(this);
        mDanmuControl.setDanmakuView(mDanmakuView);

    }

    //增加一条弹幕
    @OnClick(R.id.btn_add)
    public void addDanmu() {
        if (thread == null) {
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!isStop) {
                        SystemClock.sleep(1000);
                        setData();
                    }
                }
            });
            thread.start();
        }

    }

    private void setData() {
        final List<Danmu> danmus = new ArrayList<>();
        Danmu danmu1 = new Danmu(0, 1, "Like", "https://ws1.sinaimg.cn/large/610dc034ly1fis7dvesn6j20u00u0jt4.jpg", "");
        Danmu danmu2 = new Danmu(0, 2, "Comment", "https://ws1.sinaimg.cn/large/610dc034ly1fitcjyruajj20u011h412.jpg", "这是一条弹幕啦啦啦");
        Danmu danmu3 = new Danmu(0, 3, "Like", "https://ws1.sinaimg.cn/large/610dc034ly1fiz4ar9pq8j20u010xtbk.jpg", "");
        Danmu danmu4 = new Danmu(0, 1, "Comment", "https://ws1.sinaimg.cn/large/610dc034ly1fj2ld81qvoj20u00xm0y0.jpg", "这又是一条弹幕啦啦啦");
        Danmu danmu5 = new Danmu(0, 2, "Like", "https://ws1.sinaimg.cn/large/610dc034ly1fj3w0emfcbj20u011iabm.jpg", "");
        Danmu danmu6 = new Danmu(0, 3, "Comment", "https://ws1.sinaimg.cn/large/610dc034ly1fj78mpyvubj20u011idjg.jpg", "这还是一条弹幕啦啦啦");
        danmus.add(danmu1);
        danmus.add(danmu2);
        danmus.add(danmu3);
        danmus.add(danmu4);
        danmus.add(danmu5);
        danmus.add(danmu6);
        Collections.shuffle(danmus);
        mDanmuControl.addDanmuList(danmus);
    }

    @OnClick(R.id.btn_hide)
    public void hideDanmu() {
        mDanmuControl.hide();
    }

    @OnClick(R.id.btn_show)
    public void showDanmu() {
        mDanmuControl.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDanmuControl.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDanmuControl.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDanmuControl.destroy();
        isStop=true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:// 点击返回图标事件
                this.finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
