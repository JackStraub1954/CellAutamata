package com.gmail.johnstraub1954.cell_automata.main;

import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.*;

import java.awt.Color;
import java.awt.Point;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

/**
 * This class provides a single point of access to parameters
 * that may change dynamically during the course of Game of Life execution.
 * For example: 
 * automated regeneration on/off,
 * speed of automated regeneration, etc.
 * Clients can register for property change notification
 * when a parameter changes.
 * 
 * @author Jack Straub
 *
 */
public enum Parameters
{
    /** Singleton instance of this class */
    INSTANCE;

    /** Object to manage property initialization */
    private final ACProperties props   = new ACProperties();
    
    /** Automatic regeneration on/off */
    private boolean             autoRegenerationOn;
    /** Pace of automatic regeneration, in generations per second */
    private float               autoRegenerationPace;
    
    /** Auto-regeneration minimum value (generations per second) */
    private float               autoRegenerationPaceMin;
    
    /** Auto-regeneration maximum value (generations per second) */
    private float               autoRegenerationPaceMax;

    /** Background color of the grid */
    private Color               gridColor;
    /** Top margin of grid */
    private int                 gridMarginTop;
    /** Left margin of grid */
    private int                 gridMarginLeft;
    /** Bottom margin of grid */
    private int                 gridMarginBottom;
    /** Right margin of grid */
    private int                 gridMarginRight;
    /** Grid width <em>in cells</em> */
    private int                 gridWidth;
    /** Grid height <em>in cells</em> */
    private int                 gridHeight;
    
    /** Grid lines on/off */
    private boolean             gridLineShow;
    /** Grid line width; pixels */
    private int                 gridLineWidth;
    /** Grid line color */
    private Color               gridLineColor;
    
    /** Grid cell size (width/height); pixels */
    private int                 gridCellSize;
    /** Grid cell color (for live cells) */
    private Color               gridCellColor;
    /** 
     * Indicates whether the application should try to center the grid
     * with each new generation.
     */
    private boolean             gridKeepCentered;

    /** 
     * The coordinates of the cell to place in the upper-left corner
     * of the physical grid.
     */
    private Point               gridCellOrigin;
    /** 
     * Grid map
     */
    private GridMap             gridMap;
    
    /** The location (URL) of the date to parse and display in the grid. */
    private URL                 gridURL;
    
    /** 
     * Conditions under which a live cell may continue to live.
     */
    private List<Integer>       survivalStates;
    /** 
     * Conditions under which a dead cell may come to life.
     */
    private List<Integer>       birthStates;
    /** The latest data used to populate the grid */
//    private RLEInput            gridLatestData;
    
    /** Name of pattern being documented/displayed */
    private String              patternName;
    /** Name of file containing the pattern being documented/displayed */
    private String              patternFileName;
    /** Name of author of pattern */
    private String              authorName;
    /** Email address of author */
    private String              authorEmail;
    /** 
     * Date/time of pattern creation; to be displayed on the author line
     * of the documentation header
     */
    private LocalDateTime       authorTime;
    
    /** 
     * Set if the GUI-control property,
     * such as line color or cell width,
     * has been modified 
     * since the last save-file operation 
     */
    private boolean             modifiedGUI;
    
    /** 
     * Set if the grid has been modified 
     * since the last save-file operation 
     */
    private boolean             modifiedGrid;
    /** Set if the pattern metadata has been modified 
     * since the last save-file operation 
     */
    private boolean             modifiedMetadata;
    /** 
     * Inclusive-or of the modifiedGrid and modifiedMetadata properties.
     * From the user's perspective,
     * this is a read-only property,
     * so its setter is private. 
     */
    private boolean             modifiedPatternData;
    /**
     * Procedure to invoke when spawning a new generation.
     */
    private Consumer<Object>    propagateProc;
    
    /** Support for PropertyChangeListeners */
    private final PropertyChangeSupport propChangeSupport   = 
        new PropertyChangeSupport( this );
    
    /** 
     * Used to maintain a list of notification listeners.
     * 
     * @see ActionRegistrar
     * @see #addNotificationListener(NotificationListener)
     * @see #addNotificationListener(String, NotificationListener)
     * @see #fireNotificationEvent(String)
     * @see #fireNotificationEvent(String, Object)
     */
    private final ActionRegistrar   actionRegistrar = new ActionRegistrar();
    
    /**
     * Constructor.
     */
    private Parameters()
    {
        autoRegenerationOn = props.getAutoRegenOn();
        autoRegenerationPace = props.getAutoRegenPace();
        autoRegenerationPaceMin = props.getAutoRegenPaceMin();
        autoRegenerationPaceMax = props.getAutoRegenPaceMax();
        
        gridColor = props.getGridColor();
        gridMarginTop = props.getGridMarginTop();
        gridMarginLeft = props.getGridMarginLeft();
        gridMarginBottom = props.getGridMarginBottom();
        gridMarginRight = props.getGridMarginRight();
        gridWidth = props.getGridWidth();
        gridHeight = props.getGridHeight();
        gridLineShow = props.getGridLineShow();
        gridLineWidth = props.getGridLineWidth();
        gridLineColor = props.getGridLineColor();
        gridCellSize = props.getGridCellSize();
        gridCellColor = props.getGridCellColor();
        gridKeepCentered = props.getGridKeepCentered();
        
        propagateProc = props.getPropagateProc();
        
        initPatternParameters();
        addPropertyChangeListener( 
            CAConstants.ACTION_FILE_SAVED_PN,
            e -> setModifiedGrid( false ) 
        );
        addPropertyChangeListener( 
            CAConstants.ACTION_FILE_SAVED_PN,
            e -> setModifiedMetadata( false ) 
        );
    }

    /*
     * Proposed facility to link properties; when one property changes,
     * another also changes/fires.
    private void linkProperties()
    {
        class Linker
        { 
            public final String property; 
            public final Consumer<Boolean> consumer;
            public Linker( String property, Consumer<Boolean> consumer )
            {
                this.property = property;
                this.consumer = consumer;
            }
        }
        Consumer<Boolean>   gridModifier        = 
            e -> setModifiedGrid( true );
        Consumer<Boolean>   metadataModifier    = 
            e -> setModifiedMetadata( true );
            
        Linker[]    linkage =
        {
            new Linker( CTRL_BIRTH_STATES_PN, metadataModifier ),
        };
        // TODO change to switch statement
    }
    */
    
    /**
     * Initialize parameters that are directly correlated with patterns.
     * Examples are survival rules, birth rule and pattern name.
     * These parameters have to be reset each time
     * a new pattern is initiated.
     */
    public void initPatternParameters()
    {
        gridCellOrigin = props.getGridCellOrigin();
        gridURL = props.getGridURL();
//        gridLatestData = props.getGridLatestData();

        survivalStates = props.getSurvivalStates();
        birthStates = props.getBirthStates();        
        patternName = props.getPatternName();
        patternFileName = props.getPatternFileName();
        authorName = props.getAuthorName();
        authorEmail = props.getAuthorEmail();
        authorTime = props.getAuthorTime();
        
        setGridMap( new GridMap() );
    }
    
    /**
     * Adds a property change listener to this object.
     * 
     * @param listener  property change listener to add
     */
    public void addPropertyChangeListener( PropertyChangeListener listener )
    {
        propChangeSupport.addPropertyChangeListener( listener );
    }
    
    /**
     * Adds a per-property property change listener to this object.
     * 
     * @param propName  target property name
     * @param listener  property change listener to add
     */
    public void addPropertyChangeListener( 
        String propName,
        PropertyChangeListener listener 
    )
    {
        propChangeSupport.addPropertyChangeListener( propName, listener );
    }
    
    /**
     * Removes a property change listener from this object.
     * Property names are listed in the CAConstants class.
     * 
     * @param listener  property change listener to remove
     * 
     * @see CAConstants
     */
    public void 
    removePropertyChangeListener( PropertyChangeListener listener )
    {
        propChangeSupport.removePropertyChangeListener( listener );
    }

    /**
     * Removes a per-property property change listener from this object.
     * Property names are listed in the CAConstants class.
     * 
     * @param propName  target property name
     * @param listener  property change listener to remove

     * @see CAConstants
     */
    public void removePropertyChangeListener( 
        String propName,
        PropertyChangeListener listener 
    )
    {
        propChangeSupport.removePropertyChangeListener( propName, listener );
    }
    
    /**
     * Add a given NotificationListener to the list of NotificationListeners.
     * Listeners will be invoked for every notification event.
     * 
     * @param listener  the given NotificationListener
     * 
     * @see ActionRegistrar#addNotificationListener(NotificationListener)
     */
    public void addNotificationListener( NotificationListener listener )
    {
        actionRegistrar.addNotificationListener( listener );
    }
    
    /**
     * Add a given NotificationListener/property pair to the list 
     * of NotificationListeners.
     * Listeners will be invoked for only for property notifications
     * encapsulating the given property.
     * 
     * @param listener  the given NotificationListener
     * @param property  the given property
     * 
     * @see ActionRegistrar#addNotificationListener(String, NotificationListener)
     */
    public void 
    addNotificationListener( String property, NotificationListener listener )
    {
        actionRegistrar.addNotificationListener( property, listener );
    }
    
    /**
     * Remove a given NotificationListener from the 
     * list of NotificationListeners.
     * If the ActionListener is not in the list
     * no action is taken.
     * 
     * @param listener  the given NotificationListener
     * 
     * @see ActionRegistrar#removeNotificationListener(NotificationListener)
     */
    public void removeNotificationListener( NotificationListener listener )
    {
        actionRegistrar.removeNotificationListener( listener );
    }

    /**
     * Gets the autoRegenerationOn parameter.
     * 
     * @return the autoRegenerationOn
     */
    public boolean isAutoRegenerationOn()
    {
        return autoRegenerationOn;
    }

    /**
     * Sets the autoRegenerationOn parameter.
     * 
     * @param autoRegenerationOn the autoRegenerationOn to set
     */
    public void setAutoRegenerationOn(boolean autoRegenerationOn)
    {
        boolean oldVal      = this.autoRegenerationOn;
        String  propName    = AUTO_REGEN_ON_PN;
        this.autoRegenerationOn = autoRegenerationOn;
        propChangeSupport.
        firePropertyChange( propName, oldVal, autoRegenerationOn );
    }

    /**
     * Gets the autoRegenerationPace parameter.
     * 
     * @return the autoRegenerationPace
     */
    public float getAutoRegenerationPace()
    {
        return autoRegenerationPace;
    }

    /**
     * Sets the autoRegenerationPace parameter.
     * 
     * @param autoRegenerationPace the autoRegenerationPace to set
     */
    public void setAutoRegenerationPace(float autoRegenerationPace)
    {
        float   oldVal      = this.autoRegenerationPace;
        String  propName    = AUTO_REGEN_PACE_PN;
        this.autoRegenerationPace = autoRegenerationPace;
        propChangeSupport.
            firePropertyChange( propName, oldVal, autoRegenerationPace );
    }

    /**
     * Gets the auto-regeneration minimum pace parameter.
     * 
     * @return the auto-regeneration minimum pace 
     */
    public float getAutoRegenerationPaceMin()
    {
        return autoRegenerationPaceMin;
    }

    /**
     * Sets the auto-regeneration minimum pace parameter.
     * 
     * @param autoRegenerationPaceMin the autoRegenerationPace to set
     */
    public void setAutoRegenerationPaceMin( float pace )
    {
        float   oldVal      = this.autoRegenerationPaceMin;
        float   newVal      = pace;
        String  propName    = AUTO_REGEN_MIN_PN;
        this.autoRegenerationPaceMin = pace;
        propChangeSupport.
            firePropertyChange( propName, oldVal, newVal );
    }

    /**
     * Gets the auto-regeneration maximum pace parameter.
     * 
     * @return the auto-regeneration maximum pace 
     */
    public float getAutoRegenerationPaceMax()
    {
        return autoRegenerationPaceMax;
    }

    /**
     * Sets the auto-regeneration maximum pace parameter.
     * 
     * @param autoRegenerationPaceMin the auto-regeneration maximum pace
     */
    public void setAutoRegenerationPaceMax(float pace )
    {
        float   oldVal      = this.autoRegenerationPaceMax;
        float   newVal      = pace;
        String  propName    = AUTO_REGEN_MAX_PN;
        this.autoRegenerationPaceMax = pace;
        propChangeSupport.
            firePropertyChange( propName, oldVal, newVal );
    }

    /**
     * Gets the gridColor parameter.
     * 
     * @return the gridColor
     */
    public Color getGridColor()
    {
        return gridColor;
    }

    /**
     * Sets the gridColor parameter.
     * 
     * @param gridColor the gridColor to set
     */
    public void setGridColor(Color gridColor)
    {
        Color   oldVal      = this.gridColor;
        String  propName    = GRID_COLOR_PN;
        this.gridColor = gridColor;
        propChangeSupport.
            firePropertyChange( propName, oldVal, gridColor );
    }

    /**
     * Gets the gridMarginTop parameter.
     * 
     * @return the gridMarginTop
     */
    public int getGridMarginTop()
    {
        return gridMarginTop;
    }

    /**
     * Sets the gridMarginTop parameter.
     * 
     * @param gridMarginTop the gridMarginTop to set
     */
    public void setGridMarginTop(int gridMarginTop)
    {
        int     oldVal      = this.gridMarginTop;
        String  propName    = GRID_MARGIN_TOP_PN;
        this.gridMarginTop = gridMarginTop;
        propChangeSupport.
            firePropertyChange( propName, oldVal, gridMarginTop );
}

    /**
     * Gets the gridMarginLeft parameter.
     * 
     * @return the gridMarginLeft
     */
    public int getGridMarginLeft()
    {
        return gridMarginLeft;
    }

    /**
     * Sets the gridMarginLeft parameter.
     * 
     * @param gridMarginLeft the gridMarginLeft to set
     */
    public void setGridMarginLeft(int gridMarginLeft)
    {
        int     oldVal      = this.gridMarginLeft;
        int     newVal      = gridMarginLeft;
        String  propName    = GRID_MARGIN_LEFT_PN;
        this.gridMarginLeft = gridMarginLeft;
        propChangeSupport.
            firePropertyChange( propName, oldVal, newVal );
    }

    /**
     * Gets the gridMarginBottom parameter.
     * 
     * @return the gridMarginBottom
     */
    public int getGridMarginBottom()
    {
        return gridMarginBottom;
    }

    /**
     * Sets the gridMarginBottom parameter.
     * 
     * @param gridMarginBottom the gridMarginBottom to set
     */
    public void setGridMarginBottom(int gridMarginBottom)
    {
        int     oldVal      = this.gridMarginBottom;
        int     newVal      = gridMarginBottom;
        String  propName    = GRID_MARGIN_BOTTOM_PN;
        this.gridMarginBottom = gridMarginBottom;
        propChangeSupport.
            firePropertyChange( propName, oldVal, newVal );
    }

    /**
     * Gets the gridMarginRigth parameter.
     * 
     * @return the gridMarginRight
     */
    public int getGridMarginRight()
    {
        return gridMarginRight;
    }

    /**
     * Sets the gridMarginRight parameter.
     * 
     * @param gridMarginRight the gridMarginRight to set
     */
    public void setGridMarginRight(int gridMarginRight)
    {
        int     oldVal      = this.gridMarginRight;
        int     newVal      = gridMarginRight;
        String  propName    = GRID_MARGIN_RIGHT_PN;
        this.gridMarginRight = gridMarginRight;
        propChangeSupport.
            firePropertyChange( propName, oldVal, newVal );
    }

    /**
     * Gets the gridWidth parameter.
     * 
     * @return the gridWidth
     */
    public int getGridWidth()
    {
        return gridWidth;
    }

    /**
     * Sets the gridWidth parameter.
     * 
     * @param gridWidth the gridWidth to set
     */
    public void setGridWidth(int gridWidth)
    {
        int     oldVal      = this.gridWidth;
        int     newVal      = gridWidth;
        String  propName    = GRID_WIDTH_PN;
        this.gridWidth = gridWidth;
        propChangeSupport.
            firePropertyChange( propName, oldVal, newVal );
    }

    /**
     * Gets the gridHeight parameter.
     * 
     * @return the gridHeight
     */
    public int getGridHeight()
    {
        return gridHeight;
    }

    /**
     * Sets the gridHeight parameter.
     * 
     * @param gridHeight the gridHeight to set
     */
    public void setGridHeight(int gridHeight)
    {
        int     oldVal      = this.gridHeight;
        int     newVal      = gridHeight;
        String  propName    = GRID_HEIGHT_PN;
        this.gridHeight = gridHeight;
        propChangeSupport.
            firePropertyChange( propName, oldVal, newVal );
    }

    /**
     * Gets the gridLineShow parameter.
     * 
     * @return the gridLineShow
     */
    public boolean isGridLineShow()
    {
        return gridLineShow;
    }

    /**
     * Sets the gridLineShow parameter.
     * 
     * @param gridLineShow the gridLineShow to set
     */
    public void setGridLineShow(boolean gridLineShow)
    {
        boolean oldVal      = this.gridLineShow;
        boolean newVal      = gridLineShow;
        String  propName    = GRID_LINE_SHOW_PN;
        this.gridLineShow = gridLineShow;
        propChangeSupport.
            firePropertyChange( propName, oldVal, newVal );
    }

    /**
     * Gets the gridLineWidth parameter.
     * 
     * @return the gridLineWidth
     */
    public int getGridLineWidth()
    {
        return gridLineWidth;
    }

    /**
     * Sets the gridLineWidth parameter.
     * 
     * @param gridLineWidth the gridLineWidth to set
     */
    public void setGridLineWidth(int gridLineWidth)
    {
        int     oldVal      = this.gridLineWidth;
        int     newVal      = gridLineWidth;
        String  propName    = GRID_LINE_WIDTH_PN;
        this.gridLineWidth = gridLineWidth;
        propChangeSupport.
            firePropertyChange( propName, oldVal, newVal );
    }

    /**
     * Gets the gridLineColor parameter.
     * 
     * @return the gridLineColor
     */
    public Color getGridLineColor()
    {
        return gridLineColor;
    }

    /**
     * Sets the gridLineColor parameter.
     * 
     * @param gridLineColor the gridLineColor to set
     */
    public void setGridLineColor(Color gridLineColor)
    {
        Color   oldVal      = this.gridLineColor;
        Color   newVal      = gridLineColor;
        String  propName    = GRID_LINE_COLOR_PN;
        this.gridLineColor = gridLineColor;
        propChangeSupport.
            firePropertyChange( propName, oldVal, newVal );
    }

    /**
     * Gets the gridCellSize parameter.
     * 
     * @return the gridCellSize
     */
    public int getGridCellSize()
    {
        return gridCellSize;
    }

    /**
     * Sets the gridCellSize parameter.
     * 
     * @param gridCellSize the gridCellSize to set
     */
    public void setGridCellSize(int gridCellSize)
    {
        int     oldVal      = this.gridCellSize;
        int     newVal      = gridCellSize;
        String  propName    = GRID_CELL_SIZE_PN;
        this.gridCellSize = gridCellSize;
        propChangeSupport.
            firePropertyChange( propName, oldVal, newVal );
    }

    /**
     * Gets the gridCellColor parameter.
     * 
     * @return the gridCellColor
     */
    public Color getGridCellColor()
    {
        return gridCellColor;
    }

    /**
     * Sets the gridCellColor parameter.
     * 
     * @param gridCellColor the gridCellColor to set
     */
    public void setGridCellColor(Color gridCellColor)
    {
        Color   oldVal      = this.gridCellColor;
        Color   newVal      = gridCellColor;
        String  propName    = GRID_CELL_COLOR_PN;
        this.gridCellColor = gridCellColor;
        propChangeSupport.
            firePropertyChange( propName, oldVal, newVal );
    }
    
    /**
     * Gets the GridMap managed by this Parameters object.
     * If the parameter has not been set by the user,
     * it is initialized to an empty map.
     * 
     * @return the GridMap managed by this Parameters object
     */
    public GridMap getGridMap()
    {
        if ( gridMap == null )
            gridMap = new GridMap();
        return gridMap;
    }
    
    /**
     * Sets the GridMap managed by this Parameters object.
     * 
     * @return the GridMap managed by this Parameters object
     */
    public void setGridMap( GridMap gridMap )
    {
        GridMap oldVal      = this.gridMap;
        GridMap newVal      = gridMap;
        String  propName    = GRID_MAP_PN;
        this.gridMap = gridMap;
        propChangeSupport.
            firePropertyChange( propName, oldVal, newVal );
    }
    
    /**
     * Gets the list of survival states that controls the state
     * of cells in the next generation.
     * 
     * @return list of survival states
     */
    public List<Integer> getSurvivalStates()
    {
        return survivalStates;
    }
    
    /**
     * Gets the list of survival states that controls the state
     * of cells in the next generation.
     * 
     * @param   list of survival states
     */
    public void setSurvivalStates( List<Integer> survivalStates )
    {
        List<Integer>   oldVal      = this.survivalStates;
        List<Integer>   newVal      = survivalStates;
        String          propName    = CTRL_SURVIVAL_STATES_PN;
        this.survivalStates = survivalStates;
        propChangeSupport.
            firePropertyChange( propName, oldVal, newVal );
    }
    
    /**
     * Gets the list of birth states that controls the state
     * of cells in the next generation.
     * 
     * @return list of birth states
     */
    public List<Integer> getBirthStates()
    {
        return birthStates;
    }
    
    /**
     * Sets the list of birth states that controls the state
     * of cells in the next generation.
     * 
     * @param   list of birth states
     */
    public void setBirthStates( List<Integer> birthStates )
    {
        List<Integer>   oldVal      = this.birthStates;
        List<Integer>   newVal      = birthStates;
        String          propName    = CTRL_BIRTH_STATES_PN;
        this.birthStates = birthStates;
        propChangeSupport.
            firePropertyChange( propName, oldVal, newVal );
    }
    
    public Point getGridCellOrigin()
    {
        return gridCellOrigin;
    }
    
    public void setGridCellOrigin( Point gridCellOrigin )
    {
        Point   oldValue    = this.gridCellOrigin;
        Point   newValue    = gridCellOrigin;
        String  propName    = GRID_CELL_ORIGIN_PN;
        this.gridCellOrigin = gridCellOrigin;
        propChangeSupport.
            firePropertyChange( propName, oldValue, newValue );
    }

    /**
     * Sets a value of the most recent cell on the grid
     * to have been clicked.
     * This method propagates a PropertyChange event
     * for property CAConstants.GRID_CELL_CLICKED_PN.
     * 
     * @param gridCenter    true to center the grid
     */
    public void selectGridCell( Cell cell )
    {
        fireNotificationEvent( GRID_CELL_CLICKED_PN, cell );
    }
    
    /**
     * Returns the value of a URL to use to populate a grid.
     * Will return null if there is no such URL.
     * 
     * @return  the value of a URL for using to populate a grid,
     *          or null if none
     */
    public URL getGridURL()
    {
        return gridURL;
    }
    
    /**
     * Sets the URL of data which may be used to populate the grid.
     * May be null.
     * This method propagates a PropertyChange event
     * for property CAConstants.CTRL_GRID_URL_PN.
     * 
     * @param gridURL
     */
    public void setGridURL( URL gridURL )
    {
        URL     oldValue    = this.gridURL;
        URL     newValue    = gridURL;
        String  propName    = CTRL_GRID_URL_PN;
        this.gridURL = gridURL;
        propChangeSupport.
            firePropertyChange( propName, oldValue, newValue );
    }
    
    /**
     * Returns a value indicating whether the application
     * should attempt to keep the grid centered
     * with each new generation.
     * 
     * @return  a value indicating whether the application
     *          should attempt to keep the grid centered
     *          with each new generation
     */
    public boolean isGridKeepCentered()
    {
        return gridKeepCentered;
    }
    
    /**
     * Sets a value indicating whether the application
     * should attempt to keep the grid centered
     * with each new generation.
     * This method propagates a PropertyChange event
     * for property CAConstants.GRID_KEEP_CENTERED_PN.
     * 
     * @param keepCentered
     */
    public void setGridKeepCentered( boolean keepCentered )
    {
        boolean oldValue    = this.gridKeepCentered;
        boolean newValue    = keepCentered;
        String  propName    = GRID_KEEP_CENTERED_PN;
        this.gridKeepCentered = keepCentered;
        propChangeSupport.
            firePropertyChange( propName, oldValue, newValue );
    }
    
    /**
     * Returns an object representing the latest data used to
     * populate the grid.
     * 
     * @return  an object representing the latest data used to
     *          populate the grid
     */
//    public RLEInput getGridLatestData()
//    {
//        return gridLatestData;
//    }
    
    /**
     * Sets a value indicating the latest data used
     * to populate the grid.
     * This method propagates a PropertyChange event
     * for property CAConstants.CTRL_GRID_LATEST_PN.
     * 
     * @param keepCentered
     */
//    public void setGridLatestData( RLEInput input )
//    {
//        Object  oldValue    = gridLatestData;
//        Object  newValue    = input;
//        String  propName    = CTRL_GRID_LATEST_PN;
//        gridLatestData = input;
//        propChangeSupport.
//            firePropertyChange( propName, oldValue, newValue );
//    }
    
    /**
     * Gets the name of the pattern being displayed/documented.
     * 
     * @return the name of the pattern being displayed/documented
     */
    public String getPatternName()
    {
        return patternName;
    }

    /**
     * Sets the name of the pattern being displayed/documented
     * to the given value.
     * This method propagates a PropertyChange event
     * for property CAConstants.MISC_PATTERN_NAME_PN.
     * 
     * @param patternName the given value
     */
    public void setPatternName(String patternName)
    {
        String  oldValue    = this.patternName;
        String  newValue    = patternName;
        String  propName    = MISC_PATTERN_NAME_PN;
        this.patternName = patternName;
        propChangeSupport.
            firePropertyChange( propName, oldValue, newValue );
    }
    
    /**
     * Gets the name of the file containing 
     * the pattern being displayed/documented.
     * 
     * @return  the name of the file containing
     *          the pattern being displayed/documented
     */
    public String getPatternFileName()
    {
        return patternFileName;
    }

    /**
     * Sets the name of the file of the pattern being displayed/documented
     * to the given value.
     * This method propagates a PropertyChange event
     * for property CAConstants.MISC_PATTERN_NAME_PN.
     * 
     * @param patternFileName the given value
     */
    public void setPatternFileName( String patternFileName )
    {
        String  oldValue    = this.patternFileName;
        String  newValue    = patternFileName;
        String  propName    = CAConstants.MISC_PATTERN_FILE_NAME_PN;
        this.patternFileName = patternFileName;
        propChangeSupport.
            firePropertyChange( propName, oldValue, newValue );
    }

    /**
     * Gets the name of the author of the pattern being documented/displayed.
     * 
     * @return the name of the author of the pattern
     */
    public String getAuthorName()
    {
        return authorName;
    }

    /**
     * Sets the name of the author of the pattern to the given value.
     * This method propagates a PropertyChange event
     * for property CAConstants.MISC_AUTHOR_NAME_PN.
     * 
     * @param authorName the given value.
     */
    public void setAuthorName(String authorName)
    {
        String  oldValue    = this.authorName;
        String  newValue    = authorName;
        String  propName    = MISC_AUTHOR_NAME_PN;
        this.authorName = authorName;
        propChangeSupport.
            firePropertyChange( propName, oldValue, newValue );
    }

    /**
     * Gets the email address of the author of the pattern.
     * 
     * @return the email address of the author of the pattern
     */
    public String getAuthorEmail()
    {
        return authorEmail;
    }

    /**
     * Sets the email address of the author of the pattern to the given value.
     * This method propagates a PropertyChange event
     * for property CAConstants.MISC_AUTHOR_EMAIL_PN.
     * 
     * @param authorName the given value.
     */
    public void setAuthorEmail(String authorEmail)
    {
        String  oldValue    = this.authorEmail;
        String  newValue    = authorEmail;
        String  propName    = MISC_AUTHOR_EMAIL_PN;
        this.authorEmail = authorEmail;
        propChangeSupport.
            firePropertyChange( propName, oldValue, newValue );
    }

    /**
     * Gets the creation time of the pattern being documented/displayed.
     * To be displayed on the author line of the documentation header.
     * 
     * @return the creation time of the pattern being documented/displayed
     */
    public LocalDateTime getAuthorTime()
    {
        return authorTime;
    }

    /**
     * Sets the creation time of the pattern being documented/displayed.
     * To be displayed on the author line of the documentation header.
     * This method propagates a PropertyChange event
     * for property CAConstants.MISC_AUTHOR_TIME_PN.
     *
     * @param authorTime the authorTime to set
     */
    public void setAuthorTime(LocalDateTime authorTime)
    {
        Object  oldValue    = this.authorTime;
        Object  newValue    = authorTime;
        String  propName    = MISC_AUTHOR_TIME_PN;
        this.authorTime = authorTime;
        propChangeSupport.
            firePropertyChange( propName, oldValue, newValue );
    }

    /**
     * Gets the value of the modified-GUI property.
     * This value is true if one of the GUI control properties,
     * e.g. cell color, line width, keep-centered
     * has been changed.
     * 
     * @return  true if a GUI control property has been modified 
     *          since the last save-file operation
     */
    public boolean getModifiedGUI()
    {
        return modifiedGrid;
    }

    /**
     * Gets the value of the modified-grid property.
     * 
     * @return  true if the grid has been modified since the last
     *          save-file operation
     */
    public boolean getModifiedGrid()
    {
        return modifiedGrid;
    }
    
    /**
     * Sets the modified-GUI flag.
     * This method propagates a PropertyChange event
     * for the CAConstants.MODIFIED_GUI_PN property.
     *
     * @param   modifiedGUI 
     *              true to indicate that
     *              the grid has been modified 
     *              since the last save operation
     */
    public void setModifiedGUI( boolean modifiedGUI )
    {
        boolean oldValue    = this.modifiedGUI;
        boolean newValue    = modifiedGUI;
        String  propName    = MODIFIED_GUI_PN;
        this.modifiedGUI = modifiedGUI;
        propChangeSupport.
            firePropertyChange( propName, oldValue, newValue );
        setModifiedPatternData( modifiedGUI | modifiedMetadata );
    }
    
    /**
     * Sets the modified-grid flag.
     * This method propagates PropertyChange events
     * for properties CAConstants.MODIFIED_GRID_PN
     * and CAConstants.MODIFIED_PATTERN_DATA_PN.
     *
     * @param   modifiedGrid 
     *              true to indicate that
     *              the grid has been modified 
     *              since the last save operation
     *              
     * @see #setModifiedPatternData(boolean)
     */
    public void setModifiedGrid( boolean modifiedGrid )
    {
        boolean oldValue    = this.modifiedGrid;
        boolean newValue    = modifiedGrid;
        String  propName    = MODIFIED_GRID_PN;
        this.modifiedGrid = modifiedGrid;
        propChangeSupport.
            firePropertyChange( propName, oldValue, newValue );
        setModifiedPatternData( modifiedGrid | modifiedMetadata );
    }

    /**
     * Gets the value of the modified-metadata property.
     * 
     * @return  true if the grid has been modified since the last
     *          save-file operation
     */
    public boolean getModifiedMetadata()
    {
        return modifiedMetadata;
    }
    
    /**
     * Sets the modified-metadata flag.
     * This method propagates PropertyChange events
     * for properties CAConstants.MODIFIED_METADATA_PN
     * and CAConstants.MODIFIED_PATTERN_DATA_PN.
     *
     * @param   modifiedMetadata 
     *              true to indicate that
     *              the pattern metadata has been modified 
     *              since the last save operation
     *              
     * @see #setModifiedPatternData(boolean)
     */
    public void setModifiedMetadata( boolean modifiedMetadata )
    {
        boolean oldValue    = this.modifiedMetadata;
        boolean newValue    = modifiedMetadata;
        String  propName    = MODIFIED_METADATA_PN;
        this.modifiedMetadata = modifiedMetadata;
        propChangeSupport.
            firePropertyChange( propName, oldValue, newValue );
        setModifiedPatternData( modifiedGrid | modifiedMetadata );
    }

    /**
     * Gets the value of the modified-pattern data property.
     * 
     * @return  true if the grid or the pattern metadata 
     *          has been modified since the last
     *          save-file operation
     */
    public boolean getModifiedPatternData()
    {
        return modifiedPatternData;
    }
    
    /**
     * Sets the modified-pattern data flag;
     * private because it is not under the control
     * of the user.
     * This method propagates a PropertyChange event
     * for MODIFIED_PATTERN_DATA_PN.
     *
     * @param   modifiedGrid 
     *              true to indicate that
     *              the grid has been modified 
     *              since the last save operation
     */
    private void setModifiedPatternData( boolean modifiedPatternData )
    {
        boolean oldValue    = this.modifiedPatternData;
        boolean newValue    = modifiedPatternData;
        String  propName    = MODIFIED_PATTERN_DATA_PN;
        this.modifiedPatternData = modifiedPatternData;
        propChangeSupport.
            firePropertyChange( propName, oldValue, newValue );
    }
    
    /**
     * Get the procedure registered to spawn the next generation
     * of a cellular automaton.
     * 
     * @return  the procedure registered to spawn the next generation
     *          of a cellular automaton
     */
    public Consumer<Object> getPropagateProc()
    {
        return propagateProc;
    }
    
    /**
     * Registers the procedure to propagate the next generation
     * of a cellular automaton.
     * 
     * @param proc  the procedure to register
     */
    public void setPropagateProc( Consumer<Object> proc )
    {
        propagateProc = proc;
    }

    /**
     * Fires a NotificationEvent to NotificationListeners.
     * The event's property name will
     * be set to CAConstants.ACTION_RESET_PN. 
     */
    public void centerGrid()
    {
        fireNotificationEvent( CAConstants.ACTION_CENTER_GRID_PN );
        reset();
    }
    
    /**
     * Fires a NotificationEvent to NotificationListeners.
     * The event's property name will
     * be set to CAConstants.ACTION_RESET_PN. 
     */
    public void reset()
    {
        fireNotificationEvent( CAConstants.ACTION_RESET_PN );
    }
    
    /**
     * Fires a NotificationEvent to NotificationListeners.
     * The event's property name will
     * be set to CAConstants.ACTION_PUSH_CP_PN. 
     */
    public void checkpointPushed()
    {
        fireNotificationEvent( CAConstants.ACTION_PUSH_CP_PN );
    }
    
    /**
     * Fires a NotificationEvent to NotificationListeners.
     * The event's property name will
     * be set to CAConstants.ACTION_POP_CP_PN. 
     */
    public void checkpointPopped()
    {
        fireNotificationEvent( CAConstants.ACTION_POP_CP_PN );
    }
    
    /*
     * Proposed facility to link properties; when one property changes,
     * another also changes/fires.
    private void propertyLinker( PropertyChangeEvent pce )
    {
        String  prop    = pce.getPropertyName();
        switch ( prop )
        {
        case GRID_COLOR_PN:
        case GRID_MARGIN_TOP_PN:
        case GRID_MARGIN_LEFT_PN:
        case GRID_MARGIN_BOTTOM_PN:
        case GRID_MARGIN_RIGHT_PN:
        case GRID_WIDTH_PN:
        case GRID_HEIGHT_PN:
        case GRID_LINE_SHOW_PN:
        case GRID_LINE_WIDTH_PN:
        case GRID_LINE_COLOR_PN:
        case GRID_CELL_SIZE_PN:
        case GRID_CELL_COLOR_PN:
        case GRID_CELL_ORIGIN_PN:
        case GRID_KEEP_CENTERED_PN:
        case GRID_MAP_PN:
            this.setModifiedGUI( true );
            break;
        }
    }
    */
    
    /**
     * Fires a NotificationEvent associated with a given property
     * to all NotificationListeners, using the given object
     * as the source of the event.
     * 
     * @param property  the given property
     * @param source    the given object (source)
     * 
     * @see ActionRegistrar#fireNotificationEvent(String,Object)
     */
    private void fireNotificationEvent( String property, Object source )
    {
        actionRegistrar.fireNotificationEvent( property, source );
    }
    
    /**
     * Fires a NotificationEvent associated with a given property
     * to NotificationListeners.
     * The source of the event will be 
     * <em>this</em> Parameters object.
     * 
     * @param property  the given property
     * 
     * @see ActionRegistrar#fireNotificationEvent(String)
     */
    private void fireNotificationEvent( String property )
    {
        fireNotificationEvent( property, this );
    }
}
