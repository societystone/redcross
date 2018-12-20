package com.app.dao.local;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 本地数据dao . <br>
 * 
 * @author wangtw <br>
 */
public interface LocalDataDAO {

	/**
	 * 查询本地数据
	 * 
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> selectList(HashMap<String, Object> map);

}