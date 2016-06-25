package pl.springTests.rest.resource;

import org.springframework.hateoas.ResourceSupport;

import pl.springTests.logic.entities.User;

/**
 * @author Tomasz Dębski
 *
 */

public class UserResource extends ResourceSupport {
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public User createUser(){
		User user= new User();
		user.setName(name);
		return user;
	}

}
