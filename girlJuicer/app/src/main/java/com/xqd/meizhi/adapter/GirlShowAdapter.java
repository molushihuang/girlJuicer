package com.xqd.meizhi.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import com.anthole.quickdev.adapter.BaseAdapterHelper;
import com.anthole.quickdev.adapter.QuickAdapter;
import com.bumptech.glide.Glide;
import com.xqd.meizhi.R;
import com.xqd.meizhi.bean.GirlBean;

public class GirlShowAdapter extends QuickAdapter<GirlBean> {

    ItemListener mItemListener;

    public GirlShowAdapter(Context context, ItemListener mItemListener) {
        super(context, R.layout.item_girl);
        this.mItemListener = mItemListener;

    }

    @Override
    protected void convert(BaseAdapterHelper helper, final GirlBean item) {

        ImageView ivGirl = helper.getView(R.id.iv_girl);
        Glide.with(context).load(item.getUrl()).asBitmap().centerCrop().into(ivGirl);

        ivGirl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mItemListener.onItemLongClick(item);
                return false;
            }
        });
    }

    public static interface ItemListener {
        void onItemClick(GirlBean item);
        void onItemLongClick(GirlBean item);

    }

}
