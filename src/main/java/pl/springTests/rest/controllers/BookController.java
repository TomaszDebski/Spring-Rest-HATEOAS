package pl.springTests.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.springTests.logic.entities.Book;
import pl.springTests.logic.repository.BookRepository;
import pl.springTests.logic.services.BookService;
import pl.springTests.rest.resource.BookResource;
import pl.springTests.rest.resource.assemblers.BookResourceAssembler;

@Controller
@RequestMapping("/library/books")
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	@RequestMapping(value="/{bookId}",method=RequestMethod.GET)
	public ResponseEntity<BookResource> getBook(@PathVariable Long bookId){
		Book findBook = bookService.findBook(bookId);
		if (findBook == null){
			return new ResponseEntity<BookResource>(HttpStatus.NOT_FOUND);
		}else{
			BookResource resource = new BookResourceAssembler().toResource(findBook);
			return new ResponseEntity<BookResource>(resource,HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/{bookId}",method=RequestMethod.DELETE)
	public ResponseEntity<BookResource> deleteBook(@PathVariable Long bookId){
		Book deleteBook = bookService.deleteBook(bookId);
		if (deleteBook == null){
			return new ResponseEntity<BookResource>(HttpStatus.NOT_FOUND);
		}else{
			BookResource resource = new BookResourceAssembler().toResource(deleteBook);
			return new ResponseEntity<BookResource>(resource,HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/{bookId}",method=RequestMethod.PUT)
	public ResponseEntity<BookResource> updateBook(@PathVariable Long bookId,
			@RequestBody BookResource bookResource){
		Book updateBook = bookService.updateBook(bookId, bookResource.toBook());
		if (updateBook == null){
			return new ResponseEntity<BookResource>(HttpStatus.NOT_FOUND);
		}else{
			BookResource resource = new BookResourceAssembler().toResource(updateBook);
			return new ResponseEntity<BookResource>(resource,HttpStatus.OK);
		}
	}
	
//	findAllAuthor
//	createAuthor
//	findAuthorsbyBook
}
