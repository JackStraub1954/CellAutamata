package test_util;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.List;

import com.gmail.johnstraub1954.cell_automata.geometry.Polygon;

/**
 * An instance of this class describes a path 
 * produced by a Polygon,
 * and the parameters used to generate the path.
 * It is used during testing, and test preparation.
 * 
 * @author java1
 *
 * @see app.PathDataCompiler
 */
public class PolygonDescriptor implements Serializable
{
	/** Generated serial version UID */
	private static final long serialVersionUID = 4468370367841976768L;
	
	/** Number of sides */
	public final int		numSides;
	/** Length of side */
	public final double		sideLen;
	/** Angle of first vertex (for computing Path2D and vertices) */
	public final double		angle;
	/** Center of polygon (for computing Path2D and vertices) */
	public final Point2D	center;
	/** 
	 * Verified Path computed from above data; 
	 * used to compare against path computed during testing.
	 * (Cannot be raw Path2D, because Path2D doesn't implement serializable.)
	 */
	public final Path2D.Double	expPath;
	/** 
	 * Verified list of vertices computed from above data; 
	 * used to compare against list computed during testing.
	 */
	public final List<Point2D>	expVertices;
	
	/**
	 * Constructor.
	 * Given a base Polygon, center and initial angle,
	 * record the data used to create the Polygon
	 * and generate the (presumably correct)
	 * path and list of vertices.
	 * This data will be used during testing
	 * to create an isomorphic Polygon
	 * and verify the new Polygon generates
	 * the same path and list of vertices.
	 * 
	 * @param poly     base polygon; presumed to generate valid data
	 * @param center   the center of the path calculated by the Polygon
	 * @param angle	   the initial angle of the path calculated by the Polygon
	 */
	public PolygonDescriptor( 
			Polygon poly,
			Point2D center, 
			double  angle 
    )
	{
		this.numSides = poly.getNumSides();
		this.sideLen = poly.getSideLen();
		this.center = center;
		this.angle = angle;
		
		Path2D    path    = poly.getPath( center, angle );
		expPath = new Path2D.Double( path );
		expVertices = poly.getVertices( center, angle );
	}
	
	   
    /**
     * Use Polygon.ofSide and stored data 
     * to create a polygon for testing.
     * 
     * @return  a Polygon constructed from stored data
     */
	public Polygon getPolygon()
	{
	    Polygon    poly    = Polygon.ofSide( numSides, sideLen );
	    return poly;
	}
	
	/**
	 * Given a Polygon,
	 * and the encapsulated center and initial angle data,
	 * generate an "actual" path to be compared against
	 * the encapsulated "expected" path.
	 * 
	 * @param polygon	the given Polygon
	 * 
	 * @return	an "actual" path to be compared against
	 * 			the encapsulated "expected" path
	 */
	public Path2D getActualPath( Polygon polygon )
	{
		Path2D path	= polygon.getPath( center, angle );
		return path;
	}
    
    /**
     * Given a Polygon,
     * and the encapsulated center and initial angle data,
     * generate an "actual" list of vertices to be compared against
     * the encapsulated "expected" list of vertices.
     * 
     * @param polygon   the given Polygon
     * 
     * @return  an "actual" path to be compared against
     *          the encapsulated "expected" path
     */
    public List<Point2D> getActualVertices( Polygon polygon )
    {
        List<Point2D>   list    = polygon.getVertices( center, angle );
        return list;
    }
	
	/**
	 * Given an "actual" path generated during testing,
	 * compare it to the encapsulated "expected" path
	 * generated during test preparation.
	 * 
	 * @param actPath	the path to compare to the encapsulated
	 * 					"expected" path
	 * @return	true if the given path is equal to
	 * 			the encapsulated "expected" path
	 */
	public boolean equals( Path2D actPath )
	{
		 boolean	result	= TestUtils.equals( actPath, expPath );
		 return result;
	}
    
    /**
     * Given an "actual" list of vertices generated during testing,
     * compare it to the encapsulated "expected" list of vertices
     * generated during test preparation.
     * 
     * @param actVertices   the "actual" list of vertices 
     *                      to compare to the encapsulated
     *                      "expected" list of vertices
     *                      
     * @return  true if the given list is equal to
     *          the encapsulated "expected" list
     */
    public boolean equals( List<Point2D> actVertices )
    {
         boolean    result  = expVertices.equals( actVertices );
         return result;
    }
	
	@Override
	public String toString()
	{
		double	xco			= center.getX();
		double  yco			= center.getY();
		String	fmt			=
			"center=(%.1f,%.1f),angle=%.1f,side=%.1f,#sides=%d";
		String	result		=
			String.format( fmt, xco, yco, angle, sideLen, numSides );
		return result;
	}
}
