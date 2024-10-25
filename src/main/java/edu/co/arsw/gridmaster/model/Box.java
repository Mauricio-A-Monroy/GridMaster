package edu.co.arsw.gridmaster.model;

import edu.co.arsw.gridmaster.persistance.Tuple;


public class Box {

    private int[] color;
    private User owner;
    private Tuple<Integer, Integer> position;

    public Box(Tuple<Integer, Integer> position){
        this.position = position;
    }

    public Box(int[] color, User owner, Tuple position) {
        this.color = color;
        this.owner = owner;
        this.position = position;
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
