package app;

import com.gmail.johnstraub1954.cell_automata.main.SquareTile;

public class Test
{
    public static void main (String[] args) 
    {
       double   hypot       = 8;
       double   side        = 4;
       double   hypotSq     = hypot * hypot;
       double   sideSq      = side * side;
       double   heightSq    = hypotSq - sideSq;
       double   height      = Math.sqrt( heightSq );
       System.out.println( height );
       
       height = 8 * Math.sin( Math.PI / 3 );
       System.out.println( height );
    }
}
