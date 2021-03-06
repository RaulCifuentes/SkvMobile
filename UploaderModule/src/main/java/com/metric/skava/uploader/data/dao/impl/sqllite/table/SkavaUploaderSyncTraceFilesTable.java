package com.metric.skava.uploader.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 8/11/14.
 */
public class SkavaUploaderSyncTraceFilesTable extends SkavaUploaderTable {

    public static String SYNC_TRACE_FILES_TABLE = "UploaderTraceFilesSync";

    public static final String ENVIRONMENT_COLUMN = "ENVIRONMENT";
    public static final String ASSESSMENT_COLUMN = "ASSESSMENT_CODE";
    public static final String OPERATION_COLUMN = "OPERATION";
    public static final String FILE_NAME_COLUMN = "FILE_NAME";
    public static final String DATE_COLUMN = "DATE";
    public static final String STATUS_COLUMN = "STATUS";

    public static final String CREATE_SYNC_TRACE_FILES_TABLE = "create table " +
            SYNC_TRACE_FILES_TABLE +
            " (" +
//            GLOBAL_KEY_ID + " integer primary key autoincrement, " +
            ENVIRONMENT_COLUMN + " text not null, " +
            ASSESSMENT_COLUMN + " text not null, " +
            OPERATION_COLUMN + " text not null, " +
            FILE_NAME_COLUMN + " text not null, " +
            DATE_COLUMN + " integer not null, " +
            STATUS_COLUMN + " text not null " +
            ", " +
            " PRIMARY KEY ( " + ASSESSMENT_COLUMN + ", " + OPERATION_COLUMN + ", " + FILE_NAME_COLUMN + " )" +
            "  );";

}
