package pl.springTests.logic.repository;

import java.util.List;

import pl.springTests.logic.entities.Account;
import pl.springTests.logic.entities.User;

/**
 * @author Tomasz Dębski
 *
 */

public interface UserRepository {
	
	List<User> findAllUsers();
    User findUser(Long id);
    User findUserByName(String name);
    User createUser(User user);
    
    Account createAccount(Account data);
	List<Account> findAllAccounts();
	List<Account> findAccountsByUser(Long userId);

}
