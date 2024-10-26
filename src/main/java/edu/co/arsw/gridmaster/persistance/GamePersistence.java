package edu.co.arsw.gridmaster.persistance;

import edu.co.arsw.gridmaster.model.Game;
import edu.co.arsw.gridmaster.model.exceptions.GridMasterException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GamePersistence {

    private ConcurrentHashMap<Integer, Game> games;

    public GamePersistence(){
        this.games = new ConcurrentHashMap<>();
    }

    public void saveGame(Game game) throws GridMasterException {
        games.putIfAbsent(game.getCode(), game);
    }

    public Game getGameByCode(Integer code) throws GridMasterException{
        return games.get(code);
    }

    public void deleteGame(Integer code) throws GridMasterException{
        games.remove(code);
    }

    public Set<Game> getAllGames(){
        return new HashSet<>(games.values());
    }

}
