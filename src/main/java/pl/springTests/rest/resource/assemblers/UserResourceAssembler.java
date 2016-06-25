package pl.springTests.rest.resource.assemblers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import pl.springTests.logic.entities.User;
import pl.springTests.rest.controllers.UserController;
import pl.springTests.rest.resource.UserResource;

/**
 * @author Tomasz Dębski
 *
 */
public class UserResourceAssembler extends ResourceAssemblerSupport<User,UserResource> {

	public UserResourceAssembler() {
		super(UserController.class, UserResource.class);
	}

	@Override
	public UserResource toResource(User user) {
		UserResource resource = new UserResource();
		resource.setName(user.getName());
		resource.add(linkTo(methodOn(UserController.class).getUser(user.getId())).withSelfRel());
		resource.add(linkTo(methodOn(UserController.class).findAllAccounts(user.getId())).withRel("accounts"));
		return resource;
	}

}
