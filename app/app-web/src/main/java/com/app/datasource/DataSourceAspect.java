package com.app.datasource;

import org.aspectj.lang.JoinPoint;
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
	@Before("execution(* com.app.dao.remote1.*.*(..))")
	public void setRemote1DataSourceKey(JoinPoint point) {
		DatabaseContextHolder.setDatabaseType(DatabaseType.appremotedb1);
	}
	@Before("execution(* com.app.dao.remote2.*.*(..))")
	public void setRemote2DataSourceKey(JoinPoint point) {
		DatabaseContextHolder.setDatabaseType(DatabaseType.appremotedb2);
	}

}
