package app.quad_visualizer;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.gmail.johnstraub1954.cell_automata.geometry.HexTile;
import com.gmail.johnstraub1954.cell_automata.geometry.Neighborhood;
import com.gmail.johnstraub1954.cell_automata.geometry.QuadTile;

import test_util.TestConstants;

/**
 * Tool for visualizing a hex tessalation.
 * 
 * @author java1
 */
public class QuadVisualizer
{
    private JFrame      frame       = new JFrame( "Hex Visualizer" );
    private QuadTile    tile        = QuadTile.ofSide( 20 ); 
    private Canvas      canvas      = new Canvas( tile );
    private Controls    controls    = new Controls();
	
	public static void main(String[] args)
	{
		QuadVisualizer visulaizer  = new QuadVisualizer();
		SwingUtilities.invokeLater( () -> visulaizer.buildGUI() );
		visulaizer.execute();
	}

	private void buildGUI()
	{
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        JPanel    contentPane = new JPanel( new BorderLayout() );
		frame.setContentPane( contentPane );
		
		// Adding the Controls panel directly to the west side
		// of the border layout will cause it to be centered
		// vertically. To align it at the top, create an empty panel,
		// add the Controls panel to the empty panel, then add the
		// empty panel to the west side.
		JPanel    emptyPanel  = new JPanel();
		emptyPanel.add( controls );
		contentPane.add( canvas, BorderLayout.CENTER );
		contentPane.add( emptyPanel, BorderLayout.WEST );
		frame.pack();
		frame.setVisible( true );
	}
    
    private void execute()
    {
        controls.apply();
    }
	
	private class Controls extends JPanel
	{
        private final JTextField    rectWidth   = new JTextField( "500", 6 );
        private final JTextField    rectHeight  = new JTextField( "500", 6 );
        private final JTextField    sideLength  = new JTextField( "20", 6 );
        private final JRadioButton  oddR        = 
                new JRadioButton( "ODD-R", true );
        private final JRadioButton  evenR       = 
            new JRadioButton( "EVEN-R" );
        private final JRadioButton  oddQ        = 
                new JRadioButton( "ODD-Q", true );
        private final JRadioButton  evenQ       = 
            new JRadioButton( "EVEN-Q" );

        public Controls()
	    {
	        super( new GridBagLayout() );
	        ButtonGroup    buttonGroup = new ButtonGroup();
            buttonGroup.add( evenR );
            buttonGroup.add( oddR );
            buttonGroup.add( evenQ );
            buttonGroup.add( oddQ );
	        
	        GridBagConstraints gbc = new GridBagConstraints();
	        gbc.anchor = GridBagConstraints.WEST;
            
	        gbc.gridx = 0;
	        gbc.gridy = 0;
            add( new JLabel( "Rect Width" ), gbc );
            ++gbc.gridx;
            add( rectWidth, gbc );
            
            gbc.gridx = 0;
            ++gbc.gridy;
            add( new JLabel( "Rect Height" ), gbc );
            ++gbc.gridx;
            add( rectHeight, gbc );
            
            gbc.gridx = 0;
            ++gbc.gridy;
            add( new JLabel( "Side Length" ), gbc );
            ++gbc.gridx;
            add( sideLength, gbc );
            
            gbc.gridx = 0;
            ++gbc.gridy;
            add( oddR, gbc );
            ++gbc.gridx;
            add( evenR, gbc );
            
            gbc.gridx = 0;
            ++gbc.gridy;
            add( oddQ, gbc );
            ++gbc.gridx;
            add( evenQ, gbc );
            
            JButton apply   = new JButton( "Apply" );
            apply.addActionListener( e -> apply() );
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.gridx = 0;
            ++gbc.gridy;
            gbc.gridwidth = 2;
            add( apply, gbc );
            
            JButton save    = new JButton( "Save Test Data" );
            save.addActionListener( e -> saveTestData() );
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.gridx = 0;
            ++gbc.gridy;
            gbc.gridwidth = 2;
            add( save, gbc );

            JEditorPane message = new JEditorPane();
            String      text    =
                "<html>"
                + "<p style=\"font-size: 125%;\"><em><strong>"
                + "click on a tile<br/>"
                + "to see the tile's<br/>"
                + "neighborhood"
                + "</strong></em></p></html>";
            message.setEditable( false );
            message.setBackground( getBackground() );
            message.setContentType( "text/html" );
            message.setText( text );
            gbc.gridx = 0;
            ++gbc.gridy;
            gbc.gridwidth = 2;
            add( message, gbc );
            
            frame.getRootPane().setDefaultButton( apply );
	    }
        
        public void apply()
        {
            try
            {
                int         rWidth      = getInt( rectWidth, "Rect Width" );
                int         rHeight     = getInt( rectHeight, "Rect Height" );
                double      side        = getDouble( sideLength, "Side" );
                QuadTile    tile        = QuadTile.ofSide( side );
                canvas.setTile( tile ) ;
                canvas.setRect( rWidth, rHeight );
                canvas.repaint();
            }
            catch ( Error exc )
            {
            }
        }
        
        private int getInt( JTextField textField, String comment )
        {
            String  text    = textField.getText();
            int     result  = 0;
            try
            {
                result = Integer.parseInt( text );
            }
            catch ( NumberFormatException exc )
            {
                String  message = 
                    comment + " \"" + text + "\": invalid integer format";
                JOptionPane.showMessageDialog( null, message );
            }
            
            return result;
        }
        
        private double getDouble( JTextField textField, String comment )
        {
            String  text    = textField.getText();
            double  result  = 0;
            try
            {
                result = Double.parseDouble( text );
            }
            catch ( NumberFormatException exc )
            {
                String  message = 
                    comment + " \"" + text + "\": invalid decimal format";
                JOptionPane.showMessageDialog( null, message );
                throw new Error();
            }
            
            return result;
        }
        
        private void saveTestData()
        {
            List<Neighborhood>      list        = 
                canvas.getAllNeighborhoods();
            if ( list.isEmpty() )
            {
                String  message = "No test data to save.";
                JOptionPane.showMessageDialog( null, message );
            }
            else
                saveTestData( list );
        }
        
        private void saveTestData( List<Neighborhood> list )
        {
//            String  testPath    =
//                TestConstants.TEST_RESOURCES + 
//                TestConstants.HEX_NEIGHBOR_TEST_DATA;
//            File    file    = new File( testPath );
//            try ( FileOutputStream fileStream = new FileOutputStream( file );
//                  ObjectOutputStream objStream = 
//                      new ObjectOutputStream( fileStream ) )
//            {
//                objStream.writeObject( list );
//            }
//            catch ( IOException exc )
//            {
//                exc.printStackTrace();
//                System.exit( 1 );
//            }
        }
	}
}
