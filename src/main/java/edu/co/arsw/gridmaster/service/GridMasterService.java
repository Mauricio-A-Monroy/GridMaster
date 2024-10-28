package edu.co.arsw.gridmaster.service;

import edu.co.arsw.gridmaster.model.Box;
import edu.co.arsw.gridmaster.model.GridMaster;
import edu.co.arsw.gridmaster.model.Player;
import edu.co.arsw.gridmaster.model.exceptions.GridMasterException;
import edu.co.arsw.gridmaster.persistance.GridMasterPersistence;
import edu.co.arsw.gridmaster.persistance.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class GridMasterService {

    @Autowired
    GridMasterPersistence gridMasterPersistence;

    public Set<GridMaster> getAllGames(){
        return gridMasterPersistence.getAllGames();
    }

    public GridMaster getGameByCode(Integer code) throws GridMasterException {
        return gridMasterPersistence.getGameByCode(code);
    }

    public ArrayList<Player> getPlayers(Integer code) throws GridMasterException {
        GridMaster game = gridMasterPersistence.getGameByCode(code);
        return new ArrayList<>(game.getPlayers().values());
    }

    public Player getPlayerByName(Integer code, String name) throws GridMasterException {
        GridMaster game = gridMasterPersistence.getGameByCode(code);
        return game.getPlayerByName(name);
    }

    public HashMap<String, Integer> getScoreBoard(Integer code) throws GridMasterException {
        GridMaster game = gridMasterPersistence.getGameByCode(code);
        ConcurrentHashMap<String, Integer> scores = game.getScores();
        HashMap<String, Integer> newScores = new HashMap<>();
        ArrayList<String> sortedKeys = new ArrayList<>(scores.keySet());
        Collections.sort(sortedKeys);
        for(String x : sortedKeys){
            newScores.put(x, scores.get(x));
        }
        return newScores;
    }

    public Integer createGridMaster() throws GridMasterException {
        GridMaster newGame = new GridMaster();
        Integer code = newGame.getCode();
        gridMasterPersistence.saveGame(newGame);
        return newGame.getCode();
    }

    public void startGame(Integer code) throws GridMasterException {
        GridMaster game = gridMasterPersistence.getGameByCode(code);
        setPositions(game);
    }

    public void setPositions(GridMaster game) throws GridMasterException {
        ArrayList<int[]> positions = new ArrayList<>();
        positions.add(new int[]{0, 0});
        int[] position;
        for(Player i : game.getPlayers().values()){
            i.generatePosition();
            position = i.getPosition();
            while(positions.contains(position)) {
                i.generatePosition();
                position = i.getPosition();
            }
            positions.add(position);
        }
        gridMasterPersistence.saveGame(game);
    }

    // Check
    public void updateGame(Integer code, Integer time, HashMap<String, Integer> score) throws GridMasterException {
        GridMaster game = gridMasterPersistence.getGameByCode(code);
        game.setTime(time);
        ConcurrentHashMap<String, Integer> scores = new ConcurrentHashMap<>(score);
        game.setScores(scores);
        gridMasterPersistence.saveGame(game);
    }

    public synchronized void addPlayer(Integer code, String name) throws GridMasterException {
        GridMaster game = gridMasterPersistence.getGameByCode(code);
        // user.setColor(game.obtainColor());
        game.addPlayer(new Player(name));
        gridMasterPersistence.saveGame(game);
    }

    public void move(Integer code, String playerName, Tuple<Integer, Integer> newPosition) throws GridMasterException {
        GridMaster game = gridMasterPersistence.getGameByCode(code);
        Player player = game.getPlayerByName(playerName);
        Tuple<Integer, Integer> oldPosition = new Tuple<>(player.getPosition()[0], player.getPosition()[1]);
        changeScore(player, game.getBox(newPosition), game.getBox(oldPosition));
        gridMasterPersistence.saveGame(game);
    }

    public synchronized void changeScore(Player player, Box newBox, Box oldBox){
        // The box is free and nobody is standing there
        if(!newBox.isBusy()){
            player.setPosition(newBox.getPosition());

            int score = player.getScore().getAndIncrement();
            player.setScore(new AtomicInteger(score));

            oldBox.setBusy(false);
            oldBox.setOwner(player);
            oldBox.setColor(player.getColor());

            newBox.setBusy(true);
            // Decrementing opponent score
            if(newBox.getOwner() != null){
                Player opponent = newBox.getOwner();
                int opponentScore = opponent.getScore().getAndDecrement();
                opponent.setScore(new AtomicInteger(opponentScore));
            }
        }
    }

    public void deleteGridMaster(Integer code) throws GridMasterException{
        gridMasterPersistence.deleteGame(code);
    }

}
