package com.xqd.meizhi.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.anthole.quickdev.adapter.BaseAdapterHelper;
import com.anthole.quickdev.adapter.QuickAdapter;
import com.bumptech.glide.Glide;
import com.xqd.meizhi.R;
import com.xqd.meizhi.bean.GirlBean;

public class AndroidShowAdapter extends QuickAdapter<GirlBean> {

    ItemListener mItemListener;

    public AndroidShowAdapter(Context context, ItemListener mItemListener) {
        super(context, R.layout.item_android);
        this.mItemListener = mItemListener;

    }

    @Override
    protected void convert(BaseAdapterHelper helper, final GirlBean item) {

        RelativeLayout rootLayout = helper.getView(R.id.activate_layout);
        TextView tvDse = helper.getView(R.id.tv_des);
        TextView ivWho = helper.getView(R.id.tv_who);
        ImageView ivGirl = helper.getView(R.id.iv_des);
        if (item.getImages() == null) {
            ivGirl.setVisibility(View.GONE);
        } else {
            ivGirl.setVisibility(View.VISIBLE);
            Glide.with(context).load(item.getImages().get(0) + "?imageView2/0/w/100").asBitmap().centerCrop().into(ivGirl);

        }
        tvDse.setText(item.getDesc());
        ivWho.setText("作者 * " + item.getWho());

        rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemListener.onItemClick(item);
            }
        });
    }

    public static interface ItemListener {
        void onItemClick(GirlBean item);

    }

}
