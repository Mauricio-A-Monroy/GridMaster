package edu.co.arsw.gridmaster.persistance;

import edu.co.arsw.gridmaster.model.User;
import edu.co.arsw.gridmaster.model.exceptions.GridMasterException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserPersistence {

    private ConcurrentHashMap<String, User> users;

    public UserPersistence(){
        users = new ConcurrentHashMap<>();
    }

    public void saveUser(User user) throws GridMasterException {
        users.putIfAbsent(user.getUserName(), user);
    }

    public User getUserByName(String name) throws GridMasterException{
        return users.get(name);
    }

    public void deleteUser(String name) throws GridMasterException{
        users.remove(name);
    }

    public Set<User> getAllUsers(){
        return new HashSet<>(users.values());
    }

}
