/**
 * 
 */
package com.gmail.johnstraub1954.cell_automata.main;

import java.awt.Point;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 * An instance of this class represents a regular hexagon
 * which can be used to tile a plane.
 * 
 * @author Jack Straub
 */
/**
 * @author Jack Straub
 *
 */
public class HexTile
{
    /** The length of a side of this hexagon. */
    private final double    sideLen;
    
    /** 
     * Reference polygon for generating a path for a specific hexagon.
     * Generating the path requires specifying the center of the hexagon
     * (x- and y-coordinates, in pixels) and specifying the orientation
     * (angle, in radians).
     * 
     * @see Polygon#getPath(double, double, double)
     */
    private final Polygon   hexagon;
    
    /** The radius of this hexagon (a.k.a. "circumradius") */
    private final double    radius;
    
    /** The apothem of this hexagon (a.k.a. "inradius") */
    private final double    apothem;
    
    /** 
     * The "height" of this hexagon, better know as the "long axis."
     * It's the length of the line segment drawn through opposite
     * vertices.
     */
    private final double    height;
    
    /** 
     * The "width" of this hexagon, better know as the "short axis."
     * It's the length of the line segment drawn between opposite
     * sides.
     */
    private final double    width;
    
    /** The vertical offset used to position sequential rows. */
    private final double    rowOffset;
    
    /** 
     * The vertical offset used to position sequential columns.
     * This does not include the offset of the first column,
     * which is 0 for even rows and <em>apothem</em>
     * for odd rows.
     */
    private final double    colOffset;
    
    /**
     * Constructor.
     * 
     * @param sideLen   the length of the side for this HexTile
     */
    private HexTile( double sideLen )
    {
        this.sideLen = sideLen;
        this.hexagon = Polygon.ofSide( 6,  sideLen );
        this.radius = hexagon.getRadius();
        this.apothem = hexagon.getApothem();
        this.height = 2 * radius;
        this.width = 2 * apothem;
        this.rowOffset = height * 3. / 4.;
        this.colOffset = width;
    }
    
    /**
     * Returns a HexTile with a given side length.
     * 
     * @param sideLen   the given side length 
     * 
     * @return  HexTile with a given side length
     */
    public static HexTile ofSide( double sideLen )
    {
        HexTile tile    = new HexTile( sideLen );
        return tile;
    }
    
    /**
     * Returns a path that can be used to draw a hexagon
     * at a given row and column, with a "vertical" orientation
     * (one vertex pointing north, at an angle of -Pi/2 radians).
     * A hexagon with (row, column) coordinates of (0, 0)
     * will have a side coincident with the y-axis, and a vertex
     * on the x-axis.
     * 
     * @param row   the given row
     * @param col   the given column
     * 
     * @return  a path representing a hexagon at the given coordinates,
     *          and with a "vertical" orientation
     */
    public Path2D getPath( int row, int col )
    {
        double  firstColOffset   = 
            (row % 2) == 0 ? apothem : 2 * apothem;
        double  centerX     = firstColOffset + col * colOffset;
        double  centerY     = radius + row * rowOffset;
        double  angle       = -Math.PI / 2;
        Path2D  path        = hexagon.getPath( centerX, centerY, angle );
        return path;
    }
    
    /**
     * Based on pixel coordinates, calculate a path representing
     * the selected hexagon.
     * Coordinates are typically determined by mouse selection.
     * To find the selected hexagon, calculate the rectangle corresponding
     * to the selection:
     * <img 
     *     style="margin-left: 2em;"
     *     src="doc-files/SelectingAHexagonSmall.png" 
     *     alt="Selecting a Hexagon (small)"
     * >
     * <p style="margin-top: -1em">
     * Each rectangle should span the upper five verticies of a hexagon,
     * which will include a portion of the hexagon one row up to the left,
     * and one row up to the right. One of these three hexagons 
     * will contain the selected point. 
     * 
     * @param xco   x pixel coordinate
     * @param yco   y pixel coordinate
     * 
     * @return  the path representing the hexagon associated
     *          with pixel coordinates
     */
    public Path2D getSelected( final int xco, final int yco )
    {
        // the row corresponding to the selected hexagon
        int row     = (int)(yco / rowOffset);
        
        // the column corresponding to the selected hexagon;
        // must compensate for the indentation of odd-numbered rows.
        int workXco = row % 2 == 0 ? xco : (int)(xco - apothem + .5);
        int col     = (int)(workXco / colOffset);
        
        List<RowCol> candidates  = new ArrayList<>();
        // principal hexagon identified by 
        candidates.add( new RowCol( row, col ) );
        
        // column numbers for the hexagons above the principal
        // are different depending on whether the row is
        // even- or odd-numbered.
        if ( row % 2 == 0 )
        {
            // one row up, half-col left
            candidates.add( new RowCol( row - 1, col - 1 ) );
            // one row up, half-col right
            candidates.add( new RowCol( row - 1, col ) );
        }
        else
        {
            // one row up, half-col left
            candidates.add( new RowCol( row - 1, col ) );
            // one row up, half-col right
            candidates.add( new RowCol( row - 1, col + 1 ) );
        }
        
        Path2D  result  = null;
        for ( RowCol candidate : candidates )
        {
            int     candRow = candidate.row;
            int     candCol = candidate.col;
            Path2D  path    = getPath( candRow, candCol );
            if ( path.contains( xco, yco ) )
            {
                result = path;
                break;
            }
        }
        
        return result;
    }
}
