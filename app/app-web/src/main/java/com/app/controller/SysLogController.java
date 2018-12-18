package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.bean.PageResultBean;
import com.app.bean.ResultBean;
import com.app.dto.SysLogDTO;
import com.app.entity.SysLog;
import com.app.service.SysLogService;

/**
 * 系统日志controller . <br>
 * 
 * @author wangtw <br>
 */
@RestController
public class SysLogController {

	/**
	 * 注入系统日志接口
	 */
	@Autowired
	private SysLogService sysLogService;

	/**
	 * 通过id获得系统日志
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/sys/log/{id}")
	public ResultBean<SysLog> selectSysLogById(@PathVariable("id") Long id) {
		return new ResultBean<SysLog>(sysLogService.selectSysLogById(id));
	}

	/**
	 * 分页查询系统日志
	 * 
	 * @param sysLog
	 * @return
	 */
	@PostMapping("/sys/log/list")
	public ResultBean<PageResultBean<SysLog>> selectSysLogByPage(@RequestBody SysLogDTO sysLogDTO) {
		return new ResultBean<PageResultBean<SysLog>>(sysLogService.selectSysLogByPage(sysLogDTO));
	}

}
