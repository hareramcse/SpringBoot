package com.hs.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableJpaRepositories(basePackages = "com.sharding.shardexample.model.shard", entityManagerFactoryRef = "shard1EntityManagerFactory", transactionManagerRef = "shard1TransactionManager")
@EntityScan(basePackages = "com.sharding.shardexample.model.shard")
public class ShardConfiguration {
	@Autowired
	private Environment env;

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.shard1")
	public DataSourceProperties shard1DataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	public DataSource shard1DataSource() {
		DataSourceProperties shard1DataSourceProperties = shard1DataSourceProperties();
		return DataSourceBuilder.create().driverClassName(shard1DataSourceProperties.getDriverClassName())
				.url(shard1DataSourceProperties.getUrl()).username(shard1DataSourceProperties.getUsername())
				.password(shard1DataSourceProperties.getPassword()).build();
	}

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.shard2")
	public DataSourceProperties shard2DataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	public DataSource shard2DataSource() {
		DataSourceProperties shard2DataSourceProperties = shard2DataSourceProperties();
		return DataSourceBuilder.create().driverClassName(shard2DataSourceProperties.getDriverClassName())
				.url(shard2DataSourceProperties.getUrl()).username(shard2DataSourceProperties.getUsername())
				.password(shard2DataSourceProperties.getPassword()).build();
	}

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.shard3")
	public DataSourceProperties shard3DataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	public DataSource shard3DataSource() {
		DataSourceProperties shard3DataSourceProperties = shard3DataSourceProperties();
		return DataSourceBuilder.create().driverClassName(shard3DataSourceProperties.getDriverClassName())
				.url(shard3DataSourceProperties.getUrl()).username(shard3DataSourceProperties.getUsername())
				.password(shard3DataSourceProperties.getPassword()).build();
	}

	@Bean(name = "multiRoutingDataSource")
	public DataSource multiRoutingDataSource() {
		Map<Object, Object> targetDataSources = new HashMap<>();
		targetDataSources.put(DBTypeEnum.SHARD1, shard1DataSource());
		targetDataSources.put(DBTypeEnum.SHARD2, shard2DataSource());
		targetDataSources.put(DBTypeEnum.SHARD3, shard3DataSource());
		MultiRoutingDataSource multiRoutingDataSource = new MultiRoutingDataSource();
		multiRoutingDataSource.setTargetDataSources(targetDataSources);
		return multiRoutingDataSource;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		EntityManagerFactory factory = multiEntityManager().getObject();
		return new JpaTransactionManager(factory);
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean multiEntityManager() {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(multiRoutingDataSource());
		factory.setPackagesToScan(new String[] { "com.hs.repository" });
		factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
		jpaProperties.put("hibernate.show-sql", env.getProperty("spring.jpa.show-sql"));
		factory.setJpaProperties(jpaProperties);
		factory.setPersistenceUnitName("multiEntityManager");
		return factory;
	}

	@Bean
	public DataSourceInitializer dataSourceInitializer() {
		DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
		dataSourceInitializer.setDataSource(shard1DataSource());
		dataSourceInitializer.setEnabled(env.getProperty("spring.datasource.shard1.initialize", Boolean.class, true));
		dataSourceInitializer.setEnabled(env.getProperty("spring.datasource.shard2.initialize", Boolean.class, true));
		dataSourceInitializer.setEnabled(env.getProperty("spring.datasource.shard3.initialize", Boolean.class, true));
		return dataSourceInitializer;
	}
}
