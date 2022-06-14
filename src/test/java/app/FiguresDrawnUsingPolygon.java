package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.gmail.johnstraub1954.cell_automata.main.Polygon;

public class FiguresDrawnUsingPolygon
{

    public static void main(String[] args)
    {
        FiguresDrawnUsingPolygon    sketch    = new FiguresDrawnUsingPolygon();
        SwingUtilities.invokeLater( () -> sketch.buildGUI() );

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
        private final float     radius  = 50;
        
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
            
            float   dim     = 2 * radius;
            for ( int inx = 0 ; inx < 10 ; ++inx )
            {
                int     sides   = inx + 3;
                float   circleX = inx * 2 * radius;
                float   circleY = 0;
                Shape   circle  = 
                    new Ellipse2D.Float( circleX, circleY, dim, dim );
                gtx.draw( circle );
            
                float   angle   = 0;
                float   polyX   = inx * 2 * radius + radius;
                float   polyY   = radius;
                Polygon poly    = Polygon.ofRadius( sides, radius );
                Path2D  path    = poly.getPath( polyX, polyY, angle );
                gtx.draw( path );
                
                System.out.printf( "%4d: ", sides );
                List<Point2D>   vertices    = 
                    poly.getVertices( polyX, polyY, angle );
                int             len         = vertices.size();
                String          fmt         = "(%11.6f, %11.6f)";
                for ( int jnx = 0 ; jnx < len ; ++jnx )
                {
                    Point2D vertex  = vertices.get( jnx );
                    System.out.printf( fmt, vertex.getX(), vertex.getY() );
                }
                System.out.println();
            }
        }
    }
}