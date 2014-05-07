package com.metric.skava.data.dao.impl.sqllite.table;

/**
         * Created by metricboy on 3/14/14.
         */
    public class SupportRecomendationTable extends SkavaTable {

        public static final String RECOMENDATION_DATABASE_TABLE = "SupportRecomendations";

        public static final String ASSESSMENT_CODE_COLUMN = "ASSESSMENT_CODE";
        public static final String BOLT_TYPE_CODE_COLUMN = "BOLT_TYPE_CODE";
        public static final String BOLT_DIAMETER_COLUMN = "BOLT_DIAMETER";
        public static final String BOLT_LENGTH_COLUMN = "BOLT_LENGTH";
        public static final String ROOF_PATTERN_COLUMN = "ROOF_PATTERN";
        public static final String ROOF_PATTERN_TYPE_CODE_COLUMN = "ROOF_PATTERN_TYPE_CODE";
        public static final String WALL_PATTERN_COLUMN = "WALL_PATTERN";
        public static final String WALL_PATTERN_TYPE_CODE_COLUMN = "WALL_PATTERN_TYPE_CODE";
        public static final String SHOTCRETE_TYPE_CODE_COLUMN = "SHOTCRETE_TYPE_CODE";
        public static final String THICKNESS_COLUMN = "THICKNESS";
        public static final String MESH_TYPE_CODE_COLUMN = "MESH_TYPE_CODE";
        public static final String COVERAGE_CODE_COLUMN = "COVERAGE_CODE";
        public static final String ARCH_TYPE_CODE_COLUMN = "ARCH_TYPE_CODE";
        public static final String SEPARATION_COLUMN = "SEPARATION";
        public static final String OBSERVATIONS_COLUMN = "OBSERVATIONS";



        public static final String CREATE_RECOMENDATIONS_TABLE = "create table " +
                RECOMENDATION_DATABASE_TABLE + " (" + GLOBAL_KEY_ID +
                " integer primary key autoincrement, " +
                ASSESSMENT_CODE_COLUMN + " text not null, " +
                BOLT_TYPE_CODE_COLUMN + " text not null, " +
                BOLT_DIAMETER_COLUMN + " real not null, " +
                BOLT_LENGTH_COLUMN + " real not null, " +
                ROOF_PATTERN_COLUMN + " text not null, " +
                ROOF_PATTERN_TYPE_CODE_COLUMN + " text not null, " +
                WALL_PATTERN_COLUMN + " text not null, " +
                WALL_PATTERN_TYPE_CODE_COLUMN + " text not null, " +
                SHOTCRETE_TYPE_CODE_COLUMN + " text not null, " +
                THICKNESS_COLUMN + " real not null, " +
                MESH_TYPE_CODE_COLUMN + " text not null, " +
                COVERAGE_CODE_COLUMN + " text not null, " +
                ARCH_TYPE_CODE_COLUMN + " text not null, " +
                SEPARATION_COLUMN + " real not null, " +
                OBSERVATIONS_COLUMN + " text null " +
                " );";

    //    public static final String INSERT_RECOMENDATIONS_TABLE = "insert into " + RECOMENDATION_DATABASE_TABLE + "(" +
    //            ASSESSMENT_CODE_COLUMN + "," + CODE_COLUMN + "," + NAME_COLUMN + "," +
    //            BOLT_TYPE_CODE_COLUMN +  "," + BOLT_DIAMETER_COLUMN + "," + BOLT_LENGTH_COLUMN + "," +
    //            SHOTCRETE_TYPE_CODE_COLUMN + "," + THICKNESS_COLUMN + "," + MESH_TYPE_CODE_COLUMN + "," +
    //            COVERAGE_CODE_COLUMN + "," + ARCH_TYPE_CODE_COLUMN + "," + SEPARATION_COLUMN +
    //            ") values('TFC_A', 'SPT_A','Support A', 'BLT_A', 10, 10, 'SHT_A', 10, 'MSH_A', 'CVR_A', 'ARC_A', 10 )";



    //    public static final String INSERT_RECOMENDATIONS_TABLE_HINT = "insert into " + RECOMENDATION_DATABASE_TABLE + "(" + CODE_COLUMN + "," + NAME_COLUMN + "," + ASSESSMENT_CODE_COLUMN + ") values('SCT_Z','Select a section ...', 'TFC_A')";

    }
