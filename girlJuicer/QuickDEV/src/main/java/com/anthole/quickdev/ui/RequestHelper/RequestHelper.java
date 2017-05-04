package com.anthole.quickdev.ui.RequestHelper;


import com.anthole.quickdev.http.ResponseHandlerInterface;
import com.anthole.quickdev.ui.MultiStateView;
import com.anthole.quickdev.ui.RequestHelper.base.IDataSource;
import com.anthole.quickdev.ui.RequestHelper.base.IParser;
import com.anthole.quickdev.ui.RequestHelper.base.IRequestHelper;
import com.anthole.quickdev.ui.ptr.PtrFrameLayout;
import com.anthole.quickdev.ui.ptr.PtrHandler;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

public abstract class RequestHelper <T,H extends ResponseHandlerInterface> implements IRequestHelper<H>,PtrHandler{
	
	protected H responseHandler;
	
	protected Context context;
	protected IDataSource<H> dataSource;
	protected IParser<T> parser;
	protected MultiStateView multiStateView;
	protected PtrFrameLayout ptr;
	protected View contentView;
	
	public void setDataSource(IDataSource<H> dataSource){
		this.dataSource = dataSource;
	}
	
	public void setParser(IParser<T> parser){
		this.parser = parser;
	}
	
	public void setPtr(PtrFrameLayout ptr){
		this.ptr = ptr;
		ptr.setPtrHandler(this);
		setMultiStateView((MultiStateView) ptr.getContentView());
	}
	
	public void setContentView(View contentView){
		this.contentView = contentView;
	}
	
	private void setMultiStateView(MultiStateView multiStateView){
		this.multiStateView = multiStateView;
		setViewState(MultiStateView.VIEW_STATE_LOADING);
		View error = multiStateView.getView(MultiStateView.VIEW_STATE_ERROR);
		try {
			error.findViewById(com.anthole.quickdev.R.id.id_retry).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					refresh(100);
				}
			});;
		} catch (Exception e) {
			error.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					refresh(100);
				}
			});
		}
	}
	
	@Override
	public void setViewState(int state){
		if(this.multiStateView!=null){
			multiStateView.setViewState(state);
		}
	}
	
	public RequestHelper (Context context) {
		this.context = context;
		init();
	}
	
	private void init(){
		responseHandler = getResponseHandler();
	}

}
