package com.gmail.johnstraub1954.cell_automata.components;

import java.awt.Component;
import java.util.EmptyStackException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;

import com.gmail.johnstraub1954.cell_automata.main.CheckpointStack;
import com.gmail.johnstraub1954.cell_automata.main.CAConstants;
import com.gmail.johnstraub1954.cell_automata.main.GridMap;
import com.gmail.johnstraub1954.cell_automata.main.Parameters;

public class ControlsPanel extends JPanel
{
    /** Generated serial version ID. */
    private static final long serialVersionUID = 5123423372989433349L;

    /** Parameters singleton */
    private static final Parameters params          = Parameters.INSTANCE;
    /** minimum cell size */
    private static final int        cellSizeMin     = 0;
    /** maximum cell size (arbitrarily chosen) */
    private static final int        cellSizeMax     = 128;
    /** size of a step when manipulating cell-size spinner */
    private static final int        cellSizeStep    = 1;
    /** initial value for cell size */
    private int cellSize    = params.getGridCellSize();

    /** Identifies the cell-size field */
    private final JLabel                cellSizeLabel   = 
        new JLabel( "Cell Size" );
    /** spinner model for setting cell size */
    private final SpinnerNumberModel    cellSizeModel   =
        new SpinnerNumberModel( 
            cellSize, cellSizeMin, cellSizeMax, cellSizeStep
        );
    /** spinner for setting cell size */
    private final JSpinner              cellSizeSpinner =
        new JSpinner( cellSizeModel );
    
    /** toggle for "grid-on" property */
    private final JCheckBox showGridCheckBox            = 
        new JCheckBox( "Show Grid" ); 
    /** toggle for "keep centered" property */
    private final JCheckBox keepCenteredCheckBox        =
        new JCheckBox( "Keep Grid Centered" );
    /** button to launch "center grid" notification */
    private final JButton   centerGridButton            =
        new JButton( "Center Grid" );
    
    /** Layout adjustment: cell-size components are added to a sub-panel */
    private final JPanel    cellSizePanel               = new JPanel();
    /** Layout adjustment: check boxes are added to a sub-panel */
    private final JPanel    checkBoxPanel               = new JPanel();
    
    /**
     * Constructor.
     */
    public ControlsPanel()
    {
        setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );
        this.setAlignmentX( Component.CENTER_ALIGNMENT );
        
        cellSizeLabel.setAlignmentX( Component.CENTER_ALIGNMENT );
        cellSizeLabel.setHorizontalAlignment( JLabel.CENTER );
        cellSizeSpinner.setAlignmentX( Component.CENTER_ALIGNMENT );
        cellSizeSpinner.setMaximumSize( 
            cellSizeSpinner.getPreferredSize() );
        cellSizeSpinner.addChangeListener( e -> tweakCellSize( e ) );
        cellSizeSpinner.setName( CAConstants.CTRL_CELL_SIZE_COMP_CN );
        
        cellSizePanel.setLayout(
            new BoxLayout( cellSizePanel, BoxLayout.X_AXIS ) );
        cellSizePanel.setAlignmentX( Component.CENTER_ALIGNMENT );
        cellSizePanel.add( cellSizeLabel );
        cellSizePanel.add( cellSizeSpinner );
        add( cellSizePanel );
        
        checkBoxPanel.setLayout( 
            new BoxLayout( checkBoxPanel, BoxLayout.Y_AXIS ));
        checkBoxPanel.setAlignmentX( Component.CENTER_ALIGNMENT );
        checkBoxPanel.add( showGridCheckBox );
        checkBoxPanel.add( keepCenteredCheckBox );
        keepCenteredCheckBox
            .setName( CAConstants.CTRL_KEEP_CENTERED_COMP_CN );
        
        showGridCheckBox.setSelected( params.isGridLineShow() );
        showGridCheckBox.addChangeListener( e -> tweakGridOn( e ) );
        keepCenteredCheckBox.
            addChangeListener( e -> tweakKeepGridCentered( e ) );
        add( checkBoxPanel );
        
        centerGridButton.setAlignmentX( Component.CENTER_ALIGNMENT );
        centerGridButton.addActionListener( e -> params.centerGrid() );
        centerGridButton.setName( CAConstants.CTRL_CENTER_COMP_CN );
        add( centerGridButton );
        
        CheckpointPanel cpPanel = new CheckpointPanel();
//        cpPanel.setAlignmentX( Component.CENTER_ALIGNMENT );
        add( cpPanel );
    }
    
    /**
     * Monitors state changes for the cell-size property.
     * Triggers property change event for CAConstants.GRID_CELL_SIZE_PN.
     * Triggers a notification event for CAConstants.ACTION_RESET_PN.
     * 
     * @param evt   event that encapsulates changes
     *              to the cell-size property
     */
    private void tweakCellSize( ChangeEvent evt )
    {
        Object  source  = evt.getSource();
        if ( !(source instanceof JSpinner) )
            return;
        Object  value   = ((JSpinner)source).getValue();
        if ( value instanceof Number )
        {
            int size    = ((Number)value).intValue();
            params.setGridCellSize( size );
            params.reset();
        }
    }
    
    /**
     * Monitors changes to the state of the show-grid property.
     * Triggers property change event for CAConstants.GRID_LINE_SHOW_PN.
     * Triggers a notification event for CAConstants.ACTION_RESET_PN.
     * 
     * @param evt   encapsulates the state of the grid-on control
     */
    private void tweakGridOn( ChangeEvent evt )
    {
        Object  src = evt.getSource();
        if ( !(src instanceof JToggleButton) )
            return;
        JToggleButton   button  = (JToggleButton)src;
        params.setGridLineShow( button.isSelected() );
        params.reset();
    }
    
    /**
     * Monitors state changes for the keep-grid-centered property.
     * Triggers property change event for CAConstants.GRID_KEEP_CENTERED_PN.
     * Triggers a notification event for CAConstants.ACTION_RESET_PN.
     * 
     * @param evt   event that encapsulates changes
     *              to the keep-grid-centered control
     */
    private void tweakKeepGridCentered( ChangeEvent evt )
    {
        Object  src = evt.getSource();
        if ( !(src instanceof JToggleButton) )
            return;
        JToggleButton   button  = (JToggleButton)src;
        params.setGridKeepCentered( button.isSelected() );
        params.reset();
    }
    
    /**
     * Encapsulates a panel containing two buttons,
     * for pushing a popping a checkpoint.
     * 
     * @author Jack Straub
     */
    private class CheckpointPanel extends JPanel
    {
        /** Generated serial version UID */
        private static final long serialVersionUID = 7138956201507642938L;

        public CheckpointPanel()
        {
            setLayout( new BoxLayout( this, BoxLayout.X_AXIS ) );
            
            Border  titledBorder    =
                BorderFactory.createTitledBorder( "Checkpoint" );
            Border  emptyBorder     =
                BorderFactory.createEmptyBorder( 5, 5, 5, 5 );
            Border  border          =
                BorderFactory
                    .createCompoundBorder( titledBorder, emptyBorder );
            
            setBorder( border );
            
            JButton push    = new JButton( "Push" );
            push.setName( CAConstants.CTRL_PUSH_CP_CN );
            push.setAlignmentX( Component.LEFT_ALIGNMENT );
            push.addActionListener( e -> push() );
            add( push );
            
            add( Box.createHorizontalStrut( 10 ) );
            
            JButton pop     = new JButton( "Pop" );
            pop.setName( CAConstants.CTRL_POP_CP_CN );
            pop.setAlignmentX( Component.RIGHT_ALIGNMENT );
            pop.addActionListener( e -> pop() );
            add( pop );
        }
        
        /**
         * Pushes the current grid map onto the checkpoint stack.
         * Generates a "checkpoint pushed" notification.
         */
        private void push()
        {
            GridMap gridMap = params.getGridMap();
            CheckpointStack.INSTANCE.push( gridMap );
            params.checkpointPushed();
        }
        
        /**
         * Pops the grid map from the top of the checkpoint stack,
         * and installs it as the current grid map.
         * Generates a "checkpoint popped" notification
         * followed by a "reset" notification.
         * 
         * @throws EmptyStackException  if the stack is empty
         */
        private void pop() throws EmptyStackException
        {
            GridMap gridMap = CheckpointStack.INSTANCE.pop();
            params.setGridMap( gridMap );
            params.checkpointPopped();
            params.reset();
        }
    }
}