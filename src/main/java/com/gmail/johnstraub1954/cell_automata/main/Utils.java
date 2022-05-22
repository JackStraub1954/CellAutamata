package com.gmail.johnstraub1954.cell_automata.main;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Miscellaneous utilities to support the Game of Life project.
 * This class is implemented as a singleton.
 * 
 * @author Jack Straub
 */
public enum Utils implements PropertyChangeListener
{
    INSTANCE;
    
    private static final Parameters params  = Parameters.INSTANCE;
    
    private static List<Integer>    survivalStates  = 
        params.getSurvivalStates();
    private static List<Integer>    birthStates     = params.getBirthStates();
    private static GridMap          gridMap         = params.getGridMap();
    
    /**
     * Default constructor.
     */
    private Utils()
    {
        Parameters.INSTANCE.addPropertyChangeListener( this );
    }
    
    /**
     * Calculate the next generation for this game pattern
     * using Conway's original algorithm (or minor variations,
     * such as Highlife).
     * The grid map is updated, then Parameters.reset() is invoked.
     * 
     * @param   obj Object controlling propagation; not used
     */
    public void conwayPropagate( Object obj )
    {
        List<Cell>      cellsToModify   = new ArrayList<>();
        Iterator<Cell>  cellIterator    = gridMap.iterator();
        while ( cellIterator.hasNext() )
        {
            Cell            cell    = cellIterator.next();
            Neighborhood    hood    = new Neighborhood( cell, gridMap );
            Cell            newCell = 
                hood.getNextState( survivalStates, birthStates );
            if ( cell.isAlive() != newCell.isAlive() )
                cellsToModify.add( newCell );
        }
        
        for ( Cell modifiedCell : cellsToModify )
            gridMap.put( modifiedCell );
        
        params.reset();
    }
    
    @SuppressWarnings("unchecked")
    public void propertyChange( PropertyChangeEvent evt )
    {
        Object  newValue    = evt.getNewValue();
        String  propName    = evt.getPropertyName();
        
        switch ( propName )
        {
        case CAConstants.CTRL_SURVIVAL_STATES_PN:
            survivalStates = (List<Integer>)newValue;
            break;
        case CAConstants.CTRL_BIRTH_STATES_PN:
            birthStates = (List<Integer>)newValue;
            break;
        case CAConstants.GRID_MAP_PN:
            gridMap = (GridMap)newValue;
            break;
        default:
            break;
        }
    }
}
