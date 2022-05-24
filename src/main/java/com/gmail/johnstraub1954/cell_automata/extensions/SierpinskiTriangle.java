package com.gmail.johnstraub1954.cell_automata.extensions;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Builds a Sierpinski triangle, one generation at a time.
 * 
 * @author Jack Straub
 *          
 * @see <a href="https://en.wikipedia.org/wiki/Sierpi%C5%84ski_triangle#Removing_triangles">
 *          Wikipidia: Sierpinski Triangle
 *      </a>
 *
 */
public class SierpinskiTriangle extends JPanel
{
    /** Generated serial version UID. */
    private static final long serialVersionUID = -2887956707081246145L;

    /** 
     * A side of the underlying workspace in which the initial
     * triangle is drawn.
     * New triangles are generated recursively by calculating new sides
     * which are half the length of the previous.
     * so an integral power of 2 is a good starting point.
     */
    private static final int canvasWidth            = 1024;
    
    /** The color of the canvas (background). */
    private static final Color  canvasColor         = Color.CYAN;
    
    /** The fill color of a triangle. */
    private static final Color  triangleColor       = Color.MAGENTA;
    
    /** List of triangles in the current generation. */
    private List<Triangle>  triangles        = new ArrayList<>();
    
    /**
     * Entry point.
     * @param args  command line arguments; not used
     */
    public static void main(String[] args)
    {
        SierpinskiTriangle  panel   = new SierpinskiTriangle();
        SwingUtilities.invokeLater( () -> panel.buildGUI() );
    }
    
    /**
     * Constructor. 
     * Set the size of the canvas, and create the initial triangle.
     * To generate a new generation, the operator will click on
     * the canvas.
     */
    public SierpinskiTriangle()
    {
        // Use the Pythagorean theorem to calculate the height
        // of the initial triangle; the width of the triangle
        // will be the preferred width of the canvas.
        float   base        = canvasWidth / 2f;
        float   baseSq      = base * base;
        float   sideSq      = canvasWidth * canvasWidth;
        float   height      = (float)Math.sqrt( sideSq - baseSq );
        
        // Set the preferred size of the canvas, making sure
        // the height is an appropriate integer.
        int     intHeight   = (int)Math.ceil( height );
        setPreferredSize( new Dimension( canvasWidth, intHeight ) );
        
        float[] xcos    = new float[3];
        float[] ycos    = new float[3];
        
        // top of triangle
        xcos[0] = base;
        ycos[0] = 0;
        
        // lower left of triangle
        xcos[1] = 0;
        ycos[1] = height;
        
        // lower right of triangle
        xcos[2] = canvasWidth;
        ycos[2] = height;
        Triangle    initTriangle    = new Triangle( xcos, ycos );
        triangles.add( initTriangle );
        
        // Operator will click on the canvas to process
        // a new generation.
        addMouseListener( new Mouser() );
    }
    
    /**
     * Assemble the GUI.
     */
    private void buildGUI()
    {
        JFrame  frame   = new JFrame( "Sierpinski's Triangle" );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setContentPane( this );
        frame.pack();
        frame.setVisible( true );
    }
    
    @Override
    public void paintComponent( Graphics graphics )
    {
        super.paintComponent( graphics );
        int width   = getWidth();
        int height  = getHeight();
        
        Graphics2D  gtx = (Graphics2D)graphics.create();
        gtx.setRenderingHint( 
            RenderingHints.KEY_ANTIALIASING, 
            RenderingHints.VALUE_ANTIALIAS_ON
        );
        
        // paint over the canvas
        gtx.setColor( canvasColor );
        gtx.fillRect( 0, 0, width, height );
        
        // draw each triangle in the current generation
        gtx.setColor( triangleColor );
        for ( Triangle triangle : triangles )
            triangle.fill( gtx );
    }
        
    /**
     * Provides event processing for mouse clicks.
     * 
     * @author Jack Straub
     */
    private class Mouser extends MouseAdapter
    {
        /**
         * Process a mouse click event.
         * Create the next generation of the fractal,
         * and initiate repainting.
         */
        @Override
        public void mouseClicked( MouseEvent evt )
        {
            List<Triangle>  newTriangles    = new ArrayList<>();
            for ( Triangle triangle : triangles )
                newTriangles.addAll( triangle.divide() );
            triangles = newTriangles;
            repaint();
        }
    }

    /**
     * An instance of this class represents a triangle.
     * The triangle is defined by the coordinates of its vertices.
     * It is assumed that the triangle is equilateral, 
     * is "pointing" north,
     * and the base is parallel to the base of the window
     * that contains it.
     * 
     * @author Jack Straub
     */
    private static class Triangle
    {
        //          top
        //           *
        //          ***
        //    left ***** right
        private static final int    topV    = 0;
        private static final int    leftV   = 1;
        private static final int    rightV  = 2;
        
        private final float[]   xVals;
        private final float[]   yVals;
        
        /**
         * Constructor.
         * Configures a triangle using the coordinates of its vertices.
         * 
         * @param xVals the x coordinates of vertices of the triangle
         * @param yVals the y coordinates of vertices of the triangle
         */
        public Triangle( float[] xVals, float[] yVals )
        {
            this.xVals = Arrays.copyOf( xVals, 3 );
            this.yVals = Arrays.copyOf( yVals, 3 );
        }
        
        /**
         * Fills the triangle using a given graphics context.
         * It is assumed that the caller has
         * configured the graphics context
         * with the desired color of the triangle.
         * 
         * @param gtx   the given graphics context
         */
        public void fill( Graphics2D gtx )
        {
            int[]   iXVals  = new int[3];
            int[]   iYVals  = new int[3];
            for ( int inx = 0 ; inx < 3 ; ++inx )
            {
                iXVals[inx] = round( xVals[inx] );
                iYVals[inx] = round( yVals[inx] );
            }
            
            Polygon poly    = new Polygon( iXVals, iYVals, 3 );
            gtx.fill( poly );
        }
        
        /**
         * Divide the current triangle into three new triangles,
         * per the algorithm for generating a Sierpinski triangle.
         * 
         * @return  a list of three new triangles
         *          derived from this triangle
         *          per the algorithm for generating a Sierpinski triangle.
         *          
         * @see <a href="https://en.wikipedia.org/wiki/Sierpi%C5%84ski_triangle#Removing_triangles">
         *          Wikipidia: Sierpinski Triangle
         *      </a>
         */
        public List<Triangle> divide()
        {
            /*
             *              midX,topY
             *                  |
             *                  *
             *                 *| *
             *                * |  *
             * quatX,midY ---*--|---*--- -quatX,midY
             *              *   |    *
             *             *    *     *
             * leftX,     ************** rightX
             * botY             |        botY
             *              midX,botY
             */
            float   leftX   = xVals[leftV];
            float   rightX  = xVals[rightV];
            float   oldWid  = rightX - leftX;
            float   newWid  = oldWid / 2;
            float   midX    = leftX + newWid;
            float   quatXL  = leftX + newWid / 2;
            float   quatXR  = midX + newWid / 2;
            
            float   topY    = yVals[topV];
            float   botY    = yVals[leftV];
            float   oldHei  = botY - topY;
            float   newHei  = oldHei / 2;
            float   midY    = topY + newHei;
            
            List<Triangle>  triangles   = new ArrayList<>();
            
            // new top triangle
            float[]     newXcos     = new float[] { midX, quatXL, quatXR };
            float[]     newYcos     = new float[] { topY, midY, midY };
            triangles.add( new Triangle( newXcos, newYcos ) );
            
            // new left triangle
            newXcos = new float[] { quatXL, leftX, midX };
            newYcos = new float[] { midY, botY, botY };
            triangles.add( new Triangle( newXcos, newYcos ) );
            
            // new right triangle
            newXcos = new float[] { quatXR, midX, rightX };
            newYcos = new float[] { midY, botY, botY };
            triangles.add( new Triangle( newXcos, newYcos ) );
            
            return triangles;
        }
        
        /**
         * Rounds a decimal number to the nearest integer
         * (half-up).
         * 
         * @param val   the number to round
         * 
         * @return  the rounded number
         */
        private static int round( float val )
        {
            int iVal    = (int)(val + .5);
            return iVal;
        }
    }
}
