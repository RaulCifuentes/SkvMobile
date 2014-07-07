package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class AssessmentSyncTraceRecordsTable extends SkavaTable {

    public static String SYNC_TRACE_RECORDS_TABLE = "AssessmentTraceRecordsSync";

    public static final String ASSESSMENT_COLUMN = "ASSESSMENT_CODE";
    public static final String OPERATION_COLUMN = "OPERATION";
    public static final String ENTITY_CODE_COLUMN = "ENTITY_CODE";
    public static final String RECORD_ID_COLUMN = "RECORD_ID";
    public static final String DATE_COLUMN = "DATE";
    public static final String STATUS_COLUMN = "STATUS";

    public static final String CREATE_SYNC_TRACE_RECORDS_TABLE = "create table " +
            SYNC_TRACE_RECORDS_TABLE +
            " (" +
//            GLOBAL_KEY_ID + " integer primary key autoincrement, " +
            ASSESSMENT_COLUMN + " text not null, " +
            OPERATION_COLUMN + " text not null, " +
            ENTITY_CODE_COLUMN + " text not null, " +
            RECORD_ID_COLUMN + " text not null, " +
            DATE_COLUMN + " integer not null, " +
            STATUS_COLUMN  + " text not null " +
            ", " +
            " PRIMARY KEY ( " + ASSESSMENT_COLUMN + ", " + OPERATION_COLUMN + ", "+ RECORD_ID_COLUMN + " )" +
             "  );";

}
