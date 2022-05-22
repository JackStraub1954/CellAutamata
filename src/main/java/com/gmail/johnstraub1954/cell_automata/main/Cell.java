/**
 * 
 */
package com.gmail.johnstraub1954.cell_automata.main;

import java.awt.Point;
import java.util.Objects;

/**
 * Encapsulation of a cell in a cellular automata context.
 * Every cell has two coordinates and a state.
 * Properties:
 * <ol>
 *      <li>
 *          xco - x coordinate of cell: integer.
 *          Equivalent to the column 
 *          in a 2D matrix.
 *      </li>
 *      <li>
 *          yco - y coordinate of cell: integer.
 *          Equivalent to the row
 *          in a 2D matrix.
 *      </li>
 *      <li>
 *          state - state of cell: integer. 
 *          Interpretation of this value
 *          is context dependent.
 *          In the Game of Life, for example,
 *          possible states may be limited to 
 *          1 (alive) or 0 (dead).
 *      </li>
 * </ol>
 * @author Jack Straub
 *
 */
public class Cell
{
    /** 
     * Grid coordinates of this cell. 
     * This object is not modifiable by the user.
     * 
     * @see #getPoint
     */
    private final Point point;
    
    /** The state of this cell. */
    private int state;

    /**
     * Default constructor. X- and y-coordinates 
     * are explicitly set to 0;
     * state is explicitly set to 0.
     */
    public Cell()
    {
        this( 0, 0, 0 );
    }
    
    /**
     * Constructor to set the x- and y-coordinates of this cell.
     * state is explicitly set to 0.
     * 
     * @param xco   x-coordinate of this cell
     * @param yco   y-coordinate of this cell
     */
    public Cell( int xco, int yco )
    {
        this( xco, yco, 0 );
    }
    
    /**
     * Constructor to set the x- and y-coordinates of a cell
     * to the x- and y-coordinates of a given Point.
     * state is explicitly set to 0.
     * 
     * @param point the given point
     */
    public Cell( Point point )
    {
        this( point.x, point.y, 0 );
    }
    
    /**
     * Constructor to set the x- and y-coordinates of a cell
     * to the x- and y-coordinates of a given Point,
     * and state to a given value. 
     * The state is specified as a boolean
     * (true = alive, false = dead);
     * internally, alive maps to a state of 1,
     * dead maps to a state of 0.
     * 
     * @param point     the given point
     * @param state     the given value for state
     */
    public Cell( Point point, boolean alive )
    {
        this( point.x, point.y, alive ? 1 : 0 );
    }
    
    /**
     * Copy constructor.
     * 
     * @param cell  the cell to copy
     */
    public Cell( Cell cell )
    {
        this( cell.point.x, cell.point.y, cell.state );
    }
    
    /**
     * Constructor to set all properties of this cell.
     * The state is specified as a boolean
     * (true = alive, false = dead);
     * internally, alive maps to a state of 1,
     * dead maps to a state of 0.
     * 
     * @param xco       the x-coordinate of this cell
     * @param yco       the y-coordinate of this cell
     * @param state     the state of this cell
     */
    public Cell( int xco, int yco, boolean state )
    {
        this( xco, yco, state ? 1 : 0 );
    }
    
    /**
     * Constructor to set all properties of this cell.
     * 
     * @param xco       the x-coordinate of this cell
     * @param yco       the y-coordinate of this cell
     * @param state     the state of this cell
     */
    public Cell( int xco, int yco, int state )
    {
        this.point = new Point( xco, yco );
        this.state = state;
    }
    
    /**
     * Constructor to set the x- and y-coordinates of a cell
     * to the x- and y-coordinates of a given Point,
     * and state to a given value.
     * 
     * @param point     the given point
     * @param state     the given value for state
     */
    public Cell( Point point, int state )
    {
        // make copy of the user's point; don't keep
        // a reference to object (point) that the user
        // might modify
        this( point.x, point.y, state );
    }
    
    /**
     * Get the neighbor of a cell in a given direction.
     * 
     * @param dir   the given direction
     * 
     * @return the neighbor of a cell in a given direction.
     */
    public Cell getNeighbor( Direction dir, GridMap map )
    {
        Point   point   = getRelativePoint( dir );
        Cell    cell    = map.get( point );
        return cell;
    }
    
    /**
     * Returns a point, relative to this Cell, in a given direction.
     * 
     * @param dir   the given direction
     * 
     * @return  a point, relative to this Cell, in the given direction
     */
    public Point getRelativePoint( Direction dir )
    {
        int     xco = getXco() + dir.getHorizontal();
        int     yco = getYco() + dir.getVertical();
        Point   point   = new Point( xco, yco );
        return point;
    }
    
    /**
     * Returns the state of this cell
     * 
     * @return the state of this cell
     */
    public int getState()
    {
        return state;
    }

    /**
     * Sets this cell to a given state
     * 
     * @param state   the given state
     */
    public void setState( int state )
    {
        this.state = state;
    }
    
    /**
     * Getter for the x-coordinate of this cell's grid location.
     * @return  the x-coordinate of this cell
     */
    public int getXco()
    {
        return point.x;
    }
    
    /**
     * Getter for the y-coordinate of this cell
     * @return  the y-coordinate of this cell
     */
    public int getYco()
    {
        return point.y;
    }
    
    /**
     * Gets a copy of the grid coordinates of this cell.
     * Modifying the returned object
     * will <em>not</em> modify the gird location
     * of this cell.
     * 
     * @return a copy of the grid coordinates of this cell
     */
    public Point getPoint()
    {
        Point   point   = new Point( this.point );
        return point;
    }
    
    /**
     * Convenience routine for environments 
     * that allow only two states for a cell: alive or dead.
     * Returns false (dead) if the cell's state is 0,
     * otherwise returns true (alive).
     * 
     * @return  false if the cell's state is dead, true if it's alive
     */
    public boolean isAlive()
    {
        boolean alive   = state == 0 ? false : true;
        return alive;
    }
    
    /**
     * Convenience routine for environments 
     * that allow only two states for a cell: alive or dead.
     * Alive cells are given a state of 1,
     * and dead cells are given a state of 0.
     * @param alive
     */
    public void setAlive( boolean alive )
    {
        setState( alive ? 1 : 0 );
    }
    
    /**
     * Returns a string representation of this Cell.
     * 
     * @return  a string representation of this Cell
     */
    @Override
    public String toString()
    {
        StringBuilder   bldr    = new StringBuilder( point.toString() );
        bldr.append( ",state=" ).append( state );
        return bldr.toString();
    }
    
    /**
     * Tests this Cell against a given object for equality.
     * Equality pertains if the given object is a Cell,
     * and its x and y coordinates
     * and living status are all equal.
     * 
     * @param   obj the given Object
     * 
     * @return true, if this Cell is equal to the given object
     */
    @Override
    public boolean equals( Object obj )
    {
        boolean result = false;
        if ( obj != null && obj instanceof Cell )
        {
            Cell    that    = (Cell)obj;
            result  = this.point.equals( that.point )
                && this.state == that.state;
        }
        return result;
    }
    
    /**
     * Produces a hashcode for this Cell.
     * Required because <em>equals</em> is overridden.
     * 
     * @return  a hashcode for this Cell
     * 
     * @see Objects#hash(Object...)
     */
    @Override
    public int hashCode()
    {
        int hash    = Objects.hash( point, state );
        return hash;
    }
}
