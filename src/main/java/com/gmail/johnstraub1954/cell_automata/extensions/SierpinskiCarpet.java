/**
 * 
 */
package com.gmail.johnstraub1954.cell_automata.extensions;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * @author Jack Straub
 *
 */
@SuppressWarnings("serial")
public class SierpinskiCarpet extends JPanel
{
    /** 
     * A side of the underlying workspace in which the initial
     * square is drawn.
     * New squares are generated recursively by calculating new sides
     * which are one-third the length of the previous.
     * so an integral power of 3 is a good starting point.
     */
    private static final int canvasWidth            = 1100;//729;
    
    /** The color of the canvas (background). */
    private static final Color  canvasColor         = Color.CYAN;
    
    /** The fill color of a triangle. */
    private static final Color  squareColor         = Color.BLACK;
    
    /** List of all Squares */
    private List<Square>        squares             = new ArrayList<>();
    

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        SierpinskiCarpet    panel   = new SierpinskiCarpet();
        SwingUtilities.invokeLater( () -> panel.buildGUI() );
    }
    
    public SierpinskiCarpet()
    {
        squares.add( new Square( 0, 0, canvasWidth ) );
        
        // Set the preferred size of the canvas
        setPreferredSize( new Dimension( canvasWidth, canvasWidth ) );
        
        // Operator will click on the canvas to process
        // a new generation.
        addMouseListener( new Mouser() );
    }
    
    /**
     * Assemble the GUI.
     */
    private void buildGUI()
    {
        JFrame  frame   = new JFrame( "Sierpinski's Carpet" );
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
        gtx.setColor( squareColor );
        for ( Square square : squares )
            square.fill( gtx );
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
            List<Square>    newSquares  = new ArrayList<>();
            for ( Square square : squares )
            {
                newSquares.add( square );
                newSquares.addAll( square.divide() );
            }
            squares = newSquares;
            repaint();
        }
    }

    /**
    * 
    * <pre>
    * *********************
    * *                   *
    * *                   *
    * *------*******------*
    * *------*******------*
    * *      *******      *
    * *                   *
    * *                   *
    * *********************</pre>
    * */
    private class Square
    {
        /** x-coordinate of upper-left corner */
        private final float xco;
        /** y-coordinate of upper-left corner */
        private final float yco;
        /** length of side of square */
        private final float side;
        
        /** x-coordinate of upper-left corner of central black square */
        private final float xcoCentral;
        /** y-coordinate of upper-left corner of central black square */
        private final float ycoCentral;
        /** length of side of square */
        private final float sideCentral;
        
        /** Encapsulation of central black square */
        private final Rectangle2D   rect;
        
        public Square( float xco, float yco, float side )
        {
            this.xco = xco;
            this.yco = yco;
            this.side = side;
            
            sideCentral = side / 3f;
            xcoCentral = xco + sideCentral;
            ycoCentral = yco + sideCentral;
            
            rect = new Rectangle2D.Float( 
                xcoCentral, 
                ycoCentral, 
                sideCentral,
                sideCentral
            );
        }
        
        public void fill( Graphics2D gtx )
        {
            gtx.fill( rect );
        }
        
        /**
         * Divide boundary of the (large) square into 8 regions.
         * 
         * <pre>
         * *********************
         * *      |     |      *
         * *   1  |  2  |   3  *
         * *------*******------*
         * *   4  *******   5  *
         * *------*******------*
         * *   6  |  7  |  8   *
         * *      |     |      *
         * *********************</pre>
         * 
         * @return  a list of 8 squares representing the boundary regions
         *          of this square
         */
        public List<Square> divide()
        {
            List<Square>    squares = new ArrayList<>();
            
            // NW rectangle
            float   newXco = xco;
            float   newYco = yco;
            float   newSide = sideCentral;
            squares.add( new Square( newXco, newYco, newSide ) );
            
            // N rectangle
            newXco += newSide;
            squares.add( new Square( newXco, newYco, newSide ) );
            
            // NE rectangle
            newXco += newSide;
            squares.add( new Square( newXco, newYco, newSide ) );
            
            // W rectangle
            newXco = xco;
            newYco += newSide;
            squares.add( new Square( newXco, newYco, newSide ) );
            
            // E rectangle
            newXco += 2 * newSide;
            squares.add( new Square( newXco, newYco, newSide ) );
            
            // SW rectangle
            newXco = xco;
            newYco += newSide;
            squares.add( new Square( newXco, newYco, newSide ) );
            
            // S rectangle
            newXco += newSide;
            squares.add( new Square( newXco, newYco, newSide ) );
            
            // NE rectangle
            newXco += newSide;
            squares.add( new Square( newXco, newYco, newSide ) );
            
            return squares;
        }
    }
}
