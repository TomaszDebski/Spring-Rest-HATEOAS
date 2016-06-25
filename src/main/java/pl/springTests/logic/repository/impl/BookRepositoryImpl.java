package pl.springTests.logic.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import pl.springTests.logic.entities.Author;
import pl.springTests.logic.entities.Book;
import pl.springTests.logic.lists.AuthorList;
import pl.springTests.logic.repository.BookRepository;

/**
 * @author Tomasz Dębski
 *
 */

@Repository
public class BookRepositoryImpl implements BookRepository {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Book createBook(Book book) {
		entityManager.persist(book);
		return book;
	}

	@Override
	public Book deleteBook(Long bookId) {
		Book findBook = findBook(bookId);
		entityManager.remove(findBook);
		return findBook;
	}

	@Override
	public Book updateBook(Long bookId, Book book) {
		Book updateBook = findBook(bookId);
		updateBook.setTitle(book.getTitle());
		entityManager.merge(updateBook);
		return updateBook;
	}

	@Override
	public List<Book> findAllBooks() {
		Query query = entityManager.createQuery("select a from Book a");
		return query.getResultList();
	}

	@Override
	public Book findBook(Long id) {
		return entityManager.find(Book.class, id);
	}

	@Override
	public Book findBookByTitle(String title) {
		Query query = entityManager.createQuery("select a from Book a where a.title = ?1");
		query.setParameter(1, title);
		List<Book> resultList = query.getResultList();
		if (resultList.size() == 0){
			return null;
		}else{
			return resultList.get(0);
		}
	}

	@Override
	public List<Book> findBooksByAccount(Long accountId) {
		Query query = entityManager.createQuery("select a from Book a where a.account.id = ?1");
		query.setParameter(1, accountId);
		return query.getResultList();
	}


}
