package com.metric.skava.data.dao.impl.sqllite.table;

/**
 * Created by metricboy on 3/14/14.
 */
public class ESRTable extends MappexIndexInstanceBaseTable {

    public static String MAPPED_INDEX_DATABASE_TABLE = "ESR";

    public static final String CREATE_ESR_TABLE = "create table " +
            MAPPED_INDEX_DATABASE_TABLE + CREATE_SCRIPT;

    public static final String INSERT_ESR_TABLE = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + "values('ESR', null,'A', 'A', 'Temporary Openings', 'Temporary mine openings, etc.', 4 )";

    public static final String INSERT_ESR_TABLE_SECOND = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('ESR','i','B', 'B','Vertical Shafts', 'Vertical shafts : circular sections', 2.5 )";

    public static final String INSERT_ESR_TABLE_THIRD = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('ESR','ii','B', 'B','Vertical Shafts', 'Vertical shafts : rectangular/square section', 2 )";

    public static final String INSERT_ESR_TABLE_FOURTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('ESR', null,'C', 'C','Water Tunnels', 'Permanent mine openings, water tunnels for hydro power (exclude high pressure penstocks) water supply tunnels, pilot tunels, drifts and heading for large openings.', 1.6 )";

    public static final String INSERT_ESR_TABLE_FIFTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('ESR',null,'D', 'D','Railway Tunnels', 'Minor road and railway tunnels, surge chambers, access tunnels, sewage tunnels, etc', 1.3 )";

    public static final String INSERT_ESR_TABLE_SIXTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('ESR',null,'E','E', 'Railway Tunnels', 'Power houses, storage rooms, water treatment plants, major road and railway tunnels, civil defence chambers, portals, intersections, etc.', 1 )";

    public static final String INSERT_ESR_TABLE_SEVENTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('ESR',null,'F', 'F', 'Underground Nuclear', 'Underground nuclear power stations, railway stations, sports and public facilities, factories, etc.', 0.8 )";

    public static final String INSERT_ESR_TABLE_EIGHTH = "insert into " + MAPPED_INDEX_DATABASE_TABLE + INSERT_SCRIPT + " values('ESR',null,'G', 'G','Underground Long Lifetime', 'Very important caverns and underground openings with a long lifetime, ≈ 100 years or without access for maintenance.', 0.5 )";

    public static final String DELETE_ESR_TABLE = "delete from " + MAPPED_INDEX_DATABASE_TABLE;

}
