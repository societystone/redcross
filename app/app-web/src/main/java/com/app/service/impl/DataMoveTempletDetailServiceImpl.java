package com.app.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.local.DataMoveTempletDetailDAO;
import com.app.entity.DataMoveTempletDetail;
import com.app.service.DataMoveTempletDetailService;
import com.app.util.ExceptionUtil;

/**
 * 数据迁移模板详情接口实现类 . <br>
 * 
 * @author wangtw <br>
 */
@Service
public class DataMoveTempletDetailServiceImpl implements DataMoveTempletDetailService {

	/**
	 * 注入dao
	 */
	@Autowired
	private DataMoveTempletDetailDAO dataMoveTempletDetailDAO;

	@Override
	public List<DataMoveTempletDetail> selectListByTempletId(Long templetId) {
		// TODO Auto-generated method stub
		ExceptionUtil.throwEmptyCheckException(templetId, "模板ID不能为空");
		HashMap<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("templetId", templetId);
		return dataMoveTempletDetailDAO.selectList(queryMap);
	}

}