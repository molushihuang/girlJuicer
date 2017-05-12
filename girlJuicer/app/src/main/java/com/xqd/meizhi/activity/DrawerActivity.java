package com.xqd.meizhi.activity;


import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import butterknife.Bind;
import com.anthole.quickdev.http.RequestHandle;
import com.anthole.quickdev.invoke.SystemBarTintInvoke;
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

import static com.xqd.meizhi.Constants.IntentKeys.TYPLE;

public class DrawerActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, GirlRecycleAdapter.ItemListener {

    @Bind(R.id.main_toolbar)
    Toolbar toolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;
    @Bind(R.id.nav_view)
    NavigationView navigationView;
    @Bind(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.recycleview)
    RecyclerView mRecyclerView;

    private List<GirlBean> mGirlBeanList = new ArrayList<>();
    GirlRecycleAdapter mRecycleAdapter;
    private boolean mIsFirstTimeTouchBottom = true;
    private int mPageNum = 1;
    private String mPageSize = "10";


    @Override
    protected int getLayoutId() {
        return R.layout.activity_drawer;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        SystemBarTintInvoke.apply(this, R.color.blue, true);

//        toolbar.setTitle("444");
        setSupportActionBar(toolbar); //将toolbar设置为actionbar

//        drawer.setScrimColor(Color.RED);
//        drawer.setDrawerShadow(R.drawable.bg_white_stroke_white, GravityCompat.START);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        mRecycleAdapter = new GirlRecycleAdapter(this, this);

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
                boolean isBottom = layoutManager.findLastCompletelyVisibleItemPositions(new int[2])[1] >= mRecycleAdapter.getItemCount() - 6;
                if (!mSwipeRefreshLayout.isRefreshing() && isBottom) {
                    if (!mIsFirstTimeTouchBottom) {
//                        mSwipeRefreshLayout.setRefreshing(true);
                        mPageNum += 1;
                        loadData(mPageNum, false);
                    } else {
                        mIsFirstTimeTouchBottom = false;
                    }
                }
            }
        };
    }

    /**
     * 请求数据
     *
     * @param pageNum
     * @param clean
     */
    public void loadData(int pageNum, final boolean clean) {
        RequestHandle requestHandle = CommonRequest.getGirl(this, mPageSize, pageNum + "", new BaseResponseHandler() {
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

    @Override
    public void onItemClick(final GirlBean item, int posion, View transitView) {

        List<String> pathArr = new ArrayList<>();
        for (GirlBean girlBean : mRecycleAdapter.getData()) {
            pathArr.add(girlBean.getUrl());
        }

        Bundle bundle = new Bundle();
        bundle.putStringArrayList(PictruePreviewActivity.EXTRA_PHOTOS, (ArrayList<String>) pathArr);
        bundle.putInt(PictruePreviewActivity.EXTRA_CURRENT_ITEM, posion);
        jump2Activity(PictruePreviewActivity.class, bundle);

        // TODO: 2017-5-10 动画待研究 
//        Intent intent = PictruePreviewActivity.newIntent(this, posion, pathArr);
//        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, transitView, PictruePreviewActivity.TRANSIT_PIC);
//        try {
//            ActivityCompat.startActivity(this, intent, optionsCompat.toBundle());
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//            startActivity(intent);
//        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 1, "about");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())//得到被点击的item的itemId
        {
            case 1:
                jump2Activity(AboutActivity.class);
                break;

        }
        return super.onOptionsItemSelected(item);


    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Bundle bundle = new Bundle();
        switch (item.getItemId())//得到被点击的item的itemId
        {
            case R.id.nav_android:
                bundle.putString(TYPLE, "android");
                jump2Activity(AndroidActivity.class, bundle);
                break;
            case R.id.nav_ios:
                bundle.putString(TYPLE, "ios");
                jump2Activity(AndroidActivity.class, bundle);
                break;
            case R.id.nav_web:
                bundle.putString(TYPLE, "web");
                jump2Activity(AndroidActivity.class, bundle);
                break;
            case R.id.nav_recommend:
                bundle.putString(TYPLE, "recommend");
                jump2Activity(AndroidActivity.class, bundle);
                break;
            case R.id.nav_expand:
                bundle.putString(TYPLE, "expend");
                jump2Activity(AndroidActivity.class, bundle);
                break;
            case R.id.nav_video:
                bundle.putString(TYPLE, "video");
                jump2Activity(AndroidActivity.class, bundle);
                break;
            case R.id.nav_collections:
                jump2Activity(CollectionActivity.class, bundle);
                break;
            default:
                jump2Activity(AndroidActivity.class, bundle);
                break;

        }
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
