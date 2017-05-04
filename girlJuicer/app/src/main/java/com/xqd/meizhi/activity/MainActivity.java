package com.xqd.meizhi.activity;

import android.os.Bundle;
import butterknife.Bind;
import com.anthole.quickdev.http.RequestParams;
import com.anthole.quickdev.invoke.SystemBarTintInvoke;
import com.anthole.quickdev.ui.RequestHelper.base.IParser;
import com.xqd.meizhi.R;
import com.xqd.meizhi.activity.base.BaseActivity;
import com.xqd.meizhi.adapter.GirlShowAdapter;
import com.xqd.meizhi.bean.GirlBean;
import com.xqd.meizhi.bean.Parser;
import com.xqd.meizhi.http.AbstractRequest;
import com.xqd.meizhi.view.PtrMaterialFrameLayout;
import com.xqd.meizhi.view.requestHelper.PullListHelper;
import com.xqd.meizhi.view.xlist.XListView;

import java.util.List;

public class MainActivity extends BaseActivity implements GirlShowAdapter.ItemListener {

    @Bind(R.id.ptr)
    PtrMaterialFrameLayout ptr;
    @Bind(R.id.xlistview)
    XListView xlistview;

    GirlShowAdapter adapter;
    PullListHelper<GirlBean> helper;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        SystemBarTintInvoke.apply(this, R.color.titlebar_bg_black, true);

        adapter = new GirlShowAdapter(this, this);
        helper = new PullListHelper<>(this, true);
        helper.setPtr(ptr);
        helper.setXListView(xlistview, adapter);
        helper.setDataSource(new AbstractRequest(this) {

            @Override
            public String getRelativeUrl() {
                return "/data/福利";
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
}
