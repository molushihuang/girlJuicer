package com.xqd.meizhi.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import butterknife.BindView;
import com.anthole.quickdev.http.RequestHandle;
import com.xqd.meizhi.R;
import com.xqd.meizhi.activity.base.BaseActivity;
import com.xqd.meizhi.adapter.GirlRecycleAdapter;
import com.xqd.meizhi.bean.GirlBean;
import com.xqd.meizhi.bean.Parser;
import com.xqd.meizhi.http.BaseResponseHandler;
import com.xqd.meizhi.http.CommonRequest;
import com.xqd.meizhi.http.ws_code;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pherson on 2017-5-10.
 */

public class GirlRecycleViewActivity extends BaseActivity implements GirlRecycleAdapter.ItemListener {

    @BindView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.recycleview)
    RecyclerView mRecyclerView;

    private List<GirlBean> mGirlBeanList = new ArrayList<>();
    GirlRecycleAdapter mRecycleAdapter;
    private boolean mIsFirstTimeTouchBottom = true;
    private int mPage = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_girl_recycle;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        mRecycleAdapter = new GirlRecycleAdapter(GirlRecycleViewActivity.this,this);

        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        mRecyclerView.addItemDecoration(decoration);
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mRecycleAdapter);
        mRecyclerView.addOnScrollListener(getOnBottomListener(layoutManager));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData(1, true);

            }
        });
        loadData(1, true);
    }

    RecyclerView.OnScrollListener getOnBottomListener(final StaggeredGridLayoutManager layoutManager) {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView rv, int dx, int dy) {
                boolean isBottom =
                        layoutManager.findLastCompletelyVisibleItemPositions(new int[2])[1] >= mRecycleAdapter.getItemCount() - 6;
                if (!mSwipeRefreshLayout.isRefreshing() && isBottom) {
                    if (!mIsFirstTimeTouchBottom) {
//                        mSwipeRefreshLayout.setRefreshing(true);
                        mPage += 1;
                        loadData(mPage, false);
                    } else {
                        mIsFirstTimeTouchBottom = false;
                    }
                }
            }
        };
    }

    public void loadData(int page, final boolean clean) {
        RequestHandle requestHandle = CommonRequest.getGirl(this, "10", page + "", new BaseResponseHandler() {
            @Override
            public void onSuccess(String data) {
                if (clean) {
                    mGirlBeanList.clear();
                }
                mGirlBeanList = Parser.parseFriendApplyList(data);
                mRecycleAdapter.addAll(mGirlBeanList);

                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(ws_code code, String message) {

            }
        });
    }


    @Override
    public void onItemClick(GirlBean item, int posion,View transitView) {

    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = space;
            }
        }
    }

}
