package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class JaTable extends MappexIndexInstanceBaseTable {

    public static String MAPPED_INDEX_DATABASE_TABLE = "Ja";

    public static final String CREATE_Ja_TABLE = "create table " +
            MAPPED_INDEX_DATABASE_TABLE + CREATE_SCRIPT;

    public static final String INSERT_Ja_TABLE = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Ja', 'a','A', 'Keywords A', 'Tightly healed, hard, non-softening, impermeable filling, i.e quartz or epidote.', 0.75 )";
    public static final String INSERT_Ja_TABLE_SECOND = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Ja', 'a','B', 'Keywords B', 'Unaltered joint walls, surface staining only.', 1 )";
    public static final String INSERT_Ja_TABLE_THIRD = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Ja', 'a','C', 'Keywords C', 'Slightly altered joint walls. Non-softening mineral coating: sandy particles, clay-free disintegrated rock, etc.', 2 )";
    public static final String INSERT_Ja_TABLE_FOURTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Ja', 'a','D', 'Keywords D', 'Silty or sandy clay coatings, small clay fraction (non-softening).', 3 )";
    public static final String INSERT_Ja_TABLE_FIFTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Ja', 'a','E', 'Keywords E', 'Softening or low friction clay mineral coatings, i.e. kaolinite or mica. Also chlorite, talc gypsum, graphite, etc. and small quantities of swelling clays.', 4 )";


    public static final String INSERT_Ja_TABLE_SIXTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('Ja', 'b', 'F', 'Keywords F', 'Sandy particles, clay-free disintegrated rock, etc.', 4 )";
    public static final String INSERT_Ja_TABLE_SEVENTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('Ja', 'b','G', 'Keywords G', 'Strongly over-consoldated, non-softening clay material fillings (continuous, but <5mm thickness).', 6 )";
    public static final String INSERT_Ja_TABLE_EIGHTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('Ja', 'b','H', 'Keywords H', 'Medium or low over-consolidation, softening, clay material fillings (continuous, but <5mm thickness).', 8 )";
    public static final String INSERT_Ja_TABLE_NINETH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('Ja', 'b','J', 'Keywords J', 'Swelling-clay filling, i.e., montmorillonite (continuous, but <5mm thickness). Value of Ja depends on percent of swelling clay-size particles.', 10 )";


    public static final String INSERT_Ja_TABLE_TENTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('Ja', 'c','K', 'Keywords K', 'Zones or bands of disintegrated or crushed rock. Strongly over-consolidated.', 6 )";
    public static final String INSERT_Ja_TABLE_ELEVENTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('Ja', 'c','L', 'Keywords L', 'Zones or bands of clay, disintegrated or crushed rock. Medium or low over-consolidation or softening fillings.', 8 )";
    public static final String INSERT_Ja_TABLE_TWELFTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('Ja', 'c','M', 'Keywords M', 'Zones or bands of clay, disintegrated or crushed rock. Swelling clay. Ja depends on percent of swelling clay-size particles.', 10 )";
    public static final String INSERT_Ja_TABLE_THIRDTEENH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('Ja', 'c','N', 'Keywords N', 'Thick continuous zones or bands of clay. Strongly over-consolidated.', 10 )";
    public static final String INSERT_Ja_TABLE_FOURTEENTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('Ja', 'c','O', 'Keywords O', 'Thick, continuous zones or bands of clay. Medium to low over-consolidation.', 13 )";
    public static final String INSERT_Ja_TABLE_FIFTEENTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('Ja', 'c','P', 'Keywords P', 'Thick, continuous zones or bands with clay. Swelling clay. Ja depends on percent of swelling clay-size particles.', 12.5 )";


    public static final String DELETE_Ja_TABLE = "delete from " + MAPPED_INDEX_DATABASE_TABLE;

}
