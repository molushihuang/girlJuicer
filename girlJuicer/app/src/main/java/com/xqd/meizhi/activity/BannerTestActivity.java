package com.xqd.meizhi.activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.Bind;
import com.anthole.quickdev.ui.autolayout.utils.AutoUtils;
import com.xqd.meizhi.R;
import com.xqd.meizhi.activity.base.BaseActivity;
import com.xqd.meizhi.adapter.BannerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * banner轮播测试代码
 * Created by Administrator on 2017/6/30.
 */

public class BannerTestActivity extends BaseActivity {

    @Bind(R.id.view_pager)
    ViewPager mViewPager;
    @Bind(R.id.fl_point)
    LinearLayout pointers;
    @Bind(R.id.main_toolbar)
    Toolbar toolbar;

    BannerAdapter mBannerAdapter;
    List<String> mList = new ArrayList<>();
    List<ImageView> pointList = new ArrayList<>();
    int currentIndex = 0;

    int pointSize = AutoUtils.getPercentWidthSize(20);
    int pointDisablePadding = AutoUtils.getPercentWidthSize(2);
    LinearLayout.LayoutParams layoutParamsPoint = new LinearLayout.LayoutParams(pointSize, pointSize);
    boolean isStop = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_banner_test;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        toolbar.setTitle("banner");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true); // 决定左上角图标的右侧是否有向左的小箭头, true
        // 有小箭头，并且图标可以点击
        actionBar.setDisplayShowHomeEnabled(false);

        for (int i = 0; i < 5; i++) {
            mList.add("https://ws1.sinaimg.cn/large/610dc034ly1fgchgnfn7dj20u00uvgnj.jpg");

        }
        layoutParamsPoint.leftMargin = AutoUtils.getPercentWidthSize(7);
        layoutParamsPoint.rightMargin = AutoUtils.getPercentWidthSize(7);

        mBannerAdapter = new BannerAdapter(this, mList);
        mViewPager.setAdapter(mBannerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentIndex = position % mList.size();//余数就是正确的位置
                resetPoints();
                setInitCurrentPoint();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        initPoint();

        // 开启新线程，4秒一次更新Banner
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (!isStop) {
                    SystemClock.sleep(4000);//安卓提供的线程休眠的方法，可以避免异常
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);

                        }
                    });
                }
            }
        }).start();

    }

    //装载指示点
    private void initPoint() {
        pointers.removeAllViews();
        pointList.clear();
        if (mList.size() == 0) {
            return;
        }
        for (int i = 0; i < mList.size(); i++) {
            ImageView iv = new ImageView(this);
            iv.setEnabled(false);
            iv.setLayoutParams(layoutParamsPoint);
            iv.setImageResource(R.drawable.point_recommend);
            iv.setPadding(pointDisablePadding, pointDisablePadding, pointDisablePadding, pointDisablePadding);

            pointers.addView(iv);
            pointList.add(iv);
        }

        setInitCurrentPoint();
    }

    //轮播时重置每个点的状态
    private void resetPoints() {
        for (int i = 0; i < pointList.size(); i++) {
            ImageView iv = pointList.get(i);
            iv.setEnabled(false);
            iv.setPadding(pointDisablePadding, pointDisablePadding, pointDisablePadding, pointDisablePadding);
        }
    }

    /**
     * 设置当前点的状态
     */
    private void setInitCurrentPoint() {
        ImageView ivCurrent = pointList.get(Math.min(currentIndex, pointList.size() - 1));
        ivCurrent.setEnabled(true);
        ivCurrent.setPadding(0, 0, 0, 0);//这里会使选中的点比没选中的显得大一点


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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isStop = true; //销毁的话不再循环
    }
}
