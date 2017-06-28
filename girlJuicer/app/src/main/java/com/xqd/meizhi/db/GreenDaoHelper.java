package com.xqd.meizhi.db;

import com.xqd.meizhi.bean.TestGreenBean;
import com.xqd.meizhi.utils.Config;
import girlJuicer.dao.TestGreenBeanDao;

/**
 * Created by Administrator on 2017/6/28.
 */

public class GreenDaoHelper {


    /**
     * 保存数据
     * @param testGreenBean
     */
    public static void saveTestGreenBean(TestGreenBean testGreenBean) {
        TestGreenBeanDao testGreenBeanDao = Config.getInstance().getDaoSession().getTestGreenBeanDao();
        TestGreenBean bean = testGreenBeanDao.queryBuilder().where(TestGreenBeanDao.Properties.Id.eq(testGreenBean.getId())).build().unique();

        if (bean == null) {

            testGreenBeanDao.insert(testGreenBean);
        } else {
            testGreenBean.setId(bean.getId());
            testGreenBeanDao.update(testGreenBean);
        }

    }

    /**
     * 根据id获取数据库存储的数据
     *
     * @param id
     * @return
     */
    public static TestGreenBean getTestBean(String id) {
        TestGreenBean testGreenBean = null;

        TestGreenBeanDao testGreenBeanDao = Config.getInstance().getDaoSession().getTestGreenBeanDao();
        testGreenBean = testGreenBeanDao.queryBuilder().where(TestGreenBeanDao.Properties.Id.eq(id)).build().unique();

        return testGreenBean;
    }
}
