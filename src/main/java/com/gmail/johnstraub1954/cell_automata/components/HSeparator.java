package com.gmail.johnstraub1954.cell_automata.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import javax.swing.JComponent;

/**
 * Creates a horizontal separator.
 * Generally used to generate a gap between two components.
 * The separator may be visible (a line) or invisible (blank space).
 * 
 * @author Jack Straub
 */
public class HSeparator extends JComponent
{
    /** Generated serial version UID */
    private static final long serialVersionUID = -3147393874221291586L;
    
    /** Default value for the stroke of the encapsulated line */
    private static final Stroke stroke1     = new BasicStroke( 1 );
    /**
     *  Default value, as a percentage 
     *  for the width of the encapsulated line
     */
    private static final float  defWidth    = 1;
    /** 
     * Default value for the height of the gap containing the
     * encapsulated line.
     */
    private static final int    defHeight   = 10;
    
    /** Determines if this separator should be visible or not */
    private final boolean   invisible;
    /** 
     * The width of the visible line as a percentage of the width
     * the component occupies.
     */
    private final float     percentWidth;
    /** Height of the gap occupied by this component */
    private final int       height;
    
    /**
     * Default constructor.
     * The width and height of the separator will be given a default;
     * the visibility will default to false.
     */
    public HSeparator()
    {
        this( defWidth, defHeight, false );
    }
    
    /**
     * Constructor.
     * The line encapsulated by the component will have the given width,
     * as a percentage of the width occupied by the entire component.
     * A default value will be provided for the height;
     * visibility will default to false.
     * 
     * @param percentWidth  the given width of the encapsulated line
     */
    public HSeparator( float percentWidth )
    {
        this( percentWidth, defHeight, false );
    }
    
    /**
     * Constructor.
     * The gap between components will have the given height,
     * The visibility is provided by the given value.
     * The width of the encapsulated line, as a percentage of the
     * width of the entire component, will be given a default value.
     * 
     * @param height    the given height of the encapsulated line
     * @param invisible true to make the encapsulated line invisible
     */
    public HSeparator( int height, boolean invisible )
    {
        this( defWidth, height, invisible );
    }
    
    /**
     * Constructor.
     * The width of the encapsulated line and the height of this component
     * will be given default values.
     * The visibility of the encapsulated line is determined by the caller.
     * 
     * @param invisible true to make the encapsulated line invisible
     */
    public HSeparator( boolean invisible )
    {
        this( defWidth, defHeight, invisible );
    }
    
    /**
     * Constructor.
     * The encapsulated line will have the given width, 
     * as a percentage of the component that contains it,
     * and the given visibility.
     * The component will have the given height.
     * 
     * @param percentWidth  the given width of the encapsulated line
     * @param height        the given height of the component
     * @param invisible     true to make the encapsulated line invisible
     */
    public HSeparator( float percentWidth, int height, boolean invisible )
    {
        this.percentWidth = percentWidth;
        this.invisible = invisible;
        this.height = height;
        
        // have to start out with non-null preferred size,
        // otherwise initial dimensions will be 0/0
        int         width   = 0;
        Dimension   size    = new Dimension( width, height );
        setPreferredSize( size );
    }
    
    /**
     * Copy constructor.
     * Useful for making many separators with the same properties.
     * 
     * @param toCopy    the separator to copy
     */
    public HSeparator( HSeparator toCopy )
    {
        this.percentWidth = toCopy.percentWidth;
        this.height = toCopy.height;
        this.invisible = toCopy.invisible;
    }
    
    /**
     * Draws this component.
     * 
     * @param   graphics    the graphics context to use while drawing
     */
    @Override
    public void paintComponent( Graphics graphics )
    {
        super.paintComponent( graphics );
        
        Graphics2D  gtx     = (Graphics2D)graphics.create();
        Container   parent  = getParent();
        int         width   = getWidth();
        Dimension   size    = new Dimension( width, height );
        this.setSize( size );
        
        gtx.setColor( parent.getBackground() );
        gtx.fillRect( 0,  0,  (int)(width * percentWidth),  height );
        
        if ( !invisible )
        {
            float       verticalMidpoint    = height / 2f;
            float       lineLength          = width * percentWidth;
            float       xStart              = (width - lineLength) / 2f;
            float       xEnd                = xStart + lineLength;
            Point2D     start               = 
                new Point2D.Float( xStart, verticalMidpoint );
            Point2D     end                 = 
                new Point.Float( xEnd, verticalMidpoint );
            Line2D      line                = new Line2D.Float( start, end );
            
            gtx.setColor( Color.BLACK );
            gtx.setStroke( stroke1 );
            gtx.draw( line );
        }
    }
}
