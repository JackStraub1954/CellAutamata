package com.gmail.johnstraub1954.cell_automata.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.gmail.johnstraub1954.cell_automata.main.CAConstants;
import com.gmail.johnstraub1954.cell_automata.main.Parameters;

/**
 * An object of this class represents the outer panel that encloses
 * the panel that holds the actual grid.
 * It's purpose is to put margins/boundaries (if any) on the grid,
 * and to size the actual grid so that it is an even multiple 
 * of the grid cell size.
 * This relieves the actual grid of taking into account
 * things like margins and boundaries when calculating locations
 * inside the grid.
 * 
 * @author Jack Straub
 *
 */
public class GridOuterPanel extends JPanel
{
    /** Generated serial version UID */
    private static final long serialVersionUID = -2104864620566857282L;
    /** The Parameters singleton. */
    private final Parameters    params  = Parameters.INSTANCE;
    /** The panel that holds the actual grid */
    private final Grid          gridPanel   = new Grid();
    
    /** 
     * Size of margin to place around the grid.
     * TODO this should be implemented as a property
     */
    private static final int    gridMargin  = 16;
    
    /**
     * Constructor.
     */
    public GridOuterPanel()
    {
        setName( CAConstants.GRID_OUTER_PANEL_CN );

        // the outer panel is going to be responsible for laying out the
        // grid panel, so get rid of the grid panel's layout manager.
        gridPanel.setLayout( null );
        gridPanel.setName( CAConstants.GRID_PANEL_CN );
        add( gridPanel );
        
        // the preferred size of the outer panel
        // TODO make this a property
        Dimension   prefSize    = new Dimension( 800, 800 );
        setPreferredSize( prefSize );
        
        // the color of the outer panel
        // TODO make this a property
        setBackground( new Color( 0x00bfff ) );
    }
    
    /**
     * Make the size of the grid panel a multiple of the cell size.
     * Center the grid panel inside the outer panel,
     * taking into account grid margins.
     * 
     * @param   graphics    the graphics context to use for drawing
     *                      this component
     */
    @Override
    public void paintComponent( Graphics graphics )
    {
        super.paintComponent( graphics );
        int         cellSize    = params.getGridCellSize();
        int         lineWidth   = params.getGridLineWidth();
        int         width       = getWidth();
        int         height      = getHeight();
        
        int         childWidth  = width - 2 * gridMargin;
        childWidth = (childWidth / cellSize) * cellSize + lineWidth;
        int         childHeight = height - 2 * gridMargin;
        childHeight = (childHeight / cellSize) * cellSize + lineWidth;
        
        Dimension   childDim    = new Dimension( childWidth, childHeight );
        gridPanel.setPreferredSize( childDim );
        gridPanel.setLocation( gridMargin, gridMargin );
    }
}
