package com.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.bean.ResultBean;
import com.app.dto.SysMenuDTO;
import com.app.entity.SysMenu;
import com.app.entity.SysUser;
import com.app.service.SysMenuService;
import com.app.util.UserUtils;

/**
 * 系统菜单controller . <br>
 * 
 * @author wangtw <br>
 */
@RestController
public class SysMenuController {

    /**
     * 注入菜单接口
     */
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 查询菜单
     * @param sysMenuDTO
     * @return
     */
    @GetMapping("/sys/menu/all")
    public ResultBean<List<SysMenuDTO>> selectAllList() {
    	List<SysMenuDTO> sysMenuDtos = null;
    	List<SysMenu> sysMenus = sysMenuService.selectListByParentIdAndUserId(null, null);
    	if(CollectionUtils.isNotEmpty(sysMenus)) {
    		sysMenuDtos = getSysMenuDTOs(sysMenus,Long.valueOf(-1));
    	}
        return new ResultBean<List<SysMenuDTO>>(sysMenuDtos);
    }

    /**
     * 查询菜单
     * @param sysMenuDTO
     * @return
     */
    @GetMapping("/sys/menu")
    public ResultBean<List<SysMenuDTO>> selectList() {
    	List<SysMenuDTO> sysMenuDtos = null;
    	SysUser su = (SysUser)UserUtils.getUser();
    	List<SysMenu> sysMenus = sysMenuService.selectListByParentIdAndUserId(null, su.getId());
    	if(CollectionUtils.isNotEmpty(sysMenus)) {
    		sysMenuDtos = getSysMenuDTOs(sysMenus,Long.valueOf(-1));
    	}
        return new ResultBean<List<SysMenuDTO>>(sysMenuDtos);
    }
    
    /**
     * 递归组装返回菜单
     * @param sysMenus
     * @param parentId
     * @return
     */
    private List<SysMenuDTO> getSysMenuDTOs(List<SysMenu> sysMenus, Long parentId) {
    	List<SysMenuDTO> smDtos = new ArrayList<SysMenuDTO>();
    	SysMenuDTO smDto = null;
    	for(SysMenu sysMenu : sysMenus) {
			if(sysMenu.getParentId()==parentId) {
	    		smDto = new SysMenuDTO();
	    		smDto.setId(sysMenu.getId());
				smDto.setName(sysMenu.getName());
				smDto.setUrl(sysMenu.getUrl());
				smDto.setIcon(sysMenu.getIcon());
				smDto.setChilds(getSysMenuDTOs(sysMenus, sysMenu.getId()));
				smDtos.add(smDto);
			}
		}
    	return smDtos.size()==0 ? null : smDtos;
    }

}
