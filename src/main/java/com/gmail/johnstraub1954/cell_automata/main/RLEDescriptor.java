package com.gmail.johnstraub1954.cell_automata.main;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Encapsulates all the data needed to create an RLE file.
 * 
 * @author Jack Straub
 *
 */
public class RLEDescriptor implements Iterable<Character>
{
    /** For formatting the date/time for use in header comment. */
    private static final DateTimeFormatter  timeFormatString    =
        DateTimeFormatter.ofPattern( "uuuu/MMM/dd HH:mm:ss" );
    
    /** For formatting the header line. */
    private static final String headerLineFormat    =
        "x = %d, y = %d, rule = %s/%s";
    
    /** 
     * The list of comments to be written to the beginning of the 
     * RLE file. The comments are plain text; the user should not
     * include the "#C" comment prefix.
     */
    private final List<String>  comments        = new ArrayList<>();
    /** Birth rules to denote in the header line */
    private final List<Integer> birthRules      = new ArrayList<>();
    /** Survival rules to denote in the header line */
    private final List<Integer> survivalRules   = new ArrayList<>();
    /** Pattern name, e.g. "Gosper Glider"; ignored if null or empty */
    private String              name            = null;
    /** Author name; ignored if null or empty */
    private String              authorName      = null;
    /** 
     * Email address for author line; ignored if null or empty, or
     * author name is not present.
     */
    private String              authorEmail     = null;
    /** 
     * Creation time for author line; ignored if null, or
     * author name is not present.
     */
    private LocalDateTime       authorTime      = null;
    /** Upper-left corner of start of pattern to be reported on header line */
    private Point               upperLeftCorner = null;
    /** Grid map for obtaining pattern data */
    private GridMap             gridMap         = null;
    
    /**
     * Default constructor.
     */
    public RLEDescriptor()
    {
    }
    
    /**
     * Constructor.
     * Initializes state (as much as possible) from a given RLEInput object.
     * 
     * @param rleData   the given RLEInputObject
     */
    public RLEDescriptor( RLEInput rleData )
    {
        if ( rleData != null )
        {
            birthRules.addAll( rleData.getBirthRules() );
            survivalRules.addAll( rleData.getSurvivalRules() );
            name = rleData.getName();
            authorName = rleData.getAuthor();
            upperLeftCorner = rleData.getUpperLeft();
        }
    }
    
    /**
     * Constructor.
     * Initializes state (as much as possible) from a given Parameters object.
     * 
     * @param params   the given Parameters object
     */
    public RLEDescriptor( Parameters params )
    {
        birthRules.addAll( params.getBirthStates() );
        survivalRules.addAll( params.getSurvivalStates() );
        name = params.getPatternName();
        authorName = params.getAuthorName();
        authorEmail = params.getAuthorEmail();
        authorTime = params.getAuthorTime();
        gridMap = params.getGridMap();
        if ( gridMap != null )
            upperLeftCorner = gridMap.getUpperLeftCorner();
    }
    
    /**
     * Gets the name of the pattern.
     * 
     * @return the name of the pattern
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Sets the pattern name.
     * 
     * @param name  the pattern name
     */
    public void setName(String name)
    {
        this.name = name;
    }
    
    /**
     * Gets the author name.
     * 
     * @return the author name
     */
    public String getAuthorName()
    {
        return authorName;
    }
    
    /**
     * Sets the author name.
     * 
     * @param authorName the author name
     */
    public void setAuthorName(String authorName )
    {
        this.authorName = authorName;
    }
    
    /**
     * Gets the author email address.
     * 
     * @return the author email address
     */
    public String getAuthorEmail()
    {
        return authorEmail;
    }
    
    /**
     * Sets the author email address
     * 
     * @param authorEmail the author email address
     */
    public void setAuthorEmail(String authorEmail)
    {
        this.authorEmail = authorEmail;
    }
    
    /**
     * Gets the file date/time.
     * 
     * @return the author date/time
     */
    public LocalDateTime getAuthorTime()
    {
        return authorTime;
    }
    
    /**
     * Sets the author date/time.
     * 
     * @param authorTime the author date/time
     */
    public void setAuthorTime(LocalDateTime authorTime)
    {
        this.authorTime = authorTime;
    }
    
    /**
     * Gets the coordinates of the upper-left corner of the
     * encapsulated grid.
     * 
     * @return  the the upper-left corner of the
     *          encapsulated grid
     */
    public Point getUpperLeftCorner()
    {
        if ( upperLeftCorner != null )
            return upperLeftCorner;
        
        if ( gridMap != null )
            return gridMap.getUpperLeftCorner();
        
        return new Point( 0, 0 );
    }
    
    /**
     * Sets the upper-left corner of the encapsulated grid.
     * 
     * @param upperLeftCorner the upper-left corner of the encapsulated grid
     */
    public void setUpperLeftCorner( Point upperLeftCorner )
    {
        this.upperLeftCorner = upperLeftCorner;
    }
    
    /**
     * Sets the entire list of birth rules. 
     * If the input is null, it is converted to an empty list.
     * 
     * @param birthRules    the new list of birth rules
     */
    public void setBirthRules( List<Integer> birthRules )
    {
        this.birthRules.clear();
        this.birthRules.addAll( birthRules );
    }
    
    /**
     * Adds a single rule to the list of birth rules.
     * 
     * @param rule  the rule to add to the list
     */
    public void addBirthRule( int rule )
    {
        this.birthRules.add( rule );
    }
    
    /**
     * Get the list of birth rules.
     * 
     * @return the list of birthRules
     */
    public List<Integer> getBirthRules()
    {
        return birthRules;
    }
    
    /**
     * Sets the entire list of survival rules. 
     * If the input is null, it is converted to an empty list.
     * 
     * @param survivalRules    the new list of survival rules
     */
    public void setSurvivalRules( List<Integer> survivalRules )
    {
        this.survivalRules.clear();
        this.survivalRules.addAll( survivalRules );
    }
    
    /**
     * Adds a single rule to the list of survival rules.
     * 
     * @param rule  the rule to add to the list
     */
    public void addSurvivalRule( int rule )
    {
        this.survivalRules.add( rule );
    }
    
    /**
     * Gets the list of survival rules.
     * 
     * @return the list of survivalRules
     */
    public List<Integer> getSurvivalRules()
    {
        return survivalRules;
    }
    
    /**
     * Gets the list of comments.
     * 
     * @return the list of comments
     */
    public List<String> getComments()
    {
        return comments;
    }
    
    /**
     * Sets the entire list of comments.
     * 
     * @param comments  the list of comments
     */
    public void setComments( List<String> comments )
    {
        this.comments.clear();
        this.comments.addAll( comments );
    }
    
    /**
     * Add a comment or comments to the list of comments.
     * The comments are passed as a single string, 
     * but each line gets parsed, trimmed and added as 
     * an individual comment.
     * 
     * @param comments  the comments to add, as a single string
     * 
     * @throws GOLException if an exception occurs while reading
     *         lines from the input string
     */
    public void addComments( String comments )
    {
        try ( StringReader    strReader   = new StringReader( comments );
              BufferedReader  bufReader   = new BufferedReader( strReader );
        )
        {
            List<String>    lines   =
                bufReader.lines()
                .map( s -> s.trim() )
                .collect( Collectors.toList() );
            this.comments.addAll( lines );
        }
        catch ( IOException exc )
        {
            exc.printStackTrace();
            String  message = 
                "Unexpected I/O error when reading from string source";
            throw new CAException( message, exc );
        }
    }
    
    /**
     * Removes a single comment from the list of comments.
     * The comment will be trimmed prior to comparing to the comments
     * in the list.
     * 
     * @param comment   the comment to remove.
     */
    public void removeComment( String comment )
    {
        comments.remove( comment.trim() );
    }
    
    /**
     * Gets the encapsulated grid map.
     */
    public GridMap getGridMap()
    {
        return gridMap;
    }
    
    /**
     * Sets the encapsulated grid map to a given value.
     * 
     * @param   the given value
     */
    public void setGridMap( GridMap gridMap )
    {
        this.gridMap = gridMap;
    }
    
    /**
     * Returns a list of comments to add to the RLE file header.
     * Each comment is formatted with the "#C" prefix.
     * 
     * @return  a list of comments to add to the RLE file header
     */
    public List<String> getHeaderComments()
    {
        List<String>    headerComments  = 
            comments
            .stream()
            .map( s -> "#C " + s )
            .collect( Collectors.toCollection( ArrayList::new ) );
        if ( name != null && !name.isEmpty() )
            headerComments.add( "#N " + name );
        
        // Only add author data if author name is present.
        // If author name is not present, author email and date
        // are ignored.
        if ( authorName != null && !authorName.isEmpty() )
        {
            StringBuilder   bldr    = new StringBuilder();
            bldr.append( "#O " ).append( authorName );
            if ( authorEmail != null && !authorEmail.isEmpty() )
                bldr.append( ' ' ).append( authorEmail );
            if ( authorTime != null )
                bldr.append( ' ' )
                .append( authorTime.format( timeFormatString ) );
            headerComments.add( bldr.toString() );
        }
        
        return headerComments;
    }
    
    /**
     * Returns the formatted header line of an RLE file, for example:
     * <pre>
     *     x = 25, y = 35, rule = B3/S23</pre>
     *     
     * If the upper left corner has not been specified 
     * an attempt will be made to obtain it from the encapsulated gridMap;
     * if there is no grid, the upper left corner will default to (0,0).
     * 
     * If one or both rules have not been specified
     * the missing rule will be replaced with the original
     * Conway rules (B3/S23).
     * The order in which the birth and survival rules are returned
     * is not specified.
     * 
     * @return the formatted header line of RLE file
     */
    public String getHeaderLine()
    {
        if ( birthRules.isEmpty() )
            birthRules.add( 3 );
        if ( survivalRules.isEmpty() )
            survivalRules.addAll( List.of( 2, 3 ) );
        
        if ( upperLeftCorner == null )
        {
            if ( gridMap == null )
                upperLeftCorner = new Point( 0, 0 );
            else
                upperLeftCorner = gridMap.getUpperLeftCorner();
        }
        
        StringBuilder   bBldr   = new StringBuilder( "B" );
        birthRules.forEach( i -> bBldr.append( i ) );
        StringBuilder   sBldr   = new StringBuilder( "S" );
        survivalRules.forEach( i -> sBldr.append( i ) );
        String  line    = String.format(
            headerLineFormat,
            upperLeftCorner.x, 
            upperLeftCorner.y,
            bBldr,
            sBldr
        );
        
        return line;
    }
    
    /**
     * Returns an iterator to traverse all the cells in the live rectangle
     * of the encapsulated grid map. The encapsulated grid map
     * may not be null.
     * 
     * @return  an iterator to traverse all the cells in the live rectangle
     *          of a grid map
     *          
     * @throws  NullPointerException if the encapsulated grid map is null
     */
    public Iterator<Character> iterator() throws NullPointerException
    {
        Iterator<Character> iterator    = new CellIterator( gridMap );
        return iterator;
    }
    
    /**
     * Encodes the encapsulated grid in RLE format.
     * Returns the encoding as a list of strings suitable for
     * writing to an output sink as individual lines of text.
     * 
     * @return  the encoding of the encapsulated grid map in RLE format
     */
    public List<String> getEncodedGrid()
    {
        RLEGridEncoder  encoder = new RLEGridEncoder( this );
        encoder.encode();
        List<String>    lines   = encoder.getLines();
        return lines;
    }
    
    /**
     * Sequentially iterates over every "significant" cell 
     * in the encapsulated gridMap.
     * A "significant cell" is every cell in the gridmap's
     * live rectangle, up to and including the last live cell
     * in each row.
     * Dead cells at the end of a row are ignored.
     * 
     * For each cell, 'o' is returned if the cell is live, 
     * 'b' is returned if the cell is dead. The next character
     * returned after a row of the rectangle is exhausted is '$'.
     * The next character returned after processing the
     * last live cell in the last row is '!'.
     * 
     * @author Jack Straub
     */
    private static class CellIterator implements Iterator<Character>
    {
        /** The grid map to traverse */
        private final GridMap   gridMap;
        /** The live rectangle within the grid map to traverse */
        private final Rectangle liveRect;
        /** 
         * The x-coordinate of the first cell in every row
         * of the live rectangle.
         */
        private final int       firstX;
        /**
         * The x-coordinate of the end of each row in the live rectangle.
         * This value is non-inclusive; if the value of the variable
         * is N, the x-coordinate of the last cell in a row is N - 1. 
         */
        private final int       lastX;
        /** The y-coordinate of the first row in the live rectangle */
        private final int       firstY;
        /**
         * The y-coordinate of the end row at the end of the live rectangle.
         * This value is non-inclusive; if the value of the variable
         * is M, the y-coordinate of the last row is M - 1. 
         */
        private final int       lastY;
        
        /** The x-coordinate of the next cell to traverse */
        private int             nextX;
        /** The y-coordinate of the next cell to traverse */
        private int             currY;
        /** X coordinate of last live cell in current row; -1 if none */
        private int             lastLiveX;
        /**
         * The last character returned by the next() method.
         * Its initial value of lastChar should be anything OTHER than:
         * 'o', 'b', '$', '!'
         */
        private char            lastChar    = ' ';
        
        /**
         * Constructor.
         * 
         * @param map   the grid map to traverse;
         *              may not be null
         */
        public CellIterator( GridMap map )
        {
            gridMap = map;
            liveRect = gridMap.getLiveRectangle();
            firstX = liveRect.x;
            lastX = firstX + liveRect.width;
            firstY = liveRect.y;
            lastY = firstY + liveRect.height;
            nextX = firstX;
            
            // Call to new row will immediately increment this 
            currY = firstY - 1;
            newRow();
        }
        
        /**
         * Returns true if there is at least one cell
         * in the live rectangle of the grid map
         * that has no yet been traversed.
         * 
         * @return  true if there are more cells to traverse
         */
        @Override
        public boolean hasNext()
        {
            boolean result  = lastChar != '!';
            return result;
        }
        
        /**
         * Returns the next character in the iteration.
         * 
         * @return  the next character in the iteration
         * 
         * @throws  NoSuchElementException if the iteration is complete
         */
        @Override
        public Character next() throws NoSuchElementException
        {
            if ( lastChar == '!' )
                throw new NoSuchElementException( "iterator overflow" );
            if ( nextX <= lastLiveX )
            {
                Cell    nextCell    = gridMap.get( nextX++, currY );
                lastChar = nextCell.isAlive() ? 'o' : 'b';
            }
            else
            {
                newRow();
                lastChar = currY < lastY ? '$' : '!';
            }
            
            return lastChar;
        }
        
        /**
         * Prepare state for processing next row in grid.
         * 
         * Precondition:<br>
         * currY is one less than the next row to process.
         * The last time this method is invoked, currY should be equal to
         * lastY - 1 (firstY + rect.height - 1).
         * 
         * Postcondition:<br>
         * currY incremented by 1.
         * 
         * Postcondition:<br>
         * nextX set equal to the first column in the row.
         * 
         * Postcondition:<br>
         * lastLiveX set equal to the column of the last live cell
         * in the new row.
         * If every cell in the new row is dead, lastLiveX == -1.
         */
        private void newRow()
        {
            lastLiveX = -1;
            if ( ++currY < lastY )
            {
                int col = lastX - 1;
                Cell    cell    = gridMap.get( col, currY );
                while ( !cell.isAlive() && col >= 0 )
                    cell = gridMap.get( --col, currY );
                lastLiveX = col;
            }
            nextX = firstX;
        }
    }
}
