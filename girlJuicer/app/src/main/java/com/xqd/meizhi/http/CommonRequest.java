package com.xqd.meizhi.http;

import android.content.Context;
import com.anthole.quickdev.http.RequestHandle;
import com.anthole.quickdev.http.RequestParams;

public class CommonRequest {


    public static RequestHandle post(Context context, String relativeUrl, RequestParams params, BaseResponseHandler responseHandler) {
        return DoRequest.postRelative(context, relativeUrl, params, responseHandler);
    }

    private static RequestHandle get(Context context, String relativeUrl, RequestParams params, BaseResponseHandler responseHandler) {
        return DoRequest.getRelative(context, relativeUrl, params, responseHandler);
    }

    //统一添加token或者id
//    private static void attachMemberIdAndToken(RequestParams params) {
//        UserBean userBean = Config.getInstance().getUserBean();
//        if (userBean != null) {
//            params.put("memberId", userBean.getId());
//            params.put("token", userBean.getToken());
//        }
//    }



    public static RequestHandle yearReport(Context context, BaseResponseHandler responseHandler) {
        String relativeUrl = "/api/report/yearUrl.check";
        RequestParams params = new RequestParams();
        return get(context, relativeUrl, params, responseHandler);
    }

    public static RequestHandle getShareUrl(Context context, String industryNo, BaseResponseHandler responseHandler) {
        String relativeUrl = "/api/investment/share.check";
        RequestParams params = new RequestParams();
        params.put("industryNo", industryNo);
        return post(context, relativeUrl, params, responseHandler);
    }



//    public static RequestHandle saveVisitorList(Context context, List<CrmEmployeeBean> list, String teamId, String versionId, BaseResponseHandler responseHandler) {
//
//
//        JSONObject object = new JSONObject();
//        try {
//            if (userBean != null) {
//                object.put("token", userBean.getToken());
//            }
//            object.put("versionsId", versionId);
//            object.put("handleTag", 2);
//            object.put("teamId", teamId);
//            object.put("teamRoleType", CrmBusinessRole.Partner.getValue());
//
//            JSONArray visitList = new JSONArray();
//            List<CrmEmployeeBean> visitBeanList = list;
//            if (visitBeanList != null && visitBeanList.size() > 0) {
//                for (CrmEmployeeBean bean : visitBeanList) {
//                    JSONObject jsonObject = new JSONObject();
//                    jsonObject.put("employeId", bean.getEmployeId());
//                    jsonObject.put("proportion", bean.getProportion());
//                    visitList.put(jsonObject);
//                }
//                object.put("visitList", visitList);
//            }
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        StringEntity stringEntity = new StringEntity(object.toString(), "UTF-8");
//        Logs.e(object.toString());
//        return AsyncHttpClientUtil.getInstance(context).post(context, BaseRequest.BaseHost + "/api/businessVersions/v2/saveMeetingInfo", stringEntity, "application/json", responseHandler);
//
//    }




}
