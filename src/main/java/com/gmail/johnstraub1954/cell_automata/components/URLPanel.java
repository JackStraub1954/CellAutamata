package com.gmail.johnstraub1954.cell_automata.components;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.gmail.johnstraub1954.cell_automata.main.CAConstants;
import com.gmail.johnstraub1954.cell_automata.main.Parameters;

/**
 * Encapsulates control over the URL used to populate
 * the application's GridMap.
 * The user can drag/drop a URL or file location to a text field,
 * or launch a file selection dialog.
 * 
 * @author Jack Straub
 *
 */
public class URLPanel extends JPanel
{
    /** Generated serial version ID. */
    private static final long serialVersionUID = -7703373313637269549L;

    private static final Parameters     params      = Parameters.INSTANCE;
    
    /** Identifier for the drag/drop text field */
    private final JLabel        enterURLLabel       = 
        new JLabel( "Enter URL or Drag/Drop" );
    /** The drag/drop text field */
    private final JTextField    urlTextBox          = new JTextField( 25 );
    /** Button to trigger opening of the URL in the text box */
    private final JButton       openURLButton      =
        new JButton( "Open" );
    /** button to launch file selection dialog */
    private final JButton       selectFileButton    =
        new JButton( "Select File" );
    
    /** Used to process selected URLs */
    private final URLManager    urlManager          =
        new URLManager( urlTextBox );
    
    /**
     * Constructor.
     */
    public URLPanel()
    {
        Dimension   minFiller   = new Dimension( 1, 1 );
        Dimension   prefFiller  = new Dimension( 4, 4 );

        setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );
        
        enterURLLabel.setAlignmentX( Component.CENTER_ALIGNMENT );
        add( enterURLLabel );
        JPanel  dragAndDropPanel    = new JPanel();
        dragAndDropPanel.setLayout( 
            new BoxLayout( dragAndDropPanel, BoxLayout.X_AXIS ) );
        urlTextBox.setAlignmentX( Component.LEFT_ALIGNMENT );
        urlTextBox.setName( CAConstants.URL_TEXT_FIELD_CN );
        
        // In a BoxLayout, a text component will expand to fill
        // available space. Try to prevent that from happening.
        urlTextBox.setMaximumSize( urlTextBox.getPreferredSize() );
        
        dragAndDropPanel.add( urlTextBox );
        openURLButton.setAlignmentX(Component.RIGHT_ALIGNMENT );
        openURLButton.addActionListener( e -> urlManager.open() );
        openURLButton.setName( CAConstants.URL_OPEN_BUTTON_CN );
        dragAndDropPanel.add( openURLButton );
        add( dragAndDropPanel );
        
        // a bit more space between the drag/drop box and the 
        // and the selectButton
        add( new Box.Filler( minFiller, prefFiller, prefFiller ) );

        selectFileButton.setAlignmentX( Component.CENTER_ALIGNMENT );
        selectFileButton.addActionListener( e-> selectFile() );
        selectFileButton.setName( CAConstants.URL_SELECT_FILE_BUTTON_CN );
        add( selectFileButton );
    }
    
    private void selectFile()
    {
        int status  = urlManager.selectFile( this );
        if ( status == JFileChooser.APPROVE_OPTION )
            urlManager.open();
        params.reset();
    }
}
