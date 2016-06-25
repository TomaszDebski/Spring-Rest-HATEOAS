package pl.springTests.logic.services;

import pl.springTests.logic.entities.Author;

/**
 * @author Tomasz Dębski
 *
 */

public interface AuthorService {

	Author deleteAuthor(Long authorId);
	Author updateAuthor(Long authorId, Author author);
	
	Author findAuthor(Long id);
	Author findAuthorByName(String name);
}
