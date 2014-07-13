package com.metric.skava.sync.model;

/**
 * Created by metricboy on 6/13/14.
 */
public class RecordToSync extends DataToSync {

    private String recordID;
    private String skavaEntityCode;

    public RecordToSync(String environment, String assessmentCode) {
        super(environment, assessmentCode);
    }

    public String getRecordID() {
        return recordID;
    }

    public void setRecordID(String recordID) {
        this.recordID = recordID;
    }

    public String getSkavaEntityCode() {
        return skavaEntityCode;
    }

    public void setSkavaEntityCode(String skavaEntityCode) {
        this.skavaEntityCode = skavaEntityCode;
    }


}
