package pl.springTests.logic.lists;

import java.util.List;

import pl.springTests.logic.entities.Book;

/**
 * @author Tomasz Dębski
 *
 */

public class BookList {

	private List<Book> books;
	
	public BookList(List<Book> books){
		this.books = books;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}
	
	
}
