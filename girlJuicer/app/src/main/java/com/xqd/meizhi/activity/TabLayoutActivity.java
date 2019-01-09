package com.xqd.meizhi.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import butterknife.BindView;
import com.xqd.meizhi.R;
import com.xqd.meizhi.activity.base.BaseActivity;

/**
 * Created by Administrator on 2017/10/18.
 */

public class TabLayoutActivity extends BaseActivity {


    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private String[] TITLES = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private Fragment[] mFragments = {new Fragment(), new Fragment(), new Fragment(), new Fragment(), new Fragment(), new Fragment(), new Fragment(), new Fragment(), new Fragment()};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tablayout;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {


        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }

            @Override
            public int getCount() {
                return mFragments.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {

                return TITLES[position];

            }
        });


        mTabLayout.setupWithViewPager(mViewPager);

    }
}
