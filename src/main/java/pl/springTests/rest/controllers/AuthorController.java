package pl.springTests.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.springTests.logic.entities.Author;
import pl.springTests.logic.services.AuthorService;
import pl.springTests.rest.resource.AuthorResource;
import pl.springTests.rest.resource.assemblers.AuthorResourceAssembler;

@Controller
@RequestMapping("/library/authors")
public class AuthorController {
	
	@Autowired
	private AuthorService authorService;
	
	@RequestMapping(value="/{authorId}",method=RequestMethod.GET)
	public ResponseEntity<AuthorResource> getAuthor(@PathVariable long authorId){
		Author findAuthor = authorService.findAuthor(authorId);
		if (findAuthor == null){
			return new ResponseEntity<AuthorResource>(HttpStatus.NOT_FOUND);
		}else{
			AuthorResource resource = new AuthorResourceAssembler().toResource(findAuthor);
			return new ResponseEntity<AuthorResource>(resource,HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/{authorId}",method=RequestMethod.DELETE)
	public ResponseEntity<AuthorResource> deleteAuthor(@PathVariable Long authorId){
		Author deleteAuthor = authorService.deleteAuthor(authorId);
		if (deleteAuthor == null){
			return new ResponseEntity<AuthorResource>(HttpStatus.NOT_FOUND);
		}else{
			AuthorResource resource = new AuthorResourceAssembler().toResource(deleteAuthor);
			return new ResponseEntity<AuthorResource>(resource,HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/{authorId}",method=RequestMethod.PUT)
	public ResponseEntity<AuthorResource> updateAuthor(@PathVariable Long authorId,
			@RequestBody AuthorResource authorResource){
		Author updateAuthor = authorService.updateAuthor(authorId, authorResource.toAuthor());
		if (updateAuthor == null){
			return new ResponseEntity<AuthorResource>(HttpStatus.NOT_FOUND);
		}else{
			AuthorResource resource = new AuthorResourceAssembler().toResource(updateAuthor);
			return new ResponseEntity<AuthorResource>(resource,HttpStatus.OK);
		}
	}

}
