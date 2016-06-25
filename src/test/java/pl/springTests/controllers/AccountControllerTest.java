package pl.springTests.controllers;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import pl.springTests.SpringTestWithHateosApplication;
import pl.springTests.logic.entities.Account;
import pl.springTests.logic.entities.Book;
import pl.springTests.logic.exceptions.AccountNotFoundException;
import pl.springTests.logic.exceptions.BookExistsException;
import pl.springTests.logic.lists.BookList;
import pl.springTests.logic.services.AccountService;
import pl.springTests.rest.controllers.AccountController;

/**
 * @author Tomasz Dębski
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringTestWithHateosApplication.class)
@WebAppConfiguration
public class AccountControllerTest {

	@InjectMocks
	private AccountController accountController;

	@Mock
	private AccountService accountService;

	MockMvc mockMvc;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		mockMvc = MockMvcBuilders.standaloneSetup(accountController)
				.build();
	}

	@Test
	public void findAllBooksForAccounts() throws Exception {

		Account account = new Account();
		account.setId(1L);
		account.setUsername("user");
		account.setPassword("password");

		List<Book> books = new ArrayList<Book>();
		Book book1 = new Book();
		book1.setId(1L);
		book1.setTitle("title1");

		Book book2 = new Book();
		book2.setId(2L);
		book2.setTitle("title2");
		books.add(book1);
		books.add(book2);

		BookList bookList = new BookList(books);

		when(accountService.findBooksByAccount(1L)).thenReturn(bookList);

		mockMvc.perform(get("/library/accounts/1/books"))
				.andDo(print())
				.andExpect(
						jsonPath(
								"$.books[*].title",
								hasItems(endsWith("title1"), endsWith("title2"))))
				.andExpect(status().isOk());
	}
	
	@Test
	public void findBlogsForNonExistingAccount() throws Exception{
		when(accountService.findBooksByAccount(1L)).thenThrow(new AccountNotFoundException());
		
		mockMvc.perform(get("/library/accounts/1/books"))
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void createBookExistingAccount() throws Exception {
		Book book = new Book();
		book.setId(1L);
		book.setTitle("title1");
		
		when(accountService.createBook(eq(1L), any(Book.class))).thenReturn(book);
		
		mockMvc.perform(post("/library/accounts/1/books")
				.content("{\"title\":\"title1\"}")
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
			.andExpect(jsonPath("$.title", is("title1")))
			.andExpect(jsonPath("$.links[*].href", hasItems(endsWith("books/1"))))
			.andExpect(header().string("Location", endsWith("books/1")))
			.andExpect(status().isCreated());
	}
	
	@Test
	public void createBookForNonExistingAccount() throws Exception {
		when(accountService.createBook(eq(1L), any(Book.class))).thenThrow(new AccountNotFoundException());
		
		mockMvc.perform(post("/library/accounts/1/books")
				.content("{\"title\":\"title1\"}")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void createBookForExistingBookName() throws Exception {
		when(accountService.createBook(eq(1L), any(Book.class))).thenThrow(new BookExistsException());
		
		mockMvc.perform(post("/library/accounts/1/books")
				.content("{\"title\":\"title1\"}")
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
		.andExpect(status().isConflict());
	}
	
	@Test
	public void getExistingAccount() throws Exception {
		Account account = new Account();
		account.setId(1L);
		account.setUsername("username");
		account.setPassword("password");
		
		when(accountService.findAccount(1L)).thenReturn(account);
		
		mockMvc.perform(get("/library/accounts/1"))
		.andDo(print())
		.andExpect(jsonPath("$.username", is("username")))
		.andExpect(jsonPath("$.links[*].rel", hasItems(is("self"),is("user"))))
		.andExpect(jsonPath("$.links[*].href", hasItems(endsWith("accounts/1"))))
		.andExpect(status().isOk());
	}
	
	@Test
	public void getNonExistingAccount() throws Exception {
		when(accountService.findAccount(1L)).thenReturn(null);
		
		mockMvc.perform(get("/library/accounts/1"))
		.andExpect(status().isNotFound());
	}
}
