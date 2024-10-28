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
            i.generatePosition(game.getDimension().getFirst(), game.getDimension().getSecond());
            position = i.getPosition();
            while(positions.contains(position)) {
                i.generatePosition(game.getDimension().getFirst(), game.getDimension().getSecond());
                position = i.getPosition();
            }
            positions.add(position);
        }
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
        changeScore(game, player, game.getBox(newPosition), game.getBox(oldPosition));
        gridMasterPersistence.saveGame(game);
    }

    public synchronized void changeScore(GridMaster game, Player player, Box newBox, Box oldBox){
        // The box is free and nobody is standing there
        if(!newBox.isBusy()){
            player.setPosition(newBox.getPosition());

            player.incrementScore();
            game.updateScoreOfPlayer(player.getName(), player.getScore().get());

            oldBox.setBusy(false);
            oldBox.setOwner(player);
            oldBox.setColor(player.getColor());

            newBox.setBusy(true);
            // Decrementing opponent score
            if(newBox.getOwner() != null){
                Player opponent = newBox.getOwner();
                opponent.decrementScore();
                game.updateScoreOfPlayer(opponent.getName(), opponent.getScore().get());
            }
        }
    }

    public void deleteGridMaster(Integer code) throws GridMasterException{
        gridMasterPersistence.deleteGame(code);
    }

}
