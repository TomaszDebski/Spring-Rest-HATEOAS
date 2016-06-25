package pl.springTests.rest.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

/**
 * @author Tomasz Dębski
 *
 */
public class AuthorListResource extends ResourceSupport {
	
	private List<AuthorResource> authors = new ArrayList<>();

	public List<AuthorResource> getAuthors() {
		return authors;
	}

	public void setAuthors(List<AuthorResource> authors) {
		this.authors = authors;
	}


}
