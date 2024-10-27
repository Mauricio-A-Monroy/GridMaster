package edu.co.arsw.gridmaster.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.co.arsw.gridmaster.persistance.Tuple;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class User {

    private String userName;
    private int[] color;
    private AtomicInteger score;
    private Tuple<Integer, Integer> position;

    @JsonCreator
    public User(@JsonProperty("userName") String userName){
        this.userName = userName;
        this.score = new AtomicInteger(0);
        this.color = new int[]{0, 0, 0};
        this.position = new Tuple<>(0, 0);
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

    public int[] getPosition() {
        return new int[]{this.position.getFirst(), this.position.getSecond()};
    }

    public void setPosition() {
        Random rand = new Random();
        this.position = new Tuple<>(rand.nextInt(100), rand.nextInt(100));
        System.out.println(this.position.getFirst());
        System.out.println(this.position.getSecond());
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", position=" + position.getFirst() + " " + position.getSecond() +
                '}';
    }
}
