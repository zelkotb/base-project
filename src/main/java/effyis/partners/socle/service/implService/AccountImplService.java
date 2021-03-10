package effyis.partners.socle.service.implService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import effyis.partners.socle.configuration.security.JWTProvider;
import effyis.partners.socle.dto.AccountDTO;
import effyis.partners.socle.dto.AuthenticationDTO;
import effyis.partners.socle.dto.JwtDTO;
import effyis.partners.socle.entity.Account;
import effyis.partners.socle.repository.AccountRepository;
import effyis.partners.socle.service.AccountService;

/**
 * 
 * @author EL KOTB ZAKARIA
 *
 */
@Service
public class AccountImplService implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AuthenticationManager authenticationManger;

	@Value("${security.jwt.secret}")
	private String secret;

	@Value("${security.jwt.expiration.time}")
	private long expirationTime;

	@Value("${default.role}")
	private String defaultRole;

	private String keyDB = "keyDB";

	@Autowired
	private HttpServletRequest request;

	@Override
	public Optional<Account> findByLogin(String login) {
		return this.accountRepository.findByLogin(login);
	}

	@Override
	public List<AccountDTO> getAccounts() {
		return this.accountRepository.findAll().stream().map(account -> this.modelMapper.map(account, AccountDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public JwtDTO authenticateUser(AuthenticationDTO authenticationDTO) {
		this.authenticationManger.authenticate(
				new UsernamePasswordAuthenticationToken(authenticationDTO.getLogin(), authenticationDTO.getPassword()));
		Account account = this.findByLogin(authenticationDTO.getLogin()).orElse(null);
		String jwt = JWTProvider.generateJWT(authenticationDTO.getLogin(),
				((account != null) && (account.getRole() != null)) ? account.getRole().getRole() : this.defaultRole,
				this.secret, this.expirationTime,
				((this.request.getHeader(this.keyDB) == null) || this.request.getHeader(this.keyDB).isEmpty())
						? "client1"
						: this.request.getHeader(this.keyDB));
		JwtDTO jwtDTO = new JwtDTO();
		jwtDTO.setJwt(jwt);
		return jwtDTO;
	}
}
