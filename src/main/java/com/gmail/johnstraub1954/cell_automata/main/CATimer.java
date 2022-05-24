package com.gmail.johnstraub1954.cell_automata.main;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Provides access to the Java Timer utility.
 * Enables tasks to be executed at millisecond intervals.
 * An individual task can be cancelled,
 * and the Timer itself can be terminated,
 * which will cancel every scheduled task.
 * Note that the scheduler runs on a separate thread;
 * to terminate the thread, invoke the cancel method.
 * Example: <pre> 
    final String message = "To be printed every .5 second";
    TimerTask    task    = CATimer.INSTANCE.addTask( 
        500, 
        () -&gt; System.out.println( message )
    );
    try
    { 
        Thread.sleep( 2000 ); 
    } 
    catch ( InterruptedException exc) {}
    task.cancel();
    CATimer.INSTANCE.dispose();</pre>

 * 
 * @author Jack Straub
 * 
 * @see java.util.TimerTask#cancel
 */
public enum CATimer
{
    /** This enum's singleton. */
    INSTANCE;
    
    /** Associated system scheduler */
    private final Timer timer   = new Timer( "Game of Life Timer" );
    
    /** Indicates whether this timer has been disposed. */
    private boolean     isDisposed  = false;
    
    /**
     * Schedules a task to be executed at millisecond intervals.
     * Returns a TimerTask object which can be used to cancel
     * task execution.
     * 
     * @param millis    the interval between executions in milliseconds
     * @param runnable  the task to be executed after each interval expires
     * 
     * @return  TimeTask control object
     * 
     * @throws IllegalStateException if the timer has been disposed
     * 
     * @see java.util.TimerTask#cancel()
     */
    public synchronized TimerTask addTask( long millis, Runnable runnable )
        throws IllegalStateException
    {
        final Task  task    = new Task( runnable );
        timer.schedule( task, 0, millis );
        return task;
    }
    
    /**
     * Cancels all tasks, and terminates the scheduler thread.
     * Attempts to add a task after calling this method
     * will result in an exception being thrown.
     */
    public synchronized void dispose()
    {
        isDisposed = true;
        timer.cancel();
    }
    
    /**
     * Indicates whether this timer has been disposed.
     */
    public boolean isDisposed()
    {
        return isDisposed;
    }
    
    /**
     * Encapsulates a TimerTask object which can be used
     * to schedule a task at millisecond intervals.
     * 
     * @author Jack Straub
     */
    private static class Task extends TimerTask
    {
        /** Encapsulates the task to be executed when the interval expires */
        private final Runnable  runnable;
        
        public Task( Runnable runnable )
        {
            this.runnable = runnable;
        }
        
        public void run()
        {
            runnable.run();
        }
        
        @Override
        public boolean cancel()
        {
            boolean status  = super.cancel();
            CATimer.INSTANCE.timer.purge();
            return status;
        }
    }
}
