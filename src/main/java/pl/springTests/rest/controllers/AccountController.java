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
import org.springframework.web.bind.annotation.RequestParam;

import pl.springTests.logic.entities.Account;
import pl.springTests.logic.entities.Book;
import pl.springTests.logic.exceptions.AccountNotFoundException;
import pl.springTests.logic.exceptions.BookExistsException;
import pl.springTests.logic.lists.BookList;
import pl.springTests.logic.services.AccountService;
import pl.springTests.logic.services.BookService;
import pl.springTests.rest.exceptions.ConflictException;
import pl.springTests.rest.exceptions.NotFoundException;
import pl.springTests.rest.resource.AccountResource;
import pl.springTests.rest.resource.BookListResource;
import pl.springTests.rest.resource.BookResource;
import pl.springTests.rest.resource.assemblers.AccountResourceAssembler;
import pl.springTests.rest.resource.assemblers.BookListResourceAssemboler;
import pl.springTests.rest.resource.assemblers.BookResourceAssembler;

@Controller
@RequestMapping("/library/accounts")
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private BookService bookService;

	
	@RequestMapping(value="/{accountId}",method=RequestMethod.PUT)
	public ResponseEntity<AccountResource> updateAccount(@PathVariable Long accountId,
			@RequestBody AccountResource accountResource){
		Account account = accountService.updateAccount(accountId, accountResource.toAccount());
		if (account != null){
			AccountResource resource = new AccountResourceAssembler().toResource(account);
			return new ResponseEntity<AccountResource>(resource,HttpStatus.OK);
		}else{
			return new ResponseEntity<AccountResource>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value="/{accountId}",method=RequestMethod.GET)
	public ResponseEntity<AccountResource> getAccount(@PathVariable Long accountId){
		Account findAccount = accountService.findAccount(accountId);
		if (findAccount == null){
			return new ResponseEntity<AccountResource>(HttpStatus.NOT_FOUND);
		}else{
		AccountResource resource = new AccountResourceAssembler().toResource(findAccount);
		return new ResponseEntity<AccountResource>(resource,HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/{accountId}",method=RequestMethod.DELETE)
	public ResponseEntity<AccountResource> deleteAccount(@PathVariable Long accountId){
		Account findAccount = accountService.findAccount(accountId);
		if (findAccount == null){
			return new ResponseEntity<AccountResource>(HttpStatus.NOT_FOUND);
		}else{
		accountService.deleteAccount(accountId);	
		AccountResource resource = new AccountResourceAssembler().toResource(findAccount);
		return new ResponseEntity<AccountResource>(resource,HttpStatus.OK);
		}
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<AccountResource> getAccountbyUsername(@RequestParam String username){
		Account findAccountByUsername = accountService.findAccountByUsername(username);
		if (findAccountByUsername == null){
			return new ResponseEntity<AccountResource>(HttpStatus.NOT_FOUND);
		}else{
			AccountResource resource = new AccountResourceAssembler().toResource(findAccountByUsername);
			return new ResponseEntity<AccountResource>(resource,HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/{accountId}/books",method=RequestMethod.GET)
	public ResponseEntity<BookListResource> findAllBooksByAccount(@PathVariable Long accountId){
		try{
		BookList findBooksByAccount = accountService.findBooksByAccount(accountId);
		BookListResource bookListResource = 
				new BookListResourceAssemboler().toResource(findBooksByAccount);
		return new ResponseEntity<BookListResource>(bookListResource,HttpStatus.OK);
		}catch(AccountNotFoundException e){
			throw new NotFoundException();
		}
	}
	
	@RequestMapping(value="/{accountId}/books",method=RequestMethod.POST)
	public ResponseEntity<BookResource> createBook(@PathVariable Long accountId,
			@RequestBody BookResource bookResource){
		try{
			Book createBook = accountService.createBook(accountId, bookResource.toBook());
			BookResource resource = new BookResourceAssembler().toResource(createBook);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create(resource.getLink("self").getHref()));
		return new ResponseEntity<BookResource>(resource,headers,HttpStatus.CREATED);
		}catch(AccountNotFoundException e){
			throw new NotFoundException();
		}catch(BookExistsException e){
			throw new ConflictException();
		}
	}
}
