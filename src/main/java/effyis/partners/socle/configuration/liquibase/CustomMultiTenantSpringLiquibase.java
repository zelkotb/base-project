package effyis.partners.socle.configuration.liquibase;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;

import effyis.partners.socle.configuration.datasource.DataSourceConfigurationProperties;
import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.MultiTenantSpringLiquibase;
import liquibase.integration.spring.SpringLiquibase;
import liquibase.logging.LogService;
import liquibase.logging.LogType;
import liquibase.logging.Logger;

/**
 * 
 * @author EL KOTB ZAKARIA
 *
 */
public class CustomMultiTenantSpringLiquibase implements InitializingBean, ResourceLoaderAware {

	private Map<String, DataSource> dataSources = new HashMap<String, DataSource>();
	private DataSourceConfigurationProperties properties;
	private Logger log = LogService.getLog(MultiTenantSpringLiquibase.class);
	private List<String> schemas;
	private ResourceLoader resourceLoader;
	private String changeLog;
	private String contexts;
	private String labels;
	private Map<String, String> parameters;
	private String defaultSchema;
	private String liquibaseSchema;
	private String liquibaseTablespace;
	private String databaseChangeLogTable;
	private String databaseChangeLogLockTable;
	private boolean dropFirst;
	private boolean clearCheckSums;
	private boolean shouldRun = true;
	private File rollbackFile;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.log.info(LogType.LOG, "DataSources based multitenancy enabled");
		this.runOnAllDataSources();
	}

	private void runOnAllDataSources() throws LiquibaseException {
		for (String client : this.dataSources.keySet()) {
			this.log.info(LogType.LOG, "Initializing Liquibase for data source " + this.dataSources.get(client));
			this.setChangeLog(this.properties.findByClient(client).getChangelog());
			this.setDropFirst(this.properties.findByClient(client).isDropFirst());
			SpringLiquibase liquibase = this.getSpringLiquibase(this.dataSources.get(client));
			liquibase.afterPropertiesSet();
			this.log.info(LogType.LOG, "Liquibase ran for data source " + this.dataSources.get(client));
		}
	}

	private SpringLiquibase getSpringLiquibase(DataSource dataSource) {
		SpringLiquibase liquibase = new SpringLiquibase();
		liquibase.setChangeLog(this.changeLog);
		liquibase.setChangeLogParameters(this.parameters);
		liquibase.setContexts(this.contexts);
		liquibase.setLabels(this.labels);
		liquibase.setDropFirst(this.dropFirst);
		liquibase.setClearCheckSums(this.clearCheckSums);
		liquibase.setShouldRun(this.shouldRun);
		liquibase.setRollbackFile(this.rollbackFile);
		liquibase.setResourceLoader(this.resourceLoader);
		liquibase.setDataSource(dataSource);
		liquibase.setDefaultSchema(this.defaultSchema);
		liquibase.setLiquibaseSchema(this.liquibaseSchema);
		liquibase.setLiquibaseTablespace(this.liquibaseTablespace);
		liquibase.setDatabaseChangeLogTable(this.databaseChangeLogTable);
		liquibase.setDatabaseChangeLogLockTable(this.databaseChangeLogLockTable);
		return liquibase;
	}

	public String getChangeLog() {
		return this.changeLog;
	}

	public void setChangeLog(String changeLog) {
		this.changeLog = changeLog;
	}

	public String getContexts() {
		return this.contexts;
	}

	public void setContexts(String contexts) {
		this.contexts = contexts;
	}

	public String getLabels() {
		return this.labels;
	}

	public void setLabels(String labels) {
		this.labels = labels;
	}

	public Map<String, String> getParameters() {
		return this.parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

	public String getDefaultSchema() {
		return this.defaultSchema;
	}

	public void setDefaultSchema(String defaultSchema) {
		this.defaultSchema = defaultSchema;
	}

	public String getLiquibaseSchema() {
		return this.liquibaseSchema;
	}

	public void setLiquibaseSchema(String liquibaseSchema) {
		this.liquibaseSchema = liquibaseSchema;
	}

	public String getLiquibaseTablespace() {
		return this.liquibaseTablespace;
	}

	public void setLiquibaseTablespace(String liquibaseTablespace) {
		this.liquibaseTablespace = liquibaseTablespace;
	}

	public String getDatabaseChangeLogTable() {
		return this.databaseChangeLogTable;
	}

	public void setDatabaseChangeLogTable(String databaseChangeLogTable) {
		this.databaseChangeLogTable = databaseChangeLogTable;
	}

	public String getDatabaseChangeLogLockTable() {
		return this.databaseChangeLogLockTable;
	}

	public void setDatabaseChangeLogLockTable(String databaseChangeLogLockTable) {
		this.databaseChangeLogLockTable = databaseChangeLogLockTable;
	}

	public boolean isDropFirst() {
		return this.dropFirst;
	}

	public void setDropFirst(boolean dropFirst) {
		this.dropFirst = dropFirst;
	}

	public boolean isClearCheckSums() {
		return this.clearCheckSums;
	}

	public void setClearCheckSums(boolean clearCheckSums) {
		this.clearCheckSums = clearCheckSums;
	}

	public boolean isShouldRun() {
		return this.shouldRun;
	}

	public void setShouldRun(boolean shouldRun) {
		this.shouldRun = shouldRun;
	}

	public File getRollbackFile() {
		return this.rollbackFile;
	}

	public void setRollbackFile(File rollbackFile) {
		this.rollbackFile = rollbackFile;
	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	public List<String> getSchemas() {
		return this.schemas;
	}

	public void setSchemas(List<String> schemas) {
		this.schemas = schemas;
	}

	public Map<String, DataSource> getDataSources() {
		return this.dataSources;
	}

	public void setDataSources(Map<String, DataSource> dataSources) {
		this.dataSources = dataSources;
	}

	public ResourceLoader getResourceLoader() {
		return this.resourceLoader;
	}

	public DataSourceConfigurationProperties getProperties() {
		return this.properties;
	}

	public void setProperties(DataSourceConfigurationProperties properties) {
		this.properties = properties;
	}

	public void populateDatasources(String client, DataSource datasource) {
		this.dataSources.put(client, datasource);
	}

}
