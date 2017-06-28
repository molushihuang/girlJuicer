package com.xqd.meizhi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/6/26.
 */

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {

    private Context mContext;
    private List<String> mList;

    //构造方法传入数据
    public TestAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //这里面获取布局，并且创建viewholder
        View view = LayoutInflater.from(mContext).inflate(null, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //这里面对view做操作
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    //创建viewholder类
    public class ViewHolder extends RecyclerView.ViewHolder {

        private View mView;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView.findViewById(0);
        }
    }
}
