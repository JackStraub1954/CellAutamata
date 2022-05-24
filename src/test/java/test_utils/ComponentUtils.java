package test_utils;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;

/**
 * This class contains miscellaneous utilities to support GUI testing.
 * 
 * @author Jack Straub
 */
public class ComponentUtils
{
    /**
     * Obtains JButton with the given name.
     * A JUnit assertion is thrown if the component doesn't exist.
     * A JUnit assertion is thrown if the component is the wrong type.
     * 
     * @param name  the given name
     * 
     * @return  the target component; will never be null
     */
    public static JButton getJButton( String name )
    {
        Component   comp = ComponentLocator.getComponent( name );
        assertNotNull( comp );
        assertTrue( comp instanceof JButton );
        JButton button  = (JButton)comp;
        return button;
    }
    
    public static JSlider getJSlider( String name )
    {
        Component   comp    = ComponentLocator.getComponent( name );
        assertNotNull( comp );
        assertTrue( comp instanceof JButton );
        JSlider     slider  = (JSlider)comp;
        return slider;
    }
    
    public static JComponent getJComponent( String name )
    {
        Component   comp    = ComponentLocator.getComponent( name );
        assertNotNull( comp );
        assertTrue( comp instanceof JComponent );
        JComponent  jComp   = (JComponent)comp;
        return jComp;
    }

    public static JTextField getJTextField( String name )
    {
        Component   comp        = ComponentLocator.getComponent( name );
        assertNotNull( comp );
        assertTrue( comp instanceof JTextField );
        JTextField textField    = (JTextField)comp;
        return textField;
    }

    public static JLabel getJLabel( String name )
    {
        Component   comp    = ComponentLocator.getComponent( name );
        assertNotNull( comp );
        assertTrue( comp instanceof JLabel );
        JLabel      label   = (JLabel)comp;
        return label;
    }

    public static JSpinner getJSpinner( String name )
    {
        Component   comp    = ComponentLocator.getComponent( name );
        assertNotNull( comp );
        assertTrue( comp instanceof JSpinner );
        JSpinner    spinner = (JSpinner)comp;
        return spinner;
    }

    public static JCheckBox getJCheckBox( String name )
    {
        Component   comp        = ComponentLocator.getComponent( name );
        assertNotNull( comp );
        assertTrue( comp instanceof JCheckBox );
        JCheckBox   checkBox    = (JCheckBox)comp;
        return checkBox;
    }
}
