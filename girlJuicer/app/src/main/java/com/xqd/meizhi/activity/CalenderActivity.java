package com.xqd.meizhi.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import butterknife.BindView;
import com.anthole.quickdev.invoke.SystemBarTintInvoke;
import com.xqd.meizhi.R;
import com.xqd.meizhi.activity.base.BaseActivity;
import com.xqd.meizhi.view.CalendarView;

/**
 * Created by Administrator on 2017/9/14.
 */

public class CalenderActivity extends BaseActivity {

    @BindView(R.id.calendarView)
    CalendarView mCalendarView;
    @BindView(R.id.main_toolbar)
    Toolbar toolbar;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_calender;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        SystemBarTintInvoke.apply(this, R.color.blue, true);

        toolbar.setTitle("日历");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true); // 决定左上角图标的右侧是否有向左的小箭头, true
        // 有小箭头，并且图标可以点击
        actionBar.setDisplayShowHomeEnabled(false);
//        actionBar.setTitle("返回");


        mCalendarView.setDateViewClick(new CalendarView.DateViewClick() {

            @Override
            public void dateClick() {
//                T.showShort(CalenderActivity.this, mCalendarView.getSelectMonth() + "月" + mCalendarView.getSelectDay() + "日");

            }
        });

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
