package com.gmail.johnstraub1954.cell_automata.main;

public class CAException extends Error
{
    /** Generated serial version ID. */
    private static final long serialVersionUID = 4029146397300699557L;

    public CAException()
    {
        // TODO Auto-generated constructor stub
    }

    public CAException(String message)
    {
        super(message);
    }

    public CAException(Throwable cause)
    {
        super(cause);
    }

    public CAException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public CAException(String message, Throwable cause,
        boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
