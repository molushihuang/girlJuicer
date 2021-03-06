package com.xqd.meizhi.bean;

import com.anthole.quickdev.commonUtils.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by pherson on 2017-5-3.
 */

public class GirlBean implements Serializable {


    private String _id;
    private String createdAt;
    private String type;
    private String url;
    private String who;
    private String desc;
    private List<String> images;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<String> getImages() {
        if (images == null || images.size() == 0)
            return null;
        else{
            if(StringUtils.isEmpty(images.get(0))){
                return null;
            }else{
                return images;

            }
        }
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public String getSmallUrl() {
        return url + "?imageView2/0/w/400";
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWho() {
        return StringUtils.isEmpty(who) ? "" : who;
    }

    public void setWho(String who) {
        this.who = who;
    }
}
