/*
 * Copyright 2014 mycloud All right reserved. This software is the
 * confidential and proprietary information of etao.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with etao.com .
 */
package org.dlut.mycloudmanage.common.property.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * 类MyPropertyPlaceholderConfigurer.java的实现描述：TODO 类实现描述
 * 
 * @author xuyizhen 2014年12月26日 下午7:48:56
 */
public class MyPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
    private static Map myProperties;

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
            throws BeansException {
        //super.processProperties(beanFactoryToProcess, props);
        super.processProperties(beanFactoryToProcess, props);
        myProperties = new HashMap();
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            //System.out.println(keyStr + " : " + value);
            myProperties.put(keyStr, value);
        }
    }

    public static Object getContextProperty(String name) {
        return myProperties.get(name);
    }
}
