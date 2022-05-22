/**
 * 
 */
package com.gmail.johnstraub1954.cell_automata.main;

/**
 * Listener associated with processing Notification events.
 * 
 * @author Jack Straub
 *
 *@see NotificationEvent
 */
public interface NotificationListener
{
    /**
     * Required method for processing notification events.
     * The given NotificationEvent can be examined
     * in order to determine what property the event
     * is associated.
     * 
     * @param event the event associated with a notification.
     */
    public void notification( NotificationEvent event );
}
