package test_util;

/**
 * @author Jack Straub
 *
 */
public class TestConstants
{
    /**
     * Path from the project root to the test/resources directory.
     * Should only be used to save test data.
     * To read data, always use ClassLoader.getResourceAsStream(). 
     */
    public static final String  TEST_RESOURCES  = "src/test/resources/";
    /** 
     * Precompiled test data file for converting between hex coordinates
     * and pixel coordinates for horizontal (vertex pointing North) hexagons.
     */
    public static final String  TEST_DATA_CVT_HEX_PIXEL_H   =
        "TestDataCvtHex_PixelHorizontal.txt";
    /** 
     * Precompiled test data file for converting between hex coordinates
     * and pixel coordinates for vertical (vertex pointing West) hexagons.
     */
    public static final String  TEST_DATA_CVT_HEX_PIXEL_V   =
        "TestDataCvtHex_PixelVertical.txt";
    /** 
     * The name of the directory where serial files containing
     * precompiled Path2D objects are stored.
     */
    public static final String  PATH_SAVER_DIR          = "PathTestData/";
    /** The prefix for all file names containing path data for testing */
    public static final String  PATH_SAVER_FILE_PREFIX  = "PathSaver";
    /** The suffix for all file names containing path data for testing */
    public static final String  PATH_SAVER_FILE_SUFFIX  = ".ser";
    
    /** 
     * The name of the directory where files containing
     * precompiled conversion data are stored.
     */
    public static final String  CVT_DATA_DIR            = 
        "ConversionTestData/";
    /** Prefix for conversion test data files */
    public static final String  CVT_DATA_FILE_PREFIX    = "CVTData";
    /** Suffix for conversion test data files */
    public static final String  CVT_DATA_FILE_SUFFIX    = ".ser";
    
    /** 
     * The name of the directory where files containing
     * precompiled polygon data are stored.
     */
    public static final String  POLY_DATA_DIR            = 
        "PolyTestData/";
    /** Prefix for polygon test data files */
    public static final String  POLY_DATA_FILE_PREFIX    = "PolyData";
    /** Suffix for polygon test data files */
    public static final String  POLY_DATA_FILE_SUFFIX    = ".ser";
    
}
