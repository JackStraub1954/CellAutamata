package com.gmail.johnstraub1954.cell_automata.geometry;

import java.awt.geom.Point2D;
import java.util.function.DoubleFunction;
import java.util.function.Function;

import Jama.Matrix;

/**
 * Enumerates the four strategies for implementing offset coordinates
 * for hexagons. These are:
 * <p>
 * For horizontal strategies:
 * <pre>
 *    /\
 *   |  |
 *    \/</pre>
 * ODD_R (odd rows pushed right) and
 * EVEN_R (even rows pushed right);
 * For vertical strategies:
 * <pre>
 *    __
 *   /  \
 *   \__/
 * </pre>
 * ODD_Q (odd columns pushed down) and
 * EVEN_Q (even columns pushed down).
 * <p>
 * Each strategy implements algorithms
 * for the following functionality:
 * <ul>
 * <li>
 *      Get the matrix multiplier
 *      for converting hex (axial) coordinates 
 *      to pixel (screen) coordinates;
 * </li>
 * <li>
 *      Get the matrix multiplier
 *      for converting pixel (screen) coordinates
 *      to hex (axial) coordinates;
 * </li>
 * <li>Get the angle that defines the horizontal/vertical orientation;</li>
 * <li>Convert hex (axial) coordinates to offset coordinates; and</li>
 * <li>Convert hex (axial) coordinates to pixel coordinates; and</li>
 * <li>Convert offset coordinates to hex (axial) coordinates.</li>
 * </ul>
 *     
 * @author Jack Straub
 *
 */
public enum HexLayout
{
    /** Odd-r, horizontal strategy; odd rows pushed right */
    ODD_R( 
        side -> getHexToPixelXierH( side ), 
        side -> getPixelToHexXierH( side ),
        Math.PI / 2, 
        h -> Hex.axialToOddR( h ),
        o -> Hex.oddRToAxial( o )
    ),
    /** Even-r, horizontal strategy; even rows pushed right */
    EVEN_R( 
        side -> getHexToPixelXierH( side ), 
        side -> getPixelToHexXierH( side ),
        Math.PI / 2,
        h -> Hex.axialToEvenR( h ),
        o -> Hex.evenRToAxial( o )
    ),
    /** Odd-q, vertical strategy; odd columns pushed down */
    ODD_Q( side -> getHexToPixelXierV( side ),
        side -> getPixelToHexXierV( side ),
        Math.PI,
        h -> Hex.axialToOddQ( h ),
        o -> Hex.oddQToAxial( o )
    ),
    /** Even-q, vertical strategy; even columns pushed down */
    EVEN_Q( side -> getHexToPixelXierV( side ), 
        side -> getPixelToHexXierV( side ),
        Math.PI,
        h -> Hex.axialToEvenQ( h ),
        o -> Hex.evenQToAxial( o )
    );
    
    private final DoubleFunction<Matrix>    getToPixelXierMatrix;
    private final DoubleFunction<Matrix>    getToHexXierMatrix;
    private final Function<Hex,Offset>      toOffset;
    private final Function<Offset,Hex>      toHex;
    private final double                    startAngle;
    
    /**
     * Constructor.
     * 
     * @param toPixelXier   strategy for obtaining a multiplier matrix
     *                      for converting axial coordinates
     *                      to pixel coordinates
     * @param toHexXier     strategy for obtaining a multiplier matrix
     *                      for converting pixel coordinates
     *                      to axial coordinates
     * @param startAngle    the start angle that defines the
     *                      horizontal/vertical orientation of a hexagon
     * @param toOffset      strategy for converting axial coordinates
     *                      to offset coordinates
     * @param toHex         strategy for converting offset coordinates
     *                      to axial coordinates
     */
    private HexLayout( 
        DoubleFunction<Matrix> toPixelXier, 
        DoubleFunction<Matrix> toHexXier, 
        double startAngle,
        Function<Hex,Offset> toOffset,
        Function<Offset,Hex> toHex
    )
    {
        getToPixelXierMatrix = toPixelXier;
        getToHexXierMatrix = toHexXier;
        this.startAngle = startAngle;
        this.toOffset = toOffset;
        this.toHex = toHex;
    }
    
    /**
     * Convert given offset coordinates to axial coordinates.
     * 
     * @param offset    the give offset coordinates
     * 
     * @return  the calculated axial coordinates
     */
    public Hex toHex( Offset offset )
    {
        Hex hex = toHex.apply( offset );
        return hex;
    }
    
    /**
     * Convert given axial coordinates to offset coordinates.
     * 
     * @param hex   the give axial coordinates
     * 
     * @return  the calculated offset coordinates
     */
    public Offset toOffset( Hex hex )
    {
        Offset offset   = toOffset.apply( hex );
        return offset;
    }
    
    /**
     * Obtain a multiplier matrix for converting axial coordinates
     * to pixel coordinates.
     * 
     * @param side  the length of the side of the target hexagon
     * 
     * @return  a multiplier matrix for converting axial coordinates
     *          to pixel coordinates
     */
    public Matrix getHexToPixelMultiplier( double side )
    {
        Matrix  matrix  = getToPixelXierMatrix.apply( side );
        return matrix;
    }
    
    /**
     * Obtain a multiplier matrix for converting pixel coordinates
     * to axial coordinates.
     * 
     * @param side  the length of the side of the target hexagon
     * 
     * @return  a multiplier matrix for converting pixel coordinates
     *          to axial coordinates
     */
    public Matrix getPixelToHexMultiplier( double side )
    {
        Matrix  matrix  = getToHexXierMatrix.apply( side );
        return matrix;
    }
    
    /**
     * Get the start angle for generating a hexagon
     * with this strategy's horizontal/vertical orientation.
     * 
     * @return  start angle for generating a hexagon
     *          with this strategy's orientation
     */
    public double getStartAngle()
    {
        return startAngle;
    }
    
    /**
     * Converts given axial coordinates to pixel coordinates
     * for a hexagon.
     * 
     * @param hex   the given axial coordinates
     * @param side  the length of the side of the target hexagon
     * 
     * @return  pixel coordinates for a hexagon
     *          with the given axial coordinates
     */
    public Point2D cvtHexToPixel( Hex hex, double side )
    {
        int         qco     = hex.qco;
        int         rco     = hex.rco;
        double[]    qrPair  = { qco, rco };
        Matrix      qrco    = new Matrix( qrPair, 2 );
        Matrix      xier    = getHexToPixelMultiplier( side );
        Matrix      xyco    = xier.times( qrco );
        double      xco     = xyco.get(0,  0 );
        double      yco     = xyco.get(1,  0 );
        Point2D     point   = new Point2D.Double( xco, yco );
        return point;
    }
    
    /**
     * Compute the matrix to use
     * to convert screen coordinates to axial coordinates
     * for a horizontal hexagon
     * with a given side length.
     * 
     * @param side  the given side length
     * 
     * @return  a matrix for converting screen coordinates
     *          to axial coordinates
     *          for a horizontal hexagon
     */
    private static Matrix getHexToPixelXierH( double side )
    {
        final double[][]    xierCons    =
        {
            { Math.sqrt( 3 ), Math.sqrt(3)/2 },
            { 0.,             3./2.          }
        };
        Matrix  consMatrix  = new Matrix( xierCons );
        Matrix  xierMatrix  = consMatrix.times( side );
        
        return xierMatrix;
    }
    
    
    /**
     * Compute the matrix to use
     * to convert screen coordinates to axial coordinates
     * for a vertical hexagon
     * with a given side length.
     * 
     * @param side  the given side length
     * 
     * @return  a matrix for converting screen coordinates
     *          to axial coordinates
     *          for a vertical hexagon
     */
    private static Matrix getHexToPixelXierV( double side )
    {
        final double[][]    xierCons    =
        {
            { 3./2.,              0          },
            { Math.sqrt( 3 ) / 2, Math.sqrt(3) },
        };
        Matrix  consMatrix  = new Matrix( xierCons );
        Matrix  xierMatrix  = consMatrix.times( side );
        
        return xierMatrix;
    }
    
    /**
     * Compute the matrix to use
     * to convert axial coordinates to screen coordinates
     * for a horizontal hexagon
     * with a given side length.
     * 
     * @param side  the given side length
     * 
     * @return  a matrix for converting axial coordinates
     *          to screen coordinates
     *          for a horizontal hexagon
     */
    private static Matrix getPixelToHexXierH( double side )
    {
        final double[][]    xierCons    =
        {
            { Math.sqrt( 3 )/3, -1./3. },
            { 0.,                2./3. }
        };
        Matrix  consMatrix  = new Matrix( xierCons );
        Matrix  xierMatrix  = consMatrix.times( 1. / side );
        
        return xierMatrix;
    }
    
    /**
     * Compute the matrix to use
     * to convert axial coordinates to screen coordinates
     * for a vertical hexagon
     * with a given side length.
     * 
     * @param side  the given side length
     * 
     * @return  a matrix for converting axial coordinates
     *          to screen coordinates
     *          for a vertical hexagon
     */
    private static Matrix getPixelToHexXierV( double side )
    {
        final double[][]    xierCons    =
        {
            { 2./3.,                    0 },
            { -1./3.,  Math.sqrt( 3 ) / 3 }
        };
        Matrix  consMatrix  = new Matrix( xierCons );
        Matrix  xierMatrix  = consMatrix.times( 1. / side );
        
        return xierMatrix;
    }
}
