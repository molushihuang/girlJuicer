package com.xqd.meizhi.adapter;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.View;

import static com.xqd.meizhi.adapter.BannerAdapter.MAX_ELEVATION_FACTOR;


public class ShadowTransformer implements ViewPager.OnPageChangeListener, ViewPager.PageTransformer {

    private ViewPager mViewPager;
    private BannerAdapter mAdapter;
    private int savePosition = 0;

    private float mLastOffset;
    private boolean mScalingEnabled = true;

    public ShadowTransformer(ViewPager viewPager, BannerAdapter adapter) {
        mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(this);
        mAdapter = adapter;
    }

    @Override
    public void transformPage(View page, float position) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        int realCurrentPosition;
        int nextPosition;
        float realOffset;
        float baseElevation = mAdapter.getBaseElevation();
        boolean goingLeft = mLastOffset > positionOffset;

        // If we're going backwards, onPageScrolled receives the last position
        // instead of the current one
        if (goingLeft) {
            realCurrentPosition = position + 1;
            nextPosition = position;
            realOffset = 1 - positionOffset;
        } else {
            nextPosition = position + 1;
            realCurrentPosition = position;
            realOffset = positionOffset;
        }

        // Avoid crash on overscroll
        if (nextPosition > mAdapter.getCount() - 1 || realCurrentPosition > mAdapter.getCount() - 1) {
            return;
        }

        CardView currentCard = mAdapter.getViewAt(realCurrentPosition);

        // This might be null if a fragment is being used
        // and the views weren't created yet
        if (currentCard != null) {
            if (mScalingEnabled) {
                currentCard.setScaleX((float) (1 + 0.1 * (1 - realOffset)));
                currentCard.setScaleY((float) (1 + 0.1 * (1 - realOffset)));
            }
            currentCard.setCardElevation((baseElevation + baseElevation * (MAX_ELEVATION_FACTOR - 1) * (1 - realOffset)));
        }

        CardView nextCard = mAdapter.getViewAt(nextPosition);

        // We might be scrolling fast enough so that the next (or previous) card
        // was already destroyed or a fragment might not have been created yet
        if (nextCard != null) {
            if (mScalingEnabled) {
                nextCard.setScaleX((float) (1 + 0.1 * (realOffset)));
                nextCard.setScaleY((float) (1 + 0.1 * (realOffset)));
            }
            nextCard.setCardElevation((baseElevation + baseElevation * (MAX_ELEVATION_FACTOR - 1) * (realOffset)));
        }

        mLastOffset = positionOffset;

//        if (positionOffset == 0.0) {
//            if (position == 0) {
//                mViewPager.setCurrentItem(mAdapter.getCount() - 2, false);
//                System.out.println("ok");
//            } else if (position == mAdapter.getCount() - 1) {
//                mViewPager.setCurrentItem(1, false);
//            }
//        }

    }

    @Override
    public void onPageSelected(int position) {
        savePosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
