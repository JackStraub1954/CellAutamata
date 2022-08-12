package com.gmail.johnstraub1954.cell_automata.geometry;

import java.awt.Dimension;
import java.awt.geom.Path2D;

/**
 * This interface identifies the operations
 * that a tile (of some dimension)
 * needs to perform
 * in order to support classes
 * that do tiling and tiling-related tasks.
 * 
 * @author Jack S.
 *
 */
public interface GridTile
{
    /** 
     * Given the dimensions of a plane, 
     * and the length of a side of this tile,
     * return the number of columns and rows
     * that will fit in the plane.
     * The goal is to provide dimensions
     * that will fully tessellate the plane.
     * Since a given plane often won't accommodate
     * an exact number or columns/rows,
     * the numbers returned will typically exceed
     * the boundaries of the plane,
     * typically by one column and one row.
     * For example,
     * if the width of a plane is 95 pixels,
     * and the width of a tile is 10 pixels,
     * the number of columns returned
     * will be 10.
     * 
     * @param   size    the width and height of the plane to tile
     *                  
     * @return  the number of columns and rows
     *          (width and height)
     *          of this tile to use
     *          to tile the given plane
     */
    public Dimension getColRowDimension( Dimension size );
    
    /**
     * Given the coordinates of this tile
     * return a path that can be used 
     * to draw/fill this tile.
     * 
     * @param coords    the coordinates (column/row)
     *                  to be used to draw this tile
     * 
     * @return  a path that can be used to draw/fill this tile
     */
    public Path2D getPath( Offset coords );
    
    /**
     * Returns a neighborhood suitable for spawning new generations
     * of this type of tile.
     * Note that there are different types of neighborhoods
     * for different tile shapes.
     * For a given shape there might be extended neighborhoods,
     * or neighborhoods based on the dimension 
     * of the autamaton.
     * 
     * @param self	offset to the grid location
     *              about which to form a neighborhood
     *              
     * @return	a neighborhood suitable for spawning new generations
     * 			of this type of tile
     */
    public Neighborhood getNeighborhood( Offset self );
    
    /**
     * Given pixel coordinates, find the corresponding tile in Offset coordinates.
     * 
     * @param xco   given x pixel coordinate
     * @param yco   given y pixel coordinate
     * 
     * @return  Offset coordinates of tile 
     *          corresponding to the given pixel coordinates
     */
    public Offset getSelected( int xco, int yco );
    
    public GridTile ofValues( String ...strings );
}
