package effyis.partners.socle.exception;

/**
 * 
 * @author EL KOTB ZAKARIA
 *
 */
public class DataSourceNotFoundException extends IllegalStateException {

	private static final long serialVersionUID = 1L;
	private final String keyDB;

	public DataSourceNotFoundException(String keyDB) {
		super("DataSource with key : " + keyDB + " Not Found");
		this.keyDB = keyDB;
	}

	public String getKeyDB() {
		return this.keyDB;
	}

}
