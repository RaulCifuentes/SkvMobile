package com.metric.skava.app.exception;

/**
 * Created by metricboy on 3/18/14.
 */
public class SkavaSystemException extends RuntimeException {

    public SkavaSystemException() {
        super();
    }

    public SkavaSystemException(String detailMessage) {
        super(detailMessage);
    }

    public SkavaSystemException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public SkavaSystemException(Throwable throwable) {
        super(throwable);
    }
}
