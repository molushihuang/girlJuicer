package com.xqd.meizhi.bean;

import com.anthole.quickdev.commonUtils.jsonUtils.JsonUtil;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pherson on 2017-5-2.
 */

public class Parser {

    /**
     * 解析福利妹纸列表
     *
     * @param data
     * @return
     */
    public static List<GirlBean> parseFriendApplyList(String data) {
        List<GirlBean> list = null;
        list = JsonUtil.getListFromJson(data, new TypeToken<ArrayList<GirlBean>>() {
        });
        return list == null ? new ArrayList<GirlBean>() : list;

    }

    public static List<SocketTestBean> parseSocketList(String data) {
        List<SocketTestBean> list = null;
        list = JsonUtil.getListFromJson(data, new TypeToken<ArrayList<SocketTestBean>>() {
        });
        return list == null ? new ArrayList<SocketTestBean>() : list;

    }
}
