package edu.co.arsw.gridmaster.persistance;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public interface ColorPersistance{

    //private ConcurrentHashMap<String, Boolean> colors = new ConcurrentHashMap<>();

    /**
     * Saves a new color.
     *
     * @param color the new color
     * @throws GridMasterException if an error occurs during saving
     */
    public void saveColor(String color) throws GridMasterException;

    /**
     * Checks if the color already exists.
     *
     * @param color the color to check
     * @return true if the color already exists, false otherwise
     * @throws GridMasterException if an error occurs during checking
     */
    public boolean colorAlreadyExist(String color) throws GridMasterException;

    /**
     * Deletes a color.
     *
     * @param color the color that will be deleted
     * @throws GridMasterException if an error occurs during deletion
     */
    public void deleteColor(String color) throws GridMasterException;
}