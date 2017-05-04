package com.xqd.meizhi.view.requestHelper;

import android.content.Context;
import android.view.View;
import com.anthole.quickdev.ui.MultiStateView;
import com.anthole.quickdev.ui.RequestHelper.RequestHelper;
import com.anthole.quickdev.ui.ptr.PtrDefaultHandler;
import com.anthole.quickdev.ui.ptr.PtrFrameLayout;
import com.xqd.meizhi.http.BaseResponseHandler;
import com.xqd.meizhi.http.ws_code;
import com.xqd.meizhi.view.PtrMaterialFrameLayout;


public class PullRequestHelper<T> extends RequestHelper<T, BaseResponseHandler> {

	private static final long MIN_LOAING_TIME = 500;
	boolean success = false;
	boolean canPullToRefresh;
	private long startLoadingTime;

	public PullRequestHelper(Context context, boolean canPullToRefresh) {
		super(context);
		this.canPullToRefresh = canPullToRefresh;
	}

	@Override
	public BaseResponseHandler getResponseHandler() {
		return new BaseResponseHandler() {
			
			@Override
			public void onSuccess(final String responseString) {
				success = true;
				refreshSuccess();
				ptr.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						setViewState(MultiStateView.VIEW_STATE_CONTENT);
						parser.parseList(responseString);
					}
				}, 150);
				
			}

			@Override
			public void onFailure(ws_code code, String message) {

				if(!success){
					ptr.refreshComplete();
					multiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
				}else{
					refreshFailed();
					multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
				}
				handlerError(context, code, message, false);
			}
		};
	}
	
	@Override
	public void refresh(int delay) {
		if(success){
			ptr.postDelayed(new Runnable() {

				@Override
				public void run() {
					ptr.autoRefresh();
				}
			}, delay);
		}else{
			ptr.postDelayed(new Runnable() {

				@Override
				public void run() {
					onRefreshBegin();
				}
			}, delay);
		}

	}

	/**
	 * 静默刷新  页面上不会显示加载
	 * @param delay
     */
	public void refreshSilent(int delay){
		ptr.postDelayed(new Runnable() {

			@Override
			public void run() {
				dataSource.request(responseHandler);
			}
		}, delay);
	}
	
	private void resetView(){
		if(success){
			multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
		}else{
			multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
		}
	}

	@Override
	public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
		if(!canPullToRefresh){
			return false;
		}
		return PtrDefaultHandler.checkContentCanBePulledDown(frame, multiStateView.getView(multiStateView.getViewState()), header);
	}

	@Override
	public void onRefreshBegin(PtrFrameLayout frame) {
		resetView();
		dataSource.request(responseHandler);
	}


	public void onRefreshBegin() {
		startLoadingTime = System.currentTimeMillis();
		resetView();
		dataSource.request(new BaseResponseHandler() {

			@Override
			public void onSuccess(final String responseString) {
				success = true;
				ptr.refreshComplete();
				long loadTime = System.currentTimeMillis() -startLoadingTime;
				long delayTime = MIN_LOAING_TIME - loadTime;//延迟加载时间
				ptr.postDelayed(new Runnable() {

					@Override
					public void run() {
						setViewState(MultiStateView.VIEW_STATE_CONTENT);
						parser.parseList(responseString);
					}
				}, Math.max(200,delayTime));

			}

			@Override
			public void onFailure(ws_code code, String message) {
				ptr.refreshComplete();

				if(!success){
					multiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
				}else{
					multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
				}
				if(code == ws_code.DELETE){
					com.anthole.quickdev.commonUtils.T.showShort(context, message);
					multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
				}else{
					handlerError(context, code, message, true);
				}

			}
		});
	}


	private void refreshSuccess(){
		if(ptr instanceof PtrMaterialFrameLayout){
			((PtrMaterialFrameLayout) ptr).refreshSuccess();
		}
	}

	private void refreshFailed(){
		if(ptr instanceof PtrMaterialFrameLayout){
			((PtrMaterialFrameLayout) ptr).refreshFailed();
		}
	}


	public void setSuccess(boolean success) {
		this.success = success;
	}
}
