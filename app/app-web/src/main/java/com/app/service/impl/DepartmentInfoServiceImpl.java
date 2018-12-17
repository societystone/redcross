package com.app.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.bean.PageResultBean;
import com.app.common.Constants;
import com.app.dao.DepartmentInfoDAO;
import com.app.dao.SysRelationDAO;
import com.app.entity.DepartmentInfo;
import com.app.entity.SysRelation;
import com.app.service.DepartmentInfoService;
import com.app.util.CollectionUtils;
import com.app.util.PageUtils;
import com.github.pagehelper.PageHelper;

/**
 * 部门接口实现类 . <br>
 * 
 * @author wangtw <br>
 */
@Service
public class DepartmentInfoServiceImpl implements DepartmentInfoService {

    /**
     * 注入角色dao
     */
    @Autowired
    private DepartmentInfoDAO departmentInfoDAO;
    
    /**
     * 注入系统关联dao
     */
    @Autowired
    private SysRelationDAO sysRelationDAO;

    @Transactional(rollbackFor = Exception.class)
	@Override
	public Long insertDepartmentInfo(DepartmentInfo departmentInfo) {
		// TODO Auto-generated method stub
		departmentInfo.setStatus(Constants.ENABLE_VALUE);
		departmentInfo.setCreateDate(new Date());
		departmentInfoDAO.insert(departmentInfo);
        Long departmentId = departmentInfo.getId();
    	SysRelation sysRelation = new SysRelation();
    	sysRelation.setRelationType(Constants.SYS_RELATION_TYPE.C.toString());
    	sysRelation.setMainPrimaryId(departmentId);
        for(Long roleId : departmentInfo.getRoles()) {
        	sysRelation.setRelPrimaryId(roleId);
        	sysRelationDAO.insert(sysRelation);
        }
        return departmentId;
	}

    @Transactional(rollbackFor = Exception.class)
	@Override
	public Long updateDepartmentInfoById(DepartmentInfo departmentInfo) {
		// TODO Auto-generated method stub
		departmentInfo.setModifyDate(new Date());
		departmentInfoDAO.update(departmentInfo);
        Long departmentId = departmentInfo.getId();
    	SysRelation sysRelation = new SysRelation();
    	sysRelation.setRelationType(Constants.SYS_RELATION_TYPE.C.toString());
    	sysRelation.setMainPrimaryId(departmentId);
    	sysRelationDAO.delete(sysRelation);
        for(Long roleId : departmentInfo.getRoles()) {
        	sysRelation.setRelPrimaryId(roleId);
        	sysRelationDAO.insert(sysRelation);
        }
        return departmentId;
	}

	@Override
	public Boolean deleteDepartmentInfoById(Long id) {
		// TODO Auto-generated method stub
		DepartmentInfo departmentInfo = new DepartmentInfo();
		departmentInfo.setId(id);
		departmentInfo.setStatus(Constants.DISABLE_VALUE);
		departmentInfo.setModifyDate(new Date());
		long result = departmentInfoDAO.update(departmentInfo);
        return result > 0 ? true : false;
	}

	@Override
	public DepartmentInfo selectDepartmentInfoById(Long id) {
		// TODO Auto-generated method stub
		DepartmentInfo departmentInfo = departmentInfoDAO.selectByPrimaryKey(id);
    	SysRelation sysRelation = new SysRelation();
		sysRelation.setRelationType(Constants.SYS_RELATION_TYPE.C.toString());
		sysRelation.setMainPrimaryId(id);
		List<SysRelation> srs = sysRelationDAO.selectList(sysRelation);
		if(CollectionUtils.isNotEmpty(srs)) {
			List<Long> roles = new ArrayList<Long>();
			for(SysRelation sr : srs) {
				roles.add(sr.getRelPrimaryId());
			}
			departmentInfo.setRoles(roles);
		}
		return departmentInfo;
	}

	@Override
	public PageResultBean<DepartmentInfo> selectDepartmentInfoByPage(DepartmentInfo departmentInfo) {
		PageHelper.startPage(PageUtils.getPageNum(), PageUtils.getPageSize());
		PageResultBean<DepartmentInfo> pages = new PageResultBean<DepartmentInfo>(departmentInfoDAO.selectList(departmentInfo));
		List<DepartmentInfo> departmentInfos = pages.getRows();
		if(CollectionUtils.isNotEmpty(departmentInfos)) {
			List<DepartmentInfo> departments = new ArrayList<DepartmentInfo>();
			for(DepartmentInfo department : departmentInfos) {
				SysRelation sysRelation = new SysRelation();
				sysRelation.setRelationType(Constants.SYS_RELATION_TYPE.C.toString());
				sysRelation.setMainPrimaryId(department.getId());
				List<SysRelation> srs = sysRelationDAO.selectList(sysRelation);
				if(CollectionUtils.isNotEmpty(srs)) {
					List<Long> roles = new ArrayList<Long>();
					for(SysRelation sr : srs) {
						roles.add(sr.getRelPrimaryId());
					}
					department.setRoles(roles);
				}
				departments.add(department);
			}
			pages.setRows(departments);
		}
		return pages;
	}

	@Override
	public List<DepartmentInfo> selectDepartmentInfoList(DepartmentInfo departmentInfo) {
		// TODO Auto-generated method stub
		return departmentInfoDAO.selectList(departmentInfo);
	}

    

}