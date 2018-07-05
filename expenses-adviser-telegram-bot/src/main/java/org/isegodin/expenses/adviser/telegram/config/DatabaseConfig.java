package org.isegodin.expenses.adviser.telegram.config;

import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.dialect.PostgreSQL95Dialect;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author isegodin
 */
@Configuration
public class DatabaseConfig {

    /**
     * DATABASE_URL parameter pattern from Heroku
     */
    private Pattern DATABASE_URL_PATTERN = Pattern.compile("^postgres://(?<username>[^:]+):(?<password>[^@]+)@(?<url>.+)$");

    @Bean
    public DataSource dataSource(@Value("${database.url}") String databaseUrl,
                                 @Value("${database.leakDetectionThreshold}") long leakDetectionThreshold,
                                 @Value("${database.maxPoolSize}") int maxPoolSize,
                                 @Value("${database.driverClassName}") String driverClassName) {

        Matcher matcher = DATABASE_URL_PATTERN.matcher(databaseUrl);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Unknown database url format: " + databaseUrl);
        }

        String jdbcUrl = "jdbc:postgresql://" + matcher.group("url") + "?sslmode=require";
        String userName = matcher.group("username");
        String password = matcher.group("password");

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        dataSource.setLeakDetectionThreshold(leakDetectionThreshold);
        dataSource.setMaximumPoolSize(maxPoolSize);
        dataSource.setDriverClassName(driverClassName);
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setPackagesToScan("org.isegodin.expenses.adviser.telegram.data.domain");

        Properties properties = new Properties();
        properties.put("hibernate.dialect", PostgreSQL95Dialect.class);
        properties.put("hibernate.show_sql", false);

        entityManagerFactoryBean.setJpaProperties(properties);

        return entityManagerFactoryBean;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        transactionManager.setValidateExistingTransaction(true);
        return transactionManager;
    }

    @Bean
    public LiquibaseUpdateExecutor liquibaseUpdateExecutor(DataSource dataSource) {
        LiquibaseUpdateExecutor executor = new LiquibaseUpdateExecutor();
        executor.setDataSource(dataSource);
        return executor;
    }

}
