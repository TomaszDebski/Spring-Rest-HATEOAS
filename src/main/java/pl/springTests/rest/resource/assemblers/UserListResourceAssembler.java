package pl.springTests.rest.resource.assemblers;

import java.util.List;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import pl.springTests.logic.lists.UserList;
import pl.springTests.rest.controllers.UserController;
import pl.springTests.rest.resource.UserListResource;
import pl.springTests.rest.resource.UserResource;

/**
 * @author Tomasz Dębski
 *
 */
public class UserListResourceAssembler extends ResourceAssemblerSupport<UserList, UserListResource>{

	public UserListResourceAssembler() {
		super(UserController.class, UserListResource.class);
	}

	@Override
	public UserListResource toResource(UserList userList) {
		List<UserResource> resources = new UserResourceAssembler().toResources(userList.getUsers());
		UserListResource userListResources = new UserListResource();		
		userListResources.setUsers(resources);
		return userListResources;
	}

}
