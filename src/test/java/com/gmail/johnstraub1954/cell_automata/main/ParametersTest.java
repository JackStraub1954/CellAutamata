package com.gmail.johnstraub1954.cell_automata.main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParametersTest
{
    private Parameters  params;
    private List<PropChangeTester>  allPropChangeTesters    = 
        new LinkedList<>();
    
    @BeforeEach
    public void beforeEach()
    {
        params = Parameters.INSTANCE;        
        while ( !allPropChangeTesters.isEmpty() )
        {
            PropChangeTester    tester  = allPropChangeTesters.remove( 0 );
            params.removePropertyChangeListener( tester );
        }
    }
    
    /**
     * test params.addPropertyChangeListener( PropertyChangeListener )
     */
    @Test
    void testAddPropertyChangeListenerPCL()
    {
        int     oldVal  = 15;
        int     newVal  = oldVal + 5;
        String  name    = CAConstants.GRID_CELL_SIZE_PN;
        
        params.setGridCellSize( oldVal );
        assertEquals( oldVal, params.getGridCellSize() );
        
        PropChangeTester    tester  = 
            getPropChangeTester(
                () -> params.getGridCellSize(),
                name,
                newVal
            );
        params.addPropertyChangeListener( name, tester );
        params.setGridCellSize( newVal );
        tester.test();
    }

    /**
     * test params.addPropertyChangeListener( String, PropertyChangeListener )
     */
    @Test
    void testAddPropertyChangeListenerStringPCL()
    {
        int     oldVal  = 15;
        int     newVal  = oldVal + 5;
        String  name1   = CAConstants.GRID_WIDTH_PN;
        // name2 must be different from name1
        String  name2   = CAConstants.GRID_HEIGHT_PN;
        
        params.setGridWidth( oldVal );
        PropChangeTester    tester1  =
            getPropChangeTester(
                () -> params.getGridWidth(),
                name1,
                newVal
            );
        params.addPropertyChangeListener( name1, tester1 );

        // tester2 shouldn't ever be invoked;
        // it's here to prove that when I invoke the gridWidth-specific
        // property change listener, only the gridWidth-specific property
        // change listener is invoked.
        PropChangeTester    tester2  = 
            getPropChangeTester(
                () -> params.getGridCellSize(),
                name2,
                newVal
            );
        params.addPropertyChangeListener( name2, tester2 );

        params.setGridWidth( newVal );
        tester1.test( true );
        tester2.test( false );
    }

    /**
     * test params.removePropertyChangeListener( PropertyChangeListener )
     */
    @Test
    void testRemovePropertyChangeListenerPCL()
    {
        int     oldVal  = 15;
        int     newVal  = oldVal + 5;
        String  name    = CAConstants.GRID_CELL_SIZE_PN;
        
        params.setGridCellSize( oldVal );
        assertEquals( oldVal, params.getGridCellSize() );
        PropChangeTester    tester  = 
            getPropChangeTester(
                () -> params.getGridCellSize(),
                name,
                newVal
            );
        
        // add PCL and verify invoked
        params.addPropertyChangeListener( name, tester );
        params.setGridCellSize( newVal );
        tester.test( true );
        
        // remove PCL and verify not invoked
        tester.setInvoked( false );
        params.removePropertyChangeListener( tester );
        params.setGridCellSize( newVal );
        tester.test( false );
    }

    @Test
    void testRemovePropertyChangeListenerStringPCL()
    {
        int     oldVal  = 15;
        int     newVal  = oldVal + 5;
        String  name    = CAConstants.GRID_CELL_SIZE_PN;
        
        params.setGridCellSize( oldVal );
        assertEquals( oldVal, params.getGridCellSize() );
        PropChangeTester    tester1 = 
            getPropChangeTester(
                () -> params.getGridCellSize(),
                name,
                newVal
            );        
        // add per-property PCL
        params.addPropertyChangeListener( name, tester1 );

        PropChangeTester    tester2 = 
            getPropChangeTester(
                () -> params.getGridCellSize(),
                name,
                newVal
            );        
        // add PCL (NOT per-property)
        params.addPropertyChangeListener( tester2 );
        
        // change property; both testers should be invoked
        params.setGridCellSize( newVal );
        tester1.test( true );
        tester2.test( true );
        
        // remove per-property PCL and change property...
        // ... tester1 should not be invoked
        // ... tester2 should be invoked
        tester1.setInvoked( false );
        tester2.setInvoked( false );
        params.removePropertyChangeListener( name, tester1 );
        // put the original value back; new value cannot equal old value
        // or PCLs will not fire
        params.setGridCellSize( oldVal );
        assertFalse( tester1.isInvoked() );
        assertTrue( tester2.isInvoked() );
    }
    
    @Test
    void testAddRemoveActionListener()
    {
        NotificationTester    listener    = 
            new NotificationTester( CAConstants.ACTION_RESET_PN );
        
        params.addNotificationListener( listener );
        params.reset();
        listener.test( true, params );
        listener.reset();
        params.removeNotificationListener( listener );
        params.reset();
        listener.test( false, null );
        
        params.removeNotificationListener( listener );
    }

    @Test
    void testSetAutoRegenerationOn()
    {
        boolean oldVal  = true;
        boolean newVal  = !oldVal;
        String  name    = CAConstants.AUTO_REGEN_ON_PN;
        
        params.setAutoRegenerationOn( oldVal );
        assertEquals( oldVal, params.isAutoRegenerationOn() );
        
        PropChangeTester    tester  = 
            getPropChangeTester(
                () -> params.isAutoRegenerationOn(),
                name,
                newVal
            );
        params.addPropertyChangeListener( name, tester );
        params.setAutoRegenerationOn( newVal );
        tester.test();
    }

    @Test
    void testSetAutoRegenerationPace()
    {
        float   oldVal  = 1.5f;
        float   newVal  = oldVal + 1;
        String  name    = CAConstants.AUTO_REGEN_PACE_PN;
        
        params.setAutoRegenerationPace( oldVal );
        assertEquals( oldVal, params.getAutoRegenerationPace() );
        PropChangeTester    tester  = 
            getPropChangeTester(
                () -> params.getAutoRegenerationPace(),
                name,
                newVal
            );
        params.addPropertyChangeListener( name, tester );
        params.setAutoRegenerationPace( newVal );
        tester.test();
    }

    @Test
    void testSetGridColor()
    {
        Color   oldVal  = Color.RED;
        Color   newVal  = Color.BLACK;
        String  name    = CAConstants.GRID_COLOR_PN;
        
        params.setGridColor( oldVal );
        assertEquals( oldVal, params.getGridColor() );
        PropChangeTester    tester  = 
            getPropChangeTester(
                () -> params.getGridColor(),
                name,
                newVal
            );
        params.addPropertyChangeListener( name, tester );
        params.setGridColor( newVal );
        tester.test();
    }

    @Test
    void testSetGridMarginTop()
    {
        int     oldVal  = 5;
        int     newVal  = 2 * oldVal;
        String  name    = CAConstants.GRID_MARGIN_TOP_PN;
        
        params.setGridMarginTop( oldVal );
        assertEquals( oldVal, params.getGridMarginTop() );
        PropChangeTester    tester  = 
            getPropChangeTester(
                () -> params.getGridMarginTop(),
                name,
                newVal
            );
        params.addPropertyChangeListener( name, tester );
        params.setGridMarginTop( newVal );
        tester.test();
    }

    @Test
    void testSetGridMarginLeft()
    {
        int     oldVal  = 5;
        int     newVal  = 2 * oldVal;
        String  name    = CAConstants.GRID_MARGIN_LEFT_PN;
        
        params.setGridMarginLeft( oldVal );
        assertEquals( oldVal, params.getGridMarginLeft() );
        PropChangeTester    tester  = 
            getPropChangeTester(
                () -> params.getGridMarginLeft(),
                name,
                newVal
            );
        params.addPropertyChangeListener( name, tester );
        params.setGridMarginLeft( newVal );
        tester.test();
    }

    @Test
    void testSetGridMarginBottom()
    {
        int     oldVal  = 5;
        int     newVal  = 2 * oldVal;
        String  name    = CAConstants.GRID_MARGIN_BOTTOM_PN;
        
        params.setGridMarginBottom( oldVal );
        assertEquals( oldVal, params.getGridMarginBottom() );
        PropChangeTester    tester  = 
            getPropChangeTester(
                () -> params.getGridMarginBottom(),
                name,
                newVal
            );
        params.addPropertyChangeListener( name, tester );
        params.setGridMarginBottom( newVal );
        tester.test();
    }

    @Test
    void testSetGridMarginRight()
    {
        int     oldVal  = 5;
        int     newVal  = 2 * oldVal;
        String  name    = CAConstants.GRID_MARGIN_RIGHT_PN;
        
        params.setGridMarginRight( oldVal );
        assertEquals( oldVal, params.getGridMarginRight() );
        PropChangeTester    tester  = 
            getPropChangeTester(
                () -> params.getGridMarginRight(),
                name,
                newVal
            );
        params.addPropertyChangeListener( name, tester );
        params.setGridMarginRight( newVal );
        tester.test();
    }

    @Test
    void testSetGridWidth()
    {
        int     oldVal  = 5;
        int     newVal  = 2 * oldVal;
        String  name    = CAConstants.GRID_WIDTH_PN;
        
        params.setGridWidth( oldVal );
        assertEquals( oldVal, params.getGridWidth() );
        PropChangeTester    tester  = 
            getPropChangeTester(
                () -> params.getGridWidth(),
                name,
                newVal
            );
        params.addPropertyChangeListener( name, tester );
        params.setGridWidth( newVal );
        tester.test();
    }

    @Test
    void testSetGridHeight()
    {
        int     oldVal  = 5;
        int     newVal  = 2 * oldVal;
        String  name    = CAConstants.GRID_HEIGHT_PN;
        
        params.setGridHeight( oldVal );
        assertEquals( oldVal, params.getGridHeight() );
        PropChangeTester    tester  = 
            getPropChangeTester(
                () -> params.getGridHeight(),
                name,
                newVal
            );
        params.addPropertyChangeListener( name, tester );
        params.setGridHeight( newVal );
        tester.test();
    }

    @Test
    void testSetGridLineShow()
    {
        boolean oldVal  = true;
        boolean newVal  = !oldVal;
        String  name    = CAConstants.GRID_LINE_SHOW_PN;
        
        params.setGridLineShow( oldVal );
        assertEquals( oldVal, params.isGridLineShow() );
        PropChangeTester    tester  = 
            getPropChangeTester(
                () -> params.isGridLineShow(),
                name,
                newVal
            );
        params.addPropertyChangeListener( name, tester );
        params.setGridLineShow( newVal );
        tester.test();
    }

    @Test
    void testSetGridLineWidth()
    {
        int     oldVal  = 5;
        int     newVal  = 2 * oldVal;
        String  name    = CAConstants.GRID_LINE_WIDTH_PN;
        
        params.setGridLineWidth( oldVal );
        assertEquals( oldVal, params.getGridLineWidth() );
        PropChangeTester    tester  = 
            getPropChangeTester(
                () -> params.getGridLineWidth(),
                name,
                newVal
            );
        params.addPropertyChangeListener( name, tester );
        params.setGridLineWidth( newVal );
        tester.test();
    }

    @Test
    void testSetGridLineColor()
    {
        Color   oldVal  = Color.RED;
        Color   newVal  = Color.BLACK;
        String  name    = CAConstants.GRID_LINE_COLOR_PN;
        
        params.setGridLineColor( oldVal );
        assertEquals( oldVal, params.getGridLineColor() );
        PropChangeTester    tester  = 
            getPropChangeTester(
                () -> params.getGridLineColor(),
                name,
                newVal
            );
        params.addPropertyChangeListener( name, tester );
        params.setGridLineColor( newVal );
        tester.test();
    }

    @Test
    void testSetGridCellSize()
    {
        int     oldVal  = 5;
        int     newVal  = 2 * oldVal;
        String  name    = CAConstants.GRID_CELL_SIZE_PN;
        
        params.setGridCellSize( oldVal );
        assertEquals( oldVal, params.getGridCellSize() );
        PropChangeTester    tester  = 
            getPropChangeTester(
                () -> params.getGridCellSize(),
                name,
                newVal
            );
        params.addPropertyChangeListener( name, tester );
        params.setGridCellSize( newVal );
        tester.test();
    }

    @Test
    void testSetGridCellColor()
    {
        Color   oldVal  = Color.RED;
        Color   newVal  = Color.BLACK;
        String  name    = CAConstants.GRID_CELL_COLOR_PN;
        
        params.setGridCellColor( oldVal );
        assertEquals( oldVal, params.getGridCellColor() );
        PropChangeTester    tester  = 
            getPropChangeTester(
                () -> params.getGridCellColor(),
                name,
                newVal
            );
        params.addPropertyChangeListener( name, tester );
        params.setGridCellColor( newVal );
        tester.test();
    }
    
    @Test
    public void testSetGetAutoRegenerationPaceMin()
    {
        float   oldVal  = params.getAutoRegenerationPaceMin();
        float   newVal  = 2 * oldVal + 2;
        String  name    = CAConstants.AUTO_REGEN_MIN_PN;
        
        params.setAutoRegenerationPaceMin( oldVal );
        assertEquals( oldVal, params.getAutoRegenerationPaceMin() );
        PropChangeTester    tester  = 
            getPropChangeTester(
                () -> params.getAutoRegenerationPaceMin(),
                name,
                newVal
            );
        params.addPropertyChangeListener( name, tester );
        params.setAutoRegenerationPaceMin( newVal );
        tester.test();
    }
    
    @Test
    public void testSetGetAutoRegenerationPaceMax()
    {
        float   oldVal  = params.getAutoRegenerationPaceMax();
        float   newVal  = 2 * oldVal + 2;
        String  name    = CAConstants.AUTO_REGEN_MAX_PN;
        
        params.setAutoRegenerationPaceMax( oldVal );
        assertEquals( oldVal, params.getAutoRegenerationPaceMax() );
        PropChangeTester    tester  = 
            getPropChangeTester(
                () -> params.getAutoRegenerationPaceMax(),
                name,
                newVal
            );
        params.addPropertyChangeListener( name, tester );
        params.setAutoRegenerationPaceMax( newVal );
        tester.test();
    }
    
    @Test
    public void testSetGetGridMap()
    {
        GridMap oldVal  = params.getGridMap();
        GridMap newVal  = new GridMap();
        String  name    = CAConstants.GRID_MAP_PN;
        
        // newVal and oldVal must not be equal, so the maps they
        // contain must not be equal. Make sure the maps are unequal.
        oldVal.put( 0, 0, true );
        newVal.put( 0, 0, false );
        
        // the value returned by getGridMap must never be null
        assertNotNull( oldVal );
        params.setGridMap( oldVal );
        assertEquals( oldVal, params.getGridMap() );
        PropChangeTester    tester  = 
            getPropChangeTester(
                () -> params.getGridMap(),
                name,
                newVal
            );
        params.addPropertyChangeListener( name, tester );
        params.setGridMap( newVal );
        tester.test();
    }
    
    @Test
    public void testSetGetSurvivalStates()
    {
        List<Integer>   oldVal  = params.getSurvivalStates();
        List<Integer>   newVal  = new ArrayList<>();
        String          name    = CAConstants.CTRL_SURVIVAL_STATES_PN;
        
        params.setSurvivalStates( oldVal );
        assertEquals( oldVal, params.getSurvivalStates() );
        PropChangeTester    tester  = 
            getPropChangeTester(
                () -> params.getSurvivalStates(),
                name,
                newVal
            );
        params.addPropertyChangeListener( name, tester );
        params.setSurvivalStates( newVal );
        tester.test();
    }
    
    @Test
    public void testSetGetBirthStates()
    {
        List<Integer>   oldVal  = params.getBirthStates();
        List<Integer>   newVal  = new ArrayList<>();
        String          name    = CAConstants.CTRL_BIRTH_STATES_PN;
        
        params.setBirthStates( oldVal );
        assertEquals( oldVal, params.getBirthStates() );
        PropChangeTester    tester  = 
            getPropChangeTester(
                () -> params.getBirthStates(),
                name,
                newVal
            );
        params.addPropertyChangeListener( name, tester );
        params.setBirthStates( newVal );
        tester.test();
    }
    
    @Test
    public void testSetGridCellOrigin()
    {
        Point   oldVal  = params.getGridCellOrigin();
        Point   newVal  = new Point( oldVal.x + 5, oldVal.y + 5 );
        String  name    = CAConstants.GRID_CELL_ORIGIN_PN;
        
        params.setGridCellOrigin( oldVal );
        assertEquals( oldVal, params.getGridCellOrigin() );
        PropChangeTester    tester  = 
            getPropChangeTester(
                () -> params.getGridCellOrigin(),
                name,
                newVal
            );
        params.addPropertyChangeListener( name, tester );
        params.setGridCellOrigin( newVal );
        tester.test();
    }
    
    @Test
    public void testGetGridURL() throws MalformedURLException
    {
        String  strURL1 = "https://someserver.com/somefile.txt";
        String  strURL2 = "https://anotherserver.com/somefile.txt";
        URL     oldVal  = new URL( strURL1 );
        URL     newVal  = new URL( strURL2 );
        String  name    = CAConstants.CTRL_GRID_URL_PN;
        
        params.setGridURL( oldVal );
        assertEquals( oldVal, params.getGridURL() );
        PropChangeTester    tester  = 
            getPropChangeTester(
                () -> params.getGridURL(),
                name,
                newVal
            );
        params.addPropertyChangeListener( name, tester );
        params.setGridURL( newVal );
        tester.test();
    }
    
    @Test
    public void testIsGridKeepCentered()
    {
        boolean oldVal  = false;
        boolean newVal  = true;
        String  name    = CAConstants.GRID_KEEP_CENTERED_PN;
        
        params.setGridKeepCentered( newVal );
        assertEquals( newVal, params.isGridKeepCentered() );
        params.setGridKeepCentered( oldVal );
        assertEquals( oldVal, params.isGridKeepCentered() );
        PropChangeTester    tester  = 
            getPropChangeTester(
                () -> params.isGridKeepCentered(),
                name,
                newVal
            );
        params.addPropertyChangeListener( name, tester );
        params.setGridKeepCentered( newVal );
        tester.test();
    }
/*    
    @Test
    public void testSetGridLatestData()
    {
        ClassLoader loader      = RLEInput.class.getClassLoader();
        InputStream rleInputStream  = 
            loader.getResourceAsStream( "104p177_synth.rle" );
        assertNotNull( rleInputStream );
        RLEInput    oldVal  = params.getGridLatestData();
        RLEInput    newVal  = new RLEInput( rleInputStream );
        String      name    = CAConstants.CTRL_GRID_LATEST_PN;
        
        params.setGridLatestData( oldVal );
        assertEquals( oldVal, params.getGridLatestData() );
        PropChangeTester    tester  = 
            getPropChangeTester(
                () -> params.getGridLatestData(),
                name,
                newVal
            );
        params.addPropertyChangeListener( name, tester );
        params.setGridLatestData( newVal );
        tester.test();
    }
*/
    
    @Test
    public void testSetPatternName()
    {
        String  oldVal  = "Some pattern name";
        String  newVal  = "New pattern name";
        String  name    = CAConstants.MISC_PATTERN_NAME_PN;
        
        params.setPatternName( oldVal );
        assertEquals( oldVal, params.getPatternName() );
        PropChangeTester    tester  = 
            getPropChangeTester(
                () -> params.getPatternName(),
                name,
                newVal
            );
        params.addPropertyChangeListener( name, tester );
        params.setPatternName( newVal );
        tester.test();
    }
    
    @Test
    public void testSetPatternFileName()
    {
        String  oldVal  = "OldPatternFileName.rle";
        String  newVal  = "SNewPatternFileName.rle";
        String  name    = CAConstants.MISC_PATTERN_FILE_NAME_PN;
        
        params.setPatternFileName( oldVal );
        assertEquals( oldVal, params.getPatternFileName() );
        PropChangeTester    tester  = 
            getPropChangeTester(
                () -> params.getPatternFileName(),
                name,
                newVal
            );
        params.addPropertyChangeListener( name, tester );
        params.setPatternFileName( newVal );
        tester.test();
    }
    
    @Test
    public void testSetAuthorName()
    {
        String  oldVal  = "Author, Old";
        String  newVal  = "Author, New";
        String  name    = CAConstants.MISC_AUTHOR_NAME_PN;
        
        params.setAuthorName( oldVal );
        assertEquals( oldVal, params.getAuthorName() );
        PropChangeTester    tester  = 
            getPropChangeTester(
                () -> params.getAuthorName(),
                name,
                newVal
            );
        params.addPropertyChangeListener( name, tester );
        params.setAuthorName( newVal );
        tester.test();
    }
    
    @Test
    public void testSetAuthorEmail()
    {
        String  oldVal  = "Author, Old";
        String  newVal  = "Author, New";
        String  name    = CAConstants.MISC_AUTHOR_EMAIL_PN;
        
        params.setAuthorEmail( oldVal );
        assertEquals( oldVal, params.getAuthorEmail() );
        PropChangeTester    tester  = 
            getPropChangeTester(
                () -> params.getAuthorEmail(),
                name,
                newVal
            );
        params.addPropertyChangeListener( name, tester );
        params.setAuthorEmail( newVal );
        tester.test();
    }
    
    @Test
    public void testSetAuthorTime()
    {
        LocalDateTime   oldVal  = LocalDateTime.now();
        LocalDateTime   newVal  = oldVal.plusDays( 1 );
        String          name    = CAConstants.MISC_AUTHOR_TIME_PN;
        
        params.setAuthorTime( oldVal );
        assertEquals( oldVal, params.getAuthorTime() );
        PropChangeTester    tester  = 
            getPropChangeTester(
                () -> params.getAuthorTime(),
                name,
                newVal
            );
        params.addPropertyChangeListener( name, tester );
        params.setAuthorTime( newVal );
        tester.test();
    }
    
    @Test
    public void testAddNotificationListenerStringNotificationListener() 
    {
        String  notificationProperty1   = CAConstants.GRID_CELL_CLICKED_PN;
        String  notificationProperty2   = CAConstants.ACTION_CENTER_GRID_PN;
        
        // listener1 should receive notifications
        // ONLY for notificationProperty1
        NotificationTester  listener1   =
            new NotificationTester( notificationProperty1 );
        params.addNotificationListener(notificationProperty1, listener1 );
        
        // listener2 should receive notifications
        // ONLY for notificationProperty2
        NotificationTester  listener2   =
            new NotificationTester( notificationProperty2 );
        params.addNotificationListener( notificationProperty2, listener2 );
        
        // listener3 should receive notifications for all properties
        NotificationTester  listener3   =
            new NotificationTester( null );
        params.addNotificationListener( listener3 );
        
        // listeners 1 and 3 should be notified; listener 2 should not
        params.selectGridCell( new Cell( 0, 0 ) );
        assertEquals( notificationProperty1, listener1.actProperty );
        assertTrue( listener1.invoked );
        assertEquals( notificationProperty1, listener3.actProperty );
        assertTrue( listener3.invoked );
        assertEquals( listener3.actProperty, notificationProperty1 );
        assertFalse( listener2.invoked );
        
        params.removeNotificationListener( listener1 );
        params.removeNotificationListener( listener2 );
        params.removeNotificationListener( listener3 );
    }
    
    @Test
    public void testReset()
    {
        String              name    = CAConstants.ACTION_RESET_PN;
        NotificationTester  tester  = new NotificationTester( name );
        params.addNotificationListener( tester );
        params.reset();
        tester.test( true, null );
    }
    
    @Test
    public void testCenterGrid()
    {
        String              name    = CAConstants.ACTION_CENTER_GRID_PN;
        NotificationTester  tester  = new NotificationTester( name );
        params.addNotificationListener( name, tester );
        params.centerGrid();
        tester.test( true, null );
    }
        
    private PropChangeTester getPropChangeTester(
        Supplier<Object> propGetter,
        String expPropName, 
        Object expNewValue
    )
    {
        PropChangeTester    tester  =
            new PropChangeTester( propGetter, expPropName, expNewValue );
        allPropChangeTesters.add( tester );
        return tester;
    }

    private class PropChangeTester implements PropertyChangeListener
    {
        private final Supplier<Object>  propertyGetter;

        private final String    expPropertyName;
        private final Object    expOldValue;
        private final Object    expNewValue;
        
        private boolean         invoked             = false;
        private String          actPropertyName     = null;
        private Object          actOldValue         = null;
        private Object          actNewValue         = null;

        public PropChangeTester(
            Supplier<Object> propertyGetter,
            String expPropertyName, 
            Object expNewValue
        )
        {
            super();
            this.propertyGetter = propertyGetter;
            this.expPropertyName = expPropertyName;
            this.expOldValue = propertyGetter.get();
            this.expNewValue = expNewValue;
        }
        
        public void test()
        {
            test( true );
        }
        
        public void test( boolean expInvoked )
        {
            assertEquals( expInvoked, invoked );
            if ( expInvoked )
            {
                assertEquals( expNewValue, actNewValue );
                assertEquals( expOldValue, actOldValue );
                assertEquals( expPropertyName, actPropertyName );
                assertEquals( expNewValue, propertyGetter.get() );
            }
        }
        
        public void setInvoked( boolean invoked )
        {
            this.invoked = invoked;
        }
        
        public boolean isInvoked()
        {
            return invoked;
        }

        @Override
        public void propertyChange( PropertyChangeEvent evt )
        {
            invoked = true;
            actNewValue = evt.getNewValue();
            actOldValue = evt.getOldValue();
            actPropertyName = evt.getPropertyName();
        }
    }

    private class NotificationTester implements NotificationListener
    {
        private boolean invoked     = false;
        private Object  source      = null;
        private String  expProperty = null;
        private String  actProperty = null;
        
        public NotificationTester( String expProperty )
        {
            this.expProperty = expProperty;
        }
        
        @Override
        public void notification( NotificationEvent evt )
        {
            invoked = true;
            source = evt.getSource();
            actProperty = evt.getProperty();
        }
        
        public void test( boolean expInvoked, Object expSource )
        {
            assertEquals( expInvoked, invoked );
            if ( expInvoked )
            {
                assertEquals( expProperty, actProperty );
                if ( expSource != null )
                    assertEquals( expSource, source );
            }
        }
        
        public void reset()
        {
            invoked = false;
            source = null;
        }
    }
}
