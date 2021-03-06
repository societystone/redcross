package com.app.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.local.LocalDataDAO;
import com.app.dao.remote.RemoteDataDAO;
import com.app.entity.DataMoveTemplate;
import com.app.entity.DataMoveTemplateDetail;
import com.app.service.DataMoveService;
import com.app.service.DataMoveTemplateService;
import com.app.util.Emptys;
import com.app.util.ExceptionUtil;

/**
 * 数据迁移接口实现类 . <br>
 * 
 * @author wangtw <br>
 */
@Service
public class DataMoveServiceImpl implements DataMoveService {

	/**
	 * 注入dao
	 */
	@Autowired
	private LocalDataDAO localDataDAO;
	@Autowired
	private RemoteDataDAO remoteDataDAO;
	@Autowired
	private DataMoveTemplateService dataMoveTemplateService;

	@Transactional(value = "transactionManagerLocal", rollbackFor = { Exception.class })
	@Override
	public Long dataMove() {
		// TODO Auto-generated method stub
		long count = 0;
		Long templateId = Long.valueOf(2);
		DataMoveTemplate dataMoveTemplate = dataMoveTemplateService.selectTemplateDetail(templateId);
		List<DataMoveTemplateDetail> dataMoveTemplateDetails = dataMoveTemplate.getTemplateDetails();
		ExceptionUtil.throwEmptyCheckException(dataMoveTemplateDetails, "数据迁移模板为空");

		List<String> resultColumns = new ArrayList<String>();
		List<String> insertColumns = new ArrayList<String>();
		for (DataMoveTemplateDetail dataMoveTemplateDetail : dataMoveTemplateDetails) {
			resultColumns.add(dataMoveTemplateDetail.getDataType());
			insertColumns.add(dataMoveTemplateDetail.getRemoteColumn());
		}
		HashMap<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("resultColumns", resultColumns);
		List<Map<String, Object>> localDatas = localDataDAO.selectList(queryMap);
		if (Emptys.isNotEmpty(localDatas)) {
			List<List<Object>> insertValues = new ArrayList<List<Object>>();
			for (Map<String, Object> localData : localDatas) {
				List<Object> record = new ArrayList<Object>();
				for (String columnName : resultColumns) {
					Object value = localData.get(columnName);
					record.add(value);
				}
				insertValues.add(record);
			}
			count = remoteDataDAO.insertBatch(insertColumns, insertValues);
		}
		return Long.valueOf(count);
	}

}