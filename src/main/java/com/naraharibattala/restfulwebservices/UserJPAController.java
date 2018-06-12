package com.naraharibattala.restfulwebservices;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserJPAController {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private PostRepository postRepository;
	
	@RequestMapping(method=RequestMethod.GET,value="jpa/users")
	public List<User> getUsers()
	{
		return repository.findAll();
	}
	
	@RequestMapping(method=RequestMethod.GET,value="jpa/users/{id}/posts")
	public List<Post> getUsers(@PathVariable int id)
	{
      Optional<User> user = repository.findById(id);
		
		if(!user.isPresent()) {
			
			throw new UserNotFoundException("id-"+id);
		}
		
		return user.get().getPosts();
	}
	
	
	
	@GetMapping(value="jpa/users/{id}")
	
	public Resource<User> getUser(@PathVariable int id) {
		
		Optional<User> user = repository.findById(id);
		
		if(!user.isPresent()) {
			
			throw new UserNotFoundException("id-"+id);
		}
		
		Resource<User> resource = new Resource<User>(user.get());
		ControllerLinkBuilder linkTo= linkTo(methodOn(this.getClass()).getUsers());
		resource.add(linkTo.withRel("all-users"));
		return resource;
	}
	
	@PostMapping (value="jpa/users")
	
	public ResponseEntity<Object> addUser(@Valid @RequestBody User user) {
		User savedUser =repository.save(user);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
   @PostMapping (value="jpa/users/{id}/posts")
	
	public ResponseEntity<Object> addPost(@PathVariable int id, @RequestBody Post post) {
		
        Optional<User> userOptional = repository.findById(id);
        
         if(!userOptional.isPresent()) {
			
			throw new UserNotFoundException("id-"+id);
		}
         
        User user = userOptional.get();
        
        post.setUser(user);
        
        postRepository.save(post);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(post.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping(value="jpa/users/{id}")
	
	public void deleteUser(@PathVariable int id) {
		
		repository.deleteById(id);

	}
}
