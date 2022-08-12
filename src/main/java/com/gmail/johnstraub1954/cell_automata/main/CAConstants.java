package com.gmail.johnstraub1954.cell_automata.main;

/**
 * Encapsulates the constants used throughout 
 * the Game of Life code.
 * 
 * @author Jack Straub
 *
 *  TODO gridInteractiveOn
 */
public class CAConstants
{
    ////////////////////////////////////////
    //
    // GRID PROPERTIES
    //
    ////////////////////////////////////////
    
    /** 
     * Grid color property name. 
     * The grid color is the background color
     * of the grid.
     * This may be a color name or an integer (usually in hexadecimal format).
     * Color names must begin with an alphabetic character
     * and be present in the system properties.
     */
    public static final String  GRID_COLOR_PN       = "gridColor";
    /** Grid width default value. */
    public static final String  GRID_COLOR_DV       = "0xEEEEEE";

    /** 
     * Grid margin top property name. 
     * The top margin is the blank space above the grid.
     * May be specified in pixels (px) or EMs (em);
     * pixels is the default.
     */
    public static final String  GRID_MARGIN_TOP_PN   = "gridMarginTop";
    /** Grid margin top default value. */
    public static final String  GRID_MARGIN_TOP_DV   = "1em";

    /** 
     * Grid margin left property name. 
     * The left margin is the blank space to the left
     * of the the grid.
     * May be specified in pixels (px) or EMs (em);
     * pixels is the default.
     */
    public static final String  GRID_MARGIN_LEFT_PN  = "gridMarginLeft";
    /** Grid margin left default value. */
    public static final String  GRID_MARGIN_LEFT_DV  = "1em";

    /** 
     * Grid margin bottom property name. 
     * The bottom margin is the blank space below the grid.
     * May be specified in pixels (px) or EMs (em);
     * pixels is the default.
     */
    public static final String  GRID_MARGIN_BOTTOM_PN   = "gridMarginBottom";
    /** Grid margin top default value. */
    public static final String  GRID_MARGIN_BOTTOM_DV   = "1em";

    /** 
     * Grid margin right property name. 
     * The right margin is the blank space to the right
     * of the the grid.
     * May be specified in pixels (px) or EMs (em);
     * pixels is the default.
     */
    public static final String  GRID_MARGIN_RIGHT_PN = "gridMarginRight";
    /** Grid margin left default value. */
    public static final String  GRID_MARGIN_RIGHT_DV ="1em";
    
    /** 
     * Grid width property name. 
     * The grid width is number of physical cells
     * that are drawn along the x-axis 
     * when the grid is refreshed.
     */
    public static final String  GRID_WIDTH_PN       = "gridWidth";
    /** Grid width default value. */
    public static final String  GRID_WIDTH_DV       = "100";
    
    /** 
     * Grid height property name. 
     * The grid height is number of physical cells
     * that are drawn along the y-axis 
     * when the grid is refreshed.
     */
    public static final String  GRID_HEIGHT_PN      = "gridHeight";
    /** Grid width default value. */
    public static final String  GRID_HEIGHT_DV      = "100";

    /** 
     * Show-grid-line property name. 
     * A Boolean value that indicates 
     * whether grid lines are to be draw around each cell.
     */
    public static final String  GRID_LINE_SHOW_PN   = "gridLineShow";
    /** Show-grid-line default value. */
    public static final String  GRID_LINE_SHOW_DV   = "true";

    /** 
     * Grid line width property name. 
     * The grid width is width of the lines
     * drawn around a cell.
     * May be specified in pixels (px) or EMs (em);
     * pixels is the default.
     */
    public static final String  GRID_LINE_WIDTH_PN   = "gridLineWidth";
    /** Grid line width default value. */
    public static final String  GRID_LINE_WIDTH_DV   = "1px";

    /** 
     * Grid line color property name. 
     * The grid color is color used to draw the grid lines.
     * This may be a color name or an integer (usually in hexadecimal format).
     * Color names must begin with an alphabetic character
     * and be present in the system properties.
     */
    public static final String  GRID_LINE_COLOR_PN   = "gridLineColor";
    /** Grid line color default value. */
    public static final String  GRID_LINE_COLOR_DV   = "0";

    /** 
     * Grid cell width property name. 
     * The grid cell width is width of a cell
     * <em>not</em> including the grid lines.
     * May be specified in pixels (px) or EMs (em);
     * pixels is the default.
     */
    public static final String  GRID_CELL_SIZE_PN   = "gridCellSize";
    /** Grid line width default value. */
    public static final String  GRID_CELL_SIZE_DV   = "4";

    /** 
     * Grid cell color property name. 
     * The grid cell color is the color used to draw live cells
     * (dead cells are drawn in the background color of the grid).
     * This may be a color name or an integer (usually in hexadecimal format).
     * Color names must begin with an alphabetic character
     * and be present in the system properties.
     */
    public static final String  GRID_CELL_COLOR_PN   = "gridCellColor";
    /** Grid line color default value. */
    public static final String  GRID_CELL_COLOR_DV   = "0";
    
    /** 
     * The x- and y- coordinates of the cell to appear in the upper-left
     * corner of the physical grid.
     */
    public static final String  GRID_CELL_ORIGIN_PN = "gridCellOrigin";
    /** 
     * The default value of the x- and y- coordinates of the cell to 
     * appear in the upper-left corner of the physical grid.
     * This consists of two integers separated by whitespace
     * and/or a comma, for example: "100 200", "100,200", "100 , 200".
     */
    public static final String  GRID_CELL_ORIGIN_DV = "0 0";
    /** 
     * The most recent cell that has been clicked (with the mouse)
     * on the grid. This property does not have a default value.
     */
    public static final String  GRID_CELL_CLICKED_PN    = "gridCellClicked";
    /** 
     * A value indicating whether the application should attempt to keep 
     * the grid centered with each new generation.
     */
    public static final String  GRID_KEEP_CENTERED_PN   = "gridKeepCentered";
    /** grid-keep-centered default value; must be boolean. */
    public static final String  GRID_KEEP_CENTERED_DV   = "false";
    /** 
     * The GridMap used in this game. It has no default value,
     * and cannot be configured in a Properties file 
     * or from the command line.
     */
    public static final String  GRID_MAP_PN          = "gridMap";
    public static final String  GRID_QUAD_TILE_PN   = "gridQuadTile";
    public static final String  GRID_QUAD_TILE_DV   = "QuadTile";
    public static final String  GRID_HEX_TILE_PN    = "gridHexTile";
    public static final String  GRID_HEX_TILE_DV    = "HexTile";
    /**
     * The GridTile that controls things like the shape of a cell, length of 
     * side of cell, and pixel-to-cell mappings. E.g., QuadTile, HexTile.
     */
    public static final String  GRID_TILE_PN        = "gridTile";
    /** GridTile default value. */
    public static final String  GRID_TILE_DV        = "QuadTile";
    
    ////////////////////////////////////////
    //
    // GRID CONTOLS
    // Actions/properties linked to GUI controls.
    //
    ////////////////////////////////////////
    
    /** The next-generation feature has been invoked. */
    public static final String  GC_NEXT_GENERATION_FN   = "nextGeneration";
    /** The rewind feature has been invoked. */
    public static final String  GC_REWIND_FN            = "rewind";
    /** The center-grid feature has been invoked. */
    public static final String  GC_CENTER_GRID          = "centerGrid";
    /** The push-checkpoint feature has been invoked. */
    public static final String  GC_CHECKPOINT_PUSH_FN   = "checkpointPush";
    /** The pop-checkpoint feature has been invoked. */
    public static final String  GC_CHECKPOINT_POP_FN    = "checkpointPop";
    /** The open-URL feature has been invoked. */
    public static final String  GC_OPEN_URL_FN          = "openURL";
    /** The select-file feature has been invoked. */
    public static final String  GC_SELECT_FILE_FN       = "selectFile";
    
    /** The generations-per-second property value has changed. */
    public static final String  GC_GEN_PER_SEC_COUNT_PN = "genPerSecond";
    /** The generations-per-second property default value. */
    public static final String  GC_GEN_PER_SEC_COUNT_DV = "4.0";
    /** Animation has been enabled or disabled. */
    public static final String  GC_ANIMATE_PN           = "animate";
    /** Animation enabled default value. */
    public static final String  GC_ANIMATE_DV           = "false";
    /** Grid cell size property name. */
    public static final String  GC_CELL_SIZE_PN         = "cellSize";
    /** Cell size default value. */
    public static final String  GC_CELL_SIZE_DV         = "4";
    /** The show-grid property name. */
    public static final String  GC_SHOW_GRID_PN         = "showGrid";
    /** Show-grid property default value. */
    public static final String  GC_SHOW_GRID_DV         = "true";
    /**
     *  Grid-on-top property property.
     *  TODO: still to be implemented
     */
    public static final String  GC_GRID_ON_TOP_PN       = "gridOnTop";
    /**
     *  Grid-on-top property default value.
     *  TODO: still to be implemented
     */
    public static final String  GC_GRID_ON_TOP_DV       = "true";
    /** The keep-grid-centered property name. */
    public static final String  GC_GRID_CENTERED_PN     = "gridOnTop";
    /** The keep-grid-centered default value. */
    public static final String  GC_GRID_CENTERED_DV     = "false";
    /** The URL-to-open property name. */
    public static final String  GC_URL_TO_OPEN_PN       = "urlToOpen";
    /** The URL-to-open property default value. */
    public static final String  GC_URL_TO_OPEN_DV       = "urlToOpen";
    
    ////////////////////////////////////////
    //
    // PROPAGATION PROPERTIES
    //
    ////////////////////////////////////////
    
    /** Propagation procedure property name. */
    public static final String  PROP_PROC_PN        = "spawn";
    /** Propagation procedure default value (none). */
    public static final String  PROP_PROC_DV        = "";
    
    ////////////////////////////////////////
    //
    // AUTOREGENERATION PROPERTIES
    //
    ////////////////////////////////////////
    
    /** 
     * Auto-regeneration-on property name.
     * May be <em>true</em> or <em>false</em>.
     */
    public static final String  AUTO_REGEN_ON_PN    = "autoRegenerationOn";
    /** Auto-regeneration default value */
    public static final String  AUTO_REGEN_ON_DV    = "false";
    /**
     * Auto-regeneration pace, property name.
     * Specified in generations per second.
     */
    public static final String  AUTO_REGEN_PACE_PN  = "autoRegenerationPace";
    /** Auto-regeneration pace, default value */
    public static final String  AUTO_REGEN_PACE_DV  = "4";
    /** Minimum regeneration pace in generations per second */
    public static final String  AUTO_REGEN_MIN_PN   = "autoRegeneratonMinPace";
    /** Minimum regeneration pace default value*/
    public static final String  AUTO_REGEN_MIN_DV   = ".1";
    /** Maximum regeneration pace in generations per second */
    public static final String  AUTO_REGEN_MAX_PN   = "autoRegeneratonMaxPace";
    /** Maximum regeneration pace default value*/
    public static final String  AUTO_REGEN_MAX_DV   = "25";

    ////////////////////////////////////////
    //
    // CONTROL PROPERTIES
    //
    ////////////////////////////////////////
    
    /**  
     * List of states under which a live cell may continue living.
     * This is a sequence of integers separated by commas.
     * Example: "2,3" a living cell with 2 or 3 neighbors will
     * continue to live.
     */
    public static final String  CTRL_SURVIVAL_STATES_PN = "survivalStates";
    /** Survival states, default value */
    public static final String  CTRL_SURVIVAL_STATES_DV = "2,3";
    /**  
     * List of states under which a dead cell may become alive.
     * This is a sequence of integers separated by commas.
     * Example: "1,2" a living cell with 1 or 2 neighbors will
     * will become alive.
     */
    public static final String  CTRL_BIRTH_STATES_PN    = "birthStates";
    /** Survival states, default value */
    public static final String  CTRL_BIRTH_STATES_DV    = "3";
    /** Center rectangle containing live cells default value. */
    public static final String  CTRL_CENTER_DV          = "false";
    /** URL of file/web link to read and display */
    public static final String  CTRL_GRID_URL_PN        = "url";
    /** Default value of URL of file/web link to read and display */
    public static final String  CTRL_GRID_URL_DV        = "";
    /** Most recent input used to populate grid */
    public static final String  CTRL_GRID_LATEST_PN     = "gridLatestData";
    /** Most recent input used to populate grid default value */
    public static final String  CTRL_GRID_LATEST_DV     = "";
    /** The checkpoint stack */
    public static final String  CTRL_CP_STACK_PN        = "gridLatestData";
    /** The checkpoint stack default value */
    public static final String  CTRL_CP_STACK_DV        = "";
    

    ////////////////////////////////////////
    //
    // ACTION PROPERTIES
    // action properties do not have values; instead,
    // the are associated with ActionEvents.
    //
    ////////////////////////////////////////
    
    /** Property associated with firing a reset action.*/
    public static final String  ACTION_RESET_PN         = "reset";
    /** Property associated with firing an "apply" action.*/
    public static final String  ACTION_APPLY_PN         = "apply";
    /** Property associated with firing a "cancel" action.*/
    public static final String  ACTION_CANCEL_PN        = "cancel";
    /** Property associated with firing an "OK" action.*/
    public static final String  ACTION_OKAY_PN          = "okay";
    /** Property associated with opening a dialog. */
    public static final String  ACTION_OPENED_PN        = "opened";
    /** Property associated with pushing a checkpoint. */
    public static final String  ACTION_PUSH_CP_PN       = "pushCP";
    /** Property associated with popping a checkpoint. */
    public static final String  ACTION_POP_CP_PN        = "popCP";
    /** Property associated with saving a pattern file. */
    public static final String  ACTION_FILE_SAVED_PN    = "fileSaved";
    /** Property associated with spawning a new generation. */
    public static final String  ACTION_PROPAGATE_PN     = "propagate";

    ////////////////////////////////////////
    //
    // MISCELLANEOUS PROPERTIES
    // Miscellaneous properties are mostly devoted to bookkeeping,
    // such as pattern name, author name, author email address, etc.
    // Also includes tile class names and related values.
    //
    ////////////////////////////////////////
    
    /** Name of the pattern being documented/displayed. */
    public static final String  MISC_PATTERN_NAME_PN        = "patternName";
    /** Pattern name default value. */
    public static final String  MISC_PATTERN_NAME_DV        = "";
    /** The name of the file containing the pattern */
    public static final String  MISC_PATTERN_FILE_NAME_PN   = 
        "patternFileName";
    /** The default value of the name of the file containing the pattern */
    public static final String  MISC_PATTERN_FILE_NAME_DV   = "";
    /** Author name of the pattern being documented/displayed. */
    public static final String  MISC_AUTHOR_NAME_PN         = "authorName";
    /** Author name default value. */
    public static final String  MISC_AUTHOR_NAME_DV         = "";
    /** Author email address. */
    public static final String  MISC_AUTHOR_EMAIL_PN        = "authorEmail";
    /** Author email address default value. */
    public static final String  MISC_AUTHOR_EMAIL_DV        = "";
    /** 
     * Creation date/time of pattern; to be displayed on the author line 
     * of the documentation header.
     */
    public static final String  MISC_AUTHOR_TIME_PN         = "authorTime";
    /** Author creation date default value. */
    public static final String  MISC_AUTHOR_TIME_DV         = "";
    
    ////////////////////////////////////////
    //
    // MODIFICATION FLAGS
    //
    //  Boolean flags that indicate whether state has been changed,
    //  and may need to be saved.
    //
    ////////////////////////////////////////
    
    /** 
     * The state of the grid defining the pattern has been modified;
     * may be true or false
     */
    public static final String  MODIFIED_GRID_PN            = 
        "modifiedGridState";
    /** Grid modification state default value. */
    public static final String  MODIFIED_GRID_DV            = "false";
    /** 
     * The state of the pattern has meta-data been modified;
     * metadata includes author name, pattern name and rules.
     * May be true of false.
     */
    public static final String  MODIFIED_METADATA_PN        = 
        "modifiedMetaDataState";
    /** Metadata modification state default value. */
    public static final String  MODIFIED_METADATA_DV        = "false";
    /** 
     * The state of the pattern data has been modified;
     * this is defined as the inclusive-or of the MODIFIED_GRID
     * and MODIFIED_META_DATA flags.
     * This is a read-only property.
     * May be true of false.
     */
    public static final String  MODIFIED_PATTERN_DATA_PN    = 
        "modifiedPatternData";
    /** 
     * The state of the GUI controls has been been modified;
     * this includes cell color, cell size, cell line color, etc.
     * May be true of false.
     */
    public static final String  MODIFIED_GUI_PN             = 
        "modifiedGridState";
    /** Grid modification state default value. */
    public static final String  MODIFIED_GUI_DV             = "false";
    

    ////////////////////////////////////////
    //
    // COMPONENT NAMES
    // The names of GUI components, such as the panel that displays the
    // grid, or the dialog that is used to set property names.
    // These are pretty exclusively for testing.
    //
    // TODO reorganize the names of the pattern properties and 
    //      grid properties panel components
    //
    ////////////////////////////////////////
    
    ////////////////////////////////
    // save-as dialog
    ////////////////////////////////    
    /** The name of the dialog that creates new pattern files */
    public static final String  SAVE_DLG_CN         = "saveDialog";
    
    ////////////////////////////////
    // preferences dialog
    ////////////////////////////////    
    /** The name of the dialog that manages preferences */
    public static final String  PREF_DLG_CN         = "preferencesDialog";
    
    /** The name of the component that initiates setting the grid color */
    public static final String  PREF_GRID_COLOR_CN  = 
       PREF_DLG_CN + ".gridColorComp";
    /** The name of the component that displays the selected grid color */
    public static final String  PREF_GRID_COLOR_FB_CN  = 
       PREF_DLG_CN + ".gridColorFBComp";
    /** The name of the component that initiates setting the cell color */
    public static final String  PREF_CELL_COLOR_CN  = 
       PREF_DLG_CN + ".cellColorComp";
    /** The name of the component that displays the selected grid color */
    public static final String  PREF_CELL_COLOR_FB_CN  = 
       PREF_DLG_CN + ".cellColorFBComp";
    /** The name of the component that initiates setting the grid line color */
    public static final String  PREF_LINE_COLOR_CN  = 
       PREF_DLG_CN + ".lineColorComp";
    /** The name of the component that displays the selected line color */
    public static final String  PREF_LINE_COLOR_FB_CN  = 
       PREF_DLG_CN + ".gridLineFBComp";
    
    /** The name of the component that contains the grid width */
    public static final String PREF_LINE_WIDTH_CN  = 
       PREF_DLG_CN + ".lineWidthComp";
    /** The name of the component that contains the cell size*/
    public static final String PREF_CELL_SIZE_CN   = 
       PREF_DLG_CN + ".lineColorButton";
    /** The name of the component that contains the "show grid" value */
    public static final String PREF_SHOW_GRID_CN   = 
       PREF_DLG_CN + ".lineShowGrid";
    
    /** The name of the component that contains the pattern name */
    public static final String PREF_PATTERN_NAME_CN    = 
       PREF_DLG_CN + ".patternName";
    /** The name of the component that contains the author name */
    public static final String PREF_AUTHOR_NAME_CN     = 
       PREF_DLG_CN + ".authorName";
    /** The name of the component that contains the author email address */
    public static final String PREF_AUTHOR_EMAIL_CN      = 
       PREF_DLG_CN + ".authorEmail";
    /** The name of the component that contains the modification date */
    public static final String PREF_AUTHOR_TIME_CN            = 
       PREF_DLG_CN + ".date";
    /** The name of the component that contains the pattern file name */
    public static final String PREF_FILE_NAME_CN       = 
       PREF_DLG_CN + ".fileName";
    /** The name of the component that contains the birth rules */
    public static final String PREF_BIRTH_RULES_CN     = 
       PREF_DLG_CN + ".birthRules";
    /** The name of the component that contains the survival rules */
    public static final String PREF_SURVIVAL_RULES_CN  = 
       PREF_DLG_CN + ".survivalRules";
    
    ////////////////////////////////
    // button panel
    ////////////////////////////////
    /** The name of the button panel */
    public static final String BUTT_PANEL_CN            = "buttonPanel";
        /** The name of the preferences dialog apply; button */
    public static final String BP_APPLY_BUTTON_CN     = 
        BUTT_PANEL_CN + ".applyButton";
    /** The name of the preferences dialog cancel; button */
    public static final String BP_CANCEL_BUTTON_CN    = 
        BUTT_PANEL_CN + ".cancelButton";
    /** The name of the preferences dialog OK button */
    public static final String BP_OK_BUTTON_CN        = 
        BUTT_PANEL_CN + ".okButton";
    
    ////////////////////////////////
    // main frame panels
    ////////////////////////////////
    /**
     * The name of the main frame, which contains the grid,
     * the generation panel, the controls panel and the URL panel.
     */
    public static final String  MAIN_FRAME_CN           = "mainFrame";

    ////////////////////////////////
    // generator panel
    ////////////////////////////////
    /** The name of the next generation button */
    public static final String  GEN_NEXT_BUTTON_CN      =
        MAIN_FRAME_CN + ".nextGenerationButton";
    /** The name of the rewind button */
    public static final String  GEN_REWIND_BUTTON_CN    =
        MAIN_FRAME_CN + ".rewindButton";
    /** 
     * The name of the component that displays feedback from the
     * "generations per second" control
     */
    public static final String  GEN_FEEDBACK_COMP_CN    =
        MAIN_FRAME_CN + ".genPerSecFeedbackComponent";
    /** The name of the "generations per second" control */
    public static final String  GEN_PER_SEC_COMP_CN     =
        MAIN_FRAME_CN + ".genPerSecComponent";
    /** The name of the "animate" control */
    public static final String  GEN_ANIMATE_TOGGLE_CN   =
        MAIN_FRAME_CN + ".genPerSecComponent";

    ////////////////////////////////
    // controls panel
    ////////////////////////////////
    /** The name of the cell size component */
    public static final String  CTRL_CELL_SIZE_COMP_CN  =
        MAIN_FRAME_CN + ".cellSizeSpinner";
    /** The name of the show-grid component */
    public static final String  CTRL_SHOW_GRID_COMP_CN  =
        MAIN_FRAME_CN + ".showGridCheckBox";
    /** The name of the keep-centered component */
    public static final String  CTRL_KEEP_CENTERED_COMP_CN  =
        MAIN_FRAME_CN + ".keepCenteredCheckBox";
    /** The name of the center-grid component */
    public static final String  CTRL_CENTER_COMP_CN     =
        MAIN_FRAME_CN + ".centerGridButton";
    /** The name of the "push checkpoint" control */
    public static final String  CTRL_PUSH_CP_CN   =
        MAIN_FRAME_CN + ".genPushCP";
    /** The name of the "pop checkpoint" control */
    public static final String  CTRL_POP_CP_CN   =
        MAIN_FRAME_CN + ".genPushCP";

    ////////////////////////////////
    // URL panel
    ////////////////////////////////
    /** The name of the URL text field */
    public static final String  URL_TEXT_FIELD_CN       =
        MAIN_FRAME_CN + ".urlTextField";
    /** The name of the open-URL button */
    public static final String  URL_OPEN_BUTTON_CN      =
        MAIN_FRAME_CN + ".urlOpenButton";
    /** The name of the select-file button */
    public static final String  URL_SELECT_FILE_BUTTON_CN   =
        MAIN_FRAME_CN + ".selectFileButton";

    ////////////////////////////////
    // outer grid panel
    ////////////////////////////////
    /** The name of the outer grid panel*/
    public static final String  GRID_OUTER_PANEL_CN     =
        MAIN_FRAME_CN + ".gridOuterPanel";

    /** The name of the grid panel*/
    public static final String  GRID_PANEL_CN           =
        MAIN_FRAME_CN + ".gridPanel";
}
