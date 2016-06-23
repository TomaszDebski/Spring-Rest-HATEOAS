package pl.springTests.logic.services.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.springTests.logic.entities.Account;
import pl.springTests.logic.entities.User;
import pl.springTests.logic.exceptions.AccountFoundException;
import pl.springTests.logic.exceptions.AccountNotFoundException;
import pl.springTests.logic.exceptions.UserNotFoundException;
import pl.springTests.logic.lists.AccountList;
import pl.springTests.logic.repository.AccountRepository;
import pl.springTests.logic.repository.UserRepository;
import pl.springTests.logic.services.AccountService;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public Account deleteAccount(Long accountId) {
		return accountRepository.deleteAccount(accountId);
	}

	@Override
	public Account updateAccount(Long accountId, Account account) {
		Account findAccount = accountRepository.findAccount(accountId);
		if (findAccount == null){
			throw new AccountNotFoundException();
		}
		return accountRepository.updateAccount(accountId, account);
	}

	
	@Override
	public Account findAccount(Long id) {
		return accountRepository.findAccount(id);
	}

	@Override
	public Account findAccountByUsername(String userName) {
		return accountRepository.findAccountByUsername(userName);
	}

	

}
