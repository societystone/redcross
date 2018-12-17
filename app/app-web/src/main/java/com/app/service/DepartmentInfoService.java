package com.app.service;

import java.util.List;

import com.app.bean.PageResultBean;
import com.app.entity.DepartmentInfo;

/**
 * 部门接口 . <br>
 * 
 * @author wangtw <br>
 */
public interface DepartmentInfoService {

    /**
     * 插入部门到数据库
     * 
     * @param DepartmentInfo
     * @return
     */
    Long insertDepartmentInfo(DepartmentInfo departmentInfo);

    /**
     * 通过id更新部门
     * 
     * @param DepartmentInfo
     * @return
     */
    Long updateDepartmentInfoById(DepartmentInfo departmentInfo);

    /**
     * 通过id删除部门
     * 
     * @param id
     * @return
     */
    Boolean deleteDepartmentInfoById(Long id);

    /**
     * 通过id获得部门
     * 
     * @param id
     * @return
     */
    DepartmentInfo selectDepartmentInfoById(Long id);

    /**
     * 分页查询部门
     * 
     * @param departmentInfoDTO
     * @return
     */
    PageResultBean<DepartmentInfo> selectDepartmentInfoByPage(DepartmentInfo departmentInfo);
    
    /**
     * 不分页查询部门
     * @param departmentInfo
     * @return
     */
    List<DepartmentInfo> selectDepartmentInfoList(DepartmentInfo departmentInfo);

}