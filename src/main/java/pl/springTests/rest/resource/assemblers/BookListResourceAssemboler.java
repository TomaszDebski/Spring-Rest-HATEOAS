package pl.springTests.rest.resource.assemblers;

import java.util.List;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import pl.springTests.logic.lists.BookList;
import pl.springTests.rest.controllers.BookController;
import pl.springTests.rest.resource.BookListResource;
import pl.springTests.rest.resource.BookResource;

/**
 * @author Tomasz Dębski
 *
 */
public class BookListResourceAssemboler extends ResourceAssemblerSupport<BookList, BookListResource> {

	public BookListResourceAssemboler() {
		super(BookController.class, BookListResource.class);
	}

	@Override
	public BookListResource toResource(BookList bookList) {
		List<BookResource> resource = new BookResourceAssembler().toResources(bookList.getBooks());
		BookListResource bookListResource = new BookListResource();
		bookListResource.setBooks(resource);
		return bookListResource;
	}

}
