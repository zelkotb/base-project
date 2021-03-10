package effyis.partners.socle.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import effyis.partners.socle.dto.AccountDTO;
import effyis.partners.socle.dto.AuthenticationDTO;
import effyis.partners.socle.dto.JwtDTO;
import effyis.partners.socle.entity.Account;
import effyis.partners.socle.repository.AccountRepository;
import effyis.partners.socle.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * 
 * @author EL KOTB ZAKARIA
 *
 */
@RestController
@RequestMapping("/effyis/api/accounts")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private AccountRepository rep;

	@PostMapping("/login")
	@Operation(security = { @SecurityRequirement(name = "Bearer Token") })
	public JwtDTO authenticate(@RequestBody AuthenticationDTO authenticationDTO) throws Exception {
		return this.accountService.authenticateUser(authenticationDTO);
	}

	@PutMapping("/{id}")
	@Operation(security = { @SecurityRequirement(name = "Bearer Token") })
	public Account updateAccount(@PathVariable Long id, @Valid @RequestBody Account authenticationDTO)
			throws Exception {
		authenticationDTO.setId(id);
		return this.rep.save(authenticationDTO);
	}

	@GetMapping()
	public List<AccountDTO> getAccounts() {
		return this.accountService.getAccounts();
	}
}
