package io.github.LorranFernandes.libraryapi.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DataBaseConfiguration { // Por padrão o spring ja cria a conexão com os dados que estiverem no application.yml

    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;
    @Value("${spring.datasource.driver-class-name}")
    String driver;

    // @Bean
    public DataSource datasource() { // Não suporta grandes sistemas, só feito para mostrar que tem
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setDriverClassName(driver);

        return ds;
    }

    @Bean
    public DataSource hikariDataSource() { //suporta grandes sistemas
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driver);

        config.setMaximumPoolSize(10); //Maximo de conexoes liberadas, mudar o max pode resolver problemas de lentidão
        config.setMinimumIdle(1); // tamanho inicial do pool
        config.setPoolName("library-db-poll");
        config.setMaxLifetime(600000); // 600 mil ms (10 min)
        config.setConnectionTimeout(100000); // Timeout para conseguir uma conexao
        config.setConnectionTestQuery("SELECT 1"); // Query de test


        return new HikariDataSource(config);
    }
}
