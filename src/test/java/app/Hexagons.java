package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.gmail.johnstraub1954.cell_automata.main.HexTile;

public class Hexagons
{

    public static void main(String[] args)
    {
        Hexagons    hexagons    = new Hexagons();
        SwingUtilities.invokeLater( () -> hexagons.buildGUI() );

    }

    private void buildGUI()
    {
        JFrame  frame   = new JFrame( "Hexagon Test" );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setContentPane( new Canvas() );
        frame.pack();
        frame.setVisible( true );
    }
    
    @SuppressWarnings("serial")
    private class Canvas extends JPanel
    {
        private final float     radius      = 20;
        private final HexTile   hexagon     = new HexTile( radius );
        private final float     yTranslate  = hexagon.yTranslate();
        private final float     xTranslate  = hexagon.xTranslate();
        
        private final double    evenRowXOffset = hexagon.getEvenRowOffset();
        private final double    oddRowXOffset  = hexagon.getOddRowOffset();

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
            
            AffineTransform saveAT  = gtx.getTransform();
            gtx.setColor( Color.BLACK );
            for ( int row = 0 ; row < 100 ; ++row )
            {
                gtx.setTransform( saveAT );
                double  rowTransX   = 
                    row % 2 == 0 ? evenRowXOffset  : oddRowXOffset;
                double  rowTransY   = row * yTranslate;
                gtx.translate( rowTransX, rowTransY );
                for ( int col = 0 ; col < 100 ; ++col )
                {
                    hexagon.draw( gtx, new Point( col, row ) );
                    gtx.translate( xTranslate, 0 );
                }
            }
        }
    }
}
