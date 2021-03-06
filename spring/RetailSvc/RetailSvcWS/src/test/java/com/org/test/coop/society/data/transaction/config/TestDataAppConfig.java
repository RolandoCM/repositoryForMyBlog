package com.org.test.coop.society.data.transaction.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
//@ComponentScan(basePackages = "com.org.coop", excludeFilters = { @Filter(type = FilterType.ANNOTATION, value = Configuration.class) })
@PropertySource("classpath:retailSvcWSTest.properties")
@Import({TestRetailDBConfig.class, TestCoOperativeAdminDBConfig.class})
public class TestDataAppConfig {
	
	@Autowired
	private Environment env;

}
