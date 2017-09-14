package com.xqd.meizhi.activity;

import android.os.Bundle;
import android.widget.Toast;
import butterknife.Bind;
import com.xqd.meizhi.R;
import com.xqd.meizhi.activity.base.BaseActivity;
import com.xqd.meizhi.view.CalendarView;

/**
 * Created by Administrator on 2017/9/14.
 */

public class CalenderActivity extends BaseActivity {

    @Bind(R.id.calendarView)
    CalendarView mCalendarView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_calender;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {



        mCalendarView.setDateViewClick(new CalendarView.DateViewClick() {

            @Override
            public void dateClick() {
                Toast.makeText(getApplication(), "点击了：" + mCalendarView.getSelectMonth()+"月"+mCalendarView.getSelectDay()+"日", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
