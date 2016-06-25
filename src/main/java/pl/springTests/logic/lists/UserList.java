package pl.springTests.logic.lists;

import java.util.ArrayList;
import java.util.List;

import pl.springTests.logic.entities.Account;
import pl.springTests.logic.entities.User;

/**
 * @author Tomasz Dębski
 *
 */

public class UserList {

	List<User> users = new ArrayList<>();

	public UserList(List<User> users){
		this.users = users;
	}
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	
	
}
