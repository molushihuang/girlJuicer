package com.xqd.meizhi.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import butterknife.BindView;
import com.xqd.meizhi.R;
import com.xqd.meizhi.activity.base.BaseActivity;
import com.xqd.meizhi.adapter.RotationTransformer;
import com.xqd.meizhi.adapter.ViewPagerCardAdapter;
import com.xqd.meizhi.bean.VirePagerBean;

/**
 * Created by Administrator on 2017/10/19.
 */

public class ViewpagerCardActivity extends BaseActivity {

    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    ViewPagerCardAdapter mViewPagerCardAdapter;
    RotationTransformer mRotationTransformer;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_viewpager_card;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        mViewPagerCardAdapter = new ViewPagerCardAdapter(this);
        mRotationTransformer = new RotationTransformer(mViewPager, mViewPagerCardAdapter);

        VirePagerBean virePagerBean = new VirePagerBean();
        mViewPagerCardAdapter.addCardItem(virePagerBean);
        mViewPagerCardAdapter.addCardItem(virePagerBean);
        mViewPagerCardAdapter.addCardItem(virePagerBean);
        mViewPagerCardAdapter.addCardItem(virePagerBean);
        mViewPagerCardAdapter.addCardItem(virePagerBean);
        mViewPagerCardAdapter.addCardItem(virePagerBean);
        mViewPagerCardAdapter.addCardItem(virePagerBean);

        mViewPager.setAdapter(mViewPagerCardAdapter);
        mViewPager.setOffscreenPageLimit(5);

    }
}
