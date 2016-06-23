package pl.springTests.logic.services.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.springTests.logic.entities.Account;
import pl.springTests.logic.entities.Book;
import pl.springTests.logic.exceptions.AccountNotFoundException;
import pl.springTests.logic.exceptions.BookExistsException;
import pl.springTests.logic.exceptions.BookNotFoundException;
import pl.springTests.logic.lists.BookList;
import pl.springTests.logic.repository.AccountRepository;
import pl.springTests.logic.repository.BookRepository;
import pl.springTests.logic.services.BookService;

@Service
@Transactional
public class BookServiceImpl implements BookService{
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private AccountRepository accountRepository;

	@Override
	public Book createBook(Long accountId, Book book) {
		Book findBookByTitle = findBookByTitle(book.getTitle());
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

	@Override
	public Book deleteBook(Long bookId) {
//		Book findBook = findBook(bookId);
//		if (findBook == null){
//			throw new BookNotFoundException();
//		}
		return bookRepository.deleteBook(bookId);
	}

	@Override
	public Book updateBook(Long bookId, Book book) {
		return bookRepository.updateBook(bookId, book);
	}

	@Override
	public BookList findAllBooks() {
		return new BookList(bookRepository.findAllBooks());
	}

	@Override
	public Book findBook(Long id) {
		return bookRepository.findBook(id);
	}

	@Override
	public Book findBookByTitle(String title) {
		return bookRepository.findBookByTitle(title);
	}

	@Override
	public BookList findBooksByAccount(Long accountId) {
		Account findAccount = accountRepository.findAccount(accountId);
		if (findAccount == null){
			throw new AccountNotFoundException();
		}
		return new BookList(bookRepository.findBooksByAccount(accountId));
	}
	
	

}
