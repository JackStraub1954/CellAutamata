package app.pixel_hex_data_compiler;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

import javax.swing.JPanel;

import com.gmail.johnstraub1954.cell_automata.geometry.Hex;
import com.gmail.johnstraub1954.cell_automata.geometry.HexLayout;
import com.gmail.johnstraub1954.cell_automata.geometry.Offset;
import com.gmail.johnstraub1954.cell_automata.geometry.Polygon;

@SuppressWarnings("serial")
public class Canvas extends JPanel
{
    public static final int COORD_DISPLAY_AXIAL     = 0;
    public static final int COORD_DISPLAY_OFFSET    = 1;
    
    public static final int LAYER_BG                = 1;
    public static final int LAYER_OFFSET            = 2;
    public static final int LAYER_HEX               = 4;
    
    private final Color     bgColor             = Color.LIGHT_GRAY;
    private final Color     edgeColor           = Color.BLACK;
    private final Color     vertexPointColor    = Color.RED;
    private final Color     midpointPointColor  = Color.BLUE;
    private final double    testPointWidth      = 7;
    
    private Hex         hex0            = null;
    private Hex         hex1            = null;
    private HexLayout   strategy        = HexLayout.ODD_R;
    private int         coordDisplay    = COORD_DISPLAY_AXIAL;
    private double      side            = 75; 
    private Polygon     polygon         = null;
    
    private Graphics2D  gtx             = null;
    private FontMetrics fontMetrics     = null;
    
    public Canvas()
    {
        super( null );
        Dimension   size        = new Dimension( 800, 900 );
        setPreferredSize( size );
        
        polygon = Polygon.ofSide( 6, side );
    }
    
    public void setCoordDisplay( int type )
    {
        coordDisplay = type;
        repaint();
    }
    
    public void setStrategy( HexLayout strategy )
    {
        this.strategy = strategy;
        repaint();
    }
    
    public void add( Hex hex )
    {
        if ( hex0 == null )
            hex0 = new Hex( hex );
        else if ( hex1 == null )
            hex1 = new Hex( hex );
        else
            ;
        repaint();
    }
    
    public void add( Offset offset )
    {
        Hex hex = strategy.toHex( offset );
        add( hex);
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
        for ( Hex hex : new Hex[] { hex0, hex1 } )
        {
            double  angle   = strategy.getStartAngle();
            Point2D center  = strategy.cvtHexToPixel( hex, side );
            Path2D  path    = polygon.getPath( center, angle );
            gtx.draw( path );
            if ( coordDisplay == COORD_DISPLAY_AXIAL )
                drawCoords( path, hex );
            else
                drawCoords( path, strategy.toOffset( hex ) );
            if ( hex == hex0 )
                plotTestPointsVertices( hex );
            else
                plotTestPointsSides( hex );
        }
    }
    
    private void plotTestPointsVertices( Hex hex )
    {
        Color           saveColor   = gtx.getColor();
        Point2D         center      = strategy.cvtHexToPixel( hex, side );
        
        gtx.setColor( vertexPointColor );        
        double  radius  = polygon.getRadius();
        double  angle   = strategy.getStartAngle();
        plotTestPoints( radius, angle, center );
        plotRefCircles( radius, center );
        plotRefLine( radius, angle, center );

        gtx.setColor( saveColor );
    }
    
    private void plotTestPointsSides( Hex hex )
    {
        Color           saveColor   = gtx.getColor();
        Point2D         center      = strategy.cvtHexToPixel( hex, side );
        double          startAngle  = strategy.getStartAngle();

        gtx.setColor( midpointPointColor );
        double  radius      = polygon.getApothem();
        double  angle       = startAngle + (2 * Math.PI) /12;
        plotTestPoints( radius, angle, center );
        plotRefCircles( radius, center );
        plotRefLine( radius, angle, center );

        gtx.setColor( saveColor );
    }
    
    private void 
    plotTestPoints( double radius, double startAngle, Point2D center )
    {
        double  centerX     = center.getX();
        double  centerY     = center.getY();
        double  angleIncr   = (2 * Math.PI) / 6; 
        for ( int inx = 0 ; inx < 6 ; ++inx )
        {
            double      angle   = startAngle + inx * angleIncr;
            double      xco     = centerX + radius * Math.cos( angle );
            double      yco     = centerY + radius * Math.sin( angle );
            double      rectX   = xco - testPointWidth / 2;
            double      rectY   = yco - testPointWidth / 2;
            Ellipse2D   circle  = 
                new Ellipse2D.Double( 
                    rectX, 
                    rectY, 
                    testPointWidth, 
                    testPointWidth
                );
            gtx.fill( circle );
        }
    }
    
    private void plotRefCircles( double radius, Point2D center )
    {
        double      centerX     = center.getX();
        double      centerY     = center.getY();
        
        double      refRadius   = radius * 1.1;
        double      rectX       = centerX - refRadius;
        double      rectY       = centerY - refRadius;
        double      rectS       = 2 * refRadius;
        Ellipse2D   circle      =
            new Ellipse2D.Double( rectX, rectY, rectS, rectS );
        gtx.draw( circle );
        
        refRadius = radius * .9;
        rectX   = centerX - refRadius;
        rectY   = centerY - refRadius;
        rectS   = 2 * refRadius;
        circle  =
            new Ellipse2D.Double( rectX, rectY, rectS, rectS );
        gtx.draw( circle );
    }
    
    private void plotRefLine( double radius, double angle, Point2D center )
    {
        double  centerX     = center.getX();
        double  centerY     = center.getY();
        double  refRadius   = radius * 1.3;
        double  xco         = centerX + refRadius * Math.cos( angle );
        double  yco         = centerY + refRadius * Math.sin( angle );
        Point2D end         = new Point2D.Double( xco, yco );
        Line2D  line        = new Line2D.Double( center, end );
        gtx.draw( line );
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
