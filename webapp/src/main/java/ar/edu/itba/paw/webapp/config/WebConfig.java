package ar.edu.itba.paw.webapp.config;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@EnableTransactionManagement
@EnableWebMvc
@ComponentScan({ "ar.edu.itba.paw.webapp.controllers",
		"ar.edu.itba.paw.services", "ar.edu.itba.paw.persistence" })
@Configuration
public class WebConfig  extends WebMvcConfigurerAdapter {
	
	private static final String RESOURCES = "/resources/";
	private static final String RESOURCES_PATH = "/resources/**";
	private final static String PREFIX_WEB_INF = "/WEB-INF/jsp/";
	private final static String SUFFIX_JSP = ".jsp";
	
	@Value("classpath:schema.sql")
    private Resource schemaSql;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(RESOURCES_PATH).addResourceLocations(RESOURCES);
	}
	
	@Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan("ar.edu.itba.paw.models");
        JpaVendorAdapter jpa = new HibernateJpaVendorAdapter();
        factoryBean.setJpaVendorAdapter(jpa);
        factoryBean.setJpaProperties(hibernateProperties());
        return factoryBean;
     }
	
	private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL82Dialect");        
        return properties;        
    }
	
	@Bean
	public PlatformTransactionManager transactionManager(final EntityManagerFactory emf){
		return new JpaTransactionManager(emf);
	}
	
//	@Bean
//    public PlatformTransactionManager transactionManager(final DataSource ds) {
//            return new DataSourceTransactionManager(ds);
//    }

	@Bean
	public ViewResolver viewResolver() {
		final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix(PREFIX_WEB_INF);
		viewResolver.setSuffix(SUFFIX_JSP);

		return viewResolver;
	}

	@Bean					
	public DataSource dataSource() {
		final DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName("org.postgresql.Driver");

//		// DB ELEPHANT
//		ds.setUrl("jdbc:postgresql://pellefant-02.db.elephantsql.com:5432/rroxiqgx");
//		ds.setUsername("rroxiqgx");
//		ds.setPassword("IugU760wJ4CcMpk2g-iwyMM8VSyQnjXi");

		
//		// DB ELEPHANT 2 CREO QUE NO SIRVE MAS ESTA...
//		ds.setUrl("jdbc:postgresql://pellefant-02.db.elephantsql.com:5432/nruingnw");
//		ds.setUsername("nruingnw");
//		ds.setPassword("4cgleDjzN1_qwvsbwzge7-6bwiElxsp3");

//		// DB TESTING TOMI
//		ds.setUrl("jdbc:postgresql://localhost/tomi");
//		ds.setUsername("Tomi");
// 		ds.setPassword("147852");
		
		// DB TESTING LUCAS
//		ds.setUrl("jdbc:postgresql://localhost/lucaspaw");
//		ds.setUsername("postgres");
//		ds.setPassword("123456");

		// DB FOR DEPLOY
		ds.setUrl("jdbc:postgresql://localhost/grupo6");
		ds.setUsername("grupo6");
		ds.setPassword("baiK8Hah");

		return ds;
	}
	
	@Bean
	public MessageSource messageSource(){
		final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:i18n/messages");
		messageSource.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
		return messageSource;
	}
	
//	@Bean
//    public DataSourceInitializer dataSourceInitializer(final DataSource ds) {
//        final DataSourceInitializer dsi = new DataSourceInitializer();
//        dsi.setDataSource(ds);
//        dsi.setDatabasePopulator(databasePopulator());
//        return dsi;
//    }
//
//    private DatabasePopulator databasePopulator() {
//        final ResourceDatabasePopulator dbp = new ResourceDatabasePopulator();
//        dbp.addScript(schemaSql);
//        return dbp;
//    }
}
