package effyis.partners.socle.datasource;

/**
 * 
 * @author EL KOTB ZAKARIA
 *
 */
public class TenantStorageContext {

	private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();

	public static void setTenantId(String tenantId) {
		TenantStorageContext.CONTEXT.set(tenantId);
	}

	public static String getTenantId() {
		return TenantStorageContext.CONTEXT.get();
	}

	public static void clear() {
		TenantStorageContext.CONTEXT.remove();
	}
}
