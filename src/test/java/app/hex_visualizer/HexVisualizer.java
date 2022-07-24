package app.hex_visualizer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.gmail.johnstraub1954.cell_automata.geometry.HexTile;

/**
 * Tool for visualizing a hex tessalation.
 * 
 * @author java1
 */
public class HexVisualizer
{
    private JFrame      frame       = new JFrame( "Hex Visualizer" );
    private HexTile     tile        = HexTile.ofSide( 20, HexTile.HORIZONTAL ); 
    private Canvas      canvas      = new Canvas( tile );
    private Controls    controls    = new Controls();
	
	public static void main(String[] args)
	{
		HexVisualizer visulaizer  = new HexVisualizer();
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
        private final JRadioButton  horizontal  = 
            new JRadioButton( "Horizontal", true );
        private final JRadioButton  vertical    = 
            new JRadioButton( "Vertical" );

        public Controls()
	    {
	        super( new GridBagLayout() );
	        ButtonGroup    buttonGroup = new ButtonGroup();
	        buttonGroup.add( vertical );
	        buttonGroup.add( horizontal );
	        
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
            add( horizontal, gbc );
            ++gbc.gridx;
            add( vertical, gbc );
            
            JButton apply   = new JButton( "Apply" );
            apply.addActionListener( e -> apply() );
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.gridx = 0;
            ++gbc.gridy;
            gbc.gridwidth = 2;
            add( apply, gbc );
            
            frame.getRootPane().setDefaultButton( apply );
	    }
        
        public void apply()
        {
            try
            {
                int     rWidth      = getInt( rectWidth, "Rect Width" );
                int     rHeight     = getInt( rectHeight, "Rect Height" );
                double  side        = getDouble( sideLength, "Side" );
                int     orientation = 
                    horizontal.isSelected() ?
                    HexTile.HORIZONTAL :
                    HexTile.VERTICAL;
                HexTile tile        = HexTile.ofSide( side, orientation );
                canvas.setTile( tile );
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
	}
}
