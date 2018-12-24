package com.app.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.bean.PageResultBean;
import com.app.common.Constants;
import com.app.dao.local.VoucherTemplateDAO;
import com.app.dao.local.VoucherTemplateDetailDAO;
import com.app.entity.VoucherTemplate;
import com.app.entity.VoucherTemplateDetail;
import com.app.service.VoucherTemplateService;
import com.app.util.Emptys;
import com.app.util.ExceptionUtil;
import com.app.util.PageUtils;
import com.github.pagehelper.PageHelper;

/**
 * 凭证模板接口实现类 . <br>
 * 
 * @author wangtw <br>
 */
@Service
public class VoucherTemplateServiceImpl implements VoucherTemplateService {

	/**
	 * 注入dao
	 */
	@Autowired
	private VoucherTemplateDAO voucherTemplateDAO;
	@Autowired
	private VoucherTemplateDetailDAO voucherTemplateDetailDAO;

	@Override
	public List<VoucherTemplate> selectTemplateList(VoucherTemplate voucherTemplate) {
		// TODO Auto-generated method stub
		HashMap<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("name", voucherTemplate.getName());
		queryMap.put("status", voucherTemplate.getStatus());
		queryMap.put("isUse", voucherTemplate.getIsUse());
		return voucherTemplateDAO.selectList(queryMap);
	}

	@Override
	public VoucherTemplate selectTemplateDetail(Long id) {
		// TODO Auto-generated method stub
		ExceptionUtil.throwEmptyCheckException(id, "模板ID为空");
		VoucherTemplate voucherTemplate = voucherTemplateDAO.selectByPrimaryKey(id);
		ExceptionUtil.throwEmptyCheckException(voucherTemplate, "模板不存在");
		HashMap<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("templateId", id);
		voucherTemplate.setTemplateDetails(voucherTemplateDetailDAO.selectList(queryMap));
		return voucherTemplate;
	}

	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	@Override
	public Long insertTemplateInfo(VoucherTemplate voucherTemplate) {
		// TODO Auto-generated method stub
		ExceptionUtil.throwEmptyCheckException(voucherTemplate, "模板参数为空");
		List<VoucherTemplateDetail> voucherTemplateDetails = voucherTemplate.getTemplateDetails();
		ExceptionUtil.throwEmptyCheckException(voucherTemplateDetails, "模板详情参数为空");
		voucherTemplateDetailDAO.insertBatch(voucherTemplateDetails);
		voucherTemplate.setStatus(Constants.SYS_STATUS.START_USE.getValue());
		voucherTemplate.setIsUse(Constants.SYS_YES_NO.NO.getValue());
		voucherTemplate.setCreateDate(new Date());
		return voucherTemplateDAO.insert(voucherTemplate);
	}

	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	@Override
	public void updateTemplateInfo(VoucherTemplate voucherTemplate) {
		// TODO Auto-generated method stub
		ExceptionUtil.throwEmptyCheckException(voucherTemplate, "模板参数为空");
		Long id = voucherTemplate.getId();
		VoucherTemplate dmt = voucherTemplateDAO.selectByPrimaryKey(id);
		ExceptionUtil.throwEmptyCheckException(dmt, "模板不存在");
		if (Constants.SYS_YES_NO.YES.getValue().equals(dmt.getIsUse())) {
			ExceptionUtil.throwCheckException("模板已被使用，不允许修改");
		}
		List<VoucherTemplateDetail> voucherTemplateDetails = voucherTemplate.getTemplateDetails();
		if (Emptys.isNotEmpty(voucherTemplateDetails)) {
			voucherTemplateDetailDAO.deleteByTemplateId(id);
			voucherTemplateDetailDAO.insertBatch(voucherTemplateDetails);
		}
		dmt.setName(voucherTemplate.getName());
		dmt.setModifyDate(new Date());
		voucherTemplateDAO.update(dmt);
	}

	@Override
	public void delteTemplateInfo(Long id) {
		// TODO Auto-generated method stub
		ExceptionUtil.throwEmptyCheckException(id, "模板ID为空");
		VoucherTemplate voucherTemplate = voucherTemplateDAO.selectByPrimaryKey(id);
		ExceptionUtil.throwEmptyCheckException(voucherTemplate, "模板不存在");
		if (Constants.SYS_YES_NO.YES.getValue().equals(voucherTemplate.getIsUse())) {
			ExceptionUtil.throwCheckException("模板已被使用，不允许删除");
		}
		voucherTemplate.setStatus(Constants.SYS_STATUS.REMOVE.getValue());
		voucherTemplate.setModifyDate(new Date());
		voucherTemplateDAO.update(voucherTemplate);
	}

	@Override
	public void startUseTemplateInfo(Long id) {
		// TODO Auto-generated method stub
		ExceptionUtil.throwEmptyCheckException(id, "模板ID为空");
		VoucherTemplate voucherTemplate = voucherTemplateDAO.selectByPrimaryKey(id);
		ExceptionUtil.throwEmptyCheckException(voucherTemplate, "模板不存在");
		if (Constants.SYS_STATUS.REMOVE.getValue().equals(voucherTemplate.getStatus())) {
			ExceptionUtil.throwCheckException("模板已被删除，不允许启用");
		}
		voucherTemplate.setStatus(Constants.SYS_STATUS.START_USE.getValue());
		voucherTemplate.setModifyDate(new Date());
		voucherTemplateDAO.update(voucherTemplate);
	}

	@Override
	public void stopUseTemplateInfo(Long id) {
		// TODO Auto-generated method stub
		ExceptionUtil.throwEmptyCheckException(id, "模板ID为空");
		VoucherTemplate voucherTemplate = voucherTemplateDAO.selectByPrimaryKey(id);
		ExceptionUtil.throwEmptyCheckException(voucherTemplate, "模板不存在");
		if (Constants.SYS_STATUS.REMOVE.getValue().equals(voucherTemplate.getStatus())) {
			ExceptionUtil.throwCheckException("模板已被删除，不允许停用");
		}
		voucherTemplate.setStatus(Constants.SYS_STATUS.STOP_USE.getValue());
		voucherTemplate.setModifyDate(new Date());
		voucherTemplateDAO.update(voucherTemplate);
	}

	@Override
	public PageResultBean<VoucherTemplate> selectTemplateListByPage(VoucherTemplate voucherTemplate) {
		// TODO Auto-generated method stub
		PageHelper.startPage(PageUtils.getPageNum(), PageUtils.getPageSize());
		return new PageResultBean<VoucherTemplate>(selectTemplateList(voucherTemplate));
	}

}