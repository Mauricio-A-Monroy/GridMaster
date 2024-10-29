package edu.co.arsw.gridmaster.service;

import edu.co.arsw.gridmaster.model.Box;
import edu.co.arsw.gridmaster.model.GridMaster;
import edu.co.arsw.gridmaster.model.Player;
import edu.co.arsw.gridmaster.model.exceptions.*;
import edu.co.arsw.gridmaster.persistance.GridMasterRepository;
import edu.co.arsw.gridmaster.persistance.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GridMasterService {

    @Autowired
    GridMasterRepository gridMasterRepository;

    public List<GridMaster> getAllGames(){
        return gridMasterRepository.findAll();
    }

    public Optional<GridMaster> getGameByCode(Integer code) throws GridMasterException {
        return gridMasterRepository.findById(code);
    }

    public ArrayList<Player> getPlayers(Integer code) throws GridMasterException {
        GridMaster game;
        if(gridMasterRepository.findById(code).isPresent()){
            game = gridMasterRepository.findById(code).get();
            return new ArrayList<>(game.getPlayers().values());
        }
        else{
            throw new GameNotFoundException();
        }
    }

    public Player getPlayerByName(Integer code, String name) throws GridMasterException {
        GridMaster game;
        if(gridMasterRepository.findById(code).isPresent()){
            game = gridMasterRepository.findById(code).get();
        }
        else{
            throw new GameNotFoundException();
        }
        Player player = game.getPlayerByName(name);
        if(player == null){
            throw new PlayerNotFoundException();
        }
        return player;
    }

    public HashMap<String, Integer> getScoreBoard(Integer code) throws GridMasterException {
        GridMaster game;
        if(gridMasterRepository.findById(code).isPresent()){
            game = gridMasterRepository.findById(code).get();
        }
        else{
            throw new GameNotFoundException();
        }
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
        gridMasterRepository.save(newGame);
        return newGame.getCode();
    }

    public void startGame(Integer code) throws GridMasterException {
        GridMaster game;
        if(gridMasterRepository.findById(code).isPresent()){
            game = gridMasterRepository.findById(code).get();
        }
        else{
            throw new GameNotFoundException();
        }
        setPositions(game);
    }

    public void setPositions(GridMaster game) throws GridMasterException {
        ArrayList<int[]> positions = new ArrayList<>();
        int[] position;
        for(Player i : game.getPlayers().values()){
            do {
                i.generatePosition(game.getDimension().getFirst(), game.getDimension().getSecond());
                position = i.getPosition();
            } while (positions.contains(position));
            positions.add(position);
            game.getBox(new Tuple<>(position[0], position[1])).setBusy(true);
        }
        gridMasterRepository.save(game);
    }

    public synchronized void addPlayer(Integer code, String name) throws GridMasterException {
        GridMaster game;
        if(gridMasterRepository.findById(code).isPresent()){
            game = gridMasterRepository.findById(code).get();
        }
        else{
            throw new GameNotFoundException();
        }
        if(game.getMaxPlayers() == game.getPlayers().size()){
            throw new GameException("Room is full.");
        }
        if(game.getPlayerByName(name) != null){
            throw new PlayerSaveException();
        }
        Player player = new Player(name);
        player.setColor(game.obtainColor());
        game.addPlayer(player);
        gridMasterRepository.save(game);
    }

    public void move(Integer code, String playerName, Tuple<Integer, Integer> newPosition) throws GridMasterException {
        GridMaster game;
        if(gridMasterRepository.findById(code).isPresent()){
            game = gridMasterRepository.findById(code).get();
        }
        else{
            throw new GameNotFoundException();
        }
        Integer x = newPosition.getFirst();
        Integer y = newPosition.getSecond();
        if(x < 0 || y < 0 || x >= game.getDimension().getFirst() || y >= game.getDimension().getSecond()){
            throw new BoardException("Invalid move.");
        }
        Player player = game.getPlayerByName(playerName);
        Tuple<Integer, Integer> oldPosition = new Tuple<>(player.getPosition()[0], player.getPosition()[1]);
        changeScore(game, player, game.getBox(newPosition), game.getBox(oldPosition));
        game.printBoard();
        gridMasterRepository.save(game);
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
        gridMasterRepository.deleteById(code);
    }

}
