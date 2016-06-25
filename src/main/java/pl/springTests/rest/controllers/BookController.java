package pl.springTests.rest.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.springTests.logic.entities.Author;
import pl.springTests.logic.entities.Book;
import pl.springTests.logic.exceptions.AuthorExistsException;
import pl.springTests.logic.exceptions.BookNotFoundException;
import pl.springTests.logic.lists.AuthorList;
import pl.springTests.logic.services.BookService;
import pl.springTests.rest.exceptions.ConflictException;
import pl.springTests.rest.exceptions.NotFoundException;
import pl.springTests.rest.resource.AuthorListResource;
import pl.springTests.rest.resource.AuthorResource;
import pl.springTests.rest.resource.BookResource;
import pl.springTests.rest.resource.assemblers.AuthorListResourceAssembler;
import pl.springTests.rest.resource.assemblers.AuthorResourceAssembler;
import pl.springTests.rest.resource.assemblers.BookResourceAssembler;

/**
 * @author Tomasz Dębski
 *
 */

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
	
	@RequestMapping(value="/{bookId}/authors",method=RequestMethod.POST)
	public ResponseEntity<AuthorResource> createAuthor(@PathVariable Long bookId,
			@RequestBody AuthorResource authorResource){
		try{
		Author createAuthor = bookService.createAuthor(bookId, authorResource.toAuthor());
		AuthorResource resource = new AuthorResourceAssembler().toResource(createAuthor);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create(resource.getLink("self").getHref()));
		return new ResponseEntity<AuthorResource>(resource,headers,HttpStatus.CREATED);
		}catch(BookNotFoundException e){
			throw new NotFoundException();
		}catch(AuthorExistsException e){
			throw new ConflictException();
		}
		
	}
	
	@RequestMapping(value="/{bookId}/authors",method=RequestMethod.GET)
	public ResponseEntity<AuthorListResource> findAuthorsbyBook(@PathVariable Long bookId){
		try{
			AuthorList findedAuthors = bookService.findAllAuthorsByBookId(bookId);
			AuthorListResource resource = new AuthorListResourceAssembler().toResource(findedAuthors);
			return new ResponseEntity<AuthorListResource>(resource,HttpStatus.OK);
		}catch(BookNotFoundException e ){
			throw new NotFoundException();
		}
	}
	
}
