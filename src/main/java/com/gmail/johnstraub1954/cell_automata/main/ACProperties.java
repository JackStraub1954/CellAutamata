package com.gmail.johnstraub1954.cell_automata.main;

import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.AUTO_REGEN_MAX_DV;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.AUTO_REGEN_MAX_PN;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.AUTO_REGEN_MIN_DV;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.AUTO_REGEN_MIN_PN;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.AUTO_REGEN_ON_DV;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.AUTO_REGEN_ON_PN;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.AUTO_REGEN_PACE_DV;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.AUTO_REGEN_PACE_PN;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.CTRL_BIRTH_STATES_DV;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.CTRL_BIRTH_STATES_PN;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.CTRL_GRID_LATEST_DV;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.CTRL_GRID_LATEST_PN;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.CTRL_GRID_URL_DV;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.CTRL_GRID_URL_PN;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.CTRL_SURVIVAL_STATES_DV;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.CTRL_SURVIVAL_STATES_PN;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.GRID_CELL_COLOR_DV;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.GRID_CELL_COLOR_PN;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.GRID_CELL_ORIGIN_DV;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.GRID_CELL_ORIGIN_PN;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.GRID_CELL_SIZE_DV;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.GRID_CELL_SIZE_PN;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.GRID_COLOR_DV;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.GRID_COLOR_PN;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.GRID_HEIGHT_DV;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.GRID_HEIGHT_PN;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.GRID_KEEP_CENTERED_DV;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.GRID_KEEP_CENTERED_PN;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.GRID_LINE_COLOR_DV;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.GRID_LINE_COLOR_PN;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.GRID_LINE_SHOW_DV;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.GRID_LINE_SHOW_PN;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.GRID_LINE_WIDTH_DV;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.GRID_LINE_WIDTH_PN;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.GRID_MARGIN_BOTTOM_DV;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.GRID_MARGIN_BOTTOM_PN;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.GRID_MARGIN_LEFT_DV;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.GRID_MARGIN_LEFT_PN;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.GRID_MARGIN_RIGHT_DV;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.GRID_MARGIN_RIGHT_PN;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.GRID_MARGIN_TOP_DV;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.GRID_MARGIN_TOP_PN;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.GRID_WIDTH_DV;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.GRID_WIDTH_PN;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.MISC_AUTHOR_EMAIL_DV;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.MISC_AUTHOR_EMAIL_PN;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.MISC_AUTHOR_NAME_DV;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.MISC_AUTHOR_NAME_PN;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.MISC_AUTHOR_TIME_DV;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.MISC_AUTHOR_TIME_PN;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.MISC_PATTERN_FILE_NAME_DV;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.MISC_PATTERN_FILE_NAME_PN;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.MISC_PATTERN_NAME_DV;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.MISC_PATTERN_NAME_PN;

import java.awt.Color;
import java.awt.Point;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;

/**
 * @author Jack Straub
 *
 */
public class ACProperties extends Properties
{
    /** Generated serial version UID */
    private static final long serialVersionUID = 998230253608692257L;

    /**
     * Default constructor.
     */
    public ACProperties()
    {
        super();
    }

    /**
     * Constructor providing the initial capacity of this object.
     * 
     * @param initialCapacity   initial capacity of this object
     */
    public ACProperties( int initialCapacity )
    {
        super( initialCapacity );
    }

    /**
     * Constructor providing a set of default values for properties.
     * 
     * @param defaults  the set of default values for properties
     */
    public ACProperties( Properties defaults )
    {
        super( defaults );
    }
    
    /**
     * Obtains the value of the GRID COLOR property.
     * 
     * @return the value of the GRID COLOR property.
     */
    public Color getGridColor()
    {
        Color   color   = getColor( GRID_COLOR_PN, GRID_COLOR_DV );
        return color;
    }
    
    /**
     * Obtains the value of the GRID MARGIN-TOP property.
     * 
     * @return the value of the GRID MARGIN-TOP property
     */
    public int getGridMarginTop()
    {
        int size    = getSize( GRID_MARGIN_TOP_PN, GRID_MARGIN_TOP_DV );
        return size;
    }
    
    /**
     * Obtains the value of the GRID MARGIN-LEFT property.
     * 
     * @return the value of the GRID MARGIN-LEFT property
     */
    public int getGridMarginLeft()
    {
        int size    = getSize( GRID_MARGIN_LEFT_PN, GRID_MARGIN_LEFT_DV );
        return size;
    }
    
    /**
     * Obtains the value of the GRID MARGIN-BOTTOM property.
     * 
     * @return the value of the GRID MARGIN-BOTTOM property
     */
    public int getGridMarginBottom()
    {
        int size    = getSize( GRID_MARGIN_BOTTOM_PN, GRID_MARGIN_BOTTOM_DV );
        return size;
    }
    
    /**
     * Obtains the value of the GRID MARGIN-RIGHT property.
     * 
     * @return the value of the GRID MARGIN-RIGHT property
     */
    public int getGridMarginRight()
    {
        int size    = getSize( GRID_MARGIN_RIGHT_PN, GRID_MARGIN_RIGHT_DV );
        return size;
    }
    
    /**
     * Obtains the value of the GRID WIDTH property.
     * 
     * @return the value of the GRID WIDTH property
     */
    public int getGridWidth()
    {
        int size    = getInt( GRID_WIDTH_PN, GRID_WIDTH_DV );
        return size;
    }
    
    /**
     * Obtains the value of the GRID HEIGHT property.
     * 
     * @return the value of the GRID HEIGHT property
     */
    public int getGridHeight()
    {
        int size    = getInt( GRID_HEIGHT_PN, GRID_HEIGHT_DV );
        return size;
    }
    
    /**
     * Obtains the value of the GRID-LINE-SHOW property.
     * 
     * @return the value of the GRID-LINE-SHOW property
     */
    public boolean getGridLineShow()
    {
        boolean show    = getBoolean( GRID_LINE_SHOW_PN, GRID_LINE_SHOW_DV );
        return show;
    }
    
    /**
     * Obtains the value of the GRID LINE WIDTH property.
     * 
     * @return the value of the GRID LINE WIDTH property
     */
    public int getGridLineWidth()
    {
        int size    = getSize( GRID_LINE_WIDTH_PN, GRID_LINE_WIDTH_DV );
        return size;
    }
    
    /**
     * Obtains the value of the GRID LINE COLOR property.
     * 
     * @return the value of the GRID LINE COLOR property.
     */
    public Color getGridLineColor()
    {
        Color   color   = getColor( GRID_LINE_COLOR_PN, GRID_LINE_COLOR_DV );
        return color;
    }
    
    /**
     * Obtains the value of the GRID CELL WIDTH property.
     * 
     * @return the value of the GRID CELL WIDTH property
     */
    public int getGridCellSize()
    {
        int size    = getSize( GRID_CELL_SIZE_PN, GRID_CELL_SIZE_DV );
        return size;
    }
    
    /**
     * Obtains the value of the GRID CELL COLOR property.
     * 
     * @return the value of the GRID CELL COLOR property.
     */
    public Color getGridCellColor()
    {
        Color   color   = getColor( GRID_CELL_COLOR_PN, GRID_CELL_COLOR_DV );
        return color;
    }
    
    public Point getGridCellOrigin()
    {
        Point   origin  = getPoint( GRID_CELL_ORIGIN_PN, GRID_CELL_ORIGIN_DV );
        return origin;
    }
    
    /**
     * Obtains the value of the AUTO-REGENERATION ON property.
     * 
     * @return the value of the AUTO-REGENERATION ON property.
     */
    public boolean getAutoRegenOn()
    {
        boolean isOn    = getBoolean( AUTO_REGEN_ON_PN, AUTO_REGEN_ON_DV );
        return isOn;
    }
    
    /**
     * Obtains the value of the AUTO-REGENERATION PACE property.
     * Specifies generations per second.
     * 
     * @return the value of the AUTO-REGENERATION PACE property.
     */
    public float getAutoRegenPace()
    {
        float    pace    = getFloat( AUTO_REGEN_PACE_PN, AUTO_REGEN_PACE_DV );
        return pace;
    }
    
    /**
     * Obtains the value of the AUTO-REGENERATION MINIMUM PACE property.
     * Specifies generations per second.
     * 
     * @return the value of the AUTO-REGENERATION MINIMUM PACE property
     */
    public float getAutoRegenPaceMin()
    {
        float   pace    = getFloat( AUTO_REGEN_MIN_PN, AUTO_REGEN_MIN_DV );
        return pace;
    }
    
    /**
     * Obtains the value of the AUTO-REGENERATION MAXIMUM PACE property.
     * Specifies generations per second.
     * 
     * @return the value of the AUTO-REGENERATION MAXIMUM PACE property
     */
    public float getAutoRegenPaceMax()
    {
        float   pace    = getFloat( AUTO_REGEN_MAX_PN, AUTO_REGEN_MAX_DV );
        return pace;
    }
    
    /**
     * Gets a value that indicates whether the application should attempt
     * to re-center the live cells in its display
     * with each new generation.
     * 
     * @return  a value that indicates whether the grid should attempt
     *          to re-center the live cells in its display
     *          with each new generation
     */
    public boolean getGridKeepCentered()
    {
        boolean keepCentered    = 
            getBoolean( GRID_KEEP_CENTERED_PN, GRID_KEEP_CENTERED_DV );
        return keepCentered;
    }
    
    /**
     * Get the default propagation procedure.
     * 
     * @return  the default propagation procedure
     */
    public Consumer<Object> getPropagateProc()
    {
        return o -> Utils.INSTANCE.conwayPropagate( o );
    }
    
    /**
     * Gets the list of states that determines whether
     * a live cell survives into the next generation.
     * 
     * @return  the list of states that determines whether
     *          a live cell survives into the next generation
     */
    public List<Integer> getSurvivalStates()
    {
        List<Integer>   list    = 
            getIntegerList( CTRL_SURVIVAL_STATES_PN, CTRL_SURVIVAL_STATES_DV );
        return list;
    }
    
    /**
     * Gets the list of states that determines whether
     * a dead cell comes alive in the next generation.
     * 
     * @return  the list of states that determines whether
     *          a dead cell comes alive in the next generation
     */
    public List<Integer> getBirthStates()
    {
        List<Integer>   list    = 
            getIntegerList( CTRL_BIRTH_STATES_PN, CTRL_BIRTH_STATES_DV );
        return list;
    }
    
    /**
     * Gets the URL of the data to display in the grid.
     * 
     * @return  the URL of the data to display in the grid
     */
    public URL getGridURL()
    {
        URL url = getURL( CTRL_GRID_URL_PN, CTRL_GRID_URL_DV );
        return url;
    }
    
    /**
     * Gets the most recent data used to populate the grid.
     * Currently not stored as a property value; null is
     * always returned.
     * 
     * @return  the most recent data used to populate the grid
     */
    public RLEInput getGridLatestData()
    {
        RLEInput    input   = 
            (RLEInput)getObject( CTRL_GRID_LATEST_PN, CTRL_GRID_LATEST_DV );
        return input;
    }
    
    /**
     * Gets the name of the pattern being documented/displayed.
     * 
     * @return the name of the pattern being documented/displayed
     */
    public String getPatternName()
    {
        String  name    =
            getString( MISC_PATTERN_NAME_PN, MISC_PATTERN_NAME_DV );
        return name;
    }
    
    /**
     * Gets the name of the file
     * containing the pattern being documented/displayed.
     * 
     * @return  the name of the file containing
     *          the pattern being documented/displayed
     */
    public String getPatternFileName()
    {
        String  name    =
            getString( MISC_PATTERN_FILE_NAME_PN, MISC_PATTERN_FILE_NAME_DV );
        return name;
    }
    
    /**
     * Gets the name of the author of the pattern being documented/displayed.
     * 
     * @return  the name of the author of the pattern
     *          being documented/displayed
     */
    public String getAuthorName()
    {
        String  name    =
            getString( MISC_AUTHOR_NAME_PN, MISC_AUTHOR_NAME_DV );
        return name;
    }
    
    /**
     * Gets the email address of the author of the pattern.
     * 
     * @return  the email address of the author of the pattern
     */
    public String getAuthorEmail()
    {
        String  name    =
            getString( MISC_AUTHOR_EMAIL_PN, MISC_AUTHOR_EMAIL_DV );
        return name;
    }
    
    /**
     * Gets the creation date of the pattern being documented/displayed.
     * To be displayed on the author line of the documentation header.
     * 
     * @return  the creation date of the pattern being documented/displayed
     */
    public LocalDateTime getAuthorTime()
    {
        // TODO figure out how to parse a string into a date/time
        LocalDateTime   time    =
            getTime( MISC_AUTHOR_TIME_PN, MISC_AUTHOR_TIME_DV );
        return time;
    }

    /**
     * Retrieves a property value and converts it to a Color.
     * If the value of the property begins with an alphabetic character
     * it will be treated as a color name,
     * otherwise it will be treated as an integer.
     * If the value is expected to be an integer,
     * but cannot be converted to an integer,
     * an IllegalStateException is thrown.
     * If the property name cannot be found
     * <em>defValue</em> will be returned.
     * 
     * @param name      the name of the property
     * @param defValue  the default value, 
     *                  to be returned if the property name is not found
     *                  in this property set
     *                  
     * @return  the value of the named property,
     *          or <em>defValue</em> if the property name is not found
     *          
     * @throws IllegalArgumentException if <em>defValue</em> is null
     *          
     * @throws  IllegalStateException if the property value
     *          is expected to be an integer
     *          but cannot be converted to an integer 
     */
    public Color getColor( String name, String defValue )
        throws IllegalArgumentException, IllegalStateException
    {
        if ( defValue == null )
        {
            String  message = "Default value may not be null";
            throw new IllegalArgumentException( message );
        }
        
        Color   result  = null;
        String  value   = getProperty( name, defValue );
        
        if ( Character.isAlphabetic( value.charAt( 0 ) ) )
        {
            result = Color.getColor( value );
            if ( result == null )
            {
                String  message = "\"" + value + "\" is not a valid color";
                throw new IllegalStateException( message );
            }
        }
        else
        {
            try
            {
                int     radix   = 10;
                String  strNum  = value;
                if ( value.startsWith( "0x" ) )
                {
                    radix = 16;
                    strNum = value.substring( 2 );
                }
                else if ( value.startsWith( "0b" ) )
                {
                    radix = 2;
                    strNum = value.substring( 2 );
                }
                else if ( !value.equals( "0" ) &&value.startsWith( "0" ) )
                {
                    radix = 8;
                    strNum = value.substring( 1 );
                }
                else
                    ;
                
                int intColor    = Integer.parseInt( strNum, radix );
                result = new Color( intColor );
            }
            catch ( NumberFormatException exc )
            {
                String  message = "\"" + value + "\" is not a valid color";
                throw new IllegalStateException( message, exc );
            }
        }   
        return result;
    }
    
    /**
     * Obtains the value of a property and converts it to a size.
     * The value may end with "px" (for pixels) or "e" (for EMs).
     * 
     * @param name      the name of the property to obtain
     * @param defValue  the default value to use if the property name
     *                  can't be found
     *                  
     * @return the value of the property converted to a size
     * 
     * @throws IllegalArgumentException if <em>defValue</em> is null
     * @throws IllegalStateException if the value of the property
     *         cannot be converted to a size
     */
    public int getSize( String name, String defValue )
        throws IllegalArgumentException, IllegalStateException
    {
        if ( defValue == null )
        {
            String  message = "Default value may not be null";
            throw new IllegalArgumentException( message );
        }
        
        int     size        = 0;
        String  value       = getProperty( name, defValue );
        String  strNum      = value;
        int     xier        = 1;
        int     len         = value.length();
        
        if ( value.endsWith( "px" ) )
            strNum = value.substring( 0, len - 2 );
        else if ( value.endsWith( "em" ) )
        {
            strNum = value.substring( 0, len - 2 );
            xier = 16;
        }
        else
            ;
        
        try
        {
            size = Integer.parseInt( strNum ) * xier;
        }
        catch ( NumberFormatException exc )
        {
            String  message = "\"" + value + "\" is not a valid color";
            throw new IllegalStateException( message, exc );
        }
        
        return size;
    }   
    
    /**
     * Obtains the value of a property and converts it to an int.
     * 
     * @param name      the name of the property to obtain
     * @param defValue  the default value to use if the property name
     *                  can't be found
     *                  
     * @return the named property converted to an int
     * 
     * @throws IllegalArgumentException if <em>defValue</em> is null
     * @throws IllegalStateException if the value of the property
     *         cannot be converted to a size
     */
    public int getInt( String name, String defValue )
        throws IllegalArgumentException, IllegalStateException
    {
        if ( defValue == null )
        {
            String  message = "Default value may not be null";
            throw new IllegalArgumentException( message );
        }
        
        int     size        = 0;
        String  value       = getProperty( name, defValue );
        try
        {
            size = Integer.parseInt( value );
        }
        catch ( NumberFormatException exc )
        {
            String  message = "\"" + value + "\" is not a valid size";
            throw new IllegalStateException( message, exc );
        }
        
        return size;
    }   
    
    /**
     * Obtains the value of a property and converts it to an boolean.
     * 
     * @param name      the name of the property to obtain
     * @param defValue  the default value to use if the property name
     *                  can't be found
     *                  
     * @return the named property converted to a boolean.
     * 
     * @throws IllegalArgumentException if <em>defValue</em> is null
     */
    public boolean getBoolean( String name, String defValue )
        throws IllegalArgumentException
    {
        if ( defValue == null )
        {
            String  message = "Default value may not be null";
            throw new IllegalArgumentException( message );
        }
        
        boolean show        = false;
        String  value       = getProperty( name, defValue );
        show = Boolean.parseBoolean( value );
        
        return show;
    }   
    
    /**
     * Parses a string property into a Point, and return the Point.
     * The format of the input string consists of two integers
     * separated by whitespace and/or comma.
     * 
     * @param name      the name of the property to retrieve
     * @param defValue  the default value to use if the property name
     *                  can't be found
     * @return  a Point, consisting of an x- and y-coordinate
     * @throws IllegalArgumentException if defValue is null
     * @throws CAException if the property string cannot be properly parsed
     */
    public Point getPoint( String name, String defValue )
        throws IllegalArgumentException, CAException
    {
        if ( defValue == null )
        {
            String  message = "Default value may not be null";
            throw new IllegalArgumentException( message );
        }
        
        String      strValue    = getProperty( name, defValue );
        String[]    strCoords   = strValue.split( "[\\s,]" );
        if ( strCoords.length != 2 )
        {
            String  message = 
                "Value is not a point;"
                + "expected: \"x, y\""
                + "actual: \"strValue\"";
            throw new CAException( message );
        }
        Point   point   = new Point();
        try
        {
            point.x = Integer.parseInt( strCoords[0] );
            point.y = Integer.parseInt( strCoords[1] );
        }
        catch ( NumberFormatException exc )
        {
            String  message = "Invalid integers: " + strValue;
            throw new CAException( message, exc );
        }
            
        return point;
    }   
    
    /**
     * Obtains the value of a property and converts it to a float.
     * 
     * @param name      the name of the property to obtain
     * @param defValue  the default value to use if the property name
     *                  can't be found
     *                  
     * @return the named property converted to an float
     * 
     * @throws IllegalArgumentException if <em>defValue</em> is null
     * @throws IllegalStateException if the value of the property
     *         cannot be converted to a size
     */
    public float getFloat( String name, String defValue )
        throws IllegalArgumentException, IllegalStateException
    {
        if ( defValue == null )
        {
            String  message = "Default value may not be null";
            throw new IllegalArgumentException( message );
        }
        
        float   num         = 0;
        String  value       = getProperty( name, defValue );
        try
        {
            num = Float.parseFloat( value );
        }
        catch ( NumberFormatException exc )
        {
            String  message = "\"" + value + "\" is not a valid float";
            throw new IllegalStateException( message, exc );
        }
        
        return num;
    }   
    
    /**
     * Obtains the value of a property and converts it to a list
     * of Integers.
     * 
     * @param name      the name of the property to obtain
     * @param defValue  the default value to use if the property name
     *                  can't be found
     *                  
     * @return the named property converted to a a list of Integers.
     * 
     * @throws IllegalArgumentException if <em>defValue</em> is null
     * @throws CAException if the value to parse is not a valid point
     */
    public List<Integer> getIntegerList( String name, String defValue )
        throws IllegalArgumentException, CAException
    {
        if ( defValue == null )
        {
            String  message = "Default value may not be null";
            throw new IllegalArgumentException( message );
        }
        List<Integer>   list    = new ArrayList<>();
        String          value   = getProperty( name, defValue );
        String[]        strNums = value.split( "," );
        try
        {
            for ( String num : strNums )
                list.add( Integer.parseInt( num ) );
        }
        catch ( NumberFormatException exc )
        {
            String  message = 
                "\"" + value + "\" is not a list of integers";
            throw new   CAException( message, exc );
        }
        
        return list;
    }
    
    /**
     * Obtains the value of a property and converts it to a URL.
     * Returns null if the property value is null or an empty string.
     * 
     * @param name      the name of the property
     * @param defValue  the default value of the property; may not be null
     * 
     * @return the converted URL
     * 
     * @throws IllegalArgumentException if defValue is null
     * @throws IllegalStateException    if the property value
     *      cannot be converted to a URL.
     */
    public  URL getURL( String name, String defValue )
        throws IllegalArgumentException, IllegalStateException
    {
        if ( defValue == null )
        {
            String  message = "Default value may not be null";
            throw new IllegalArgumentException( message );
        }
        String      value   = getProperty( name, defValue ).trim();
        URL         url     = null;
        if ( value != null && !value.isEmpty() )
        {
            try
            {
                url = new URL( value );
            }
            catch ( MalformedURLException exc )
            {
                String  message = "\"" + value + "\" invalid URL";
                throw new IllegalStateException( message, exc );
            }
        }
        return url;
    }
    
    
    /**
     * Obtains the value of a property and "converts" it to a string.
     * Returns null if the property value is null or an empty string.
     * 
     * @param name      the name of the property
     * @param defValue  the default value of the property; may not be null
     * 
     * @return the converted string
     * 
     * @throws IllegalArgumentException if defValue is null
     */
    public String getString( String name, String defValue )
    {
        if ( defValue == null )
        {
            String  message = "Default value may not be null";
            throw new IllegalArgumentException( message );
        }
        String      value   = getProperty( name, defValue ).trim();
        return value;
    }
    
    /**
     * Obtains the value of a property and converts it to a date/time.
     * Returns null if the property value is null or an empty string,
     * or if the string cannot be converted to a date/time.
     * 
     * @param name      the name of the property
     * @param defValue  the default value of the property; may not be null
     * 
     * @return the converted string
     * 
     * @throws IllegalArgumentException if defValue is null
     */
    public LocalDateTime getTime( String name, String defValue )
    {
        if ( defValue == null )
        {
            String  message = "Default value may not be null";
            throw new IllegalArgumentException( message );
        }
        
        // TODO figure out how to convert a string to a date/time
//        String          value   = getProperty( name, defValue ).trim();
        LocalDateTime   time    = LocalDateTime.now();
        return time;
    }
    
    /**
     * Obtains the value of a property and converts it to an object.
     * Returns null if the property value is null or an empty string.
     * 
     * TODO for the moment this is just a placeholder; might need 
     *      to delete or replace this method. See additional 
     *      "todo" comments below.
     * 
     * @param name      the name of the property
     * @param defValue  the default value of the property; may not be null
     * 
     * @return the converted object
     * 
     * @throws IllegalArgumentException if defValue is null
     * @throws IllegalStateException    if the property value
     *      cannot be converted to an object.
     */
    public Object getObject( String name, String defValue )
        throws IllegalArgumentException, IllegalStateException
    {
        if ( defValue == null )
        {
            String  message = "Default value may not be null";
            throw new IllegalArgumentException( message );
        }
        String      value   = getProperty( name, defValue ).trim();
        Object      obj     = null;
        if ( value != null && !value.isEmpty() )
        {
            try
            {
                // TODO figure this one out...
                // so far the only Object value we have to deal with is
                // the input data (to date, RLEInput object).
                // What to do about this? Possible nothing; maybe a
                // non-null value doesn't come out of the set of properties.
                // Maybe need a method named "GetGridInputData()"?
                // Maybe the initial value could be a URL to read
                // and convert to input object?
                //obj = "...";
            }
            catch ( Exception exc )
            {
                String  message = "\"" + value + "\" invalid Object";
                throw new IllegalStateException( message, exc );
            }
        }
        return obj;
    }
}
