package prg421.lab2.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import prg421.lab2.model.Movie;

/**
 * Created by Dominic on 10/25/2016.
 */
@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"prg421.lab2.model"})
@EnableJpaRepositories(basePackages = {"prg421.lab2.repo"})
@EnableTransactionManagement
public class RepoConfig {
   /* @Bean
    Movie movie()
    {
        return new Movie();
    }*/
}
