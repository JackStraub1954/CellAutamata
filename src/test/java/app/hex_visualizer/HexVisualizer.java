package app.hex_visualizer;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.gmail.johnstraub1954.cell_automata.geometry.HexTile;
import com.gmail.johnstraub1954.cell_automata.geometry.Polygon;

import test_util.PolygonDescriptor;
import test_util.TestConstants;

/**
 * Tool for visualizing a hex tessalation.
 * 
 * @author java1
 */
public class HexVisualizer
{
    private HexTile     tile    = HexTile.ofSide( 20, HexTile.HORIZONTAL ); 
    private Canvas      canvas  = new Canvas( tile );
	
	public static void main(String[] args)
	{
		HexVisualizer visulaizer  = new HexVisualizer();
		SwingUtilities.invokeLater( () -> visulaizer.buildGUI() );
		visulaizer.execute();
	}

	private void buildGUI()
	{
		JFrame	frame	= new JFrame( "Polygon Data" );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.setContentPane( canvas );
		frame.pack();
		frame.setVisible( true );
	}
	
	private void execute()
	{
	}
}
