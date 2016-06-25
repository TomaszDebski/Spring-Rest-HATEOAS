package pl.springTests.logic.lists;

import java.util.List;

import pl.springTests.logic.entities.Account;

/**
 * @author Tomasz Dębski
 *
 */

public class AccountList {
	
	List<Account> accounts;
	
	public AccountList(List<Account> accounts){
		this.accounts = accounts;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
	
	

}
