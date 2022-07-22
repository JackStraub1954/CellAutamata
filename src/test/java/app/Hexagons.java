package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.gmail.johnstraub1954.cell_automata.geometry.Hex;
import com.gmail.johnstraub1954.cell_automata.geometry.HexTile;

public class Hexagons
{

    public static void main(String[] args)
    {
        Hexagons    hexagons    = new Hexagons();
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
        private final double    side        = 20;
        private final HexTile   hexTile;
        
        private Shape   selection   = null;

        public Canvas()
        {
            super( null );
            Dimension   size        = new Dimension( 800, 800 );
            setPreferredSize( size );
            
            hexTile = HexTile.ofSide( side, HexTile.HORIZONTAL );
            addMouseListener( new MouseProcessor() );
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
                for ( int col = 0 ; col < 100 ; ++col )
                {
                    Path2D  path    = 
                        hexTile.getPath( row, col );
                    gtx.draw( path );
                }
            
            gtx.setColor( Color.BLUE );
            for ( int row = 0 ; row < 100 ; row += 2 )
                for ( int col = 2 ; col < 100 ; col += 3 )
                    gtx.fill( hexTile.getPath( row, col ) );
            
            gtx.setColor( Color.RED );
            for ( int row = -1 ; row < 100 ; row += 2 )
                for ( int col = -1 ; col < 100 ; col += 5 )
                    gtx.fill( hexTile.getPath( row, col ) );
            
            gtx.setColor( Color.GREEN );
            gtx.fill( hexTile.getPath( 5, 5 ) );
            
            if ( selection != null )
            {
                gtx.setColor( Color.MAGENTA );
                gtx.fill( selection );
            }
        }
        
        private class MouseProcessor extends MouseAdapter
        {
            @Override
            public void mouseClicked( MouseEvent evt )
            {
                int xco = evt.getX();
                int yco = evt.getY();
                Hex hex = hexTile.getSelectedHex( xco, yco );
                selection = hexTile.getPath(hex);
                repaint();
            }
        }
    }
}
