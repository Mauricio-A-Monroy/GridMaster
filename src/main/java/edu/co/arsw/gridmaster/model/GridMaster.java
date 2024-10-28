package edu.co.arsw.gridmaster.model;

import edu.co.arsw.gridmaster.persistance.Tuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class GridMaster {

    private Integer code;
    private Integer time;
    private Integer maxPlayers;
    private ConcurrentHashMap<String, Integer> scores;
    private HashMap<String, Player> players;
    private Tuple<Integer, Integer> dimension;
    private ArrayList<ArrayList<Box>> boxes;

    public GridMaster() {
        this.code = (int) ((Math.random() * (9999 - 1000) + 1000));
        this.time = 600;
        this.maxPlayers = 4;
        this.scores = new ConcurrentHashMap<>();
        this.players = new HashMap<>();
        this.dimension = new Tuple<>(5, 5);
        this.boxes = new ArrayList<>();
        for(int i = 0; i < dimension.getFirst(); i++){
            boxes.add(new ArrayList<>());
            for(int j = 0; j < dimension.getSecond(); j++){
                boxes.get(i).add(new Box( new Tuple<>(i, j) ));
            }
        }
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer delta) {
        this.time = delta;
    }

    public Integer getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(Integer newMaxPlayers) {
        this.maxPlayers = newMaxPlayers;
    }

    public ConcurrentHashMap<String, Integer> getScores() {
        return scores;
    }

    public void setScores(ConcurrentHashMap<String, Integer> scores) {
        this.scores = scores;
    }

    public HashMap<String, Player> getPlayers() {
        return players;
    }

    public void setPlayers(HashMap<String, Player> players) {
        this.players = players;
    }

    public Player getPlayerByName(String name){
        return this.players.get(name);
    }

    public ArrayList<ArrayList<Box>> getBoxes() {
        return boxes;
    }

    public void setBoxes(ArrayList<ArrayList<Box>> boxes) {
        this.boxes = boxes;
    }

    public Box getBox(Tuple<Integer, Integer> position){
        return boxes.get(position.getFirst()).get(position.getSecond());
    }

    public Tuple<Integer, Integer> getDimension() {
        return dimension;
    }

    public void setDimension(Tuple<Integer, Integer> dimension) {
        this.dimension = dimension;
    }

    public void updateScoreOfPlayer(String name, Integer score){
        scores.put(name, score);
    }

    @Override
    public String toString() {
        return "Game{" +
                "code=" + code +
                ", time=" + time +
                ", maxPlayers=" + maxPlayers +
                ", players=" + players +
                '}';
    }

    public void addPlayer(Player player){
        players.put(player.getName(), player);
        scores.put(player.getName(), player.getScore().get());
    }
}
