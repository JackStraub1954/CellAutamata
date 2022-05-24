package com.gmail.johnstraub1954.cell_automata.main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Translates a grid map into a run-length encoded character stream.
 * The stream consists of one or more lines of character data,
 * each no more than 70 characters in length.
 * 
 * @author Jack Straub
 *
 */
public class RLEGridEncoder
{
    private static final int    MAX_LINE_LEN   = 70;
    
    private final Iterator<Character>   iter;
    private final List<String>          lines       = new ArrayList<>();
    private final StringBuilder         currLine    = new StringBuilder();
    private int                         currCount   = 0;
    private char                        currChar    = ' ';
    
    public RLEGridEncoder( RLEDescriptor descrip )
    {
        iter  = descrip.iterator();
    }
    
    public void encode() throws IllegalStateException
    {
        // The smallest iteration string will never be less than
        // a single character (!).
        if ( !iter.hasNext() )
        {
            String  message =
                "Unexpected 0-length iteration";
            throw new IllegalStateException( message );
        }
        currChar = iter.next();
        currCount = 1;
            
        while ( iter.hasNext() )
        {
            char    nextChar    = iter.next();
            if ( nextChar == currChar )
                ++currCount;
            else if ( currCount > 0 )
            {
                appendToCurrLine();
                currCount = 1;
                currChar = nextChar;
            }
        }
        
        appendToCurrLine();
        if ( currLine.length() > 0 )
            lines.add( currLine.toString() );
    }
    
    public List<String> getLines()
    {
        return lines;
    }
    
    private void appendToCurrLine()
    {
        if ( currCount > 1 )
            currLine.append( currCount );
        currLine.append( currChar );
        if ( currLine.length() >= MAX_LINE_LEN )
        {
            int testPos = MAX_LINE_LEN;
            // Don't break line in middle of count
            while ( Character.isDigit( currLine.charAt( testPos - 1 ) ) )
                --testPos;
            lines.add( currLine.substring( 0, testPos ) );
            currLine.delete( 0, testPos );
        }
    }
}
