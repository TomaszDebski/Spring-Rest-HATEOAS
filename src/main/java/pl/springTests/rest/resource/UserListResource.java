package pl.springTests.rest.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

public class UserListResource extends ResourceSupport{

	List<UserResource> users = new ArrayList<>();

	public List<UserResource> getUsers() {
		return users;
	}

	public void setUsers(List<UserResource> users) {
		this.users = users;
	}
	
	
}
