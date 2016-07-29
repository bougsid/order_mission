package com.bougsid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@SpringBootApplication
@EnableAsync
public class OrderMissionApplication {
//private static ApplicationContext context;
	public static void main(String[] args) {
		 SpringApplication.run(OrderMissionApplication.class, args);
	}

	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(Locale.FRANCE); // Set default Locale as US
		return slr;
	}

	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		source.setBasenames("messages");  // name of the resource bundle
		source.setUseCodeAsDefaultMessage(true);
		return source;
	}
	@Bean
	public ServerProperties getServerProperties() {
		return new ServerCustomization();
	}
//	public static ApplicationContext getContext() {
//		return context;
//	}
	//
//	@Bean
//	public ServletRegistrationBean servletRegistrationBean() {
//		FacesServlet servlet = new FacesServlet();
//		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(servlet, "*.jsf");
//		return servletRegistrationBean;
//	}
}
