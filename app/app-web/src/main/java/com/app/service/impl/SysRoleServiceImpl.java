package com.app.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.bean.PageResultBean;
import com.app.common.Constants;
import com.app.dao.local.SysRelationDAO;
import com.app.dao.local.SysRoleDAO;
import com.app.entity.SysRelation;
import com.app.entity.SysRole;
import com.app.service.SysRoleService;
import com.app.util.CollectionUtils;
import com.app.util.PageUtils;
import com.github.pagehelper.PageHelper;

/**
 * 角色接口实现类 . <br>
 * 
 * @author wangtw <br>
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    /**
     * 注入角色dao
     */
    @Autowired
    private SysRoleDAO sysRoleDAO;
    

    @Autowired
    private SysRelationDAO sysRelationDAO;

	@Override
	public Long insertSysRole(SysRole sysRole) {
		// TODO Auto-generated method stub
		sysRole.setStatus(Constants.ENABLE_VALUE);
		sysRole.setCreateDate(new Date());
		return sysRoleDAO.insert(sysRole);
	}

	@Override
	public Long updateSysRoleById(SysRole sysRole) {
		// TODO Auto-generated method stub
		sysRole.setModifyDate(new Date());
		return sysRoleDAO.update(sysRole);
	}

	@Override
	public Boolean deleteSysRoleById(Long id) {
		// TODO Auto-generated method stub
		SysRole sysRole = new SysRole();
		sysRole.setId(id);
		sysRole.setStatus(Constants.DISABLE_VALUE);
		sysRole.setModifyDate(new Date());
		long result = sysRoleDAO.update(sysRole);
        return result > 0 ? true : false;
	}

	@Override
	public SysRole selectSysRoleById(Long id) {
		// TODO Auto-generated method stub
		return (SysRole) sysRoleDAO.selectByPrimaryKey(id);
	}

	@Override
	public PageResultBean<SysRole> selectSysRoleByPage(SysRole sysRole) {
		PageHelper.startPage(PageUtils.getPageNum(), PageUtils.getPageSize());
        return new PageResultBean<SysRole>(sysRoleDAO.selectList(sysRole));
	}

	@Override
	public List<SysRole> selectSysRoleList(SysRole sysRole) {
        return sysRoleDAO.selectList(sysRole);
	}

	@Override
	public List<Long> selectMenuIdByRoleId(Long roleId) {
		// TODO Auto-generated method stub
		List<Long> menuIds = null;
		SysRelation sysRelation = new SysRelation();
		sysRelation.setRelationType(Constants.SYS_RELATION_TYPE.B.toString());
		sysRelation.setMainPrimaryId(roleId);
		List<SysRelation> sysRelations = sysRelationDAO.selectList(sysRelation);
		if(CollectionUtils.isNotEmpty(sysRelations)) {
			menuIds = new ArrayList<Long>();
			for(SysRelation sr : sysRelations) {
				menuIds.add(sr.getRelPrimaryId());
			}
		}
		return menuIds;
	}

    @Transactional(rollbackFor = Exception.class)
	@Override
	public Long updateMenuIdByRoleId(Long roleId, List<Long> menuIds) {
		// TODO Auto-generated method stub
		SysRelation sysRelation = new SysRelation();
		sysRelation.setRelationType(Constants.SYS_RELATION_TYPE.B.toString());
		sysRelation.setMainPrimaryId(roleId);
		sysRelationDAO.delete(sysRelation);
		for(Long menuId : menuIds) {
			sysRelation.setRelPrimaryId(menuId);
			sysRelationDAO.insert(sysRelation);
		}
		return Long.valueOf(1);
	}

    

}