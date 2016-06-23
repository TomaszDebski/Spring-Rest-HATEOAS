package pl.springTests.rest.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.springTests.logic.entities.Account;
import pl.springTests.logic.entities.User;
import pl.springTests.logic.exceptions.AccountFoundException;
import pl.springTests.logic.exceptions.UserExistsException;
import pl.springTests.logic.exceptions.UserNotFoundException;
import pl.springTests.logic.lists.AccountList;
import pl.springTests.logic.lists.UserList;
import pl.springTests.logic.services.AccountService;
import pl.springTests.logic.services.UserService;
import pl.springTests.rest.exceptions.ConflictException;
import pl.springTests.rest.exceptions.NotFoundException;
import pl.springTests.rest.resource.AccountListResource;
import pl.springTests.rest.resource.AccountResource;
import pl.springTests.rest.resource.UserListResource;
import pl.springTests.rest.resource.UserResource;
import pl.springTests.rest.resource.assemblers.AccountListResourceAssembler;
import pl.springTests.rest.resource.assemblers.AccountResourceAssembler;
import pl.springTests.rest.resource.assemblers.UserListResourceAssembler;
import pl.springTests.rest.resource.assemblers.UserResourceAssembler;

@Controller
@RequestMapping("/library/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired 
	private AccountService accountService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<UserListResource> findAllUsers(){
		UserList allUsers = userService.findAllUsers();
		UserListResource resource = new UserListResourceAssembler().toResource(allUsers);
		return new ResponseEntity<UserListResource>(resource,HttpStatus.OK);
	}
	
	@RequestMapping(value="/{userId}",method=RequestMethod.GET)
	public ResponseEntity<UserResource> getUser(@PathVariable Long userId){
		try {
			User findedUser = userService.findUser(userId);
			UserResource resource = new UserResourceAssembler().toResource(findedUser);
			return new ResponseEntity<UserResource>(resource,HttpStatus.OK);
		} catch (UserNotFoundException exception) {
			throw new NotFoundException();
		}
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<UserResource> createUser(@RequestBody UserResource userResource){
		try{
			User createduser = userService.createUser(userResource.createUser());
			UserResource resource = new UserResourceAssembler().toResource(createduser);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create(resource.getLink("self").getHref()));
			return new ResponseEntity<UserResource>(resource,headers,HttpStatus.CREATED);
		}catch(UserExistsException exception){
			throw new ConflictException("Konflikt nazw User");
		}
	}
	
	@RequestMapping(value="/{userId}/accounts",method=RequestMethod.POST)
	public ResponseEntity<AccountResource> createAccount(@PathVariable Long userId,
			@RequestBody AccountResource accountResource){
		try{
			Account createAccount = userService.createAccount(userId,accountResource.toAccount());
			AccountResource resource = new AccountResourceAssembler().toResource(createAccount);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create(resource.getLink("self").getHref()));
			return new ResponseEntity<AccountResource>(resource,headers,HttpStatus.CREATED);
		}catch(AccountFoundException e){
			throw new ConflictException();
		}catch(UserNotFoundException e){
			throw new NotFoundException();
		}
	}
	
	@RequestMapping(value="/{userId}/accounts",method=RequestMethod.GET)
	public ResponseEntity<AccountListResource> findAllAccounts(@PathVariable Long userId){
		try{
		AccountList findAllAccounts = userService.findAccountsByUser(userId);
		AccountListResource ListResource = 
						new AccountListResourceAssembler().toResource(findAllAccounts);
		return new ResponseEntity<AccountListResource>(ListResource,HttpStatus.OK);
		}catch(UserNotFoundException e){
			throw new NotFoundException();
		}
		
	}

}
