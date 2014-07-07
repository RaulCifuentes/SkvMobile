package com.metric.skava.sync.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by metricboy on 6/20/14.
 */
public class SyncQueue {

    private Map<String, List<FileToSync>> filesPerAssessment;
    private Map<String, List<RecordToSync>> recordsPerAssessment;

    public SyncQueue() {
        this.recordsPerAssessment= new HashMap<String, List<RecordToSync>>();
        this.filesPerAssessment = new HashMap<String, List<FileToSync>>();
    }

    public Map<String, List<FileToSync>> getAllFiles() {
        return filesPerAssessment;
    }

    public Map<String, List<RecordToSync>> getAllRecords() {
        return recordsPerAssessment;
    }

    public void clearFiles(String assessmentCode){
        if (filesPerAssessment.containsKey(assessmentCode)){
            filesPerAssessment.clear();
        }
    }

    public void clearRecords(String assessmentCode){
        if (recordsPerAssessment.containsKey(assessmentCode)){
            recordsPerAssessment.clear();
        }
    }

    public List<FileToSync> getFiles(String assessmentCode){
        if (filesPerAssessment.containsKey(assessmentCode)) {
            return filesPerAssessment.get(assessmentCode);
        }
        return null;
    }

    public List<RecordToSync> getRecords(String assessmentCode){
        if (recordsPerAssessment.containsKey(assessmentCode)){
            return recordsPerAssessment.get(assessmentCode);
        }
        return null;
    }

//    public void addFile(String assessmentCode, FileToSync fileToSync){
//        if (filesPerAssessment.containsKey(assessmentCode)){
//            getFiles(assessmentCode).add(fileToSync);
//        } else {
//            ArrayList<FileToSync> files = new ArrayList<FileToSync>();
//            files.add(fileToSync);
//            filesPerAssessment.put(assessmentCode, files);
//        }
//    }
//
//    public void addRecord(String assessmentCode, RecordToSync recordToSync){
//        if (recordsPerAssessment.containsKey(assessmentCode)) {
//            getRecords(assessmentCode).add(recordToSync);
//        } else {
//            ArrayList<RecordToSync> records = new ArrayList<RecordToSync>();
//            records.add(recordToSync);
//            recordsPerAssessment.put(assessmentCode, records);
//        }
//    }

    public void addFile(FileToSync fileToSync){
        String assessmentCode = fileToSync.getAssessmentCode();
        if (filesPerAssessment.containsKey(assessmentCode)){
            getFiles(assessmentCode).add(fileToSync);
        } else {
            ArrayList<FileToSync> files = new ArrayList<FileToSync>();
            files.add(fileToSync);
            filesPerAssessment.put(assessmentCode, files);
        }
    }

    public void addRecord(RecordToSync recordToSync){
        String assessmentCode = recordToSync.getAssessmentCode();
        if (recordsPerAssessment.containsKey(assessmentCode)) {
            getRecords(assessmentCode).add(recordToSync);
        } else {
            ArrayList<RecordToSync> records = new ArrayList<RecordToSync>();
            records.add(recordToSync);
            recordsPerAssessment.put(assessmentCode, records);
        }
    }

}
