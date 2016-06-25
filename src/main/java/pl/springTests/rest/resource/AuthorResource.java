package pl.springTests.rest.resource;

import org.springframework.hateoas.ResourceSupport;

import pl.springTests.logic.entities.Author;

/**
 * @author Tomasz Dębski
 *
 */
public class AuthorResource extends ResourceSupport {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Author toAuthor(){
		Author author = new Author();
		author.setName(name);
		return author;
	}
}
