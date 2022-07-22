package test_util;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a compilation of utilities for use in testing.
 * 
 * @author Jack Straub
 *
 */
public class TestUtils
{
    /**
     * Read all conversion test data from resources
     * and return them in a list.
     * All such resources are contained in a subdirectory 
     * of the test resources directory.
     * 
     * @return  a list of all conversion test data resources
     * 
     * @see CvtTestData
     */
    public static List<CvtTestData> getConversionTestData()
    {
        List<CvtTestData>   testData    = new ArrayList<>();
        
        String  dirPath = 
            TestConstants.TEST_RESOURCES + TestConstants.CVT_DATA_DIR;
        File    dirFile = new File( dirPath );
        assertTrue( dirFile.exists() );
        assertTrue( dirFile.isDirectory() );
        
        File[]  files   = dirFile.listFiles();
        String  prefix  = TestConstants.CVT_DATA_FILE_PREFIX;
        String  suffix  = TestConstants.CVT_DATA_FILE_SUFFIX;
        for ( File file : files )
        {
            String  fileName    = file.getName();
            if ( fileName.startsWith( prefix ) && fileName.endsWith( suffix ) )
            {
                System.out.println( fileName );
                try ( 
                    FileInputStream fileStream = new FileInputStream( file );
                    ObjectInputStream objStream = 
                        new ObjectInputStream( fileStream );
                )
                {
                    Object  obj = objStream.readObject();
                    assertTrue( obj instanceof CvtTestData );
                    testData.add( (CvtTestData)obj );
                }
                catch ( IOException | ClassNotFoundException exc )
                {
                    exc.printStackTrace();
                    fail( exc.getMessage() );
                }
            }
        }
        return testData;
    }
    
    /**
     * Read all Polygon test data from resources
     * and return them in a list.
     * All such resources are contained in a subdirectory 
     * of the test resources directory.
     * 
     * @return  a list of all Polygon test data resources
     * 
     * @see PolygonDescriptor
     */
    public static List<PolygonDescriptor> getPolygonTestData()
    {
        List<PolygonDescriptor>	testData    = new ArrayList<>();
        
        String  dirPath = 
            TestConstants.TEST_RESOURCES + TestConstants.POLY_DATA_DIR;
        File    dirFile = new File( dirPath );
        assertTrue( dirFile.exists() );
        assertTrue( dirFile.isDirectory() );
        
        File[]  files   = dirFile.listFiles();
        String  prefix  = TestConstants.POLY_DATA_FILE_PREFIX;
        String  suffix  = TestConstants.POLY_DATA_FILE_SUFFIX;
        for ( File file : files )
        {
            String  fileName    = file.getName();
            if ( fileName.startsWith( prefix ) && fileName.endsWith( suffix ) )
            {
                System.out.println( fileName );
                try ( 
                    FileInputStream fileStream = new FileInputStream( file );
                    ObjectInputStream objStream = 
                        new ObjectInputStream( fileStream );
                )
                {
                    Object  obj = objStream.readObject();
                    assertTrue( obj instanceof PolygonDescriptor );
                    testData.add( (PolygonDescriptor)obj );
                }
                catch ( IOException | ClassNotFoundException exc )
                {
                    exc.printStackTrace();
                    fail( exc.getMessage() );
                }
            }
        }
        return testData;
    }
    
    /**
     * Compare two given paths for equality.
     * 
     * @param path1	the first given path
     * @param path2	the second given path
     * 
     * @return	true if the two paths are equal
     */
    public static boolean equals( Path2D path1, Path2D path2 )
    {
    	boolean		result	= true;
    	Rectangle	rect1	= path1.getBounds();
    	Rectangle	rect2	= path2.getBounds();
    	if ( rect1.equals( rect2 ) )
    	{
    		int				firstX		= rect1.x;
    		int				firstY		= rect1.y;
    		int				lastX	    = firstX + rect1.width;
    		int				lastY		= firstY + rect1.height;
    		int				width		= firstX + rect1.width;
    		int				height		= firstY + rect1.height;
    		int				type		= BufferedImage.TYPE_INT_RGB;
    		BufferedImage	bitmap1		= new BufferedImage( width, height, type );
    		BufferedImage	bitmap2		= new BufferedImage( width, height, type );
    		
    		Graphics2D		gtx1		= (Graphics2D)bitmap1.getGraphics();
    		Graphics2D		gtx2		= (Graphics2D)bitmap2.getGraphics();
    		gtx1.draw( path1 );
    		gtx2.draw( path2 );
    		
    		for ( int yco = firstY ; yco < lastY && result ; ++yco )
    			for ( int xco = firstX ; xco < lastX && result ; ++xco )
    			{
    				int	pixel1	= bitmap1.getRGB( xco, yco );
    				int	pixel2	= bitmap2.getRGB( xco, yco );
    				if ( pixel1 != pixel2 )
    					result = false;
    			}
    	}
    	return result;
    }
    
    /**
     * Sleep (via Thread.sleep()) for the given number of milliseconds.
     * 
     * @param millis	the given number of milliseconds
     */
    public static void pause( long millis )
    {
        try
        {
            Thread.sleep( millis );
        }
        catch ( InterruptedException exc )
        {
            // ignore
        }
    }
}
