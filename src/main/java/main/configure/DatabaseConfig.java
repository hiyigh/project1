package main.configure;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {
    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mariadb://webserver.caw1uaxlfzuq.ap-northeast-2.rds.amazonaws.com:3306/webserver");
        dataSource.setUsername("admin123456");
        dataSource.setPassword("admin123456");
        dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
        dataSource.setConnectionTimeout(20000);
        dataSource.setMaximumPoolSize(1);
        return dataSource;
    }
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
