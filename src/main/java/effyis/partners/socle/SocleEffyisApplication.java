package effyis.partners.socle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * 
 * @author EL KOTB ZAKARIA
 *
 */
@SpringBootApplication
@EnableWebSecurity
@EnableAsync
@EnableCaching
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class, LiquibaseAutoConfiguration.class })
public class SocleEffyisApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocleEffyisApplication.class, args);
	}
}
