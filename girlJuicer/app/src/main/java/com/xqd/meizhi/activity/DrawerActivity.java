package com.xqd.meizhi.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import com.anthole.quickdev.commonUtils.T;
import com.anthole.quickdev.http.RequestParams;
import com.anthole.quickdev.invoke.SystemBarTintInvoke;
import com.anthole.quickdev.ui.RequestHelper.base.IParser;
import com.xqd.meizhi.R;
import com.xqd.meizhi.activity.base.BaseActivity;
import com.xqd.meizhi.adapter.GirlShowAdapter;
import com.xqd.meizhi.bean.GirlBean;
import com.xqd.meizhi.bean.Parser;
import com.xqd.meizhi.http.AbstractRequest;
import com.xqd.meizhi.http.BaseRequest;
import com.xqd.meizhi.utils.PictureUtils;
import com.xqd.meizhi.utils.ViewUtil;
import com.xqd.meizhi.view.PtrMaterialFrameLayout;
import com.xqd.meizhi.view.requestHelper.PullListHelper;
import com.xqd.meizhi.view.xlist.XListView;

import java.util.List;

public class DrawerActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, GirlShowAdapter.ItemListener {

    @Bind(R.id.main_toolbar)
    Toolbar toolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;
    @Bind(R.id.nav_view)
    NavigationView navigationView;

    @Bind(R.id.ptr)
    PtrMaterialFrameLayout ptr;
    @Bind(R.id.xlistview)
    XListView xlistview;

    GirlShowAdapter adapter;
    PullListHelper<GirlBean> helper;
    String savUrl;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_drawer;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        SystemBarTintInvoke.apply(this, R.color.blue, true);

        setSupportActionBar(toolbar); //将toolbar设置为actionbar
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        adapter = new GirlShowAdapter(this, this);
        helper = new PullListHelper<>(this, true);
        helper.setPtr(ptr);
        helper.setXListView(xlistview, adapter);
        helper.setDataSource(new AbstractRequest(this) {

            @Override
            public String getRelativeUrl() {
                return BaseRequest.URL_woman;
            }

            @Override
            public RequestParams getParams() {
                RequestParams params = new RequestParams();

                return params;
            }
        });
        helper.setParser(new IParser<GirlBean>() {

            @Override
            public List<GirlBean> parseList(String data) {

//                String subData = JSONUtils.getString(data, "list", "");
                List<GirlBean> list = Parser.parseFriendApplyList(data);
                return list;
            }
        });
        helper.refresh(200);


    }

    @Override
    public void onItemClick(GirlBean item) {

    }

    @Override
    public void onItemLongClick(final GirlBean item) {
        final Dialog dialog = new Dialog(this);
        ViewUtil.showCustomDialog(dialog, R.layout.dialog_picture_save);
        TextView delete = (TextView) dialog.findViewById(R.id.save_ok);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int checkCallPhonePermission = ContextCompat.checkSelfPermission(DrawerActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (Build.VERSION.SDK_INT >= 23 && checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {

                    String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.CALL_PHONE,
                            Manifest.permission.READ_LOGS,
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.SET_DEBUG_APP,
                            Manifest.permission.SYSTEM_ALERT_WINDOW,
                            Manifest.permission.GET_ACCOUNTS,
                            Manifest.permission.WRITE_APN_SETTINGS};

                    savUrl = item.getUrl();
                    ActivityCompat.requestPermissions(DrawerActivity.this, mPermissionList, 123);

                } else {
                    save(item.getUrl());
                }


                dialog.dismiss();
            }
        });
    }

    //保存图片
    public void save(String url) {
        new PictureUtils.SaveImageTask(DrawerActivity.this).execute(url);

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 1, "关于");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())//得到被点击的item的itemId
        {
            case 1:
                jump2Activity(AboutActivity.class);
                break;

        }
        return super.onOptionsItemSelected(item);


    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId())//得到被点击的item的itemId
        {
            case R.id.nav_about:
                jump2Activity(GirlListViewActivity.class);
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
