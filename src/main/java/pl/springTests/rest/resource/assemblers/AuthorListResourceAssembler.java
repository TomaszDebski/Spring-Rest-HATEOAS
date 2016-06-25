package pl.springTests.rest.resource.assemblers;

import java.util.List;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import pl.springTests.logic.lists.AuthorList;
import pl.springTests.rest.controllers.AuthorController;
import pl.springTests.rest.resource.AuthorListResource;
import pl.springTests.rest.resource.AuthorResource;

/**
 * @author Tomasz Dębski
 *
 */
public class AuthorListResourceAssembler extends ResourceAssemblerSupport<AuthorList,AuthorListResource> {

	public AuthorListResourceAssembler() {
		super(AuthorController.class, AuthorListResource.class);
	}

	@Override
	public AuthorListResource toResource(AuthorList authorList) {
		List<AuthorResource> authorsList = new AuthorResourceAssembler().toResources(authorList.getAuthors());
		AuthorListResource resource = new AuthorListResource();
		resource.setAuthors(authorsList);
		return resource;
	}

}
