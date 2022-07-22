package com.gmail.johnstraub1954.cell_automata.geometry;

import java.util.ArrayList;
import java.util.List;

public class HexNeighborhood implements Neighborhood
{
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
    private final List<Offset>	neighbors   = new ArrayList<>();
    
    public HexNeighborhood( Offset self, HexLayout layout )
    {
        this.self = self;
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
    
    public List<Offset> getNeighbors()
    {
        return neighbors;
    }
}
