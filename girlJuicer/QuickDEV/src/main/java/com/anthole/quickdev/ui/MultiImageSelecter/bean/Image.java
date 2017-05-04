package com.anthole.quickdev.ui.MultiImageSelecter.bean;

import java.io.Serializable;

/**
 * 图片实体
 * Created by Nereo on 2015/4/7.
 */
public class Image implements Serializable {
    public String path;
    public String name;
    public long time;
    public long duration;
    public long size;
    public Image(String path){
        this.path = path;

    }
    public Image(String path, String name, long time){
        this.path = path;
        this.name = name;
        this.time = time;
    }
    public Image(String path,long time,long duration,long size ){
        this.path = path;
        this.time = time;
        this.duration = duration;
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        try {
            Image other = (Image) o;
            return this.path.equalsIgnoreCase(other.path);
        }catch (ClassCastException e){
            e.printStackTrace();
        }
        return super.equals(o);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
