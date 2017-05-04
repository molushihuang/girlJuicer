package com.xqd.meizhi.http;

import android.content.Context;
import com.anthole.quickdev.http.RequestHandle;
import com.anthole.quickdev.http.RequestParams;


/**
 * @author Administrator
 */
public abstract class AbstractRequest implements BaseRequest {

    private final static int DEFAULT_PAGE_STEP = 10;

    private final static String PAGE_NUM_KEY = "pageNum";

    private final static String PAGE_STEP_KEY = "pageSize";

    protected RequestHandle mRequestHandle = null;

    protected Context mContext;

    protected int pageStep;

    @Override
    public int getPageStep() {
        return pageStep;
    }

    public void setPageStep(int pageStep) {
        this.pageStep = pageStep;
    }

    public AbstractRequest(Context context) {
        this.mContext = context;
        this.pageStep = DEFAULT_PAGE_STEP;
    }

    /**
     * 获取相对路径
     *
     * @return
     */
    public abstract String getRelativeUrl();

    @Override
    public void request(BaseResponseHandler responseHandler) {
        request(10, 1,getRelativeUrl(), getParams(), responseHandler);
    }

    @Override
    public void request(RequestParams params, BaseResponseHandler responseHandler) {
        request(10, 1,getRelativeUrl(), getParams(), responseHandler);
    }


    /**
     * 请求
     *
     * @param params          参数
     * @param responseHandler
     */
    public void request(int pageSize, int pageNum, String relativeUrl, RequestParams params, final BaseResponseHandler responseHandler) {
        String url = getHost() + relativeUrl + "/" + pageSize + "/" + pageNum;
        HttpMethod httpMethod = getHttpMethod();
        if (params == null) {
            params = new RequestParams();
        }
        mRequestHandle = DoRequest.request(mContext, httpMethod, url, params, responseHandler);
    }

    @Override
    public String getHost() {
        return BaseHost;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        if (mRequestHandle != null) {
            return mRequestHandle.cancel(mayInterruptIfRunning);
        }
        return false;
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.GET;
    }

    @Override
    public void requestPage(int page, BaseResponseHandler responseHandler) {
        RequestParams params = getParams();
//		if (params != null) {
//			params.put(PAGE_NUM_KEY, page);
//			params.put(PAGE_STEP_KEY, pageStep);
//		}
        request(pageStep, page, getRelativeUrl(),params, responseHandler);
    }

    @Override
    public void requestPage(int page, int page_step, BaseResponseHandler responseHandler) {
        RequestParams params = getParams();
//		if (params != null) {
//			params.put(PAGE_NUM_KEY, page);
//			params.put(PAGE_STEP_KEY, page_step);
//		}
        request(page_step, page,getRelativeUrl(), params, responseHandler);
    }


}
