package com.gmail.johnstraub1954.cell_automata.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is a simple class that allows users to receive notification
 * of actions performed.
 * One use of this class is to allow GUI panels that accumulate information
 * to receive "apply", "cancel" and "okay" notifications.
 * 
 * @author Jack Straub
 */
public class ActionRegistrar
{
    // Implementation of per-property notification listeners:
    // without specific property:
    // ... add listener to notification listener list
    // ... do not add anything to the notification/property map
    // with specific property prop:
    // ... add listener to notification listener list
    // ... add prop to the notification/property map
    //
    // When dispatching notification notify for property prop:
    // ... any listener that doesn't have a property map entry is invoked
    // ... a listener with a property map entry is invoked only if
    //     prop equals the mapped property
    //
    // Removing a listener from the list:
    // ... the first listener match in the listener list is removed;
    //     it doesn't matter whether there's an entry in the property
    //     map or not.
    /** List of NotificationListeners */
    private final List<NotificationListener>  notificationListeners = 
        new ArrayList<>();
    /** map notification listeners to a specific property */
    private final Map<NotificationListener, String> 
        notificationPropertyMap = new HashMap<>();
    
    /**
     * Add a given NotificationListener to the list of NotificationListeners.
     * Listeners will be invoked for every notification event.
     * 
     * @param listener  the given NotificationListener
     * 
     * @see #addNotificationListener(String, NotificationListener)
     */
    public void addNotificationListener( NotificationListener listener )
    {
        notificationListeners.add( listener );
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
     * @see #addNotificationListener(NotificationListener)
     */
    public void 
    addNotificationListener( String property, NotificationListener listener )
    {
        notificationListeners.add( listener );
        notificationPropertyMap.put( listener, property );
    }
    
    /**
     * Remove a given NotificationListener from the 
     * list of NotificationListeners.
     * If the ActionListener is not in the list
     * no action is taken.
     * 
     * @param listener  the given NotificationListener
     */
    public void removeNotificationListener( NotificationListener listener )
    {
        notificationListeners.remove( listener );
        notificationPropertyMap.remove( listener );
    }

    /**
     * Fires a NotificationEvent associated with a given property
     * to NotificationListeners.
     * If non-null, the source of the event will be the
     * <em>source</em> parameter;
     * if <em>source</em> is null, the source will be 
     * <em>this</em> ActionRegistrar object.
     * After propagating the notification explicitly sends
     * a reset notification.
     * To suppress the reset notification, 
     * use {@link #fireNotificationEvent(String,Object,boolean)}
     * 
     * @param property  the given property
     * @param source    source of the event; may be null
     * 
     * @see #fireNotificationEvent(String, Object, boolean)
     */
    public void fireNotificationEvent( String property, Object source )
    {
        NotificationEvent   event   =
            new NotificationEvent( source, property );
        for ( NotificationListener listener : notificationListeners )
        {
            String  mappedProperty  = notificationPropertyMap.get( listener );
            if ( mappedProperty == null || mappedProperty.equals( property ) )
                listener.notification( event );
        }
    }
    
    /**
     * Fires a NotificationEvent associated with a given property
     * to NotificationListeners.
     *  The source of the event will be <em>this</em> ActionRegistrar object.
     * After propagating the notification explicitly sends
     * a reset notification.
     * To suppress the reset notification, 
     * use {@link #fireNotificationEvent(String,Object,boolean)}
     * 
     * @param property  the given property
     * 
     * @see #fireNotificationEvent(String, Object, boolean)
     */
    public void fireNotificationEvent( String property )
    {
        fireNotificationEvent( property, this, true );
    }
    
    /**
     * Fires a NotificationEvent associated with a given property
     * to NotificationListeners.
     * If non-null, the source of the event will be the
     * <em>source</em> parameter;
     * if <em>source</em> is null, the source will be 
     * <em>this</em> ActionRegistrar object.
     * After propagating the notification related to the given property,
     * optionally issues a reset notification.
     * 
     * @param property  the given property
     * @param source    source of the event; may be null
     * @param reset     true to automatically generate a reset notification
     */
    public void 
    fireNotificationEvent( String property, Object source, boolean reset )
    {
        fireNotificationEvent( property, this );
        if ( reset )
            Parameters.INSTANCE.reset();
    }
}
