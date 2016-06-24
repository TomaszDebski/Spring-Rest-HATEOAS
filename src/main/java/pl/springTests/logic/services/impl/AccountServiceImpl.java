package pl.springTests.logic.services.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.springTests.logic.entities.Account;
import pl.springTests.logic.entities.Book;
import pl.springTests.logic.exceptions.AccountNotFoundException;
import pl.springTests.logic.exceptions.BookExistsException;
import pl.springTests.logic.lists.BookList;
import pl.springTests.logic.repository.AccountRepository;
import pl.springTests.logic.repository.BookRepository;
import pl.springTests.logic.repository.UserRepository;
import pl.springTests.logic.services.AccountService;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private BookRepository bookRepository;

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
	
	@Override
	public BookList findBooksByAccount(Long accountId) {
		Account findAccount = accountRepository.findAccount(accountId);
		if (findAccount == null){
			throw new AccountNotFoundException();
		}
		return new BookList(bookRepository.findBooksByAccount(accountId));
	}

	@Override
	public Book createBook(Long accountId, Book book) {
		Book findBookByTitle = bookRepository.findBookByTitle(book.getTitle());
		if (findBookByTitle != null){
			throw new BookExistsException();
		}
		Account findAccount = accountRepository.findAccount(accountId);
		if (findAccount == null){
			throw new AccountNotFoundException();
		}
		Book createBook = bookRepository.createBook(book);
		createBook.setAccount(findAccount);
		return createBook;
	}
	

}
