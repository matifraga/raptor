package ar.edu.itba.persistence;

import javax.sql.DataSource;

import org.hsqldb.jdbc.JDBCDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

/**
 * Created by alumno on 27/04/2016.
 */
@ComponentScan({"ar.edu.itba.paw.persistence"})
@Configuration
public class TestConfig {


    @Bean 
    public DataSource dataSource() {
        final SimpleDriverDataSource ds = new SimpleDriverDataSource();
        ds.setDriverClass(JDBCDriver.class);
        ds.setUrl("jdbc:hsqldb:mem:paw");
        ds.setUsername("hq");
        ds.setPassword("");

        return ds;
    }
}
