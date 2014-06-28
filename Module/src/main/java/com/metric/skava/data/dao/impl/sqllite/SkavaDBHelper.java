package com.metric.skava.data.dao.impl.sqllite;

/**
 * Created by metricboy on 3/12/14.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.data.dao.impl.sqllite.table.ApertureTable;
import com.metric.skava.data.dao.impl.sqllite.table.ArchTypeTable;
import com.metric.skava.data.dao.impl.sqllite.table.AssessmentTable;
import com.metric.skava.data.dao.impl.sqllite.table.BoltTypeTable;
import com.metric.skava.data.dao.impl.sqllite.table.ClientTable;
import com.metric.skava.data.dao.impl.sqllite.table.ConditionTable;
import com.metric.skava.data.dao.impl.sqllite.table.CoverageTable;
import com.metric.skava.data.dao.impl.sqllite.table.DiscontinuityFamilyTable;
import com.metric.skava.data.dao.impl.sqllite.table.DiscontinuityRelevanceTable;
import com.metric.skava.data.dao.impl.sqllite.table.DiscontinuityShapeTable;
import com.metric.skava.data.dao.impl.sqllite.table.DiscontinuityTypeTable;
import com.metric.skava.data.dao.impl.sqllite.table.DiscontinuityWaterTable;
import com.metric.skava.data.dao.impl.sqllite.table.ESRTable;
import com.metric.skava.data.dao.impl.sqllite.table.ExcavationFactorTable;
import com.metric.skava.data.dao.impl.sqllite.table.ExcavationMethodTable;
import com.metric.skava.data.dao.impl.sqllite.table.ExcavationProjectTable;
import com.metric.skava.data.dao.impl.sqllite.table.ExcavationSectionTable;
import com.metric.skava.data.dao.impl.sqllite.table.ExternalResourcesTable;
import com.metric.skava.data.dao.impl.sqllite.table.FractureTypeTable;
import com.metric.skava.data.dao.impl.sqllite.table.GroundwaterTable;
import com.metric.skava.data.dao.impl.sqllite.table.InfillingTable;
import com.metric.skava.data.dao.impl.sqllite.table.JaTable;
import com.metric.skava.data.dao.impl.sqllite.table.JnTable;
import com.metric.skava.data.dao.impl.sqllite.table.JrTable;
import com.metric.skava.data.dao.impl.sqllite.table.JwTable;
import com.metric.skava.data.dao.impl.sqllite.table.MappedIndexGroupsTable;
import com.metric.skava.data.dao.impl.sqllite.table.MappedIndexTable;
import com.metric.skava.data.dao.impl.sqllite.table.MeshTypeTable;
import com.metric.skava.data.dao.impl.sqllite.table.OrientationTable;
import com.metric.skava.data.dao.impl.sqllite.table.PermissionTable;
import com.metric.skava.data.dao.impl.sqllite.table.PersistenceTable;
import com.metric.skava.data.dao.impl.sqllite.table.QCalculationTable;
import com.metric.skava.data.dao.impl.sqllite.table.RMRCalculationTable;
import com.metric.skava.data.dao.impl.sqllite.table.RockQualityTable;
import com.metric.skava.data.dao.impl.sqllite.table.RoleTable;
import com.metric.skava.data.dao.impl.sqllite.table.RoughnessTable;
import com.metric.skava.data.dao.impl.sqllite.table.SRFTable;
import com.metric.skava.data.dao.impl.sqllite.table.ShotcreteTypeTable;
import com.metric.skava.data.dao.impl.sqllite.table.SpacingTable;
import com.metric.skava.data.dao.impl.sqllite.table.StrengthTable;
import com.metric.skava.data.dao.impl.sqllite.table.SupportPatternTypeTable;
import com.metric.skava.data.dao.impl.sqllite.table.SupportRecommendationTable;
import com.metric.skava.data.dao.impl.sqllite.table.SupportRequirementTable;
import com.metric.skava.data.dao.impl.sqllite.table.SyncLoggingTable;
import com.metric.skava.data.dao.impl.sqllite.table.TunnelFaceTable;
import com.metric.skava.data.dao.impl.sqllite.table.TunnelTable;
import com.metric.skava.data.dao.impl.sqllite.table.UserRolesTable;
import com.metric.skava.data.dao.impl.sqllite.table.UserTable;
import com.metric.skava.data.dao.impl.sqllite.table.WeatheringTable;

/**
 * Listing 8-2: Implementing an SQLite Open Helper
 */
public class SkavaDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "mySkavaDatabase.db";

    public static final int DATABASE_VERSION = 52;

    public SkavaDBHelper(Context context, String name,
                         SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        Log.d(SkavaConstants.LOG, "Database SkavaDBHelper created succesfully ");

    }


    // Called when no database exists in disk and the helper class needs
    // to create a new one.
    @Override
    public void onCreate(SQLiteDatabase db) {

        // ******************** SyncLogging ********************
        //
        db.execSQL(SyncLoggingTable.CREATE_SYNC_LOGGING_TABLE);

        // ******************** Clients ********************
        db.execSQL(ClientTable.CREATE_CLIENTS_TABLE);

        // ******************** User & Roles ********************
        db.execSQL(UserTable.CREATE_USERS_TABLE);

        db.execSQL(RoleTable.CREATE_ROLES_TABLE);

        db.execSQL(UserRolesTable.CREATE_USER_ROLES_TABLE);

        // ******************** Identification ********************

        db.execSQL(ExcavationProjectTable.CREATE_PROJECTS_TABLE);

        db.execSQL(TunnelTable.CREATE_TUNNELS_TABLE);

        db.execSQL(ExcavationFactorTable.CREATE_FACTOR_TABLE);

        db.execSQL(TunnelFaceTable.CREATE_FACES_TABLE);

        db.execSQL(PermissionTable.CREATE_PERMISSIONS_TABLE);

        db.execSQL(ExcavationSectionTable.CREATE_SECTIONS_TABLE);

        db.execSQL(ExcavationMethodTable.CREATE_METHODS_TABLE);

        // ******************** Support ********************
        db.execSQL(ArchTypeTable.CREATE_ARCHS_TABLE);

        db.execSQL(BoltTypeTable.CREATE_BOLTS_TABLE);

        db.execSQL(CoverageTable.CREATE_COVERAGES_TABLE);

        db.execSQL(MeshTypeTable.CREATE_MESHES_TABLE);

        db.execSQL(ShotcreteTypeTable.CREATE_SHOTCRETES_TABLE);

        db.execSQL(SupportPatternTypeTable.CREATE_PATTERN_TYPE_TABLE);

        db.execSQL(SupportRequirementTable.CREATE_SUPPORTS_REQUIREMENT_TABLE);

        db.execSQL(SupportRecommendationTable.CREATE_RECOMENDATIONS_TABLE);

        // ******************** Discontinuities ********************
        db.execSQL(DiscontinuityTypeTable.CREATE_DISCONTINUITIES_TABLE);

        db.execSQL(DiscontinuityRelevanceTable.CREATE_RELEVANCES_TABLE);

        db.execSQL(DiscontinuityWaterTable.CREATE_WATERS_TABLE);

        db.execSQL(DiscontinuityShapeTable.CREATE_SHAPES_TABLE);

        db.execSQL(DiscontinuityFamilyTable.CREATE_DISCONTINUITY_FAMILIES_TABLE);

        // ******************** Rock Mass ********************
        db.execSQL(FractureTypeTable.CREATE_FRACTURES_TABLE);

        // ******************** MappedIndex Base Tables ********************

        db.execSQL(MappedIndexTable.CREATE_INDEXES_TABLE);

        db.execSQL(MappedIndexGroupsTable.CREATE_GROUP_TABLE);

        db.execSQL(ESRTable.CREATE_ESR_TABLE);

        db.execSQL(OrientationTable.CREATE_ORIENTATION_TABLE);

        db.execSQL(ApertureTable.CREATE_APERTURE_TABLE);

        db.execSQL(PersistenceTable.CREATE_PERSISTENCE_TABLE);

        db.execSQL(RoughnessTable.CREATE_ROUGHNESS_TABLE);

        db.execSQL(InfillingTable.CREATE_INFILLING_TABLE);

        db.execSQL(WeatheringTable.CREATE_WEATHERING_TABLE);

        db.execSQL(GroundwaterTable.CREATE_GROUNDWATER_TABLE);

        db.execSQL(StrengthTable.CREATE_STRENGTH_TABLE);

        db.execSQL(SpacingTable.CREATE_SPACING_TABLE);

        db.execSQL(SRFTable.CREATE_SRF_TABLE);

        db.execSQL(JwTable.CREATE_Jw_TABLE);

        db.execSQL(JaTable.CREATE_Ja_TABLE);

        db.execSQL(JrTable.CREATE_Jr_TABLE);

        db.execSQL(JnTable.CREATE_Jn_TABLE);

        db.execSQL(RockQualityTable.CREATE_ROCK_QUALITIES_TABLE);

        // ***************** Q Calculations *****************
        db.execSQL(QCalculationTable.CREATE_QCALCULATION_TABLE);

        // ***************** RMR Calculations *****************
        db.execSQL(RMRCalculationTable.CREATE_RMRCALCULATION_TABLE);

        // ******************** Assessment ********************

        db.execSQL(AssessmentTable.CREATE_ASSESSMENT_TABLE);

        // ******************** Pictures ********************

        db.execSQL(ExternalResourcesTable.CREATE_RESOURCES_TABLE);


    }


    // Called when there is a database version mismatch meaning that
    // the version of the database on disk needs to be upgraded to
    // the current version.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        // Log the version upgrade.
        Log.w("TaskDBAdapter", "Upgrading from version " +
                oldVersion + " to " +
                newVersion + ", which will destroy all old data");

        // Upgrade the existing database to conform to the new
        // version. Multiple previous versions can be handled by
        // comparing oldVersion and newVersion values.

        // The simplest case is to drop the old table and create a new one.

        // ******************** Clients ********************
        db.execSQL("DROP TABLE IF EXISTS " + SyncLoggingTable.SYNC_LOGGING_TABLE);

        // ******************** Clients ********************
        db.execSQL("DROP TABLE IF EXISTS " + ClientTable.CLIENT_DATABASE_TABLE);

        // ******************** Users & Roles ********************
        db.execSQL("DROP TABLE IF EXISTS " + UserTable.USER_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + RoleTable.ROLE_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + UserRolesTable.USER_ROLES_DATABASE_TABLE);

        // ******************** Identification ********************
        db.execSQL("DROP TABLE IF EXISTS " + ExcavationProjectTable.PROJECT_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TunnelTable.TUNNEL_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ExcavationFactorTable.FACTOR_DATABASE_TABLE );

        db.execSQL("DROP TABLE IF EXISTS " + TunnelFaceTable.FACE_DATABASE_TABLE);

        db.execSQL("DROP TABLE IF EXISTS " + PermissionTable.PERMISSION_DATABASE_TABLE);

        db.execSQL("DROP TABLE IF EXISTS " + ExcavationMethodTable.METHOD_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ExcavationSectionTable.SECTION_DATABASE_TABLE);

        // ******************** Support ********************
        db.execSQL("DROP TABLE IF EXISTS " + ArchTypeTable.ARCH_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + BoltTypeTable.BOLT_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CoverageTable.COVERAGE_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MeshTypeTable.MESH_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ShotcreteTypeTable.SHOTCRETE_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SupportPatternTypeTable.PATTERN_TYPE_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SupportRequirementTable.SUPPORT_REQUIREMENT_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SupportRecommendationTable.RECOMENDATION_DATABASE_TABLE);

        // ******************** Discontinuities ********************
        db.execSQL("DROP TABLE IF EXISTS " + DiscontinuityTypeTable.DISCONTINUITY_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DiscontinuityRelevanceTable.RELEVANCES_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DiscontinuityWaterTable.WATER_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DiscontinuityShapeTable.SHAPE_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DiscontinuityFamilyTable.DISCONTINUITY_FAMILY_DATABASE_TABLE);

        // ******************** Rock Mass ********************
        db.execSQL("DROP TABLE IF EXISTS " + FractureTypeTable.FRACTURE_DATABASE_TABLE);

        // ******************** MappedIndex Base Tables ********************
        db.execSQL("DROP TABLE IF EXISTS " + MappedIndexTable.INDEX_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MappedIndexGroupsTable.GROUPS_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ESRTable.MAPPED_INDEX_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + OrientationTable.MAPPED_INDEX_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ApertureTable.MAPPED_INDEX_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PersistenceTable.MAPPED_INDEX_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + RoughnessTable.MAPPED_INDEX_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + InfillingTable.MAPPED_INDEX_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + WeatheringTable.MAPPED_INDEX_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + GroundwaterTable.MAPPED_INDEX_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + StrengthTable.MAPPED_INDEX_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ConditionTable.MAPPED_INDEX_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SpacingTable.MAPPED_INDEX_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SRFTable.MAPPED_INDEX_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + JwTable.MAPPED_INDEX_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + JaTable.MAPPED_INDEX_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + JrTable.MAPPED_INDEX_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + JnTable.MAPPED_INDEX_DATABASE_TABLE);

        // ***************** Q/RMR Calculations *****************
        db.execSQL("DROP TABLE IF EXISTS " + RockQualityTable.ROCK_QUALITY_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + QCalculationTable.Q_CALCULATION_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + RMRCalculationTable.RMR_CALCULATION_DATABASE_TABLE);


        // ******************** Assessment ********************
//        db.execSQL("DROP TABLE IF EXISTS " + InternalCodeTable.INTERNAL_CODE_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + AssessmentTable.ASSESSMENT_DATABASE_TABLE);

        // ******************** Pictures ********************
        db.execSQL("DROP TABLE IF EXISTS " + ExternalResourcesTable.EXTERNAL_RESOURCES_DATABASE_TABLE);

        //Just to clear garbage
        db.execSQL("DROP TABLE IF EXISTS " + "SUPPORTS");

        // Create a new one.
        onCreate(db);
    }






}