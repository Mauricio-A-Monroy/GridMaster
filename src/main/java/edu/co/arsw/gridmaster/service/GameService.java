package edu.co.arsw.gridmaster.service;

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
    public Game createGame(Game game) throws GridMasterException {
        gamePersistence.saveGame(game);
        return gamePersistence.getGameByCode(game.getCode());
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

}
