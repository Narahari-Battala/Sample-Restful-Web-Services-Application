package com.naraharibattala.restfulwebservices;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class UserDaoServices {

	static ArrayList<User> users = new ArrayList<User>();
	static int idCount =3;
	static {
		
		users.add(new User(1,"hari",new Date()));
		users.add(new User(2,"nani",new Date()));
		users.add(new User(3,"krish",new Date()));
	}
	
	public ArrayList<User> getAllUsers(){
		
		return users;
	}
	
	public User getUser(int userId) {
		
		for(User user:users) {
			
			if(user.getId() == userId) {
				
				return user;
			}
		}
		
		return null;
	}
	
	public User addUser(User user) {
		
		if(user.getId() == null) {
			
			user.setId(++idCount);
		}
		
		users.add(user);
		return user;
	}
	
	public User deleteUser(int id) {
		
		for(User user:users) {
			
			if(user.getId() == id) {
				
				users.remove(user);
				return user;
			}
		}
		
		return null;
	}
}
