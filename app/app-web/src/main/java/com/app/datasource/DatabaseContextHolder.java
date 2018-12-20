package com.app.datasource;

/**
 * 作用： 1、保存一个线程安全的DatabaseType容器
 */
public class DatabaseContextHolder {
	private static final ThreadLocal<DatabaseType> contextHolder = new ThreadLocal<>();

	public static void setDatabaseType(DatabaseType type) {
		contextHolder.set(type);
	}

	public static DatabaseType getDatabaseType() {
		return contextHolder.get();
	}

	/**
	 * 清除当前数据源
	 */
	public static void clear() {
		contextHolder.remove();
	}

}
