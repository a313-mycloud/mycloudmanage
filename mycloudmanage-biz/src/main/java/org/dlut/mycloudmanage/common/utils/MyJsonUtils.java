/*
 * Copyright 2014 etao.com All right reserved. This software is the
 * confidential and proprietary information of etao.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with etao.com .
 */
package org.dlut.mycloudmanage.common.utils;

import com.alibaba.fastjson.JSONObject;

/**
 * 类JsonUtils.java的实现描述：TODO 类实现描述
 * 
 * @author xuyizhen Dec 17, 2014 4:19:45 PM
 */
public class MyJsonUtils {
    /**
     * @param json
     * @param isLogin
     * @param isAuth
     * @param isSuccess
     * @param mess
     * @param data
     * @return
     */
    public static String  getJsonString(JSONObject json, boolean isLogin, boolean isAuth, boolean isSuccess,
                                       String mess, String data) {
        json.put("isLogin", isLogin);
        json.put("isAuth", isAuth);
        json.put("isSuccess", isSuccess);
        json.put("message", mess);
        json.put("data", data);
        return json.toString();
    }

    public static String getSuccessJsonString(JSONObject json, String mess) {
        json.put("isLogin", true);
        json.put("isAuth", true);
        json.put("isSuccess", true);
        json.put("message", mess);
        json.put("data", "");
        return json.toString();
    }

    public static String getSuccessJsonString(JSONObject json, String mess, String data) {
        json.put("isLogin", true);
        json.put("isAuth", true);
        json.put("isSuccess", true);
        json.put("message", mess);
        json.put("data", data);
        return json.toString();
    }

    public static String getFailJsonString(JSONObject json, String mess) {
        json.put("isLogin", true);
        json.put("isAuth", true);
        json.put("isSuccess", false);
        json.put("message", mess);
        json.put("data", "");
        return json.toString();
    }
}
