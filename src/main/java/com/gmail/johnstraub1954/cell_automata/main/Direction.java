package com.gmail.johnstraub1954.cell_automata.main;

import java.awt.Point;

/**
 * Encapsulation of the first 8 principle compass points.
 * Properties:
 * <ol>
 * <li>vertical - vertical direction component (1 = north, -1 = south)</li>
 * <li>horizontal - horizontal direction component (1 = north, -1 = south)</li>
 * </ol>
 * 
 * @author Jack Straub
 *
 */
public enum Direction
{
    N( 1, 0 ),
    NE( 1, 1 ),
    E( 0, 1 ),
    SE( -1, 1 ),
    SOUTH( -1, 0 ),
    SW( -1, -1 ),
    W( 0, -1 ),
    NW( 1, -1 );

    /** vertical component of this direction; 1 = north, -1 = south */
    private final int vertical;
    /** horizontal component of this direction; 1 = east, -1 = west */
    private final int horizontal;
    
    private Direction( int vertical, int horizontal )
    {
        this.vertical = vertical;
        this.horizontal = horizontal;
    }

    /**
     * Returns the vertical component of this direction.
     * 
     * @return  1 for north component, -1 for south component
     */
    public int getVertical()
    {
        return vertical;
    }

    /**
     * Returns the horizontal component of this direction.
     * 
     * @return  1 for east, -1 for west
     */
    public int getHorizontal()
    {
        return horizontal;
    }
    
    /**
     * Returns a Point constructed from the horizontal (x-coordinate)
     * and vertical (y-coordinate) components of this Direction.
     * 
     * @return  a Point constructed from the horizontal 
     *          and vertical components of this Direction.
     */
    public Point getPoint()
    {
        Point   point   = new Point( horizontal, vertical );
        return point;
    }
}
