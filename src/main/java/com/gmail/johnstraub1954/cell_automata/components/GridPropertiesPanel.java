package com.gmail.johnstraub1954.cell_automata.components;

import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.GRID_CELL_COLOR_PN;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.GRID_CELL_SIZE_PN;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.GRID_LINE_COLOR_PN;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.GRID_LINE_SHOW_PN;
import static com.gmail.johnstraub1954.cell_automata.main.CAConstants.GRID_LINE_WIDTH_PN;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;

import com.gmail.johnstraub1954.cell_automata.main.ActionRegistrar;
import com.gmail.johnstraub1954.cell_automata.main.CAConstants;
import com.gmail.johnstraub1954.cell_automata.main.Parameters;

/**
 * A panel used to edit grid properties, 
 * such as grid color, grid cell color and grid line width.
 * Usually used as a child of a dialog.<br>
 * 
 * <img src="doc-files/GridPropertiesPanel.png" alt="GridPropertiesPanel.png">
 * 
 * @author Jack Straub
 */
public class GridPropertiesPanel extends JPanel
{
    /** Generated serial version UID */
    private static final long serialVersionUID = 1388936646835938326L;

    /** Parameter collector for this application */
    private static final Parameters params  = Parameters.INSTANCE;
    
    /** for creating empty border around this panel */
    private static final int        margin              = 5;
    
    /** minimum grid line width */
    private static final int        gridLineWidthMin     = 0;
    /** maximum grid line width (arbitrarily chosen) */
    private static final int        gridLineWidthMax     = 128;
    /** size of a step when manipulating cell-size spinner */
    private static final int        gridLineWidthStep    = 1;
    
    /** minimum cell size */
    private static final int        cellSizeMin     = 0;
    /** maximum cell size (arbitrarily chosen) */
    private static final int        cellSizeMax     = 128;
    /** size of a step when manipulating cell-size spinner */
    private static final int        cellSizeStep    = 1;
    
    /** Object that notifies NotificationListeners of apply actions */
    private final ActionRegistrar   actionRegistrar;

    /**
     * Constructor.
     * The user includes an ActionRegistrar which can be used to link
     * to actions in the parent dialog, such OK, Apply and Cancel.
     * 
     * @param actionRegistrar   link to actions in the parent dialog
     */
    public GridPropertiesPanel( ActionRegistrar actionRegistrar )
    {
        super( new GridBagLayout() );
        this.actionRegistrar = actionRegistrar;
        
        Border  emptyBorder = BorderFactory
            .createEmptyBorder( margin, margin, margin, margin );
        Border  lineBorder  = BorderFactory
            .createLineBorder( Color.BLACK, 2 );
        Border  border      = BorderFactory
            .createCompoundBorder( lineBorder, emptyBorder );
        setBorder( border );

        GridBagConstraints  gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        ColorPanel  colorPanel  = new ColorPanel( 
            "Grid Color",
            "GRID_COLOR_PN",
            CAConstants.PREF_GRID_COLOR_CN,
            CAConstants.PREF_GRID_COLOR_FB_CN,
            c -> params.setGridColor( c ),
            () -> params.getGridColor()
        );
        add( colorPanel, gbc );
        gbc.gridy++;
        
        add( new HSeparator( 3, true ), gbc );
        gbc.gridy++;
        
        colorPanel = new ColorPanel( 
            "Cell Color", 
            GRID_CELL_COLOR_PN,
            CAConstants.PREF_CELL_COLOR_CN,
            CAConstants.PREF_CELL_COLOR_FB_CN,
            c -> params.setGridCellColor( c ),
            () -> params.getGridCellColor() 
        );
        add( colorPanel, gbc );
        gbc.gridy++;
        
        add( new HSeparator( 3, true ), gbc );
        gbc.gridy++;
        
        colorPanel = new ColorPanel( 
            "Grid Line Color", 
            GRID_LINE_COLOR_PN,
            CAConstants.PREF_LINE_COLOR_CN,
            CAConstants.PREF_LINE_COLOR_FB_CN,
            c -> params.setGridLineColor( c ),
            () -> params.getGridLineColor() 
        );
        add( colorPanel, gbc );
        gbc.gridy++;
        
        add( new HSeparator( 3, true ), gbc );
        gbc.gridy++;
        
        SpinnerNumberPanel  spinnerPanel = new SpinnerNumberPanel(
            gridLineWidthMin,
            gridLineWidthMax,
            gridLineWidthStep,
            "Grid Line Width",
            GRID_LINE_WIDTH_PN,
            CAConstants.PREF_LINE_WIDTH_CN,
            i -> params.setGridLineWidth( i ),
            () -> params.getGridLineWidth()
        );
        add( spinnerPanel, gbc );
        gbc.gridy++;
        
        add( new HSeparator( 3, true ), gbc );
        gbc.gridy++;
        
        spinnerPanel = new SpinnerNumberPanel( 
            cellSizeMin,
            cellSizeMax,
            cellSizeStep,
            "Cell Size",
            GRID_CELL_SIZE_PN,
            CAConstants.PREF_CELL_SIZE_CN,
            i -> params.setGridCellSize( i ),
            () -> params.getGridCellSize()
        );
        add( spinnerPanel, gbc );
        gbc.gridy++;
        
        add( new HSeparator( 3, true ), gbc );
        gbc.gridy++;
        
        CheckBox    checkBox    = new CheckBox(
            "Show Grid",
            GRID_LINE_SHOW_PN,
            CAConstants.PREF_SHOW_GRID_CN,
            b -> params.setGridLineShow( b ),
            () -> params.isGridLineShow()
        );
        checkBox.setSelected( params.isGridLineShow() );
        add( checkBox, gbc );
    }

    /**
     * Creates a linked button/label pair.
     * The button is used to launch a color chooser dialog;
     * the label displays the chosen color.
     * Components are laid out horizontally.
     * The panel is also connected to the "apply" action
     * in the parent dialog;
     * when the apply action is propagated,
     * the color from the feedback label is set
     * in the appropriate property in the Parameters singleton.
     * 
     * @author Jack Straub
     */
    private class ColorPanel 
        extends JPanel 
        implements ActionListener
    {
        /** Generated serial version UID */
        private static final long serialVersionUID = -7212071808089091404L;
        
        /** The label that provides feedback */
        private final JLabel    label;
        
        /**
         * Constructor.
         * 
         * @param text          the text to display on the button
         * @param propertyName  the name of the property being configured
         * @param buttonCN      the component name of the button that
         *                      will launch the color chooser dialog
         * @param feedbackCN    the component name of the feedback label
         * @param consumer      used to get the value of the linked property
         * @param supplier      used to set the value of the linked property
         */
        public ColorPanel( 
            String          text, 
            String          propertyName,
            String          buttonCN,
            String          feedbackCN,
            Consumer<Color> consumer,
            Supplier<Color> supplier
        )
        {
            super( new GridLayout( 1, 2, 2, 2 ) );
            label = new JLabel( " " );
            JButton button  = new JButton( text );
            button.setName( buttonCN );
            Border  border  = 
                BorderFactory.createLineBorder( Color.BLACK, 1 );
            label.setBackground( supplier.get() );
            label.setBorder( border );
            label.setOpaque( true );
            label.setName( feedbackCN );

            button.addActionListener( this );
            
            add( button );
            add( label );
            
            // link this panel to the apply action in the dialog parent
            actionRegistrar.addNotificationListener(
                CAConstants.ACTION_APPLY_PN,
                e -> consumer.accept(label.getBackground() )
            );
            
            actionRegistrar.addNotificationListener(
                CAConstants.ACTION_OPENED_PN,
                e -> label.setBackground( supplier.get() )
            );
        }
        
        /**
         * Allow operator to choose a new color for some property.
         * 
         * @param evt   event object associated with this event
         */
        @Override
        public void actionPerformed( ActionEvent evt )
        {
            Object  source      = evt.getSource();
            if ( !(source instanceof JButton) )
                return;
            JButton button      = (JButton)source;
            String  title       = button.getText();
            Color   oldColor    = label.getBackground();
            Color   color   = 
                JColorChooser.showDialog( this, title, oldColor );
            System.out.println( color );
            if ( color != null )
            {
                label.setBackground( color );
            }
        }
    }
    
    /**
     * Creates a label/JNumberSpinner pair.
     * The label identifies the property
     * controlled by the spinner.
     * Components are laid out horizontally.
     * The panel is also to the "apply" action
     * in the parent dialog;
     * when the apply action is propagated,
     * the value from the spinner is set
     * in the appropriate property in the Parameters singleton.
     * 
     * @author Jack Straub
     */
    private class SpinnerNumberPanel 
        extends JPanel
    {
        /** Generated serial version UID */
        private static final long serialVersionUID = -6148427940082811615L;

        /**
         * Constructor.
         * 
         * @param min           minimum value property of the spinner
         * @param max           maximum value property of the spinner
         * @param step          step property of the spinner
         * @param text          text to display in the label
         * @param propertyName  the name of the property being configured
         * @param spinnerName   the component name of the spinner
         * @param consumer      used to set the value of the property in
         *                      the Parameters singleton
         * @param supplier      used to get the value of the property in
         *                      the Parameters singleton
         */
        public SpinnerNumberPanel(
            int         min,
            int         max,
            int         step,
            String      text, 
            String      propertyName,
            String      spinnerName,
            IntConsumer consumer,
            IntSupplier supplier
        )
        {
            super( new GridLayout( 1, 2, 2, 2 ) );
            JLabel  label   = new JLabel( text );
            label.setHorizontalAlignment( JLabel.RIGHT );
            
            int val = supplier.getAsInt();
            SpinnerNumberModel  model   = 
                new SpinnerNumberModel( val, min, max, step );
            JSpinner    spinner = new JSpinner( model );
            spinner.setName( spinnerName );
            add( label );
            add( spinner );
            
            actionRegistrar.addNotificationListener(
                CAConstants.ACTION_APPLY_PN,
                e -> consumer.accept( model.getNumber().intValue() )
            );
            actionRegistrar.addNotificationListener(
                CAConstants.ACTION_OPENED_PN,
                e -> model.setValue( supplier.getAsInt() )
            );
        }
    }
    
    /**
     * A subclass of JCheckBox that is used to configure the value
     * of a Boolean property.
     * 
     * @author Jack Straub
     */
    private class CheckBox extends JCheckBox
    {
        /** Generated serial version UID */
        private static final long serialVersionUID = 992944997939260776L;

        /**
         * Constructor.
         * 
         * @param text          text to display on the check box
         * @param propertyName  the name of the property being configured
         * @param spinnerName   the component name of the check box
         * @param consumer      used to set the value of the property in
         *                      the Parameters singleton
         * @param supplier      used to get the value of the property in
         *                      the Parameters singleton
         */
        public CheckBox( 
            String              text, 
            String              propertyName,
            String              checkBoxName,
            Consumer<Boolean>   consumer,
            Supplier<Boolean>   supplier
        )
        {
            super( text );
            setName( checkBoxName );
            setSelected( supplier.get() );
            actionRegistrar.addNotificationListener(
                CAConstants.ACTION_APPLY_PN,
                e -> consumer.accept( isSelected() )
            );
            actionRegistrar.addNotificationListener(
                CAConstants.ACTION_OPENED_PN,
                e -> model.setSelected( supplier.get() )
            );
        }
    }
}
