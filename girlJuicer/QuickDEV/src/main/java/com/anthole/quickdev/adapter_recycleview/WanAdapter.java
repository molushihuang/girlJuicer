package com.anthole.quickdev.adapter_recycleview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 可以添加多个透视图、尾视图的适配器
 * 只需重写简单方法即可
 * 需要 DmlViewHolder 配合
 * Created by WangGang on 2015/6/27.
 */
public abstract class WanAdapter<T> extends RecyclerView.Adapter<WanViewHolder> {


    private ArrayList<View> mHeaderViews = new ArrayList<>(); //头视图
    private ArrayList<View> mFooterViews = new ArrayList<>();   //尾视图


    private ArrayList<Integer> mHeaderViewTypes = new ArrayList<>();
    private ArrayList<Integer> mFooterViewTypes = new ArrayList<>();


    protected LayoutInflater mInflater;
    protected Context mContext;
    protected List<T> mDatas;
    protected final int mItemLayoutId;


    /**
     * 构造器
     *
     * @param context
     * @param mDatas
     * @param itemLayoutId
     */
    protected WanAdapter(Context context, List<T> mDatas, int itemLayoutId) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mDatas = mDatas;
        this.mItemLayoutId = itemLayoutId;
    }
    
    /**
     * 构造器
     *
     * @param context
     * @param mDatas
     * @param itemLayoutId
     */
    protected WanAdapter(Context context, int itemLayoutId) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mDatas = new ArrayList<>();
        this.mItemLayoutId = itemLayoutId;
    }

    /**
     * 可以添加多个头视图
     *
     * @param headerView
     */
    public void addHeaderView(View headerView) {
        mHeaderViews.add(headerView);
    }

    /**
     * 可以添加多个尾视图
     *
     * @param footerView 尾视图
     */
    public void addFooterView(View footerView) {
        mFooterViews.add(footerView);
    }

    @Override
    public int getItemViewType(int position) {

        if (mHeaderViews.size() > 0 && position < mHeaderViews.size()) {
            //用position作为HeaderView 的   ViewType标记
            //记录每个ViewType标记
            mHeaderViewTypes.add(position * 100000);
            return position * 100000;
        }

        if (mFooterViews.size() > 0 && position > getAdvanceCount() - 1 + mHeaderViews.size()) {
            //用position作为FooterView 的   ViewType标记
            //记录每个ViewType标记
            mFooterViewTypes.add(position * 100000);
            return position * 100000;
        }

        if (mHeaderViews.size() > 0) {
            return getAdvanceViewType(position - mHeaderViews.size());
        }


        return getAdvanceViewType(position);
    }

    /**
     * Item页布局类型个数，默认为1
     * ！！！！不能返回0！！！！
     *
     * @param position
     * @return 不能放为0！！
     */
    public int getAdvanceViewType(int position) {
        return 1;
    }

    private int getAdvanceCount() {
        if (mDatas != null) {
            return mDatas.size();
        }
        return 0;
    }

    private void onBindAdvanceViewHolder(WanViewHolder holder, int i) {
        convert(holder, mDatas.get(i),i);
    }
    
    public void add(T elem)
	{
    	mDatas.add(elem);
		notifyDataSetChanged();
	}

	public void addAll(List<T> elem)
	{
		mDatas.addAll(elem);
		notifyDataSetChanged();
	}

	public void set(T oldElem, T newElem)
	{
		set(mDatas.indexOf(oldElem), newElem);
	}

	public void set(int index, T elem)
	{
		mDatas.set(index, elem);
		notifyItemChanged(index);
	}

	public void remove(T elem)
	{
		mDatas.remove(elem);
		notifyDataSetChanged();
	}

	/**
	 * holder.getAdapterPosition()
	 * @param index
	 */
	public void remove(int index)
	{
		mDatas.remove(index);
		notifyItemRemoved(index);
	}

	public void replaceAll(List<T> elem)
	{
		mDatas.clear();
		mDatas.addAll(elem);
		notifyDataSetChanged();
	}

	public boolean contains(T elem)
	{
		return mDatas.contains(elem);
	}

	/** Clear data list */
	public void clear()
	{
		mDatas.clear();
		notifyDataSetChanged();
	}

    /**
     * 设置每个页面显示的内容
     *
     * @param holder itemHolder
     * @param item   每一Item显示的数据
     */
    public abstract void convert(WanViewHolder holder, T item,int position);


    /**
     * 创建ViewHolder
     *
     * @param parent   RecycleView对象
     * @param viewType viee类型
     * @return Holder对象
     */
    protected WanViewHolder onCreateAdvanceViewHolder(ViewGroup parent, int viewType) {

        View v = mInflater.inflate(mItemLayoutId, null);
        return new WanViewHolder(mContext,v, this);
    }

    @Override
    public WanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        if (mHeaderViewTypes.contains(viewType)) {
            return new HeaderHolder(mContext,mHeaderViews.get(viewType / 100000));
        }

        if (mFooterViewTypes.contains(viewType)) {
            int index = viewType / 100000 - getAdvanceCount() - mHeaderViews.size();
            return new FooterHolder(mContext,mFooterViews.get(index));
        }

        return onCreateAdvanceViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(WanViewHolder holder, int position) {

        if (mFooterViews.size() > 0 && (position > getAdvanceCount() - 1 + mHeaderViews.size())) {
            return;
        }


        if (mHeaderViews.size() > 0) {
            if (position < mHeaderViews.size()) {
                return;
            }
            onBindAdvanceViewHolder(holder, position - mHeaderViews.size());
            return;
        }
        onBindAdvanceViewHolder(holder, position);
    }


    class HeaderHolder extends WanViewHolder {

        public HeaderHolder(Context context,View itemView) {
            super(context,itemView);
        }
    }

    class FooterHolder extends WanViewHolder {

        public FooterHolder(Context context,View itemView) {
            super(context,itemView);
        }
    }

    @Override
    public int getItemCount() {
        if (mHeaderViews.size() > 0 && mFooterViews.size() > 0) {
            return getAdvanceCount() + mHeaderViews.size() + mFooterViews.size();
        }
        if (mHeaderViews.size() > 0) {
            return getAdvanceCount() + mHeaderViews.size();
        }
        if (mFooterViews.size() > 0) {
            return getAdvanceCount() + mFooterViews.size();
        }

        return getAdvanceCount();
    }

    public void setDatas(List<T> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }
    public List<T> getData(){
    	return mDatas;
    }
    public int getHeaderViewsCount() {
        return mHeaderViews.size();
    }

    public int getFooterViewsCount() {
        return mFooterViews.size();
    }


    protected OnItemClickListener l;


    public OnItemClickListener getItemClickListener() {
        return l;
    }

    /**
     * 设置点击事件监听器
     *
     * @param l 监听器对象
     */
    public void setOnItemClickListener(OnItemClickListener l) {
        this.l = l;
    }

    public interface OnItemClickListener {
        void onItemClickListener(int posotion);
    }
    


}
