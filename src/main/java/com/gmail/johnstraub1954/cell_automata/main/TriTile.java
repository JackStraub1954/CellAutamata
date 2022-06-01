/**
 * 
 */
package com.gmail.johnstraub1954.cell_automata.main;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.geom.Path2D.Float;

/**
 * @author Jack Straub
 *
 */
public class TriTile
{
    private final float     apothem;
    private final Path2D    evenPath;
    private final Path2D    oddPath;
    private final float     side;
    private final float     yTranslate;
    private final float     xTranslate;
    private final float     radius;
    
    public TriTile( float radius )
    {
        Xiers   xiers   = new Xiers( radius );
        
        this.radius = radius;
        evenPath = xiers.evenPath();
        oddPath = xiers.oddPath();
        apothem = (float)(radius * Math.cos( Math.PI / 6 )); 
        side = (float)(2 * radius * Math.cos( Math.PI / 6 ));
        
        xTranslate = side;
        yTranslate = (float)(Math.sin( Math.PI / 3 ) * side );
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
        return evenPath;
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
        gtx.draw( oddPath );
        String      str     = coords.y + "," + coords.x;
        FontMetrics metrics = gtx.getFontMetrics();
        int         width   = metrics.stringWidth( str );
        int         height  = metrics.getHeight();
        float       startX  = radius - width / 2f;
        float       startY  = radius + height / 2;
        gtx.drawString( str, startX, startY );
    }
    
    private static class Xiers
    {
        /** Rotation from one vertex to the next */
        private static final double     rotateBy    = 2 * Math.PI / 3;
        /** Defines even-numbered orientation */
        private static final double     evenStart   = -Math.PI / 2;
        /** Defines odd-numbered orientation */
        private static final double     oddStart    = Math.PI / 2;
        /** Cosines that control even-numbered orientation */
        private static final float[]    evenCosines = new float[3];
        /** Sines that control even-numbered orientation */
        private static final float[]    evenSines   = new float[3];
        /** Cosines that control odd-numbered orientation */
        private static final float[]    oddCosines  = new float[3];
        /** Sines that control odd-numbered orientation */
        private static final float[]    oddSines    = new float[3];
        
        static
        {
            for ( int inx = 0 ; inx < 3 ; ++inx )
            {
                double  evenAngle   = evenStart + inx * rotateBy;
                double  oddAngle    = oddStart + inx * rotateBy;
                System.out.println( Math.toDegrees(evenAngle));
                evenCosines[inx] = (float)Math.cos( evenAngle );
                evenSines[inx] = (float)Math.sin( evenAngle );
                oddCosines[inx] = (float)Math.cos( oddAngle );
                oddSines[inx] = (float)Math.sin( oddAngle );
            }
        }
        
        private final Path2D    evenPath    = new Path2D.Float();
        private final Path2D    oddPath    = new Path2D.Float();
        
        public Xiers( float radius )
        {
            float   centerX = radius;
            float   centerY = radius;
            float   xco;
            float   yco;
            
            xco = centerX + radius * evenCosines[0];
            yco = centerY + radius * evenSines[0];
            evenPath.moveTo( xco, yco );
            
            xco = centerX + radius * oddCosines[0];
            yco = centerY + radius * oddSines[0];
            oddPath.moveTo( xco, yco );

            for ( int inx = 1 ; inx <= 2 ; ++inx )
            {
                xco = centerX + radius * evenCosines[inx];
                yco = centerY + radius * evenSines[inx];
                evenPath.lineTo(xco, yco);
                
                xco = centerX + radius * oddCosines[inx];
                yco = centerY + radius * oddSines[inx];
                oddPath.lineTo(xco, yco);
            }
            evenPath.closePath();
            oddPath.closePath();
        }
        
        public Path2D evenPath()
        {
            return evenPath;
        }
        
        public Path2D oddPath()
        {
            return oddPath;
        }
    }
}
