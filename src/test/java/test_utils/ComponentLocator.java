package test_utils;

import java.awt.Component;
import java.awt.Container;
import java.awt.Window;
import java.util.function.BiPredicate;

import javax.swing.AbstractButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

public class ComponentLocator
{
    /** Convenient predicate to find a component by component name. */
    public static final BiPredicate<Component,Object> nameFinder    = 
        (c,s) -> s.equals( c.getName() );
        
    /** Convenient predicate to find a button by its label. */
    public static final BiPredicate<Component,Object> textFinder    = 
        (c,s) -> c instanceof AbstractButton 
                 && s.equals( ((AbstractButton)c).getText() );
    
    /**
     * Obtains a Swing component by its name.
     * 
     * @param name  the name of the component to find
     * 
     * @return the target component, or null if not found
     */
    public static Component getComponent( String name )
    {
        Component   comp    = getComponent( nameFinder, name );
        return comp;
    }
    
    /**
     * Obtains a Swing component given an object and a predicate.
     * 
     * @param pred  the given predicate
     * @param obj   the given object
     * 
     * @return the target component, or null if not found
     */
    public static Component 
    getComponent( BiPredicate<Component,Object> pred, Object obj )
    {
        Component   comp    = null;
        Window[]    frames  = Window.getWindows();
        for ( int inx = 0 ; inx < frames.length && comp == null ; ++inx )
        {
            Window  frame   = frames[inx];
            if ( !frame.isDisplayable() )
                continue;
            if ( pred.test( frame, obj ) )
                comp = frame;
            else if ( frame instanceof JFrame )
            {
                Container   cont    = ((JFrame)frame).getContentPane();
                comp = getComponent( cont, pred, obj );
            }
            else if ( frame instanceof JDialog )
            {
                Container   cont    = ((JDialog)frame).getContentPane();
                comp = getComponent( cont, pred, obj );
            }
            else
                comp = getComponent( frame, pred, obj );
        }
        return comp;
    }
    
    /**
     * Finds a Swing component given a Container to search,
     * a predicate and an object. 
     * The Container is searched recursively.
     * 
     * @param container the given container
     * @param pred      the given predicate
     * @param obj       the given object
     * 
     * @return the target component, or null if not found
     */
    public static Component getComponent(
        Container container, 
        BiPredicate<Component,Object> pred, 
        Object obj
    )
    {
        Component       comp        = null;
        Component[]     children    = container.getComponents();
        int             numChildren = children.length;
        for ( int inx = 0 ; inx < numChildren && comp == null ; ++inx )
        {
            Component   test    = children[inx];
            if ( pred.test( test, obj ) )
                comp = test;
            else if ( test instanceof Container )
                comp = getComponent( (Container)test, pred, obj );
            else
                ;
        }
        return comp;
    }
    
    /**
     * Obtains a Swing component given an object and a predicate.
     * 
     * @param pred  the given predicate
     * @param obj   the given object
     * 
     * @return the target component, or null if not found
     */
    public static Component 
    getContainer( BiPredicate<Component,Object> pred, Object obj )
    {
        Component   comp    = null;
        Window[]    frames  = Window.getWindows();
        for ( int inx = 0 ; inx < frames.length && comp == null ; ++inx )
        {
            Window  frame   = frames[inx];
            if ( !frame.isDisplayable() )
                continue;
            if ( pred.test( frame, obj ) )
                comp = frame;
            else if ( frame instanceof JFrame )
            {
                Container   cont    = ((JFrame)frame).getContentPane();
                comp = getComponent( cont, pred, obj );
            }
            else if ( frame instanceof JDialog )
            {
                Container   cont    = ((JDialog)frame).getContentPane();
                comp = getComponent( cont, pred, obj );
            }
            else
                comp = getComponent( frame, pred, obj );
        }
        return comp;
    }
    
    public static JDialog 
    getJDialogForComponent( Component component, boolean mustBeVisible )
    {
        JDialog dialog  = null;
        for ( Window window : Window.getWindows() )
        {
            if ( window instanceof JDialog )
            {
                JDialog testDialog  = (JDialog)window;
                if ( mustBeVisible && !testDialog.isVisible() )
                    continue;
                Container   container       = testDialog.getContentPane();
                Component   testComponent   = 
                    getComponent( container, (c,o) -> c == o, component );
                if ( testComponent != null )
                {
                    dialog = testDialog;
                    break;
                }
            }
        }
        return dialog;
    }
}
