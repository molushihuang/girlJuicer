package com.xqd.meizhi.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import butterknife.Bind;
import com.anthole.quickdev.http.RequestParams;
import com.anthole.quickdev.invoke.SystemBarTintInvoke;
import com.anthole.quickdev.ui.RequestHelper.base.IParser;
import com.xqd.meizhi.R;
import com.xqd.meizhi.activity.base.BaseActivity;
import com.xqd.meizhi.adapter.AndroidShowAdapter;
import com.xqd.meizhi.bean.GirlBean;
import com.xqd.meizhi.bean.Parser;
import com.xqd.meizhi.http.AbstractRequest;
import com.xqd.meizhi.http.BaseRequest;
import com.xqd.meizhi.utils.Invoke;
import com.xqd.meizhi.view.PtrMaterialFrameLayout;
import com.xqd.meizhi.view.requestHelper.PullListHelper;
import com.xqd.meizhi.view.xlist.XListView;

import java.util.List;

import static com.xqd.meizhi.Constants.IntentKeys.TYPLE;

public class AndroidActivity extends BaseActivity implements AndroidShowAdapter.ItemListener {

    @Bind(R.id.ptr)
    PtrMaterialFrameLayout ptr;
    @Bind(R.id.xlistview)
    XListView xlistview;
    @Bind(R.id.main_toolbar)
    Toolbar toolbar;

    AndroidShowAdapter adapter;
    PullListHelper<GirlBean> helper;

    String title;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_android;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        SystemBarTintInvoke.apply(this, R.color.blue, true);

        title = getIntent().getStringExtra(TYPLE);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true); // 决定左上角图标的右侧是否有向左的小箭头, true
        // 有小箭头，并且图标可以点击
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setTitle(title);

        adapter = new AndroidShowAdapter(this, this);
        helper = new PullListHelper<>(this, true);
        helper.setPtr(ptr);
        helper.setXListView(xlistview, adapter);
        helper.setDataSource(new AbstractRequest(this) {

            @Override
            public String getRelativeUrl() {
                if(title.equals("android")){
                    return BaseRequest.URL_android;
                }else if(title.equals("ios")){
                    return BaseRequest.URL_ios;
                }else if(title.equals("web")){
                    return BaseRequest.URL_web;
                }else if(title.equals("recommend")){
                    return BaseRequest.URL_recommend;
                }else if(title.equals("expend")){
                    return BaseRequest.URL_expend;
                }else if(title.equals("video")){
                    return BaseRequest.URL_video;
                }
                return BaseRequest.URL_all;

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
        Invoke.openLink(this, item.getUrl(), item.getDesc());
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
