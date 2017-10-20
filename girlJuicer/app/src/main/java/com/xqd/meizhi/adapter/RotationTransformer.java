package com.xqd.meizhi.adapter;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.View;


public class RotationTransformer implements ViewPager.OnPageChangeListener, ViewPager.PageTransformer {

    private ViewPager mViewPager;
    private ViewPagerCardAdapter mAdapter;
    private int savePosition = 0;

    private float mLastOffset;
    private boolean mScalingEnabled=true;

    public RotationTransformer(ViewPager viewPager, ViewPagerCardAdapter adapter) {
        mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(this);
        mAdapter = adapter;
    }

    @Override
    public void transformPage(View page, float position) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//        int realCurrentPosition;
//        int nextPosition;
//        int previousPosition;
//
//        float realOffset;
//
//        realCurrentPosition = position;
//        nextPosition = position + 1;
//        previousPosition = position - 1;
//        if (position == savePosition) {
//            realOffset = positionOffset;
//        } else {
//            realOffset = positionOffset - 1;
//        }
//
//
//        if (nextPosition > mAdapter.getCount() - 1 || realCurrentPosition > mAdapter.getCount() - 1 || previousPosition > mAdapter.getCount() - 1) {
//            return;
//        }
//
//        //当前卡片
//        CardView currentCard = mAdapter.getCardViewAt(realCurrentPosition);
//        if (currentCard != null) {
//            currentCard.setRotationY(realOffset * 10);
//
//        }
//
//        //下一张
//        CardView nextCard = mAdapter.getCardViewAt(nextPosition);
//
//        if (nextCard != null) {
//            nextCard.setRotationY(realOffset * 10);
//
//        }
//
//        //上一张
//        CardView previousCard = mAdapter.getCardViewAt(previousPosition);
//        if (previousCard != null) {
//            previousCard.setRotationY(realOffset * 10);
//        }


        int realCurrentPosition;
        int nextPosition;
        float baseElevation = mAdapter.getBaseElevation();
        float realOffset;
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

        CardView currentCard = mAdapter.getCardViewAt(realCurrentPosition);

        // This might be null if a fragment is being used
        // and the views weren't created yet
        if (currentCard != null) {
            if (mScalingEnabled) {
                currentCard.setScaleX((float) (1 + 1 * (1 - realOffset)));
                currentCard.setScaleY((float) (1 + 1 * (1 - realOffset)));
            }
            currentCard.setCardElevation((baseElevation + baseElevation * (8 - 1) * (1 - realOffset)));
        }

        CardView nextCard = mAdapter.getCardViewAt(nextPosition);

        // We might be scrolling fast enough so that the next (or previous) card
        // was already destroyed or a fragment might not have been created yet
        if (nextCard != null) {
            if (mScalingEnabled) {
                nextCard.setScaleX((float) (1 + 1 * (realOffset)));
                nextCard.setScaleY((float) (1 + 1 * (realOffset)));
            }
            nextCard.setCardElevation((baseElevation + baseElevation * (8- 1) * (realOffset)));
        }

        mLastOffset = positionOffset;

    }

    @Override
    public void onPageSelected(int position) {
        savePosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
