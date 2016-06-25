	package pl.springTests.logic.services.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.springTests.logic.entities.Account;
import pl.springTests.logic.entities.User;
import pl.springTests.logic.exceptions.AccountFoundException;
import pl.springTests.logic.exceptions.UserExistsException;
import pl.springTests.logic.exceptions.UserNotFoundException;
import pl.springTests.logic.lists.AccountList;
import pl.springTests.logic.lists.UserList;
import pl.springTests.logic.repository.AccountRepository;
import pl.springTests.logic.repository.UserRepository;
import pl.springTests.logic.services.UserService;

/**
 * @author Tomasz Dębski
 *
 */

@Service
@Transactional
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AccountRepository accountRepository;

	@Override
	public UserList findAllUsers() {
		return new UserList(userRepository.findAllUsers());
	}

	@Override
	public User findUser(Long id) {
		User user = userRepository.findUser(id);
		if(user == null){
			throw new UserNotFoundException("User not exists");
		}
		return user;
	}

	@Override
	public User findUserByName(String name) {
		User user = userRepository.findUserByName(name);
		if (user == null){
			throw new UserNotFoundException("User not exists");
		}
		return user;
	}

	@Override
	public User createUser(User user) {
		User findedUser = userRepository.findUserByName(user.getName());
		if (findedUser != null){
			throw new UserExistsException("User already exist");
		}
		return userRepository.createUser(user);
	}
	
	@Override
	public Account createAccount(Long userId, Account account) {
		Account findAccount = accountRepository.findAccountByUsername(account.getUsername());
		if (findAccount != null){
			throw new AccountFoundException();
		}
		User findUser = userRepository.findUser(userId);
		if (findUser == null){
			throw new UserNotFoundException();
		}
		account.setUser(findUser);
		return userRepository.createAccount(account);
	}
	
	@Override
	public AccountList findAllAccounts() {
		return new AccountList(userRepository.findAllAccounts());
	}
	
	@Override
	public AccountList findAccountsByUser(Long userId) {
		User findUser = findUser(userId);
		if (findUser == null){
			throw new UserNotFoundException();
		}
		return new AccountList(userRepository.findAccountsByUser(userId));
	}

}
