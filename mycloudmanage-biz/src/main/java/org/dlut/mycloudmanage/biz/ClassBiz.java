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
import org.dlut.mycloudserver.client.common.classmanage.ClassDTO;
import org.dlut.mycloudserver.client.common.classmanage.QueryClassCondition;
import org.dlut.mycloudserver.client.common.usermanage.UserDTO;
import org.dlut.mycloudserver.client.common.vmmanage.VmDTO;
import org.dlut.mycloudserver.client.service.classmanage.IClassManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 类ClassBiz.java的实现描述：TODO 类实现描述
 * 
 * @author xuyizhen Dec 11, 2014 4:27:41 PM
 */
@Service
public class ClassBiz {
    private static Logger       log = LoggerFactory.getLogger(ClassBiz.class);

    @Resource(name = "classManageService")
    private IClassManageService classManageService;

    /**
     * 根据id获取课程信息
     * 
     * @param classId
     * @return
     */
    public ClassDTO getClassById(int classId) {
        MyCloudResult<ClassDTO> result = this.classManageService.getClassById(classId);
        if (!result.isSuccess()) {
            log.warn("调用classManageService.getClassById(" + classId + ")出错，" + result.getMsgCode() + ":"
                    + result.getMsgInfo());
            return null;
        }
        return result.getModel();
    }

    /**
     * 创建一个新的课程
     * 
     * @param classDTO
     * @return
     */
    public int createClass(ClassDTO classDTO) {
        MyCloudResult<Integer> result = this.classManageService.createClass(classDTO);
        if (!result.isSuccess()) {
            log.warn("调用classManageService.createClass()出错，" + result.getMsgCode() + ":" + result.getMsgInfo());
            return 0;
        }
        return result.getModel();
    }

    /**
     * 更新课程
     * 
     * @param classDTO
     * @return
     */
    public boolean updateClass(ClassDTO classDTO) {
        MyCloudResult<Boolean> result = this.classManageService.updateClass(classDTO);
        if (!result.isSuccess()) {
            log.warn("调用classManageService.updateClass()出错，" + result.getMsgCode() + ":" + result.getMsgInfo());
            return false;
        }
        return result.getModel();

    }

    /**
     * 删除课程
     * 
     * @param classDTO
     * @return
     */
    public boolean deleteClass(int classId) {
        MyCloudResult<Boolean> result = this.classManageService.deleteClass(classId);

        if (!result.isSuccess()) {
            log.warn("调用classManageService.deleteClass()出错，" + result.getMsgCode() + ":" + result.getMsgInfo());
            return false;
        }
        return result.getModel();
    }

    /**
     * 根据条件统计课程数量
     * 
     * @param queryClassCondition
     * @return
     */
    public int countQuery(QueryClassCondition queryClassCondition) {
        MyCloudResult<Integer> result = this.classManageService.countQuery(queryClassCondition);

        if (!result.isSuccess()) {
            log.warn("调用classManageService.countQuery()出错，" + result.getMsgCode() + ":" + result.getMsgInfo());
            return 0;
        }
        return result.getModel();
    }

    /**
     * 根据条件查询课程列表
     * 
     * @param queryClassCondition
     * @return
     */
    public Pagination<ClassDTO> query(QueryClassCondition queryClassCondition) {
        MyCloudResult<Pagination<ClassDTO>> result = this.classManageService.query(queryClassCondition);

        if (!result.isSuccess()) {
            log.warn("调用classManageService.query()出错，" + result.getMsgCode() + ":" + result.getMsgInfo());
            return null;
        }
        return result.getModel();
    }

    /**
     * 将一个学生添加到一门课程中
     * 
     * @param studentAccount
     * @param classId
     * @return
     */
    public boolean addStudentInOneClass(String studentAccount, int classId) {
        MyCloudResult<Boolean> result = this.classManageService.addStudentInOneClass(studentAccount, classId);
        if (!result.isSuccess()) {
            log.warn("调用classManageService.addStudentInOneClass()出错，" + result.getMsgCode() + ":" + result.getMsgInfo());
            return false;
        }
        return result.getModel();
    }

    /**
     * 删除一门课中的一个学生
     * 
     * @param studentAccount
     * @param classId
     * @return
     */
    public boolean deleteStudentInOneClass(String studentAccount, int classId) {
        MyCloudResult<Boolean> result = this.classManageService.deleteStudentInOneClass(studentAccount, classId);

        if (!result.isSuccess()) {
            log.warn("调用classManageService.deleteStudentInOneClass()出错，" + result.getMsgCode() + ":"
                    + result.getMsgInfo());
            return false;
        }
        return result.getModel();
    }

    /**
     * 删除一个学生的所有课程
     * 
     * @param studentAccount
     * @return
     */
    public boolean deleteStudentAllClass(String studentAccount) {
        MyCloudResult<Boolean> result = this.classManageService.deleteStudentAllClass(studentAccount);

        if (!result.isSuccess()) {
            log.warn("调用classManageService.deleteStudentAllClass()出错，" + result.getMsgCode() + ":"
                    + result.getMsgInfo());
            return false;
        }
        return result.getModel();
    }

    /**
     * 删除一门课下面的所有学生
     * 
     * @param classId
     * @return
     */
    public boolean deleteAllStudentInOneClass(int classId) {
        MyCloudResult<Boolean> result = this.classManageService.deleteAllStudentInOneClass(classId);
        if (!result.isSuccess()) {
            log.warn("调用classManageService.deleteAllStudentInOneClass()出错，" + result.getMsgCode() + ":"
                    + result.getMsgInfo());
            return false;
        }
        return result.getModel();
    }

    /**
     * 分页获取一个课程下的学生列表
     * 
     * @param classId
     * @param offset
     * @param limit
     * @return
     */
    public Pagination<UserDTO> getStudentsInOneClass(int classId, int offset, int limit) {
        MyCloudResult<Pagination<UserDTO>> result = this.classManageService.getStudentsInOneClass(classId, offset,
                limit);

        if (!result.isSuccess()) {
            log.warn("调用classManageService.getStudentInOneClass()出错，" + result.getMsgCode() + ":" + result.getMsgInfo());
            return null;
        }
        return result.getModel();
    }

    /**
     * 分页获取一个学生的课程列表
     * 
     * @param studentAccount
     * @param offset
     * @param limit
     * @return
     */
    public Pagination<ClassDTO> getClassesOfOneStudent(String studentAccount, int offset, int limit) {

        MyCloudResult<Pagination<ClassDTO>> result = this.classManageService.getClassesOfOneStudent(studentAccount,
                offset, limit);

        if (!result.isSuccess()) {
            log.warn("调用classManageService.getClassesOfOneStudent()出错，" + result.getMsgCode() + ":"
                    + result.getMsgInfo());
            return null;
        }
        return result.getModel();
    }

    /**
     * 将虚拟机模板添加到课程中
     * 
     * @param templateVmUuid
     * @param classId
     * @return
     */
    public boolean addTemplateVmToClass(String templateVmUuid, int classId) {
        MyCloudResult<Boolean> result = this.classManageService.addTemplateVmToClass(templateVmUuid, classId);

        if (!result.isSuccess()) {
            log.warn("调用classManageService.addTemplateVmToClass()出错，" + result.getMsgCode() + ":" + result.getMsgInfo());
            return false;
        }
        return result.getModel();
    }

    /**
     * 删除某个课程下的的某个虚拟机
     * 
     * @param classId
     * @param templateVmUuid
     * @return
     */
    public boolean deleteOneTemplateVmInOneClass(String templateVmUuid, int classId) {
        MyCloudResult<Boolean> result = this.classManageService.deleteOneTemplateVmInOneClass(templateVmUuid, classId);

        if (!result.isSuccess()) {
            log.warn("调用classManageService.deleteOneTemplateVmOneClass()出错，" + result.getMsgCode() + ":"
                    + result.getMsgInfo());
            return false;
        }
        return result.getModel();
    }

    /**
     * 删除某个课程下面的所有虚拟机
     * 
     * @param classId
     * @return
     */
    public boolean deleteAllTemplateVmInOneClass(int classId) {
        MyCloudResult<Boolean> result = this.classManageService.deleteAllTemplateVmInOneClass(classId);
        if (!result.isSuccess()) {
            log.warn("调用classManageService.deleteAllTemplateVmInOneClass()出错，" + result.getMsgCode() + ":"
                    + result.getMsgInfo());
            return false;
        }
        return result.getModel();
    }

    /**
     * 删除某个虚拟机镜像模板下面的所有虚拟机
     * 
     * @param templateVmUuid
     * @return
     */
    public boolean deleteAllClassWithTemplateVm(String templateVmUuid) {
        MyCloudResult<Boolean> result = this.classManageService.deleteAllClassWithTemplateVm(templateVmUuid);

        if (!result.isSuccess()) {
            log.warn("调用classManageService.deleteOneTemplateVmInOneClass()出错，" + result.getMsgCode() + ":"
                    + result.getMsgInfo());
            return false;
        }
        return result.getModel();
    }

    /**
     * 获取某个课程下面的所有模板虚拟机
     * 
     * @param classId
     * @param offset
     * @param limit
     * @return
     */
    public Pagination<VmDTO> getTemplateVmsInOneClass(int classId, int offset, int limit) {
        MyCloudResult<Pagination<VmDTO>> result = this.classManageService.getTemplateVmsInOneClass(classId, offset,
                limit);

        if (!result.isSuccess()) {
            log.warn("调用classManageService.getTemplateVmsInOneClass()出错，" + result.getMsgCode() + ":"
                    + result.getMsgInfo());
            return null;
        }
        return result.getModel();
    }

    /**
     * 获取某个虚拟机模板对应的所有课程
     * 
     * @param templateVmUuid
     * @param offset
     * @param limit
     * @return
     */
    public Pagination<ClassDTO> getClassesWithTemplateVm(String templateVmUuid, int offset, int limit) {
        MyCloudResult<Pagination<ClassDTO>> result = this.classManageService.getClassesWithTemplateVm(templateVmUuid,
                offset, limit);

        if (!result.isSuccess()) {
            log.warn("调用classManageService.getClassesWithTemplateVm()出错，" + result.getMsgCode() + ":"
                    + result.getMsgInfo());
            return null;
        }
        return result.getModel();
    }

    /**
     * 判断课程与模板虚拟机是否已经关联
     * 
     * @param classId
     * @param templateVmUuid
     * @return
     */
    public boolean isBind(int classId, String templateVmUuid) {
        MyCloudResult<Boolean> result = this.classManageService.isBind(classId, templateVmUuid);
        if (!result.isSuccess()) {
            log.warn("调用classManageService.isBind()出错，" + result.getMsgCode() + ":" + result.getMsgInfo());
            return false;
        }
        return result.getModel();
    }

}
