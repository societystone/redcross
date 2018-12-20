package com.app.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源（需要继承AbstractRoutingDataSource）
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
	protected Object determineCurrentLookupKey() {
		System.out.println("当前数据源：" + DatabaseContextHolder.getDatabaseType());
		return DatabaseContextHolder.getDatabaseType();
	}
}