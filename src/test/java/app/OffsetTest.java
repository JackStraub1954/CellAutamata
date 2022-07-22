package app;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;

import org.junit.jupiter.api.Test;

import com.gmail.johnstraub1954.cell_automata.geometry.Offset;

class OffsetTest
{
    @Test
    void testOffsetIntInt()
    {
        int     col     = 25;
        int     row     = -50;
        Offset  offset  = new Offset( col, row );
        assertEquals( col, offset.getCol() );
        assertEquals( row, offset.getRow() );
    }

    @Test
    void testOffsetOffset()
    {
        int     col     = 25;
        int     row     = -50;
        Offset  offset1 = new Offset( col, row );
        Offset  offset2 = new Offset( offset1 );
        assertEquals( col, offset2.getCol() );
        assertEquals( row, offset2.getRow() );
    }

    @Test
    void testOffsetPoint()
    {
        int     xco     = 25;
        int     yco     = -50;
        Point   point   = new Point( xco, yco );
        Offset  offset  = new Offset( point );
        assertEquals( xco, offset.getCol() );
        assertEquals( yco, offset.getRow() );
    }

    @Test
    void testGetCol()
    {
        int     col     = 25;
        int     row     = -50;
        Offset  offset  = new Offset( col, row );
        assertEquals( col, offset.getCol() );
    }

    @Test
    void testGetRow()
    {
        int     col     = 25;
        int     row     = -50;
        Offset  offset  = new Offset( col, row );
        assertEquals( row, offset.getRow() );
    }

    @Test
    void testEqualsObject()
    {
        int     col1    = 25;
        int     col2    = 2 * col1;
        int     row1    = col1 + 10;
        int     row2    = row1 + 10;
        Offset  offsetA = new Offset( col1, row1 );
        Offset  offsetB = null;
        
        assertNotEquals( offsetA, null );
        assertNotEquals( offsetA, new Object() );
        assertEquals( offsetA, offsetA );
        assertEquals( offsetA.hashCode(), offsetA.hashCode() );
        
        offsetB = new Offset( col2, row2 );
        assertNotEquals( offsetA, offsetB );
        assertNotEquals( offsetB, offsetA );
        
        offsetB = new Offset( col1, row2 );
        assertNotEquals( offsetA, offsetB );
        assertNotEquals( offsetB, offsetA );
        
        offsetB = new Offset( col2, row1 );
        assertNotEquals( offsetA, offsetB );
        assertNotEquals( offsetB, offsetA );
        
        offsetB = new Offset( col1, row1 );
        assertEquals( offsetA, offsetB );
        assertEquals( offsetB, offsetA );
        assertEquals( offsetA.hashCode(), offsetB.hashCode() );
    }
}
