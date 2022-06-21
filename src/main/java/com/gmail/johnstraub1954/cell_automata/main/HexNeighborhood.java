package com.gmail.johnstraub1954.cell_automata.main;

import java.util.Collection;
import java.util.HashMap;

/**
 * Encapsulates the logic to assemble and evaluate neighborhood
 * of a grid with hexagonal cells.
 * 
 * A <em>neighborhood</em> of a cell, <em>self,</em>
 * consists of <em>self's</em> nearest six neighbors.
 * The specific neighbor of a cell can be accessed using
 * <em>get(Direction&nbsp;dir)</em>
 * where <em>dir</em> is one of six directions
 * as indicated by <em>enum Direction:</em> NE, E, S, SE, SW and NW.
 * <img 
 *     style="margin-left: 2em;float:right;"
 *     src="doc-files/HexagonalNeighborhoodSmall.png" 
 *     alt="Hexagonal Neighborhoods (small)"
 * >
 * <p>
 * Note that other types of hexagonal neighborhoods are common; see
 * <a href="https://demonstrations.wolfram.com/HexLifeHexagonalCellularAutomata/">
 *     Hex Life: Hexagonal Cellular Automata
 * </a>
 * from the
 * <a href="https://demonstrations.wolfram.com/">
 *     Wolfram Demonstrations Project
 * </a>.
 * 
 * @author Jack Straub
 * 
 * @see Direction
 */
public class HexNeighborhood extends HashMap<Direction, Cell>
{
    /** Generated serial version UID */
    
    /** The Cell whose neighbors can be accessed via this RectNeighborhood */
    private final Cell      self;
    
    /**
     * Constructor.
     * 
     * @param self  the cell from which this neighborhood is extrapolated
     * @param map   map of all cells in this game
     */
    public HexNeighborhood( Cell self, GridMap map )
    {
        this.self = self;
        for ( Direction dir : Direction.values() )
        {
            Cell    cell    = self.getNeighbor( dir, map );
            put( dir, cell );
        }
    }
    
    /**
     * Gets the number of live cells in the neighborhood,
     * not including self.
     * 
     * @return  the number of live cells in the neighborhood,
     *          not including self
     */
    public int  getLivingNeighborCount()
    {
        int count   = 0;
        for ( Cell cell : values() )
        {
            if ( cell.isAlive() )
                ++count;
        }
        
        return count;
    }
    
    /**
     * Returns the base cell for this neighborhood.
     * 
     * @return  the base cell for this neighborhood
     */
    public Cell getSelf()
    {
        return self;
    }
    
    /**
     * Determines whether the next state of the base cell
     * of this neighborhood should be <em>alive</em>
     * or <em>dead.</em>
     * The new state is determined by a
     * given set of live neighbor counts that indicates 
     * a live cell should survive,
     * and a given set of live neighbor counts that indicates
     * a dead cell should be born
     * 
     * @param survivalStates    the given set of live neighbor counts 
     *                          that indicates a live cell should survive
     * @param birthStates       the given set of live neighbor counts 
     *                          that indicates a dead cell should be born
     *                          
     * @return  a copy of <em>self</em> in the new state
     *          as dictated by the above
     */
    public Cell getNextState( 
        Collection<Integer> survivalStates, 
        Collection<Integer> birthStates
    )
    {
        int     count       = getLivingNeighborCount();
        Cell    nextState   = new Cell( self );
        if ( nextState.isAlive() )
        {
            nextState.setAlive( survivalStates.contains( count ) );
        }
        else
        {
            nextState.setAlive( birthStates.contains( count ) );
        }
        return nextState;
    }
}
