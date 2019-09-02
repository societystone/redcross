package com.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.config.Config;
import com.app.dao.remote.RemoteDataDAO;
import com.app.dto.RemoteTableDTO;
import com.app.service.RemoteDataService;
import com.app.util.Emptys;
import com.app.util.ExceptionUtil;

/**
 * 远程库接口实现类 . <br>
 * 
 * @author wangtw <br>
 */
@Service
public class RemoteDataServiceImpl implements RemoteDataService {

	/**
	 * 注入dao
	 */
	@Autowired
	private RemoteDataDAO remoteDataDAO;

	@Override
	public List<RemoteTableDTO> selectColumnsByTableName(String tableName) {
		// TODO Auto-generated method stub
		ExceptionUtil.throwEmptyCheckException(tableName, "表名为空");
		List<RemoteTableDTO> results = null;
		List<Map<String, String>> lists = remoteDataDAO.selectColumns(Config.databaseName, tableName);
		if (Emptys.isNotEmpty(lists)) {
			results = new ArrayList<RemoteTableDTO>();
			for (Map<String, String> map : lists) {
				RemoteTableDTO remoteTableDTO = new RemoteTableDTO();
				remoteTableDTO.setColumnName(map.get("column_name"));
				remoteTableDTO.setColumnComment(map.get("column_comment"));
				results.add(remoteTableDTO);
			}
		}
		return results;
	}

}