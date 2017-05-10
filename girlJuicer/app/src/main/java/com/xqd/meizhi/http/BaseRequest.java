package com.xqd.meizhi.http;

import com.anthole.quickdev.http.RequestParams;
import com.anthole.quickdev.ui.RequestHelper.base.IDataSource;

public interface BaseRequest extends IDataSource<BaseResponseHandler> {

    //url
    public static final String BaseHost = "http://gank.io/api";

    public RequestParams getParams();

    public String getHost();

    /**
     * 获取请求方法 get post
     *
     * @return
     */
    public HttpMethod getHttpMethod();

    public boolean cancel(boolean mayInterruptIfRunning);

    public enum HttpMethod {
        POST, GET
    }


    public static final String URL_woman = "/data/福利";
    public static final String URL_android = "/data/Android";
    public static final String URL_ios = "/data/iOS";
    public static final String URL_web = "/data/前端";
    public static final String URL_recommend = "/data/瞎推荐";
    public static final String URL_expend = "/data/拓展资源";
    public static final String URL_video = "/data/休息视频";
    public static final String URL_all = "/data/all";

}
