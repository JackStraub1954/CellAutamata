package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import java.awt.geom.Path2D.Double;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.gmail.johnstraub1954.cell_automata.geometry.Polygon;

public class Triangles
{

    public static void main(String[] args)
    {
        Triangles    triangles  = new Triangles();
        SwingUtilities.invokeLater( () -> triangles.buildGUI() );

    }

    private void buildGUI()
    {
        JFrame  frame   = new JFrame( "Triangles Test" );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setContentPane( new Canvas() );
        frame.pack();
        frame.setVisible( true );
    }
    
    @SuppressWarnings("serial")
    private class Canvas extends JPanel
    {
        private final double    triSide     = 60;
        private final double    triRadius   = triSide / 2;
        private final double    triHeight   = 
            triSide * Math.sin( Math.PI / 3 );

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
            
            for ( int row = 0 ; row < 10 ; ++row )
            {
                double  topYco      = row * triHeight;
                double  bottomYco   = topYco + triHeight;
                for ( int col = 0 ; col < 100 ; ++col )
                {
                    // create a square that will be divided into two triangles
                    // the square in col zero will be half-off the grid
                    final double    centerXco   = col * triRadius;
                    final double    leftXco     = centerXco - triRadius;
                    final double    rightXco    = centerXco + triRadius;
                    
                    Path2D  path        = new Path2D.Double();
                    if ( isDown( row, col ) )
                    {
                        // "down" triangle
                        path.moveTo( centerXco, bottomYco );
                        path.lineTo( leftXco,  topYco );
                        path.lineTo( rightXco, topYco );
                        path.closePath();
                        gtx.setColor( Color.RED );
                        gtx.fill( path );
                    }
                    else
                    {
                        // "up" triangle
                        path.moveTo( centerXco, topYco );
                        path.lineTo( leftXco, bottomYco );
                        path.lineTo( rightXco, bottomYco );
                        gtx.setColor( Color.BLACK );
                        gtx.draw( path );
                    }
                }
            }
        }
        
        private boolean isDown( int row, int col )
        {
            boolean fill    = (row %2 == col % 2);
            return fill;
        }
    }
}
