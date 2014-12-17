/*
 * Copyright 2014 etao.com All right reserved. This software is the
 * confidential and proprietary information of etao.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with etao.com .
 */
package org.dlut.mycloudmanage.biz;

import javax.annotation.Resource;

import org.dlut.mycloudserver.client.common.MyCloudResult;
import org.dlut.mycloudserver.client.common.Pagination;
import org.dlut.mycloudserver.client.common.storemanage.ImageDTO;
import org.dlut.mycloudserver.client.common.storemanage.QueryImageCondition;
import org.dlut.mycloudserver.client.service.storemanage.IImageManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 
 * 类ImageBiz.java的实现描述：TODO 类实现描述
 * 
 * @author xuyizhen Dec 17, 2014 2:37:24 PM
 */
@Service
public class ImageBiz {

	private static Logger log = LoggerFactory.getLogger(ImageBiz.class);

	@Resource(name = "imageManageService")
	private IImageManageService imageManageService;

	/**
	 * 根据镜像的uuid获取镜像信息
	 * 
	 * @param imageUuid
	 * @return
	 */
	public ImageDTO getImageByUuid(String imageUuid,
			boolean isIncludDeletedImage) {
		MyCloudResult<ImageDTO> result = this.imageManageService
				.getImageByUuid(imageUuid, isIncludDeletedImage);
		if (!result.isSuccess()) {
			log.warn("调用IImageManage.getImageByUuid()出错");
			return null;
		}
		return result.getModel();

	}

	/**
	 * 创建一个新的镜像，只需要设置imageName、imageUuid、imagePath以及isTemplate这几个属性即可
	 * 
	 * @param imageDTO
	 * @return
	 */
	public boolean createImage(ImageDTO imageDTO) {
		MyCloudResult<Boolean> result = this.imageManageService
				.createImage(imageDTO);
		if (!result.isSuccess()) {
			log.warn("调用ImageManageService.createImage()出错");
			return false;
		}
		return result.getModel();
	}

	/**
	 * 更新一个镜像
	 * 
	 * @param imageDTO
	 * @return
	 */
	public boolean updateImage(ImageDTO imageDTO) {
		MyCloudResult<Boolean> result = this.imageManageService
				.updateImage(imageDTO);
		if (!result.isSuccess()) {
			log.warn("掉用imageManageService.updateImage()出错");
			return false;
		}
		return result.getModel();
	}

	/**
	 * 快速克隆一个虚拟机镜像
	 * 
	 * @param srcImageUuid
	 * @param destImageName
	 * @param isTemplate
	 *            是否作为模板
	 * @return 克隆后的虚拟机镜像信息
	 */
	public ImageDTO cloneImage(String srcImageUuid, String destImageName,
			boolean isTemplate) {
		MyCloudResult<ImageDTO> result = this.imageManageService.cloneImage(
				srcImageUuid, destImageName, isTemplate);
		if (!result.isSuccess()) {
			log.warn("调用imageManageService.cloneImage()出错");
			return null;
		}
		return result.getModel();
	}

	/**
	 * 根据uuid删除镜像
	 * 
	 * @param imageUuid
	 * @return
	 */
	public boolean deleteImageByUuid(String imageUuid) {
		MyCloudResult<Boolean> result = this.imageManageService
				.deleteImageByUuid(imageUuid);
		if (!result.isSuccess()) {
			log.warn("调用imageManageService.deleteImageByUuid()出错");
			return false;
		}
		return result.getModel();

	}

	/**
	 * 根据查询条件统计数量
	 * 
	 * @param queryImageCondition
	 * @return
	 */
	public int countQuery(QueryImageCondition queryImageCondition) {
		MyCloudResult<Integer> result = this.imageManageService
				.countQuery(queryImageCondition);
		if (!result.isSuccess()) {
			log.warn("调用imageManageService.countQuery()出错");
			return 0;
		}
		return result.getModel();
	}

	/**
	 * 根据查询条件分页获取镜像列表
	 * 
	 * @param queryImageCondition
	 * @return
	 */
	public Pagination<ImageDTO> query(QueryImageCondition queryImageCondition) {
		MyCloudResult<Pagination<ImageDTO>> result = this.imageManageService
				.query(queryImageCondition);
		if (!result.isSuccess()) {
			log.warn("调用imageManageService.query()出错");
			return null;
		}
		return result.getModel();
	}
}
