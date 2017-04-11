package com.payinxl;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;

@SpringBootApplication
@ServletComponentScan
@Controller
@Import(value = com.payinxl.common.util.SpringUtil.class)
@MapperScan(basePackages = "com.payinxl.dao",annotationClass = com.payinxl.common.persistence.annotation.MyBatisDao.class)
public class PayinxlApplication extends WebMvcConfigurerAdapter {
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
	}
	public static void main(String[] args) {
		SpringApplication.run(PayinxlApplication.class, args);
	}
	@Bean
	@ConfigurationProperties(prefix="spring.datasource")
	public DataSource druidDataSource() {
		return new DruidDataSource();
	}
}
