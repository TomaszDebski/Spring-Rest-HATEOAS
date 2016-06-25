package pl.springTests.logic.services;

import pl.springTests.logic.entities.Author;
import pl.springTests.logic.entities.Book;
import pl.springTests.logic.lists.AuthorList;
import pl.springTests.logic.lists.BookList;

/**
 * @author Tomasz Dębski
 *
 */

public interface BookService {

	Book deleteBook(Long bookId);
	Book updateBook(Long bookId, Book book);
	
	BookList findAllBooks();
	Book findBook(Long id);
	Book findBookByTitle(String title);
	
	AuthorList findAllAuthorsByBookId(Long bookId);
	Author createAuthor(Long bookId, Author author);
    
}
