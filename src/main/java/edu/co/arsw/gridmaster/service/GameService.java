package edu.co.arsw.gridmaster.service;

import edu.co.arsw.gridmaster.model.Board;
import edu.co.arsw.gridmaster.model.User;
import edu.co.arsw.gridmaster.model.exceptions.GridMasterException;
import edu.co.arsw.gridmaster.persistance.GamePersistence;
import edu.co.arsw.gridmaster.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameService {

    @Autowired
    GamePersistence gamePersistence;

    @Autowired
    UserService userService;

    public Integer createGame() throws GridMasterException {
        Game newGame = new Game();
        Integer code = newGame.getCode();
        gamePersistence.saveGame(newGame);
        return code;
    }

    public void deleteGame(Integer code) throws GridMasterException {
        gamePersistence.deleteGame(code);
    }

    public void startGame(Integer code) throws GridMasterException {
        Game game = gamePersistence.getGameByCode(code);
        setPositions(game);
    }

    public void updateGame(Integer code, Integer time, HashMap<String, Integer> score, Board board) throws GridMasterException {
        Game game = gamePersistence.getGameByCode(code);
        game.setTime(time);
        ConcurrentHashMap<String, Integer> scores = new ConcurrentHashMap<>(score);
        game.setScores(scores);
        game.setBoard(board);
        gamePersistence.saveGame(game);
    }

    public HashMap<String, Integer> getScoreBoard(Integer code) throws GridMasterException {
        Game game = gamePersistence.getGameByCode(code);
        ConcurrentHashMap<String, Integer> scores = game.getScores();
        HashMap<String, Integer> newScores = new HashMap<>();
        ArrayList<String> sortedKeys = new ArrayList<>(scores.keySet());
        Collections.sort(sortedKeys);
        for(String x : sortedKeys){
            newScores.put(x, scores.get(x));
        }
        return newScores;
    }

    public Set<Game> getAllGames(){
        return gamePersistence.getAllGames();
    }

    public Game getGameByCode(Integer code) throws GridMasterException {
        return gamePersistence.getGameByCode(code);
    }

    public synchronized void addUser(Integer code, String userName) throws GridMasterException {
        Game game = gamePersistence.getGameByCode(code);
        User user = userService.getUserByName(userName);
        // user.setColor(game.obtainColor());
        game.addUser(user);
        gamePersistence.saveGame(game);
    }

    public void setPositions(Game game) throws GridMasterException {
        userService.setPositions(game.getUsers());
    }

}
