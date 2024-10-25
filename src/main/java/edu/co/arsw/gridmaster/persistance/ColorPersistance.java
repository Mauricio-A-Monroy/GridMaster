package edu.co.arsw.gridmaster.persistance;

import java.util.concurrent.ConcurrentHashMap;
import edu.co.arsw.gridmaster.model.exceptions.*;

public class ColorPersistance{

    private ConcurrentHashMap<String, Boolean> colors = new ConcurrentHashMap<>();

    public void saveColor(String color) throws ColorSaveException{
        colors.put(color, false);
    }

    public boolean colorAlreadyExist(String color) throws GridMasterException{
      return colors.containsKey(color);
    }

    public void deleteColor(String color) throws ColorNotFoundException{
        colors.remove(color);
    }
}