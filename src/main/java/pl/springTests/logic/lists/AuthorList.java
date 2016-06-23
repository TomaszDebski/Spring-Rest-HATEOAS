package pl.springTests.logic.lists;

import java.util.ArrayList;
import java.util.List;

import pl.springTests.logic.entities.Author;

public class AuthorList {

	private List<Author> authors = new ArrayList<>();

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}
	
	
}
