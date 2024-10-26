package edu.co.arsw.gridmaster.model;

import edu.co.arsw.gridmaster.persistance.Tuple;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Game {

    private Integer code;
    private Integer time;
    private Integer maxPlayers;
    private ConcurrentHashMap<String, Integer> scores;
    private ArrayList<User> users;
    private Board board;

    public Game() {
        this.code = (int) ((Math.random() * (9999 - 1000) + 1000));
        this.time = 600;
        this.maxPlayers = 4;
        this.scores = new ConcurrentHashMap<>();
        this.users = new ArrayList<>();
        this.board = new Board(new Tuple<>(100, 100));
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

    public void setUserScore(User user) {
        scores.get(user);
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    @Override
    public String toString() {
        return "Game{" +
                "code=" + code +
                ", time=" + time +
                ", maxPlayers=" + maxPlayers +
                ", users=" + users +
                '}';
    }

    public void addUser(User user){
        users.add(user);
        scores.put(user.getUserName(), user.getScore().get());
    }
}
