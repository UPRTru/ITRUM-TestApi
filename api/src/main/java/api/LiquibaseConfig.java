package api;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@EnableAutoConfiguration
@Configuration
public class LiquibaseConfig {
    @Bean
    public static DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:45533/ITRUM-TestApi");
        dataSource.setUsername("ITRUM-TestApi");
        dataSource.setPassword("ITRUM-TestApi");
        return dataSource;
    }

    @Bean
    public static SpringLiquibase springLiquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource());
        liquibase.setChangeLog("db/changelog/master.xml");
        return liquibase;
    }
}

