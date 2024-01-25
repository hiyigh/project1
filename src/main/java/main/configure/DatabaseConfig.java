package main.configure;

import com.zaxxer.hikari.HikariDataSource;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {
    @Value( "${spring.database.url}")
    private String jdbcUrl;

    @Value("${spring.database.username}")
    private String username;

    @Value("${spring.database.password}")
    private String password;

    @Value("${spring.database.driver-class-name}")
    private String driverClassName;

    @Value("${spring.database.connection-timeout}")
    private int connectionTimeout;

    @Value("${spring.database.maximum-pool-size}")
    private int maximumPoolSize;
    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);
        dataSource.setConnectionTimeout(connectionTimeout);
        dataSource.setMaximumPoolSize(maximumPoolSize);
        return dataSource;
    }
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
