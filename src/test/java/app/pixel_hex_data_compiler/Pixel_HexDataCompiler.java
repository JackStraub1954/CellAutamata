package app.pixel_hex_data_compiler;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import com.gmail.johnstraub1954.cell_automata.geometry.HexLayout;
import com.gmail.johnstraub1954.cell_automata.geometry.Offset;

import test_util.CvtTestData;
import test_util.TestConstants;

/**
 * @author Jack Straub
 *
 */
public class Pixel_HexDataCompiler
{
    private final Canvas    canvas      = new Canvas();
    
    public static void main(String[] args)
    {
        Pixel_HexDataCompiler    compiler    = new Pixel_HexDataCompiler();
        SwingUtilities.invokeLater( () -> compiler.buildGUI() );
        compiler.paintCanvas();
    }
    
    private void paintCanvas()
    {
        int     col     = 2;
        int     row     = 2;
        
        Offset  offset  = new Offset( col, row );
        canvas.add( offset );
        
        offset = new Offset( col + 2, row );
        canvas.add( offset );
    }
    
    private void setStrategy( HexLayout strategy )
    {
        canvas.setStrategy( strategy );
        paintCanvas();
    }

    private void buildGUI()
    {
        JFrame  frame   = new JFrame( "Pixel-to-Hex Test Data Compiler" );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        
        JPanel  contentPane = new JPanel( new BorderLayout() );
        contentPane.add( canvas, BorderLayout.CENTER );
        contentPane.add( new Controls(), BorderLayout.WEST );
        
        frame.setContentPane( contentPane );
        frame.pack();
        frame.setVisible( true );
    }
    
    private void generateData()
    {
        for ( HexLayout layout : HexLayout.values() )
            generateData( layout );
    }
    
    private void generateData( HexLayout layout )
    {
        double          side    = 25;
        for ( int row = -10 ; row < 11 ; ++row )
            for ( int col = -10 ; col < 11 ; ++col )
            {
                Offset  offset      = new Offset( col, row );
                CvtTestData data    = new CvtTestData( layout, offset, side );
                String  path        = 
                    TestConstants.TEST_RESOURCES + data.getTestFilePath();
                File    file        = new File( path );
                try ( 
                    FileOutputStream fileStream = new FileOutputStream( file );
                    ObjectOutputStream objStream = 
                        new ObjectOutputStream( fileStream ) )
                {
                    objStream.writeObject( data );
                }
                catch ( IOException exc )
                {
                    exc.printStackTrace();
                    System.exit( 1 );
                }
                System.out.println( file.getPath() );
            }
    }
    
    @SuppressWarnings("serial")
    private class Controls extends JPanel
    {
        public Controls()
        {
            setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );
            
            Border  border  =
                BorderFactory.createEmptyBorder( 10, 10, 10 ,10 );
            setBorder( border );
            add( getStrategyOptions() );
            add( getDisplayOptions() );
            add( getGenerateButton() );
        }
        
        private JPanel getStrategyOptions()
        {
            JPanel      panel   = new JPanel();
            panel.setLayout( new BoxLayout( panel, BoxLayout.Y_AXIS ) );
            
            Border      border  =
                BorderFactory.createTitledBorder( "Strategies" );
            panel.setBorder( border );
            
            ButtonGroup group   = new ButtonGroup();
            
            JRadioButton oddR   = new JRadioButton( "Odd-R" );
            oddR.addActionListener( o -> setStrategy( HexLayout.ODD_R ) );
            group.add( oddR );
            panel.add( oddR );
            
            JRadioButton evenR  = new JRadioButton( "Even-R" );
            evenR.addActionListener( o -> setStrategy( HexLayout.EVEN_R ) );
            group.add( evenR );
            panel.add( evenR );
            
            JRadioButton oddQ   = new JRadioButton( "Odd-Q" );
            oddQ.addActionListener( o -> setStrategy( HexLayout.ODD_Q ) );
            group.add( oddQ );
            panel.add( oddQ );

            JRadioButton evenQ  = new JRadioButton( "Even-Q" );
            evenQ.addActionListener( o -> setStrategy( HexLayout.EVEN_Q ) );
            group.add( evenQ );
            panel.add( evenQ );

            oddR.setSelected( true );
            return panel;
        }
        
        private JPanel getDisplayOptions()
        {
            JPanel      panel   = new JPanel();
            panel.setLayout( new BoxLayout( panel, BoxLayout.Y_AXIS ) );
            
            Border      border  =
                BorderFactory.createTitledBorder( "Coordinate Display Type" );
            panel.setBorder( border );
            
            ButtonGroup group   = new ButtonGroup();
            
            final int   typeA    = Canvas.COORD_DISPLAY_AXIAL;
            JRadioButton axial  = new JRadioButton( "Axial" );
            axial.addActionListener( o -> canvas.setCoordDisplay( typeA ) );
            group.add( axial );
            panel.add( axial );
            
            final int   typeO   = Canvas.COORD_DISPLAY_OFFSET;
            JRadioButton offset  = new JRadioButton( "Offset" );
            offset.addActionListener( o -> canvas.setCoordDisplay( typeO ) );
            group.add( offset );
            panel.add( offset );
            
            axial.setSelected( true );
            return panel;
        }
        
        private JComponent getGenerateButton()
        {
            JButton button  = new JButton( "Generate Test Data" );
            button.addActionListener( e -> generateData() );
            return button;
        }
    }
    
    private static class HexRow
    {
        private final int   rco;
        private final int   startQco;
        private final int   qcoCount;
        
        public HexRow( int rco, int startQco, int qcoCount )
        {
            this.rco = rco;
            this.startQco = startQco;
            this.qcoCount = qcoCount;
        }
    }
}
