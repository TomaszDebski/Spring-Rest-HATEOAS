package pl.springTests.logic.repository;

import java.util.List;

import pl.springTests.logic.entities.Author;
import pl.springTests.logic.entities.Book;
import pl.springTests.logic.lists.AuthorList;

public interface AuthorRepository {

	Author deleteAuthor(Long authorId);
	Author updateAuthor(Long authorId, Author author);
	
	Author findAuthor(Long id);
	Author findAuthorByName(String name);
}
