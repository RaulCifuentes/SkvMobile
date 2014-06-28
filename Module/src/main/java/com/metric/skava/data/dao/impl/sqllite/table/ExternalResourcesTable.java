package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class ExternalResourcesTable extends SkavaTable {

    public static final String EXTERNAL_RESOURCES_DATABASE_TABLE = "ExternalResources";

    public static final String PICTURE_RESOURCE_TYPE = "PICTURE";

    public static final String ASSESSMENT_CODE_COLUMN = "ASSESSMENT_CODE";
    public static final String RESOURCE_TAG_COLUMN = "RESOURCE_TAG";
    public static final String RESOURCE_TYPE_COLUMN = "RESOURCE_TYPE";
    public static final String RESOURCE_ORDINAL = "RESOURCE_ORDINAL";
    public static final String RESOURCE_URL_COLUMN = "RESOURCE_URL";

    public static final String CREATE_RESOURCES_TABLE = "create table " +
            EXTERNAL_RESOURCES_DATABASE_TABLE + " (" +
            ASSESSMENT_CODE_COLUMN + " text not null, " +
            RESOURCE_TYPE_COLUMN + " text not null, " +
            RESOURCE_TAG_COLUMN + " text not null, " +
            RESOURCE_ORDINAL + " integer not null, " +
            RESOURCE_URL_COLUMN + " real not null " +
            ", " +
            " PRIMARY KEY ( " + ASSESSMENT_CODE_COLUMN + ", " + RESOURCE_TYPE_COLUMN + ", "+ RESOURCE_TAG_COLUMN + ", "  + RESOURCE_ORDINAL  + " )" +
            " );";


}
