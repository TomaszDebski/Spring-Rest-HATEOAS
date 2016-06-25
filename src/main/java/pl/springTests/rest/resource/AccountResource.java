package pl.springTests.rest.resource;

import org.springframework.hateoas.ResourceSupport;

import pl.springTests.logic.entities.Account;

/**
 * @author Tomasz Dębski
 *
 */
public class AccountResource extends ResourceSupport{
	
	private String username;
	
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Account toAccount(){
		Account account = new Account();
		account.setUsername(username);
		account.setPassword(password);
		return account;
	}
}
