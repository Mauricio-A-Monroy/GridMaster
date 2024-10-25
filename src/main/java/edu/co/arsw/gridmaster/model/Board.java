package edu.co.arsw.gridmaster.model;

import edu.co.arsw.gridmaster.persistance.Tuple;

import java.util.ArrayList;

public class Board {

    private Game game;
    private Tuple<Integer, Integer> dimension;
    private ArrayList<ArrayList<Box>> boxes;

    public Board(Game game, Tuple<Integer, Integer> dimension) {
        this.game = game;
        this.dimension = dimension;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Tuple<Integer, Integer> getDimension() {
        return dimension;
    }

    public void setDimension(Tuple<Integer, Integer> dimension) {
        this.dimension = dimension;
    }

    public ArrayList<ArrayList<Box>> getBoxes() {
        return boxes;
    }

    public void setBoxes(ArrayList<ArrayList<Box>> boxes) {
        this.boxes = boxes;
    }

    @Override
    public String toString() {
        return "Board{" +
                "game=" + game +
                ", dimension=" + dimension +
                ", boxes=" + boxes +
                '}';
    }
}
