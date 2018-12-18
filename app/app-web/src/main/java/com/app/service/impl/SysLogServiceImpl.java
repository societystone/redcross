package com.app.service.impl;

import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.bean.PageResultBean;
import com.app.dao.local.SysLogDAO;
import com.app.dto.SysLogDTO;
import com.app.entity.SysLog;
import com.app.service.SysLogService;
import com.app.util.PageUtils;
import com.github.pagehelper.PageHelper;

/**
 * 系统日志接口实现类 . <br>
 * 
 * @author wangtw <br>
 */
@Service
public class SysLogServiceImpl implements SysLogService {

	/**
	 * 注入系统系统日志dao
	 */
	@Autowired
	private SysLogDAO sysLogDAO;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Long insertSysLog(SysLog sysLog) {
		sysLog.setCreateDate(new Date());
		sysLogDAO.insert(sysLog);
		return sysLog.getId();
	}

	@Override
	public SysLog selectSysLogById(Long id) {
		// TODO Auto-generated method stub
		return sysLogDAO.selectByPrimaryKey(id);
	}

	@Override
	public PageResultBean<SysLog> selectSysLogByPage(SysLogDTO sysLogDTO) {
		// TODO Auto-generated method stub
		PageHelper.startPage(PageUtils.getPageNum(), PageUtils.getPageSize());
		HashMap<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("username", sysLogDTO.getUsername());
		queryMap.put("beginDate", sysLogDTO.getBeginDate());
		queryMap.put("endDate", sysLogDTO.getEndDate());
		return new PageResultBean<SysLog>(sysLogDAO.selectAll(queryMap));
	}

}