package com.xqd.meizhi.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.ButterKnife;
import com.anthole.quickdev.ui.autolayout.AutoFrameLayout;
import com.anthole.quickdev.ui.ptr.PtrFrameLayout;
import com.anthole.quickdev.ui.ptr.PtrUIHandler;
import com.anthole.quickdev.ui.ptr.indicator.PtrIndicator;
import com.xqd.meizhi.R;


/**
 * Created by UJU103 on 2016/7/13.
 */
public class CustomHeader extends AutoFrameLayout implements PtrUIHandler {


    ProgressBar progressBar;
    TextView tvTip;
    ImageView ivTip;
    ImageView ivLoading;

    public CustomHeader(Context context) {
        super(context);
        initViews(null);
    }

    public CustomHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(attrs);
    }

    public CustomHeader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews(attrs);
    }

    protected void initViews(AttributeSet attrs) {
        View header = LayoutInflater.from(getContext()).inflate(R.layout.include_ptr_custom_header, this);
        progressBar = ButterKnife.findById(header, R.id.progress_bar);
        tvTip = ButterKnife.findById(header, R.id.tv_tip);
        ivTip = ButterKnife.findById(header, R.id.iv_tip);
        ivLoading = ButterKnife.findById(header, R.id.iv_loading);

    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {
        tvTip.setVisibility(GONE);
        ivTip.setVisibility(GONE);
        ivLoading.setVisibility(VISIBLE);
        ivLoading.setRotation(0);
        progressBar.setVisibility(GONE);
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        tvTip.setVisibility(GONE);
        ivTip.setVisibility(GONE);
        ivLoading.setVisibility(VISIBLE);
        ivLoading.setRotation(0);
        progressBar.setVisibility(GONE);
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        ivLoading.setVisibility(GONE);
        progressBar.setVisibility(VISIBLE);
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
//        tvTip.setVisibility(GONE);
//        ivTip.setVisibility(GONE);
//        ivLoading.setVisibility(VISIBLE);
//        ivLoading.setRotation(0);
//        progressBar.setVisibility(GONE);
    }

    public void onRefreshSuccess(final PtrFrameLayout frame) {
        tvTip.setVisibility(VISIBLE);
        ivTip.setVisibility(VISIBLE);
        tvTip.setText("刷新成功");
        ivTip.setImageResource(R.drawable.ic_ptr_load_success);
        progressBar.setVisibility(GONE);
//        postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                onUIRefreshComplete(frame);
//            }
//        },TipShowTime);
    }

    public void onRefreshFailed(final PtrFrameLayout frame) {
        tvTip.setVisibility(VISIBLE);
        ivTip.setVisibility(VISIBLE);
        tvTip.setText("失败了");
        ivTip.setImageResource(R.drawable.ic_ptr_load_fail);
        progressBar.setVisibility(GONE);
//        postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                onUIRefreshComplete(frame);
//            }
//        },TipShowTime);
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        final int mOffsetToRefresh = frame.getOffsetToRefresh();
        final int currentPos = ptrIndicator.getCurrentPosY();
        final int lastPos = ptrIndicator.getLastPosY();

        if (currentPos <= mOffsetToRefresh) {
            ivLoading.setRotation(currentPos * 180.0f / mOffsetToRefresh);
        }

//        if (currentPos < mOffsetToRefresh && lastPos >= mOffsetToRefresh) {
//            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
//                crossRotateLineFromBottomUnderTouch(frame);
//                if (mRotateView != null) {
//                    mRotateView.clearAnimation();
//                    mRotateView.startAnimation(mReverseFlipAnimation);
//                }
//            }
//        } else if (currentPos > mOffsetToRefresh && lastPos <= mOffsetToRefresh) {
//            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
//                crossRotateLineFromTopUnderTouch(frame);
//                if (mRotateView != null) {
//                    mRotateView.clearAnimation();
//                    mRotateView.startAnimation(mFlipAnimation);
//                }
//            }
//        }

    }
}
