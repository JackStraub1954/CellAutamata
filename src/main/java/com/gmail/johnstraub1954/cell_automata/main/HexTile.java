/**
 * 
 */
package com.gmail.johnstraub1954.cell_automata.main;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Path2D;

/**
 * @author Jack Straub
 *
 */
public class HexTile
{
    private static final double  hexAngle    = (double)(2 * Math.PI / 6);
    
    private final double     apothem;
    private final Path2D    path;
    private final double     side;
    private final double     yTranslate;
    private final double     xTranslate;
    private final double     radius;
    
    public HexTile( double radius )
    {
        this.radius = radius;
        
        path = new Path2D.Float();
        apothem = (double)(radius * Math.cos( Math.PI / 6 )); 
        side = (double)(2 * radius * Math.sin( Math.PI / 6 ));
        
        xTranslate = 2 * apothem;
        yTranslate = (double)(2 * side - side * Math.cos( hexAngle ) );

        double   start   = (double)(Math.PI / 2);
        double   centerX = radius;
        double   centerY = radius;
        double   xco     = centerX + radius * (double)Math.cos( start );
        double   yco     = centerY + radius * (double)Math.sin( start );
        System.out.println( xco + "," + yco );
        path.moveTo( xco, yco);
        for (int inx = 1; inx < 6 ; inx++ )
        {
            xco = centerX + radius * (double)Math.cos( start + inx * hexAngle );
            yco = centerY + radius * (double)Math.sin( start + inx * hexAngle );
            path.lineTo( xco, yco );
            System.out.println( "(" + xco + "," + yco + ")    " );
        }
        path.closePath();
    }
    
    public double apothem()
    {
        return apothem;
    }
    
    public double side()
    {
        return side;
    }
    
    public double xTranslate()
    {
        return xTranslate;
    }
    
    public double yTranslate()
    {
        return yTranslate;
    }
    
    public Shape getShape()
    {
        return path;
    }
    
    public double getEvenRowOffset()
    {
        return 0;
    }
    
    public double getOddRowOffset()
    {
        return apothem;
    }
    
    public void draw( Graphics2D gtx, Point coords )
    {
        gtx.draw( path );
        String      str     = coords.y + "," + coords.x;
        FontMetrics metrics = gtx.getFontMetrics();
        int         width   = metrics.stringWidth( str );
        int         height  = metrics.getHeight();
        float       startX  = (float)(radius - width / 2);
        float       startY  = (float)(radius + height / 2);
        gtx.drawString( str, startX, startY );
    }
}
