/**
 * 
 */
package com.gmail.johnstraub1954.cell_automata.geometry;

import java.awt.Dimension;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

import Jama.Matrix;

/**
 * An instance of this class represents a regular hexagon
 * which can be used to tile a plane.
 * The hexagons used for tiling
 * can be selected by providing pixel coordinates
 * for the target hexagon
 * (usually using the mouse).
 * <p>
 * All the concepts herein were taken from the
 * <a href="https://www.redblobgames.com/grids/hexagons/">Hexagonal Grids</a>
 * page, written by 
 * <a href="http://www-cs-students.stanford.edu/~amitp/">
 * Amit Patel
 * </a>
 * with contributions from 
 * <a href="http://www-cs-students.stanford.edu/~amitp/Articles/Hexagon2.html">
 * Charles Fu
 * </a> 
 * and
 * <a href="http://www-cs-students.stanford.edu/~amitp/Articles/HexLOS.html">
 * Clark Verbrugge
 * </a>.
 * The 
 * <a href="https://math.nist.gov/javanumerics/jama/">Jama</a>
 * matrix library 
 * is provided by the 
 * <a href="https://math.nist.gov/">
 * National Institute of Standards and Technology
 * </a>
 * (NIST).
 * 
 * @author Jack Straub
 * 
 * @see <a href="https://www.redblobgames.com/grids/hexagons/">Hexagonal Grids</a>
 *      by
 *      <a href="http://www-cs-students.stanford.edu/~amitp/">
 *      Amit Patel
 *      </a>
 * @see <a href="https://math.nist.gov/javanumerics/jama/">Jama</a>
 *      
 */
/**
 * @author java1
 *
 */
public class HexTile implements GridTile
{
    /**
     * Constant indicating a hexagon with a vertical layout.
     * Such a hexagon has a vertex pointing West,
     * and is also known as a flat-top hexagon.
     * <pre>
     *    __
     *   /  \
     *   \__/</pre>
     * @see #HORIZONTAL
     */
    public static final int     VERTICAL    = 0;
    
    /**
     * Constant indicating a hexagon with a horizontal layout.
     * Such a hexagon has a vertex pointing North,
     * and is also known as a pointy-top hexagon.
     * <pre>
     *    /\
     *   |  |
     *    \/</pre>
     * @see #VERTICAL
     */
    public static final int     HORIZONTAL  = 1;
    
    /** The layout strategy for this hexagon. */
    private final HexLayout     layout;
    
    /** 
     * Reference polygon for generating a path for a specific hexagon.
     * Generating the path requires specifying the center of the hexagon
     * (x- and y-coordinates, in pixels) and specifying the layout
     * (angle, in radians).
     * 
     * @see Polygon#getPath(double, double, double)
     */
    private final Polygon   hexagon;
    
    /** 
     * Matrix multiplier for converting axial coordinates to pixel coordinates.
     * Formulating the matrix requires knowing the layout of the hexagon
     * and the length of a side.
     */
    private final Matrix    hexToPixelMatrixXier;
    
    /** 
     * Matrix multiplier for converting pixel coordinates to axial coordinates.
     * Formulating the matrix requires knowing the layout of the hexagon
     * (horizontal vs. vertical)
     * and the length of a side.
     */
    private final Matrix    pixelToHexMatrixXier;
    
    /**
     * Constructor.
     * Creates a HexTile with the given side length
     * and layout.
     * Orientation may be VERTICAL
     * (with a vertex pointing west)
     * or HORIZONTAL
     * (with a vertex pointing north).
     * Layout strategy defaults to ODD_R for HORIZONTAL
     * and ODD_Q FOR VERTICAL.
     * 
     * @param sideLen       the length of the side for this HexTile
     * @param orientation   the given layout
     * 
     * @see #ofSide(double, int)
     */
    private HexTile( double sideLen, int orientation )
    {
        this( 
            sideLen, 
            orientation == HORIZONTAL ? HexLayout.ODD_R : HexLayout.ODD_Q
        );
    }
    
    /**
     * Constructs a HexTile with the given side length and layout.
     * 
     * @param sideLen   the given side length
     * @param layout    the given layout
     * 
     * @see #ofSide(double, HexLayout)
     */
    private HexTile( double sideLen, HexLayout layout )
    {
        this.layout = layout;
        this.hexagon = Polygon.ofSide( 6,  sideLen );
        hexToPixelMatrixXier = layout.getHexToPixelMultiplier( sideLen );
        pixelToHexMatrixXier = layout.getPixelToHexMultiplier( sideLen );
    }
    
    /**
     * Returns a HexTile with a given side length
     * and orientation.
     * Orientation may be VERTICAL, 
     * with a vertex positioned at PI/2,
     * or HORIZONTAL,
     * with a vertex positioned at PI.
     * <pre>
     *       _           /\
     *      / \         |  |
     *      \_/          \/
     *   vertical   horizontal</pre>
     * 
     * @param sideLen       the given side length 
     * @param orientation   the given orientation
     * 
     * @return  HexTile with the given orientation and side length
     */
    public static HexTile ofSide( double sideLen, int orientation )
    {
        HexTile tile    = new HexTile( sideLen, orientation );
        return tile;
    }
    
    /**
     * Returns a HexTile with a given side length and layout.
     * 
     * @param sideLen       the given side length 
     * @param layout   the given layout
     * 
     * @return  HexTile with the given layout and side length
     */
    public static HexTile ofSide( double sideLen, HexLayout layout )
    {
        HexTile tile    = new HexTile( sideLen, layout );
        return tile;
    }
    
    /**
     * Based on pixel coordinates, find the axial coordinates
     * of the selected hexagon.
     * Coordinates are typically determined by mouse selection.
     * 
     * @param xco   x pixel coordinate
     * @param yco   y pixel coordinate
     * 
     * @return  the axial coordinates
     *          of the selected hexagon
     */
    public Hex getSelectedHex( int xco, int yco )
    {
        Matrix          xyco    = new Matrix( new double[] { xco, yco }, 2 );
        Matrix          qrco    = pixelToHexMatrixXier.times( xyco );
        double          dQco    = qrco.get( 0, 0 );
        double          dRco    = qrco.get( 1, 0 );
        
        // Round fractional coordinates to integer coordinates.
        FractionalHex   fHex    = new FractionalHex( dQco, dRco );
        Hex             hex     = fHex.round();
        
        return hex;
    }
    
    /**
     * Given the axial coordinates of a hexagon,
     * find the screen coordinates at which to draw the hexagon.
     * 
     * @param hex   axial coordinates of given hexagon
     * 
     * @return  screen coordinates of given hexagon
     */
    public Point2D toPixelCoords( Hex hex )
    {
        Point2D pixels  = toPixelCoords( (int)hex.qco, (int)hex.rco );
        return pixels;
    }
    
    /**
     * Given the axial coordinates of a hexagon,
     * find the screen coordinates at which to draw the hexagon.
     * 
     * @param qco   q-coordinate of hexagon
     * @param rco   r-coordinate of hexagon
     * 
     * @return  screen coordinates of given hexagon
     */
    public Point2D toPixelCoords( int qco, int rco )
    {
        double[]    qrPair  = { qco, rco };
        Matrix      qrco    = new Matrix( qrPair, 2 );
        Matrix      xyco    = hexToPixelMatrixXier.times( qrco );
        double      xco     = xyco.get(0,  0 );
        double      yco     = xyco.get(1,  0 );
        Point2D     point   = new Point2D.Double( xco, yco );
        return point;
    }
    
    @Override
    public Offset getSelected( int xco, int yco )
    {
        Hex     hex     = getSelectedHex( xco, yco );
        Offset  offset  = layout.toOffset( hex );
        return offset;
    }
    
    /**
     * Get a path to draw this hexagon at the given axial coordinates.
     * 
     * @param hex   the given axial coordinates
     * 
     * @return  a path to draw this hexagon at the given axial coordinates
     */
    public Path2D getPath( Hex hex )
    {
        Path2D  path    = getPath( hex.qco, hex.rco );
        return path;
    }

    /**
     * Given the dimensions of a plane, 
     * return the number of columns and rows
     * needed to tesselate the plane with this tile.
     * 
     * @param   size    the width and height of the plane to tile
     */
    @Override
    public Dimension getColRowDimension( Dimension size )
    {
        double  radius      = hexagon.getRadius();
        double  apothem	    = hexagon.getApothem();
        int     cols        = 0;
        int	    rows        = 0;
        double  cellWidth;
        double  cellHeight;
        if ( getOrientation() == VERTICAL )
        {
            // In this orientation width of a cell is 2 * radius,
            // and the horizontal distance between the centers of 
            // adjacent cells is width * (3/4).
            cellWidth   = (2 * radius) * .75;
            cellHeight  = 2 * apothem;
        }
        else
        {
            // In this orientation height of a cell is 2 * radius,
            // and the vertical distance between the centers of 
            // adjacent cells is height * (3/4).
            cellWidth   = 2 * apothem;
            cellHeight  = (2 * radius) * .75;
        }
        
        // North and west edges of the first row/column overlap the
        // rectangle to be tessellated, so it is sometimes necessary 
        // to add an extra row/column to fully cover the rectangle
        cols = (int)Math.ceil( size.width / cellWidth ) + 1;
        rows = (int)Math.ceil( size.height / cellHeight ) + 1;
        Dimension	out		= new Dimension( cols, rows );
        return out;
    }
    
    /**
     * Get a path to draw this hexagon at the given offset coordinates.
     * 
     * @param offset    the given axial coordinates
     * 
     * @return  a path to draw this hexagon at the given offset coordinates
     */
    public Path2D getPath( Offset offset )
    {
        Hex     hex     = layout.toHex( offset );
        Path2D  path    = getPath( hex.qco, hex.rco );
        return path;
    }
    
    /**
     * Get a path to draw this hexagon at the given axial coordinates.
     * 
     * @param qco   q-coordinate of hexagon
     * @param rco   r-coordinate of hexagon
     * 
     * @return  a path to draw this hexagon at the given axial coordinates
     */
    public Path2D getPath( int qco, int rco )
    {
        Point2D center      = toPixelCoords( qco, rco );
        double  xco         = center.getX();
        double  yco         = center.getY();
        double  angle       = layout.getStartAngle();
        Path2D  polyPath    = hexagon.getPath( xco, yco, angle );
        
        return polyPath;
    }
    
    /**
     * Gets the length of a side of this hexagon.
     * 
     * @return  the length of a side of this hexagon
     */
    public double getSideLen()
    {
        return hexagon.getSideLen();
    }
    
    /**
     * Gets the orientation of this hexagon.
     * Orientation may be HORIZONTAL (vertex pointing North)
     * or VERTICAL (vertex pointing West).
     * 
     * @return  the orientation of this hexagon
     */
    public int getOrientation()
    {
        int orientation = 
            layout == HexLayout.ODD_R || layout == HexLayout.EVEN_R ? 
            HORIZONTAL : VERTICAL;
        return orientation;
    }
    
    /**
     * Gets the layout used to configure this hexagon.
     * 
     * @return  the layout used to configure this hexagon
     */
    public HexLayout getLayout()
    {
        return layout;
    }

	/**
	 * Gets a Neighborhood object suitable
	 * for spawning new generations of hexagonal tile.
     * 
     * @param self	offset to the grid location
     *              about which to form a neighborhood
     *              
     * @return	a neighborhood suitable for spawning new generations
     * 			of this type of tile
	 */
	@Override
	public HexNeighborhood getNeighborhood(Offset self)
	{
		HexNeighborhood   neighborhood	= new HexNeighborhood( self, layout );
		return neighborhood;
	}
}
