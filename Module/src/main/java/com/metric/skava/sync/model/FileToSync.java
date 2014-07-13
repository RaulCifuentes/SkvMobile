package com.metric.skava.sync.model;

/**
 * Created by metricboy on 6/13/14.
 */
public class FileToSync extends DataToSync {

    private String fileName;

    public FileToSync(String environment, String assessmentCode) {
        super(environment, assessmentCode);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
