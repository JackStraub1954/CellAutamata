package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class AdHocSketch
{

    public static void main(String[] args)
    {
        AdHocSketch    hexagons    = new AdHocSketch();
        SwingUtilities.invokeLater( () -> hexagons.buildGUI() );

    }

    private void buildGUI()
    {
        JFrame  frame   = new JFrame( "Ad Hoc Sketch" );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setContentPane( new Canvas() );
        frame.pack();
        frame.setVisible( true );
    }
    
    @SuppressWarnings("serial")
    private class Canvas extends JPanel
    {
        public Canvas()
        {
            super( null );
            Dimension   size        = new Dimension( 800, 800 );
            setPreferredSize( size );
        }
        
        public void paintComponent( Graphics graphics )
        {
            Graphics2D  gtx     = (Graphics2D)graphics.create();
            gtx.setRenderingHint( 
                RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON
            );

            int         width   = getWidth();
            int         height  = getHeight();
            gtx.setColor( Color.CYAN );
            gtx.fillRect( 0, 0, width, height );
            gtx.setColor( Color.BLACK );
            
            float   radius  = 25;
            float   dim     = 2 * radius;
            float   centerX = radius;
            float   centerY = radius;
            Shape   circle  = new Ellipse2D.Float( 0, 0, dim, dim );
            gtx.draw( circle );
            
            Path2D  path    = new Path2D.Float();
            float   delta   = (float)(2 * Math.PI / 3);
            float   start   = -(float)(Math.PI / 2);
            float   xco     = centerX + radius * (float)Math.cos( start );
            float   yco     = centerY + radius * (float)Math.sin( start );
            path.moveTo( xco, yco );
            
            for ( int inx = 1 ; inx < 3 ; ++inx )
            {
                xco = centerX + radius * (float)Math.cos( start + inx * delta );
                yco = centerY + radius * (float)Math.sin( start + inx * delta );
                path.lineTo(xco, yco);
            }
            path.closePath();
            gtx.draw( path );
        }
    }
}