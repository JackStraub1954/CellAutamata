package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.gmail.johnstraub1954.cell_automata.geometry.Polygon;

public class HexagonsOrig
{

    public static void main(String[] args)
    {
        HexagonsOrig    hexagons    = new HexagonsOrig();
        SwingUtilities.invokeLater( () -> hexagons.buildGUI() );
    }

    private void buildGUI()
    {
        JFrame  frame   = new JFrame( "Hexagons Test" );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setContentPane( new Canvas() );
        frame.pack();
        frame.setVisible( true );
    }
    
    @SuppressWarnings("serial")
    private class Canvas extends JPanel
    {
        private final double    sideLen     = 10;
        private final Polygon   hexagon     = Polygon.ofSide( 6,  sideLen );
        private final double    radius      = hexagon.getRadius();
        private final double    apothem     = hexagon.getApothem();
        // better known as the "long axis"
        private final double    height      = 2 * radius;
        // better known as the "short axis"
        private final double    width       = 2 * apothem;
        private final double    rowOffset   = height * 3. / 4.;
        private final double    colOffset   = width;

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
            
            for ( int row = 0 ; row < 100 ; ++row )
            {
                // the center of row 0 is radius; for each additional
                // row, add height * 3/4
                double  centerY     = radius + row * rowOffset;
                // the center of column 0 in even rows is the apothem;
                // in odd rows it is two * apothem.
                double  firstColOffset   = 
                    row % 2 == 0 ? apothem : 2 * apothem;
                for ( int col = 0 ; col < 100 ; ++col )
                {
                    // get a path that makes the hexagon's long axis vertical;
                    // the first path in an even-numbered row will have the
                    // top vertex flush with the top of the row, and the
                    // left side flush with the left of the row.
                    double  centerX = firstColOffset + col * colOffset;
                    double  angle   = -Math.PI / 2;
                    Path2D  path    = 
                        hexagon.getPath( centerX, centerY, angle );
                    gtx.draw( path );
                }
            }
            
            for ( int row = 0 ; row < 100 ; row += 2 )
                for ( int col = 2 ; col < 100 ; col += 3 )
                    gtx.fill( getPath( row, col ) );
            
            gtx.setColor( Color.RED );
            for ( int row = -1 ; row < 100 ; row += 2 )
                for ( int col = -1 ; col < 100 ; col += 5 )
                    gtx.fill( getPath( row, col ) );
            
            gtx.setColor( Color.GREEN );
            gtx.fill( getPath( 5, 5 ) );
        }
        
        private Path2D getPath( int row, int col )
        {
            double  firstColOffset   = 
                (row % 2) == 0 ? apothem : 2 * apothem;
            double  centerX     = firstColOffset + col * colOffset;
            double  centerY     = radius + row * rowOffset;
            double  angle       = -Math.PI / 2;
            Path2D  path        = hexagon.getPath( centerX, centerY, angle );
            return path;
        }
    }
}
