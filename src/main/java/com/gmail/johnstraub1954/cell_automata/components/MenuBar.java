package com.gmail.johnstraub1954.cell_automata.components;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import com.gmail.johnstraub1954.cell_automata.main.Parameters;
import com.gmail.johnstraub1954.cell_automata.main.RLEDescriptor;

public class MenuBar extends JMenuBar
{
    /** Generated serial version ID. */
    private static final long serialVersionUID = -1480572392683031783L;

    private final PreferencesDialog preferencesDialog   = 
        new PreferencesDialog();
    
    private final Parameters        params              = Parameters.INSTANCE;
    
    private final JFileChooser      fileChooser         = new JFileChooser();
    
    private final JMenu fileMenu;
    private final JMenu windowMenu;
    
    public MenuBar()
    {
        final int   ctrlMask    = ActionEvent.CTRL_MASK;
        KeyStroke   keyStroke   = null;
        
        fileMenu = new JMenu( "File" );
        fileMenu.setMnemonic( KeyEvent.VK_F );
        
        JMenuItem   newItem     = new JMenuItem( "New", KeyEvent.VK_N );
        keyStroke = KeyStroke.getKeyStroke( KeyEvent.VK_N, ctrlMask );
        newItem.setAccelerator( keyStroke );
        newItem.addActionListener( e -> newPattern() );
        fileMenu.add( newItem );
        
        JMenuItem   openItem    = new JMenuItem( "Open", KeyEvent.VK_O );
        keyStroke = KeyStroke.getKeyStroke( KeyEvent.VK_O, ctrlMask );
        openItem.setAccelerator( keyStroke );
        openItem.addActionListener( e -> openPattern() );
        fileMenu.add( openItem );

        JMenuItem   saveItem    = new JMenuItem( "Save", KeyEvent.VK_S );
        keyStroke = KeyStroke.getKeyStroke( KeyEvent.VK_S, ctrlMask );
        saveItem.setAccelerator( keyStroke );
        saveItem.addActionListener( e -> save() );
        fileMenu.add( saveItem );

        JMenuItem   saveAsItem  = new JMenuItem( "Save As", KeyEvent.VK_A );
        keyStroke = KeyStroke.getKeyStroke( KeyEvent.VK_A, ctrlMask );
        saveAsItem.setAccelerator( keyStroke );
        saveAsItem.addActionListener( e -> saveAs() );
        fileMenu.add( saveAsItem );

        add( fileMenu );
        
        windowMenu = new JMenu( "Window" );
        windowMenu.setMnemonic( KeyEvent.VK_W );
        JMenuItem   preferencesItem     = 
            new JMenuItem( "Preferences", KeyEvent.VK_P );
        keyStroke = KeyStroke.getKeyStroke( KeyEvent.VK_P, ctrlMask );
        preferencesItem.setAccelerator( keyStroke );
        preferencesItem.addActionListener( 
            e -> preferencesDialog.setVisible( true ) );
        windowMenu.add( preferencesItem );
        add( windowMenu );
    }
    
    private void save()
    {
        String  currFile    = params.getPatternFileName();
        if ( currFile == null || currFile.isEmpty() )
            saveAs();
        else
            save( new File( currFile ) );
    }
    
    private void saveAs()
    {
        int result  = fileChooser.showSaveDialog( null );
        if ( result == JFileChooser.APPROVE_OPTION )
        {
            save( fileChooser.getSelectedFile() );
        }
    }
    
    private void save( File fileName )
    {
        RLEDescriptor   descrip = new RLEDescriptor( params );
        try ( PrintStream pStream = new PrintStream( fileName ); )
        {
            descrip.getComments().forEach( pStream::println );
            pStream.println( descrip.getHeaderLine() );
            descrip.getEncodedGrid().forEach( pStream::println );
        }
        catch ( IOException exc )
        {
            exc.printStackTrace();
            String  message =
                "Save operation failed: " + exc.getMessage();
            JOptionPane.showMessageDialog( 
                null, 
                "File Save Failure", 
                message,
                JOptionPane.ERROR_MESSAGE 
            );
        }
    }
    
    private void openPattern()
    {
        int openStatus  = JOptionPane.OK_OPTION;
        if ( params.getModifiedPatternData() )
        {
            openStatus = doYouWantToSave();
            if ( openStatus == JOptionPane.OK_OPTION )
                save();
        }
        
        if ( openStatus != JOptionPane.CANCEL_OPTION )
        {
            int openFileStatus  = fileChooser.showOpenDialog( null );
            if ( openFileStatus == JFileChooser.APPROVE_OPTION )
            {
                File    file    = fileChooser.getSelectedFile();
                URLManager.open( file );
            }
        }
    }
    
    private void newPattern()
    {
        int newStatus   = JOptionPane.OK_OPTION;
        if ( params.getModifiedPatternData() )
        {
            newStatus = doYouWantToSave();
            if ( newStatus == JOptionPane.OK_OPTION )
                save();
        }
        
        if ( newStatus != JOptionPane.CANCEL_OPTION )
        {
            params.initPatternParameters();
            params.reset();
        }
    }
    
    private int doYouWantToSave()
    {
        final String    message =
            "Do you want to save the current pattern?";
        int     rcode   = JOptionPane.showConfirmDialog( null, message );
        return rcode;
    }
}
