/*
 * Copyright 2014 mycloud All right reserved. This software is the
 * confidential and proprietary information of etao.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with etao.com .
 */
package org.dlut.mycloudmanage.common.property.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 类MyPropertyUtil.java的实现描述：TODO 类实现描述
 * 
 * @author xuyizhen 2014年12月26日 下午8:06:22
 */
//@Component
public class MyPropertiesUtil implements ApplicationContextAware {
    public static final String        KEY = "propertyConfigurer"; //自定义配置文件的bean的Id
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {

        MyPropertiesUtil.applicationContext = applicationContext;

        //     System.out.println("初始化applicationContext ==null?" + (applicationContext == null));
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 获取配置文件myconfig.properties中的内容
     *
     * @param key
     * @return
     */
    public static String getValue(String key) {
        MyPropertyPlaceholderConfigurer cp = (MyPropertyPlaceholderConfigurer) applicationContext.getBean(KEY);
        String result = cp.getContextProperty(key).toString();

        return result;
    }

}
