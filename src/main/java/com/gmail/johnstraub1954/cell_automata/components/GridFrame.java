package com.gmail.johnstraub1954.cell_automata.components;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GridFrame implements Runnable
{
    private final JFrame        frame;

    public GridFrame()
    {
        frame = new JFrame( "Conway's Game of Life" );
    }

    public void run()
    {
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        JPanel  contentPane = new JPanel( new BorderLayout() );
        
        contentPane.add( new MenuBar(), BorderLayout.NORTH );
        contentPane.add( new GridOuterPanel(), BorderLayout.CENTER );
        contentPane.add( new Controls(), BorderLayout.WEST );
        frame.setContentPane( contentPane );
        frame.pack();
        frame.setVisible( true );
        
    }
    
    private class Controls extends JPanel
    {
        /** Generated serial version ID. */
        private static final long serialVersionUID = -9042948552168026706L;

        private final float     separatorLength = .9f;
        public Controls()
        {
            setLayout( new GridBagLayout() );
            GridBagConstraints  gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.NORTH;
            gbc.gridx = 0;
            gbc.gridy = 0;
            
            add( new HSeparator( separatorLength ), gbc );
            gbc.gridy++;

            GeneratorPanel  genPanel    = new GeneratorPanel();
            genPanel.setAlignmentY( Component.TOP_ALIGNMENT );
            add( genPanel, gbc );
            gbc.gridy++;
            
            add( new HSeparator( separatorLength ), gbc );
            gbc.gridy++;

            ControlsPanel   conPanel   = new ControlsPanel();
            conPanel.setAlignmentY(Component.TOP_ALIGNMENT );
            add( conPanel, gbc );
            gbc.gridy++;
            
            add( new HSeparator( separatorLength ), gbc );
            gbc.gridy++;
            
            URLPanel        urlPanel    = new URLPanel();
            urlPanel.setAlignmentY( Component.TOP_ALIGNMENT );
            add( urlPanel, gbc );
            gbc.gridy++;
            
            add( new HSeparator( separatorLength ), gbc );
            gbc.gridy++;
            
            // the BorderLayout in the parent is going to try very hard
            // to center its children. To defeat this, create an empty
            // component in the last row, and configure it to fill all
            // remaining empty space in this panel.
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weighty = 1;
            add( new JPanel(), gbc );
        }
    }
}
