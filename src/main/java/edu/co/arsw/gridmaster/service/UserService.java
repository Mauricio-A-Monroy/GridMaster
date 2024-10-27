package edu.co.arsw.gridmaster.service;

import edu.co.arsw.gridmaster.model.Box;
import edu.co.arsw.gridmaster.model.Game;
import edu.co.arsw.gridmaster.model.User;
import edu.co.arsw.gridmaster.model.exceptions.GridMasterException;
import edu.co.arsw.gridmaster.persistance.Tuple;
import edu.co.arsw.gridmaster.persistance.UserPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class UserService {

    @Autowired
    UserPersistence userPersistence;

    @Autowired
    GameService gameService;

    public void move(String userName, Tuple<Integer, Integer> newPosition) throws GridMasterException {
        User user = userPersistence.getUserByName(userName);
        Game game = gameService.getGameByCode(user.getGameCode());
        Tuple<Integer, Integer> oldPosition = new Tuple<>(user.getPosition()[0], user.getPosition()[1]);
        changeScore(user, game.getBoard().getBox(newPosition), game.getBoard().getBox(oldPosition));
        userPersistence.saveUser(user);
    }

    public void createUser(User user) throws GridMasterException {
        userPersistence.saveUser(new User(user.getUserName(), user.getGameCode()));
    }

    public void deleteUser(String name) throws GridMasterException {
        userPersistence.deleteUser(name);
    }

    public synchronized void changeScore(User user, Box newBox, Box oldBox){
        // The box is free and nobody is standing there
        if(!newBox.isBusy()){
            user.setPosition(newBox.getPosition());

            int score = user.getScore().getAndIncrement();
            user.setScore(new AtomicInteger(score));

            oldBox.setBusy(false);
            oldBox.setOwner(user);
            oldBox.setColor(user.getColor());

            newBox.setBusy(true);
            // Decrementing opponent score
            if(newBox.getOwner() != null){
                User opponent = newBox.getOwner();
                int opponentScore = opponent.getScore().getAndDecrement();
                opponent.setScore(new AtomicInteger(opponentScore));
            }
        }
    }

    public Set<User> getAllUsers(){
        return userPersistence.getAllUsers();
    }

    public User getUserByName(String name) throws GridMasterException {
        return userPersistence.getUserByName(name);
    }

    public void setPositions(ArrayList<User> users) throws GridMasterException {
        ArrayList<int[]> positions = new ArrayList<>();
        positions.add(new int[]{0, 0});
        int[] position;
        for(User i : users){
            i.generatePosition();
            position = i.getPosition();
            while(positions.contains(position)) {
                i.generatePosition();
                position = i.getPosition();
            }
            positions.add(position);
            userPersistence.saveUser(i);
        }
    }

}
