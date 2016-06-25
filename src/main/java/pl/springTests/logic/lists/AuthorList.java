package pl.springTests.logic.lists;

import java.util.ArrayList;
import java.util.List;

import pl.springTests.logic.entities.Author;

/**
 * @author Tomasz Dębski
 *
 */

public class AuthorList {

	private List<Author> authors = new ArrayList<>();
	
	public AuthorList(List<Author> authors){
		this.authors = authors;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}
	
	
}
