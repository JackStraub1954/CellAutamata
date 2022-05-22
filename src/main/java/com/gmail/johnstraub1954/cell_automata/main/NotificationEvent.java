/**
 * 
 */
package com.gmail.johnstraub1954.cell_automata.main;

import java.awt.event.ActionEvent;

/**
 * Encapsulates an event informing the listener 
 * of a notification.
 * The event is identified by a property name
 * contained in the event object.
 * 
 * @author Jack Straub
 *
 * @see NotificationListener
 * @see #getProperty()
 */
public class NotificationEvent extends ActionEvent
{
    /** Generated serial version UID */
    private static final long serialVersionUID = 5454849246066240192L;
    /**
     * Name of the property associated with this event.
     */
    private final String    property;
    
    /**
     * Constructor. 
     * Most arguments are passed to the ActionEvent superclass.
     * See the documentation java.awt.AWTEvent.ActionEvent
     * 
     * @param source    source of this event
     * @param property  name of property associated with this event
     * 
     */
    public NotificationEvent( Object source, String property )
    {
        this( source, ACTION_PERFORMED, null, 0, 0, property );
    }
    
    /**
     * Constructor. 
     * Most arguments are passed to the ActionEvent superclass.
     * See the documentation java.awt.AWTEvent.ActionEvent
     * 
     * @param source    source of this event
     * @param id        id of this event (see <em>class ActionEvent</em>
     * @param property  name of property associated with this event
     * 
     */
    public NotificationEvent( Object source, int id, String property )
    {
        this( source, id, null, 0, 0, property );
    }
    
    /**
     * Constructor. 
     * Most arguments are passed to the ActionEvent superclass.
     * See the documentation java.awt.AWTEvent.ActionEvent
     * 
     * @param source    source of this event
     * @param id        id of this event (see <em>class ActionEvent</em>
     * @param command   command associated with this event 
     *                  (see <em>class ActionEvent</em>
     * @param modifiers modifiers associated with this event 
     *                  (see <em>class ActionEvent</em>
     * @param property  name of property associated with this event
     * 
     */
    public NotificationEvent(
        Object  source,
        int     id, 
        String  command,
        int     modifiers,
        String  property
    )
    {
        this( source, id, command, 0, modifiers, property );
    }

    /**
     * Constructor. 
     * Most arguments are passed to the ActionEvent superclass.
     * See the documentation java.awt.AWTEvent.ActionEvent
     * 
     * @param source    source of this event
     * @param id        id of this event (see <em>class ActionEvent</em>
     * @param command   command associated with this event 
     *                  (see <em>class ActionEvent</em>
     * @param when      timestamp associated with this event
     * @param modifiers modifiers associated with this event 
     *                  (see <em>class ActionEvent</em>
     * @param property  name of property associated with this event
     */
    public NotificationEvent(
        Object  source, 
        int     id, 
        String  command, 
        long    when,
        int     modifiers,
        String  property
    )
    {
        super(source, id, command, when, modifiers);
        this.property = property;
    }

    /**
     * Constructor. 
     * Most arguments are passed to the ActionEvent superclass.
     * See the documentation java.awt.AWTEvent.ActionEvent
     * 
     * @param source    source of this event
     * @param id        id of this event (see <em>class ActionEvent</em>
     * @param command   command associated with this event 
     *                  (see <em>class ActionEvent</em>
     * @param property  name of property associated with this event
     */
    public NotificationEvent(
        Object  source, 
        int     id, 
        String  command,
        String  property
    )
    {
        this(source, id, command, 0, 0, property );
    }

    /**
     * Returns the name of the property associated with this event.
     * 
     * @return the name of the property associated with this event
     */
    public String getProperty()
    {
        return property;
    }
}