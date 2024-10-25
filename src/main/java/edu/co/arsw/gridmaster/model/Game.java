package edu.co.arsw.gridmaster.model;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class Game {

    private Integer code;
    private Integer time;
    private Integer maxPlayers;
    private ConcurrentHashMap<User, Integer> scores;
    private ArrayList<User> users;
    private Board board;

    public Game(Integer code, Integer time, Integer maxPlayers, ConcurrentHashMap<User, Integer> scores, ArrayList<User> users, Board board) {
        this.code = code;
        this.time = time;
        this.maxPlayers = maxPlayers;
        this.scores = scores;
        this.users = users;
        this.board = board;
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

    public ConcurrentHashMap<User, Integer> getScores() {
        return scores;
    }

    public void setScores(ConcurrentHashMap<User, Integer> scores) {
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
}
