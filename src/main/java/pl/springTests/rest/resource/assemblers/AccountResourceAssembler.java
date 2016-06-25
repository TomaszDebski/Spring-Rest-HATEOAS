package pl.springTests.rest.resource.assemblers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import pl.springTests.logic.entities.Account;
import pl.springTests.rest.controllers.AccountController;
import pl.springTests.rest.resource.AccountResource;

/**
 * @author Tomasz Dębski
 *
 */
public class AccountResourceAssembler extends ResourceAssemblerSupport<Account, AccountResource>{

	public AccountResourceAssembler() {
		super(AccountController.class, AccountResource.class);
	}

	@Override
	public AccountResource toResource(Account account) {
		AccountResource resource = new AccountResource();
		resource.setUsername(account.getUsername());
		resource.setPassword(account.getPassword());
		resource.add(linkTo(methodOn(AccountController.class).getAccount(account.getId())).withSelfRel());
		resource.add(linkTo(methodOn(AccountController.class).getAccount(account.getId())).withRel("user"));
		return resource;
	}

}
