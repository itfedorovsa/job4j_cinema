package ru.job4j.cinema;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Application launch class
 *
 * @author itfedorovsa (itfedorovsa@gmail.com)
 * @version 1.0
 * @since 03.11.22
 */
@SpringBootApplication
public class Main {

    /**
     * Load properties
     *
     * @return {@link java.util.Properties}
     */
    private Properties loadDbProperties() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new InputStreamReader(
                        Main.class.getClassLoader()
                                .getResourceAsStream("db.properties")
                )
        )) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return cfg;
    }

    /**
     * Starting DB connections' pool
     *
     * @return BasicDataSource pool with connections to DB. Type {@link org.apache.commons.dbcp2.BasicDataSource}
     */
    @Bean
    public BasicDataSource loadPool() {
        Properties cfg = loadDbProperties();
        BasicDataSource pool = new BasicDataSource();
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
        return pool;
    }

    /**
     * Starts the application.
     *
     * @param args app arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        System.out.println("Go to http://localhost:8080/index");
    }

}

