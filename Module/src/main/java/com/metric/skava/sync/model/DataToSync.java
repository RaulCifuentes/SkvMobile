package com.metric.skava.sync.model;

/**
 * Created by metricboy on 6/6/14.
 */
public class DataToSync {

    public enum Operation {INSERT, DELETE}

    private Operation operation;


    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }
}
