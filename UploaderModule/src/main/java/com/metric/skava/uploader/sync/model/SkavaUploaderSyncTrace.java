package com.metric.skava.uploader.sync.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 8/11/14.
 */
public class SkavaUploaderSyncTrace {

    private String environment;
    private String assessmentCode;
    private List<SkavaUploaderFileToSync> files;


    public SkavaUploaderSyncTrace(String environment, String theAssessmentCode) {
        this.environment = environment;
        this.assessmentCode = theAssessmentCode;
        this.files = new ArrayList<SkavaUploaderFileToSync>();
    }


    public void clearFiles() {
        files.clear();
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

    public List<SkavaUploaderFileToSync> getFiles() {
        return files;
    }

    public void setFiles(List<SkavaUploaderFileToSync> files) {
        this.files = files;
    }

    public void addFile(SkavaUploaderFileToSync fileToSync) {
        getFiles().add(fileToSync);
    }



}
