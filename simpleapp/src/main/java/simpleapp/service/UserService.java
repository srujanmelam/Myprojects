package simpleapp.service;

import java.util.List;

import simpleapp.model.User;

public interface UserService {
	List<User> getAll();

    User findById(int id);

    User findByName(String name);
    
    User findByMail(String mail);

    void create(User user);

    void update(User user);

    void delete(int id);

    boolean nameExists(User user);
    boolean mailExists(User user);
    String operationId();

}
