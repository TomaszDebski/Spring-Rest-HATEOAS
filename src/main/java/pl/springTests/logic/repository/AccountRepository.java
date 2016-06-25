package pl.springTests.logic.repository;

import java.util.List;

import pl.springTests.logic.entities.Account;

/**
 * @author Tomasz Dębski
 *
 */

public interface AccountRepository {

	
	Account deleteAccount(Long accountId);
	Account updateAccount(Long accountId, Account account);
	
    Account findAccount(Long id);
    Account findAccountByUsername(String userName);
    
}
