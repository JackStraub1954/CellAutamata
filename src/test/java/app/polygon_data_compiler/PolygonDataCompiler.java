package app.polygon_data_compiler;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.gmail.johnstraub1954.cell_automata.geometry.Offset;
import com.gmail.johnstraub1954.cell_automata.geometry.Polygon;

import test_util.CvtTestData;
import test_util.PolygonDescriptor;
import test_util.TestConstants;

public class PolygonDataCompiler
{
	private final List<PolygonDescriptor>	polygonDescrips	= new ArrayList<>();
	private final Canvas	canvas	= new Canvas();
	
	public static void main(String[] args)
	{
		PolygonDataCompiler	data	= new PolygonDataCompiler();
		SwingUtilities.invokeLater( () -> data.buildGUI() );
		data.execute();
	}

	private void buildGUI()
	{
		JFrame	frame	= new JFrame( "Polygon Data" );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.setContentPane( canvas );
		frame.pack();
		frame.setVisible( true );
	}
	
	private void execute()
	{
		draw( 20, Math.PI / 2, 0 );
		draw( 20, Math.PI / 4, 60 );
		draw( 20, 0, 120 );
		int	status	= JOptionPane.showConfirmDialog( null, "Save results?" );
		if ( status == JOptionPane.OK_OPTION )
			saveResults();
		System.exit( 0 );
	}
	
	private void draw( double side, double orientationStart, double yOffset )
	{
		double	yco	= yOffset + 2 * side;
		double  xco	= side;
		for ( int numSides = 3 ; numSides <= 6 ; ++numSides )
		{
			Point2D				center	= new Point2D.Double( xco, yco );
			Polygon				poly	= Polygon.ofSide( numSides, side );
			PolygonDescriptor	descrip	= 
					PolygonDescriptor.ofPolygon( poly, center, orientationStart );
			polygonDescrips.add( descrip );
			canvas.addPolygon( poly, center, orientationStart );
			xco += 4 * side;
		}
	}
	
	private void saveResults()
	{
		int	count	= 0;
        for ( PolygonDescriptor descrip : polygonDescrips )
        {
            String  dirPath	= 
                TestConstants.TEST_RESOURCES + TestConstants.POLY_DATA_DIR;
            String	fmt		= "%s/%s%04d%s";
            String	path	= 
            	String.format( 
            			fmt, 
            			dirPath,
            			TestConstants.POLY_DATA_FILE_PREFIX,
            			count++,
            			TestConstants.POLY_DATA_FILE_SUFFIX
            	);
            File    file        = new File( path );
            try ( 
                FileOutputStream fileStream = new FileOutputStream( file );
                ObjectOutputStream objStream = 
                    new ObjectOutputStream( fileStream ) )
            {
                objStream.writeObject( descrip );
            }
            catch ( IOException exc )
            {
                exc.printStackTrace();
                System.exit( 1 );
            }
            System.out.println( file.getAbsolutePath() );
        }
	}
}
