package com.xqd.meizhi.adapter;

import android.content.Context;
import android.view.View;
import com.anthole.quickdev.adapter_recycleview.WanAdapter;
import com.anthole.quickdev.adapter_recycleview.WanViewHolder;
import com.bumptech.glide.Glide;
import com.xqd.meizhi.R;
import com.xqd.meizhi.bean.GirlBean;
import com.xqd.meizhi.view.RatioImageView;

/**
 * Created by pherson on 2017-5-10.
 */

public class GirlRecycleAdapter extends WanAdapter<GirlBean> {
    Context context;
    ItemListener mItemListener;

    public GirlRecycleAdapter(Context context, ItemListener mItemListener) {
        super(context, R.layout.item_girl_recycle);
        this.context = context;
        this.mItemListener = mItemListener;
    }

    @Override
    public void convert(WanViewHolder holder, final GirlBean item, final int position) {
        final RatioImageView ivGirl = holder.getView(R.id.iv_girl);
        ivGirl.setOriginalSize(50, 70);
//        ivGirl.setOriginalSize(50, (int) (60 + 15 * Math.random()));
        Glide.with(context).load(item.getSmallUrl()).asBitmap().centerCrop().into(ivGirl);

        ivGirl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemListener.onItemClick(item, position, ivGirl);
            }
        });
    }

    public static interface ItemListener {
        void onItemClick(GirlBean item, int posion, View transitView);

    }
}
