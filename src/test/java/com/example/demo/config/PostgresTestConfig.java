package com.example.demo.config;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;

@TestConfiguration
@EntityScan(basePackages = "com.example.demo")
public class PostgresTestConfig {
    @Bean
    public PostgreSQLContainer postgreSQLContainer() {
        final PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:13.8");
        postgreSQLContainer.start();
        return postgreSQLContainer;
    }

    @Bean
    public DataSource datasource(PostgreSQLContainer postgreSQLContainer) {
        return DataSourceBuilder.create()
                .driverClassName(postgreSQLContainer.getDriverClassName())
                .url(postgreSQLContainer.getJdbcUrl())
                .username(postgreSQLContainer.getUsername())
                .password(postgreSQLContainer.getPassword())
                .build();
    }

}
