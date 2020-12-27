package com.wenxiahy.hy.mybatis.config;

import com.wenxiahy.hy.mybatis.support.MapperScan;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @Author zhouw
 * @Description
 * @Date 2020-12-27
 */
@Configuration
@MapperScan(basePackages = {"${mybatis.mapperScan.basePackages.log}"}, sqlSessionTemplateRef = "logSqlSessionTemplate")
public class LogDataSourceConfig {

    @Bean("logDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.log")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean("logSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("logDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        return factoryBean.getObject();
    }

    @Bean("logSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("logSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean("logTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("logDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
