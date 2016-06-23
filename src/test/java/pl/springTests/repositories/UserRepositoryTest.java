package pl.springTests.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import pl.springTests.SpringTestWithHateosApplication;
import pl.springTests.logic.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringTestWithHateosApplication.class)
@WebAppConfiguration
public class UserRepositoryTest {

	
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void findAllUser(){
		userRepository.findAllUsers();
	}
}
