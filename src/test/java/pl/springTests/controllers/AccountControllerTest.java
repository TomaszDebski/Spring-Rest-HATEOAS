package pl.springTests.controllers;

import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import pl.springTests.SpringTestWithHateosApplication;

/**
 * @author Tomasz Dębski
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringTestWithHateosApplication.class)
@WebAppConfiguration
public class AccountControllerTest {

}
