package pl.springTests.rest.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

public class AccountListResource extends ResourceSupport {
	
	List<AccountResource> accounts = new ArrayList<>();

	public List<AccountResource> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<AccountResource> accounts) {
		this.accounts = accounts;
	}
	
	

}
