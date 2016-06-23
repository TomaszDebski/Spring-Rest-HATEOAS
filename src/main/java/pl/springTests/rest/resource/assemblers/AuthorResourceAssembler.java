package pl.springTests.rest.resource.assemblers;

import org.springframework.hateoas.ResourceSupport;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import pl.springTests.logic.entities.Author;
import pl.springTests.rest.controllers.AuthorController;
import pl.springTests.rest.resource.AuthorResource;

public class AuthorResourceAssembler extends ResourceAssemblerSupport<Author,AuthorResource> {

	public AuthorResourceAssembler() {
		super(AuthorController.class, AuthorResource.class);
	}

	@Override
	public AuthorResource toResource(Author author) {
		AuthorResource resource = new AuthorResource();
		resource.setName(author.getName());
		resource.add(linkTo(methodOn(AuthorController.class).getAuthor(author.getId())).withSelfRel());
		return resource;
	}

}
