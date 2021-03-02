package effyis.partners.socle.datasource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author EL KOTB ZAKARIA
 *
 */
@Configuration
@ConfigurationProperties(prefix = "config")
@EnableConfigurationProperties
public class DataSourceConfigurationProperties {

	private List<DataSourceDTO> datasources = new ArrayList<DataSourceConfigurationProperties.DataSourceDTO>();

	public List<DataSourceDTO> getDatasources() {
		return this.datasources;
	}

	public void setDatasources(List<DataSourceDTO> datasources) {
		this.datasources = datasources;
	}

	public DataSourceConfigurationProperties() {
		super();
	}

	public static class DataSourceDTO {
		private String url;
		private String username;
		private String password;
		private String client;

		public String getUrl() {
			return this.url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getUsername() {
			return this.username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return this.password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getClient() {
			return this.client;
		}

		public void setClient(String client) {
			this.client = client;
		}

	}
}
