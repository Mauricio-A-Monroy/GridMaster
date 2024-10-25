package edu.co.arsw.gridmaster.service;

import edu.co.arsw.gridmaster.model.Box;
import edu.co.arsw.gridmaster.model.User;
import edu.co.arsw.gridmaster.model.exceptions.GridMasterException;
import edu.co.arsw.gridmaster.persistance.Tuple;
import edu.co.arsw.gridmaster.persistance.UserPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {

    @Autowired
    UserPersistence userPersistence;

    public void move(String userName, Tuple<Integer, Integer> newPosition){
        // void method for the moment
    }

    public void createUser(String userName) throws GridMasterException {
        userPersistence.saveUser(new User(userName));
    }

    public void deleteUser(String name) throws GridMasterException {
        userPersistence.deleteUser(name);
    }

    public void changeScore(String userName, Box box){
        // void method for the moment
    }

    public Set<User> getAllUsers(){
        return userPersistence.getAllUsers();
    }

    public User getUserByName(String name) throws GridMasterException {
        return userPersistence.getUserByName(name);
    }

}
