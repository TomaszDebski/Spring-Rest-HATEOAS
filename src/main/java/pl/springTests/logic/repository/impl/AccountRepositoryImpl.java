package pl.springTests.logic.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pl.springTests.logic.entities.Account;
import pl.springTests.logic.repository.AccountRepository;

/**
 * @author Tomasz Dębski
 *
 */

@Repository
public class AccountRepositoryImpl implements AccountRepository{

	@Autowired
	private EntityManager entityManager;
	

	@Override
	public Account deleteAccount(Long accountId) {
		Account findAccount = findAccount(accountId);
		entityManager.remove(findAccount);
		return findAccount;
	}

	@Override
	public Account updateAccount(Long accountId, Account account) {
		Account findAccount = findAccount(accountId);
		findAccount.setUsername(account.getUsername());
		findAccount.setPassword(account.getPassword());
		entityManager.merge(findAccount);
		return findAccount;
	}

	

	@Override
	public Account findAccount(Long id) {
		return entityManager.find(Account.class, id);
	}

	@Override
	public Account findAccountByUsername(String userName) {
		Query query = entityManager.createQuery("select a from Account a where a.username = ?1");
		query.setParameter(1, userName);
		List<Account> resultList = query.getResultList();
		if (resultList.size() > 0){
			return resultList.get(0);
		}else{
			return null;
		}
	}
}
