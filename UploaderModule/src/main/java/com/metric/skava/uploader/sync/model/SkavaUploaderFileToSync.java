package com.metric.skava.uploader.sync.model;

/**
 * Created by metricboy on 8/11/14.
 */
public class SkavaUploaderFileToSync extends SkavaUploaderDataToSync {

    private String fileName;

    public SkavaUploaderFileToSync(String environment, String assessmentCode) {
        super(environment, assessmentCode);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
