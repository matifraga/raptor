package ar.edu.itba.paw.webapp.config;

import java.nio.charset.StandardCharsets;

import javax.sql.DataSource;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@EnableWebMvc
@ComponentScan({ "ar.edu.itba.paw.webapp.controllers",
		"ar.edu.itba.paw.services", "ar.edu.itba.paw.persistence" })
@Configuration
public class WebConfig  extends WebMvcConfigurerAdapter {
	
	private static final String RESOURCES = "/resources/";
	private static final String RESOURCES_PATH = "/resources/**";
	private final static String PREFIX_WEB_INF = "/WEB-INF/jsp/";
	private final static String SUFFIX_JSP = ".jsp";

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(RESOURCES_PATH).addResourceLocations(RESOURCES);
	}

	@Bean
	public ViewResolver viewResolver() {
		final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix(PREFIX_WEB_INF);
		viewResolver.setSuffix(SUFFIX_JSP);

		return viewResolver;
	}


	/*@Bean
	public DataSource dataSource() {
		final SimpleDriverDataSource ds = new SimpleDriverDataSource();
		ds.setDriverClass(JDBCDriver.class);
		ds.setUrl("jdbc:hsqldb:mem:paw");
		ds.setUsername("hq");
		ds.setPassword("");

		return ds;
	}*/
	

	@Bean
	public DataSource dataSource() {
		final DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName("org.postgresql.Driver");
		ds.setUrl("jdbc:postgresql://tantor.db.elephantsql.com:5432/bevoasov");
		ds.setUsername("bevoasov");
		ds.setPassword("hJmre4hNt9KHH44PVLNBH44HmJJn6DVh");

		return ds;
	}
	
	@Bean
	public MessageSource messageSource(){
		final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:i18n/messages");
		messageSource.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
		return messageSource;
	}
}