package com.gmail.johnstraub1954.cell_automata.geometry;

/**
 * Represents axial coordinates as fractional values.
 * This is required, for example,
 * when mapping a mouse-click to an axial coordinate position.
 * 
 * @author Jack Straub
 *
 */
public class FractionalHex
{
    /** Axial q-coordinate, stored as a double */
    public final double dQco;
    /** Axial r-coordinate, stored as a double */
    public final double dRco;
    
    /**
     * Constructor.
     * 
     * @param qco   q-coordinate of this object
     * @param rco   r-coordinate of this object.
     */
    public FractionalHex( double qco, double rco )
    {
        this.dQco = qco;
        this.dRco = rco;
    }
    
    /**
     * Rounds a fractional axial coordinate
     * to an integral axial coordinate.
     * 
     * @see <a href="https://www.redblobgames.com/grids/hexagons/#rounding">
     *          Rounding to nearest hex
     *      </a> at Red Blob Games
     *      
     * @return  a Hex value rounded from this FractionalHex value
     */
    public Hex round()
    {
        double  dSco    = -dQco - dRco;
        int     qco     = (int)(Math.round( dQco ));
        int     rco     = (int)(Math.round( dRco ));
        int     sco     = (int)(Math.round( dSco ));
        
        double  qDiff   = Math.abs( qco - dQco );
        double  rDiff   = Math.abs( rco - dRco );
        double  sDiff   = Math.abs( sco - dSco );
        
        // TODO improve rounding algorithm
        // This code needs to be improved to eliminate impossible cases,
        // such as (qDiff >rDiff) && !(qDiff > sDiff) && (rDiff > sDiff).
        // I don't care about efficiency or elegance, but I do care about
        // test coverage.
        if ( qDiff > rDiff && qDiff > sDiff )
            qco = -rco - sco;
        else if ( rDiff > sDiff )
            rco = -qco - sco;
        else
            sco = -qco - rco;
        
        Hex hex = new Hex( qco, rco );
        return hex;
    }
}
