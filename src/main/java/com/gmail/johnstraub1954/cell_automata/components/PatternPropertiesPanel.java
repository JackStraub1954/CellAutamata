package com.gmail.johnstraub1954.cell_automata.components;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.border.Border;

import com.gmail.johnstraub1954.cell_automata.main.ActionRegistrar;
import com.gmail.johnstraub1954.cell_automata.main.CAConstants;
import com.gmail.johnstraub1954.cell_automata.main.CAException;
import com.gmail.johnstraub1954.cell_automata.main.Parameters;

/**
 * Panel for managing pattern properties, such as pattern name and
 * pattern author.
 * Usually used as a child of a dialog.<br>
 * 
 * <img 
 *      src="doc-files/PatternPropertiesPanel.png" 
 *      alt="PatternPropertiesPanel.png"
 * >
 * 
 * @author Jack Straub
 */
public class PatternPropertiesPanel extends JPanel
{
    /** Generated serial version UID */
    private static final long serialVersionUID = -4236556116528317750L;

    /** Time zone ID for translating between Date and LocalDate */
    private static final ZoneId    ZONE_ID     = ZoneId.systemDefault();
    
    /** Parameter collector for this application */
    private static final Parameters params  = Parameters.INSTANCE;
    
    /** for creating empty border around this panel */
    private static final int        margin              = 5;
    
    /** Object that notifies NotificationListeners of apply actions */
    private final ActionRegistrar   actionRegistrar;

    /**
     * Constructor.
     * The given ActionRegistrar can be used to register for such 
     * notification as Apply and Cancel.
     * 
     * @param actionRegistrar   parent dialog facility that allows
     *                          registration for actions
     */
    public PatternPropertiesPanel( ActionRegistrar actionRegistrar )
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
        gbc.anchor = GridBagConstraints.PAGE_START;
        
        TextFieldPanel  textPanel   = null;
        textPanel = new TextFieldPanel( 
            "Pattern Name",
            CAConstants.PREF_PATTERN_NAME_CN,
            s -> params.setPatternName( s ),
            () -> params.getPatternName()
        );
        add( textPanel, gbc );
        gbc.gridy++;
        
        add( new HSeparator( 3, true ), gbc );
        gbc.gridy++;
        
        textPanel = new TextFieldPanel( 
            "Author Name",
            CAConstants.PREF_AUTHOR_NAME_CN,
            s -> params.setAuthorName( s ),
            () -> params.getAuthorName()
        );
        add( textPanel, gbc );
        gbc.gridy++;
        
        add( new HSeparator( 3, true ), gbc );
        gbc.gridy++;
        
        textPanel = new TextFieldPanel( 
            "Author Email",
            CAConstants.PREF_AUTHOR_EMAIL_CN,
            s -> params.setAuthorEmail( s ),
            () -> params.getAuthorEmail()
        );
        add( textPanel, gbc );
        gbc.gridy++;
        
        add( new HSeparator( 3, true ), gbc );
        gbc.gridy++;


        textPanel = new TextFieldPanel( 
            "Pattern File Name",
            CAConstants.PREF_FILE_NAME_CN,
            s -> params.setPatternFileName( s ),
            () -> params.getPatternFileName()
        );
        add( textPanel, gbc );
        gbc.gridy++;
        
        add( new HSeparator( 3, true ), gbc );
        gbc.gridy++;

        SpinnerDatePanel    datePanel   = new SpinnerDatePanel(
            "Author Date",
            CAConstants.PREF_AUTHOR_TIME_CN,
            d -> params.setAuthorTime( d ),
            () -> params.getAuthorTime()
        );
        add( datePanel, gbc );
        gbc.gridy++;
        
        add( new HSeparator( 3, true ), gbc );
        gbc.gridy++;
        
        IntegerListPanel    listPanel   = new IntegerListPanel(
            "Birth Rules",
            CAConstants.PREF_BIRTH_RULES_CN,
            l -> params.setBirthStates( l ),
            () -> params.getBirthStates()
        );
        add( listPanel, gbc );
        gbc.gridy++;
        
        add( new HSeparator( 3, true ), gbc );
        gbc.gridy++;
        
        listPanel = new IntegerListPanel(
            "Survival Rules",
            CAConstants.PREF_SURVIVAL_RULES_CN,
            l -> params.setSurvivalStates( l ),
            () -> params.getSurvivalStates()
        );
        add( listPanel, gbc );
        gbc.gridy++;
    }
    
    /**
     * Converts a given list of integers to a comma-separated values string.
     * Example: List.of( 1, 2, 3 ) converts to "1,2,3".
     * 
     * @param list  the given list
     * 
     * @return  a CSV string containing the integers in the given list
     * 
     * @see #cvtCSVToList(String)
     */
    private static String cvtListToCSV( List<?> list )
    {
        StringBuilder   bldr    = 
            list.stream()
            .distinct()
            .sorted()
            .map( e -> e + "," )
            .collect( 
                StringBuilder::new, 
                StringBuilder::append, 
                StringBuilder::append
            );
        int len = bldr.length();
        if ( len > 0 )
            bldr.deleteCharAt( len - 1 );
        return bldr.toString();
    }
    
    /**
     * Converts a given comma-separated string of integers
     * to a list of integers.
     * Example: "1,2,3" converts to List.of( 1, 2, 3 ).
     * 
     * @param csv   the given comma-separated string of integers
     * 
     * @return a list containing the integers in the given string
     * 
     * @see #cvtListToCSV(List)
     */
    private static List<Integer> cvtCSVToList( String csv )
    {
        List<Integer>   list    = new ArrayList<>();
        if ( csv.isEmpty() )
            return list;
        String[]        strs    = csv.split( "," );
        try
        {
            for ( String str : strs )
                list.add( Integer.parseInt( str.trim() ) );
        }
        catch ( NumberFormatException exc )
        {
            String  msg = "\"" + csv + "\" is not a list of integers";
            JOptionPane.showMessageDialog(
                null,
                msg,
                "Invalid input", 
                JOptionPane.ERROR_MESSAGE
            );
        }
        return list;
    }
    
    /**
     * Translates a java.util.Date object into a LocalDate object.
     * An exception is thrown if the input is not a Date object.
     * 
     * @param obj   The object to translate
     * 
     * @return  The translated value
     * 
     * @throws CAException if the given object is not type Date
     */
    private static LocalDateTime getLocalDate( Object obj )
        throws CAException
    {
        if ( !(obj instanceof Date) )
        {
            String  message = 
                "\"" + obj.getClass().getName() + "\" "
                + "is not type Date";
            throw new CAException( message );
        }
        Date            dateIn  = (Date)obj;
        LocalDateTime   dateOut =
            dateIn.toInstant().atZone( ZONE_ID ).toLocalDateTime();
        return dateOut;
    }
    
    /**
     * Translates a LocalDateTime object into a java.util.Date object.
     * 
     * @param obj   The object to translate
     * 
     * @return  The translated value
     */
    private static Date getDate( LocalDateTime dateIn )
    {
        ZonedDateTime   zTime       = dateIn.atZone( ZONE_ID );
        Instant         instant     = zTime.toInstant();
        Date            dateOut     = Date.from( instant );
        return dateOut;
    }

    /**
     * Creates a JPanel consisting of a label and a text field
     * arranged horizontally.
     * 
     * @author Jack Straub
     */
    private class TextFieldPanel extends JPanel
    {
        /** Generated serial version UID */
        private static final long serialVersionUID = -680084702645498914L;

        /**
         * Constructor.
         * 
         * @param text          the text to place on the label
         * @param textFieldName name for the text field component
         * @param consumer      reference to method in Parameters instance
         *                      used to set the value of the associated
         *                      property
         * @param supplier      reference to method in Parameters instance
         *                      used to get the value of the associated
         *                      property
         */
        public TextFieldPanel( 
            final String           text, 
            final String           textFieldName,
            final Consumer<String> consumer,
            final Supplier<String> supplier
        )
        {
            super( new GridLayout( 1, 2, 2, 2 ) );
            JLabel      label       = new JLabel( text );
            JTextField  textField   = new JTextField( supplier.get(), 10 );
            textField.setName( textFieldName );
            label.setHorizontalAlignment( JLabel.RIGHT );
            add( label );
            add( textField );
            actionRegistrar.addNotificationListener( 
                CAConstants.ACTION_APPLY_PN,
                e -> consumer.accept( textField.getText() )
            );
            actionRegistrar.addNotificationListener(
                CAConstants.ACTION_OPENED_PN,
                e -> textField.setText( supplier.get() )
            );
        }
    }
    
    /**
     * Creates a JPanel consisting of a label and a text field
     * arranged horizontally. 
     * The text field is assumed to contain a comma-separated
     * string of integers.
     * 
     * @author Jack Straub
     */
    private class IntegerListPanel extends JPanel
    {
        /** Generated serial version UID */
        private static final long serialVersionUID = -4763706338474726209L;

        /**
         * Constructor.
         *          * 
         * @param text          the text to place on the label
         * @param textFieldName name for the text field component
         * @param consumer      reference to method in Parameters instance
         *                      used to set the value of the associated
         *                      property
         * @param supplier      reference to method in Parameters instance
         *                      used to get the value of the associated
         *                      property
         */
        public IntegerListPanel( 
            final String                  text, 
            final String                  textFieldName,
            final Consumer<List<Integer>> consumer,
            final Supplier<List<Integer>> supplier
        )
        {
            super( new GridLayout( 1, 2, 2, 2 ) );
            JLabel          label       = new JLabel( text );
            JTextField      textField   = new JTextField( 10 );
            textField.setName( textFieldName );
            textField.setText( cvtListToCSV( supplier.get() ) );
            label.setHorizontalAlignment( JLabel.RIGHT );
            add( label );
            add( textField );
            
            actionRegistrar.addNotificationListener(
                CAConstants.ACTION_APPLY_PN,
                e -> consumer.accept( cvtCSVToList( textField.getText() ) ) 
            );
            actionRegistrar.addNotificationListener(
                CAConstants.ACTION_OPENED_PN,
                e -> textField.setText( cvtListToCSV( supplier.get() ) )
            );            
        }
    }
    
    /**
     * Creates a JPanel consisting of a label and a date spinner
     * arranged horizontally. 
     * 
     * @author Jack Straub
     */
    private class SpinnerDatePanel extends JPanel
    {
        /** Generated serial version UID */
        private static final long serialVersionUID = 8724490733282506802L;

        /**
         * Constructor.
         *
         * @param text          the text to place on the label
         * @param textFieldName name for the spinner component
         * @param consumer      reference to method in Parameters instance
         *                      used to set the value of the associated
         *                      property
         * @param supplier      reference to method in Parameters instance
         *                      used to get the value of the associated
         *                      property
         */
        public SpinnerDatePanel(
            final String                  text, 
            final String                  spinnerName,
            final Consumer<LocalDateTime> consumer,
            final Supplier<LocalDateTime> supplier
        )
        {
            super( new GridLayout( 1, 2, 2, 2 ) );
            SpinnerDateModel    model       = new SpinnerDateModel();
            JLabel              label       = new JLabel( text );
            JSpinner            spinner     = new JSpinner( model );
            LocalDateTime       dateTime    = supplier.get();
            Date                date        = getDate( dateTime );
            model.setValue( date );
            spinner.setName( spinnerName );
            
            label.setHorizontalAlignment( JLabel.RIGHT );
            add( label );
            add( spinner );
            actionRegistrar.addNotificationListener(
                CAConstants.ACTION_APPLY_PN, //this
                e -> consumer.accept( getLocalDate( model.getDate() ) )
            );
            actionRegistrar.addNotificationListener(
                CAConstants.ACTION_OPENED_PN,
                e -> model.setValue( getDate( supplier.get() ) )
            );
        }
    }
}
