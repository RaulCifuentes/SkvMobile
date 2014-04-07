package com.metric.skava.data.dao.exception;

/**
 * Created by metricboy on 3/5/14.
 */
public class DAOException extends Exception {

    public DAOException(String detailMessage) {
        super(detailMessage);
    }

    public DAOException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public DAOException(Throwable throwable) {
        super(throwable);
    }
}
