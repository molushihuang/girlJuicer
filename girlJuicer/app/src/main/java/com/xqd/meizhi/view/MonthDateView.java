package com.xqd.meizhi.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.TextView;
import com.xqd.meizhi.R;
import com.xqd.meizhi.utils.DateUtils;

import java.util.Calendar;


public class MonthDateView extends View {
    private final int NUM_COLUMNS = 7;
    private int NUM_ROWS = 6;//行数
    private Paint mPaint;
    private int mSelectDayColor = Color.parseColor("#FFFFFF");
    private int mSelectBGColor;
    private int mCurrentColor;
    private int mCircleColor;
    private int mEnableDateColor;
    private int mUnableDateColor;
    private int mRelaxColor;
    private int mWorkColor;
    private int mPriceColor;
    private int mDateHeight;
    private int mCurrYear, mCurrMonth, mCurrDay;//当前时间
    private int mSelYear, mSelMonth, mSelDay;//选择的时间
    private float mColumnSize, mRowSize;
    private int mDaySize, mPriceSize;
    private TextView tv_date, tv_week;
    private int weekRow;
    private int[][] daysString;
    private int mCircleRadius = 6;
    private DateClick dateClick;
    private int mMarginSize = 1;
    private int minYear, minMonth, minDay, maxYear, maxMonth;

    private int mTouchSlop;

    public MonthDateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CalendarView);
        mEnableDateColor = typedArray.getColor(R.styleable.CalendarView_EnableDateColor, Color.parseColor("#000000"));
        mUnableDateColor = typedArray.getColor(R.styleable.CalendarView_UnableDateColor, Color.parseColor("#CBCBCB"));
        mCircleColor = typedArray.getColor(R.styleable.CalendarView_CircleColor, Color.parseColor("#68CB00"));
        mRelaxColor = typedArray.getColor(R.styleable.CalendarView_RelaxColor, Color.parseColor("#65CD00"));
        mWorkColor = typedArray.getColor(R.styleable.CalendarView_WorkColor, Color.parseColor("#FF9B12"));
        mPriceColor = typedArray.getColor(R.styleable.CalendarView_PriceColor, Color.parseColor("#FF9B12"));
        mSelectBGColor = typedArray.getColor(R.styleable.CalendarView_SelectBGColor, Color.parseColor("#13A4D3"));
        mCurrentColor = typedArray.getColor(R.styleable.CalendarView_CurrentColor, Color.parseColor("#FF0000"));
        mDateHeight = (int) typedArray.getDimension(R.styleable.CalendarView_DateHeight, 66);
        mDaySize = (int) typedArray.getDimension(R.styleable.CalendarView_DateSize, 15);
        mPriceSize = (int) typedArray.getDimension(R.styleable.CalendarView_PriceSize, 12);
        typedArray.recycle();

        Calendar calendar = Calendar.getInstance();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCurrYear = calendar.get(Calendar.YEAR);
        mCurrMonth = calendar.get(Calendar.MONTH);
        mCurrDay = calendar.get(Calendar.DATE);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mRowSize = mDateHeight;
        setSelectYearMonthDate(mCurrYear, mCurrMonth);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST) {
            widthSize = (int) (300 * getResources().getDisplayMetrics().density);
        }
        NUM_ROWS = getMonthRowNumber();
        heightSize = NUM_ROWS * mDateHeight;
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        initSize();
        daysString = new int[6][7];
        String dayString;
        int mMonthDays = DateUtils.getMonthDays(mSelYear, mSelMonth);//获得被选择到的月份的天数
        int weekNumber = DateUtils.getFirstDayWeek(mSelYear, mSelMonth);//一号是周几
        int column, row = 0;
        //绘制线条
//        drawLines(canvas);
        for (int day = 0; day < mMonthDays; day++) {
            dayString = (day + 1) + "";
            column = (day + weekNumber - 1) % 7;
            row = (day + weekNumber - 1) / 7;
            daysString[row][column] = day + 1;

            mPaint.setTextSize(mDaySize);
            float startX = mColumnSize * column + (mColumnSize - mPaint.measureText(dayString)) / 2;
            float startY = mRowSize * row + mRowSize / 2 - (mPaint.ascent() + mPaint.descent()) / 2;

            if (dayString.equals(mSelDay + "")) {
                //绘制背景色矩形
                float startRecX = mColumnSize * column + mMarginSize;
                float startRecY = mRowSize * row + mMarginSize;
                float endRecX = startRecX + mColumnSize - 2 * mMarginSize;
                float endRecY = startRecY + mRowSize - 2 * mMarginSize;
                mPaint.setColor(mSelectBGColor);
                mPaint.setStyle(Style.FILL);
                canvas.drawRect(startRecX, startRecY, endRecX, endRecY, mPaint);
                //记录第几行，即第几周
                weekRow = column;
            }
            //绘制事务圆形标志
//            drawCircle(row, column, day + 1, canvas);

            mPaint.setColor(mEnableDateColor);
            mPaint.setStyle(Style.STROKE);
            canvas.drawText(dayString, startX, startY, mPaint);
        }
        if (tv_date != null) {
            tv_date.setText(mSelYear + "年" + (mSelMonth + 1) + "月");
        }

        if (tv_week != null) {
            tv_week.setText((mSelMonth + 1) + "月" + mSelDay + "日" + "   " + DateUtils.getWeekName(weekRow));
        }
    }

    /**
     * 初始化列宽行高
     */
    private void initSize() {
        mColumnSize = getWidth() * 1.0F / NUM_COLUMNS;
    }

    /**
     * 绘制线条
     *
     * @param canvas
     */
    private void drawLines(Canvas canvas) {
        int rightX = getWidth();
        int BottomY = getHeight();
        int rowCount = getMonthRowNumber();
        int columnCount = 7;
        Path path;
        float startX = 0;
        float endX = rightX;
        mPaint.setStyle(Style.STROKE);
        mPaint.setColor(mUnableDateColor);
        for (int row = 1; row <= rowCount; row++) {
            float startY = row * mRowSize;
            path = new Path();
            path.moveTo(startX, startY);
            path.lineTo(endX, startY);
            canvas.drawPath(path, mPaint);
        }

        float startY = 0;
        float endY = BottomY;
        for (int column = 1; column < columnCount; column++) {
            startX = column * mColumnSize;
            path = new Path();
            path.moveTo(startX, startY);
            path.lineTo(startX, endY);
            canvas.drawPath(path, mPaint);
        }

    }

    /**
     * 绘制事务圆形
     *
     * @param row
     * @param column
     * @param day
     * @param canvas
     */
    private void drawCircle(int row, int column, int day, Canvas canvas) {
        mPaint.setColor(mCircleColor);
        mPaint.setStyle(Style.FILL);
        float circleX = (float) (mColumnSize * column + mColumnSize * 0.8);
        float circley = (float) (mRowSize * row + mRowSize * 0.2);
        canvas.drawCircle(circleX, circley, mCircleRadius, mPaint);
    }


    @Override
    public boolean performClick() {
        return super.performClick();
    }

    private int downX = 0, downY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int eventCode = event.getAction();
        switch (eventCode) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                downY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                int upX = (int) event.getX();
                int upY = (int) event.getY();
                if (upX - downX > 0 && Math.abs(upX - downX) > mTouchSlop) {//左滑
                    onLeftClick();
                } else if (upX - downX < 0 && Math.abs(upX - downX) > mTouchSlop) {//右滑
                    onRightClick();
                }
                if (Math.abs(upX - downX) < 10 && Math.abs(upY - downY) < 10) {//点击事件
                    performClick();
                    doClickAction((upX + downX) / 2, (upY + downY) / 2);
                }
                break;
        }
        return true;
    }


    /**
     * 获取总共行数
     *
     * @return
     */
    private int getMonthRowNumber() {
        int mMonthDays = DateUtils.getMonthDays(mSelYear, mSelMonth);
        int weekNumber = DateUtils.getFirstDayWeek(mSelYear, mSelMonth);
        return (mMonthDays + weekNumber - 1) % 7 == 0 ? (mMonthDays + weekNumber - 1) / 7 : (mMonthDays + weekNumber - 1) / 7 + 1;
    }

    /**
     * 设置年月
     *
     * @param year
     * @param month
     */
    private void setSelectYearMonth(int year, int month, int day) {
        if (year <= 0 || month <= 0 || day <= 0) return;
        mSelYear = year;
        mSelMonth = month;
        mSelDay = day;
    }

    /**
     * 设置选中的月份
     *
     * @param year
     * @param month
     */
    private void setSelectYearMonthDate(int year, int month) {
        mSelYear = year;
        mSelMonth = month;
    }

    /**
     * 执行点击事件
     *
     * @param x
     * @param y
     */
    private void doClickAction(int x, int y) {
        int row = (int) (y / mRowSize);
        int column = (int) (x / mColumnSize);
        setSelectYearMonth(mSelYear, mSelMonth, daysString[row][column]);
        invalidate();
        //执行activity发送过来的点击处理事件
        if (dateClick != null) {
            dateClick.onClickOnDate();
        }
    }

    /**
     * 左点击，日历向后翻页
     */
    public void onLeftClick() {
        if (minYear == mSelYear && minMonth == mSelMonth + 1) return;
        int year = mSelYear;
        int month = mSelMonth;
        int day = mSelDay;
        if (month == 0) {//若果是1月份，则变成12月份
            year = mSelYear - 1;
            month = 11;
        } else if (DateUtils.getMonthDays(year, month) == day) {
            //如果当前日期为该月最后一点，当向前推的时候，就需要改变选中的日期
            month = month - 1;
            day = DateUtils.getMonthDays(year, month);
        } else {
            month = month - 1;
        }
        setSelectYearMonth(year, month, 1);
        forceLayout();
        measure(0, 0);
        requestLayout();
        invalidate();
    }

    /**
     * 右点击，日历向前翻页
     */
    public void onRightClick() {
        if (maxYear == mSelYear && maxMonth == mSelMonth + 1) return;
        int year = mSelYear;
        int month = mSelMonth;
        int day = mSelDay;
        if (month == 11) {//若果是12月份，则变成1月份
            year = mSelYear + 1;
            month = 0;
        } else if (DateUtils.getMonthDays(year, month) == day) {
            //如果当前日期为该月最后一点，当向前推的时候，就需要改变选中的日期
            month = month + 1;
            day = DateUtils.getMonthDays(year, month);
        } else {
            month = month + 1;
        }
        setSelectYearMonth(year, month, 1);
        forceLayout();
        measure(0, 0);
        requestLayout();
        invalidate();
    }


    /**
     * 获取选择的年份
     *
     * @return
     */
    public int getmSelYear() {
        return mSelYear;
    }

    /**
     * 获取选择的月份
     *
     * @return
     */
    public int getmSelMonth() {
        return mSelMonth;
    }

    /**
     * 获取选择的日期
     *
     * @param mSelDay
     */
    public int getmSelDay() {
        return this.mSelDay;
    }

    /**
     * 选择日期的颜色，默认为白色
     *
     * @param mSelectDayColor
     */
    public void setmSelectDayColor(int mSelectDayColor) {
        this.mSelectDayColor = mSelectDayColor;
    }

    /**
     * 选中日期的背景颜色，默认蓝色
     *
     * @param mSelectBGColor
     */
    public void setmSelectBGColor(int mSelectBGColor) {
        this.mSelectBGColor = mSelectBGColor;
    }

    /**
     * 当前日期不是选中的颜色，默认红色
     *
     * @param mCurrentColor
     */
    public void setmCurrentColor(int mCurrentColor) {
        this.mCurrentColor = mCurrentColor;
    }

    /**
     * 日期的大小，默认18sp
     *
     * @param mDaySize
     */
    public void setmDaySize(int mDaySize) {
        this.mDaySize = mDaySize;
    }

    /**
     * 设置显示当前日期的控件
     *
     * @param tv_date 显示日期
     * @param tv_week 显示周
     */
    public void setTextView(TextView tv_date, TextView tv_week) {
        this.tv_date = tv_date;
        this.tv_week = tv_week;
        invalidate();
    }


    /***
     * 设置圆圈的半径，默认为6
     * @param mCircleRadius
     */
    public void setmCircleRadius(int mCircleRadius) {
        this.mCircleRadius = mCircleRadius;
    }

    /**
     * 设置圆圈的半径
     *
     * @param mCircleColor
     */
    public void setmCircleColor(int mCircleColor) {
        this.mCircleColor = mCircleColor;
    }

    /**
     * 设置日期的点击回调事件
     *
     * @author shiwei.deng
     */
    public interface DateClick {
        public void onClickOnDate();
    }

    /**
     * 设置日期点击事件
     *
     * @param dateClick
     */
    public void setDateClick(DateClick dateClick) {
        this.dateClick = dateClick;
    }

    /**
     * 跳转至今天
     */
    public void setTodayToView() {
        setSelectYearMonth(mCurrYear, mCurrMonth, mCurrDay);
        invalidate();
    }
}
