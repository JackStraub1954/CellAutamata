package com.gmail.johnstraub1954.cell_automata.main;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Polygon
{
    private final int       numSides;
    private final double    radius;
    
    private Polygon( int numSides, double radius )
    {
        this.numSides = numSides;
        this.radius = radius;
    }
    
    public static Polygon ofSide( int numSides, double sideLen )
    {
        double  angle   = Math.PI / numSides;
        double  radius  = sideLen / (2 * Math.sin( angle ));
        Polygon polygon = new Polygon( numSides, radius );
        return polygon;
    }
    
    public static Polygon ofRadius( int numSides, double radius )
    {
        Polygon polygon = new Polygon( numSides, radius );
        return polygon;
    }
    
    public static Polygon ofApothem( int numSides, double apothem )
    {
        double  angle   = Math.PI / numSides;
        double  radius  = apothem / Math.cos( angle );
        Polygon polygon = new Polygon( numSides, radius );
        return polygon;
    }
    
    public int getNumSides()
    {
        return numSides;
    }
    
    public double getRadius()
    {
        return radius;
    }
    
    public double getSideLen()
    {
        double  angle   = Math.PI / numSides;
        double  len     = 2 * radius * Math.sin( angle );
        return len;
    }
    
    public double getApothem()
    {
        double  angle   = Math.PI / numSides;
        double  apothem = radius * Math.cos( angle );
        return apothem;
    }
    
    public double getIntAngle()
    {
        double  angle   = ((numSides - 2) * Math.PI) / numSides;
        return angle;
    }
    
    public double getExtAngle()
    {
        double  angle   = (2 * Math.PI) / numSides;
        return angle;
    }
    
    public double getArea()
    {
        double  apothem         = getApothem();
        double  sideLen         = getSideLen();
        double  area            = (numSides * sideLen * apothem) / 2;
        return area;
    }
    
    public double getPerimeter()
    {
        double  sideLen     = getSideLen();
        double  perimeter   = numSides * sideLen;
        return perimeter;
    }
    
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
