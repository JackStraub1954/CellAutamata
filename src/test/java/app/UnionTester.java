package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Path2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.gmail.johnstraub1954.cell_automata.geometry.HexTile;
import com.gmail.johnstraub1954.cell_automata.geometry.Offset;

public class UnionTester {

	public static void main(String[] args)
	{
		UnionTester   tester  = new UnionTester();
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
	        
	        Path2D union   = new Path2D.Double();
	        for ( int row = 5 ; row < 10 ; ++row )
	            for ( int col = 5 ; col < 10 ; ++col )
	            {
	                Offset offset  = new Offset( col, row );
	                Path2D path    = tile.getPath( offset );
	                union.append( path, false );
	            }
	        
	        gtx.setColor( Color.RED );
	        for ( int yco = 0 ; yco < 1000 ; ++yco )
	            for ( int xco = 0 ; xco < 1000 ; ++xco )
	            {
	                if ( union.contains( xco, yco ) )
	                    gtx.drawLine( xco, yco, xco, yco );
	            }

            gtx.setColor( Color.BLACK );
	        for ( int row = 5 ; row < 10 ; ++row )
                for ( int col = 5 ; col < 10 ; ++col )
                {
                    Offset offset  = new Offset( col, row );
                    Path2D path    = tile.getPath( offset );
                    gtx.draw( path );
                }
	    }
	}
}
