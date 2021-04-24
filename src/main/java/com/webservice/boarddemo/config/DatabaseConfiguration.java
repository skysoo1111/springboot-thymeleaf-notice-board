package com.webservice.boarddemo.config;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfiguration {

  @Bean(name = "dataSource")
  @ConfigurationProperties("spring.datasource.hikari")
  public DataSource dataSource(){
    return DataSourceBuilder.create()
        .type(HikariDataSource.class)
        .build();
  }

}
