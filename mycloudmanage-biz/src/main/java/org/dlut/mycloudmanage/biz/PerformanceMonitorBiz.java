/*
 * Copyright 2015 etao.com All right reserved. This software is the
 * confidential and proprietary information of etao.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with etao.com .
 */
package org.dlut.mycloudmanage.biz;

import javax.annotation.Resource;

import org.dlut.mycloudserver.client.common.MyCloudResult;
import org.dlut.mycloudserver.client.common.Pagination;
import org.dlut.mycloudserver.client.common.performancemonitor.PerformanceMonitorDTO;
import org.dlut.mycloudserver.client.common.performancemonitor.QueryPerformanceMonitorCondition;
import org.dlut.mycloudserver.client.service.performancemonitor.IPerformanceMonitorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 类PerformanceMonitorBiz.java的实现描述：TODO 类实现描述
 * 
 * @author luojie 2015年4月28日 下午2:46:37
 */
@Service
public class PerformanceMonitorBiz {

    private static Logger              log = LoggerFactory.getLogger(PerformanceMonitorBiz.class);

    @Resource(name = "performanceMonitorService")
    private IPerformanceMonitorService performanceMonitorService;

    public PerformanceMonitorDTO getPerformanceMonitorById(int id) {
        MyCloudResult<PerformanceMonitorDTO> res = performanceMonitorService.getPerformanceMonitorById(id);
        if (!res.isSuccess()) {
            log.error("调用performanceMonitorService.getPerformanceMonitorById()出错：原因：" + res.getMsgInfo());
            return null;
        }

        return res.getModel();
    }

    public int createPerformanceMonitor(PerformanceMonitorDTO performanceMonitorDTO) {
        MyCloudResult<Integer> res = performanceMonitorService.createPerformanceMonitor(performanceMonitorDTO);
        if (!res.isSuccess()) {
            log.error("调用performanceMonitorService.createPerformanceMonitor()出错，原因：" + res.getMsgInfo());
            return 0;
        }
        return res.getModel();
    }

    public boolean updatePerformanceMonitor(PerformanceMonitorDTO performanceMonitorDTO) {
        MyCloudResult<Boolean> res = performanceMonitorService.updatePerformanceMonitor(performanceMonitorDTO);
        if (!res.isSuccess()) {
            log.error("调用performanceMonitorService.updatePerformanceMonitor()失败，原因：" + res.getMsgInfo());
            return false;
        }
        return res.getModel();
    }

    public boolean deletePerformanceMonitor(int id) {
        MyCloudResult<Boolean> res = performanceMonitorService.deletePerformanceMonitor(id);
        if (!res.isSuccess()) {
            log.error("调用performanceMonitorService.deletePerformanceMonitor()失败，原因：" + res.getMsgInfo());
            return false;
        }
        return res.getModel();
    }

    public int countQuery(QueryPerformanceMonitorCondition queryPerformanceMonitorCondition) {
        MyCloudResult<Integer> res = performanceMonitorService.countQuery(queryPerformanceMonitorCondition);
        if (!res.isSuccess()) {
            log.error("调用performanceMonitorService.countQuery()失败，原因：" + res.getMsgInfo());
            return 0;
        }
        return res.getModel();
    }

    public Pagination<PerformanceMonitorDTO> query(QueryPerformanceMonitorCondition queryPerformanceMonitorCondition) {
        MyCloudResult<Pagination<PerformanceMonitorDTO>> res = performanceMonitorService
                .query(queryPerformanceMonitorCondition);
        if (!res.isSuccess()) {
            log.error("调用performanceMonitorService.query()失败，原因：" + res.getMsgInfo());
            return null;
        }
        return res.getModel();
    }
}
