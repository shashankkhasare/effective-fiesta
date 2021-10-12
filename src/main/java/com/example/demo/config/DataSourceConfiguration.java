package com.example.demo.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataSourceConfiguration {

	@Bean
	@ConfigurationProperties("multitenancy.tenant.datasource")
	public DataSourceProperties tenantDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean("dataSource")
	@ConfigurationProperties("multitenancy.tenant.datasource.hikari")
	public DataSource tenantDataSource(Environment environment) {
		HikariDataSource dataSource = tenantDataSourceProperties().initializeDataSourceBuilder()
				.type(HikariDataSource.class).username(environment.getProperty("datasource.username"))
				.password(environment.getProperty("datasource.password")).build();

		dataSource.setPoolName("tenantDataSource");
		return new TenantAwareDataSource(dataSource);
	}

}