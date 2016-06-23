package pl.springTests.rest.resource.assemblers;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import pl.springTests.logic.entities.Book;
import pl.springTests.rest.controllers.BookController;
import pl.springTests.rest.resource.BookResource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

public class BookResourceAssembler extends ResourceAssemblerSupport<Book, BookResource>{

	public BookResourceAssembler() {
		super(BookController.class, BookResource.class);
	}

	@Override
	public BookResource toResource(Book book) {
		BookResource bookResource = new BookResource();
		bookResource.setTitle(book.getTitle());
		bookResource.add(linkTo(methodOn(BookController.class).getBook(book.getId())).withSelfRel());
		bookResource.add(linkTo(BookController.class).slash(book.getId()).slash("authors").withRel("authors"));
		return bookResource;
	}

}
