package com.gmail.johnstraub1954.cell_automata.extensions;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.gmail.johnstraub1954.cell_automata.components.GridFrame;
import com.gmail.johnstraub1954.cell_automata.main.GridMap;
import com.gmail.johnstraub1954.cell_automata.main.Parameters;

/**
 * Breeds new generations of 1-D automata based on the Wolfram algorithm.
 * 
 * @author Jack Straub
 *          
 * @see <a href="https://mathworld.wolfram.com/ElementaryCellularAutomaton.html">
 *          Wolfram MathWorld: Elementary Cellular Automaton
 *      </a>
 *
 */
public class Wolfram1DAutomaton
{
    /** Workspace parameters */
    private static final Parameters params          = Parameters.INSTANCE;
    
    /** Map containing successive generations. */
    private final GridMap           gridMap         = params.getGridMap();
    
    /** Rule to generate successive generations. */
    private int                     rule;
    
    /** Determines how triplets map to live/dead cells */
    private boolean[]               ruleMap;
    
    /** Row containing the last generation */
    private int                     lastRow         = 0;
    
    /**
     * Entry point.
     * @param args  command line arguments; not used
     */
    public static void main(String[] args)
    {
        GridFrame   frame   = new GridFrame();
        SwingUtilities.invokeLater( () -> frame.run() );
        new Wolfram1DAutomaton();
    }
    
    /**
     * Constructor. 
     * Initializes the grid with a single live cell.
     */
    public Wolfram1DAutomaton()
    {
        // Initialize map; center grid with the occupied cell
        // at the top of the grid.
        gridMap.put( 0, 0, true );
        params.centerGrid();
        params.setGridKeepCentered( true );
        
        // register the propagation proc
        params.setPropagateProc( o -> generateNext( o ) );
        
        // Get the base rule
        rule = getRule();
        
        // Create the rule map
        ruleMap = new boolean[8];
        for ( int inx = 0 ; inx < 8 ; ++inx )
        {
            int bit = 1 << inx;
            ruleMap[inx] = (bit & rule) != 0;
        }
        
        params.reset();
    }
    
    /**
     * Calculate the next generation.
     * 
     * <pre>
     *            -cols       +cols
     *          5 4 3 2 1 0 1 2 3 4 5
     * row 0              x
     * row 1            x x x
     * row 2          x x x x x
     * row 3        x x x x x x x
     * row 4      x x x x x x x x x
     * row 5    x x x x x x x x x x x</pre>
     * 
     * @param   obj control object (not used)
     */
    private void generateNext( Object obj )
    {
        // first cell of first triplet of row 0 is -2...
        // first cell of first triplet of row 1 is -3...
        // etc.
        int         firstTrip   = -lastRow - 2;
        
        // first cell of last triplet of row 0 is 0...
        // first cell of last triplet of row 1 is 1...
        // etc.
        int         lastTrip    = lastRow;
        
        for ( int xco = firstTrip ; xco <= lastTrip ; ++xco )
            calculateChild( xco, lastRow );
        ++lastRow;
        
        params.reset();
    }
    
    private void calculateChild( int xco, int yco )
    {
        int triplet = 0;
        if ( gridMap.get( xco, yco ).isAlive() )
            triplet = 4; // 1 << 2
        if ( gridMap.get( xco + 1, yco ).isAlive() )
            triplet += 2; // 1 << 1
        if ( gridMap.get( xco + 2, yco ).isAlive() )
            triplet += 1; // 1 << 0
        
        boolean alive   = ruleMap[triplet];
        // set the center cell of the triplet
        // on the row beneath "row"
        gridMap.put( xco + 1, yco + 1, alive );
    }
    
    /**
     * Ask the operator for the generation rule.
     * 
     * @return the generation rule
     */
    private static int getRule()
    {
        final String    prompt  = "Enter the generation rule";
        String          input   = null;
        int             rule    = 0;
        boolean         done    = false;
        while ( !done )
        {
            input = JOptionPane.showInputDialog( prompt );
            if ( input == null )
                System.exit( 0 );
            try
            {
                rule = Byte.parseByte( input );
                done = true;
            }
            catch ( NumberFormatException exc )
            {
                String  msg = "\"" + input + "\" "
                    + "is not valid input.";
                JOptionPane.showMessageDialog( null, msg );
            }
        }
        
        return rule;
    }
}
