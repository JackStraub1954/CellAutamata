package test_util;

import java.awt.geom.Path2D;
import java.io.Serializable;

import com.gmail.johnstraub1954.cell_automata.geometry.Hex;
import com.gmail.johnstraub1954.cell_automata.geometry.HexTile;

/**
 *  An instance of this class encapsulates a <em>Path2D</em> object
 *  to be used for testing. In addition to the path, the instance
 *  contains the axial coordinates (qco, rco), orientation (HORIZONTAL
 *  or VERTICAL) and the length of a side of the source hexagon,
 *  all of which are needed to reproduce the path for testing purposes.
 *  If a new path is generated using the data enclosed herein,
 *  the new path should be equal to the precompiled path.
 * 
 * @author Jack Straub
 *
 */
public class PathSaver implements Serializable
{
    /** Generated serial version UID */
    private static final long serialVersionUID = -5638881157947719636L;
    
    /** The axial coordinates at which to construct a path. */
    private final Hex           hexCoordinates;
    /** The length of the side of the hexagon used to construct a path. */
    private final double        side;
    /** The orientation of the hexagon used to construct a path. */
    private final int           orientation;
    /** 
     * The path previously constructed for a hexagon with the given
     * axial coordinates, side and orientation.
     * During testing, this path can be used to verify the validity of
     * a new, dynamically generated path.
     * 
     * Note that the type of this member cannot be Path2D,
     * because Path2D is not serializable.
     * Path2D.Double, however, is.
     * 
     * @see #generatePath()
     */
    private final Path2D.Double preCompiledPath;
    
    /**
     * Constructor.
     * It is assumed that, at the time of construction,
     * the HexTile will produce a valid path.
     * 
     * @param tile  the tile to use to produce the path
     * @param hex   the axial coordinates at which to center the path
     * 
     */
    public PathSaver( HexTile tile, Hex hex )
    {
        hexCoordinates = new Hex( hex );
        side = tile.getSideLen();
        orientation = tile.getOrientation();
        Path2D  path2D = tile.getPath( hex );
        preCompiledPath = new Path2D.Double( path2D );
    }
    
    public Path2D generatePath()
    {
        HexTile tile    = HexTile.ofSide( side, orientation );
        Path2D  path    = tile.getPath( hexCoordinates );
        return path;
    }
    
    /**
     * Gets the axial coordinates associated with this object.
     * 
     * @return the hexCoordinates associated with this object
     */
    public Hex getHexCoordinates()
    {
        return hexCoordinates;
    }

    /**
     * Gets the length of a side used to construct the hexagon
     * associated with this object.
     * 
     * @return the side length of the associated hexagon
     */
    public double getSideLen()
    {
        return side;
    }

    /**
     * Gets the orientation associated with this path.
     * 
     * @return the orientation associated with this path
     */
    public int getOrientation()
    {
        return orientation;
    }

    /**
     * Gets the precompiled path to use for testing with this data.
     * 
     * @return the preCompiledPath
     */
    public Path2D.Double getPreCompiledPath()
    {
        return preCompiledPath;
    }
    
    @Override
    public String toString()
    {
        int     qco             = hexCoordinates.qco;
        int     rco             = hexCoordinates.rco;
        String  orientationStr  = 
            orientation == HexTile.HORIZONTAL ?
            "HORIZONTAL" : "VERTICAL";
        StringBuilder   bldr    = new StringBuilder();
        bldr.append( "(" ).append( qco ).append(",")
            .append( rco ).append( "),")
            .append( side ).append( "," )
            .append( orientationStr );
        return bldr.toString();
    }
}
