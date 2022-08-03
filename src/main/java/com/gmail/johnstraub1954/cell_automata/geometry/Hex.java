package com.gmail.johnstraub1954.cell_automata.geometry;

import java.io.Serializable;
import java.util.Objects;

/**
 * Given an infinite plane tiled with regular hexagons, 
 * an instance of this class encapsulates an axial-coordinate pair
 * for determining the position of any hexagon in the plane.
 * For a discussion of axial coordinated as applied to hexagons
 * see the attribution reference, below.
 * <p>
 * This class also contains algorithms for converting between
 * axial and other coordinate systems, including column/row
 * coordinates and screen coordinates.
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
 */
/**
 * @author johns
 *
 */
public class Hex implements Serializable
{
    /** Generated serial version UID */
    private static final long serialVersionUID = -6112248107288672583L;
    
    /** The q component of an axial coordinate pair. */
    public final int    qco;
    /** The r component of an axial coordinate pair. */
    public final int    rco;
    
    /**
     * Constructor.
     * 
     * @param qco   q component of the encapsulated axial coordinate pair
     * @param rco   r component of the encapsulated axial coordinate pair
     */
    public Hex( int qco, int rco )
    {
        this.qco = qco;
        this.rco = rco;
    }
    
    /**
     * Copy constructor.
     * 
     * @param hex   the coordinate pair to copy
     */
    public Hex( Hex hex )
    {
        this( hex.qco, hex.rco );
    }
    
    /**
     * Returns a description of this object as a printable string.
     */
    @Override
    public String toString()
    {
        StringBuilder   bldr    = new StringBuilder();
        bldr.append( "qco=" ).append( qco )
            .append( ",rco=" ).append( rco );
        String  str = String.format( "qco=%d,rco=%d", qco, rco );
        return str;
    }
    
    /**
     * Returns the q component of this coordinate pair.
     * 
     * @return  the q component of this coordinate pair
     */
    public int getQco()
    {
        return qco;
    }
    
    /**
     * Returns the r component of this coordinate pair.
     * 
     * @return  the r component of this coordinate pair
     */
    public int getRco()
    {
        return rco;
    }
    
    /**
     * Returns the s component needed to convert this axial pair
     * to a cubic coordinated triplet, in which q + r + s = 0.
     * 
     * @return  the s coordinate associated with this axial pair
     */
    public int getSco()
    {
        int sco = -qco - rco;
        return sco;
    }
    
    /**
     * Add a given hex value to this hex value
     * producing a new Hex object containing the sum.
     * 
     * @param addend    the given Hex value
     * @return  the sum of this object and the given object
     */
    public Hex add( Hex addend )
    {
        int sumQco  = qco + addend.qco;
        int sumRco  = rco + addend.rco;
        Hex sum     = new Hex( sumQco, sumRco);
        return sum;
    }
    
    
    
    /**
     * Converts a given axial coordinate pair to an offset pair
     * in the odd-r column/row coordinate system.
     * 
     * @param hex   the given axial coordinated pair
     * 
     * @return  the calculated offset coordinate pair
     */
    public static Offset axialToOddR( Hex hex )
    {
        Offset  offset  = axialToOddR( hex.qco, hex.rco );
        return offset;
    }
    
    /**
     * Converts a given axial coordinate pair to an offset pair
     * in the odd-r column/row coordinate system.
     * 
     * @param qco   the q component of the given axial coordinate pair
     * @param rco   the r component of the given axial coordinate pair
     * 
     * @return  the calculated offset coordinate pair
     */
    public static Offset axialToOddR( int qco, int rco )
    {
        int     col     = qco + (rco - (rco & 1)) / 2;
        int     row     = rco;
        Offset  offset   = new Offset( col, row );
        return offset;
    }
    
    /**
     * Converts a given axial coordinate pair to an offset pair
     * in the odd-q column/row coordinate system.
     * 
     * @param hex   the given axial coordinate pair
     * 
     * @return  the calculated offset coordinate pair
     */
    public static Offset axialToOddQ( Hex hex )
    {
        Offset  offset  = axialToOddQ( hex.qco, hex.rco );
        return offset;
    }
    
    /**
     * Converts a given axial coordinate pair to an offset pair
     * in the odd-q column/row coordinate system.
     * 
     * @param qco   the q component of the given axial coordinate pair
     * @param rco   the r component of the given axial coordinate pair
     * 
     * @return  the calculated offset coordinate pair
     */
    public static Offset axialToOddQ( int qco, int rco )
    {
        int     col     = qco;
        int     row     = rco + (qco -(qco & 1)) / 2;
        Offset  offset  = new Offset( col, row );
        return offset;
    }
    
    /**
     * Converts a given axial coordinate pair to an offset pair
     * in the even-q column/row coordinate system.
     * 
     * @param hex   the given axial coordinate pair
     * 
     * @return  the calculated offset coordinate pair
     */
    public static Offset axialToEvenQ( Hex hex )
    {
        Offset  offset  = axialToEvenQ( hex.qco, hex.rco );
        return offset;
    }
    
    /**
     * Converts a given axial coordinate pair to an offset pair
     * in the even-q column/row coordinate system.
     * 
     * @param qco   the q component of the given axial coordinate pair
     * @param rco   the r component of the given axial coordinate pair
     * 
     * @return  the calculated offset coordinate pair
     */
    public static Offset axialToEvenQ( int qco, int rco )
    {
        int     col     = qco;
        int     row     = rco + (qco + (qco & 1)) / 2;
        Offset  offset  = new Offset( col, row );
        return offset;
    }
    
    /**
     * Converts a given axial coordinate pair to an offset pair
     * in the even-r column/row coordinate system.
     * 
     * @param hex   the given axial coordinate pair
     * 
     * @return  the calculated offset coordinate pair
     */
    public static Offset axialToEvenR( Hex hex )
    {
        Offset  offset  = axialToEvenR( hex.qco, hex.rco );
        return offset;
    }
    
    /**
     * Converts a given axial coordinate pair to an offset pair
     * in the even-r column/row coordinate system.
     * 
     * @param qco   the q component of the given axial coordinate pair
     * @param rco   the r component of the given axial coordinate pair
     * 
     * @return  the calculated offset coordinate pair
     */
    public static Offset axialToEvenR( int qco, int rco )
    {
        int     col     = qco + (rco + (rco&1)) / 2;;
        int     row     = rco;
        Offset  offset  = new Offset( col, row );
        return offset;
    }
    
    /**
     * Converts a given coordinate pair in the odd-q offset
     * coordinate system, to a coordinate pair in the 
     * axial coordinate system.
     * 
     * @param from  the given offset coordinate pair
     * 
     * @return  the corresponding axial coordinate pair
     */
    public static Hex oddQToAxial( Offset from )
    {
        Hex hex = oddQToAxial( from.col, from.row );
        return hex;
    }
    
    /**
     * Converts a given coordinate pair in the odd-q offset
     * coordinate system, to a coordinate pair in the 
     * axial coordinate system.
     * 
     * @param col   the column component of the offset pair
     * @param row   the row component of the offset pair
     * 
     * @return  the corresponding axial coordinate pair
     */
    public static Hex oddQToAxial( int col, int row )
    {
        int     qco     = col;
        int     rco     = row - (col - (col & 1 )) / 2;
        Hex     hex     = new Hex( qco, rco );
        return hex;
    }
    
    /**
     * Converts a given coordinate pair in the even-q offset
     * coordinate system, to a coordinate pair in the 
     * axial coordinate system.
     * 
     * @param from  the given offset coordinate pair
     * 
     * @return  the corresponding axial coordinate pair
     */
    public static Hex evenQToAxial( Offset from )
    {
        Hex hex = evenQToAxial( from.col, from.row );
        return hex;
    }
    
    /**
     * Converts a given coordinate pair in the even-q offset
     * coordinate system, to a coordinate pair in the 
     * axial coordinate system.
     * 
     * @param col   the column component of the offset pair
     * @param row   the row component of the offset pair
     * 
     * @return  the corresponding axial coordinate pair
     */
    public static Hex evenQToAxial( int col, int row )
    {
        int     qco     = col;
        int     rco     = row - (col + (col & 1 )) / 2;
        Hex     hex     = new Hex( qco, rco );
        return hex;
    }
    
    /**
     * Converts a given coordinate pair in the even-r offset
     * coordinate system, to a coordinate pair in the 
     * axial coordinate system.
     * 
     * @param from  the given offset coordinate pair
     * 
     * @return  the corresponding axial coordinate pair
     */
    public static Hex evenRToAxial( Offset from )
    {
        Hex hex = evenRToAxial( from.col, from.row );
        return hex;
    }
    
    /**
     * Converts a given coordinate pair in the even-r offset
     * coordinate system, to a coordinate pair in the 
     * axial coordinate system.
     * 
     * @param col   the column component of the offset pair
     * @param row   the row component of the offset pair
     * 
     * @return  the corresponding axial coordinate pair
     */
    public static Hex evenRToAxial( int col, int row )
    {
        int     qco     = col - (row + (row & 1)) / 2;
        int     rco     = row;
        Hex     hex     = new Hex( qco, rco );
        return hex;
    }
    
    /**
     * Converts a given coordinate pair in the odd-r offset
     * coordinate system, to a coordinate pair in the 
     * axial coordinate system.
     * 
     * @param from  the given offset coordinate pair
     * 
     * @return  the corresponding axial coordinate pair
     */
    public static Hex oddRToAxial( Offset from )
    {
        Hex hex = oddRToAxial( from.col, from.row );
        return hex;
    }
    
    /**
     * Converts a given coordinate pair in the odd-r offset
     * coordinate system, to a coordinate pair in the 
     * axial coordinate system.
     * 
     * @param col   the column component of the offset pair
     * @param row   the row component of the offset pair
     * 
     * @return  the corresponding axial coordinate pair
     */
    public static Hex oddRToAxial( int col, int row )
    {
        int     qco     = col - (row - (row & 1)) / 2;
        int     rco     = row;
        Hex     hex     = new Hex( qco, rco );
        return hex;
    }
    
    /**
     * Tests this Hex object against another object
     * and returns true if the two objects are equal.
     * The other object is equal to this object
     * if it is not null, is an instance of the Hex class,
     * and its q and r components are equal to those 
     * of this object.
     */
    @Override
    public boolean equals( Object obj )
    {
        boolean result  = false;
        if ( obj == null )
            ;
        if ( obj instanceof Hex )
        {
            Hex that    = (Hex)obj;
            result = this.qco == that.qco && this.rco == that.rco;
        }
        return result;
    }
    
    /**
     *  Returns a hash code for this Hex object.
     *  This method is required because equals is overridden.
     */
    @Override
    public int hashCode()
    {
        int hash    = Objects.hash( qco, rco );
        return hash;
    }
}
