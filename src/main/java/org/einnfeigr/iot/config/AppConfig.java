package org.einnfeigr.iot.config;

import java.util.List;

import org.einnfeigr.iot.ArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mobile.device.DeviceHandlerMethodArgumentResolver;
import org.springframework.mobile.device.DeviceResolverHandlerInterceptor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.springmvc.HandlebarsViewResolver;

@Configuration
public class AppConfig implements WebMvcConfigurer {

	private static HandlebarsViewResolver handlebars = null;
	
	@Bean
	public DriverManagerDataSource getDataSource() {
		DriverManagerDataSource bds = new DriverManagerDataSource();
		bds.setDriverClassName("org.mariadb.jdbc.Driver");
		bds.setUrl("jdbc:mariadb://"+System.getenv("db.host")+":"
				+ (System.getenv("db.port") != null ? System.getenv("db.port") : "3306")+"/"
				+ System.getenv("db.schema")
				+ "?serverTimezone=UTC"
				+ "&useUnicode=yes&characterEncoding=UTF-8&allowPublicKeyRetrieval=true");
		bds.setUsername(System.getenv("db.username"));
		bds.setPassword(System.getenv("db.password"));
		return bds;
	}
	@Bean
    public DeviceResolverHandlerInterceptor deviceResolverHandlerInterceptor() { 
    	return new DeviceResolverHandlerInterceptor(); 
    }
	 
    @Bean
    public DeviceHandlerMethodArgumentResolver deviceHandlerMethodArgumentResolver() { 
        return new DeviceHandlerMethodArgumentResolver(); 
    }
 
    @Override
    public void addInterceptors(InterceptorRegistry registry) { 
        registry.addInterceptor(deviceResolverHandlerInterceptor()); 
    }
 
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(deviceHandlerMethodArgumentResolver()); 
        argumentResolvers.add(new ArgumentResolver(deviceHandlerMethodArgumentResolver())); 
    }
    
	@Bean
	public HandlebarsViewResolver handlebars() {
		ClassPathTemplateLoader loader = new ClassPathTemplateLoader("/templates", ".hbs");
		Handlebars hbs = new Handlebars(loader);
		handlebars = new HandlebarsViewResolver(hbs);
		handlebars.setCache(false);
		return handlebars;
	}
		
}
