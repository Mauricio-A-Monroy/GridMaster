package edu.co.arsw.gridmaster.model;

import edu.co.arsw.gridmaster.persistance.Tuple;

import java.util.ArrayList;

public class Board {

    private Tuple<Integer, Integer> dimension;
    private ArrayList<ArrayList<Box>> boxes;

    public Board(Tuple<Integer, Integer> dimension) {
        this.dimension = dimension;
        this.boxes = new ArrayList<>();
        for(int i = 0; i < dimension.getFirst(); i++){
            boxes.add(new ArrayList<>());
            for(int j = 0; j < dimension.getSecond(); j++){
                boxes.get(i).add(new Box( new Tuple<>(i, j) ));
            }
        }
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
                ", dimension=" + dimension +
                ", boxes=" + boxes +
                '}';
    }
}
