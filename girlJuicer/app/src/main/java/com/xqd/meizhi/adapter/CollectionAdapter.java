package com.xqd.meizhi.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.anthole.quickdev.adapter_recycleview.WanAdapter;
import com.anthole.quickdev.adapter_recycleview.WanViewHolder;
import com.bumptech.glide.Glide;
import com.xqd.meizhi.R;
import com.xqd.meizhi.bean.GirlBean;

/**
 * Created by pherson on 2017-5-10.
 */

public class CollectionAdapter extends WanAdapter<GirlBean> {
    Context context;
    ItemListener mItemListener;

    public CollectionAdapter(Context context, ItemListener mItemListener) {
        super(context, R.layout.item_android);
        this.context = context;
        this.mItemListener = mItemListener;
    }

    @Override
    public void convert(WanViewHolder holder, final GirlBean item, final int position) {
        RelativeLayout rootLayout = holder.getView(R.id.activate_layout);
        TextView tvDse = holder.getView(R.id.tv_des);
        TextView ivWho = holder.getView(R.id.tv_who);
        ImageView ivGirl = holder.getView(R.id.iv_des);
        if (item.getImages() == null) {
            ivGirl.setVisibility(View.GONE);
        } else {
            ivGirl.setVisibility(View.VISIBLE);
            Glide.with(context).load(item.getImages().get(0) + "?imageView2/0/w/200").asBitmap().centerCrop().into(ivGirl);

        }
        tvDse.setText(item.getDesc());
        ivWho.setText(item.getType() + "   作者 * " + item.getWho());

        rootLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mItemListener != null) {
                    mItemListener.onItemLongClick(item, position);
                }
                return true;
            }
        });

        rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemListener != null) {
                    mItemListener.onItemClick(item);
                }
            }
        });
    }

    public static interface ItemListener {
        void onItemLongClick(GirlBean item, int posion);
        void onItemClick(GirlBean item);

    }
}
