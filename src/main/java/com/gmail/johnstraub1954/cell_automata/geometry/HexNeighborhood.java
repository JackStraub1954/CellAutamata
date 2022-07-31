package com.gmail.johnstraub1954.cell_automata.geometry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HexNeighborhood implements Neighborhood, Serializable
{
    /** Generated serial version UID */
    private static final long serialVersionUID = -4927121922330453827L;
    
    private static final Hex[]  vectors =
    {
        new Hex( 1, 0 ),
        new Hex( 1, -1 ),
        new Hex( 0, -1 ),
        new Hex( -1, 0 ),
        new Hex( -1, 1 ),
        new Hex( 0, 1 ),
    };
    private final Offset    	self;
    private final HexLayout     layout;
    private final List<Offset>	neighbors   = new ArrayList<>();
    
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
    
    public Offset getSelf()
    {
        return self;
    }
    
    public HexLayout getLayout()
    {
        return layout;
    }
    
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
