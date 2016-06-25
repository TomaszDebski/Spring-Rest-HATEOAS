package pl.springTests.controllers;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import pl.springTests.logic.entities.Author;
import pl.springTests.logic.entities.Book;
import pl.springTests.logic.exceptions.AuthorExistsException;
import pl.springTests.logic.exceptions.BookNotFoundException;
import pl.springTests.logic.lists.AuthorList;
import pl.springTests.logic.services.BookService;
import pl.springTests.rest.controllers.BookController;

/**
 * @author Tomasz Dębski
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringTestWithHateosApplication.class)
@WebAppConfiguration
public class BookControllerTest {
	
	@InjectMocks
	private BookController bookController;
	
	@Mock
	private BookService bookService;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		
		mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
	}

	@Test
	public void getExistingBook() throws Exception {
		Book book = new Book();
		book.setId(1L);
		book.setTitle("title1");
		
		when(bookService.findBook(1L)).thenReturn(book);
		
		mockMvc.perform(get("/library/books/1"))
		.andDo(print())
		.andExpect(jsonPath("$.title", is("title1")))
		.andExpect(status().isOk());
	}
	
	@Test
	public void getNonExistingBook() throws Exception {
		when(bookService.findBook(1L)).thenReturn(null);
		
		mockMvc.perform(get("/library/books/1"))
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void deleteExistingBook() throws Exception {
		Book book = new Book();
		book.setId(1L);
		book.setTitle("title1");
		
		when(bookService.deleteBook(1L)).thenReturn(book);
		
		mockMvc.perform(delete("/library/books/1"))
		.andDo(print())
		.andExpect(jsonPath("$.title", is("title1")))
		.andExpect(jsonPath("$.links[*].href", hasItems(endsWith("/books/1"))))
		.andExpect(status().isOk());
	}
	
	@Test
	public void deleteNonExistingBook() throws Exception {
		when(bookService.deleteBook(1L)).thenReturn(null);
		
		mockMvc.perform(delete("/library/books/1"))
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void updateExistingBook() throws Exception {
		Book updatedBook = new Book();
		updatedBook.setId(1L);
		updatedBook.setTitle("title1");
		
		when(bookService.updateBook(eq(1L), any(Book.class))).thenReturn(updatedBook);
		
		mockMvc.perform(put("/library/books/1")
				.content("{\"title\":\"title1\"}")
				.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$.title", is("title1")))
		.andExpect(jsonPath("$.links[*].href", hasItems(endsWith("/books/1"))))
		.andExpect(status().isOk());
	}
	
	@Test
	public void updateNotExistingBook() throws Exception {
		when(bookService.updateBook(eq(1L), any(Book.class))).thenReturn(null);
		
		mockMvc.perform(put("/library/books/1")
				.content("{\"title\":\"title1\"}")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void createAuthorExistingBook() throws Exception {
		Book book = new Book();
		book.setId(1L);
		book.setTitle("title1");
		
		Author author = new Author();
		author.setId(1L);
		author.setName("name1");
		author.setBook(book);
		
		when(bookService.createAuthor(eq(1L), any(Author.class))).thenReturn(author);
		
		mockMvc.perform(post("/library/books/1/authors")
				.content("{\"name\":\"name1\"}")
				.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$.name", is("name1")))
		.andExpect(jsonPath("$.links[*].href", hasItems(endsWith("/authors/1"))))
		.andExpect(header().string("Location", endsWith("/authors/1")))
		.andExpect(status().isCreated());
	}
	
	@Test
	public void createAuthorNonExistingBook() throws Exception {
		when(bookService.createAuthor(eq(1L), any(Author.class))).thenThrow(new BookNotFoundException());
		
		mockMvc.perform(post("/library/books/1/authors")
				.content("{\"name\":\"name1\"}")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void createExistingAuthor() throws Exception {
		when(bookService.createAuthor(eq(1L), any(Author.class))).thenThrow(new AuthorExistsException());
		
		mockMvc.perform(post("/library/books/1/authors")
				.content("{\"name\":\"name1\"}")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isConflict());
	}
	
	@Test
	public void findAuthorsExistingBook() throws Exception {
		Author author = new Author();
		author.setId(1L);
		author.setName("name1");
		
		Author author2 = new Author();
		author2.setId(2L);
		author2.setName("name2");
		
		List<Author> authors = new ArrayList<>();
		authors.add(author);
		authors.add(author2);
		AuthorList authorList = new AuthorList(authors);
		
		when(bookService.findAllAuthorsByBookId(1L)).thenReturn(authorList);
		
		mockMvc.perform(get("/library/books/1/authors"))
		.andDo(print())
		.andExpect(status().isOk());
	}
}
