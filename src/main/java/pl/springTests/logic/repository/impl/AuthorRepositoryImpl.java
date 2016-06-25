package pl.springTests.logic.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pl.springTests.logic.entities.Author;
import pl.springTests.logic.lists.AuthorList;
import pl.springTests.logic.repository.AuthorRepository;

/**
 * @author Tomasz Dębski
 *
 */

@Repository
public class AuthorRepositoryImpl implements AuthorRepository {
	
	@Autowired
	private EntityManager entityManager;

	@Override
	public Author deleteAuthor(Long authorId) {
		Author author = entityManager.find(Author.class, authorId);
		entityManager.remove(author);
		return author;
	}

	@Override
	public Author updateAuthor(Long authorId, Author author) {
		Author updatedAuthor = entityManager.find(Author.class, authorId);
		updatedAuthor.setName(author.getName());
		entityManager.merge(updatedAuthor);
		return updatedAuthor;
	}

	@Override
	public Author findAuthor(Long id) {
		return entityManager.find(Author.class, id);
	}

	@Override
	public Author findAuthorByName(String name) {
		Query query = entityManager.createQuery("select a from Author where a.name = ?1");
		query.setParameter(1, name);
		List<Author> resultList = query.getResultList();
		if (resultList.size() == 0){
			return null;
		}else{
			return resultList.get(0);
		}
	}
	

	@Override
	public Author createAuthor(Author author) {
		entityManager.persist(author);
		return author;
	}

	@Override
	public List<Author> findAuthorsByBook(Long bookId) {
		Query query = entityManager.createQuery("select a from Author a where a.book.id = ?1");
		query.setParameter(1, bookId);
		return query.getResultList();
		
	}
}
