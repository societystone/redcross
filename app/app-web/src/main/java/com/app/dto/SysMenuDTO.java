package com.app.dto;

import java.util.List;

import lombok.Data;

/**
 * 系统菜单DTO . <br>
 * 
 * @author wangtw
 *
 */
@Data
public class SysMenuDTO {

	private Long id;//菜单id
	private String name;//菜单名称
	private String url;//链接地址
	private String icon;//图标
	
	private List<SysMenuDTO> childs;

}
