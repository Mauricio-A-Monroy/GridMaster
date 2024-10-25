package edu.co.arsw.gridmaster.service;

import edu.co.arsw.gridmaster.model.User;
import edu.co.arsw.gridmaster.model.exceptions.GridMasterException;
import edu.co.arsw.gridmaster.persistance.GamePersistence;
import edu.co.arsw.gridmaster.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class GameService {

    @Autowired
    GamePersistence gamePersistence;

    // Returns game?
    public void createGame(String userName) throws GridMasterException {
        Game newGame = new Game();
        User admin = new User(userName);
        // Obtain the color to associate it with the admin
        newGame.addUser(admin);
        gamePersistence.saveGame(newGame);
    }

    public void deleteGame(Integer code) throws GridMasterException {
        gamePersistence.deleteGame(code);
    }

    public void startGame(){
        // void method for the moment
    }

    public void endGame(){
        // void method for the moment
    }

    public Set<Game> getAllGames(){
        return gamePersistence.getAllGames();
    }

    public Game getGameByCode(Integer code) throws GridMasterException {
        return gamePersistence.getGameByCode(code);
    }

    public void addUser(Integer code, String userName) throws GridMasterException {
        Game game = gamePersistence.getGameByCode(code);
        User newUser = new User(userName);
        // Obtain the color
        game.addUser(newUser);
        gamePersistence.addUser(new User(userName));
    }

}
