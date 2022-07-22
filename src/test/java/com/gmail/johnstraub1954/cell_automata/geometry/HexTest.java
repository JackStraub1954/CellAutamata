package com.gmail.johnstraub1954.cell_automata.geometry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import test_util.CvtTestData;
import test_util.TestUtils;


class HexTest
{
    private static List<CvtTestData>    testData;
    
    @BeforeAll
    public static void beforeAll()
    {
        testData = TestUtils.getConversionTestData();
    }
    
    @Test
    void testHashCode()
    {
        int qco = 5;
        int rco = qco + 1;
        Hex hexOne  = new Hex( qco, rco );
        Hex hexTwo  = new Hex( qco, rco );
        assertEquals( hexOne.hashCode(), hexTwo.hashCode() );
    }

    @Test
    void testHexIntInt()
    {
        int expQco  = 5;
        int expRco  = expQco + 1;
        Hex hex     = new Hex( expQco, expRco );
        assertEquals( expQco, hex.qco );
        assertEquals( expRco, hex.rco );
    }

    @Test
    void testHexHex()
    {
        int expQco  = 5;
        int expRco  = expQco + 1;
        Hex hex     = new Hex( expQco, expRco );
        Hex copy    = new Hex( hex );
        assertEquals( expQco, copy.qco );
        assertEquals( expRco, copy.rco );
    }

    @Test
    void testToString()
    {
        int     expQco  = 5;
        int     expRco  = expQco + 1;
        Hex     hex     = new Hex( expQco, expRco );
        String  str     = hex.toString().trim().toLowerCase();
        String  expQStr = "qco=" + expQco;
        String  expRStr = "rco=" + expRco;
        assertTrue( str.contains( expQStr ) );
        assertTrue( str.contains( expRStr ) );
    }

    @Test
    void testGetQco()
    {
        int expQco  = 5;
        int expRco  = expQco + 1;
        Hex hex     = new Hex( expQco, expRco );
        assertEquals( expQco, hex.getQco() );
    }

    @Test
    void testGetRco()
    {
        int expQco  = 5;
        int expRco  = expQco + 1;
        Hex hex     = new Hex( expQco, expRco );
        assertEquals( expRco, hex.getRco() );
    }

    @Test
    void testGetSco()
    {
        int expQco  = 5;
        int expRco  = expQco + 1;
        int expSco  = -expQco - expRco;
        Hex hex     = new Hex( expQco, expRco );
        assertEquals( expSco, hex.getSco() );
    }

    @Test
    void testAxialToOddRHex()
    {
        testData.stream()
            .filter( d -> d.layout == HexLayout.ODD_R )
            .forEach(d -> {
               Offset   offset  = Hex.axialToOddR( d.axial );
               assertTrue( d.equals( offset ) );
            });
    }

    @Test
    void testAxialToOddRIntInt()
    {
        testData.stream()
        .filter( d -> d.layout == HexLayout.ODD_R )
        .forEach(d -> {
           int      qco     = d.axial.qco;
           int      rco     = d.axial.rco;
           Offset   offset  = Hex.axialToOddR( qco, rco );
           assertTrue( d.equals( offset ) );
        });
    }

    @Test
    void testAxialToEvenRHex()
    {
        testData.stream()
            .filter( d -> d.layout == HexLayout.EVEN_R )
            .forEach(d -> {
               Offset   offset  = Hex.axialToEvenR( d.axial );
               assertTrue( d.equals( offset ) );
            });
    }

    @Test
    void testAxialToEvenRIntInt()
    {
        testData.stream()
        .filter( d -> d.layout == HexLayout.EVEN_R )
        .forEach(d -> {
           int      qco     = d.axial.qco;
           int      rco     = d.axial.rco;
           Offset   offset  = Hex.axialToEvenR( qco, rco );
           assertTrue( d.equals( offset ) );
        });
    }

    @Test
    void testAxialToOddQHex()
    {
        testData.stream()
            .filter( d -> d.layout == HexLayout.ODD_Q )
            .forEach(d -> {
               Offset   offset  = Hex.axialToOddQ( d.axial );
               assertTrue( d.equals( offset ) );
        });
    }

    @Test
    void testAxialToOddQIntInt()
    {
        testData.stream()
        .filter( d -> d.layout == HexLayout.ODD_Q )
        .forEach(d -> {
           int      qco     = d.axial.qco;
           int      rco     = d.axial.rco;
           Offset   offset  = Hex.axialToOddQ( qco, rco );
           assertTrue( d.equals( offset ) );
        });
    }

    @Test
    void testAxialToEvenQHex()
    {
        testData.stream()
            .filter( d -> d.layout == HexLayout.EVEN_Q )
            .forEach(d -> {
               Offset   offset  = Hex.axialToEvenQ( d.axial );
               assertTrue( d.equals( offset ) );
        });
    }

    @Test
    void testAxialToEvenQIntInt()
    {
        testData.stream()
        .filter( d -> d.layout == HexLayout.EVEN_Q )
        .forEach(d -> {
           int      qco     = d.axial.qco;
           int      rco     = d.axial.rco;
           Offset   offset  = Hex.axialToEvenQ( qco, rco );
           assertTrue( d.equals( offset ) );
        });
    }

    @Test
    void testOddQToAxialOffset()
    {
        testData.stream()
        .filter( d -> d.layout == HexLayout.ODD_Q )
        .forEach(d -> {
           Hex hex  = Hex.oddQToAxial( d.offset );
           assertTrue( d.equals( hex ) );
        });
    }

    @Test
    void testOddQToAxialIntInt()
    {
        testData.stream()
        .filter( d -> d.layout == HexLayout.ODD_Q )
        .forEach(d -> {
           int col  = d.offset.col;
           int row  = d.offset.row;
           Hex hex  = Hex.oddQToAxial( col, row );
           assertTrue( d.equals( hex ) );
        });
    }

    @Test
    void testEvenQToAxialOffset()
    {
        testData.stream()
        .filter( d -> d.layout == HexLayout.EVEN_Q )
        .forEach(d -> {
           Hex hex  = Hex.evenQToAxial( d.offset );
           assertTrue( d.equals( hex ) );
        });
    }

    @Test
    void testEvenQToAxialIntInt()
    {
        testData.stream()
        .filter( d -> d.layout == HexLayout.EVEN_Q )
        .forEach(d -> {
           int col  = d.offset.col;
           int row  = d.offset.row;
           Hex hex  = Hex.evenQToAxial( col, row );
           assertTrue( d.equals( hex ) );
        });
    }

    @Test
    void testEvenRToAxialOffset()
    {
        testData.stream()
        .filter( d -> d.layout == HexLayout.EVEN_R )
        .forEach(d -> {
           Hex hex  = Hex.evenRToAxial( d.offset );
           assertTrue( d.equals( hex ) );
        });
    }

    @Test
    void testEvenRToAxialIntInt()
    {
        testData.stream()
        .filter( d -> d.layout == HexLayout.EVEN_R )
        .forEach(d -> {
           int col  = d.offset.col;
           int row  = d.offset.row;
           Hex hex  = Hex.evenRToAxial( col, row );
           assertTrue( d.equals( hex ) );
        });
    }

    @Test
    void testOddRToAxialPoint()
    {
        testData.stream()
        .filter( d -> d.layout == HexLayout.ODD_R )
        .forEach(d -> {
           Hex hex  = Hex.oddRToAxial( d.offset );
           assertTrue( d.equals( hex ) );
        });
    }

    @Test
    void testOddRToAxialIntInt()
    {
        testData.stream()
        .filter( d -> d.layout == HexLayout.ODD_R )
        .forEach(d -> {
           int col  = d.offset.col;
           int row  = d.offset.row;
           Hex hex  = Hex.oddRToAxial( col, row );
           assertTrue( d.equals( hex ) );
        });
    }
    
    @Test
    public void testAddHex()
    {
    	int	qco1	= -5;
    	int rco1	= qco1 + 2;
    	Hex	hex1	= new Hex( qco1, rco1 );
    	int qco2	= 2 * qco1;
    	int rco2	= 2 * rco1;
    	Hex	hex2	= new Hex( qco2, rco2 );
    	int	expQco	= qco1 + qco2;
    	int	expRco	= rco1 + rco2;
    	Hex	expHex	= new Hex( expQco, expRco );
    	Hex actHex	= hex1.add( hex2 );
    	assertEquals( expHex, actHex );
    }

    @Test
    void testEqualsObject()
    {
        int qcoOne  = 5;
        int rcoOne  = qcoOne + 1;
        int qcoTwo  = qcoOne * 10;
        int rcoTwo  = rcoOne * 10;
        Hex hexOne  = new Hex( qcoOne, rcoOne );
        Hex hexTwo  = null;
        
        assertNotEquals( hexOne, null );
        assertNotEquals( hexOne, new Object() );
        assertEquals( hexOne, hexOne );
        assertEquals( hexOne.hashCode(), hexOne.hashCode() );
        
        hexTwo = new Hex( qcoOne, rcoTwo );
        assertNotEquals( hexOne, hexTwo );
        hexTwo = new Hex( qcoTwo, rcoOne );
        assertNotEquals( hexOne, hexTwo );
        hexTwo = new Hex( qcoOne, rcoOne );
        assertEquals( hexOne, hexTwo );
        assertEquals( hexTwo, hexOne );
        assertEquals( hexOne.hashCode(), hexTwo.hashCode() );
    }

}
