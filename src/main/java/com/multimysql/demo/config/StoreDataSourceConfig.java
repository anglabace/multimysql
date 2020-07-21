package com.multimysql.demo.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
//得到mapper
@MapperScan(basePackages = StoreDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "storeSqlSessionFactory")
public class StoreDataSourceConfig {
    // 精确到 master 目录，以便跟其他数据源隔离
    static final String PACKAGE = "com.multimysql.demo.mapper.store";
    static final String MAPPER_LOCATION = "classpath:mapper/store/*.xml";

    @Value("${spring.datasource.store.url}")
    private String url;

    @Value("${spring.datasource.store.username}")
    private String username;

    @Value("${spring.datasource.store.password}")
    private String password;

    @Value("${spring.datasource.store.driver-class-name}")
    private String driverClass;

    @Value("${spring.datasource.store.maximum-pool-size}")
    private int maximumPoolSize;

    @Value("${spring.datasource.store.minimum-idle}")
    private int minimumIdle;

    @Value("${spring.datasource.store.idle-timeout}")
    private long idleTimeout;

    @Value("${spring.datasource.store.max-lifetime}")
    private long maxLifetime;

    //得到datasource
    @Bean(name = "storeDataSource")
    @Primary
    public DataSource storeDataSource()  {
        HikariDataSource dataSource=new HikariDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClass);
        dataSource.setMaximumPoolSize(maximumPoolSize);
        dataSource.setMinimumIdle(minimumIdle);
        dataSource.setIdleTimeout(idleTimeout);
        dataSource.setMaxLifetime(maxLifetime);
        return dataSource;
    }

    //得到TransactionManager
    @Bean(name = "storeTransactionManager")
    @Primary
    public DataSourceTransactionManager storeTransactionManager() {
        return new DataSourceTransactionManager(storeDataSource());
    }

    //得到SqlSessionFactory
    @Bean(name = "storeSqlSessionFactory")
    @Primary
    public SqlSessionFactory storeSqlSessionFactory(@Qualifier("storeDataSource") DataSource storeDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(storeDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(StoreDataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
}