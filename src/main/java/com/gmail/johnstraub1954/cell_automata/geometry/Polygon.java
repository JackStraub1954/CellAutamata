package com.gmail.johnstraub1954.cell_automata.geometry;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates a regular polygon.
 * 
 * @author johns
 *
 */
public class Polygon implements Serializable
{
    /** Generated serial version UID */
	private static final long serialVersionUID = -8155074369234285888L;
	/** The number of sides of the encapsulated polygon. */
	private final int       numSides;
    /** The radius of the encapsulated polygon. */
    private final double    radius;
    
    /**
     * Constructor.
     * 
     * @param numSides
     * @param radius
     */
    private Polygon( int numSides, double radius )
    {
        this.numSides = numSides;
        this.radius = radius;
    }
    
    /**
     * Creates a Polygon based on the number of sides,
     * and the length of a side
     * 
     * @param numSides  the given number of sides
     * @param sideLen   the length of a side
     * 
     * @return  a Polygon, based on the number of sides
     *          and length of a side
     */
    public static Polygon ofSide( int numSides, double sideLen )
    {
        double  angle   = Math.PI / numSides;
        double  radius  = sideLen / (2 * Math.sin( angle ));
        Polygon polygon = new Polygon( numSides, radius );
        return polygon;
    }
    
    /**
     * Creates a Polygon based on the number of sides,
     * and the length of the radius
     * 
     * @param numSides  the given number of sides
     * @param radius    the length of the radius
     * 
     * @return  a Polygon, based on the number of sides
     *          and length of the radius
     */
    public static Polygon ofRadius( int numSides, double radius )
    {
        Polygon polygon = new Polygon( numSides, radius );
        return polygon;
    }
    
    /**
     * Creates a Polygon based on the number of sides,
     * and the length of the apothem (inradius)
     * 
     * @param numSides  the given number of sides
     * @param apothem   the length of the apothem
     * 
     * @return  a Polygon, based on the number of sides
     *          and length of the apothem
     */
    public static Polygon ofApothem( int numSides, double apothem )
    {
        double  angle   = Math.PI / numSides;
        double  radius  = apothem / Math.cos( angle );
        Polygon polygon = new Polygon( numSides, radius );
        return polygon;
    }
    
    /**
     * Returns the number of sides of this polygon.
     * 
     * @return  the number of sides of this polygon
     */
    public int getNumSides()
    {
        return numSides;
    }
    
    /**
     * Returns the radius of this polygon.
     * 
     * @return  the radius of this polygon
     */
    public double getRadius()
    {
        return radius;
    }
    
    /**
     * Returns the length of a side of this polygon.
     * 
     * @return  the length of a side of this polygon
     */
    public double getSideLen()
    {
        double  angle   = Math.PI / numSides;
        double  len     = 2 * radius * Math.sin( angle );
        return len;
    }
    
    /**
     * Returns the apothem of this polygon.
     * 
     * @return  the apothem of this polygon
     */
    public double getApothem()
    {
        double  angle   = Math.PI / numSides;
        double  apothem = radius * Math.cos( angle );
        return apothem;
    }
    
    /**
     * Returns the size of an interior angle of this polygon,
     * in radians.
     * 
     * @return  the size of an interior angle  of this polygon
     */
    public double getIntAngle()
    {
        double  angle   = ((numSides - 2) * Math.PI) / numSides;
        return angle;
    }
    
    /**
     * Returns the size of an exterior angle of this polygon,
     * in radians.
     * 
     * @return  the size of an exterior angle of this polygon
     */
    public double getExtAngle()
    {
        double  angle   = (2 * Math.PI) / numSides;
        return angle;
    }
    
    /**
     * Returns the area of this polygon,.
     * 
     * @return  the area  of this polygon
     */
    public double getArea()
    {
        double  apothem         = getApothem();
        double  sideLen         = getSideLen();
        double  area            = (numSides * sideLen * apothem) / 2;
        return area;
    }
    
    /**
     * Returns the perimeter of this polygon,.
     * 
     * @return  the perimeter  of this polygon
     */
    public double getPerimeter()
    {
        double  sideLen     = getSideLen();
        double  perimeter   = numSides * sideLen;
        return perimeter;
    }
    
    /**
     * Given a center and starting angle,
     * returns a list of the vertices of this polygon.
     * 
     * @param center        the given center of this polygon
     * @param startAngle    the starting angle of this polygon
     * 
     * @return  a list of vertices for this polygon
     */
    public List<Point2D> getVertices( Point2D center, double startAngle )
    {
        double          xco     = center.getX();
        double          yco     = center.getY();
        List<Point2D>   list    = getVertices( xco, yco, startAngle );
        return list;
    }
    
    /**
     * Given a center and starting angle,
     * returns a list of the vertices of this polygon.
     * 
     * @param centerX       the x-coordinate of the center of this polygon
     * @param centerY       the y-coordinate of the center of this polygon
     * @param startAngle    the starting angle of this polygon
     * 
     * @return  a list of vertices for this polygon
     */
    public List<Point2D> 
    getVertices( double centerX, double centerY, double startAngle )
    {
        List<Point2D>   list        = new ArrayList<>();
        double          angleIncr   = getExtAngle();
        double          angle       = startAngle;
        for ( int inx = 0 ; inx < numSides ; ++inx )
        {
            double  xco     = centerX + radius * Math.cos( angle );
            double  yco     = centerY + radius * Math.sin( angle );
            Point2D vertex  = new Point2D.Double( xco, yco );
            list.add( vertex );
            angle += angleIncr;
        }
        
        return list;
    }
    
    /**
     * Given a center and starting angle,
     * returns a list a path suitable for drawing this polygon.
     * 
     * @param center        the given center of this polygon
     * @param startAngle    the starting angle of this polygon
     * 
     * @return  a list of vertices for this polygon
     */
    public Path2D getPath(Point2D center, double startAngle )
    {
        Path2D  path    = getPath( center.getX(), center.getY(), startAngle );
        return path;
    }
    
    /**
     * Given a center and starting angle,
     * returns a list a path suitable for drawing this polygon.
     * 
     * @param centerX       the x-coordinate of the center of this polygon
     * @param centerY       the y-coordinate of the center of this polygon
     * @param startAngle    the starting angle of this polygon
     * 
     * @return  a list of vertices for this polygon
     */
    public Path2D 
    getPath( double centerX, double centerY, double startAngle )
    {
        Path2D          path        = new Path2D.Double();
        List<Point2D>   vertices    = 
            getVertices( centerX, centerY, startAngle );
        
        Point2D         vertex      = vertices.get( 0 );
        path.moveTo( vertex.getX(),  vertex.getY() );
        for ( int inx = 0 ; inx < numSides ; ++inx )
        {
            vertex = vertices.get( inx );
            path.lineTo( vertex.getX(),  vertex.getY() );
        }
        path.closePath();
        
        return path;
    }
}
