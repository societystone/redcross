package com.app.datasource;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan(basePackages = "com.app.dao.remote", sqlSessionFactoryRef = "sqlSessionFactoryRemote", sqlSessionTemplateRef = "sqlSessionTemplateRemote")
public class RemoteSourceConfig {

	@Autowired
	private Environment env;

	@Bean(name = "remoteDataSource", destroyMethod = "close")
	@ConfigurationProperties(prefix = "spring.datasource.remote")
	public DataSource dataSource() {
		return new AtomikosDataSourceBean();
	}

	@Bean(name = "sqlSessionFactoryRemote")
	@Autowired
	public SqlSessionFactory sqlSessionFactory(@Qualifier("remoteDataSource") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		// 下边两句仅仅用于*.xml文件，如果整个持久层操作不需要使用到xml文件的话（只用注解就可以搞定），则不加
		bean.setTypeAliasesPackage(env.getProperty("mybatis.typeAliasesPackage"));// 指定基包
		bean.setMapperLocations(new PathMatchingResourcePatternResolver()
				.getResources(env.getProperty("mybatis.mapper-locations.remote")));
		return bean.getObject();
	}

	@Bean(name = "transactionManagerRemote")
	@Autowired
	public DataSourceTransactionManager transactionManager(@Qualifier("remoteDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Autowired
	@Bean(name = "sqlSessionTemplateRemote")
	public SqlSessionTemplate sqlSessionTemplate(@Qualifier("remoteDataSource") DataSource dataSource)
			throws Exception {
		SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory(dataSource));
		return template;
	}

}
