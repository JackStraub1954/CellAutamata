package com.gmail.johnstraub1954.cell_automata.components;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.gmail.johnstraub1954.cell_automata.main.ActionRegistrar;
import com.gmail.johnstraub1954.cell_automata.main.CAConstants;

/**
 * Encapsulates the buttons used to dismiss a dialog:
 * OK, Apply or Cancel.
 * Typically displayed at the bottom of a frame.
 * 
 * @author Jack Straub
 * 
 * @see PreferencesDialog
 * @see SaveDialog
 */
public class ButtonPanel extends JPanel
{
    /** Generated serial version ID. */
    private static final long serialVersionUID = 1383961957502672211L;

    private final JButton   applyButton     = new JButton( "Apply" );
    private final JButton   cancelButton    = new JButton( "Cancel" );
    private final JButton   okayButton      = new JButton( "OK" );
    
    /**
     * Constructor.
     * The parent component (typically a dialog) passes an object
     * (the actionRegistrar) through which the buttons in this panel
     * can notify other components of their selection.
     * 
     * @param actionRegistrar   an object through which the buttons in this
     *                          panel can notify other components 
     *                          of their selection
     */
    public ButtonPanel( ActionRegistrar actionRegistrar )
    {
        setLayout( new BoxLayout( this, BoxLayout.X_AXIS ) );
        okayButton.addActionListener(
            e -> actionRegistrar.
                fireNotificationEvent( CAConstants.ACTION_APPLY_PN )
        );
        okayButton.addActionListener(
            e -> actionRegistrar.
                fireNotificationEvent( CAConstants.ACTION_OKAY_PN )
        );
        applyButton.addActionListener(
            e -> actionRegistrar.
                fireNotificationEvent( CAConstants.ACTION_APPLY_PN )
        );
        cancelButton.addActionListener(
            e -> actionRegistrar.
                fireNotificationEvent( CAConstants.ACTION_CANCEL_PN )
        );
        
        applyButton.setName( CAConstants.BP_APPLY_BUTTON_CN );
        cancelButton.setName( CAConstants.BP_CANCEL_BUTTON_CN );
        okayButton.setName( CAConstants.BP_OK_BUTTON_CN );
        
        add( okayButton );
        add( applyButton );
        add( cancelButton );
    }
}
