package com.gmail.johnstraub1954.cell_automata.geometry;

import java.awt.Dimension;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

import com.gmail.johnstraub1954.cell_automata.main.CAException;

public class QuadTile implements GridTile
{
    /** 
     * The prefix for any error message created by
     * the <em>ofValues</em> method.
     * @see #ofValues(String...)
     */
    private static final String     ofValuesErrorPrefix     =
        "QuadLayout.ofValues error: ";
    /**
     * The default value for the length of the side
     * of the QuadTile created by 
     * the <em>ofValues</em> method.
     * @see #ofValues(String...)
     */
    private static final double     ofValuesSideDefault     = 10;
    /** 
     * Reference polygon for generating a path for a specific square.
     * Generating the path requires specifying the center of the square.
     * 
     * @see Polygon#getPath(double, double, double)
     */
    private final Polygon   square;

    private QuadTile( double side )
    {
        square = Polygon.ofSide( 4, side );
    }
    
    /**
     * Creates a QuadTile based on the length of a side
     * of the constituent square.
     * 
     * @param side  length of the side of the constituent square
     * 
     * @return QuadTile with the given side
     */
    public static QuadTile ofSide( double side )
    {
        QuadTile    tile    = new QuadTile( side );
        return tile;
    }
    
    /**
     * Get a path to draw this square at the given coordinates.
     * 
     * @param point   the given coordinates
     * 
     * @return  a path to draw this square at the given coordinates
     */
    public Path2D getPath( Point2D point )
    {
        Path2D  path    = getPath( point.getX(), point.getY() );
        return path;
    }
    /**
     * Get a path to draw this square at the given coordinates.
     * 
     * @param xco   the given x-coordinate
     * @param yco   the given y-coordinate
     * 
     * @return  a path to draw this square at the given coordinates
     */
    public Path2D getPath( double xco, double yco )
    {
        // angle to compute the upper-left vertex of a square
        // with sides parallel to the x- and y-axes.
        final double    angle       = -Math.PI * 3. / 4.;
        Path2D          polyPath    = square.getPath( xco, yco, angle );
        
        return polyPath;
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
        double  side    = getSideLen();
        
        int         cols    = (int)Math.ceil( size.width / side );
        int         rows    = (int)Math.ceil( size.height / side );
        Dimension   dimOut  = new Dimension( cols, rows );
        
        return dimOut;
    }
    
    /**
     * Based on pixel coordinates, find the offset coordinates
     * of the selected square.
     * Coordinates are typically determined by mouse selection.
     * 
     * @param xco   x pixel coordinate
     * @param yco   y pixel coordinate
     * 
     * @return  the offset coordinates
     *          of the selected square
     */
    public Offset getSelected( int xco, int yco )
    {
        double  side    = getSideLen();
        // pixel coordinates; adjusted because top row/left column
        // are off by half the square.
        double  adjXco  = xco - side / 2;
        double  adjYco  = yco - side / 2;
        int     col     = (int)(adjXco / side + .5);
        int     row     = (int)(adjYco / side + .5);
        Offset  offset  = new Offset( col, row );
        
        return offset;
    }
    
    public Point2D toPixelCoords( Offset offset )
    {
        double  side    = getSideLen();
        double  xco     = side * offset.col + side / 2;
        double  yco     = side * offset.row + side / 2;
        Point2D center  = new Point2D.Double( xco, yco );
        return center;
    }
    
    public Point2D toPixelCoords( int col, int row )
    {
        double  side    = getSideLen();
        double  xco     = side * col + side / 2;
        double  yco     = side * row + side / 2;
        Point2D center  = new Point2D.Double( xco, yco );
        return center;
    }
    
    /**
     * Get a path to draw this square at the given offset coordinates.
     * 
     * @param offset    the given coordinates
     * 
     * @return  a path to draw this square at the given offset coordinates
     */
    public Path2D getPath( Offset offset )
    {
        Point2D center  = toPixelCoords( offset );
        Path2D  path    = square.getPath( center, Math.PI * 3. / 4. );
        return path;
    }

    /**
     * Gets the length of a side of this square.
     * 
     * @return  the length of a side of this square.
     */
    public double getSideLen()
    {
        return square.getSideLen();
    }

    @Override
    public Neighborhood getNeighborhood(Offset self)
    {
        Neighborhood    neighborhood    = new QuadNeighborhood( self );
        return neighborhood;
    }
    
    /**
     * Construct a QuadTile using a sequence of String arguments.
     * The sequence may consist of 0 or 1 arguments,
     * length of side of the tile.
     * If the side argument is present
     * it is expected to be a value decimal value.
     * If there are 0 arguments
     * the length of the side will default.
     * 
     * @return  a QuadTile constructed from the given arguments
     * 
     * @throws  CAException if the list of values is invalid
     * 
     * @see #ofValuesSideDefault
     */
    @Override
    public QuadTile ofValues( String...strings )
    {
        StringBuilder   error   = new StringBuilder( ofValuesErrorPrefix );
        int             argc    = strings.length;
        double          side    = ofValuesSideDefault;
        if ( argc > 1 )
        {
            error.append( "invalid number of arguments (" )
                 .append( argc )
                 .append( ")" );
            throw new CAException( error.toString() );
        }
        if ( argc > 0 )
        {
            try
            {
                side = Double.parseDouble( strings[0] );
            }
            catch ( NumberFormatException exc )
            {
                error.append( "\"" )
                     .append( strings[0] )
                     .append( "\" is an invalid decimal value" );
                throw new CAException( error.toString() );
            }
        }
        
        QuadTile    tile    = ofSide( side );
        return tile;
    }
}
