package com.multimysql.demo.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
//得到mapper
@MapperScan(basePackages = TikuDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "tikuSqlSessionFactory")
public class TikuDataSourceConfig {
    static final String PACKAGE = "com.multimysql.demo.mapper.tiku";
    static final String MAPPER_LOCATION = "classpath:mapper/tiku/*.xml";

    @Value("${spring.datasource.tiku.url}")
    private String url;

    @Value("${spring.datasource.tiku.username}")
    private String username;

    @Value("${spring.datasource.tiku.password}")
    private String password;

    @Value("${spring.datasource.tiku.driver-class-name}")
    private String driverClass;

    @Value("${spring.datasource.tiku.maximum-pool-size}")
    private int maximumPoolSize;

    @Value("${spring.datasource.tiku.minimum-idle}")
    private int minimumIdle;

    @Value("${spring.datasource.tiku.idle-timeout}")
    private long idleTimeout;

    @Value("${spring.datasource.tiku.max-lifetime}")
    private long maxLifetime;

    //得到datasource
    @Bean(name = "tikuDataSource")
    public DataSource tikuDataSource() {
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
    @Bean(name = "tikuTransactionManager")
    public DataSourceTransactionManager tikuTransactionManager() {
        return new DataSourceTransactionManager(tikuDataSource());
    }

    //得到SqlSessionFactory
    @Bean(name = "tikuSqlSessionFactory")
    public SqlSessionFactory tikuSqlSessionFactory(@Qualifier("tikuDataSource") DataSource tikuDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(tikuDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(TikuDataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
}