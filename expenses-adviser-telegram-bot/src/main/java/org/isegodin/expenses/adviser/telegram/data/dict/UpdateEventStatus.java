package org.isegodin.expenses.adviser.telegram.data.dict;

/**
 * @author isegodin
 */
public enum UpdateEventStatus {

    /**
     * New event waits processing
     */
    NEW,

    /**
     * Update processor required additional input
     */
    PENDING,

    /**
     * Already processed event, can be removed from database.
     */
    PROCESSED,

    /**
     * Unknown message format, has no processor
     */
    UNKNOWN,

    /**
     * Error occurred while processing. Details should be store in errorDescription field.
     */
    ERROR;
}
