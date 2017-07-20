package com.xqd.meizhi.bean;

/**
 * Created by Administrator on 2017/7/20.
 */

public class Contant {

    public Contant(String name) {
        this.name = name;
    }

    private String name;

    private String firstChar;

    private int headIndex;

    public int getHeadIndex() {
        return headIndex;
    }

    public void setHeadIndex(int headIndex) {
        this.headIndex = headIndex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstChar() {
        return firstChar;
    }

    public void setFirstChar(String firstChar) {
        this.firstChar = firstChar;
    }
}
