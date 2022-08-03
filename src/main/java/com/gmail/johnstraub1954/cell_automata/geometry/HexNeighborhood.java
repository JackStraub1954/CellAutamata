package com.gmail.johnstraub1954.cell_automata.geometry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Calculate the neighborhood for a given offset position.
 * Currently only simple neighborhoods, 
 * including the tiles six closest neighbors,
 * are supported.
 * In the future we should consider 
 * including so-called "extended" neighborhoods.
 * See 
 * <a href="https://conwaylife.com/wiki/Hexagonal_neighbourhood">
 *      LifeWiki/Hexagonal Neighbourhood."
 * </a>
 * @author johns
 */
public class HexNeighborhood implements Neighborhood, Serializable
{
    /** Generated serial version UID */
    private static final long serialVersionUID = -4927121922330453827L;
    
    /**
     * Vectors to nearest neighbors in axial coordinates.
     */
    private static final Hex[]  vectors =
    {
        new Hex( 1, 0 ),
        new Hex( 1, -1 ),
        new Hex( 0, -1 ),
        new Hex( -1, 0 ),
        new Hex( -1, 1 ),
        new Hex( 0, 1 ),
    };
    /** The source cell for calculating the neighborhood. */
    private final Offset    	self;
    /** The hex layout to use for calculating offsets. */
    private final HexLayout     layout;
    /** List of neighbors in offset coordinates. */
    private final List<Offset>	neighbors   = new ArrayList<>();
    
    /**
     * Constructor. 
     * Create a neighborhood object using a given base cell,
     * and the format to use to convert between axial and offset
     * coordinates.
     * 
     * @param self      the base cell, about which
     *                  the neighborhood is to be constructed
     * @param layout    the format to use to convert between 
     *                  axial and offset coordinates
     */
    public HexNeighborhood( Offset self, HexLayout layout )
    {
        this.self = self;
        this.layout = layout;
        Hex	hexSelf	= layout.toHex( self );
        for ( Hex vector : vectors )
        {
        	Hex	neighbor	= hexSelf.add( vector );
            neighbors.add( layout.toOffset( neighbor ) );
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
     * Gets the Hex layout used to calculate offsets.
     * 
     * @return  the Hex layout used to calculate offsets
     */
    public HexLayout getLayout()
    {
        return layout;
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
     * and if the given object is a HexNeighborhood;
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
        else if ( !(obj instanceof HexNeighborhood ) )
            rcode = false;
        else
        {
            HexNeighborhood that    = (HexNeighborhood)obj;
            rcode = 
                this.self.equals( that.self )
                && this.layout == that.layout
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
