package com.gmail.johnstraub1954.cell_automata.geometry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import test_util.CvtTestData;
import test_util.PathSaver;
import test_util.TestConstants;

class HexTileTest
{
    /** Precompiled test data */
    private static final List<CvtTestData>  testData    = new ArrayList<>();
    
    @BeforeAll
    public static void beforeAll()
    {
        String  dirPath = 
            TestConstants.TEST_RESOURCES + TestConstants.CVT_DATA_DIR;
        File    dirFile = new File( dirPath );
        assertTrue( dirFile.exists() );
        assertTrue( dirFile.isDirectory() );
        
        File[]  files   = dirFile.listFiles();
        String  prefix  = TestConstants.CVT_DATA_FILE_PREFIX;
        String  suffix  = TestConstants.CVT_DATA_FILE_SUFFIX;
        int     count       = 0;
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
                    ++count;
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
        System.out.println( count + " files read" );
    }

    @ParameterizedTest
    @ValueSource( ints = {HexTile.HORIZONTAL, HexTile.VERTICAL } )
    void testOfSideInt( int orientation )
    {
        double  side        = 25;
        HexTile hexTile = HexTile.ofSide( side, orientation );
        assertEquals( side, hexTile.getSideLen() );
        assertEquals( orientation, hexTile.getOrientation() );
    }
    
    @ParameterizedTest
    @EnumSource( HexLayout.class )
    public void testOfSideHexLayout( HexLayout layout )
    {
        double  side        = 25;
        HexTile hexTile = HexTile.ofSide( side, layout );
        assertEquals( side, hexTile.getSideLen() );
        assertEquals( layout, hexTile.getLayout() );
        switch ( layout )
        {
        case ODD_R:
        case EVEN_R:
            assertEquals( HexTile.HORIZONTAL, hexTile.getOrientation() );
            break;
        case ODD_Q:
        case EVEN_Q:
            assertEquals( HexTile.VERTICAL, hexTile.getOrientation() );
            break;
        default:
            fail ( "unexpected HexLayout: " + layout );
        }
    }
    
    /**
     * Tests both toPixelCoords(Hex)
     * and toPixelCoords(int,int).
     */
    @Test
    public void testToPixelCoords()
    {
        for ( CvtTestData data : testData )
        {
            HexTile tile        = HexTile.ofSide( data.side, data.layout );
            Hex     hex         = data.axial;
            Point2D actPixel    = tile.toPixelCoords( hex );
            assertTrue( data.equals( actPixel ) );
            actPixel = tile.toPixelCoords( hex.qco, hex.rco );
            assertTrue( data.equals( actPixel ) );
        }
    }

    /**
     * Verify that a hexagon can be selected using x/y pixel coordinates
     * for any given HexLayout.
     * 
     * @param layout    the given HexLayout
     */
    @ParameterizedTest
    @EnumSource( HexLayout.class )
    void testGetSelectedHex( HexLayout layout )
    {
        /** The length of a side of a hexagon in this test. */
        int side        = 25;
        
        /** Reference hexagon, to be mapped to many rows/columns */
        HexTile hexTile = HexTile.ofSide( side, layout );
        
        // Generate all test hexagons, and calculate bounding rectangle
        for ( int yco = -100 ; yco < 100 ; ++yco )
            for ( int xco = -100 ; xco < 100 ; ++ xco )
                {
                    // Figure out which tile this pixel maps to
                    Hex     hex     = hexTile.getSelectedHex( xco, yco );
                    // Get the hexagon centered at the calculated coordinates
                    Path2D  path    = hexTile.getPath( hex );
                    // Is the calculated hexagon really at that 
                    // pixel location? There are some problematic edge
                    // conditions, so make sure the hexagon is within
                    // two pixels of the pixel coordinates.
                    Rectangle2D rect    = 
                        new Rectangle2D.Double( xco - 1, yco - 1, 2, 2 );
                    assertTrue( path.intersects( rect ) );
                }
    }

    /**
     * Verify that a hexagon at a particular axial coordinate
     * maps to the correct path for that hexagons.
     * This test uses files of serialized paths that were 
     * previously computed and verified.
     */
    @Test
    void testGetPathHex()
    {
        /** Resource subdirectory containing precompiled paths. */
        String  pathSaverDir    = TestConstants.PATH_SAVER_DIR;
        /** Project-relative path to appropriate subdirectory. */
        String  pathSaverPath   = TestConstants.TEST_RESOURCES + pathSaverDir;
        /** File corresponding to test subdirectory. */
        File    pathSavers      = new File( pathSaverPath );
        
        // Make sure that target file exists, and is a directory
        String  msg = "\"" + pathSaverPath + "\" doesn't exist";
        assertTrue( pathSavers.exists(), msg );
        msg = "\"" + pathSaverPath + "\" is not a directory";
        assertTrue( pathSavers.isDirectory(), msg );

        // Get all files in the target subdirectory.
        int     count   = 0;
        File[]  files   = pathSavers.listFiles();
        for ( File file : files )
        {
            // Filter just those files that correspond 
            // to precompiled path test data
            String  name    = file.getName();
            if ( 
                name.startsWith( TestConstants.PATH_SAVER_FILE_PREFIX )
                && name.endsWith( TestConstants.PATH_SAVER_FILE_SUFFIX )
            )
            {
                // Read test file and execute test.
                testGetPath( pathSaverDir + name );
                ++count;
            }
        }
        System.out.println( count + " test files processed" );
    }

    /**
     * Verify that a hexagon at a particular offset coordinate
     * maps to the correct path for that hexagon.
     */
    @ParameterizedTest
    @EnumSource( HexLayout.class )
    void testGetPathOffset( HexLayout layout )
    {
        HexTile tile    = HexTile.ofSide( 10.25, layout );
        for ( int rco = -10 ; rco < 10 ; ++rco )
            for ( int qco = 10 ; qco < 10 ; ++qco )
            {
                Hex     hex         = new Hex( qco, rco );
                Offset  offset      = layout.toOffset( hex );
                Path2D  pathHex     = tile.getPath( hex );
                Path2D  pathOffset  = tile.getPath( offset );
                String  message     = "Testing offset = " + offset;
                intAssertEquals( pathHex, pathOffset, message );
            }
    }
    
    @Test
    public void testGetRowColDimensionVertical()
    {
        
    }
    
    /**
     * Reads a file containing precompiled path data.
     * Calculates a new path using the stored data and verifies
     * that the new path is equivalent to the stored path.
     * 
     * @param filePath  path to target test file within 
     *                  relative to test/resources directory.
     */
    private void testGetPath( String filePath )
    {
        ClassLoader loader  = HexTileTest.class.getClassLoader();
        InputStream inStr   = loader.getResourceAsStream( filePath );
        String  message = "\"" + filePath + "\" not loaded";
        assertNotNull( inStr, message );
        
        try ( ObjectInputStream objReader   = new ObjectInputStream( inStr ); )
        {
            Object  obj     = objReader.readObject();
            message = 
                "\"" + obj.getClass().getName() + 
                "\" is not a type PathSaver";
            assertTrue( obj instanceof PathSaver, message );
            PathSaver   saver   = (PathSaver)obj;
            
            Path2D  expPath = saver.getPreCompiledPath();
            Path2D  actPath = saver.generatePath();
            message = filePath;
            intAssertEquals( expPath, actPath, message );
        }
        catch ( IOException | ClassNotFoundException exc )
        {
            exc.printStackTrace();
            fail( exc.getMessage() );
        }

    }
    
    /**
     * Internal method to assert that two paths are equal.
     * 
     * @param path1     the first of two paths to compare
     * @param path2     the second of two paths to compare
     * @param message   message to print should the assertion fail
     */
    private void intAssertEquals( Path2D path1, Path2D path2, String message )
    {
        assertTrue( equals( path1, path2 ), message );
    }
    
    /**
     * Compare two Path2Ds for equality.
     * 
     * @param path1 the first of two paths to compare
     * @param path2 the second of two paths to compare
     * 
     * @return true if the two paths are equal
     * 
     * @see #equals(BufferedImage, BufferedImage)
     */
    private boolean equals( Path2D path1, Path2D path2 )
    {
        // Path2D does not override equals.
        // To test for equality, generate a bitmap for each path
        // and compare the bitmaps.
        boolean         rval    = false;
        Rectangle       rect1   = path1.getBounds();
        Rectangle       rect2   = path2.getBounds();
        if ( rect1.equals( rect2 ) )
        {
            int             width   = rect1.width;
            int             height  = rect1.height;
            int             type    = BufferedImage.TYPE_INT_RGB;
            BufferedImage   bitmap1 = new BufferedImage( width, height, type );
            BufferedImage   bitmap2 = new BufferedImage( width, height, type );
            
            Graphics2D      gtx1    = (Graphics2D)bitmap1.getGraphics();
            Graphics2D      gtx2    = (Graphics2D)bitmap2.getGraphics();
            gtx1.draw( path1 );
            gtx2.draw( path2 );
            rval = equals( bitmap1, bitmap2 );
        }
        return rval;
    }
    
    /**
     * Compare two bitmaps (BufferedImages) for equality.
     * BufferedImage does not override equals, so equality
     * must be tested pixel-by-pixel.
     * 
     * @param bitmap1   the first bitmap to compare
     * @param bitmap2   the second bitmap to compare
     * 
     * @return  true if the two bitmaps are equal
     */
    private boolean equals( BufferedImage bitmap1, BufferedImage bitmap2 )
    {
        boolean rval    = true;
        
        int     rows1   = bitmap1.getHeight( null );
        int     rows2   = bitmap2.getHeight( null );
        int     cols1   = bitmap1.getWidth( null );
        int     cols2   = bitmap2.getWidth( null );
        if ( rows1 != rows2 || cols1 != cols2 )
            rval = false;
        else
        {
        for ( int yco = 0 ; yco < rows1 && rval ; ++yco )
            for ( int xco = 0 ; xco < cols1 && rval ; ++xco  )
            {
                int pixel1  = bitmap1.getRGB( xco, yco );
                int pixel2  = bitmap2.getRGB( xco, yco );
                if ( pixel1 != pixel2 )
                    rval = false;
            }
        }
        
        return rval;
    }
}
