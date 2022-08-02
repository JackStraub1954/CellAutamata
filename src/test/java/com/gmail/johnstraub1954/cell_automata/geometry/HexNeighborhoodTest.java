package com.gmail.johnstraub1954.cell_automata.geometry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;

import org.junit.jupiter.api.Test;

class HexNeighborhoodTest
{
    @Test
    void testHexNeighborhood()
    {
        Offset          self    = new Offset( 5, 10 );
        HexLayout       layout  = HexLayout.EVEN_Q;
        HexNeighborhood hood    = new HexNeighborhood( self, layout );
        assertEquals( self, hood.getSelf() );
        assertEquals( layout, hood.getLayout() );
    }

    @Test
    void testGetSelf()
    {
        Offset          self    = new Offset( 5, 10 );
        HexNeighborhood hood    = new HexNeighborhood( self, HexLayout.EVEN_Q );
        assertEquals( self, hood.getSelf() );
    }

    @Test
    void testGetLayout()
    {
        Offset          self    = new Offset( 5, 10 );
        HexLayout       layout  = HexLayout.EVEN_Q;
        HexNeighborhood hood    = new HexNeighborhood( self, layout );
        assertEquals( layout, hood.getLayout() );
    }

    @Test
    void testGetNeighbors()
    {
        // The "correctness" of the returned list is extensively tested elsewhere.
        // For testing the getter, just make sure a non-empty list is returned.
        Offset          self        = new Offset( 5, 10 );
        HexLayout       layout      = HexLayout.EVEN_Q;
        HexNeighborhood hood        = new HexNeighborhood( self, layout );
        List<Offset>    neighbors   = hood.getNeighbors();
        assertFalse( neighbors.isEmpty() );
    }

    @Test
    void testToString()
    {
        // just make sure a string is returned, and it contains self
        Offset          self        = new Offset( 5, 10 );
        String          selfStr     = self.toString();
        HexLayout       layout      = HexLayout.EVEN_Q;
        HexNeighborhood hood        = new HexNeighborhood( self, layout );
        String          str         = hood.toString();
        assertTrue( str.contains( selfStr ) );
    }

    @Test
    void testEqualsObject() throws IllegalAccessException
    {
        int             baseCol     = 19;
        int             baseRow     = baseCol + 1;
        int             testCol     = 2 * baseCol;
        int             testRow     = 2 * baseRow;
        HexLayout       layoutA     = HexLayout.EVEN_Q;
        HexLayout       layoutB     = HexLayout.ODD_Q;
        HexLayout       layoutC     = HexLayout.EVEN_R;
        HexLayout       layoutD     = HexLayout.ODD_R;
        Offset          selfA       = new Offset( baseCol, baseRow );
        Offset          selfB       = new Offset( testCol, testRow );
        
        HexNeighborhood hoodA       = new HexNeighborhood( selfA, layoutA );
        HexNeighborhood hoodB       = new HexNeighborhood( selfA, layoutA );
        
        assertNotEquals( hoodA, null );
        assertNotEquals( hoodA, new Object() );

        assertEquals( hoodA, hoodA );
        assertEquals( hoodA, hoodB );
        assertEquals( hoodB, hoodA );
        assertEquals( hoodA.hashCode(), hoodB.hashCode() );
        
        hoodB = new HexNeighborhood( selfB, layoutA );
        assertNotEquals( hoodA, hoodB );
        assertNotEquals( hoodB, hoodA );
        
        hoodB = new HexNeighborhood( selfA, layoutB );
        assertNotEquals( hoodA, hoodB );
        assertNotEquals( hoodB, hoodA );
        
        hoodB = new HexNeighborhood( selfA, layoutC );
        assertNotEquals( hoodA, hoodB );
        assertNotEquals( hoodB, hoodA );
        
        hoodB = new HexNeighborhood( selfA, layoutD );
        assertNotEquals( hoodA, hoodB );
        assertNotEquals( hoodB, hoodA );
        
        // get coverage of oddball conditions
        // (that will probably never be executed in production)...
        hoodA = new HexNeighborhood( selfA, layoutA );
        hoodB = new HexNeighborhood( selfA, layoutA );
        assertEquals( hoodA, hoodB );
        
        // find list of neighbors based on type of field
        // (so if name changes won't have to change test case)
        Field[] fields      = hoodA.getClass().getDeclaredFields();
        Field   listField   = null;
        for ( Field field : fields )
        {
            Type    type        = field.getGenericType();
            String  typeName    = type.getTypeName();
            if ( typeName.contains( "List" ) && typeName.contains( "Offset" ) )
            {
                field.setAccessible( true );
                listField = field;
                break;
            }
        }
        assertNotNull( listField );
        @SuppressWarnings("unchecked")
        List<Offset>    listA   = (List<Offset>)listField.get( hoodA );
        @SuppressWarnings("unchecked")
        List<Offset>    listB   = (List<Offset>)listField.get( hoodB );
        int             size    = listA.size();
        Offset          itemA   = listA.remove( size - 1);
        assertNotEquals( listA, listB );
        listA.add( listA.get( 0 ) );
        assertNotEquals( hoodA, hoodB );
        listA.remove( size - 1 );
        listA.add( itemA );
        assertEquals( hoodA, hoodB );
        
        listB.remove( size - 1 );
        assertNotEquals( hoodA, hoodB );
        listB.add( listB.get( 0 ) );
        assertNotEquals( hoodA, hoodB );
    }

}
