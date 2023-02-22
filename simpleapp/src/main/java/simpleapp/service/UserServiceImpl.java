package simpleapp.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import simpleapp.model.User;
@Component
public class UserServiceImpl implements UserService{
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	 private static final AtomicInteger counter = new AtomicInteger();
	    static List<User> users = new ArrayList<User>(
	            Arrays.asList(
	                    new User(counter.incrementAndGet(), "SrujanMelam","Jai Bala Srujan","Melam","srujanmelam@gmail.com","Srujan@123"),
	                    new User(counter.incrementAndGet(), "CharanMelam","laxmi sri charan","Melam","charanmelam@gmail.com","Charan@123"),
	                    new User(counter.incrementAndGet(), "MayankGupta","Mayank","Gupta","mayankgupta@gmail.com","Mayank@887"),
	                    new User(counter.incrementAndGet(), "AkshayPatil","Akshay","Patil","akshaypatil@gmail.com","akp@001")));

	    @Override
	    public List<User> getAll() {
	        return users;
	    }

	    @Override
	    public User findById(int id) {
	        for (User user : users){
	            if (user.getId() == id){
	                return user;
	            }
	        }
	        return null;
	    }

	    @Override
	    public User findByName(String name) {
	        for (User user : users){
	            if (user.getUsername().equals(name)){
	                return user;
	            }
	        }
	        return null;
	    }
	    @Override
	    public User findByMail(String mail) {
	        for (User user : users){
	            if (user.getEmail().equals(mail)){
	                return user;
	            }
	        }
	        return null;
	    }

	    @Override
	    public void create(User user) {
	    	logger.info("created user");
	        user.setId(counter.incrementAndGet());
	        users.add(user);
	    }

	    @Override
	    public void update(User user) {
	        int index = users.indexOf(user);
	        users.set(index, user);
	    }

	    @Override
	    public void delete(int id) {
	        User user = findById(id);
	        users.remove(user);
	    }
	    @Override
	    public String operationId() {
	    	UUID uuid = UUID.randomUUID();
	    	String s=uuid.toString();
	    	return s;
	    }


	    @Override
	    public boolean nameExists(User user) {
	        return findByName(user.getUsername()) != null;
	    }
	    @Override
	    public boolean mailExists(User user) {
	        return findByMail(user.getEmail()) != null;
	    }

}
