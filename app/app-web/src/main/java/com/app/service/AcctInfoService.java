package com.app.service;

import java.util.List;

import com.app.entity.AcctInfo;

/**
 * 账户交易流水接口 . <br>
 * 
 * @author wangtw <br>
 */
public interface AcctInfoService {
	/**
	 * 获取所有有效账户列表
	 * 
	 * @return
	 */
	public List<AcctInfo> getAllValidAcct();

}