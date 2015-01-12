/*
 * Copyright 2015 mycloud All right reserved. This software is the
 * confidential and proprietary information of etao.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with etao.com .
 */
package org.dlut.mycloudmanage.biz;

import javax.annotation.Resource;

import org.dlut.mycloudserver.client.common.MyCloudResult;
import org.dlut.mycloudserver.client.common.Pagination;
import org.dlut.mycloudserver.client.common.storemanage.DiskDTO;
import org.dlut.mycloudserver.client.common.storemanage.QueryDiskCondition;
import org.dlut.mycloudserver.client.service.storemanage.IDiskManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 类DiskBiz.java的实现描述：TODO 类实现描述
 * 
 * @author xuyizhen 2015年1月12日 下午1:36:59
 */
@Service
public class DiskBiz {
    private static Logger      log = LoggerFactory.getLogger(DiskBiz.class);
    @Resource(name = "diskManageService")
    private IDiskManageService diskManageService;

    /**
     * @param diskUuid
     * @return
     */
    public DiskDTO getDiskByUuid(String diskUuid) {
        MyCloudResult<DiskDTO> result = this.diskManageService.getDiskByUuid(diskUuid);
        if (!result.isSuccess()) {
            log.warn("调用diskManageService.getDiskByUuid()出错，" + result.getMsgCode() + ":" + result.getMsgInfo());
            return null;
        }
        return result.getModel();
    }

    /**
     * @param diskDTO
     * @return
     */
    public String createDisk(DiskDTO diskDTO) {
        MyCloudResult<String> result = this.diskManageService.createDisk(diskDTO);
        if (!result.isSuccess()) {
            log.warn("调用diskManageService.createDisk()出错");
            return null;
        }
        return result.getModel();
    }

    /**
     * @param diskDTO
     * @return
     */
    public boolean updateDisk(DiskDTO diskDTO) {
        MyCloudResult<Boolean> result = this.diskManageService.updateDisk(diskDTO);
        if (!result.isSuccess()) {
            log.warn("调用diskManageService.updateDisk()出错");
            return false;
        }
        return result.getModel();
    }

    /**
     * @param diskUuid
     * @return
     */
    public boolean deleteDiskByUuid(String diskUuid) {
        MyCloudResult<Boolean> result = this.diskManageService.deleteDiskByUuid(diskUuid);
        if (!result.isSuccess()) {
            log.warn("调用diskManageService.deleteDiskByUuid()出错");
            return false;
        }
        return result.getModel();
    }

    /**
     * @param queryDiskCondition
     * @return
     */
    public int countQuery(QueryDiskCondition queryDiskCondition) {
        MyCloudResult<Integer> result = this.diskManageService.countQuery(queryDiskCondition);
        if (!result.isSuccess()) {
            log.warn("调用diskManageService.countQuery()出错");
            return 0;
        }
        return result.getModel();
    }

    /**
     * @param queryDiskCondition
     * @return
     */
    public Pagination<DiskDTO> query(QueryDiskCondition queryDiskCondition) {
        MyCloudResult<Pagination<DiskDTO>> result = this.diskManageService.query(queryDiskCondition);
        if (!result.isSuccess()) {
            log.warn("调用diskManageService.query()出错");
            return null;
        }
        return result.getModel();
    }

}
