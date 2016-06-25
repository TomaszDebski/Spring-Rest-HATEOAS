package pl.springTests.logic.services;

import pl.springTests.logic.entities.Account;
import pl.springTests.logic.entities.User;
import pl.springTests.logic.lists.AccountList;
import pl.springTests.logic.lists.UserList;

/**
 * @author Tomasz Dębski
 *
 */

public interface UserService {

	UserList findAllUsers();
    User findUser(Long id);
    User findUserByName(String name);
    User createUser(User user);
    
	Account createAccount(Long userId, Account data);
	AccountList findAllAccounts();
	AccountList findAccountsByUser(Long userId);


}
