package edu.co.arsw.gridmaster.persistance;

import java.util.concurrent.ConcurrentHashMap;
import edu.co.arsw.gridmaster.model.exceptions.*;

public class ColorPersistance{

    private ConcurrentHashMap<String, Boolean> colors = new ConcurrentHashMap<>();

    /**
     * Saves a new color.
     *
     * @param color the new color
     * @throws ColorSaveException if an error occurs during saving
     */
    public void saveColor(String color) throws ColorSaveException{
        colors.put(color, false);
    }

    /**
     * Checks if the color already exists.
     *
     * @param color the color to check
     * @return true if the color already exists, false otherwise
     * @throws GridMasterException if an error occurs during checking
     */
    public boolean colorAlreadyExist(String color) throws GridMasterException{
      return colors.containsKey(color);
    }

    /**
     * Deletes a color.
     *
     * @param color the color that will be deleted
     * @throws ColorNotFoundException if an error occurs during deletion
     */
    public void deleteColor(String color) throws ColorNotFoundException{
        colors.remove(color);
    }
}