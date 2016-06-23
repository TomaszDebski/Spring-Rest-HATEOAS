package pl.springTests.rest.resource.assemblers;

import java.util.List;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import pl.springTests.logic.lists.AccountList;
import pl.springTests.rest.controllers.AccountController;
import pl.springTests.rest.resource.AccountListResource;
import pl.springTests.rest.resource.AccountResource;

public class AccountListResourceAssembler extends ResourceAssemblerSupport<AccountList, AccountListResource> {

	public AccountListResourceAssembler() {
		super(AccountController.class, AccountListResource.class);
	}

	@Override
	public AccountListResource toResource(AccountList accountList) {
		List<AccountResource> resList = new AccountResourceAssembler().toResources(accountList.getAccounts());
		AccountListResource resources = new AccountListResource();
		resources.setAccounts(resList);
		return resources;
	}

}
