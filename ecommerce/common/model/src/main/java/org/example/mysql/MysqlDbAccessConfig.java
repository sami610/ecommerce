package org.example.mysql;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;

@Configuration
@PropertySource(value = "classpath:mysql.properties")
@EnableJpaRepositories("org.example.*")
@EntityScan("org.example.*")
public class MysqlDbAccessConfig {

    @Value("${spring.datasource.url}")
    private String mysqlHost;

    @Value("${spring.datasource.username}")
    private String mysqlUsername;

    @Value("${spring.datasource.password}")
    private String mysqlPassword;

    @PostConstruct
    public void runDBMigration() {
        String url = mysqlHost;
        String username = mysqlUsername;
        String password = mysqlPassword;
        Flyway flyway = Flyway.configure().dataSource(url, username, password).load();
        flyway.migrate();
    }
}