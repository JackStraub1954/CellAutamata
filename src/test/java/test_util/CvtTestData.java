package test_util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.geom.Point2D;
import java.io.Serializable;

import com.gmail.johnstraub1954.cell_automata.geometry.Hex;
import com.gmail.johnstraub1954.cell_automata.geometry.HexLayout;
import com.gmail.johnstraub1954.cell_automata.geometry.Offset;

public class CvtTestData implements Serializable
{
    /** Generated serial version UID */
    private static final long serialVersionUID = 8155678397391702607L;
    
    public final HexLayout      layout;
    public final double         side;
    public final Offset         offset;
    public final Hex            axial;
    public final Point2D.Double pixel;
    
    /**
     * Constructor.
     * This constructor assumes that the given HexLayout 
     * generates valid data for all conversions. 
     * It then generates and stores all conversions for
     * Offset, Hex and Point2D coordinates.
     * 
     * Presumably this data will be saved somewhere
     * and later retrieved.
     * After retrieval, coordinates of any type can be
     * independently generated using the layout and side
     * components of this object,
     * and then verified against the coordinates stored herein.
     * 
     * @param layout    the layout strategy used to calculate coordinates
     *                  for this object
     * @param offset    the offset coordinates associated
     *                  with this object
     * @param side      the length of a side to use in calculating
     *                  coordinates for this object
     */
    public CvtTestData( HexLayout layout, Offset offset, double side )
    {
        this.layout = layout;
        this.side = side;
        this.offset = new Offset( offset );
        axial = layout.toHex( offset );
        
        // it's necessary to store Point2D as type Double; 
        // Point2D.Double implements serializable, Point2D does not.
        Point2D point       = layout.cvtHexToPixel( axial, side );
        double  xco         = point.getX();
        double  yco         = point.getY();
        this.pixel = new Point2D.Double( xco, yco );
    }
    
    /**
     * Get a unique path for a data file in which to save
     * the encapsulated data.
     * The path includes the subdirectory of the resources
     * directory (but not the resources directory itself)
     * in which the file is to reside.
     * The file name includes a unique string 
     * that identifies the encapsulated HexLayout strategy
     * and offset.
     * 
     * @return  a unique path for a data file in which to save
     *          the encapsulated data
     */
    public String getTestFilePath()
    {
        String          fmt         = "_%s_%+04d_%+04d";
        String          uniquePart  = 
            String.format( fmt, layout, offset.col, offset.row );
        StringBuilder   bldr    = new StringBuilder();
        bldr.append( TestConstants.CVT_DATA_DIR )
            .append( TestConstants.CVT_DATA_FILE_PREFIX )
            .append( uniquePart )
            .append( TestConstants.CVT_DATA_FILE_SUFFIX );
        return bldr.toString();
    }
    
    /**
     * Checks for internal consistency.
     * For example, 
     * if offsetCoordinates1 converts to axialCoordinates1,
     * the axialCoordinates1 must convert to offsetCoordinates1.
     */
    public void consistencyTest()
    {
        Hex     generatedAxial  = layout.toHex( offset );
        assertTrue( equals( generatedAxial ) );
        
        Offset  generatedOffset = layout.toOffset( axial );
        assertTrue( equals( generatedOffset ) );
        
        Point2D generatedPixel  = layout.cvtHexToPixel( axial, side );
        assertTrue( equals( generatedPixel ) );
    }
    
    /**
     * Compare for equality generated axial coordinates
     * and stored axial coordinates.
     * 
     * @param generatedAxial   the generated axial coordinates
     *  
     * @return  true if the given coordinates are 
     *          equal to the stored coordinates
     */
    public boolean equals( Hex generatedAxial )
    {
        boolean result  = axial.equals( generatedAxial );
        return result;
    }
    
    /**
     * Compare for equality generated offset coordinates
     * and stored offset coordinates.
     * 
     * @param generatedOffset   the generated offset coordinates
     *  
     * @return  true if the given coordinates are 
     *          equal to the stored coordinates
     */
    public boolean equals( Offset generatedOffset )
    {
        boolean result  = offset.equals( generatedOffset );
        return result;
    }
    
    /**
     * Compare for equality generated pixel coordinates
     * and stored pixel coordinates.
     * The precision of the comparison is two decimal digits.
     * 
     * @param generatedPixel    the generated pixel coordinates
     * 
     * @return  true if the given pixel coordinates are equal to
     *          the stored coordinates 
     *          within a precision of .01.
     */
    public boolean equals( Point2D generatedPixel )
    {
        final double epsilon    = .01;
        double  xco             = pixel.x;
        double  yco             = pixel.y;
        double  generatedXco    = generatedPixel.getX();
        double  generatedYco    = generatedPixel.getY();
        boolean result  = 
            Math.abs( xco - generatedXco ) < epsilon
                && Math.abs( yco - generatedYco ) < epsilon;
        return result;
    }
}
