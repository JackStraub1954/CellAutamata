package com.gmail.johnstraub1954.cell_automata.main;

import java.util.Objects;

/**
 * An instance of this class represents a row/column pair.
 * It's really not much difference from java.awt.Point,
 * but with a point the x-coordinate (the column) comes first
 * followed by the y-coordinate (the row).
 * In a row column pair, the row (the y-coordinate) 
 * is specified first.
 * 
 * @author Jack Straub
 */
public class RowCol
{
    public final int    row;
    public final int    col;
    
    public RowCol( int row, int col )
    {
        this.row = row;
        this.col = col;
    }
    
    /**
     * Gets the row component of this pair.
     * 
     * @return the row component of this pair
     */
    public int getRow()
    {
        return row;
    }
    
    /**
     * Gets the column component of this pair.
     * 
     * @return the column component of this pair
     */
    public int getCol()
    {
        return col;
    }
    
    /**
     * Returns a string representation of this row/column pair.
     * 
     * @return  a string representation of this row/column pair
     */
    @Override
    public String toString()
    {
        StringBuilder   bldr    = new StringBuilder();
        bldr.append( "row=" ).append( row )
            .append( ",col=" + col );
        return bldr.toString();
    }
    
    /**
     * Determines if an object is equal to this row/column pair.
     * The object is equal if it is type RowCol and its row and column
     * components are equal to those of this RowCol.
     */
    @Override
    public boolean equals( Object obj )
    {
        boolean result  = false;
        if ( obj instanceof RowCol )
        {
            RowCol  that    = (RowCol)obj;
            result = this.row == that.row && this.col == that.col;
        }
        return result;
    }
    
    /**
     * Returns a hash of this row/column pair.
     * This method is required to be overridden
     * because equals is overridden.
     */
    @Override
    public int hashCode()
    {
        int hashcode    = Objects.hash( row, col );
        return hashcode;
    }
}
