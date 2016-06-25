package pl.springTests.controllers;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import pl.springTests.logic.services.AuthorService;
import pl.springTests.rest.controllers.AuthorController;

/**
 * @author Tomasz Dębski
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringTestWithHateosApplication.class)
@WebAppConfiguration
public class AuthorControllerTest {
	
	@InjectMocks
	private AuthorController authorController;
	
	@Mock
	private AuthorService authorService;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		
		mockMvc = MockMvcBuilders.standaloneSetup(authorController).build();
	}
	
	@Test
	public void getExistingAuthor() throws Exception {
		Author author = new Author();
		author.setId(1L);
		author.setName("name1");
		
		when(authorService.findAuthor(1L)).thenReturn(author);
		
		mockMvc.perform(get("/library/authors/1"))
		.andDo(print())
		.andExpect(jsonPath("$.name", is("name1")))
		.andExpect(status().isOk());
	}
	
	@Test
	public void getNotExistingAuthor() throws Exception {
		when(authorService.findAuthor(1L)).thenReturn(null);
		
		mockMvc.perform(get("/library/books/1"))
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void deleteExistingAuthor() throws Exception {
		Author author = new Author();
		author.setId(1L);
		author.setName("name1");
		
		when(authorService.deleteAuthor(1L)).thenReturn(author);
		
		mockMvc.perform(delete("/library/authors/1"))
		.andDo(print())
		.andExpect(jsonPath("$.name", is("name1")))
		.andExpect(status().isOk());
	}
	
	@Test
	public void deleteNotExistingAuthor() throws Exception {
		when(authorService.deleteAuthor(1L)).thenReturn(null);
		
		mockMvc.perform(delete("/library/authors/1"))
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void updateExistingAuthor() throws Exception {
		Author author = new Author();
		author.setId(1L);
		author.setName("name1");
		
		when(authorService.updateAuthor(eq(1L), any(Author.class))).thenReturn(author);
		
		mockMvc.perform(put("/library/authors/1")
				.content("{\"name\":\"name1\"}")
				.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(jsonPath("$.name", is("name1")))
		.andExpect(jsonPath("$.links[*].href", hasItems(endsWith("/authors/1"))))
		.andExpect(status().isOk());
	}
	
	@Test
	public void updateNotExistingAuthor() throws Exception {
		when(authorService.updateAuthor(eq(1L), any(Author.class))).thenReturn(null);
		
		mockMvc.perform(put("/library/authors/1")
				.content("{\"name\":\"name1\"}")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
	}

}
