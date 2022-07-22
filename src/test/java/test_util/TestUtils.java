package test_util;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

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
     * Compare two given paths for equality.
     * 
     * @param path1	the first given path
     * @param path2	the second given path
     * 
     * @return	true if the two paths are equal
     */
    public static boolean equals( Path2D path1, Path2D path2 )
    {
    	boolean		result	= false;
    	Rectangle	rect1	= path1.getBounds();
    	Rectangle	rect2	= path2.getBounds();
    	if ( rect1.equals( rect2 ) )
    	{
    		int				width		= rect1.width;
    		int				height		= rect1.height;
    		int				type		= BufferedImage.TYPE_INT_RGB;
    		BufferedImage	bitmap1		= new BufferedImage( width, height, type );
    		BufferedImage	bitmap2		= new BufferedImage( width, height, type );
    		
    		int				lastY		= rect1.y + height;
    		int				lastX	    = rect1.x + width;
    		for ( int yco = rect1.y ; yco < lastY && result ; ++yco )
    			for ( int xco = rect1.x ; xco < lastX && result ; ++xco )
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
