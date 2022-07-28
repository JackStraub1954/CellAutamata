package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.gmail.johnstraub1954.cell_automata.geometry.HexTile;
import com.gmail.johnstraub1954.cell_automata.geometry.Offset;

public class UnionTester2 {

	public static void main(String[] args)
	{
		UnionTester2   tester  = new UnionTester2();
		SwingUtilities.invokeLater( () -> tester.buildGUI() );
	}
	
	private void buildGUI()
	{
	    JFrame frame   = new JFrame( "Union Tester" );
	    frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	    JPanel contentPane = new JPanel();
	    frame.setContentPane( contentPane );
	    contentPane.add( new Canvas( HexTile.ofSide( 20, HexTile.VERTICAL ) ) );
	    
	    frame.pack();
	    frame.setVisible( true );
	}

	private class Canvas extends JPanel
	{
	    private final HexTile  tile;
	    private Graphics2D gtx;
	    
	    public Canvas( HexTile tile )
	    {
	        this.tile = tile;
	        Dimension  prefSize    = new Dimension( 500, 500 );
	        setPreferredSize( prefSize );
	    }
	    
	    @Override
	    public void paintComponent( Graphics graphics )
	    {
	        super.paintComponent( graphics );
	        gtx = (Graphics2D)graphics.create();
	        
	        int    width   = getWidth();
	        int    height  = getHeight();
	        gtx.setColor( Color.LIGHT_GRAY );
	        gtx.fillRect( 0,  0,  width, height );
	        
	        gtx.translate( 50, 50 );
	        int            rWidth  = 250;
	        int            rHeight = 200;
	        Rectangle2D    rect    = new Rectangle2D.Double( 0, 0, rWidth, rHeight );
	        Dimension      dimIn   = new Dimension( rWidth, rHeight );
	        Dimension      dimOut  = tile.getColRowDimension( dimIn );
	        Path2D         union   = new Path2D.Double();
	        List<Path2D>   list    = new ArrayList<>();
	        for ( int row = 0 ; row < dimOut.height ; ++row )
	            for ( int col = 0 ; col < dimOut.width ; ++col )
	            {
	                Offset offset  = new Offset( col, row );
	                Path2D path    = tile.getPath( offset );
	                union.append( path, false );
	                list.add( path );
	            }
	        
	        gtx.setColor( Color.RED );
	        for ( int yco = 0 ; yco < 1000 ; ++yco )
	            for ( int xco = 0 ; xco < 1000 ; ++xco )
	            {
	                if ( union.contains( xco, yco ) )
	                    gtx.drawLine( xco, yco, xco, yco );
	            }

            gtx.setColor( Color.BLACK );
            list.forEach( p ->gtx.draw( p ) );
	        gtx.draw( rect );
	        
	        // Make a test rectangle that is one pixel different
	        // in width/height than the original rectangle. This
	        // to avoid an anomaly in which about 5 pixels
	        // of the top of the original rectangle don't get tiled.
	        // See HexTileTest.testGetRowColDimension.
	        Rectangle2D    testRect    = 
	            new Rectangle2D.Double( 1, 1, rWidth - 1, rHeight - 1 );
	        
            System.out.println( "Original: " + union.contains( rect ) );
            System.out.println( "Test: " + union.contains( testRect ) );
	        
	        gtx.setColor( Color.GREEN );
	        double limitX  = rect.getX() + rect.getWidth();
	        double limitY  = rect.getY() + rect.getHeight();
	        for ( int row = 1 ; row < limitY ; ++row )
	            for ( int col = 1 ; col < limitX  ; ++col )
	            {
	                Point2D    point   = new Point2D.Double( col, row );
	                if ( !union.contains( point ) )
	                    gtx.drawLine( col, row, col, row );
	            }
	    }
	}
}
