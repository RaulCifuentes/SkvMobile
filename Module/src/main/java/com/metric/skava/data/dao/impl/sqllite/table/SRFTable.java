package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class SRFTable extends MappexIndexInstanceBaseTable {

    public static String MAPPED_INDEX_DATABASE_TABLE = "SRF";

    public static final String CREATE_SRF_TABLE = "create table " +
            MAPPED_INDEX_DATABASE_TABLE + CREATE_SCRIPT;

    public static final String INSERT_SRF_TABLE = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('SRF', 'a','A', 'Keywords A', 'Multiple occurrences of weak zones within a short section containing clay or chemically disintegrated, very loose surrounding rock (any depth), or long sectinos with incompetent (weak) rock (any depth).', 10 )";
    public static final String INSERT_SRF_TABLE_SECOND = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('SRF', 'a','B', 'Keywords B', 'Multiple shear zones within a short section in competent clay-free rock with loose surrounding rock (any depth).', 7.5 )";
    public static final String INSERT_SRF_TABLE_THIRD = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('SRF', 'a','C', 'Keywords C', 'Single weak zones within a short section in competent clay-free rock with loose surroundin rock (any depth).', 5 )";
    public static final String INSERT_SRF_TABLE_FOURTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('SRF', 'a','D', 'Keywords D', 'Loose, open joints, heavily jointed or ''sugar cube'', etc.', 5 )";
    public static final String INSERT_SRF_TABLE_FIFTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('SRF', 'a','E', 'Keywords E', 'Single weak zones with or without clay or chemical disintegrated rock (depth > 50m).', 2.5 )";


    public static final String INSERT_SRF_TABLE_SIXTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('SRF', 'b', 'F', 'Keywords F', 'Low stres, near surface, open joints.', 2.5 )";
    public static final String INSERT_SRF_TABLE_SEVENTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('SRF', 'b','G', 'Keywords G', 'Medium stress, favourable stress condition.', 1 )";
    public static final String INSERT_SRF_TABLE_EIGHTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('SRF', 'b','H', 'Keywords H', 'High stress, very tigh structure. Usually favourable to stability', 1.5 )";
    public static final String INSERT_SRF_TABLE_NINETH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('SRF', 'b','J', 'Keywords J', 'Moderate spalling and/or slabbing after > 1 hour in massive rock.', 25 )";
    public static final String INSERT_SRF_TABLE_TENTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('SRF', 'b','K', 'Keywords K', 'Spalling or rock burst after a few minutes in massive rock.', 100 )";
    public static final String INSERT_SRF_TABLE_ELEVENTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('SRF', 'b','L', 'Keywords L', 'Heavy rock burst and immediate dynamic deformation in massive rock.', 300 )";


    public static final String INSERT_SRF_TABLE_TWELFTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('SRF', 'c','M', 'Keywords M', 'Mild squeezing rock pressure', 7.5 )";
    public static final String INSERT_SRF_TABLE_THIRDTEENH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('SRF', 'c','N', 'Keywords N', 'Heavy squeezing rock pressure', 15 )";

    public static final String INSERT_SRF_TABLE_FOURTEENTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('SRF', 'd','O', 'Keywords O', 'Mild swelling rock pressure', 7.5 )";
    public static final String INSERT_SRF_TABLE_FIFTEENTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('SRF', 'd','P', 'Keywords P', 'Heavy swelling rock pressure', 12.5 )";

    public static final String DELETE_SRF_TABLE = "delete from " + MAPPED_INDEX_DATABASE_TABLE;

}
