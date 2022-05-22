package com.gmail.johnstraub1954.cell_automata.main;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Point;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

class CellTest
{
    @Test
    void testCell()
    {
        Cell    cell    = new Cell();
        assertEquals( new Point( 0, 0 ), cell.getPoint() );
        assertEquals( 0, cell.getXco() );
        assertEquals( 0, cell.getYco() );
        assertFalse( cell.isAlive() );
    }

    @Test
    void testCellIntInt()
    {
        int     xco     = -10;
        int     yco     = -20;
        Point   point   = new Point( xco, yco );
        Cell    cell    = new Cell( xco, yco );

        assertEquals( point, cell.getPoint() );
        assertEquals( xco, cell.getXco() );
        assertEquals( yco, cell.getYco() );
        assertFalse( cell.isAlive() );
    }

    @Test
    void testCellPoint()
    {
        int     xco     = -10;
        int     yco     = -20;
        Point   point   = new Point( xco, yco );
        Cell    cell    = new Cell( point );

        assertEquals( point, cell.getPoint() );
        assertEquals( xco, cell.getXco() );
        assertEquals( yco, cell.getYco() );
        assertFalse( cell.isAlive() );
    }

    @ParameterizedTest
    @ValueSource( booleans = { true, false } )
    void testCellPointBoolean( boolean val )
    {
        int     xco     = -10;
        int     yco     = -20;
        Point   point   = new Point( xco, yco );
        Cell    cell    = new Cell( point, val );

        assertEquals( point, cell.getPoint() );
        assertEquals( xco, cell.getXco() );
        assertEquals( yco, cell.getYco() );
        assertEquals( val, cell.isAlive() );
    }

    @ParameterizedTest
    @ValueSource( booleans = { true, false } )
    void testCellCell( boolean val )
    {
        int     xco     = -10;
        int     yco     = -20;
        Point   point   = new Point( xco, yco );
        Cell    source  = new Cell( point, val );
        Cell    copy    = new Cell( source );

        assertEquals( point, copy.getPoint() );
        assertEquals( xco, copy.getXco() );
        assertEquals( yco, copy.getYco() );
        assertEquals( val, copy.isAlive() );
    }

    @ParameterizedTest
    @ValueSource( booleans = { true, false } )
    void testCellIntIntBoolean( boolean val )
    {
        int     xco     = -10;
        int     yco     = -20;
        Point   point   = new Point( xco, yco );
        Cell    cell    = new Cell( xco, yco, val );

        assertEquals( point, cell.getPoint() );
        assertEquals( xco, cell.getXco() );
        assertEquals( yco, cell.getYco() );
        assertEquals( val, cell.isAlive() );
    }

    @ParameterizedTest
    @EnumSource ( Direction.class )
    void testGetNeighbor( Direction dir )
    {
        GridMap map     = new GridMap();
        int     xco     = 10;
        int     yco     = 20;
        int     deltaX  = dir.getHorizontal();
        int     deltaY  = dir.getVertical();
        Cell    cell    = new Cell( xco, yco );
        
        int     expX    = xco + deltaX;
        int     expY    = yco + deltaY;
        Cell    test    = cell.getNeighbor( dir, map );
        assertEquals( expX, test.getXco() );
        assertEquals( expY, test.getYco() );
    }

    @ParameterizedTest
    @EnumSource ( Direction.class )
    void testGetRelativePoint( Direction dir )
    {
        int     xco     = 10;
        int     yco     = 20;
        int     deltaX  = dir.getHorizontal();
        int     deltaY  = dir.getVertical();
        Cell    cell    = new Cell( xco, yco );
        
        int     expX    = xco + deltaX;
        int     expY    = yco + deltaY;
        Point   test    = cell.getRelativePoint( dir );
        assertEquals( expX, test.x );
        assertEquals( expY, test.y );
    }

    @Test
    void testSetAlive()
    {
        Cell    cell    = new Cell( 0, 0, true );
        assertTrue( cell.isAlive() );
        
        cell.setAlive( false );
        assertFalse( cell.isAlive() );
        
        cell.setAlive( true );
        assertTrue( cell.isAlive() );
    }

    @Test
    void testGetXco()
    {
        int     xco     = 50;
        Cell    cell    = new Cell( xco, 0 );
        assertEquals( xco, cell.getXco() );
    }

    @Test
    void testGetYco()
    {
        int     yco     = 50;
        Cell    cell    = new Cell( 0, yco );
        assertEquals( yco, cell.getYco() );
    }

    @Test
    void testGetPoint()
    {
        int     xco     = 50;
        int     yco     = 100;
        Point   point   = new Point( xco, yco );
        Cell    cell    = new Cell( xco, yco );
        assertEquals( point, cell.getPoint() );
    }

    @ParameterizedTest
    @ValueSource( booleans = { true, false } )
    void testToString( boolean val )
    {
        int     xco     = 50;
        int     yco     = 100;
        Cell    cell    = new Cell( xco, yco, val );
        
        String  expX    = Integer.toString( xco );
        String  expY    = Integer.toString( yco );
        String  expVal  = 
            val ? "1" : "0";
        
        String  str     = cell.toString().toLowerCase();
        assertTrue( str.contains( expX ) );
        assertTrue( str.contains( expY ) );
        assertTrue( str.contains( expVal ) );
    }

    @ParameterizedTest
    @ValueSource( ints = { -10, -5, 0, 5, 10 } )
    void testSetGetState( int state )
    {
        Cell    cell    = new Cell();
        assertEquals( 0, cell.getState() );
        
        cell.setState( state );
        assertEquals( state, cell.getState() );
    }

    @ParameterizedTest
    @ValueSource( ints = { -10, -5, 0, 5, 10 } )
    void testToString( int val )
    {
        int     xco     = 50;
        int     yco     = 100;
        Cell    cell    = new Cell( xco, yco, val );
        
        String  expX    = Integer.toString( xco );
        String  expY    = Integer.toString( yco );
        String  expVal  = "" + val;
        
        String  str     = cell.toString().toLowerCase();
        assertTrue( str.contains( expX ) );
        assertTrue( str.contains( expY ) );
        assertTrue( str.contains( expVal ) );
    }

    @ParameterizedTest
    @ValueSource( booleans = { true, false } )
    void testEqualsObject( boolean val )
    {
        int     xco     = 5;
        int     yco     = 10;
        Point   point1  = new Point( xco, yco );
        Point   point2  = new Point( xco + 5, yco );
        
        Cell    cell1   = new Cell( point1, val );
        assertFalse( cell1.equals( null ) );
        assertFalse( cell1.equals( new Object() ) );
        assertTrue( cell1.equals( cell1 ) );
        assertEquals( cell1.hashCode(), cell1.hashCode() );
        
        Cell    cell2   = new Cell( point1, val );
        assertEquals( cell1, cell2 );
        assertEquals( cell2, cell1 );
        assertEquals( cell1.hashCode(), cell2.hashCode() );
        
        cell2.setAlive( !val );
        assertNotEquals( cell1, cell2 );
        assertNotEquals( cell2, cell1 );
        cell2.setAlive( val );
        assertEquals( cell1, cell2 );
        assertEquals( cell1.hashCode(), cell2.hashCode() );
        
        cell2 = new Cell( point2, val );
        assertNotEquals( cell1, cell2 );
        assertNotEquals( cell2, cell1 );
    }
}
