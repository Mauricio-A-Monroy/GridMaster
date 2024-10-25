package edu.co.arsw.gridmaster.model;

import edu.co.arsw.gridmaster.persistance.Tuple;


public class Box {

    private int[] color;
    private User owner;
    private Tuple<Integer, Integer> position;
    private Board board;

    public Box(int[] color, User owner, Tuple position, Board board) {
        this.color = color;
        this.owner = owner;
        this.position = position;
        this.board = board;
    }

    public int[] getColor() {
        return color;
    }

    public void setColor(int[] color) {
        this.color = color;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Tuple<Integer, Integer> getPosition() {
        return position;
    }

}
