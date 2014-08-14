package com.metric.skava.uploader.sync.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by metricboy on 8/11/14.
 */
public class SkavaUploaderSyncQueue {

    private Map<String, List<SkavaUploaderFileToSync>> filesPerAssessment;


    public SkavaUploaderSyncQueue() {
        this.filesPerAssessment = new HashMap<String, List<SkavaUploaderFileToSync>>();
    }

    public Map<String, List<SkavaUploaderFileToSync>> getAllFiles() {
        return filesPerAssessment;
    }


    public void clearFiles(String assessmentCode) {
        if (filesPerAssessment.containsKey(assessmentCode)) {
            filesPerAssessment.clear();
        }
    }

    
    public List<SkavaUploaderFileToSync> getFiles(String assessmentCode) {
        if (filesPerAssessment.containsKey(assessmentCode)) {
            return filesPerAssessment.get(assessmentCode);
        }
        return null;
    }


    public void addFile(SkavaUploaderFileToSync fileToSync) {
        String assessmentCode = fileToSync.getAssessmentCode();
        if (filesPerAssessment.containsKey(assessmentCode)) {
            getFiles(assessmentCode).add(fileToSync);
        } else {
            ArrayList<SkavaUploaderFileToSync> files = new ArrayList<SkavaUploaderFileToSync>();
            files.add(fileToSync);
            filesPerAssessment.put(assessmentCode, files);
        }
    }


}


