package com.xqd.meizhi.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import butterknife.Bind;
import com.anthole.quickdev.commonUtils.T;
import com.anthole.quickdev.invoke.SystemBarTintInvoke;
import com.anthole.quickdev.ui.ViewPagerFixed;
import com.xqd.meizhi.R;
import com.xqd.meizhi.activity.base.BaseActivity;
import com.xqd.meizhi.adapter.PicturePagerAdapter;
import com.xqd.meizhi.utils.Invoke;
import com.xqd.meizhi.utils.PictureUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pherson on 2017-5-10.
 */

public class PictruePreviewActivity extends BaseActivity implements PicturePagerAdapter.PhotoViewClickListener {

    public static final String EXTRA_PHOTOS = "extra_photos";
    public static final String EXTRA_CURRENT_ITEM = "extra_current_item";
    public static final String TRANSIT_PIC = "picture";
    private int currentItem = 0;

    @Bind(R.id.vp_photos)
    ViewPagerFixed mViewPager;

    PicturePagerAdapter mPagerAdapter;
    String savUrl;

    public static Intent newIntent(Context context, int currentItem, List<String> pathArr) {
        Intent intent = new Intent(context, PictruePreviewActivity.class);
        intent.putStringArrayListExtra(EXTRA_PHOTOS, (ArrayList<String>) pathArr);
        intent.putExtra(EXTRA_CURRENT_ITEM, currentItem);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_picture_preciew;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        SystemBarTintInvoke.apply(this, R.color.true_black, true);

        List<String> pathArr = getIntent().getStringArrayListExtra(EXTRA_PHOTOS);
        currentItem = getIntent().getIntExtra(EXTRA_CURRENT_ITEM, 0);
        mViewPager = (ViewPagerFixed) findViewById(R.id.vp_photos);

//        ViewCompat.setTransitionName(mViewPager, TRANSIT_PIC);
        mPagerAdapter = new PicturePagerAdapter(this, pathArr);
        mPagerAdapter.setPhotoViewClickListener(this);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(currentItem);
        mViewPager.setOffscreenPageLimit(1);

        mViewPager
                .addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position,
                                               float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
    }

    @Override
    public void OnPhotoTapListener(View view, float v, float v1) {
        finish();
    }

    @Override
    public void onItemLongClik(final String url) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(new String[]{"save the picture"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case 0:
                        checkPermission(url);
                        break;
                }
            }
        })
                .create().show();

        // 自定义的弹框
//        final Dialog dialog = new Dialog(this);
//        ViewUtil.showCustomDialog(dialog, R.layout.dialog_picture_save);
//        TextView delete = (TextView) dialog.findViewById(R.id.save_ok);
//
//        delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                checkPermission(url);
//
//                dialog.dismiss();
//            }
//        });
    }

    //判断权限
    public void checkPermission(String url) {
        int checkCallPhonePermission = ContextCompat.checkSelfPermission(PictruePreviewActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (Build.VERSION.SDK_INT >= 23 && checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {

            savUrl = url;
            Invoke.phonePermission(PictruePreviewActivity.this, 123);
        } else {
            save(url);
        }
    }


    //保存图片
    public void save(String url) {
        new PictureUtils.SaveImageTask(PictruePreviewActivity.this).execute(url);

    }

    //申请权限回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 123:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    save(savUrl);
                } else {
                    T.showShort(this, "很遗憾您手机6.0系统分享权限设置失败");
                }
                break;
        }
    }
}
