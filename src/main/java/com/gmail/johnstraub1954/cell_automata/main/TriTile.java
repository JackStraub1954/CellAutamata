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
public class TriTile
{
    private static final float  triAngle    = (float)(Math.PI / 3);
    
    private final float     apothem;
    private final Path2D    path;
    private final float     side;
    private final float     yTranslate;
    private final float     xTranslate;
    private final float     radius;
    
    public TriTile( float radius )
    {
        this.radius = radius;
        
        path = new Path2D.Float();
        apothem = (float)(radius * Math.cos( Math.PI / 6 )); 
        side = (float)(radius * Math.cos( Math.PI / 6 ));
        
        xTranslate = 2 * apothem;
        yTranslate = (float)(2 * side - side * Math.cos( triAngle ) );

        float   centerX = radius;
        float   centerY = radius;
        float   xco     = centerX + radius * (float)Math.cos( Math.PI / 2 );
        float   yco     = centerY + radius * (float)Math.sin( Math.PI / 2 );
        System.out.println( xco + "," + yco );
        path.moveTo( xco, yco);
        for (int inx = 1; inx < 6 ; inx++ )
        {
            xco = centerX + radius * (float)Math.cos(Math.PI / 2 + inx * triAngle);
            yco = centerY + radius * (float)Math.sin(Math.PI / 2 + inx * triAngle);
            path.lineTo( xco, yco );
            System.out.println( "(" + xco + "," + yco + ")    " );
        }
        path.closePath();
    }
    
    public float apothem()
    {
        return apothem;
    }
    
    public float side()
    {
        return side;
    }
    
    public float xTranslate()
    {
        return xTranslate;
    }
    
    public float yTranslate()
    {
        return yTranslate;
    }
    
    public Shape getShape()
    {
        return path;
    }
    
    public float getEvenRowOffset()
    {
        return 0;
    }
    
    public float getOddRowOffset()
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
        float       startX  = radius - width / 2f;
        float       startY  = radius + height / 2;
        gtx.drawString( str, startX, startY );
    }
}
