package pl.springTests.controllers;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import pl.springTests.SpringTestWithHateosApplication;
import pl.springTests.logic.entities.Account;
import pl.springTests.logic.entities.User;
import pl.springTests.logic.exceptions.AccountFoundException;
import pl.springTests.logic.exceptions.UserExistsException;
import pl.springTests.logic.exceptions.UserNotFoundException;
import pl.springTests.logic.lists.AccountList;
import pl.springTests.logic.lists.UserList;
import pl.springTests.logic.services.UserService;
import pl.springTests.rest.controllers.UserController;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringTestWithHateosApplication.class)
@WebAppConfiguration
public class UserControllerTest {

	@InjectMocks
	UserController userController;
	
	@Mock
	UserService userService;
	
	MockMvc mockMvc;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}
	
	@Test
	public void findAllAccountsForUser() throws Exception{
		
		List<Account> accounts = new ArrayList<>();
		Account account1 = new Account();
		account1.setId(1L);
		account1.setUsername("username1");
		account1.setPassword("password1");
		Account account2 = new Account();
		account2.setId(2L);
		account2.setUsername("username2");
		account2.setPassword("password2");
		accounts.add(account1);
		accounts.add(account2);
		AccountList accountList = new AccountList(accounts);
		when(userService.findAccountsByUser(1L)).thenReturn(accountList);
		
		mockMvc.perform(get("/library/users/1/accounts"))
		.andDo(print())
		.andExpect(jsonPath("$.accounts[*].username",
					hasItems(endsWith("username1"),endsWith("username2"))))
		.andExpect(status().isOk());
	}
	
	@Test
	public void findAllBlogsForNonExistingUser() throws Exception{
		when(userService.findAccountsByUser(1L)).thenThrow(new UserNotFoundException());
		
		mockMvc.perform(get("/library/users/1/accounts"))
		.andDo(print())
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void createAccountExistingUser() throws Exception{
		Account account = new Account();
		account.setId(1L);
		account.setUsername("username1");
		account.setPassword("password1");
		
		when(userService.createAccount(eq(1L), any(Account.class))).thenReturn(account);
		
		mockMvc.perform(post("/library/users/1/accounts")
				.content("{\"username\":\"username1\",\"password\":\"password1\"}")
				.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$.username", is("username1")))
		.andExpect(jsonPath("$.password", is("password1")))
		.andExpect(jsonPath("$.links[*].href", hasItems(endsWith("/accounts/1"))))
		.andExpect(header().string("Location", endsWith("/accounts/1")))
		.andExpect(status().isCreated());
	}
	
	@Test
	public void createAccountNonExistingUser() throws Exception{
		when(userService.createAccount(eq(1L), any(Account.class))).thenThrow(new UserNotFoundException());
		
		mockMvc.perform(post("/library/users/1/accounts")
				.content("{\"username\":\"username1\",\"password\":\"password1\"}")
				.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void createAccountExistingAccountName() throws Exception{
		when(userService.createAccount(eq(1L), any(Account.class))).thenThrow(new AccountFoundException());
		
		mockMvc.perform(post("/library/users/1/accounts")
				.content("{\"username\":\"username1\",\"password\":\"password1\"}")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isConflict());
		
	}
	
	@Test
	public void createAccountNonExistingUsername() throws Exception{
		Account account = new Account();
		account.setId(1L);
		account.setUsername("username1");
		account.setPassword("password1");
		
		when(userService.createAccount(eq(1L), any(Account.class))).thenReturn(account);
		
		mockMvc.perform(post("/library/users/1/accounts")
				.content("{\"username\":\"username1\",\"password\":\"password1\"}")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.username", is("username1")))
		.andExpect(jsonPath("$.links[*].href", hasItems(endsWith("/accounts/1"))))
		.andExpect(header().string("Location", endsWith("/accounts/1")))
		.andExpect(status().isCreated());
	}
	
	@Test
	public void createUserNonExistingName() throws Exception{
		User user = new User();
		user.setId(1L);
		user.setName("name1");
		
		when(userService.createUser(any(User.class))).thenReturn(user);
		
		mockMvc.perform(post("/library/users")
				.content("{\"name\":\"name1\"}")
				.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$.name", is(user.getName())))
		.andExpect(header().string("Location", endsWith("/users/1")))
		.andExpect(status().isCreated())
		;
	}
	
	@Test
	public void createUserExistingUser() throws Exception{
		when(userService.createUser(any(User.class))).thenThrow(new UserExistsException());
		
		mockMvc.perform(post("/library/users")
				.content("{\"name\":\"name1\"}")
				.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isConflict())
		;
	}
	
	@Test
	public void getExistingUser() throws Exception{
		User user = new User();
		user.setId(1L);
		user.setName("name1");
		
		when(userService.findUser(1L)).thenReturn(user);
		
		mockMvc.perform(get("/library/users/1"))
		.andDo(print())
		.andExpect(jsonPath("$.name", is("name1")))
		.andExpect(jsonPath("$.links[*].href", hasItems(endsWith("/users/1"))))
		.andExpect(jsonPath("$.links[*].rel", hasItems(endsWith("self"),endsWith("accounts"))))
		.andExpect(status().isOk());
	}
	
	@Test
	public void getNonExistingUser() throws Exception{
		when(userService.findUser(1L)).thenThrow(new UserNotFoundException());
		
		mockMvc.perform(get("/library/users/1"))
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void findAllExistingUsers() throws Exception{
		User user1 = new User();
		user1.setId(1L);
		user1.setName("name1");
		
		User user2 = new User();
		user2.setId(2L);
		user2.setName("name2");
		
		
		List<User> users = new ArrayList<>();
		users.add(user1);
		users.add(user2);
		UserList userList = new UserList(users);
		
		when(userService.findAllUsers()).thenReturn(userList);
		
		mockMvc.perform(get("/library/users"))
		.andDo(print())
		.andExpect(jsonPath("$.users[*].name", hasItems(is("name1"),is("name2"))))
		.andExpect(status().isOk());
	}
	
}
