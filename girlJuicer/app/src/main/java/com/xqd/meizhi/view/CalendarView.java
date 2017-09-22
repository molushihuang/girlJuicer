package com.xqd.meizhi.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.xqd.meizhi.R;


public class CalendarView extends LinearLayout {
	private TextView tv_left;
	private TextView tv_right;
	private TextView tv_date;
	private TextView tv_week;
	private TextView tv_today;
	private MonthDateView monthDateView;
	private DateViewClick dateViewClick;

	public CalendarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		View view = LayoutInflater.from(context).inflate(R.layout.layout_calenderview, this);
		tv_left = (TextView) view.findViewById(R.id.tv_left);
		tv_right = (TextView) view.findViewById(R.id.tv_right);
		monthDateView = (MonthDateView) view.findViewById(R.id.monthDateView);
		tv_date = (TextView) view.findViewById(R.id.date_text);
		tv_week  =(TextView) view.findViewById(R.id.selectDate_text);
		tv_today = (TextView) view.findViewById(R.id.tv_today);
		monthDateView.setTextView(tv_date,tv_week);

		monthDateView.setDateClick(new MonthDateView.DateClick() {
			
			@Override
			public void onClickOnDate() {
				if(dateViewClick != null){
					dateViewClick.dateClick();
				}
			}
		});
		setOnlistener();
	}
	/**
	 * 设置监听
	 */
	private void setOnlistener(){
		tv_left.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				monthDateView.onLeftClick();
			}
		});

		tv_right.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				monthDateView.onRightClick();
			}
		});
		
		tv_today.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				monthDateView.setTodayToView();
			}
		});
	}
	

	/**
	 * 获取所选择的年份
	 * @return
	 */
	public int getSelectYear(){
		return monthDateView.getmSelYear();
	}
	
	/**
	 * 获取所选择的月份
	 * @return
	 */
	public int getSelectMonth(){
		return monthDateView.getmSelMonth();
	}
	/**
	 * 获取所选择的日期
	 * @return
	 */
	public int getSelectDay(){
		return monthDateView.getmSelDay();
	}
	
	/**
	 * 设置日期的click事件
	 * @param dateViewClick
	 */
	public void setDateViewClick(DateViewClick dateViewClick) {
		this.dateViewClick = dateViewClick;
	}
	public interface DateViewClick{
		public void dateClick();
	}

}
