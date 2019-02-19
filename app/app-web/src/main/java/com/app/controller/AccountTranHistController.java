package com.app.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.bean.PageResultBean;
import com.app.bean.ResultBean;
import com.app.entity.AcctTranHist;
import com.app.service.AcctTranHistService;

@RestController
public class AccountTranHistController {
	
	@Autowired
	private AcctTranHistService acctTranHistService;


	
	/**
	 * 分页查询账户
	 * @param acct
	 * @return
	 */
	@RequestMapping("accttranhist/selectlist")
	public ResultBean<PageResultBean<AcctTranHist>> selectList(@RequestBody HashMap<String, Object> queryMap) {
		
		return new ResultBean<PageResultBean<AcctTranHist>>(acctTranHistService.SelectListByPage(queryMap));
	}
	/**
	 * 按账户id查询账户信息
	 * @param id
	 * @return
	 */
	@RequestMapping("accttranhist/select/{id}")
	@ResponseBody
	public ResultBean<AcctTranHist> selectById(@PathVariable("id") Long id) {
	
		return new ResultBean<AcctTranHist>(acctTranHistService.selectById(id));
	}
	
}
