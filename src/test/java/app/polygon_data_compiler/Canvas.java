package app.polygon_data_compiler;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.gmail.johnstraub1954.cell_automata.geometry.Polygon;

@SuppressWarnings("serial")
public class Canvas extends JPanel
{
	private static final Color			bgColor		= Color.LIGHT_GRAY;
	private static final Color			edgeColor	= Color.BLACK;
	private static final Color			textColor	= Color.BLACK;
	
	private final List<PolygonDescrip>	polygons	= new ArrayList<>();
	
    private Graphics2D  gtx             = null;
    private FontMetrics fontMetrics     = null;
    
    public Canvas()
    {
        super( null );
        Dimension   size        = new Dimension( 800, 900 );
        setPreferredSize( size );
    }
    
    public void addPolygon( Polygon polygon, Point2D center, double angle )
    {
        polygons.add( new PolygonDescrip( polygon, center, angle ) );
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
        for ( PolygonDescrip descrip : polygons )
        {
        	Polygon	polygon	= descrip.polygon;
        	Point2D	center	= descrip.center;
        	double	angle	= descrip.angle;
        	
        	Path2D	path	= polygon.getPath( center, angle );
        	gtx.draw( path );
        	drawCoords( center );
        }
    }
    
    private void drawCoords( Point2D point )
    {
    	double		xco		= point.getX();
    	double		yco		= point.getY();
        
        int			radius	= 2;
        int			ovalXco	= (int)(xco - radius + .5);
        int			ovalYco = (int)(yco - radius + .5);
        int			diam	= 2 * radius;
        gtx.fillOval( ovalXco, ovalYco, diam, diam );
        
    	 String		fmt		= "x=%3.1f,y=3.1f";
        String      str     = String.format( fmt, point.getX(), point.getY() );
        int         height  = fontMetrics.getHeight();
        int         width   = fontMetrics.stringWidth( str );
        Color		save	= gtx.getColor();
        gtx.setColor( textColor );
        int			strXco	= (int)(xco - width / 2 + .5);
        int			strYco	= (int)(yco + height + .5);
//        gtx.drawString( str, strXco, strYco );
        gtx.setColor( save );
    }
    
    private class PolygonDescrip
    {
    	public final Polygon	polygon;
    	public final Point2D	center;
    	public final double		angle;
    	
    	public PolygonDescrip( Polygon polygon, Point2D center, double angle )
    	{
    		this.polygon = polygon;
    		this.center = center;
    		this.angle = angle;
    	}
    }
}
