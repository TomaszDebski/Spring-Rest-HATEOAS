package pl.springTests.rest.resource;

import org.springframework.hateoas.ResourceSupport;

import pl.springTests.logic.entities.Book;

public class BookResource extends ResourceSupport {
	
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public Book toBook(){
		Book book = new Book();
		book.setTitle(title);
		return book;
	}
	
	

}
