package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Path2D.Float;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class AdHocSketch2
{

    public static void main(String[] args)
    {
        AdHocSketch2    hexagons    = new AdHocSketch2();
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
    private static class Canvas extends JPanel
    {
        private static final float      triAngle        = (float)(Math.PI / 3);
        private static final float[]    evenVertices    = new float[3];
        private static final float[]    oddVertices     = new float[3];
        
        static
        {
            double  delta   = 3 * Math.PI / 2; // 270 degrees
            double  inverse = Math.PI; // 180 degrees
//            double  vertex = -Math.PI;  // -90 degrees
            for ( int inx = 0 ; inx < 2 ; ++inx )
            {
                double  vertex  = -Math.PI + inx * delta;
                evenVertices[inx] = (float)vertex;
                oddVertices[inx] = -(float)vertex;
                System.out.println( vertex + ", " + -vertex );
            }
        }
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
            
            Path2D  path1   = new Path2D.Float();
            Path2D  path2   = new Path2D.Float();
            
//            path1.moveTo( evenVertices, height);
            
//            gtx.draw( path );
        }
    }
}