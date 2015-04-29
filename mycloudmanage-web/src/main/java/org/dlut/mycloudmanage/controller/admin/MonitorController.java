/*
 * Copyright 2015 etao.com All right reserved. This software is the
 * confidential and proprietary information of etao.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with etao.com .
 */
package org.dlut.mycloudmanage.controller.admin;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dlut.mycloudmanage.biz.PerformanceMonitorBiz;
import org.dlut.mycloudmanage.common.constant.MenuEnum;
import org.dlut.mycloudmanage.common.constant.UrlConstant;
import org.dlut.mycloudmanage.common.property.utils.MyPropertiesUtil;
import org.dlut.mycloudmanage.common.utils.MyJsonUtils;
import org.dlut.mycloudmanage.controller.common.BaseController;
import org.dlut.mycloudserver.client.common.Pagination;
import org.dlut.mycloudserver.client.common.performancemonitor.PerformanceMonitorDTO;
import org.dlut.mycloudserver.client.common.performancemonitor.QueryPerformanceMonitorCondition;
import org.dlut.mycloudserver.client.common.usermanage.RoleEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 类MonitorController.java的实现描述：TODO 类实现描述
 * 
 * @author luojie 2015年4月28日 下午3:14:57
 */
@Controller
public class MonitorController extends BaseController {

    private static Logger         log = LoggerFactory.getLogger(MonitorController.class);

    @Resource(name = "performanceMonitorBiz")
    private PerformanceMonitorBiz performanceMonitorBiz;

    @RequestMapping(value = UrlConstant.ADMIN_MONITOR_LIST)
    public String monitorList(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String errorDesc = this.setDefaultEnv(request, response, model);
        if (errorDesc != null) {
            return this.goErrorPage(errorDesc);
        }
        this.setShowMenuList(RoleEnum.ADMIN, MenuEnum.ADMIN_MONITOR_LIST, model);
        model.put("screen", "admin/monitor_list");
        model.put("js", "admin/monitor_list");
        return "default";
    }

    @RequestMapping(value = UrlConstant.ADMIN_MONITOR_GETLIST, produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public String getMonitorListByAjax(Integer currentPage) {
        JSONObject json = new JSONObject();

        if (currentPage == null || currentPage <= 0) {
            currentPage = 1;
        }
        QueryPerformanceMonitorCondition queryPerformanceMonitorCondition = new QueryPerformanceMonitorCondition();
        int PAGESIZE = Integer.parseInt(MyPropertiesUtil.getValue("pagesize"));
        queryPerformanceMonitorCondition.setLimit(PAGESIZE);
        queryPerformanceMonitorCondition.setOffset((currentPage - 1) * PAGESIZE);
        Pagination<PerformanceMonitorDTO> pagination = performanceMonitorBiz.query(queryPerformanceMonitorCondition);
        if (pagination == null) {
            return MyJsonUtils.getFailJsonString(json, "获取性能数据失败");
        }

        JSONArray data = new JSONArray();
        for (PerformanceMonitorDTO performanceMonitorDTO : pagination.getList()) {
            JSONObject monitorData = new JSONObject();
            monitorData.put("id", performanceMonitorDTO.getId());
            monitorData.put("aliaseName", performanceMonitorDTO.getAliaseName());
            monitorData.put("ip", performanceMonitorDTO.getIp());
            monitorData.put("status", performanceMonitorDTO.getPerformanceMonitorStatus().getDesc());
            monitorData.put("cores", performanceMonitorDTO.getCores());
            monitorData.put("loadAverage", performanceMonitorDTO.getLoadAverage());
            monitorData.put("totalMem", performanceMonitorDTO.getTotalMem());
            monitorData.put("usedMem", performanceMonitorDTO.getUsedMem());
            monitorData.put("sendRate", performanceMonitorDTO.getSendRate());
            monitorData.put("receiveRate", performanceMonitorDTO.getReceiveRate());
            data.add(monitorData);
        }
        String res = MyJsonUtils.getSuccessJsonString(json, "获取数据成功", data.toString());
        System.out.println(res);
        return MyJsonUtils.getSuccessJsonString(json, "获取数据成功", data.toString());
    }
}
