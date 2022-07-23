package app.hex_visualizer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;

import javax.swing.JPanel;

import com.gmail.johnstraub1954.cell_automata.geometry.HexTile;
import com.gmail.johnstraub1954.cell_automata.geometry.Offset;

@SuppressWarnings("serial")
public class Canvas extends JPanel
{
	private static final Color         bgColor	    = Color.LIGHT_GRAY;
	private static final Color         edgeColor   = Color.BLACK;
    private static final int           xOffset     = 100;
    private static final int           yOffset     = 100;
    
    private int     rectWidth       = 500;
    private int     rectHeight      = 500;
    private HexTile tile;
	
    private Graphics2D  gtx             = null;
    
    public Canvas( HexTile tile )
    {
        super( null );
        Dimension   size        = new Dimension( 800, 900 );
        setPreferredSize( size );
        this.tile = tile;
    }
    
    public void paintComponent( Graphics graphics )
    {
        gtx = (Graphics2D)graphics.create();
        gtx.setRenderingHint( 
            RenderingHints.KEY_ANTIALIASING, 
            RenderingHints.VALUE_ANTIALIAS_ON
        );

        int         width       = getWidth();
        int         height      = getHeight();
        gtx.setColor( bgColor );
        gtx.fillRect( 0, 0, width, height );
        gtx.setColor( edgeColor );
        
        Rectangle   rect    = new Rectangle( 0, 0, rectWidth, rectHeight );
        gtx.translate( xOffset, yOffset );
        gtx.draw( rect );
        
        Dimension   widthHeight = new Dimension( rectWidth, rectHeight );
        Dimension   rowCol      = tile.getColRowDimension( widthHeight );
        for ( int row = 0 ; row < rowCol.height ; ++row )
            for ( int col = 0 ; col < rowCol.width ; ++col )
            {
                Offset  offset  = new Offset( col, row );
                Path2D  path    = tile.getPath( offset );
                gtx.draw( path );
            }
    }
}
