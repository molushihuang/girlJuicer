package com.xqd.meizhi.activity;


import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

import static com.xqd.meizhi.Constants.IntentKeys.TYPLE;

public class DrawerActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, GirlRecycleAdapter.ItemListener {

    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;
    SwipeRefreshLayout mSwipeRefreshLayout;
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
        toolbar=findViewById(R.id.main_toolbar);
        drawer=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        mSwipeRefreshLayout=findViewById(R.id.swipe_refresh_widget);
        mRecyclerView=findViewById(R.id.recycleview);
//        SystemBarTintInvoke.apply(this, R.color.blue, true);

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
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mRecycleAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                boolean isBottom = isSlideToBottom(recyclerView);
                if (!mSwipeRefreshLayout.isRefreshing() && isBottom) {
                    if (!mIsFirstTimeTouchBottom) {
                        mSwipeRefreshLayout.setRefreshing(true);
                        mPageNum += 1;
                        loadData(mPageNum, false);
                    } else {
                        mIsFirstTimeTouchBottom = false;
                    }
                }
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData(1, true);

            }
        });
        loadData(1, true);

    }

    //判断是否滑动到底部
    private boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
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
                mSwipeRefreshLayout.setRefreshing(false);
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
        menu.add(0, 2, 2, "banner_test");
        menu.add(0, 3, 3, "letter_test");
        menu.add(0, 4, 4, "map_test");
        menu.add(0, 5, 5, "danmaku_test");
        menu.add(0, 6, 6, "canvas_test");
        menu.add(0, 7, 7, "socket_test");
        menu.add(0, 8, 8, "calender_test");
        menu.add(0, 9, 9, "video_test");
        menu.add(0, 10, 10, "tablayout_test");
        menu.add(0, 11, 11, "Capture_test");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())//得到被点击的item的itemId
        {
            case 1:
                jump2Activity(AboutActivity.class);
                break;
            case 2:
                jump2Activity(BannerTestActivity.class);
                break;
            case 3:
                jump2Activity(LetterSortActivity.class);
                break;
            case 4:
                jump2Activity(MapActivity.class);
                break;
            case 5:
                jump2Activity(DanmakuActivity.class);
                break;
            case 6:
                jump2Activity(CanvasDemoActivity.class);
                break;
            case 7:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setItems(new String[]{"to client", "to server"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which) {
//                            case 0:
//                                jump2Activity(SocketClientTestActivity.class);
//                                break;
//                            case 1:
//                                jump2Activity(SocketServerTestActivityActivity.class);
//                                break;
                        }
                    }
                })
                        .create().show();
                break;
            case 8:
                jump2Activity(CalenderActivity.class);
                break;
            case 9:
                jump2Activity(VideoTestActivity.class);
                break;
            case 10:
                jump2Activity(TabLayoutActivity.class);
                break;
            case 11:
                jump2Activity(CaptureActivity.class);
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
            case R.id.nav_setting:

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
