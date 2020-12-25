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
 * @Date 2020-12-25
 */
@Configuration
@MapperScan(basePackages = {"${mybatis.mapperScan.basePackages}"}, sqlSessionTemplateRef = "orderSqlSessionTemplate")
public class OrderDataSourceConfig {

    @Bean("orderDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.order")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean("orderSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("orderDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        return factoryBean.getObject();
    }

    @Bean("orderSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("orderSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean("orderTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("orderDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
