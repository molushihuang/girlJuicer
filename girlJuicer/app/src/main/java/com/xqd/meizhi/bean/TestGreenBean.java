package com.xqd.meizhi.bean;

import org.greenrobot.greendao.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2017/6/28.
 */

@Entity(nameInDb = "TestGreenTab")
public class TestGreenBean {
    @Id(autoincrement = true)
    private Long _id;
    @Property(nameInDb = "ID")
    private String id;
    @Property(nameInDb = "NAME")
    private String name;
    @Property(nameInDb = "AGE")
    private String age;
    @Transient
    private List<TestGreenBean> mGreenBeanList;
    public String getAge() {
        return this.age;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Long get_id() {
        return this._id;
    }
    public void set_id(Long _id) {
        this._id = _id;
    }

    public List<TestGreenBean> getGreenBeanList() {
        return mGreenBeanList;
    }

    public void setGreenBeanList(List<TestGreenBean> greenBeanList) {
        mGreenBeanList = greenBeanList;
    }

    @Generated(hash = 2024032249)
    public TestGreenBean(Long _id, String id, String name, String age) {
        this._id = _id;
        this.id = id;
        this.name = name;
        this.age = age;
    }
    @Generated(hash = 2118067341)
    public TestGreenBean() {
    }

}
