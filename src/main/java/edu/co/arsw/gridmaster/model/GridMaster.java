package edu.co.arsw.gridmaster.model;

import edu.co.arsw.gridmaster.persistance.Tuple;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class GridMaster {

    private Integer code;
    private Integer time;
    private Integer maxPlayers;
    private ConcurrentHashMap<String, Integer> scores;
    private ConcurrentHashMap<String, Player> players;
    private Tuple<Integer, Integer> dimension;
    private ArrayList<ArrayList<Box>> boxes;
    private Color color;
    private GameState gameState;

    public GridMaster() {
        this.code = (int) ((Math.random() * (9999 - 1000) + 1000));
        this.time = 30;
        this.maxPlayers = 4;
        this.scores = new ConcurrentHashMap<>();
        this.players = new ConcurrentHashMap<>();
        this.dimension = new Tuple<>(100, 100);
        this.color = new Color();
        this.boxes = new ArrayList<>();
        this.gameState = GameState.WAITING_FOR_PLAYERS;
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

    public ConcurrentHashMap<String, Player> getPlayers() {
        return players;
    }

    public void setPlayers(ConcurrentHashMap<String, Player> players) {
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

    public int[] obtainColor(){
        return color.getColor();
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void decrementTime(){
        this.time--;
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

    public void setPlayerPositionInScoreboard(){
        Map<String, Integer> orderedScores = this.scores.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
        int position = 1;
        for(String key : orderedScores.keySet()){
            if(!key.equals("EMPTY")){
                players.get(key).setScoreboardPosition(position);
                position++;
            }
        }
    }

    public Map<String, Integer> topTen(){
        ConcurrentHashMap<String, Integer> scores = this.getScores();
        ConcurrentHashMap<String, Integer> topTen = new ConcurrentHashMap<>();
        int cont = 0;
        for(String key : scores.keySet()){
            if(cont == 10){
                break;
            }
            topTen.put(key, scores.get(key));
            cont++;
        }
        Map<String, Integer> newScores = topTen.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        int sum = scores.values().stream()
                .mapToInt(a -> a)
                .sum();

        newScores.put("EMPTY", 10000 - sum);
        return newScores;
    }

    public String getFormatTime(){
        int time = this.getTime();
        int minutes = time / 60;
        int seconds = time % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
