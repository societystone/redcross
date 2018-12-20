package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.aspect.annotation.Log;
import com.app.bean.ResultBean;
import com.app.service.DataMoveService;

/**
 * 数据迁移controller . <br>
 * 
 * @author wangtw <br>
 */
@RestController
public class DataMoveController {

	/**
	 * 注入接口
	 */
	@Autowired
	private DataMoveService dataMoveService;

	/**
	 * 数据迁移
	 * 
	 * @return
	 */
	@Log("数据迁移")
	@PostMapping("/dataMove")
	public ResultBean<Long> dataMove() {
		return new ResultBean<Long>(dataMoveService.dataMove());
	}

}
