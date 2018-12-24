package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.app.bean.ResultBean;
import com.app.dto.RemoteTableDTO;
import com.app.service.RemoteDataService;

/**
 * 远程库controller . <br>
 * 
 * @author wangtw <br>
 */
@RestController
public class RemoteDataController {

	/**
	 * 注入接口
	 */
	@Autowired
	private RemoteDataService remoteDataService;

	/**
	 * 查询远程表字段列表
	 * 
	 * @param remoteData
	 * @return
	 */
	@GetMapping("/remoteTableColumn/{tableName}")
	public ResultBean<List<RemoteTableDTO>> selectRemoteTableColumnList(@PathVariable("tableName") String tableName) {
		return new ResultBean<List<RemoteTableDTO>>(remoteDataService.selectColumnsByTableName(tableName));
	}

}
