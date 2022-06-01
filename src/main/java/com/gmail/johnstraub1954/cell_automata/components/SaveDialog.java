package com.gmail.johnstraub1954.cell_automata.components;

import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JDialog;
import javax.swing.JPanel;

import com.gmail.johnstraub1954.cell_automata.main.ActionRegistrar;
import com.gmail.johnstraub1954.cell_automata.main.CAConstants;

public class SaveDialog extends JDialog
{
    /** Generated serial version ID. */
    private static final long serialVersionUID = 6302652798011264916L;

    private final ActionRegistrar   actionRegistrar = new ActionRegistrar();

    public SaveDialog()
    {
        this.setName( CAConstants.SAVE_DLG_CN );
        setTitle( "Save As" );
        setModal( true );
        JPanel  contentPane = new JPanel( new BorderLayout() );
        setContentPane( contentPane );
        
        JPanel  propertiesPanel =  
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
