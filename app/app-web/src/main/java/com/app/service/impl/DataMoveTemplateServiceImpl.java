package com.app.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.bean.PageResultBean;
import com.app.common.Constants;
import com.app.dao.local.DataMoveTemplateDAO;
import com.app.dao.local.DataMoveTemplateDetailDAO;
import com.app.entity.DataMoveTemplate;
import com.app.entity.DataMoveTemplateDetail;
import com.app.service.DataMoveTemplateService;
import com.app.util.Emptys;
import com.app.util.ExceptionUtil;
import com.app.util.PageUtils;
import com.github.pagehelper.PageHelper;

/**
 * 数据迁移模板接口实现类 . <br>
 * 
 * @author wangtw <br>
 */
@Service
public class DataMoveTemplateServiceImpl implements DataMoveTemplateService {

	/**
	 * 注入dao
	 */
	@Autowired
	private DataMoveTemplateDAO dataMoveTemplateDAO;
	@Autowired
	private DataMoveTemplateDetailDAO dataMoveTemplateDetailDAO;

	@Override
	public List<DataMoveTemplate> selectTemplateList(DataMoveTemplate dataMoveTemplate) {
		// TODO Auto-generated method stub
		HashMap<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("name", dataMoveTemplate.getName());
		queryMap.put("status", dataMoveTemplate.getStatus());
		queryMap.put("isUse", dataMoveTemplate.getIsUse());
		return dataMoveTemplateDAO.selectList(queryMap);
	}

	@Override
	public DataMoveTemplate selectTemplateDetail(Long id) {
		// TODO Auto-generated method stub
		ExceptionUtil.throwEmptyCheckException(id, "模板ID为空");
		DataMoveTemplate dataMoveTemplate = dataMoveTemplateDAO.selectByPrimaryKey(id);
		ExceptionUtil.throwEmptyCheckException(dataMoveTemplate, "模板不存在");
		HashMap<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("templateId", id);
		dataMoveTemplate.setTemplateDetails(dataMoveTemplateDetailDAO.selectList(queryMap));
		return dataMoveTemplate;
	}

	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	@Override
	public Long insertTemplateInfo(DataMoveTemplate dataMoveTemplate) {
		// TODO Auto-generated method stub
		ExceptionUtil.throwEmptyCheckException(dataMoveTemplate, "模板参数为空");
		List<DataMoveTemplateDetail> dataMoveTemplateDetails = dataMoveTemplate.getTemplateDetails();
		ExceptionUtil.throwEmptyCheckException(dataMoveTemplateDetails, "模板详情参数为空");
		List<DataMoveTemplateDetail> lists = new ArrayList<DataMoveTemplateDetail>();
		for (DataMoveTemplateDetail dataMoveTemplateDetail : dataMoveTemplateDetails) {
			dataMoveTemplateDetail.setTemplateId(dataMoveTemplate.getId());
			lists.add(dataMoveTemplateDetail);
		}
		dataMoveTemplateDetailDAO.insertBatch(lists);
		dataMoveTemplate.setStatus(Constants.SYS_STATUS.START_USE.getValue());
		dataMoveTemplate.setIsUse(Constants.SYS_YES_NO.NO.getValue());
		dataMoveTemplate.setCreateDate(new Date());
		return dataMoveTemplateDAO.insert(dataMoveTemplate);
	}

	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	@Override
	public void updateTemplateInfo(DataMoveTemplate dataMoveTemplate) {
		// TODO Auto-generated method stub
		ExceptionUtil.throwEmptyCheckException(dataMoveTemplate, "模板参数为空");
		Long id = dataMoveTemplate.getId();
		DataMoveTemplate dmt = dataMoveTemplateDAO.selectByPrimaryKey(id);
		ExceptionUtil.throwEmptyCheckException(dmt, "模板不存在");
		if (Constants.SYS_YES_NO.YES.getValue().equals(dmt.getIsUse())) {
			ExceptionUtil.throwCheckException("模板已被使用，不允许修改");
		}
		List<DataMoveTemplateDetail> dataMoveTemplateDetails = dataMoveTemplate.getTemplateDetails();
		if (Emptys.isNotEmpty(dataMoveTemplateDetails)) {
			dataMoveTemplateDetailDAO.deleteByTemplateId(id);
			List<DataMoveTemplateDetail> lists = new ArrayList<DataMoveTemplateDetail>();
			for (DataMoveTemplateDetail dataMoveTemplateDetail : dataMoveTemplateDetails) {
				dataMoveTemplateDetail.setTemplateId(id);
				lists.add(dataMoveTemplateDetail);
			}
			dataMoveTemplateDetailDAO.insertBatch(lists);
		}
		dmt.setName(dataMoveTemplate.getName());
		dmt.setModifyDate(new Date());
		dataMoveTemplateDAO.update(dmt);
	}

	@Override
	public void delteTemplateInfo(Long id) {
		// TODO Auto-generated method stub
		ExceptionUtil.throwEmptyCheckException(id, "模板ID为空");
		DataMoveTemplate dataMoveTemplate = dataMoveTemplateDAO.selectByPrimaryKey(id);
		ExceptionUtil.throwEmptyCheckException(dataMoveTemplate, "模板不存在");
		if (Constants.SYS_YES_NO.YES.getValue().equals(dataMoveTemplate.getIsUse())) {
			ExceptionUtil.throwCheckException("模板已被使用，不允许删除");
		}
		dataMoveTemplate.setStatus(Constants.SYS_STATUS.REMOVE.getValue());
		dataMoveTemplate.setModifyDate(new Date());
		dataMoveTemplateDAO.update(dataMoveTemplate);
	}

	@Override
	public void startUseTemplateInfo(Long id) {
		// TODO Auto-generated method stub
		ExceptionUtil.throwEmptyCheckException(id, "模板ID为空");
		DataMoveTemplate dataMoveTemplate = dataMoveTemplateDAO.selectByPrimaryKey(id);
		ExceptionUtil.throwEmptyCheckException(dataMoveTemplate, "模板不存在");
		if (Constants.SYS_STATUS.REMOVE.getValue().equals(dataMoveTemplate.getStatus())) {
			ExceptionUtil.throwCheckException("模板已被删除，不允许启用");
		}
		dataMoveTemplate.setStatus(Constants.SYS_STATUS.START_USE.getValue());
		dataMoveTemplate.setModifyDate(new Date());
		dataMoveTemplateDAO.update(dataMoveTemplate);
	}

	@Override
	public void stopUseTemplateInfo(Long id) {
		// TODO Auto-generated method stub
		ExceptionUtil.throwEmptyCheckException(id, "模板ID为空");
		DataMoveTemplate dataMoveTemplate = dataMoveTemplateDAO.selectByPrimaryKey(id);
		ExceptionUtil.throwEmptyCheckException(dataMoveTemplate, "模板不存在");
		if (Constants.SYS_STATUS.REMOVE.getValue().equals(dataMoveTemplate.getStatus())) {
			ExceptionUtil.throwCheckException("模板已被删除，不允许停用");
		}
		dataMoveTemplate.setStatus(Constants.SYS_STATUS.STOP_USE.getValue());
		dataMoveTemplate.setModifyDate(new Date());
		dataMoveTemplateDAO.update(dataMoveTemplate);
	}

	@Override
	public PageResultBean<DataMoveTemplate> selectTemplateListByPage(DataMoveTemplate dataMoveTemplate) {
		// TODO Auto-generated method stub
		PageHelper.startPage(PageUtils.getPageNum(), PageUtils.getPageSize());
		return new PageResultBean<DataMoveTemplate>(selectTemplateList(dataMoveTemplate));
	}

}