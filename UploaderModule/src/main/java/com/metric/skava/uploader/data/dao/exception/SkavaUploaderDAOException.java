package com.metric.skava.uploader.data.dao.exception;

/**
 * Created by metricboy on 8/7/14.
 */
public class SkavaUploaderDAOException extends Exception {
    public SkavaUploaderDAOException() {
    }

    public SkavaUploaderDAOException(String detailMessage) {
        super(detailMessage);
    }

    public SkavaUploaderDAOException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public SkavaUploaderDAOException(Throwable throwable) {
        super(throwable);
    }
}
