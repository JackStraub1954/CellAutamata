package com.gmail.johnstraub1954.cell_automata.main;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RLEInput
{
    /** 
     * Regular expression for parsing rules.
     * For example:
     * <br>&nbsp;&nbsp;&nbsp;&nbsp;
     * x=100,y=200,rule=23/3
     * <br>&nbsp;&nbsp;&nbsp;&nbsp;
     * x = 100 , y = 200 , rule = 23/3
     * <br>&nbsp;&nbsp;&nbsp;&nbsp;
     * x = 100 y= 100 rule = 23/3
     * <br>&nbsp;&nbsp;&nbsp;&nbsp;
     * x = 100, y= 100 rule = s23/b3
     * 
     * @see #rulePattern
     * @see #parseHeader(String, BufferedReader)
     */
    private static final String  ruleRegEx  = 
        "x\\s*=\\s*(\\d+)\\s*,*\\s*"
        + "y\\s*=\\s*(\\d+)\\s*,*"
        + "\\s*rule\\s*=\\s*"
        + "([sb])?(\\d+)\\s*/\\s*([sb])?(\\d+)"
        + "\\s*(?:/\\s*(\\d+))?";

    /* ****************************************************
     * Position of capture groups in the rule string
     * of the input header.
     */
    /** Position of the 'x' field in the input header */
    private static final int    xGroupPos       = 1;
    /** Position of the 'y' field in the input header */
    private static final int    yGroupPos       = 2;
    /**
     *  Position of the first survival/birth states tag field in the input
     *  header. For example, the 's' in "s23/b3"
     */
    private static final int    tagAGroupPos    = 3;;
    /**
     *  Position of the first state field in the input
     *  header. For example, the '23' in "s23/b3"
     */
    private static final int    stateAGroupPos  = 4;
    /**
     *  Position of the second survival/birth states tag field in the input
     *  header. For example, the 'b' in "s23/b3"
     */
    private static final int    tagBGroupPos    = 5;
    /**
     *  Position of the second state field in the input
     *  header. For example, the '3' in "s23/b3"
     */
    private static final int    stateBGroupPos  = 6;
    /**
     *  If present, position of the second game-states field in the input
     *  header. For example, the '2' in "23/3/2"
     */
    private static final int    stateCGroupPos  = 7;
    
    /**
     * Compiled pattern for parsing rules
     * 
     * @see #ruleRegEx
     * @see #parseHeader(String, BufferedReader)
     */
    private static final Pattern    rulePattern = 
        Pattern.compile( ruleRegEx, Pattern.CASE_INSENSITIVE );
    
    /** Used for reading input file */
    private BufferedReader reader  = null;
    
    /** true if the header line was present in the input */
    private boolean     headerPresent   = false;
    
    private final List<String>  comments        = new ArrayList<>();
    private final List<Integer> survivalRules   = new ArrayList<>();
    private final List<Integer> birthRules      = new ArrayList<>();
    private int                 gameStates      = -1;
    private String              name            = "Unnamed";
    private String              author          = "Unknown";
    private Point               upperLeft       = new Point( 0, 0 );
    private RLEGridDecoder      gridDecoder;
    private GridMap             gridMap         = null;

    public RLEInput( String path )
    {
        this( new File( path ) );
    }
    
    public RLEInput( File path )
    {
        try
        {
            if ( !path.exists() )
            {
                String  message = path.getPath() + ": not found";
                throw new CAException( message );
            };
            URL url = path.toURI().toURL();
            parse( url );
        }
        catch ( IOException exc )
        {
            throw new CAException( exc );
        }
    }
    
    public RLEInput( URL url )
    {
        try
        {
            parse( url );
        }
        catch ( IOException exc )
        {
            throw new CAException( exc );
        }
    }
    
    public RLEInput( InputStream inStream )
    {
        try
        {
            parse( inStream );
        }
        catch ( IOException exc )
        {
            throw new CAException( exc );
        }
    }

    /**
     * @return the gameStates
     */
    public int getGameStates()
    {
        return gameStates;
    }

    /**
     * @param gameStates the gameStates to set
     */
    public void setGameStates(int gameStates)
    {
        this.gameStates = gameStates;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the author
     */
    public String getAuthor()
    {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author)
    {
        this.author = author;
    }

    /**
     * @return the upperLeft
     */
    public Point getUpperLeft()
    {
        return upperLeft;
    }

    /**
     * @param upperLeft the upperLeft to set
     */
    public void setUpperLeft(Point upperLeft)
    {
        this.upperLeft = upperLeft;
    }

    /**
     * @return the gridMap
     */
    public GridMap getGridMap()
    {
        return gridMap;
    }

    /**
     * @param gridMap the gridMap to set
     */
    public void setGridMap(GridMap gridMap)
    {
        this.gridMap = gridMap;
    }

    /**
     * @return the comments
     */
    public List<String> getComments()
    {
        return comments;
    }

    /**
     * @return the survivalRules
     */
    public List<Integer> getSurvivalRules()
    {
        return survivalRules;
    }

    /**
     * @return the birthRules
     */
    public List<Integer> getBirthRules()
    {
        return birthRules;
    }
    
    public RLEGridDecoder getGridDecoder()
    {
        return gridDecoder;
    }
    
    private void parse( URL url ) throws IOException
    {
        try ( InputStream inStream = url.openStream(); )
        {
            parse( inStream );
        }
    }
        
    private void parse( InputStream inStream ) throws IOException
    {
        try ( 
            InputStreamReader inReader = new InputStreamReader( inStream );
            BufferedReader bufReader = new BufferedReader( inReader );
        )
        {
            reader = bufReader;
            String  line    = parseComments();
            if ( line != null )
            {
                bufReader.mark( 10000 );
                line = parseHeader( line );
                // there's a good chance that parsing
                // the front matter will read
                // the first line of the input sequence,
                // so put stream back to where it was before
                // parsing the header.
                if ( headerPresent )
                    bufReader.reset();
            }
            gridDecoder = new RLEGridDecoder( bufReader );
        }
    }
    
    /**
     * Parses all comments from the header of the RLE source.
     * Returns the first non-comment line from the source, 
     * or null if the source is exhausted.
     * In particular, if a non-null string is returned
     * the user should be prepared for that line
     * to be the header line
     * (<em>x = m, y = n, rule = abc</em>).
     * @param reader
     * @return
     * @throws IOException
     */
    private String parseComments() throws IOException
    {
        boolean done    = false;
        String  line    = null;
        while ( !done && (line = nextLine( reader )) != null )
        {
            // note: nextLine never returns an empty line
            if ( line.charAt( 0 ) == '#' )
                parseComment( line );
            else
                done = true;
        }
        
        return line;
    }
    
    /**
     * Attempts to parse a given line as a header line
     * (<em>x = m, y = n, rule = abc</em>).
     * @param line
     * @param reader
     * @return
     * @throws IOException
     * 
     * @see #ruleRegEx
     * @see #rulePattern
     * @see #parseHeaderStates(Matcher)
     */
    private String parseHeader( String line )
        throws IOException
    {
        String  next        = line;
        Matcher matcher     = rulePattern.matcher( line );
        boolean found       = matcher.find();
        
        if ( found )
        {
            headerPresent = true;
            int matchCount  = matcher.groupCount();
            if ( matchCount != 7 )
            {
                String  message =
                    "for RLE header string \"" + line
                    + "\": invalid match count ("
                    + matchCount + ")";
                throw new CAException( message );
            }

            int     xco     = Integer.parseInt( matcher.group( xGroupPos ) );
            int     yco     = Integer.parseInt( matcher.group( yGroupPos ) );
            upperLeft = new Point( xco, yco );
            
            parseHeaderStates( line, matcher );

            next = nextLine( reader );
        }
        
        return next;
    }
    
    /**
     * Parse the survival and birth states.
     * 
     * This operation is an integral part of the header parsing
     * process. It is removed to a separate method in order to
     * avoid making the main part of the process more complicated
     * than it already is.
     * 
     * Survival and birth states can be documented in three general forms:
     * <ol>
     * <li>
     *      23/3<br>
     *      In this form the left-hand string describes the survival states
     *      and the right-hand string describes the birth states.
     * </li>
     * <li>
     *      s23/b3<br>
     *      In this form the string following the 's'
     *      describes the survival states
     *      and the string following the 'b' describes the birth states.
     * </li>
     * <li>
     *      b3/s23<br>
     *      This is essentially the same as the second form,
     *      except the 'b' term is on the left,
     *      and the 's' term is on the right.
     * </li>
     * </ol>
     * The following examples are all considered errors
     * which will generate a CAException containing
     * a ParseException:
     * <ul>
     * <li>s23/3 (exactly one tag present)</li>
     * <li>23/b3 (exactly one tag present)</li>
     * <li>s23/s3 (two 's' tags, no 'b' tag)</li>
     * <li>b23/b3 (two 'b' tags, no 's' tag)</li>
     * <li>one or both states are missing</li>
     * </ul>
     * 
     * @param matcher   matcher that was used to parse the input
     *                  string in parseHeader
     * @param line      the line being parsed;
     *                  only used in the case of exceptions
     *                  
     * @throws  CAException if the survival/birth states are ambiguous;
     *          the CAException will contain a ParseException
     * @throws  NumberFormatException if a field that should be numeric
     *          cannot be converted
     *                  
     * @see #parseHeader(String) throws CAException
     */
    private void parseHeaderStates(  String line, Matcher matcher ) 
        throws CAException
    {
        String  stateTagA   = matcher.group( tagAGroupPos );
        String  stateA      = matcher.group( stateAGroupPos );
        String  stateTagB   = matcher.group( tagBGroupPos );
        String  stateB      = matcher.group( stateBGroupPos );
        if ( stateTagA == null && stateTagB != null )
        {
            CAException    exc = 
                generateGOL_ParseException( line, matcher, tagAGroupPos );
            throw exc;
        }
        if ( stateTagA != null && stateTagB == null )
        {
            CAException    exc = 
                generateGOL_ParseException( line, matcher, tagBGroupPos );
            throw exc;
        }
        if ( stateTagA != null && stateTagA.equals( stateTagB ) )
        {
            CAException    exc = 
                generateGOL_ParseException( line, matcher, tagAGroupPos );
            throw exc;
        }
        if ( stateA == null )
        {
            CAException    exc = 
                generateGOL_ParseException( line, matcher, stateAGroupPos );
            throw exc;
        }
        if ( stateB == null )
        {
            CAException    exc = 
                generateGOL_ParseException( line, matcher, stateBGroupPos );
            throw exc;
        }
        
        if ( stateTagA == null )
        {
            stateTagA = "S";
            stateTagB = "B";
        }
        
        // Assume stateA is survival state;
        // if wrong, swap with stateB
        if ( stateTagA.equals( "B" ) )
        {
            String  temp    = stateA;
            stateA = stateB;
            stateB = temp;
        }
        int aLen    = stateA.length();
        for ( int inx = 0 ; inx < aLen ; ++inx )
            survivalRules.add( Integer.parseInt( "" + stateA.charAt( inx ) ) );
        
        int bLen    = stateB.length();
        for ( int inx = 0 ; inx < bLen ; ++inx )
            birthRules.add( Integer.parseInt( "" + stateB.charAt( inx ) ) );
        
        String  stateC  = matcher.group( stateCGroupPos );
        if ( stateC != null )
            gameStates = Integer.parseInt( stateC );
    }
    
    /**
     * Generates a CAException containing a ParseException as a cause.
     * This operation is integral to the processing performed by 
     * parseHeaderStates; it is in a separate method in order to
     * encapsulate duplicate logic.
     * 
     * @param line      the line being parsed
     * @param matcher   the matcher used in the parsing
     * @param groupInx  the index of the group actively being parsed
     * 
     * @return  a CAException containing a ParseException as a cause
     */
    private CAException
    generateGOL_ParseException( String line, Matcher matcher, int groupInx )
    {
        int             offset      = matcher.end( groupInx );
        ParseException  parseExc    = new ParseException( line, offset );
        String          message     = 
            "Invalid state specification in header line";
        CAException    golExc      = new CAException( message, parseExc );
        return golExc;
    }
    
    private void parseComment( String line )
    {
        if ( line.length() < 2 )
            return;
        
        String  comment = line.substring( 1 );
        switch ( comment.charAt( 0 ) )
        {
        case 'C':
        case 'c':
            if ( comment.length() > 1 )
                comments.add( comment.substring( 1 ) );
            break;
        case 'N':
            if ( comment.length() > 1 )
                name = comment;
            break;
        case 'O':
            if ( comment.length() > 1 )
                author = comment.substring( 1 );
            break;
        case 'P':
        case 'R':
            parseUpperLeft( comment );
            break;
        case 'r':
            parseRules( comment );
            break;
        default:
            String  message =
            "Invalid RLE comment: " + comment;
            throw new CAException( message );
        }
    }
    
    private void parseUpperLeft( String comment )
    {
        String[]    nums    = 
            comment.substring( 1 ).strip().split( "\\S" );
        if ( nums.length != 2 )
        {
            String  message = "Invalid comment in input stream:" + comment;
            throw new CAException( message );
        }
        try
        {
            int xco = Integer.parseInt( nums[0] );
            int yco = Integer.parseInt( nums[1] );
            upperLeft = new Point( xco, yco );
        }
        catch ( NumberFormatException exc )
        {
            String  message = "Invalid ulc coordinates: " + comment;
            throw new CAException( message );
        }
    }
    
    private void parseRules( String comment )
    {
        String[]    rules   = 
            comment.substring( 1 ).strip().split( "/" );
        if ( rules.length < 2 || rules.length > 3 )
        {
            String  message = "Invalid comment in input stream:" + comment;
            throw new CAException( message );
        }
        try
        {
            String  rule    = rules[0];
            int     len     = rule.length();
            for ( int inx = 0 ; inx < len ; ++inx )
                survivalRules.add( Integer.parseInt( rule ) );
            
            rule = rules[1];
            len = rule.length();
            for ( int inx = 0 ; inx < len ; ++inx )
            {
                String  count   = "" + rule.charAt( inx );
                birthRules.add( Integer.parseInt( count ) );
            }
            
            if ( rules.length > 2 )
            {
                gameStates = Integer.parseInt( rules[2] );
            }
        }
        catch ( NumberFormatException exc )
        {
            String  message = "Invalid ulc coordinates: " + comment;
            throw new CAException( message );
        }
    }
    
    /**
     * Reads and returns the next line from a given BufferedReader.
     * Lines are stripped;
     * Blank lines are skipped; 
     * lines consisting of only "#" (after stripping) are skipped.
     * Null is returned if the reader is exhausted.
     *  
     * @param reader    the given BufferedReader
     * 
     * @return  the next non-blank line from the reader,
     *          or null if the reader is exhausted
     *          
     * @throws IOException  if an IO error occurs
     * 
     * @see #nextLineRaw(BufferedReader)
     */
    private String nextLine( BufferedReader reader) throws IOException
    {
        String  line    = nextLineRaw( reader );
        while ( line != null && line.isEmpty() )
            line = nextLineRaw( reader );
        return line;
    }
    
    /**
     * This is a helper method for <em>nextLine.</em>
     * Reads and returns the next line from a given BufferedReader.
     * Lines are stripped;
     * lines consisting of only "#" (after stripping)
     * are converted to empty strings.
     * Null is returned if the reader is exhausted.
     *  
     * @param reader    the given BufferedReader
     * 
     * @return  the next non-blank line from the reader,
     *          or null if the reader is exhausted
     *          
     * @throws IOException  if an IO error occurs
     * 
     * @see #nextLine(BufferedReader)
     */
    private String nextLineRaw( BufferedReader reader ) throws IOException
    {
        String  line    = reader.readLine();
        if ( line != null )
        {
            line = line.strip();
            if ( line.equals( "#" ) )
                line = "";
        }
        return line;
    }
}
