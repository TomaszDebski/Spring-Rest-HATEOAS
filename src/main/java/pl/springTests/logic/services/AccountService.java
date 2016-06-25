package pl.springTests.logic.services;

import pl.springTests.logic.entities.Account;
import pl.springTests.logic.entities.Book;
import pl.springTests.logic.lists.BookList;

/**
 * @author Tomasz Dębski
 *
 */

public interface AccountService {

	Account deleteAccount(Long accountId);
	Account updateAccount(Long accountId, Account account);
	
    Account findAccount(Long id);
    Account findAccountByUsername(String userName);
    
    BookList findBooksByAccount(Long accountId);
    Book createBook(Long accountId,Book data);
    
}
