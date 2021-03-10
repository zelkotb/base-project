package effyis.partners.socle.dto;

/**
 * 
 * @author Elkotb Zakaria
 *
 */
public class JwtDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;
	private String jwt;

	public JwtDTO() {
		super();
	}

	@Override
	public String toString() {
		StringBuilder audit = new StringBuilder("{jwt : ");
		return audit.append(this.jwt).append(" }").toString();
	}

	public String getJwt() {
		return this.jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

}
