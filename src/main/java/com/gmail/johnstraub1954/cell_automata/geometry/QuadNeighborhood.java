package com.gmail.johnstraub1954.cell_automata.geometry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Calculate the neighborhood for a given offset position.
 * Currently only simple neighborhoods, 
 * including the tiles eight closest neighbors,
 * are supported.
 * In the future we should consider 
 * including so-called "extended" neighborhoods.
 * See 
 * <a href="https://conwaylife.com/wiki/Gallery_of_neighbourhoods">
 *      LifeWiki/Gallery of neighbourhoods."
 * </a>
 * @author Jack S.
 */
public class QuadNeighborhood implements Neighborhood, Serializable
{
    /**
     * Vectors to nearest neighbors in axial coordinates.
     */
    private static final Offset[]   vectors =
    {
        new Offset(  0, -1 ),   // north 
        new Offset(  1, -1),    // northeast
        new Offset(  1,  0 ),   // east
        new Offset(  1,  1 ),   // southeast
        new Offset(  0 , 1 ),   // south
        new Offset( -1, 1 ),    // southwest
        new Offset( -1, 0 ),    // west
        new Offset( -1, -1 ),   // northwest
    };
    /** The source cell for calculating the neighborhood. */
    private final Offset    	self;
    /** List of neighbors in offset coordinates. */
    private final List<Offset>	neighbors   = new ArrayList<>();
    
    /**
     * Constructor. 
     * Create a neighborhood object using a given base cell.
     * 
     * @param self      the base cell, about which
     *                  the neighborhood is to be constructed
     */
    public QuadNeighborhood( Offset self )
    {
        this.self = self;
        for ( Offset vector : vectors )
        {
            Offset  neighbor    = self.add( vector );
            neighbors.add( neighbor );
        }
    }
    
    /**
     * Gets the source cell for calculating the neighborhood.
     * 
     * @return the source cell for calculating the neighborhood
     */
    public Offset getSelf()
    {
        return self;
    }
    
    /**
     * Gets the list of neighbors of the source cell.
     */
    public List<Offset> getNeighbors()
    {
        return neighbors;
    }
    
    @Override
    public String toString()
    {
        StringBuilder   bldr    = new StringBuilder();
        bldr.append( self ).append( ("->{") );
        neighbors.forEach( n -> bldr.append( n ).append( "," ) );
        bldr.deleteCharAt( bldr.length() - 1 );
        bldr.append( "}" );
        return bldr.toString();
    }
    
    /**
     * Tests this object for equality with a given object.
     * The two object are equal 
     * if the given object is non-null;
     * and if the given object is a QuadNeighborhood;
     * and if the given object's list of neighbors
     * is equivalent to this object's list of neighbors
     * without regard to order.
     * 
     * @return true if this object is equal to the given object
     */
    @Override
    public boolean equals( Object obj )
    {
        boolean rcode   = false;
        if ( obj == null )
            rcode = false;
        else if ( obj == this )
            rcode = true;
        else if ( !(obj instanceof QuadNeighborhood ) )
            rcode = false;
        else
        {
            QuadNeighborhood that    = (QuadNeighborhood)obj;
            rcode = 
                this.self.equals( that.self )
                && this.neighbors.size() == that.neighbors.size()
                && this.neighbors.containsAll( that.neighbors )
                && that.neighbors.containsAll( this.neighbors );
        }
        return rcode;
    }
    
    /**
     * Returns a hash code for this object.
     * Required because equals is overridden.
     * 
     * @return a hash code for this object
     */
    @Override
    public int hashCode()
    {
        int hashCode    = Objects.hash( self, neighbors );
        return hashCode;
    }
}
