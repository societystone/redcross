package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.bean.PageResultBean;
import com.app.bean.ResultBean;
import com.app.entity.DepartmentInfo;
import com.app.service.DepartmentInfoService;

/**
 * 部门controller . <br>
 * 
 * @author wangtw <br>
 */
@RestController
public class DepartmentInfoController {

    /**
     * 注入部门接口
     */
    @Autowired
    private DepartmentInfoService departmentInfoService;

    /**
     * 添加部门
     * 
     * @param departmentInfo
     * @return
     */
    @PostMapping("/sys/department")
    public ResultBean<Long> insertDepartmentInfo(@RequestBody DepartmentInfo departmentInfo) {
        return new ResultBean<Long>(departmentInfoService.insertDepartmentInfo(departmentInfo));
    }

    /**
     * 通过id更新部门
     * 
     * @param DepartmentInfo
     * @return
     */
    @PutMapping("/sys/department")
    public ResultBean<Long> updateDepartmentInfoById(@RequestBody DepartmentInfo departmentInfo) {
        return new ResultBean<Long>(departmentInfoService.updateDepartmentInfoById(departmentInfo));
    }

    /**
     * 通过id删除部门
     * 
     * @param userId
     * @return
     */
    @DeleteMapping("/sys/department/{id}")
    public ResultBean<Boolean> deleteDepartmentInfoById(@PathVariable("id") Long id) {
        return new ResultBean<Boolean>(departmentInfoService.deleteDepartmentInfoById(id));
    }

    /**
     * 通过id获得部门
     * 
     * @param id
     * @return
     */
    @GetMapping("/sys/department/{id}")
    public ResultBean<DepartmentInfo> selectDepartmentInfoById(@PathVariable("id") Long id) {
        return new ResultBean<DepartmentInfo>(departmentInfoService.selectDepartmentInfoById(id));
    }

    /**
     * 分页查询部门
     * @param departmentInfo
     * @return
     */
    @PostMapping("/sys/department/list")
    public ResultBean<PageResultBean<DepartmentInfo>> selectDepartmentInfoByPage(@RequestBody DepartmentInfo departmentInfo) {
        return new ResultBean<PageResultBean<DepartmentInfo>>(departmentInfoService.selectDepartmentInfoByPage(departmentInfo));
    }

    /**
     * 不分页查询部门
     * @param departmentInfo
     * @return
     */
    @PostMapping("/sys/department/noPageList")
    public ResultBean<List<DepartmentInfo>> selectDepartmentInfoList(@RequestBody DepartmentInfo departmentInfo) {
        return new ResultBean<List<DepartmentInfo>>(departmentInfoService.selectDepartmentInfoList(departmentInfo));
    }

}
