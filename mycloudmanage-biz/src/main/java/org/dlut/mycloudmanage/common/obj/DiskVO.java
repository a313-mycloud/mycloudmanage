/*
 * Copyright 2015 mycloud All right reserved. This software is the
 * confidential and proprietary information of etao.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with etao.com .
 */
package org.dlut.mycloudmanage.common.obj;

import org.dlut.mycloudserver.client.common.storemanage.StoreFormat;
import org.dlut.mycloudserver.client.common.vmmanage.MasterDiskBusTypeEnum;

/**
 * 类DiskVO.java的实现描述：TODO 类实现描述
 * 
 * @author xuyizhen 2015年1月12日 下午2:12:28
 */
public class DiskVO {
    /**
     * 硬盘uuid
     */
    private String                diskUuid;

    /**
     * 硬盘名称
     */
    private String                diskName;

    /**
     * 硬盘的总大小MB
     */
    private String                diskTotalSize;

    /**
     * 硬盘已使用的大小
     */
    private String                diskUsedSize;

    /**
     * 硬盘路径
     */
    private String                diskPath;

    /**
     * 硬盘镜像格式
     */
    private StoreFormat           diskFormat;

    private MasterDiskBusTypeEnum diskBusType;

    /**
     * 所挂载的虚拟机名称
     */
    private String                vmName;

    private String                diskDesc;

    public String getDiskUuid() {
        return diskUuid;
    }

    public void setDiskUuid(String diskUuid) {
        this.diskUuid = diskUuid;
    }

    public String getDiskName() {
        return diskName;
    }

    public void setDiskName(String diskName) {
        this.diskName = diskName;
    }

    public String getDiskTotalSize() {
        return diskTotalSize;
    }

    public void setDiskTotalSize(String diskTotalSize) {
        this.diskTotalSize = diskTotalSize;
    }

    public String getDiskUsedSize() {
        return diskUsedSize;
    }

    public void setDiskUsedSize(String diskUsedSize) {
        this.diskUsedSize = diskUsedSize;
    }

    public String getDiskPath() {
        return diskPath;
    }

    public void setDiskPath(String diskPath) {
        this.diskPath = diskPath;
    }

    public StoreFormat getDiskFormat() {
        return diskFormat;
    }

    public void setDiskFormat(StoreFormat diskFormat) {
        this.diskFormat = diskFormat;
    }

    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    public String getDiskDesc() {
        return diskDesc;
    }

    public void setDiskDesc(String diskDesc) {
        this.diskDesc = diskDesc;
    }

    public MasterDiskBusTypeEnum getDiskBusType() {
        return diskBusType;
    }

    public void setDiskBusType(MasterDiskBusTypeEnum diskBusType) {
        this.diskBusType = diskBusType;
    }

}
