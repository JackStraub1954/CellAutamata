package com.gmail.johnstraub1954.cell_automata.components;

import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JDialog;
import javax.swing.JPanel;

import com.gmail.johnstraub1954.cell_automata.main.ActionRegistrar;
import com.gmail.johnstraub1954.cell_automata.main.CAConstants;

public class PreferencesDialog extends JDialog
{
    /** Generated serial version ID. */
    private static final long serialVersionUID = -6023537429830506759L;

    private final ActionRegistrar   actionRegistrar = new ActionRegistrar();
    public PreferencesDialog()
    {
        this.setName( CAConstants.PREF_DLG_CN );
        setTitle( "Preferences" );
        setModal( true );
        JPanel  contentPane = new JPanel( new BorderLayout() );
        setContentPane( contentPane );
        
        JPanel  propertiesPanel = new GridPropertiesPanel( actionRegistrar );
        add( propertiesPanel, BorderLayout.WEST );
        propertiesPanel = 
            new PatternPropertiesPanel( actionRegistrar );
        add( propertiesPanel, BorderLayout.EAST );
        add( new ButtonPanel( actionRegistrar ), BorderLayout.SOUTH );
        addComponentListener(
            new ComponentAdapter() {
                @Override
                public void componentShown( ComponentEvent e ) {
                    System.out.println( "dialog shown" );
                    actionRegistrar.
                        fireNotificationEvent( CAConstants.ACTION_OPENED_PN );
                }
            }
        );
        pack();
        
        actionRegistrar.addNotificationListener(
            CAConstants.ACTION_CANCEL_PN, e -> setVisible( false )
        );
        actionRegistrar.addNotificationListener(
            CAConstants.ACTION_OKAY_PN, e -> setVisible( false )
        );
    }
}
