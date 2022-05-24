package com.gmail.johnstraub1954.cell_automata.main;

import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.GRID_CELL_CLICKED_PN;

import javax.swing.SwingUtilities;

import com.gmail.johnstraub1954.cell_automata.components.GridFrame;

/**
 * This class contains the main method that boots the 
 * Game of Life GUI.
 * 
 * @author Jack Straub
 *
 */
public class Main
{
    private static final Parameters params  = Parameters.INSTANCE;
    
    /**
     * Boots the Game of Life GUI.
     * 
     * @param args  command line arguments; not used
     */
    public static void main(String[] args)
    {
        GridFrame   frame   = new GridFrame();
        SwingUtilities.invokeLater( () -> frame.run() );
        params.addNotificationListener( 
            GRID_CELL_CLICKED_PN, 
            e -> {
                Cell cell = (Cell)e.getSource();
                cell.setAlive( !cell.isAlive() );
                System.out.println( cell );
                Parameters.INSTANCE.getGridMap().put( cell );
                Parameters.INSTANCE.reset();
            });
        
        try
        {
            Thread.sleep( 1000 );
        }
        catch ( InterruptedException exc )
        {
            
        }
        System.out.println( "latest = " + params.getGridLatestData() );
        System.out.println( "gridMap = " + params.getGridMap() );
        System.out.println( "url = " + params.getGridURL() );
        System.out.println( "file = " + params.getPatternFileName() );
    }
}
