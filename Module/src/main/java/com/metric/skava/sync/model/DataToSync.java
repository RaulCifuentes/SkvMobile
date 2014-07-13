package com.metric.skava.sync.model;

import java.util.Date;

/**
 * Created by metricboy on 6/6/14.
 */
public class DataToSync {

    public enum Operation {INSERT, DELETE}
    public enum Status {QUEUED, SERVED}

    private String environment;
    private String assessmentCode;
    private Operation operation;
    private Date date;
    private Status status;

    public DataToSync(String environment, String assessmentCode) {
        this.environment = environment;
        this.assessmentCode = assessmentCode;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getAssessmentCode() {
        return assessmentCode;
    }

    public void setAssessmentCode(String assessmentCode) {
        this.assessmentCode = assessmentCode;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }
}
