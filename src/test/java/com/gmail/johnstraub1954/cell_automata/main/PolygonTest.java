/**
 * 
 */
package com.gmail.johnstraub1954.cell_automata.main;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author johns
 *
 */
class PolygonTest
{
    /** To test for equality */
    private static final double epsilon = .0001;
    
    /**
     * Pre-calculated values for properties of a polygon
     */
    private static final PolygonProps[] polygonValues   =
    {
        new PolygonProps(
            5, // numSides
            5, // sideLen
            4.25325, // circumRadius
            3.44095 // apothem
        ),
        new PolygonProps( 3, 5, 2.88675, 1.44338  ),
        new PolygonProps( 6, 5, 5.00000, 4.33013  ),
    };
    
    /**
     * Test method for {@link com.gmail.johnstraub1954.cell_automata.main.Polygon#radius(double, int)}.
     */
    @Test
    void testRadiusOfSide()
    {
        for ( PolygonProps props : polygonValues )
        {
            double  radius  = Polygon.circumRadiusOfSide( props.numSides, props.sideLen );
            assertEquals( props.circumRadius, radius, epsilon );
        }
    }
    
    private static class PolygonProps
    {
        private final double    sideLen;
        private final double    circumRadius; // radius
        private final double    apothem; // inRadius
        private final double    intAngle;
        private final double    extAngle;
        private final int       numSides;
        
//        public static PolygonProps ofSide( double sideLen, int numSides )
//        {
//            
//        }
        
        public PolygonProps(
            int numSides,
            double sideLen, 
            double circumRadius,
            double apothem // inRadius
        )
        {
            this.numSides = numSides;
            this.sideLen = sideLen;
            this.circumRadius = circumRadius;
            this.apothem = apothem;
            this.extAngle = 2 * Math.PI / numSides;
            this.intAngle = (numSides - 2) * Math.PI / numSides;
        }
    }

}
