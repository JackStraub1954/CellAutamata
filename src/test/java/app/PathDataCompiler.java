package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.gmail.johnstraub1954.cell_automata.geometry.Hex;
import com.gmail.johnstraub1954.cell_automata.geometry.HexTile;

import test_util.PathSaver;
import test_util.TestConstants;

public class PathDataCompiler
{
    private final Canvas  canvas    = new Canvas();
    List<PathSaver> dynamicPaths    = new ArrayList<>();
    
    private static final String newl        = System.lineSeparator();
    private static final String continueMsg =
        "OK to continue;" + newl + "Cancel to exit";
    private static final String saveMsg     =
        "OK to save data;" + newl + "Cancel to discard data and exit";
    
    private HexTile hexTile         = null;
    private static final int    startQco    = -3;
    private static final int    endQco      = 5;
    private static final int    startRco    = -3;
    private static final int    endRco      = 5;
    
    public static void main(String[] args)
    {
        PathDataCompiler    compiler    = new PathDataCompiler();
        SwingUtilities.invokeLater( () -> compiler.buildGUI() );
        
        List<PathSaver> allPaths        = new ArrayList<>();
        int[]           orientations    = 
            { HexTile.HORIZONTAL, HexTile.VERTICAL };
        for ( int orientation : orientations )
        {
            for ( int side = 25 ; side < 45 ; side += 5 )
            {
                HexTile tile    = HexTile.ofSide( side, orientation );
                compiler.setTile( tile );
                compiler.refresh();
                
                int status  = 
                    JOptionPane.showConfirmDialog( null, continueMsg );
                if ( status == JOptionPane.CANCEL_OPTION )
                {
                    System.exit( 0 );
                }
                allPaths.addAll( compiler.dynamicPaths );
            }
        }
        int status  =
            JOptionPane.showConfirmDialog( null, saveMsg );
        if ( status == JOptionPane.CANCEL_OPTION )
            System.exit( 1 );
        allPaths.forEach( p -> compiler.savePath( p ) );
    }
    
    private void savePath( PathSaver saver )
    {
        // Presumably the path from the project root to the 
        // test data files in the test/resources folder.
        final String    pathSaverPath   = 
            TestConstants.TEST_RESOURCES
            + TestConstants.PATH_SAVER_DIR;
        final String    intFmt          = "_%+04d";
        final String    doubleFmt       = "_%04.2f";
            
        
        // Construct a unique file name, using the orientation
        // and coordinates of the path.
        Hex             hex     = saver.getHexCoordinates();
        int             qco     = hex.qco;
        int             rco     = hex.rco;
        double          side    = saver.getSideLen();
        String          orient  =
            saver.getOrientation() == HexTile.HORIZONTAL ? "_H" : "_V";
        StringBuilder   bldr    = new StringBuilder();
        bldr.append( pathSaverPath )
            .append( TestConstants.PATH_SAVER_FILE_PREFIX )
            .append( String.format( intFmt, qco ) )
            .append( String.format( intFmt, rco ) )
            .append( String.format( doubleFmt, side ) )
            .append( orient )
            .append( TestConstants.PATH_SAVER_FILE_SUFFIX );
        String  pathName    = bldr.toString();
        try ( 
            FileOutputStream fileStream = new FileOutputStream( pathName );
            ObjectOutputStream objStream = new ObjectOutputStream( fileStream )
        )
        {
            System.out.println( pathName );
            objStream.writeObject( saver );
        }
        catch ( IOException exc )
        {
            exc.printStackTrace();
            System.exit( 1 );
        }
    }
    
    private void refresh()
    {
        canvas.repaint();
    }
    
    private void setTile( HexTile tile )
    {
        hexTile = tile;
    }

    private void buildGUI()
    {
        JFrame  frame   = new JFrame( "Path Test Data Compiler" );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setContentPane( canvas );
        frame.pack();
        frame.setVisible( true );
    }
    
    private class Canvas extends JPanel
    {        
        /** Generated serial version UID */
        private static final long serialVersionUID = -533078725729554703L;
        private Graphics2D  gtx         = null;
        private FontMetrics fontMetrics = null;

        public Canvas()
        {
            super( null );
            Dimension   size        = new Dimension( 800, 800 );
            setPreferredSize( size );
        }
        
        /**
         * Draw the hexagons designated by the given hexTile,
         * and the coordinate ranges (startQco, endQco)
         * and (startRco, endRco).
         * 
         * Special logic: at the start of each iteration of this method
         * the instance variable <em>dynamicPaths</em> is cleared.
         * Subsequently the paths generated for all hexagons are
         * stored in a PathSaver object, and the PathSaver object
         * is added to <em>dynamicPaths</em>.
         * Presumably the driving code will save these PathSaver
         * objects as needed, and eventually use them to create
         * test data files.
         * 
         * @param   graphics    the graphics context used to draw
         */
        public void paintComponent( Graphics graphics )
        {
            dynamicPaths.clear();

            gtx = (Graphics2D)graphics.create();
            fontMetrics = gtx.getFontMetrics();
            gtx.setRenderingHint( 
                RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON
            );

            int         width       = getWidth();
            int         height      = getHeight();
            gtx.setColor( Color.CYAN );
            gtx.fillRect( 0, 0, width, height );
            gtx.setColor( Color.BLACK );

            for ( int rco = startRco ; rco < endRco ; ++rco )
                for ( int qco = startQco ; qco < endQco ; ++qco )
                {
                    Hex     hex     = new Hex( qco, rco );
                    Path2D  path    = hexTile.getPath( hex );
                    dynamicPaths.add( new PathSaver( hexTile, hex ) );
                    gtx.draw( path );
                    drawCoords( path, hex );
                }
        }
        
        private void drawCoords( Path2D path, Hex hex )
        {
            String      str1    = "q=" + hex.qco;
            String      str2    = "r=" + hex.rco;
            int         height  = fontMetrics.getHeight();
            Rectangle   rect    = path.getBounds();
            
            int         width   = fontMetrics.stringWidth( str1 );
            int         yco     = rect.y + rect.height / 2;
            int         xco     = rect.x + rect.width / 2 - width / 2;
            gtx.drawString( str1, xco, yco );
            
            width = fontMetrics.stringWidth( str2 );
            yco += 3 * height / 4;
            xco = rect.x + rect.width / 2 - width / 2;
            gtx.drawString( str2, xco, yco );
        }
    }
}
