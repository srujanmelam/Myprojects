package simpleapp.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import simpleapp.model.User;
import simpleapp.service.UserService;

@RestController
@RequestMapping("/users")
public class WelcomeController {

	private static final Logger logger = LoggerFactory.getLogger(WelcomeController.class);
	@Autowired
    private UserService userService;
	 @RequestMapping(method = RequestMethod.GET)
	    public ResponseEntity<List<User>> getAll() {
	        logger.info("getting all users");
			
	        List<User> users = userService.getAll();

	        if (users == null || users.isEmpty()){
	            logger.info("no users found");
	            return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
	        }
	        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	    }

	    //Get User By ID

	    @RequestMapping(value = "{id}", method = RequestMethod.GET)
	    public ResponseEntity<User> get(@PathVariable("id") int id){
	       
	        User user = userService.findById(id);

	        if (user == null){
	            logger.info("user with id {} not found", id);
	            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	        }
	        logger.info("getting user with id: {}", id);
	        return new ResponseEntity<User>(user, HttpStatus.OK);
	    }

	    //Create New User

	    @RequestMapping(method = RequestMethod.POST)
	    public ResponseEntity<Void> create(@RequestBody User user, UriComponentsBuilder ucBuilder){
	    	if(user.getUsername().length() == 0 || user.getPassword().length() == 0 || user.getEmail().length()==0) {
	    		logger.info("username or email or password cannot be null");
	    		return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
	    	}
	    	else if(userService.mailExists(user)) {
	    		 logger.info("a user with mail " + user.getEmail() + " already exists");
		         return new ResponseEntity<Void>(HttpStatus.CONFLICT);
	    	}
	    	else {
	    		  logger.info("creating new user: {}", user);
	  	        if (userService.nameExists(user)){
	  	            logger.info("a user with name " + user.getUsername() + " already exists");
	  	            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
	  	        }

	  	        userService.create(user);

	  	        HttpHeaders headers = new HttpHeaders();
	  	        headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
	  	        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	    		
	    	}
	      
	    }

	    //Update Existing User

	    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
	    public ResponseEntity<User> update(@PathVariable int id, @RequestBody User user){
	        logger.info("updating user: {}", user);
	        User currentUser = userService.findById(id);

	        if (currentUser == null){
	            logger.info("User with id {} not found", id);
	            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	        }

	        currentUser.setId(user.getId());
	        currentUser.setUsername(user.getUsername());

	        userService.update(user);
	        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
	    }

	    //Delete User

	    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	    public ResponseEntity<Void> delete(@PathVariable("id") int id){
	        logger.info("deleting user with id: {}", id);
	        User user = userService.findById(id);

	        if (user == null){
	            logger.info("Unable to delete. User with id {} not found", id);
	            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	        }

	        userService.delete(id);
	        return new ResponseEntity<Void>(HttpStatus.OK);
	    }



}