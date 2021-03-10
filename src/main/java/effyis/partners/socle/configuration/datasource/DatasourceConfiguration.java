package effyis.partners.socle.configuration.datasource;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;

import com.zaxxer.hikari.HikariDataSource;

import effyis.partners.socle.configuration.datasource.DataSourceConfigurationProperties.DataSourceDTO;

/**
 * 
 * @author EL KOTB ZAKARIA
 *
 */
@Configuration
public class DatasourceConfiguration {

	@Autowired
	private DataSourceConfigurationProperties properties;

	@Value("${datasource.max.pool.size}")
	private int maxPoolSize;
	@Value("${datasource.min.idle}")
	private int minIdle;
	@Value("${datasource.idle.timeout}")
	private int idleTimeout;

	@Bean(name = "dataSource")
	@DependsOn("datasources")
	public DataSource getDataSource(Map<Object, Object> dataSources) throws DataSourceNotFoundException {
		MyRoutingDataSource dataSource = new MyRoutingDataSource();
		dataSource.setTargetDataSources(dataSources);
		dataSource.afterPropertiesSet();
		return dataSource;
	}

	@Bean(name = "datasources")
	@Primary
	public Map<Object, Object> getDataSources() {
		Map<Object, Object> dataSources = new HashMap<Object, Object>();
		for (DataSourceDTO datasource : this.properties.getDatasources()) {
			HikariDataSource hkDataSource = new HikariDataSource();
			hkDataSource.setJdbcUrl(datasource.getUrl());
			hkDataSource.setUsername(datasource.getUsername());
			hkDataSource.setPassword(datasource.getPassword());
			hkDataSource.setMaximumPoolSize(this.maxPoolSize);
			hkDataSource.setMinimumIdle(this.minIdle);
			hkDataSource.setIdleTimeout(this.idleTimeout);
			dataSources.put(datasource.getClient(), hkDataSource);
		}
		return dataSources;
	}

}
