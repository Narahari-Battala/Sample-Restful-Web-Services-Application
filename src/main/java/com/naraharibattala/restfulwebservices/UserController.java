package com.naraharibattala.restfulwebservices;

import java.net.URI;
import java.util.ArrayList;

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
public class UserController {

	@Autowired
	private UserDaoServices service;
	
	@RequestMapping(method=RequestMethod.GET,value="/users")
	public ArrayList<User> getUsers()
	{
		return service.getAllUsers();
	}
	
	@GetMapping(value="/users/{id}")
	
	public Resource<User> getUser(@PathVariable int id) {
		
		User user = service.getUser(id);
		
		if(user==null) {
			
			throw new UserNotFoundException("id-"+id);
		}
		
		Resource<User> resource = new Resource<User>(user);
		ControllerLinkBuilder linkTo= linkTo(methodOn(this.getClass()).getUsers());
		resource.add(linkTo.withRel("all-users"));
		return resource;
	}
	
	@PostMapping (value="/users")
	
	public ResponseEntity<Object> addUser(@Valid @RequestBody User user) {
		User savedUser =service.addUser(user);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping(value="/users/{id}")
	
	public void deleteUser(@PathVariable int id) {
		
		User user = service.deleteUser(id);
		if(user==null) {
			
			throw new UserNotFoundException("id--> " + id);
		}
		service.deleteUser(id);
	}
}
