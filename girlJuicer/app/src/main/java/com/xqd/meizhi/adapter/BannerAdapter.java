package com.xqd.meizhi.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.xqd.meizhi.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/30.
 */

public class BannerAdapter extends PagerAdapter {
   public static final int MAX_ELEVATION_FACTOR = 3;
    private float mBaseElevation;
    private List<CardView> mViews;
    Context mContext;
    List<String> mdatas = new ArrayList<>();

    public float getBaseElevation() {
        return mBaseElevation;
    }

    public void setBaseElevation(float baseElevation) {
        mBaseElevation = baseElevation;
    }


    public BannerAdapter(Context mContext, List<String> mdatas) {
        this.mContext = mContext;
        this.mdatas = mdatas;
        this.mViews = new ArrayList<>();
        for (String s:mdatas){
            mViews.add(null);
        }
    }

    public CardView getViewAt(int position){
        int truePosition = position % mdatas.size();
        if(position<0){
            return new CardView(mContext);
        }
        return mViews.get(position);
    }

//    @Override
//    public int getCount() {
//        int trueSize = 0;
//        if (mdatas != null && mdatas.size() != 0) {
//            trueSize = mdatas.size();
//        }
//        return trueSize == 0 ? 0 : Integer.MAX_VALUE;//为了轮播的效果，设置一个很大的值
//    }
    @Override
    public int getCount() {

        return mdatas.size();//为了轮播的效果，设置一个很大的值
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_banner, null, false);
//        int truePosition = position % mdatas.size();//余数就是正确的位置
        int truePosition = position ;

        ImageView imageView = (ImageView) view.findViewById(R.id.iv_banner);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);
        Glide.with(mContext).load(mdatas.get(truePosition)).asBitmap().centerCrop().into(imageView);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(truePosition, cardView);
        container.addView(view);
        return view;
    }


//    @Override
//    public int getItemPosition(Object object) {
//        return POSITION_NONE;
//    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;//这一句一定要有
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
        mViews.set(position , null);
    }


}
