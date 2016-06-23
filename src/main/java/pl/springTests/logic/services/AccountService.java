package pl.springTests.logic.services;

import pl.springTests.logic.entities.Account;
import pl.springTests.logic.lists.AccountList;

public interface AccountService {

	Account deleteAccount(Long accountId);
	Account updateAccount(Long accountId, Account account);
	
    Account findAccount(Long id);
    Account findAccountByUsername(String userName);
    
}
