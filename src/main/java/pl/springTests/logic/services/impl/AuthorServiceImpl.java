package pl.springTests.logic.services.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.springTests.logic.entities.Author;
import pl.springTests.logic.exceptions.AuthorExistsException;
import pl.springTests.logic.exceptions.AuthorNotFoundException;
import pl.springTests.logic.repository.AuthorRepository;
import pl.springTests.logic.services.AuthorService;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService{
	
	@Autowired
	private AuthorRepository authorRepository;

	@Override
	public Author deleteAuthor(Long authorId) {
		return authorRepository.deleteAuthor(authorId);
	}

	@Override
	public Author updateAuthor(Long authorId, Author author) {
		Author findAuthor = authorRepository.findAuthorByName(author.getName());
		if (findAuthor != null){
			throw new AuthorExistsException();
		}
		return authorRepository.updateAuthor(authorId, author);
	}

	@Override
	public Author findAuthor(Long id) {
		return authorRepository.findAuthor(id);
	}

	@Override
	public Author findAuthorByName(String name) {
		return authorRepository.findAuthorByName(name);
	}

}
