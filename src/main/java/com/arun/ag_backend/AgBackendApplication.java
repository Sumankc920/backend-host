package com.arun.ag_backend;

import com.arun.ag_backend.Filter.CustomCorsFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AgBackendApplication {

	public static void main(String[] args) {

		SpringApplication.run(AgBackendApplication.class, args);
	}

//	@Bean
//	public FilterRegistrationBean<CustomCorsFilter> corsFilter() {
//		FilterRegistrationBean<CustomCorsFilter> registrationBean = new FilterRegistrationBean<>();
//		registrationBean.setFilter(new CustomCorsFilter());
//		registrationBean.addUrlPatterns("/*"); // Apply the filter to all URL patterns
//		return registrationBean;
//	}
}
