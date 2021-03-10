package effyis.partners.socle.configuration.liquibase;

import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import effyis.partners.socle.configuration.datasource.DataSourceConfigurationProperties;

/**
 * 
 * @author EL KOTB ZAKARIA
 *
 */
@Configuration
@ConditionalOnProperty(prefix = "spring.liquibase", name = "enabled", matchIfMissing = true)
public class LiquibaseConfiguration {

	@Autowired
	private DataSourceConfigurationProperties properties;

	@Bean(name = "customMultiTenantSpringLiquibase")
	@DependsOn("datasources")
	public CustomMultiTenantSpringLiquibase getLiquibase(Map<Object, Object> dataSources) throws SQLException {
		CustomMultiTenantSpringLiquibase multiTenantSpringLiquibase = new CustomMultiTenantSpringLiquibase();
		dataSources.forEach((client, datasource) -> multiTenantSpringLiquibase.populateDatasources((String) client,
				(DataSource) datasource));
		multiTenantSpringLiquibase.setProperties(this.properties);
		return multiTenantSpringLiquibase;
	}
}
