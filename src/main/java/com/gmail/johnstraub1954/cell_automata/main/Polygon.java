package com.gmail.johnstraub1954.cell_automata.main;

public class Polygon
{
    public static double circumRadiusOfSide( int numSides, double sideLen )
    {
        double  angle   = Math.PI / numSides;
        double  radius  = sideLen / 2 * Math.sin( angle );
        return radius;
    }
}
