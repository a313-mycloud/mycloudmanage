package org.dlut.mycloudmanage.biz;

import javax.annotation.Resource;

import org.dlut.mycloudserver.client.common.MyCloudResult;
import org.dlut.mycloudserver.client.common.Pagination;
import org.dlut.mycloudserver.client.common.hostmanage.HostDTO;
import org.dlut.mycloudserver.client.common.hostmanage.QueryHostCondition;
import org.dlut.mycloudserver.client.service.hostmanage.IHostManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
/**
 * 
 * 类HostBiz.java的实现描述：TODO 类实现描述 
 * @author xuyizhen Dec 7, 2014 10:19:44 AM
 */
@Service
public class HostBiz {
	private static Logger log = LoggerFactory.getLogger(HostBiz.class);

	@Resource(name = "hostManageService")
	private IHostManageService hostManageService;

	public HostDTO getHostById(int hostId) {
		MyCloudResult<HostDTO> result = this.hostManageService
				.getHostById(hostId);
		if (!result.isSuccess()) {
			log.warn("调用hostManageService.getHostById(" + hostId + ")出错，"
					+ result.getMsgCode() + ":" + result.getMsgInfo());
			return null;
		}
		return result.getModel();
	}

	public int createHost(HostDTO hostDTO) {
		MyCloudResult<Integer> result = this.hostManageService
				.createHost(hostDTO);
		if (!result.isSuccess()) {
			log.warn("掉用hostManagerService.createHost(" + hostDTO.getHostName()
					+ "," + hostDTO.getHostIp() + ")出错，" + result.getMsgCode()
					+ ":" + result.getMsgInfo());
			return 0;
		}
		return result.getModel();
	}

	public boolean updateHost(HostDTO hostDTO) {
		MyCloudResult<Boolean> result = this.hostManageService
				.updateHost(hostDTO);
		if (!result.isSuccess()) {
			log.warn("掉用hostManagerService.updateHost(" + hostDTO.getHostName()
					+ "," + hostDTO.getHostIp() + ")出错，" + result.getMsgCode()
					+ ":" + result.getMsgInfo());
			return false;
		}
		return result.getModel();
	}

	public boolean deleteHostById(int hostId) {
		MyCloudResult<Boolean> result = this.hostManageService
				.deleteHostById(hostId);
		if (!result.isSuccess()) {
			log.warn("掉用hostManagerService.deleteHostById(" + hostId + ")出错，"
					+ result.getMsgCode() + ":" + result.getMsgInfo());
			return false;
		}
		return result.getModel();
	}

	public int countQuery(QueryHostCondition queryHostCondition) {
		MyCloudResult<Integer> result = this.hostManageService
				.countQuery(queryHostCondition);
		if (!result.isSuccess()) {
			log.warn("调用hostManagerService.countQuery("
					+ queryHostCondition.getHostName() + ","
					+ queryHostCondition.getHostIp() + ")出错，"
					+ result.getMsgCode() + ":" + result.getMsgInfo());
			return 0;
		}
		return result.getModel();

	}

	public Pagination<HostDTO> query(QueryHostCondition queryHostCondition) {
		MyCloudResult<Pagination<HostDTO>> result = this.hostManageService
				.query(queryHostCondition);
		if (!result.isSuccess()) {
			log.warn("调用hostManagerService.query("
					+ queryHostCondition.getHostName() + ","
					+ queryHostCondition.getHostIp() + ")出错，"
					+ result.getMsgCode() + ":" + result.getMsgInfo());
			return null;
		}
		return result.getModel();
	}

}
