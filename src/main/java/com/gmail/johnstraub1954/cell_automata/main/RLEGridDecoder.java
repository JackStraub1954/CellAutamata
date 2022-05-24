package com.gmail.johnstraub1954.cell_automata.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Decodes an input sequence consisting of count/character pairs.
 * For example: 3obo$3b!
 * A character not preceded by an explicit count
 * is assumed to have an implicit count of 1.
 * The input sequence may or may not consist of multiple lines.
 * 
 * Operations/services provided by the class are generally considered
 * integral parts of decoding a full formatted RLE-style description
 * of a Game of Life Pattern. It is broken into a separate class
 * in order to facilitate testing.
 * 
 * @author Jack Straub
 * 
 * @see <a href=https://conwaylife.com/wiki/Run_Length_Encoded>
 *          Run Length Encoded Files
 *      </a>
 */
public class RLEGridDecoder implements Iterable<Character>
{
    /**
     * Regular expression for parsing input.
     * It is non-greedy.
     * Some of the things it will match are:
     * <ul>
     * <li>o</li>
     * <li>b</li>
     * <li>$</li>
     * <li>!</li>
     * <li>5o</li>
     * <li>10b</li>
     * <li>3$</li>
     * </ul>
     * It contains two groups: one to match a numeric prefix
     * (if any) and the other to match the input character.
     */
    private static final String     regExp  = "(\\d*?)([$!\\D])";
    private static final Pattern    pattern = Pattern.compile( regExp );

    private final StringBuilder codes   = new StringBuilder();
    
    /**
     * Constructor.
     * Skips all blank lines and initial comments in the stream
     * indicated by a given stream reader.
     * Parses the input sequence contained in the stream
     * (lines consisting of exclusively count/character pairs,
     * such as 3o, 5b, b, o, $ or !).
     * The reader's cursor is left positioned at either
     * the first or second line after the end of the input sequence,
     * or at the end of the stream if there are no lines
     * after the input sequence.
     * 
     * @param reader    the given stream reader
     * 
     * @throws CAException if the input sequence could not be parsed
     */
    public RLEGridDecoder( BufferedReader reader ) throws CAException
    {
        try
        {
            // Eliminate blank INITIAL blank lines and comments
            String  line    = reader.readLine();
            while (
                line != null 
                && (line.isEmpty() || line.charAt( 0 ) == '#')
            )
                line = reader.readLine();
            
            // Get all the SIGNIFICANT lines
            // Stop at:
            // ... EOF
            // ... line containing !
            // ... blank line
            // ... comment
            boolean done    = false;
            int     bangInx = -1;
            while ( !done )
            {
                if ( line == null )
                    done = true;
                else if ( line.isEmpty() )
                    done = true;
                else if ( line.charAt( 0 ) == '#' )
                    done = true;
                else if ( (bangInx = line.indexOf(  '!' )) >= 0 )
                {
                    done = true;
                    String  fragment    = line.substring( 0, bangInx );
                    
                    // Add the start of the line ending with the !
                    codes.append( fragment );
                }
                else
                {
                    codes.append( line );
                    line = reader.readLine();
                }
            }
        }
        catch ( IOException exc )
        {
            throw new CAException( "I/O error", exc );
        }
        
        // Make sure the list of lines contains at least one line,
        // and that the last line ends with a !.
        int count   = codes.length();
        if ( count == 0 )
        {
            codes.append( "!" );
        }
        else
        {
            char    lastChar    = codes.charAt( count - 1 );
            if ( lastChar != '!')
                codes.append( '!' );
        }
    }

    /**
     * Returns an iterator that sequentially traverses
     * the input sequence for a grid for a run length encoded
     * specification. Count/character pairs are translated
     * into multiple characters, so "5o" translates to "ooooo".
     * 
     * @return  an iterator that sequentially traverses
     *          the input sequence for a 
     *          run length encoded specification
     */
    @Override
    public Iterator<Character> iterator()
    {
        CharIterator    iter    = new CharIterator();
        return iter;
    }
    
    private class CharIterator implements Iterator<Character>
    {
        // The codes string always ends in '!'
        // so codes.get( 0 ) is always in bounds.
        private final String    toParse = codes.toString();
        private final Matcher   matcher;
        private int             charCount;
        private char            nextChar;
        
        /**
         * Constructor.
         * 
         * Precondition: the <em>codes</em> string
         *               contains at least one character
         * 
         * Precondition: the last character in the <em>codes</em> 
         *               String '!'
         */
        public CharIterator()
        {
            matcher = pattern.matcher( toParse );
            parseNext();
        }

        /**
         * Returns true if the input sequence has not been exhausted.
         * 
         * @return true if the input sequence has not been exhausted
         */
        @Override
        public boolean hasNext()
        {
            boolean result  = charCount != 0;
            return result;
        }

        /**
         * Returns the next character in the input sequence.
         * 
         * @return  the next character in the input sequence
         * 
         * @throws  NoSuchElementException
         *          if the input sequence is exhausted
         */
        @Override
        public Character next() throws NoSuchElementException
        {
            if ( charCount == 0 )
            {
                String  message = "Iterator exhausted";
                throw new NoSuchElementException( message );
            }
            
            Character   next    = nextChar;
            if ( --charCount == 0 )
                parseNext();
            
            return next;
        }
        
        /**
         * Returns the next count/character pair 
         * in the input sequence.
         * Variable nextChar is set to <em>character</em>
         * If the <em>count</em> element is not present
         * charCount is set to 1, otherwise it is 
         * set to <em>count</em>.
         * If the end of the input sequence is reached;
         * i.e., if the next character in the sequence is '!';
         * charCount is set to 0.
         * 
         * @return  the next character in the input sequence,
         *          or null if none
         */
        private void parseNext()
        {
            // Since the last match (containing !) will terminate
            // the iteration, matcher.find() will never fail.
            matcher.find();
            
            // If there's a match there are always two groups present,
            // one for the count and the other for the character.
            // If the count is not present, its group contains the
            // empty string.
            charCount = 1;
            String  strCount    = matcher.group( 1 );
            if ( !strCount.isEmpty() )
                charCount = Integer.parseInt( strCount );
            
            String  strChar     = matcher.group( 2 );
            nextChar = strChar.charAt( 0 );
            if ( nextChar == '!' )
                charCount = 0;
        }
    }
}
