package com.gmail.johnstraub1954.cell_automata.components;

import java.awt.Component;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;

import com.gmail.johnstraub1954.cell_automata.main.Cell;
import com.gmail.johnstraub1954.cell_automata.main.CheckpointStack;
import com.gmail.johnstraub1954.cell_automata.main.CAException;
import com.gmail.johnstraub1954.cell_automata.main.GridMap;
import com.gmail.johnstraub1954.cell_automata.main.Parameters;
import com.gmail.johnstraub1954.cell_automata.main.RLEGridDecoder;
import com.gmail.johnstraub1954.cell_automata.main.RLEInput;

/**
 * This class mostly handles drag-and-drop operations for URLs.
 * The user can drop/drop a URL (link) from a web page,
 * or a file name from a file browser
 * (in this case, the file location will be converted
 * to a URL).
 * The class can also display a file-chooser for selecting a file
 * from the local file system 
 * (once again the file location will be converted to a URL;
 * see {@link #selectFile(Component)}).
 * 
 * The user must specify a JTextComponent to use as a drop target;
 * the URL will be displayed in the text component.
 * 
 * TODO test on Linux and macOS
 * 
 * @author Jack Straub
 */
public class URLManager extends DropTargetAdapter
{
    /** File selector dialog */
    private static final JFileChooser   fileChooser = new JFileChooser();
    /** GOL parameters */
    private static final Parameters     params      = Parameters.INSTANCE;
    /** Encapsulated text component; provided by user */
    private final JTextComponent        textComponent;
    /** Derived or converted URL; null if none */
    private URL                         url         = null;
    
    /**
     * Constructor.
     * Configures a given text component as a drop target.
     * 
     * @param textComponent the given text component
     */
    public URLManager( JTextComponent textComponent )
    {
        this.textComponent = textComponent;
        textComponent.setDragEnabled( true );
        new DropTarget( textComponent, this );
    }
    
    /**
     * Gets the most recently derived/converted URL. May be null.
     * 
     * @return  the most recently derived/converted URL; null if none.
     */
    public URL getURL()
    {
        return url;
    }
    
    /**
     * Displays a file selection dialog,
     * allowing the user to select a file from the local file system.
     * The location of the selected file (if any) is
     * displayed in the encapsulated text component.
     * The caller provides a Component to serve as the parent
     * of the file selection dialog; this may be null.
     * The final status returned by the selection dialog
     * (JFileChooser.APPROVE_OPTION or JFileChooser.CANCEL_OPTION)
     * is returned.
     * If URL conversion completes,
     * the CAConstants.CTRL_GRID_URL_PN property is set.
     * 
     * @param parent    component to use as the parent of the 
     *                  file selection dialog; may be null
     *                  
     * @return  the final status returned by the file selection dialog
     * 
     * @see Parameters#getGridURL()
     * @see Parameters#setGridURL(URL)
     * 
     * @throws  CAException if the Java file-to-URL conversion fails
     */
    public int selectFile( Component parent ) throws CAException
    {
        int rcode   = 
            fileChooser.showDialog( parent, JFileChooser.APPROVE_SELECTION );
        if ( rcode == JFileChooser.APPROVE_OPTION )
        {
            File    file    = fileChooser.getSelectedFile();
            try
            {
                url = file.toURI().toURL();
            }
            catch ( MalformedURLException exc )
            {
                String  message = 
                    "Failed to convert file to URL: "+ file.getName();
                throw new CAException( message, exc );
            }
            textComponent.setText( url.toString() );
            params.setGridURL( url );
        }
        return rcode;
    }
    
    /**
     * Opens the encapsulated URL, parses the input and displays
     * the new game.
     * The status of the operation is returned.
     * 
     * @return  true if the operation succeeded
     */
    public boolean open()
    {
        boolean status = open( url );
        return status;
    }
    
    /**
     * Open a given URL, parse the input and display the new game.
     * The status of the operation is returned.
     * 
     * @param url   the given URL
     * 
     * @return  true if the operation succeeded
     */
    public static boolean open( URL url )
    {
        boolean status  = false;
        try
        {
            RLEInput    input   = new RLEInput( url );
            open( input );
            status = true;
        }
        catch ( CAException exc )
        {
            String  message = "failed to process URL: " + url;
            JOptionPane.showMessageDialog( null, message );
            status = false;
        }
        
        return status;
    }
    
    /**
     * Open a given file, parse the input and display the new game.
     * The status of the operation is returned.
     * 
     * @param file   the given file
     * 
     * @return  true if the operation succeeded
     */
    public static boolean open( File file )
    {
        boolean status  = false;
        try
        {
            RLEInput    input   = new RLEInput( file );
            open( input );
            status = true;
        }
        catch ( CAException exc )
        {
            String  message = "failed to process URL: " + file;
            JOptionPane.showMessageDialog( null, message );
            status = false;
        }
        
        return status;
    }
    
    /**
     * Parse RLE input and instantiate the encapsulated game.
     * 
     * @param input the RLE input to parse
     */
    public static void open( RLEInput input )
    {
        CheckpointStack cpStack     = CheckpointStack.INSTANCE;

        params.setGridCellOrigin( input.getUpperLeft() );
        params.setSurvivalStates( input.getSurvivalRules() );
        params.setBirthStates( input.getBirthRules() );
        
        GridMap map = getGridMap( input );
        params.setGridMap( map );
        params.setGridLatestData( input );
        
        cpStack.clear();
        cpStack.push( map );
        params.reset();
    }
    
    /**
     * Parse a grid map from a given RLE input source.
     * 
     * @param input the given RLE input source
     * 
     * @return  the resultant grid map
     */
    private static GridMap getGridMap( RLEInput input )
    {
        Point               origin  = input.getUpperLeft();
        RLEGridDecoder      decoder = input.getGridDecoder();
        Iterator<Character> iter    = decoder.iterator();
        GridMap             gridMap = new GridMap();
        int currX   = origin.x;
        int currY   = origin.y;
        while ( iter.hasNext() )
        {
            char    nextChar    = iter.next();
            if ( nextChar == '$' )
            {
                ++currY;
                currX = origin.x;
            }
            else
            {
                boolean state   = nextChar == 'o';
                Cell    cell    = new Cell( currX++, currY, state );
                gridMap.put( cell );
            }
        }
        
        return gridMap;
    }
    
    /**
     * Catches link/file location 
     * dropped into the encapsulated text component
     * and converts it to a URL.
     * 
     * @param evt   event object associated with drop event
     * 
     * @throws  CAException if conversion fails
     */
    @Override
    public void drop( DropTargetDropEvent evt ) throws CAException
    {
        try
        {
            // accept the drop
            evt.acceptDrop(DnDConstants.ACTION_COPY );
            
            // get the location/link; assume it is a File type
            // note: links are passed around in files,
            // which must then be parsed to extract the actual URL.
            // There may be multiple locations/links in a single drop;
            // in this case, only the first will be used.
            // TODO test on Linux and macOS
            DataFlavor      flavor          =
                DataFlavor.javaFileListFlavor;
            Transferable    transferable    = evt.getTransferable();
            Object          obj             =
                transferable.getTransferData( flavor );
            List<?>         list            = (List<?>)obj;
            if ( list.size() > 0 && list.get( 0 ) instanceof File )
            {
                File    file    = (File)list.get( 0 );
                url = getLocation( file );
                String  text    = url == null ? "none" : url.toString();
                textComponent.setText( text );
                params.setGridURL( url );
            }
            else
                textComponent.setText( "??? ERROR ???" );
        }
        catch ( IOException | UnsupportedFlavorException exc )
        {
            String  message = 
                "Could not derive URL from file/link location";
            throw new CAException( message, exc );
        }
    }
    
    /**
     * Get the URL associated with a file.
     * 
     * @param file  the file from which to derive the URL
     * 
     * @return the derived URL, or null if none
     */
    private URL getLocation( File file )
    {
        URL url = null;
        try
        {
            if ( file.getName().toLowerCase().endsWith( ".url" ) )
                url = parseURL( file );
            else
            {
                // Note: File.toURL is deprecated
                URI uri = file.toURI();
                url = uri.toURL();
            }
        }
        catch ( MalformedURLException exc )
        {
            exc.printStackTrace();
            url = null;
        }
        
        return url;
    }
    
    /**
     * Parses the URL associated with a file.
     * If the file name ends with ".url" this is assumed to be a file
     * that contains the desired URL, in which case the file is parsed
     * to discover the URL,
     * Otherwise the file location is converted directly to a URL.
     * 
     * @param file  the file from which to derive the URL
     * 
     * @return the derived URL, or null if none
     * 
     * TODO test on Linux and macOS
     */
    private URL parseURL( File file )
    {
        List<String>    lines   = null;
        URL url         = null;
        try ( 
            FileReader  fileReader  = new FileReader( file );
            BufferedReader bufReader = new BufferedReader( fileReader );
        )
        {
            lines = bufReader.lines().collect( Collectors.toList() );
            int size    = lines.size();
            for ( int inx = 0 ; url == null && inx < size ; ++inx )
            {
                String[]    arr = lines.get( inx ).split( "=" );
                if ( arr.length == 2 && arr[0].equalsIgnoreCase( "url" ) )
                    url = new URL( arr[1] );
            }
        }
        catch ( IOException exc )
        {
            exc.printStackTrace();
            System.exit( 1 );
        }
        
        return url;
    }
}
