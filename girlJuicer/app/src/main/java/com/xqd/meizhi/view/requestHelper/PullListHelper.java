package com.xqd.meizhi.view.requestHelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import com.anthole.quickdev.adapter.QuickAdapter;
import com.anthole.quickdev.adapter.QuickAdapter.OnDataChangeListener;
import com.anthole.quickdev.ui.MultiStateView;
import com.anthole.quickdev.ui.RequestHelper.RequestHelper;
import com.anthole.quickdev.ui.ptr.PtrDefaultHandler;
import com.anthole.quickdev.ui.ptr.PtrFrameLayout;
import com.xqd.meizhi.R;
import com.xqd.meizhi.http.BaseResponseHandler;
import com.xqd.meizhi.http.ws_code;
import com.xqd.meizhi.view.PtrMaterialFrameLayout;
import com.xqd.meizhi.view.xlist.XListView;

import java.util.List;

public class PullListHelper<T> extends RequestHelper<T, BaseResponseHandler> implements XListView.IXListViewListener, OnDataChangeListener {

    public static final int MIN_LOAING_TIME = 500;

    public final static int START_PAGE = 1;
    XListView xListView;
    QuickAdapter<T> adapter;

    boolean loadMore;
    int page = START_PAGE;
    boolean canPullRefresh = true;
    boolean showEmptyViewWhenNoData = true;
    boolean loadAll;
    boolean showLoadAllView = true;
    boolean loadAllViewAdded;
    View viewLoadAll;

    long startLoadingTime;


    OnDataChangeListener onDataChangeListener;

    public PullListHelper(Context context, boolean loadMore) {
        super(context);
        this.loadMore = loadMore;
        viewLoadAll = LayoutInflater.from(context).inflate(R.layout.xlistview_footer_loadall, null, false);
    }

    public void setXListView(XListView xListView, QuickAdapter<T> adatper) {
        this.xListView = xListView;
        this.adapter = adatper;
        xListView.setXListViewListener(this);
        adapter.setOnDataChangeListener(this);
        xListView.setAdapter(adapter);
//		xListView.setOnScrollListener(new XListView.OnXScrollListener() {
//			@Override
//			public void onXScrolling(View view) {
//
//			}
//
//			@Override
//			public void onScrollStateChanged(AbsListView view, int scrollState) {
//				switch (scrollState){
//					case SCROLL_STATE_FLING:
//						Glide.with(context).pauseRequests();
//						//刷新
//						break;
//					case SCROLL_STATE_IDLE:
//						Glide.with(context).resumeRequests();
//						break;
//					case SCROLL_STATE_TOUCH_SCROLL:
//						Glide.with(context).resumeRequests();
//						break;
//				}
//			}
//
//			@Override
//			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//
//			}
//		});
    }

    @Override
    public void refresh(int delay) {

        if (adapter.getCount() > 0) {
            ptr.postDelayed(new Runnable() {

                @Override
                public void run() {
                    ptr.autoRefresh();
                }
            }, delay);
        } else {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
            ptr.postDelayed(new Runnable() {

                @Override
                public void run() {
                    onRefreshBegin();
                }
            }, delay);
        }

    }

    public void refreshCurrent(int delay, final boolean scroll2Top) {
        ptr.postDelayed(new Runnable() {

            @Override
            public void run() {
                final int currentCount = adapter.getCount() > 0 ? page * dataSource.getPageStep() : dataSource.getPageStep();
                xListView.setPullLoadEnable(false);
                dataSource.requestPage(START_PAGE, currentCount, new BaseResponseHandler() {

                    @Override
                    public void onSuccess(String data) {

                        ptr.refreshComplete();
                        final List<T> onePage = parser.parseList(data);

                        ptr.postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                if (onePage != null) {
                                    adapter.replaceAll(onePage);
                                } else {
                                    adapter.notifyDataSetChanged();
                                }
                                if (loadMore) {
                                    if (onePage == null || onePage.size() < currentCount) {
                                        xListView.setPullLoadEnable(false);
                                    } else {
                                        xListView.setPullLoadEnable(true);
                                    }
                                }
                            }
                        }, 0);
                        if (scroll2Top) {
                            xListView.setSelection(0);
                        }


                    }

                    @Override
                    public void onFailure(ws_code code, String message) {
                        ptr.refreshComplete();
                        handlerError(context, code, message, false);
                        ptr.postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                if (adapter.getData().size() == 0) {
                                    multiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
                                }
                            }
                        }, 200);

                    }
                });
            }
        }, delay);
    }

    public void resetView() {
        if (adapter == null || adapter.getCount() == 0) {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
        } else {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        }
    }


    @Override
    public BaseResponseHandler getResponseHandler() {
        return null;
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return canPullRefresh && PtrDefaultHandler.checkContentCanBePulledDown(frame, xListView, header) && !xListView.isLoading() && multiStateView.getViewState() != MultiStateView.VIEW_STATE_LOADING;
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        xListView.setPullLoadEnable(false);
        dataSource.requestPage(START_PAGE, new BaseResponseHandler() {

            @Override
            public void onSuccess(String data) {
                refreshSuccess();
                final List<T> onePage = parser.parseList(data);

                ptr.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        if (onePage != null) {
                            adapter.replaceAll(onePage);
                        } else {
                            adapter.notifyDataSetChanged();
                        }
                        if (loadMore) {
                            if (onePage == null || onePage.size() < dataSource.getPageStep()) {
                                if (onePage != null && onePage.size() > 0) {
                                    markAsLoadAll(true);
                                } else {
                                    markAsLoadAll(false);
                                }
                                xListView.setPullLoadEnable(false);

                            } else {
                                xListView.setPullLoadEnable(true);
                                markAsLoadAll(false);
                            }
                        }

                        page = START_PAGE;
                    }
                }, 200);

            }

            @Override
            public void onFailure(ws_code code, String message) {

                handlerError(context, code, message, false);
                ptr.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        if (adapter.getData().size() == 0) {
                            multiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
                            ptr.refreshComplete();
                        } else {
                            refreshFailed();
                        }
                    }
                }, 100);

            }
        });
    }

    public void onRefreshBegin() {
        startLoadingTime = System.currentTimeMillis();
        xListView.setPullLoadEnable(false);
        dataSource.requestPage(START_PAGE, new BaseResponseHandler() {

            @Override
            public void onSuccess(String data) {

                final List<T> onePage = parser.parseList(data);
                long loadTime = System.currentTimeMillis() - startLoadingTime;
                long delayTime = MIN_LOAING_TIME - loadTime;//延迟加载时间
                ptr.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        page = START_PAGE;
                        if (onePage != null) {
                            adapter.replaceAll(onePage);
                        } else {
                            adapter.notifyDataSetChanged();
                        }
                        if (loadMore) {
                            if (onePage == null || onePage.size() < dataSource.getPageStep()) {
                                if (onePage != null && onePage.size() > 0) {
                                    markAsLoadAll(true);
                                } else {
                                    markAsLoadAll(false);
                                }
                                xListView.setPullLoadEnable(false);

                            } else {
                                xListView.setPullLoadEnable(true);
                                markAsLoadAll(false);
                            }
                        }


                    }
                }, Math.max(200, delayTime));

            }

            @Override
            public void onFailure(ws_code code, String message) {
                ptr.refreshComplete();
                handlerError(context, code, message, true);
                ptr.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        if (adapter.getData().size() == 0) {
                            multiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
                        }
                    }
                }, 200);

            }
        });
    }

    @Override
    public void onLoadMore() {
        dataSource.requestPage(page + 1, new BaseResponseHandler() {

            @Override
            public void onSuccess(String data) {
                page++;
                xListView.stopLoadMore();
                List<T> onePage = parser.parseList(data);
                if (onePage != null) {
                    adapter.addAll(onePage);
                }
                if (onePage == null || onePage.size() < dataSource.getPageStep()) {
                    xListView.setPullLoadEnable(false);
                    markAsLoadAll(true);
                } else {
                    xListView.setPullLoadEnable(true);
                    markAsLoadAll(false);
                }
            }

            @Override
            public void onFailure(ws_code code, String message) {
                xListView.stopLoadMore();
                handlerError(context, code, message, true);
            }
        });
    }

    @Override
    public void onDataChanged() {
        if (multiStateView == null) {
            return;
        }

//		if ((adapter.getData() == null || adapter.getData().size() == 0) && xListView.getHeaderViewsCount() == 0) {
//			multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
//		} else {
//			multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
//		}

        if (onDataChangeListener != null) {
            onDataChangeListener.onDataChanged();
        }

        if (adapter.getData() == null || (adapter.getData().size() == 0 && showEmptyViewWhenNoData)) {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        } else {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        }
    }


    private void markAsLoadAll(boolean loadAll) {
//		if(this.loadAll == loadAll){
//			return;
//		}

        if (loadAll && showLoadAllView) {
            if (!loadAllViewAdded) {
                xListView.setFooterDividersEnabled(false);
                xListView.addFooterView(viewLoadAll, null, false);
            }
            loadAllViewAdded = true;
        } else {
            if (loadAllViewAdded) {
                xListView.setFooterDividersEnabled(true);
                xListView.removeFooterView(viewLoadAll);
            }
            loadAllViewAdded = false;
        }
        this.loadAll = loadAll;
    }

    public void setShowLoadAllView(boolean showLoadAllView) {
        this.showLoadAllView = showLoadAllView;
    }

    public boolean isCanPullRefresh() {
        return canPullRefresh;
    }

    public void setCanPullRefresh(boolean canPullRefresh) {
        this.canPullRefresh = canPullRefresh;
    }

    public void setShowEmptyViewWhenNoData(boolean showEmptyViewWhenNoData) {
        this.showEmptyViewWhenNoData = showEmptyViewWhenNoData;
    }

    public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener) {
        this.onDataChangeListener = onDataChangeListener;
    }

    private void refreshSuccess() {
        if (ptr instanceof PtrMaterialFrameLayout) {
            ((PtrMaterialFrameLayout) ptr).refreshSuccess();
        }
    }

    private void refreshFailed() {
        if (ptr instanceof PtrMaterialFrameLayout) {
            ((PtrMaterialFrameLayout) ptr).refreshFailed();
        }
    }

    public int getPage() {
        return page;
    }

}
