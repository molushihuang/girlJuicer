package com.xqd.meizhi.view;

import android.content.Context;
import android.util.AttributeSet;
import com.anthole.quickdev.ui.ptr.PtrFrameLayout;
import com.anthole.quickdev.ui.ptr.util.PtrCLog;


public class PtrMaterialFrameLayout extends PtrFrameLayout {

    private final static int TipShowTime = 500;

    CustomHeader header;

    public PtrMaterialFrameLayout(Context context) {
        super(context);
        initViews();
    }

    public PtrMaterialFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public PtrMaterialFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews();
    }

    private void initViews() {
        header = new CustomHeader(getContext());
        setHeaderView(header);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
//        header.setPadding(0, DensityUtils.dp2px(getContext(), 15f), 0, DensityUtils.dp2px(getContext(), 10f));
        addPtrUIHandler(header);
        disableWhenHorizontalMove(true);
        setPinContent(false);
    }

    @Override
    public void autoRefresh() {
        super.autoRefresh();
    }

    public CustomHeader getHeader() {
        return header;
    }

    public void refreshSuccess() {

        if (DEBUG) {
            PtrCLog.i(LOG_TAG, "refreshComplete");
        }

        header.onRefreshSuccess(this);

        postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshComplete();
            }
        }, TipShowTime);
    }

    public void refreshFailed() {
        header.onRefreshFailed(this);

        postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshComplete();
            }
        }, TipShowTime);
    }

}
