package edu.co.arsw.gridmaster.model;

import edu.co.arsw.gridmaster.persistance.Tuple;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class User {

    private String userName;
    private int[] color;
    private AtomicInteger score;
    private Tuple<Integer, Integer> position;
    private Game game;

    public User(String userName, int[] color, Game game) {
        Random rand = new Random();
        this.userName = userName;
        this.color = color;
        this.game = game;
        this.score = new AtomicInteger(0);
        this.position = new Tuple<>(rand.nextInt(100), rand.nextInt(100));
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int[] getColor() {
        return color;
    }

    public void setColor(int[] color) {
        this.color = color;
    }

    public AtomicInteger getScore() {
        return score;
    }

    public void setScore(AtomicInteger score) {
        this.score = score;
    }

    public Tuple<Integer, Integer> getPosition() {
        return position;
    }

    public void setPosition(Tuple<Integer, Integer> position) {
        this.position = position;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", position=" + position +
                '}';
    }
}
