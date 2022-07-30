package com.gmail.johnstraub1954.cell_automata.geometry;

import static org.junit.jupiter.api.Assertions.fail;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

import org.junit.jupiter.api.Test;

class UnionTest
{
    @Test
    public void testGetRowColDimension()
    {
        final Runtime   runtime = Runtime.getRuntime();
        final String    fmt     = "%d, %d, %3.1f, %d%n";
        for ( int height = 100 ; height < 5000 ; height += 250 )
            for ( int width = 100 ; width < 5000 ; width += 250 )
                for ( double side = 10 ; side < 30 ; side += 5.7 )
                {
                    double  millis  = System.currentTimeMillis();
                    testGetRowColDimension( 500, 500 );
                    double  diff    = (System.currentTimeMillis() - millis) / 1000;
                    long    mem     = runtime.totalMemory();
                    System.out.printf( fmt, height, width, side, mem );
                    System.out.printf( "%4.3f seconds%n", diff );
                    System.out.println( "*****" );
                }
    }
    
    private void 
    testGetRowColDimension( int rWidth, int rHeight )
    {
        Rectangle       rect        = new Rectangle( 0, 0, rWidth, rHeight );
        Dimension       dimIn       = new Dimension( rWidth, rHeight );
        Dimension       dimOut      = new Dimension( rWidth / 10, rHeight / 10 );
        Path2D          union       = new Path2D.Double();
        
        // form union of all paths that are supposed to tile
        // the above rectangle
        for ( int col = 0 ; col < dimOut.width ; ++col )
            for ( int row = 0 ; row < dimOut.height ; ++ row )
            {
                Point2D center      = new Point2D.Double( col, row );
                Path2D  nextTile    = nextHexagon( center );
                union.append( nextTile, false );
            }
        
        // verify that the rectangle is fully tiled
        //assertTrue( union.contains( rect ) );
        int inside  = 0;
        for ( int row = 1 ; row < rect.height ; ++row )
            for ( int col = 1 ; col < rect.width ; ++ col )
            {
                Point   point   = new Point( col, row );
                if ( union.contains( point ) )
                    ++inside;
            }
         
    }

    private Path2D nextHexagon( Point2D center ) 
    {
        Path2D  path        = new Path2D.Double();
        double  angle       = 0;
        double  angleInc    = Math.PI / 3;
        double  xco         = 20 * Math.cos( angle );
        double  yco         = 20 * Math.sin( angle );
        path.moveTo( xco, yco );
        for ( angle += angleInc ; angle < 2 * Math.PI ; angle += angleInc )
        {
            xco         = 20 * Math.cos( angle );
            yco         = 20 * Math.sin( angle );
            path.lineTo( xco, yco );
        }
        return path;
    }
}