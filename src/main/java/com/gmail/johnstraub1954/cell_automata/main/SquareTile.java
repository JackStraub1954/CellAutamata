package com.gmail.johnstraub1954.cell_automata.main;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

/**
 * @author Jack Straub
 *
 */
public class SquareTile
{
    private final float         apothem;
    private final Rectangle2D   rect;
    private final float         side;
    private final float         yTranslate;
    private final float         xTranslate;
    private final float         radius;
    
    public SquareTile( float radius )
    {
        this.radius = radius;
        
        side = (float)(radius * Math.cos( Math.PI / 4 ));
        rect = new Rectangle2D.Float( 0, 0, side, side );
        apothem = side / 2f; 
        
        xTranslate = side;
        yTranslate = side;
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
        return rect;
    }
    
    public float getEvenRowOffset()
    {
        return 0;
    }
    
    public float getOddRowOffset()
    {
        return 0;
    }

    public void draw( Graphics2D gtx, Point coords )
    {
        gtx.draw( rect );
        String      str     = coords.y + "," + coords.x;
        FontMetrics metrics = gtx.getFontMetrics();
        int         width   = metrics.stringWidth( str );
        int         height  = metrics.getHeight();
        float       startX  = side / 2 - width / 2;
        float       startY  = side / 2 + height / 2;
        gtx.drawString( str, startX, startY );
    }
    
    public static float getRadiusFromSide( float side )
    {
        float   radius  = (float)(side / Math.cos( Math.PI / 4 ) );
        return radius;
    }
}
