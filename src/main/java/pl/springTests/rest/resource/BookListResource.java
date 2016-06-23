package pl.springTests.rest.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

public class BookListResource extends ResourceSupport{
	
	List<BookResource>  books = new ArrayList<>();

	public List<BookResource> getBooks() {
		return books;
	}

	public void setBooks(List<BookResource> books) {
		this.books = books;
	}
	
	

}
