package com.metric.skava.sync.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 7/5/14.
 */
public class AssessmentSyncTrace {

    private String environment;
    private String assessmentCode;
    private List<FileToSync> files;
    private List<RecordToSync> records;

    public AssessmentSyncTrace(String environment, String theAssessmentCode) {
        this.environment = environment;
        this.assessmentCode = theAssessmentCode;
        this.records = new ArrayList<RecordToSync>();
        this.files = new ArrayList<FileToSync>();
    }


    public void clearFiles() {
        files.clear();
    }

    public void clearRecords() {
        records.clear();
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

    public List<FileToSync> getFiles() {
        return files;
    }

    public void setFiles(List<FileToSync> files) {
        this.files = files;
    }

    public List<RecordToSync> getRecords() {
        return records;
    }

    public void setRecords(List<RecordToSync> records) {
        this.records = records;
    }

    public void addFile(FileToSync fileToSync) {
        getFiles().add(fileToSync);
    }

    public void addRecord(RecordToSync recordToSync) {
        getRecords().add(recordToSync);
    }


}
