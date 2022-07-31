package com.gmail.johnstraub1954.cell_automata.geometry;

import java.awt.Point;
import java.io.Serializable;
import java.util.Objects;

/**
 * Maintains very basic information regarding data that can be stored
 * in a 2-dimensional grid. The two dimensions are referred to as 
 * column (x-coordinate) and row (y-coordinate) and are stored as integers.
 * Instances of this class are final.
 * 
 * There's not much difference between this class and java.awt.Point,
 * but it's name makes the documentation clearer (for example,
 * "convertOffsetToAxial(Offset offset, Hex axial )") and leaves Point
 * (actually Point2D) to refer to pixel coordinates.
 *
 * @author Jack Straub
 *
 */
public class Offset implements Serializable
{
    /** Generated serial version UID */
    
    private static final long serialVersionUID = -4362208421970460748L;
    /** The column component of this offset */
    public final int    col;
    /** The row component of this offset */
    public final int    row;
    
    /**
     * Constructor.
     * 
     * @param col   column component of this offset
     * @param row   row component of this offset
     */
    public Offset( int col, int row )
    {
        this.col = col;
        this.row = row;
    }
    
    /**
     * Copy constructor.
     * 
     * @param toCopy    the instance to copy
     */
    public Offset( Offset toCopy )
    {
        col = toCopy.col;
        row = toCopy.row;
    }
    
    /**
     * Constructor. Convenient for converting a java.awt.Point
     * to an Offset.
     * 
     * @param point the Point to convert
     */
    public Offset( Point point )
    {
        col = point.x;
        row = point.y;
    }

    /**
     * Returns the column component of this offset.
     * 
     * @return the column component of this offset
     */
    public int getCol()
    {
        return col;
    }

    /**
     * Returns the row component of this offset.
     * 
     * @return the row component of this offset
     */
    public int getRow()
    {
        return row;
    }
    
    /**
     * Tests this offset against another object for equality.
     * The two objects are equal if the other object is an Offset,
     * and its row and column components are equal to those
     * of this offset.
     * 
     * @return  true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals( Object obj )
    {
        boolean result  = false;
        if ( obj == null )
            ;
        else if ( obj instanceof Offset )
        {
            Offset  that    = (Offset)obj;
            result = this.col == that.col && this.row == that.row;
        }
        return result;
    }
    
    /**
     * Calculates a hash code for this object.
     * This method is required to be overridden 
     * because equals(Object) is overridden.
     * 
     * @return  a hash code for this object
     */
    @Override
    public int hashCode()
    {
        int hash    = Objects.hash( col, row );
        return hash;
    }
    
    /**
     * Returns a string representation of this Offset
     * in the format "(col,row)".
     * 
     * @return a string representation of this Offset
     */
    @Override
    public String toString()
    {
        StringBuilder   bldr    = new StringBuilder();
        bldr.append( "(" ).append( col ).append( "," )
            .append( row ).append( ")" );
        return bldr.toString();
    }
}

