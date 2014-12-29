/*
 * Copyright 2014 etao.com All right reserved. This software is the
 * confidential and proprietary information of etao.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with etao.com .
 */
package org.dlut.mycloudmanage.common.obj;

import org.dlut.mycloudserver.client.common.vmmanage.NetworkTypeEnum;
import org.dlut.mycloudserver.client.common.vmmanage.ShowTypeEnum;
import org.dlut.mycloudserver.client.common.vmmanage.VmStatusEnum;

/**
 * 类VmVO.java的实现描述：TODO 类实现描述
 * 
 * @author xuyizhen Dec 12, 2014 3:46:36 AM
 */
public class VmVO {
    private String          vmName;
    private String          vmClass;
    private VmStatusEnum    vmStatus;
    private String          hostIp;
    private String          showPort;
    private ShowTypeEnum    showType;
    private String          vmUuid;
    private Integer         vmVcpu;
    private String          vmMemory;     // 单位MB
    private String          vmDesc;
    private String          vmPass;
    private String          vmMacAddress;

    private NetworkTypeEnum vmNetworkType;

    public String getVmPass() {
        return vmPass;
    }

    public void setVmPass(String vmPass) {
        this.vmPass = vmPass;
    }

    public String getVmDesc() {
        return vmDesc;
    }

    public void setVmDesc(String vmDesc) {
        this.vmDesc = vmDesc;
    }

    public Integer getVmVcpu() {
        return vmVcpu;
    }

    public void setVmVcpu(Integer vmVcpu) {
        this.vmVcpu = vmVcpu;
    }

    public String getVmMemory() {
        return vmMemory;
    }

    public void setVmMemory(String vmMemory) {
        this.vmMemory = vmMemory;
    }

    public String getVmUuid() {
        return vmUuid;
    }

    public void setVmUuid(String vmUuid) {
        this.vmUuid = vmUuid;
    }

    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    public String getVmClass() {
        return vmClass;
    }

    public void setVmClass(String vmClass) {
        this.vmClass = vmClass;
    }

    public VmStatusEnum getVmStatus() {
        return vmStatus;
    }

    public void setVmStatus(VmStatusEnum vmStatus) {
        this.vmStatus = vmStatus;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public String getShowPort() {
        return showPort;
    }

    public void setShowPort(String showPort) {
        this.showPort = showPort;
    }

    public ShowTypeEnum getShowType() {
        return showType;
    }

    public void setShowType(ShowTypeEnum showType) {
        this.showType = showType;
    }

    public String getVmMacAddress() {
        return vmMacAddress;
    }

    public void setVmMacAddress(String vmMacAddress) {
        this.vmMacAddress = vmMacAddress;
    }

    public NetworkTypeEnum getVmNetworkType() {
        return vmNetworkType;
    }

    public void setVmNetworkType(NetworkTypeEnum vmNetworkType) {
        this.vmNetworkType = vmNetworkType;
    }

}
