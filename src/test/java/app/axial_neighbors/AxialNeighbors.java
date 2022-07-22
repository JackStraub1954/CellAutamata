package app.axial_neighbors;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * @author Jack Straub
 *
 */
public class AxialNeighbors
{
    private static final Canvas    canvas      = new Canvas();
    
    public static void main(String[] args)
    {
        AxialNeighbors    compiler    = new AxialNeighbors();
        SwingUtilities.invokeLater( () -> compiler.buildGUI() );
        canvas.addMouseListener( new MouseProcessor() );
    }

    private void buildGUI()
    {
        JFrame  frame   = new JFrame( "Path Test Data Compiler" );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        
        JPanel  contentPane = new JPanel( new BorderLayout() );
        contentPane.add( canvas, BorderLayout.CENTER );
        
        frame.setContentPane( contentPane );
        frame.pack();
        frame.setVisible( true );
    }
    
    private static class MouseProcessor extends MouseAdapter
    {
        @Override
        public void mouseClicked( MouseEvent evt )
        {
            int xco     = evt.getX();
            int yco     = evt.getY();
            canvas.setNeighborhood( xco, yco );
        }
    }
}
