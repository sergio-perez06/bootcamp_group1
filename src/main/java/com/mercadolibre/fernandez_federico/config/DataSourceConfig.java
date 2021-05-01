package com.mercadolibre.fernandez_federico.config;

import com.fury.api.FuryUtils;
import com.fury.api.exceptions.FuryDecryptException;
import com.fury.api.exceptions.FuryNotFoundAPPException;
import com.fury.api.exceptions.FuryUpdateException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = {"com.mercadolibre.fernandez_federico.repositories"})
@EnableTransactionManagement
public class DataSourceConfig {

    @Bean
    @Qualifier("datasource")
    @Profile("integration_test")
    public DataSource getDataSourceTest() {
        return DataSourceBuilder.create()
                .url("jdbc:h2:mem:warehouse;MODE=MySQL")
                .username("sa")
                .build();
    }


    @Bean
    @Qualifier("datasource")
    @Profile({"!local & !integration_test & !test"})
    public DataSource getDataSource(
//            final @Value("${spring.datasource.driver-class-name}") String driver,
            final @Value("${spring.datasource.host}") String host,
            final @Value("${spring.datasource.db}") String db,
            final @Value("${spring.datasource.username}") String user,
            final @Value("${spring.datasource.password}") String password

    )
            throws FuryDecryptException, FuryNotFoundAPPException, FuryUpdateException {
        return DataSourceBuilder.create()
//                .driverClassName(driver)
                .url(String.format("jdbc:mysql://%s/%s?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false&characterEncoding=utf8", FuryUtils.getEnv(host), db))
                .username(user)
                .password(FuryUtils.getEnv(password))
                .build();
    }
    @Bean
    @Qualifier("datasource")
    @Profile("local | test")
    public DataSource getDataSourceLocal(
//            final @Value("${spring.datasource.driver-class-name}") String driver,
            final @Value("${spring.datasource.host}") String host,
            final @Value("${spring.datasource.db}") String db,
            final @Value("${spring.datasource.username}") String user,
            final @Value("${spring.datasource.password}") String password
    ) {
        return DataSourceBuilder.create()
//                .driverClassName(driver)
                .url(String.format("jdbc:mysql://%s/%s?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=true&characterEncoding=utf8", host, db))
                .username(user)
                .password(password)
                .build();
    }
}