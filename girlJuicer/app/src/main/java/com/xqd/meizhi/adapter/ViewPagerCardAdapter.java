package com.xqd.meizhi.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.xqd.meizhi.R;
import com.xqd.meizhi.bean.VirePagerBean;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerCardAdapter extends PagerAdapter {

    private List<CardView> mViews;
    private List<VirePagerBean> mData;
    private float mBaseElevation;
    Context context;

    public float getBaseElevation() {
        return mBaseElevation;
    }

    public void setBaseElevation(float baseElevation) {
        mBaseElevation = baseElevation;
    }

    public ViewPagerCardAdapter(Context context) {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
        this.context = context;


    }

    public void clear() {
        mData.clear();
    }

    public void addAll(List<VirePagerBean> data) {
        mData.addAll(data);
        for (VirePagerBean virePagerBean : data) {
            mViews.add(null);
        }
    }

    public void addCardItem(VirePagerBean item) {
        mViews.add(null);
        mData.add(item);
    }

    public CardView getCardViewAt(int position) {
        if(position<0){
            return new CardView(context);
        }
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_card, container, false);
        container.addView(view);

        CardView cardView = (CardView) view.findViewById(R.id.cardView);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * 8);
        mViews.set(position, cardView);
        return view;
    }
    @Override
    public float getPageWidth(int position) {
        return (float) 1;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

}
