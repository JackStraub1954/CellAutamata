package com.gmail.johnstraub1954.cell_automata.geometry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.awt.geom.Path2D;
import java.util.List;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

import test_util.PolygonDescriptor;
import test_util.TestUtils;

class PolygonTest
{

    @Test
    void testOfRadius()
    {
        TestData[]  testData    =  // "other" not used
        {
            new TestData( 5, 10., 0 ),
            new TestData( 10, 5., 0 ),
            new TestData( 3, 4.2, 0 )
        };

        for ( TestData data : testData )
        {
            Polygon poly    = Polygon.ofRadius( data.numSides, data.radius );
            data.test( poly, () -> 0., "ofRadius" );
        }
    }

    @Test
    void testOfSide()
    {
        TestData[]  testData    =  
        {
            new TestData( 5, 8.50651, 10. ),
            new TestData( 10, 8.09017, 5. ),
            new TestData( 3, 2.42487, 4.2 )
        };

        for ( TestData data : testData )
        {
            Polygon poly    = Polygon.ofSide( data.numSides, data.other );
            data.test( poly, () -> poly.getSideLen(), "ofSide" );
        }
    }

    @Test
    void testOfApothem()
    {
        TestData[]  testData    =  
        {
            new TestData( 5, 12.3607, 10. ),
            new TestData( 10, 5.25731, 5. ),
            new TestData( 3, 8.4, 4.2 )
        };

        for ( TestData data : testData )
        {
            Polygon poly    = Polygon.ofApothem( data.numSides, data.other );
            data.test( poly, () -> poly.getApothem(), "ofApothem" );
        }
    }
//
//    @Test
//    void testGetNumSides()
//    {
//        int     numSides    = 10;
//        Polygon poly    = Polygon.ofSide( numSides, 10 );
//        assertEquals( numSides, poly.getNumSides() );
//    }
//
//    @Test
//    void testGetRadius()
//    {
//        double  numSides    = 5;
//        double  apothem     = 10;
//        double  sideLen     = 14.5309;
//        double  expRadius
//    }

    @Test
    void testGetSideLen()
    {
        TestData[]  testData    =  
        {
            new TestData( 5, 10, 11.7557 ),
            new TestData( 10, 5, 3.09017 ),
            new TestData( 3, 4.2, 7.27461 )
        };

        for ( TestData data : testData )
        {
            Polygon poly    = Polygon.ofRadius( data.numSides, data.radius );
            data.test( poly, () -> poly.getSideLen(), "getSideLen" );
        }
    }

    @Test
    void testGetApothem()
    {
        TestData[]  testData    =  
        {
            new TestData( 5, 10, 8.09017 ),
            new TestData( 10, 5, 4.75528 ),
            new TestData( 3, 4.2, 2.1 )
        };

        for ( TestData data : testData )
        {
            Polygon poly    = Polygon.ofRadius( data.numSides, data.radius );
            data.test( poly, () -> poly.getApothem(), "getApothem" );
        }
    }

    @Test
    void testGetIntAngle()
    {
        TestData[]  testData    =  // radius not used
        {
            new TestData( 5, 10, degreesToRadians( 108 ) ),
            new TestData( 10, 5, degreesToRadians( 144 ) ),
            new TestData( 3, 4.2, degreesToRadians( 60 ) )
        };

        for ( TestData data : testData )
        {
            Polygon poly    = Polygon.ofRadius( data.numSides, data.radius );
            data.test( poly, () -> poly.getIntAngle(), "getIntAngle" );
        }
    }

    @Test
    void testGetExtAngle()
    {
        TestData[]  testData    =  // radius not used
        {
            new TestData( 5, 10, degreesToRadians( 72 ) ),
            new TestData( 10, 5, degreesToRadians( 36 ) ),
            new TestData( 3, 4.2, degreesToRadians( 120 ) )
        };

        for ( TestData data : testData )
        {
            Polygon poly    = Polygon.ofRadius( data.numSides, data.radius );
            data.test( poly, () -> poly.getExtAngle(), "getExtAngle" );
        }
    }

    @Test
    void testGetArea()
    {
        TestData[]  testData    =
        {
            new TestData( 5, 10, 237.764 ),
            new TestData( 10, 5, 73.4732 ),
            new TestData( 3, 4.2, 22.915 )
        };

        for ( TestData data : testData )
        {
            Polygon poly    = Polygon.ofRadius( data.numSides, data.radius );
            data.test( poly, () -> poly.getArea(), "getArea" );
        }
    }

    @Test
    void testGetPerimeter()
    {
        TestData[]  testData    =
        {
            new TestData( 5, 10, 58.7785 ),
            new TestData( 10, 5, 30.9017 ),
            new TestData( 3, 4.2, 21.8238 )
        };

        for ( TestData data : testData )
        {
            Polygon poly    = Polygon.ofRadius( data.numSides, data.radius );
            data.test( poly, () -> poly.getPerimeter(), "getPerimeter" );
        }
    }
    
    @Test
    public void testGetPathPoint2DDouble()
    {
    	List<PolygonDescriptor>	descrips	= TestUtils.getPolygonTestData();
    	for ( PolygonDescriptor descrip : descrips )
    	{
    		int		numSides	= descrip.polygon.getNumSides();
    		double	sideLen		= descrip.polygon.getSideLen();
    		Polygon	poly		= Polygon.ofSide( numSides, sideLen );
    		Path2D	actPath		= descrip.getActualPath( poly );
    		assertTrue( descrip.equals( actPath ) );
    	}
    }

    @Test
    void testGetVertices()
    {
    	fail( "not implemented" );
    	List<PolygonDescriptor>	descrips	= TestUtils.getPolygonTestData();
    	for ( PolygonDescriptor descrip : descrips )
    	{
    		int		numSides	= descrip.polygon.getNumSides();
    		double	sideLen		= descrip.polygon.getSideLen();
    		Polygon	poly		= Polygon.ofSide( numSides, sideLen );
    		Path2D	actPath		= descrip.getActualPath( poly );
    		assertTrue( descrip.equals( actPath ) );
    	}
    }
    
    private double degreesToRadians( double degrees )
    {
        double  radians = degrees * Math.PI / 180;
        return radians;
    }

    private static class TestData
    {
        private static final    double  eps = .001;
        private final int       numSides;
        private final double    radius;
        private final double    other;
        
        public TestData( int numSides, double radius, double other )
        {
            this.numSides = numSides;
            this.radius = radius;
            this.other = other;
        }
        
        public void test( 
            Polygon poly, 
            Supplier<Double> otherSupplier,
            String comment
        )
        {
            assertEquals( numSides, poly.getNumSides(), comment );
            assertEquals( radius, poly.getRadius(), eps, comment );
            assertEquals( other, otherSupplier.get(), eps, comment );
        }
    }
}
