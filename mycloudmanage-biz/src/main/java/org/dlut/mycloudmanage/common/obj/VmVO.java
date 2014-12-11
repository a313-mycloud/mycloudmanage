/*
 * Copyright 2014 etao.com All right reserved. This software is the
 * confidential and proprietary information of etao.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with etao.com .
 */
package org.dlut.mycloudmanage.common.obj;

/**
 * 类VmVO.java的实现描述：TODO 类实现描述
 * 
 * @author xuyizhen Dec 12, 2014 3:46:36 AM
 */
public class VmVO {
	private String vmName;
	private String vmClass;
	private String vmStatus;
	private String hostIp;
	private String showPort;
	private String showType;
	private String vmUuid;

	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
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

	public String getVmStatus() {
		return vmStatus;
	}

	public void setVmStatus(String vmStatus) {
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

}
