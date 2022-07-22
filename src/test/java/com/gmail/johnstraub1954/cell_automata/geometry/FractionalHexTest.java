package com.gmail.johnstraub1954.cell_automata.geometry;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FractionalHexTest
{

    @Test
    void testFractionalHex()
    {
        double          qco = 3.14;
        double          rco = 2 * qco;
        FractionalHex   hex = new FractionalHex( qco, rco );
        assertEquals( qco, hex.dQco );
        assertEquals( rco, hex.dRco );
    }

    /**
     * Test qDiff &gt; rDiff &amp;&amp; qDiff &gt; sDiff
     */
    @Test
    void testRound1()
    {
        double  dQco    = 5.5;
        double  dRco    = 5.1;
        double  dSco    = -dQco - dRco;
        
        int qco = (int)Math.round( dQco );
        int rco = (int)Math.round( dRco );
        int sco = (int)Math.round( dSco );
        
        double qDiff    = Math.abs( qco - dQco );
        double rDiff    = Math.abs( rco - dRco );
        double sDiff    = Math.abs( sco - dSco );
        
//        String  fmt = "qd = %.2f, rd = %.2f, sDiff = %.2f%n";
//        System.out.printf( fmt,  qDiff, rDiff, sDiff );
        
        assertTrue( qDiff > rDiff );
        assertTrue( qDiff > sDiff );
        
        qco = -rco - sco;
        Hex             expHex  = new Hex( qco, rco );
        FractionalHex   fracHex = new FractionalHex( dQco, dRco );
        Hex             actHex  = fracHex.round();
        assertEquals( expHex, actHex );
    }

    /**
     * Test !(qDiff &gt; rDiff) &amp;&amp; rDiff &gt; sDiff
     */
    @Test
    void testRound2()
    {
        double  dQco    = 5.1;
        double  dRco    = 5.5;
        double  dSco    = -dQco - dRco;
        
        int qco = (int)Math.round( dQco );
        int rco = (int)Math.round( dRco );
        int sco = (int)Math.round( dSco );
        
        double qDiff    = Math.abs( qco - dQco );
        double rDiff    = Math.abs( rco - dRco );
        double sDiff    = Math.abs( sco - dSco );
        
//        String  fmt = "qd = %.2f, rd = %.2f, sDiff = %.2f%n";
//        System.out.printf( fmt,  qDiff, rDiff, sDiff );
        
        assertTrue( qDiff <= rDiff );
        assertTrue( rDiff > sDiff );
        
        rco = -qco - sco;
        Hex             expHex  = new Hex( qco, rco );
        FractionalHex   fracHex = new FractionalHex( dQco, dRco );
        Hex             actHex  = fracHex.round();
        assertEquals( expHex, actHex );
    }

    /**
     * Test qDiff &gt; rDiff &amp;&amp; !(qDiff &gt; sDiff) &amp;&amp; rDiff &gt; sDiff
     * *** I don't think this is possible ***
     */
    //@Test
    void testRound3()
    {
        double  dQco    = 4.1;
        double  dRco    = 4.3;
        double  dSco    = -dQco - dRco;
        
        int qco = (int)Math.round( dQco );
        int rco = (int)Math.round( dRco );
        int sco = (int)Math.round( dSco );
        
        double qDiff    = Math.abs( qco - dQco );
        double rDiff    = Math.abs( rco - dRco );
        double sDiff    = Math.abs( sco - dSco );
        
        String  fmt = "qd = %.2f, rd = %.2f, sDiff = %.2f%n";
        System.out.printf( fmt,  qDiff, rDiff, sDiff );
        
        assertFalse( qDiff > rDiff && qDiff > sDiff );
        assertTrue( qDiff > rDiff );
        assertFalse( qDiff > sDiff );
        assertTrue( rDiff > sDiff );
        
        rco = -qco - sco;
        Hex             expHex  = new Hex( qco, rco );
        FractionalHex   fracHex = new FractionalHex( dQco, dRco );
        Hex             actHex  = fracHex.round();
        assertEquals( expHex, actHex );
    }
}
