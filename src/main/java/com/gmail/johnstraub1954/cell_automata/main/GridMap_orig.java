package com.gmail.johnstraub1954.cell_automata.main;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

/**
 * Encapsulates all cells in a grid. 
 * Each cell is associated with a point
 * and an integer state;
 * In the context of an environment
 * in which states are limited to <em>alive</em> and <em>dead</em>,
 * non-0 states represent live cells,
 * and states with 0 values represent dead cells.
 * Theoretically, the grid has an infinite extent,
 * and the grid map is infinitely large.
 * Only live cells are physically stored in the map; 
 * any location not physically present in the map
 * is assumed to be a valid, but dead, cell.
 * Every valid <em>get</em> invocation returns a physical cell.
 * 
 * To facilitate traversal of a grid,
 * this class can generate a rectangle that contains
 * all live cells.
 * 
 * Note about using a rectangle for a range:
 * 
 * Given a Rectangle with:<br>
 * (x,y) = (5,4), width = 5, height = 5
 * <blockquote>
 * Horizontal line segment (5,4) - (5,8) (inclusive)
 * is in-bounds, (5,9) is not.
 * <br>
 * Vertical line segment (5,4) - (9,4) (inclusive) 
 * is in-bounds, (10,4)) is not
 * </blockquote>
 * 
 * 
 * @author Jack Straub
 *
 */
public class GridMap_orig implements Iterable<Cell>
{
    /** 
     * The map that defines the contents of the map.
     * Virtually speaking, the grid is infinite, and every cell in
     * the grid exists; however, only live cells
     * (cells with non-0 states) are physically
     * stored in the map. 
     */
    private final Map<Point,Integer>    grid;
    
    /**
     * Default constructor.
     */
    public GridMap_orig()
    {
        grid = new HashMap<>();
    }
    
    /**
     * Copy constructor.
     * 
     * @param copyFrom  the gridMap to copy
     */
    public GridMap_orig( GridMap_orig copyFrom )
    {
        grid = new HashMap<>();
        grid.putAll( copyFrom.grid );
    }
    
    /**
     * Returns the cell corresponding to a given coordinate pair.
     * 
     * @param   xco     the x-coordinate of the target cell
     * @param   yco     the y-coordinate of the target cell
     * 
     * @return the cell corresponding to the given coordinates
     */
    public Cell get( int xco, int yco )
    {
        Point   point   = new Point( xco, yco );
        Cell    cell    = get( point );
        return cell;
    }
    
    /**
     * Returns the cell corresponding to a given point.
     * 
     * @param   point   the location of the target cell
     *  
     * @return the cell corresponding to the given point
     */
    public Cell get( Point point )
    {
        Integer state   = grid.get( point );
        if ( state == null )
            state = 0;
        Cell    cell    = new Cell( point, state );
        return cell;
    }
    
    /**
     * Returns a HexCell corresponding to given coordinates.
     * 
     * @param rowCol    the given coordinates
     * 
     * @return  a HexCell corresponding to given coordinates
     */
    public HexCell getHexCell( RowCol rowCol )
    {
        // map the row/col coordinates to x/y coordinates
        Point   point   = new Point( rowCol.col, rowCol.row );
        Integer state   = grid.get( point );
        if ( state == null )
            state = 0;
        HexCell cell    = new HexCell( rowCol, state );
        return cell;
    }
    
    /**
     * Specifies the state of a cell at a given location.
     * The previous state of the cell is returned.
     * This is a convenience method to treat <em>state</em>
     * as a boolean value,
     * for the convenience of applications
     * that recognize only two states,
     * <em>alive</em> (true) and <em>dead</em>.
     * 
     * @param xco       the x-coordinate of the given location
     * @param yco       the y-coordinate of the given location
     * @param state     the state of the cell,
     *                  true for a live cell, false for a dead cell
     * 
     * @return  the previous state of the cell
     * 
     * @see #put(int, int, int)
     * @see #put(Point, int)
     */
    public Cell put( int xco, int yco, boolean state )
    {
        int     intState    = state ? 1 : 0;
        Cell    cell        = put( new Point( xco, yco ), intState );
        return cell;
    }
    
    /**
     * Specifies the value of a cell at a given location.
     * The previous value of the cell is returned.
     * 
     * @param xco       the x-coordinate of the given location
     * @param yco       the y-coordinate of the given location
     * @param state     the state of the cell
     * 
     * @return  the previous value of the cell
     */
    public Cell put( int xco, int yco, int state )
    {
        Cell    cell    = put( new Point( xco, yco ), state );
        return cell;
    }
    
    /**
     * Adds a cell to the grid.
     * 
     * @param cell  the cell to add
     * 
     * @return  the previous value of the cell at the given location
     */
    public Cell put( Cell cell )
    {
        Cell    result  = put( cell.getPoint(), cell.getState() );

        return result;
    }
    
    /**
     * Specifies the state of a cell at a given location.
     * The previous state of the cell is returned.
     * This is a convenience method to treat <em>state</em>
     * as a boolean value,
     * for the convenience of applications
     * that recognize only two states,
     * <em>alive</em> (true) and <em>dead</em>.
     * 
     * @param point     the given point
     * @param state     the state of the cell,
     *                  true for a live cell, false for a dead cell
     * 
     * @return  the previous state of the cell
     * 
     * @see #put(Point, int)
     */
    public Cell put( Point point, boolean state )
    {
        int     intState    = state ? 1 : 0;
        Cell    cell        = put( point, intState );

        return cell;
    }
    
    /**
     * Specifies the state of a cell at a given point.
     * The previous state of the cell is returned.
     * 
     * @param point     the given point
     * @param state     the state of the cell
     * 
     * @return  the previous value of the cell
     */
    public Cell put( Point point, int state )
    {
        Cell    cell    = get( point );
        if ( state == 0 )
            grid.remove( point );
        else
            grid.put( point, state );
        return cell;
    }
    
    /**
     * Return a rectangle that encloses all live cells in the grid.
     * 
     * @return a rectangle that encloses all live cells in the grid
     */
    public Rectangle getLiveRectangle()
    {
        Set<Point>  keySet  = grid.keySet();
        Rectangle   rect    = null;
        
        if ( keySet.isEmpty() )
            rect = new Rectangle( 0, 0, 0, 0 );
        else
        {
            int         minX    = Integer.MAX_VALUE;
            int         maxX    = Integer.MIN_VALUE;
            int         minY    = Integer.MAX_VALUE;
            int         maxY    = Integer.MIN_VALUE;
            for ( Point point : keySet )
            {
                int xco = point.x;
                if ( xco < minX )
                    minX = xco;
                if ( xco > maxX )
                    maxX = xco;
    
                int yco = point.y;
                if ( yco < minY )
                    minY = yco;
                if ( yco > maxY )
                    maxY = yco;
            }
            
            int xExtent = maxX - minX + 1;
            int yExtent = maxY - minY + 1;
            rect    = new Rectangle( minX, minY, xExtent, yExtent );
        }
        return rect;
    }
    
    public Iterator<Cell> iterator()
    {
        Iterator<Cell>  iter    = new GenerationIterator();
        return iter;
    }
    
    /**
     * Returns an iterator that traverses every live cell
     * in the map.
     * 
     * @return  an iterator that traverses every live cell
     *          in the map
     */
    public Iterator<Cell> liveIiterator()
    {
        Rectangle       rect    = getLiveRectangle();
        Iterator<Cell>  iter    = new CellIterator( rect );
        return iter;
    }
    
    /**
     * Returns an iterator that traverses every live cell
     * in a given rectangle.
     * 
     * @param rect  the given rectangle
     * 
     * @return  an iterator that traverses every live cell
     *          in the given rectangle
     */
    public Iterator<Cell> iterator( Rectangle rect )
    {
        Iterator<Cell>  iter    = new CellIterator( rect );
        return iter;
    }

    /**
     * Returns the upper-left-corner of a rectangle that encloses
     * all live cells.
     * 
     * @return  the upper-left-corner of a rectangle that encloses
     *          all live cells
     */
    public Point getUpperLeftCorner()
    {
        Rectangle   rect    = getLiveRectangle();
        Point       ulc     = new Point( rect.x, rect.y );
        return ulc;
    }
    
    /**
     * Computes a hashcode for this object.
     * Required because equals is overridden.
     * 
     * @return  a hashcode for this object
     */
    @Override
    public int hashCode()
    {
        int hash    = Objects.hash( grid );
        return hash;
    }
    
    /**
     * Determines if this GridMap is equals to
     * a given object.
     * The result is true if the given object
     * is a GridMap,
     * and all of its members are equal to
     * the corresponding members of this GridMap.
     * 
     * @param   the given object
     * 
     * @return true if this GridMap is equal to the given object
     */
    @Override
    public boolean equals( Object obj )
    {
        boolean result  = false;
        if ( obj == null )
            result = false;
        else if ( this == obj )
            result = true;
        else if ( !(obj instanceof GridMap_orig) )
            result = false;
        else
        {
            GridMap_orig that    = (GridMap_orig)obj;
            result = this.grid.equals( that.grid );
        }
        return result;
    }

    /**
     * This class is used to iterate over every live cell
     * in some rectangle.
     * 
     * @author Jack Straub
     */
    private class CellIterator implements Iterator<Cell>
    {
        /** The source Rectangle */
        private final Rectangle rect;
        
        /** 
         * Iterator over all keys in the map.
         * Note that only live cells are stored in the map,
         * so every key maps to a live cell.
         */
        private Iterator<Point> pointIter;
        
        /** Next cell to return; null if none. */
        private Cell            next;
        
        public CellIterator( Rectangle rect )
        {
            this.rect = rect;
            Set<Point>   keys    = grid.keySet();
            pointIter = keys.iterator();
            next = nextCell();
        }
        
        /**
         * Returns true if this iterator can return another element.
         * 
         * @return true, if this iterator can return another element
         */
        @Override
        public boolean hasNext()
        {
            boolean result  = next != null;
            return result;
        }

        /**
         * Returns the next Cell associated with this iterator.
         * Throws NoSuchElement exception if there is no next Cell.
         * 
         * @return  the next Cell associated with this iterator
         * 
         * @throws NoSuchElementException if there is no next Cell
         */
        @Override
        public Cell next() throws NoSuchElementException
        {
            if ( next == null )
            {
                String  message = "Iterator exhausted";
                throw new NoSuchElementException( message );
            }
            
            Cell    cell    = next;
            next = nextCell();
            
            return cell;
        }
        
        /**
         * Locates the "next" live cell that falls within the range
         * of the given rectangle.
         * Cells are not returned in any particular order.
         * 
         * @return the "next" live cell in the rectangle
         */
        private Cell nextCell()
        {
            Cell    nextCell = null;
            while ( nextCell == null && pointIter.hasNext() )
            {
                Point   point   = pointIter.next();
                
                if ( rect.contains( point) )
                    nextCell = new Cell( point, true );
            }
            return nextCell;
        }
    }
    
    private class GenerationIterator implements Iterator<Cell>
    {
        private static final int    extendRectBy    = 2;
        
        private final int   firstRow;
        private final int   firstCol;
        private final int   lastRow;
        private final int   lastCol;
        
        private Point   nextLoc;
        
        public GenerationIterator()
        {
            Rectangle   rect    = getLiveRectangle();
            firstRow = rect.y - extendRectBy;
            firstCol = rect.x - extendRectBy;
            lastRow = firstRow + rect.height + 2 * extendRectBy;
            lastCol = firstCol + rect.width + 2 * extendRectBy;
            
            nextLoc = new Point( firstCol, firstRow );
        }

        @Override
        public boolean hasNext()
        {
            return nextLoc != null;
        }

        @Override
        public Cell next() throws NoSuchElementException
        {
            if ( nextLoc == null )
            {
                String  message = "Iterator bounds exceeded";
                throw new NoSuchElementException( message );
            }
            
            Cell    nextCell    = get( nextLoc );
            if ( ++nextLoc.x >= lastCol )
            {
                nextLoc.x = firstCol;
                if ( ++nextLoc.y >= lastRow )
                    nextLoc = null;;
            }

            return nextCell;
        }
    }
}
