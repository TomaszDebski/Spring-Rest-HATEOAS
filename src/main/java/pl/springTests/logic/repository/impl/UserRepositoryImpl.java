package pl.springTests.logic.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import pl.springTests.logic.entities.Account;
import pl.springTests.logic.entities.User;
import pl.springTests.logic.repository.UserRepository;

@Repository
public class UserRepositoryImpl implements UserRepository {
	
	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findAllUsers() {
		Query users = entityManager.createQuery("select a from User a",User.class);
		return users.getResultList();
	}

	@Override
	public User findUser(Long id) {
		return entityManager.find(User.class, id);
	}

	@Override
	public User findUserByName(String name) {
		Query user = entityManager.createQuery("select a from User a where a.name = ?1",User.class);
		user.setParameter(1, name);
		List<User> resultList = user.getResultList();
		if (resultList.size() > 0){
			return resultList.get(0);
		}
		return null;
	}

	@Override
	public User createUser(User user) {
		entityManager.persist(user);
		return user;
	}
	
	@Override
	public Account createAccount(Account account) {
		entityManager.persist(account);
		return account;
	}
	
	@Override
	public List<Account> findAllAccounts() {
		Query query = entityManager.createQuery("select a from Account a");
		return query.getResultList();
	}
	
	@Override
	public List<Account> findAccountsByUser(Long userId) {
		Query query = entityManager.createQuery("select a from Account a where a.user.id = ?1");
		query.setParameter(1, userId);
		return query.getResultList();
	}

}
