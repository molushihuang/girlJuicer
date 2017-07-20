package com.xqd.meizhi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.xqd.meizhi.R;
import com.xqd.meizhi.bean.Contant;

import java.util.List;

/**
 * Created by Administrator on 2017/7/20.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Contant> mContantList;
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayMap<String, Integer> lettes;

    public MyAdapter(List<Contant> contantList, Context context, ArrayMap<String, Integer> lettes) {
        mContantList = contantList;
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        this.lettes = lettes;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return new ViewHolder(mInflater.inflate(R.layout.item_letter, parent, false), false);
            case 1:
                return new ViewHolder(mInflater.inflate(R.layout.item_letter, parent, false), true);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_name.setText(mContantList.get(position).getName());
        holder.tvHead.setText(mContantList.get(position).getFirstChar());
    }

    @Override
    public int getItemCount() {
        return mContantList.size();
    }

    @Override
    public int getItemViewType(int position) {
        //根据每个字母下第一个联系人在数据中的位置，来显示headView
        Contant contant = mContantList.get(position);
        if (lettes.get(contant.getFirstChar()) == position) {
            return 1;
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_name;
        private TextView tvHead;

        public ViewHolder(View itemView, boolean show) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tvHead = (TextView) itemView.findViewById(R.id.tvHead);
            if (!show) {
                tvHead.setVisibility(View.GONE);
            } else {
                tvHead.setVisibility(View.VISIBLE);
            }
        }
    }
}
