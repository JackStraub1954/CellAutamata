package com.gmail.johnstraub1954.cell_automata.geometry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.awt.geom.Point2D;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import Jama.Matrix;
import test_util.CvtTestData;
import test_util.TestUtils;

class HexLayoutTest
{
    private static List<CvtTestData>    testData;
    
    @BeforeAll
    public static void beforeAll()
    {
        testData = TestUtils.getConversionTestData();
    }
    
    @Test
    void testToHex()
    {
        for ( CvtTestData test : testData )
        {
            HexLayout   layout  = test.layout;
            Offset      offset  = test.offset;
            Hex         actHex  = layout.toHex( offset );
            assertTrue( test.equals( actHex ) );
        }
    }
    
    @Test
    void testToOffset()
    {
        for ( CvtTestData test : testData )
        {
            HexLayout   layout      = test.layout;
            Hex         hex         = test.axial;
            Offset      actOffset   = layout.toOffset( hex );
            assertTrue( test.equals( actOffset ) );
        }
    }
    
    @Test
    void testGetHexToPixelMultiplier()
    {
        for ( CvtTestData test : testData )
        {
            HexLayout   layout      = test.layout;
            Hex         hex         = test.axial;
            double[]    qrPair      = { hex.qco, hex.rco };
            Matrix      qrco        = new Matrix( qrPair, 2 );
            Matrix      xier        = 
                layout.getHexToPixelMultiplier( test.side );
            Matrix      xyco        = xier.times( qrco );
            double      xco         = xyco.get(0,  0 );
            double      yco         = xyco.get(1,  0 );
            Point2D     point       = new Point2D.Double( xco, yco );
            assertTrue( test.equals( point ) );
        }
    }
    
    @Test
    void testGetStartAngle()
    {
        // Horizontal (row-oriented) layouts have a start angle of 90 degrees.
        double  angle   = HexLayout.EVEN_R.getStartAngle();
        assertEquals( Math.PI / 2, angle, .01 );
        angle = HexLayout.ODD_R.getStartAngle();
        assertEquals( Math.PI / 2, angle, .01 );
        
        // Vertical (column-oriented) layouts 
        // have a start angle of 180 degrees.
        angle = HexLayout.EVEN_Q.getStartAngle();
        assertEquals( Math.PI, angle, .01 );
        angle = HexLayout.ODD_Q.getStartAngle();
        assertEquals( Math.PI, angle, .01 );
        
    }
    
    @Test
    void testCvtHexToPixel()
    {
        for ( CvtTestData test : testData )
        {
            double      side        = test.side;
            HexLayout   layout      = test.layout;
            Hex         hex         = test.axial;
            Point2D     actPixel    = layout.cvtHexToPixel( hex, side );
            assertTrue( test.equals( actPixel ) );
        }
    }
    
    
    /**
     * For a given HexLayout, 
     * validate the calculated matrix multiplier for translating
     * screen coordinates into axial coordinates.
     * <p>
     * The strategy used here is to generate a multiplier internally,
     * then compare its output to the multiplier obtained from the layout.
     * The disadvantage of this strategy is that the expected multiplier
     * is calculated using the same algorithm as the layout strategy.
     * However, if the HexLayout code is changed, this test will
     * at least verify that the modified code
     * works as well as the original.
     * And the accuracy of the algorithm 
     * has been verified extensively, elsewhere.
     * 
     * @param layout    the given HexLayout
     */
    @ParameterizedTest
    @EnumSource( HexLayout.class )
    public void testGetPixelToHexXier( HexLayout layout )
    {
        double  side        = 25.3;
        Matrix  expXier     = getPixelToHexXier( layout, side );
        Matrix  actXier     = layout.getPixelToHexMultiplier( side ); 
        for ( double yco = -50 ; yco < 50 ; yco += .1 )
            for ( double xco = -50 ; xco < 50 ; xco += .1 )
            {
                Hex expHex  = cvtPixelToHex( expXier, xco, yco );
                Hex actHex  = cvtPixelToHex( actXier, xco, yco );
                assertEquals( expHex, actHex );
            }
    }
    
    private Hex cvtPixelToHex( Matrix xier, double xco, double yco )
    {
        Matrix          xyco    = new Matrix( new double[] { xco, yco }, 2 );
        Matrix          qrco    = xier.times( xyco );
        double          dQco    = qrco.get( 0, 0 );
        double          dRco    = qrco.get( 1, 0 );
        FractionalHex   fHex    = new FractionalHex( dQco, dRco );
        Hex             hex     = fHex.round();
        
        return hex;
    }
    
    /**
     * For a given HexLayout, get the matrix multiplier suitable
     * for calculating a Hex coordinate from a screen coordinate.
     * 
     * @param layout    the given HexLayout
     * @param side      the length of a side for the target hexagon
     * 
     * @return  matrix multiplier suitable
     *          for calculating a Hex coordinate 
     *          from a screen coordinate
     */
    private static Matrix getPixelToHexXier( HexLayout layout, double side )
    {
        Matrix  xier    = null;
        switch ( layout )
        {
        case ODD_R:
        case EVEN_R:
            xier = getPixelToHexXierH( side );
            break;
        case ODD_Q:
        case EVEN_Q:
            xier = getPixelToHexXierV( side );
            break;
        default:
            fail( "unexpected layout value: " + layout );
        }
        return xier;
    }
    
    /**
     * Compute the matrix to use
     * to convert axial coordinates to screen coordinates
     * for a horizontal hexagon
     * with a given side length.
     * 
     * @param side  the given side length
     * 
     * @return  a matrix for converting axial coordinates
     *          to screen coordinates
     *          for a horizontal hexagon
     */
    private static Matrix getPixelToHexXierH( double side )
    {
        final double[][]    xierCons    =
        {
            { Math.sqrt( 3 )/3, -1./3. },
            { 0.,                2./3. }
        };
        Matrix  consMatrix  = new Matrix( xierCons );
        Matrix  xierMatrix  = consMatrix.times( 1. / side );
        
        return xierMatrix;
    }
    
    /**
     * Compute the matrix to use
     * to convert axial coordinates to screen coordinates
     * for a vertical hexagon
     * with a given side length.
     * 
     * @param side  the given side length
     * 
     * @return  a matrix for converting axial coordinates
     *          to screen coordinates
     *          for a vertical hexagon
     */
    private static Matrix getPixelToHexXierV( double side )
    {
        final double[][]    xierCons    =
        {
            { 2./3.,                    0 },
            { -1./3.,  Math.sqrt( 3 ) / 3 }
        };
        Matrix  consMatrix  = new Matrix( xierCons );
        Matrix  xierMatrix  = consMatrix.times( 1. / side );
        
        return xierMatrix;
    }
}
