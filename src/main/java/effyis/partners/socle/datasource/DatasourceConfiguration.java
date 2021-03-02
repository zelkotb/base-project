package effyis.partners.socle.datasource;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariDataSource;

import effyis.partners.socle.datasource.DataSourceConfigurationProperties.DataSourceDTO;
import effyis.partners.socle.exception.DataSourceNotFoundException;

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

	@Autowired
	@Bean(name = "dataSource")
	public DataSource getDataSource() throws DataSourceNotFoundException {
		MyRoutingDataSource dataSource = new MyRoutingDataSource();
		dataSource.setTargetDataSources(this.getDataSources());
		dataSource.afterPropertiesSet();
		return dataSource;
	}

	private Map<Object, Object> getDataSources() {
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
