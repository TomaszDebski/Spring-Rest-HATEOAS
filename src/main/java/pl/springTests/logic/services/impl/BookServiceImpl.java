package pl.springTests.logic.services.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.springTests.logic.entities.Author;
import pl.springTests.logic.entities.Book;
import pl.springTests.logic.exceptions.AuthorExistsException;
import pl.springTests.logic.exceptions.BookNotFoundException;
import pl.springTests.logic.lists.AuthorList;
import pl.springTests.logic.lists.BookList;
import pl.springTests.logic.repository.AuthorRepository;
import pl.springTests.logic.repository.BookRepository;
import pl.springTests.logic.services.BookService;

/**
 * @author Tomasz Dębski
 *
 */

@Service
@Transactional
public class BookServiceImpl implements BookService{
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private AuthorRepository authorRepository;

	@Override
	public Book deleteBook(Long bookId) {
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
	public AuthorList findAllAuthorsByBookId(Long bookId) {
		Book findedBook = bookRepository.findBook(bookId);
		if (findedBook == null){
			throw new BookNotFoundException();
		}else{
			return new AuthorList(authorRepository.findAuthorsByBook(bookId));			
		}
	}

	@Override
	public Author createAuthor(Long bookId, Author author) {
		Book findedBook = bookRepository.findBook(bookId);
		if (findedBook == null){
			throw new BookNotFoundException();
		}
		Author findAuthorByName = authorRepository.findAuthorByName(author.getName());
		if (findAuthorByName != null){
			throw new AuthorExistsException();
		}
			author.setBook(findedBook);
			return authorRepository.createAuthor(author);
	}

}
