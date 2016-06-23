package pl.springTests.logic.repository;

import java.util.List;

import pl.springTests.logic.entities.Author;
import pl.springTests.logic.entities.Book;
import pl.springTests.logic.lists.AuthorList;

public interface BookRepository {

	Book createBook(Book data);
	Book deleteBook(Long bookId);
	Book updateBook(Long bookId, Book book);
	
	List<Book> findAllBooks();
	Book findBook(Long id);
	Book findBookByTitle(String title);
    List<Book> findBooksByAccount(Long accountId);
    
	Author createAuthor(Author author);
	AuthorList findAllAuthors();
	
	AuthorList findAuthorsByBook(Long bookId);
}
