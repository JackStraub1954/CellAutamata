/**
 * 
 */
package com.gmail.johnstraub1954.cell_automata.main;

import java.util.Objects;

/**
 * Encapsulation of a hexagonal cell in a cellular automata context.
 * Every cell has two coordinates and a state.
 * Properties:
 * <ol>
 *      <li>
 *          row - the vertical component of a cell: integer.
 *          Equivalent of the y-coordinate in the Cartesian plane.
 *      </li>
 *      <li>
 *          column - the horizontal component of a cell: integer.
 *          Equivalent to the x-coordinate in the Cartesian plane.
 *          in a 2D matrix. 
 *      </li>
 *      <li>
 *          state - state of cell: integer. 
 *          Interpretation of this value
 *          is context dependent.
 *          In the Hexagonal Game of Life, for example,
 *          possible states may be limited to 
 *          1 (alive) or 0 (dead).
 *      </li>
 * </ol>
 * @author Jack Straub
 *
 */
public class HexCell
{
    /** 
     * Row coordinate of this cell. 
     * This object is not modifiable by the user.
     * 
     * @see #getRow
     * @see #getRowCol
     */
    private final int       row;
    
    /** 
     * Column coordinate of this cell. 
     * This object is not modifiable by the user.
     * 
     * @see #getCol
     * @see #getRowCol
     */
    private final int       col;
    
    /** The state of this cell. */
    private int state;

    /**
     * Default constructor. 
     * Row and column coordinates 
     * are explicitly set to 0;
     * state is explicitly set to 0.
     */
    public HexCell()
    {
        this( 0, 0, 0 );
    }
    
    /**
     * Constructor to set the row and column coordinates of this cell.
     * state is explicitly set to 0.
     * 
     * @param row   row coordinate of this cell
     * @param col   column coordinate of this cell
     */
    public HexCell( int row, int col )
    {
        this( row, col, 0 );
    }
    
    /**
     * Constructor to set the row and column coordinates of a cell.
     * State is explicitly set to 0.
     * 
     * @param rowCol the given row and column coordinates
     */
    public HexCell( RowCol rowCol )
    {
        this( rowCol.row, rowCol.col, 0 );
    }
    
    /**
     * Constructor to set the coordinates of a cell
     * to the given row and column,
     * and the state to a given value. 
     * The state is specified as a boolean
     * (true = alive, false = dead);
     * internally, alive maps to a state of 1,
     * dead maps to a state of 0.
     * 
     * @param rowCol    the given coordinates
     * @param state     the given value for state
     */
    public HexCell( RowCol rowCol, boolean alive )
    {
        this( rowCol.row, rowCol.col, alive ? 1 : 0 );
    }
    
    /**
     * Copy constructor.
     * 
     * @param cell  the cell to copy
     */
    public HexCell( HexCell cell )
    {
        this( cell.row, cell.col, cell.state );
    }
    
    /**
     * Constructor to set all properties of this cell.
     * The state is specified as a boolean
     * (true = alive, false = dead);
     * internally, alive maps to a state of 1,
     * dead maps to a state of 0.
     * 
     * @param row       the row coordinate of this cell
     * @param col       the column coordinate of this cell
     * @param state     the state of this cell
     */
    public HexCell( int row, int col, boolean state )
    {
        this( row, col, state ? 1 : 0 );
    }
    
    /**
     * Constructor to set all properties of this cell.
     * 
     * @param row       the row coordinate of this cell
     * @param col       the column coordinate of this cell
     * @param state     the state of this cell
     */
    public HexCell( int row, int col, int state )
    {
        this.row = row;
        this.col = col;
        this.state = state;
    }
    
    /**
     * Constructor to set the coordinates of a cell
     * to the given row and column,
     * and the state to a given value. 
     * 
     * @param rowCol    the given coordinates
     * @param state     the given value for state
     */
    public HexCell( RowCol rowCol, int state )
    {
        this( rowCol.row, rowCol.col, state );
    }
    
    /**
     * Returns the coordinates of a cell
     * relative to this Cell
     * in a given direction.
     * 
     * @param dir   the given direction
     * 
     * @return  cell coordinates relative to this Cell 
     *          in the given direction
     * 
     * @throws  CAException if the given direction is invalid
     *          for a hexagonal grid
     */
    public RowCol getRelativeCoordinates( Direction dir )
    {
        RowCol  rowCol  = null;
        switch ( dir )
        {
        case NE:
            rowCol = new RowCol( row - 1, col + 1 );
            break;
        case E:
            rowCol = new RowCol( row, col + 1 );
            break;
        case SE:
            // the cell down/right is the same column
            // as this cell
            rowCol = new RowCol( row + 1, col );
            break;
        case SW:
            rowCol = new RowCol( row + 1, col - 1 );
            break;
        case W:
            rowCol = new RowCol( row, col - 1 );
            break;
        case NW:
            // the cell up/left is the same column
            // as this cell
            rowCol = new RowCol( row - 1, col );
            break;
        default:
            String  msg = 
                dir + " is an invalid direction for a hexagonal grid.";
            throw new CAException( msg );
        }
        return rowCol;
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
     * Getter for the row coordinate of this cell's grid location.
     * @return  the row coordinate of this cell
     */
    public int getRow()
    {
        return row;
    }
    
    /**
     * Getter for the column coordinate of this cell
     * @return  the column coordinate of this cell
     */
    public int getCol()
    {
        return col;
    }
    
    /**
     * Gets a copy of the grid coordinates of this cell.
     * Modifying the returned object
     * will <em>not</em> modify the grid location
     * of this cell.
     * 
     * @return a copy of the grid coordinates of this cell
     */
    public RowCol getCoordinates()
    {
        RowCol  rowCol  = new RowCol( row, col );
        return rowCol;
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
        StringBuilder   bldr    = new StringBuilder();
        bldr.append( "row=" ).append( row );
        bldr.append( ",col=" ).append( col );
        bldr.append( ",state=" ).append( state );
        return bldr.toString();
    }
    
    /**
     * Tests this Cell against a given object for equality.
     * Equality pertains if the given object is a HexCell,
     * and its row and column coordinates
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
        if ( obj != null && obj instanceof HexCell )
        {
            HexCell    that    = (HexCell)obj;
            result  = 
                this.row == that.row
                && this.col == that.col
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
        int hash    = Objects.hash( row, col, state );
        return hash;
    }
}
