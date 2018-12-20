package com.app.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataSourceAspect {

	@Before("execution(* com.app.dao.local.*.*(..))")
	public void setLocalDataSourceKey(JoinPoint point) {
		DatabaseContextHolder.setDatabaseType(DatabaseType.applocaldb);
	}

	@Before("execution(* com.app.dao.remote.*.*(..))")
	public void setRemoteDataSourceKey(JoinPoint point) {
		DatabaseContextHolder.setDatabaseType(DatabaseType.appremotedb);
	}

	/**
	 * 执行方法后清除数据源设置
	 *
	 * @param joinPoint 切点
	 */
	@After("execution(* com.app.dao.*.*.*(..))")
	public void doAfter(JoinPoint joinPoint) {
		DatabaseContextHolder.clear();
	}

}
