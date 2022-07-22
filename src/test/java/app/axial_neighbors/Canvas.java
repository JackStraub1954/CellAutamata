package app.axial_neighbors;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;

import javax.swing.JPanel;

import com.gmail.johnstraub1954.cell_automata.geometry.Hex;
import com.gmail.johnstraub1954.cell_automata.geometry.HexLayout;
import com.gmail.johnstraub1954.cell_automata.geometry.HexTile;
import com.gmail.johnstraub1954.cell_automata.geometry.Neighborhood;
import com.gmail.johnstraub1954.cell_automata.geometry.Offset;

@SuppressWarnings("serial")
public class Canvas extends JPanel
{
    public static final int COORD_DISPLAY_AXIAL     = 0;
    public static final int COORD_DISPLAY_OFFSET    = 1;
    
    private Neighborhood 	neighborhood    = null;
    private final Color     bgColor         = new Color( 0xeeeeee );
    private final Color     selfColor       = new Color( 0xbbbbbb );
    private final Color     neighborColor   = new Color( 0x999999 );
    private final Color     textColor       = Color.BLACK;
    private final Color     edgeColor       = Color.BLACK;
    
    private HexLayout   strategy        = HexLayout.ODD_R;
    private int         coordDisplay    = COORD_DISPLAY_AXIAL;
    private double      side            = 25; 
    private HexTile     hexagon         = HexTile.ofSide( side, strategy );
    
    private Graphics2D  gtx             = null;
    private FontMetrics fontMetrics     = null;
    
    public Canvas()
    {
        super( null );
        Dimension   size        = new Dimension( 800, 900 );
        setPreferredSize( size );
    }
    
    public HexLayout getHexLayout()
    {
        return strategy;
    }
    
    public void setCoordDisplay( int type )
    {
        coordDisplay = type;
        repaint();
    }
    
    public void setNeighborhood( int xco, int yco )
    {
        Hex self    = hexagon.getSelectedHex( xco, yco );
//        setNeighborhood( self );
    }
    
    public void setNeighborhood( Offset self )
    {
        neighborhood = hexagon.getNeighborhood( self );
        repaint();
    }
    
    public void paintComponent( Graphics graphics )
    {
        gtx = (Graphics2D)graphics.create();
        fontMetrics = gtx.getFontMetrics();
        gtx.setRenderingHint( 
            RenderingHints.KEY_ANTIALIASING, 
            RenderingHints.VALUE_ANTIALIAS_ON
        );

        int         width       = getWidth();
        int         height      = getHeight();
        gtx.setColor( bgColor );
        gtx.fillRect( 0, 0, width, height );
        gtx.setColor( edgeColor );
        for ( int row = -1 ; row < 50 ; ++row )
            for ( int col = -1 ; col < 50 ; ++col )
            {
                Offset  offset  = new Offset( col, row );
                Hex     hex     = strategy.toHex( offset );
                Path2D  path    = hexagon.getPath( hex );
                gtx.draw( path );
            }
        
        if ( neighborhood != null )
        {
        	Offset  self    = neighborhood.getSelf();
            gtx.setColor( selfColor );
            fill( self );
            gtx.setColor( neighborColor );
            for ( Offset neighbor : neighborhood.getNeighbors() )
                fill( neighbor );
        }
    }
    
    private void fill( Offset offset )
    {
        Color   saveColor   = gtx.getColor();
        Path2D  path        = hexagon.getPath( offset ); 
        gtx.fill( path );
        gtx.setColor( textColor );
        if ( coordDisplay == COORD_DISPLAY_AXIAL )
            drawCoords( path, offset );
        else
            drawCoords( path, offset );
        gtx.setColor( saveColor );
    }
    
    private void drawCoords( Path2D path, Hex hex )
    {
        String      str1    = "q=" + hex.qco;
        String      str2    = "r=" + hex.rco;
        int         height  = fontMetrics.getHeight();
        Rectangle   rect    = path.getBounds();
        
        int         width   = fontMetrics.stringWidth( str1 );
        int         yco     = rect.y + rect.height / 2;
        int         xco     = rect.x + rect.width / 2 - width / 2;
        gtx.drawString( str1, xco, yco );
        
        width = fontMetrics.stringWidth( str2 );
        yco += 3 * height / 4;
        xco = rect.x + rect.width / 2 - width / 2;
        gtx.drawString( str2, xco, yco );
    }
    
    private void drawCoords( Path2D path, Offset offset )
    {
        String      strX    = "x=" + offset.col;
        String      strY    = "y=" + offset.row;
        int         height  = fontMetrics.getHeight();
        Rectangle   rect    = path.getBounds();
        
        int         width   = fontMetrics.stringWidth( strY );
        int         yco     = rect.y + rect.height / 2;
        int         xco     = rect.x + rect.width / 2 - width / 2;
        gtx.drawString( strX, xco, yco );
        
        width = fontMetrics.stringWidth( strX );
        yco += 3 * height / 4;
        xco = rect.x + rect.width / 2 - width / 2;
        gtx.drawString( strY, xco, yco );
    }
}
