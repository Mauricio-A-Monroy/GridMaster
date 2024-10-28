package edu.co.arsw.gridmaster.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.co.arsw.gridmaster.persistance.Tuple;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Player {

    private String name;
    private int[] color;
    private AtomicInteger score;
    private Tuple<Integer, Integer> position;

    @JsonCreator
    public Player(@JsonProperty("name") String name){
        this.name = name;
        this.score = new AtomicInteger(0);
        this.color = new int[]{0, 0, 0};
        this.position = new Tuple<>(0, 0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void incrementScore(){
        this.score.incrementAndGet();
    }

    public void decrementScore(){
        this.score.decrementAndGet();
    }

    public int[] getPosition() {
        return new int[]{this.position.getFirst(), this.position.getSecond()};
    }

    public void setPosition(Tuple<Integer, Integer> position) {
        this.position = position;
    }

    public void generatePosition(Integer x, Integer y) {
        Random rand = new Random();
        this.position = new Tuple<>(rand.nextInt(x), rand.nextInt(y));
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", position=" + position.getFirst() + " " + position.getSecond() +
                '}';
    }
}
