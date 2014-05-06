package com.metric.skava.data.dao.impl.sqllite;

/**
 * Created by metricboy on 3/12/14.
 */

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.data.dao.exception.DAOException;
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
import com.metric.skava.data.dao.impl.sqllite.table.FractureTypeTable;
import com.metric.skava.data.dao.impl.sqllite.table.GroundwaterTable;
import com.metric.skava.data.dao.impl.sqllite.table.InfillingTable;
import com.metric.skava.data.dao.impl.sqllite.table.InternalCodeTable;
import com.metric.skava.data.dao.impl.sqllite.table.JaTable;
import com.metric.skava.data.dao.impl.sqllite.table.JnTable;
import com.metric.skava.data.dao.impl.sqllite.table.JrTable;
import com.metric.skava.data.dao.impl.sqllite.table.JwTable;
import com.metric.skava.data.dao.impl.sqllite.table.MappedIndexTable;
import com.metric.skava.data.dao.impl.sqllite.table.MappexIndexGroupsTable;
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
import com.metric.skava.data.dao.impl.sqllite.table.SupportRecomendationTable;
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

    public static final int DATABASE_VERSION = 27;

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

        db.execSQL(SupportRequirementTable.CREATE_SUPPORTS_TABLE);

        // ******************** Discontinuities ********************
        db.execSQL(DiscontinuityTypeTable.CREATE_DISCONTINUITIES_TABLE);

        db.execSQL(DiscontinuityRelevanceTable.CREATE_RELEVANCES_TABLE);

        db.execSQL(DiscontinuityWaterTable.CREATE_WATERS_TABLE);

        db.execSQL(DiscontinuityShapeTable.CREATE_SHAPES_TABLE);

        // ******************** Rock Mass ********************
        db.execSQL(FractureTypeTable.CREATE_FRACTURES_TABLE);

        // ******************** MappedIndex Base Tables ********************

        db.execSQL(MappedIndexTable.CREATE_INDEXES_TABLE);

        db.execSQL(MappexIndexGroupsTable.CREATE_GROUP_TABLE);

        db.execSQL(ESRTable.CREATE_ESR_TABLE);

        db.execSQL(OrientationTable.CREATE_ORIENTATION_TABLE);

        db.execSQL(ApertureTable.CREATE_APERTURE_TABLE);

        db.execSQL(PersistenceTable.CREATE_PERSISTENCE_TABLE);

        db.execSQL(RoughnessTable.CREATE_ROUGHNESS_TABLE);

        db.execSQL(InfillingTable.CREATE_INFILLING_TABLE);

        db.execSQL(WeatheringTable.CREATE_WEATHERING_TABLE);

        db.execSQL(GroundwaterTable.CREATE_GROUNDWATER_TABLE);

        db.execSQL(StrengthTable.CREATE_STRENGTH_TABLE);

        db.execSQL(ConditionTable.CREATE_CONDITION_TABLE);

        db.execSQL(SpacingTable.CREATE_SPACING_TABLE);

        db.execSQL(SRFTable.CREATE_SRF_TABLE);

        db.execSQL(JwTable.CREATE_Jw_TABLE);

        db.execSQL(JaTable.CREATE_Ja_TABLE);

        db.execSQL(JrTable.CREATE_Jr_TABLE);

        db.execSQL(JnTable.CREATE_Jn_TABLE);

        db.execSQL(RockQualityTable.CREATE_ROCK_QUALITIES_TABLE);
        // ***************** Q Calculations *****************
        db.execSQL(QCalculationTable.CREATE_QCALCULATION_TABLE);
        db.execSQL(RMRCalculationTable.CREATE_RMRCALCULATION_TABLE);
        db.execSQL(DiscontinuityFamilyTable.CREATE_DISCONTINUITY_FAMILIES_TABLE);
        db.execSQL(SupportRecomendationTable.CREATE_RECOMENDATIONS_TABLE);

        // ******************** Assessment ********************

        db.execSQL(AssessmentTable.CREATE_ASSESSMENT_TABLE);


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
        db.execSQL("DROP TABLE IF EXISTS " + SupportRequirementTable.SUPPORT_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SupportRecomendationTable.RECOMENDATION_DATABASE_TABLE);

        // ******************** Discontinuities ********************
        db.execSQL("DROP TABLE IF EXISTS " + DiscontinuityTypeTable.DISCONTINUITY_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DiscontinuityRelevanceTable.RELEVANCES_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DiscontinuityWaterTable.WATER_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DiscontinuityShapeTable.SHAPE_DATABASE_TABLE);

        // ******************** Rock Mass ********************
        db.execSQL("DROP TABLE IF EXISTS " + FractureTypeTable.FRACTURE_DATABASE_TABLE);

        // ******************** MappedIndex Base Tables ********************
        db.execSQL("DROP TABLE IF EXISTS " + MappedIndexTable.INDEX_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MappexIndexGroupsTable.GROUPS_DATABASE_TABLE);
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
        db.execSQL("DROP TABLE IF EXISTS " + DiscontinuityFamilyTable.DISCONTINUITY_FAMILY_DATABASE_TABLE);

        // ******************** Assessment ********************
        db.execSQL("DROP TABLE IF EXISTS " + InternalCodeTable.INTERNAL_CODE_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + AssessmentTable.ASSESSMENT_DATABASE_TABLE);

        // Create a new one.
        onCreate(db);
    }


    public static void insertDefaultData(SQLiteDatabase db) throws DAOException {

        // ******************** Clients ********************
        clearClients(db);
        insertClients(db);
        // ******************** User & Roles ********************
        clearUsers(db);
        insertUser(db);

        clearRoles(db);
        insertRoles(db);

        clearUserRoles(db);
        insertUserRoles(db);
        // ******************** Identification ********************
        clearProjects(db);
        insertProjects(db);

        clearTunnels(db);
        insertTunnels(db);

        clearExcavationFactors(db);
        insertExcavationFactors(db);

        clearFaces(db);
        insertFaces(db);

        clearPermissions(db);
        insertPermissions(db);

        clearSections(db);
        insertSections(db);

        clearMethods(db);
        insertMethods(db);
        // ******************** Support ********************
        clearArchs(db);
        insertArchs(db);

        clearBoltTypes(db);
        insertBoltTypes(db);

        clearCoverages(db);
        insertCoverages(db);

        clearMeshTypes(db);
        insertMeshTypes(db);

        clearShotcreteTypes(db);
        insertShotcreteTypes(db);

        clearSupportRequirements(db);
        insertSupportRequirements(db);
        // ******************** Discontinuities ********************
        clearDiscontinuityTypes(db);
        insertDiscontinuityTypes(db);

        clearRelevances(db);
        insertRelevances(db);

        clearWater(db);
        insertWaters(db);

        clearShapes(db);
        insertShapes(db);
        // ******************** Rock Mass Description **************
        clearFractureTypes(db);
        insertFractureTypes(db);
        // ******************** MappedIndex Base Tables ********************
        clearIndexes(db);
        insertIndexes(db);

        clearIndexGroups(db);
        insertIndexGroups(db);

        clearESR(db);
        insertESR(db);

        clearOrientations(db);
        insertOrientations(db);

        clearApertures(db);
        insertApertures(db);

        clearPersistences(db);
        insertPersistences(db);

        clearRouhgness(db);
        insertRoughness(db);

        clearInfillings(db);
        insertInfillings(db);

        clearWeatherings(db);
        insertWeatherings(db);

        clearGroundwaters(db);
        insertGroundwaters(db);

        clearStrengths(db);
        insertStrengths(db);

        clearConditions(db);
        insertConditions(db);

        clearSpacings(db);
        insertSpacings(db);

        clearSRFs(db);
        insertSRFs(db);

        clearJw(db);
        insertJw(db);

        clearJa(db);
        insertJa(db);

        clearJr(db);
        insertJr(db);

        clearJn(db);
        insertJn(db);


    }

    private static void insertExcavationFactors(SQLiteDatabase db) throws DAOException {
        try {
            db.execSQL(ExcavationFactorTable.INSERT_FACTOR_TABLE);
            db.execSQL(ExcavationFactorTable.INSERT_FACTOR_TABLE_SECOND);
            db.execSQL(ExcavationFactorTable.INSERT_FACTOR_TABLE_THIRD);
            db.execSQL(ExcavationFactorTable.INSERT_FACTOR_TABLE_FOURTH);
            db.execSQL(ExcavationFactorTable.INSERT_FACTOR_TABLE_FIFTH);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private static void clearExcavationFactors(SQLiteDatabase db) {
        db.execSQL(ExcavationFactorTable.DELETE_FACTOR_TABLE);
    }

    private static void insertPermissions(SQLiteDatabase db) throws DAOException {
        try {
            db.execSQL(PermissionTable.INSERT_PERMISSION_TABLE);
            db.execSQL(PermissionTable.INSERT_PERMISSION_TABLE_SECOND);
            db.execSQL(PermissionTable.INSERT_PERMISSION_TABLE_THIRD);
            db.execSQL(PermissionTable.INSERT_PERMISSION_TABLE_FOURTH);
            db.execSQL(PermissionTable.INSERT_PERMISSION_TABLE_FIFTH);
            db.execSQL(PermissionTable.INSERT_PERMISSION_TABLE_SIXTH);
            db.execSQL(PermissionTable.INSERT_PERMISSION_TABLE_SEVENTH);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private static void clearPermissions(SQLiteDatabase db) throws DAOException {
        try {
            db.execSQL(PermissionTable.DELETE_PERMISSION_TABLE);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private static void insertUserRoles(SQLiteDatabase db) throws DAOException {
        try {
            db.execSQL(UserRolesTable.INSERT_USER_ROLES_TABLE);
            db.execSQL(UserRolesTable.INSERT_USER_ROLES_TABLE_SECOND);
            db.execSQL(UserRolesTable.INSERT_USER_ROLES_TABLE_THIRD);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private static void clearUserRoles(SQLiteDatabase db) throws DAOException {
        try {
            db.execSQL(UserRolesTable.DELETE_USER_ROLES_TABLE);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
    private static void insertSupportRequirements(SQLiteDatabase db) throws DAOException {
        try {
            db.execSQL(SupportRequirementTable.INSERT_SUPPORTS_TABLE);
            db.execSQL(SupportRequirementTable.INSERT_SUPPORTS_TABLE_SECOND);
            db.execSQL(SupportRequirementTable.INSERT_SUPPORTS_TABLE_THIRD);
            db.execSQL(SupportRequirementTable.INSERT_SUPPORTS_TABLE_FOURTH);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private static void clearShotcreteTypes(SQLiteDatabase db) throws DAOException {
        try {
            db.execSQL(ShotcreteTypeTable.DELETE_SHOTCRETES_TABLE);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private static void clearSupportRequirements(SQLiteDatabase db) throws DAOException {
        try {
            db.execSQL(SupportRequirementTable.DELETE_SUPPORTS_TABLE);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private static void clearMeshTypes(SQLiteDatabase db) throws DAOException {
        try {
            db.execSQL(MeshTypeTable.DELETE_MESHES_TABLE);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private static void clearCoverages(SQLiteDatabase db) throws DAOException {
        try {
            db.execSQL(CoverageTable.DELETE_COVERAGES_TABLE);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private static void clearBoltTypes(SQLiteDatabase db) throws DAOException {
        try {
            db.execSQL(BoltTypeTable.DELETE_BOLTS_TABLE);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private static void clearIndexGroups(SQLiteDatabase db) throws DAOException {
        try {
            db.execSQL(MappexIndexGroupsTable.DELETE_GROUPS_TABLE);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private static void clearIndexes(SQLiteDatabase db) throws DAOException {
        try {
            db.execSQL(DiscontinuityShapeTable.DELETE_SHAPES_TABLE);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private static void clearShapes(SQLiteDatabase db) throws DAOException {
        try {
            db.execSQL(DiscontinuityShapeTable.DELETE_SHAPES_TABLE);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private static void clearWater(SQLiteDatabase db) throws DAOException {
        try {
            db.execSQL(DiscontinuityWaterTable.DELETE_WATERS_TABLE);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private static void clearRelevances(SQLiteDatabase db) throws DAOException {
        try {
            db.execSQL(DiscontinuityRelevanceTable.DELETE_RELEVANCES_TABLE);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private static void clearFractureTypes(SQLiteDatabase db) throws DAOException {
        try {
            db.execSQL(FractureTypeTable.DELETE_FRACTURES_TABLE);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private static void clearDiscontinuityTypes(SQLiteDatabase db) throws DAOException {
        try {
            db.execSQL(DiscontinuityTypeTable.DELETE_DISCONTINUITIES_TABLE);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private static void clearESR(SQLiteDatabase db) throws DAOException {
        try {
            db.execSQL(ESRTable.DELETE_ESR_TABLE);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private static void clearOrientations(SQLiteDatabase db) throws DAOException {
        try {
            db.execSQL(OrientationTable.DELETE_ORIENTATION_TABLE);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private static void clearApertures(SQLiteDatabase db) throws DAOException {
        try {
            db.execSQL(ApertureTable.DELETE_APERTURE_TABLE);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private static void clearArchs(SQLiteDatabase db) throws DAOException {
        try {
            db.execSQL(ArchTypeTable.DELETE_ARCHS_TABLE);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private static void clearMethods(SQLiteDatabase db) throws DAOException {
        try {
            db.execSQL(ExcavationMethodTable.DELETE_METHODS_TABLE);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private static void clearSections(SQLiteDatabase db) throws DAOException {
        try {
            db.execSQL(ExcavationSectionTable.DELETE_SECTIONS_TABLE);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private static void clearFaces(SQLiteDatabase db) throws DAOException {
        try {
            db.execSQL(TunnelFaceTable.DELETE_FACES_TABLE);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private static void clearTunnels(SQLiteDatabase db) throws DAOException {
        try {
            db.execSQL(TunnelTable.DELETE_TUNNELS_TABLE);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }



    private static void clearProjects(SQLiteDatabase db) throws DAOException {
        try {
            db.execSQL(ExcavationProjectTable.DELETE_PROJECTS_TABLE);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private static void clearRoles(SQLiteDatabase db) throws DAOException {
        try {
            db.execSQL(RoleTable.DELETE_ROLES_TABLE);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private static void clearUsers(SQLiteDatabase db) throws DAOException {
        try {
            db.execSQL(UserTable.DELETE_USERS_TABLE);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private static void clearPersistences(SQLiteDatabase db) throws DAOException {
        try {
            db.execSQL(PersistenceTable.DELETE_PERSISTENCE_TABLE);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private static void clearRouhgness(SQLiteDatabase db) throws DAOException {
        try {
            db.execSQL(RoughnessTable.DELETE_ROUGHNESS_TABLE);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private static void clearInfillings(SQLiteDatabase db) throws DAOException {
        try {
            db.execSQL(InfillingTable.DELETE_INFILLING_TABLE);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private static void clearWeatherings(SQLiteDatabase db) throws DAOException {
        try {
            db.execSQL(WeatheringTable.DELETE_WEATHERING_TABLE);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private static void clearGroundwaters(SQLiteDatabase db) throws DAOException {
        try {
            db.execSQL(GroundwaterTable.DELETE_GROUNDWATER_TABLE);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private static void clearStrengths(SQLiteDatabase db) throws DAOException {
        try {
            db.execSQL(StrengthTable.DELETE_STRENGTH_TABLE);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private static void clearConditions(SQLiteDatabase db) throws DAOException {
        try {
            db.execSQL(JwTable.DELETE_Jw_TABLE);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private static void clearSpacings(SQLiteDatabase db) {
        db.execSQL(JwTable.DELETE_Jw_TABLE);
    }

    private static void clearSRFs(SQLiteDatabase db) {
        db.execSQL(JwTable.DELETE_Jw_TABLE);
    }

    private static void clearJw(SQLiteDatabase db) {
        db.execSQL(JwTable.DELETE_Jw_TABLE);
    }

    private static void clearJa(SQLiteDatabase db) {
        db.execSQL(JaTable.DELETE_Ja_TABLE);
    }

    private static void clearJr(SQLiteDatabase db) {
        db.execSQL(JrTable.DELETE_Jr_TABLE);
    }


    private static void clearJn(SQLiteDatabase db) {
        db.execSQL(JnTable.DELETE_Jn_TABLE);
    }

    private static void clearClients(SQLiteDatabase db) {
        db.execSQL(ClientTable.DELETE_CLIENTS_TABLE);
    }

    private static void insertJn(SQLiteDatabase db) {
        db.execSQL(JnTable.INSERT_Jn_TABLE);
        db.execSQL(JnTable.INSERT_Jn_TABLE_SECOND);
        db.execSQL(JnTable.INSERT_Jn_TABLE_THIRD);
        db.execSQL(JnTable.INSERT_Jn_TABLE_FOURTH);
        db.execSQL(JnTable.INSERT_Jn_TABLE_FIFTH);
        db.execSQL(JnTable.INSERT_Jn_TABLE_SIXTH);
        db.execSQL(JnTable.INSERT_Jn_TABLE_SEVENTH);
        db.execSQL(JnTable.INSERT_Jn_TABLE_EIGHTH);
        db.execSQL(JnTable.INSERT_Jn_TABLE_NINETH);
    }

    private static void insertJr(SQLiteDatabase db) {
        db.execSQL(JrTable.INSERT_Jr_TABLE);
        db.execSQL(JrTable.INSERT_Jr_TABLE_SECOND);
        db.execSQL(JrTable.INSERT_Jr_TABLE_THIRD);
        db.execSQL(JrTable.INSERT_Jr_TABLE_FOURTH);
        db.execSQL(JrTable.INSERT_Jr_TABLE_FIFTH);
        db.execSQL(JrTable.INSERT_Jr_TABLE_SIXTH);
        db.execSQL(JrTable.INSERT_Jr_TABLE_SEVENTH);
        db.execSQL(JrTable.INSERT_Jr_TABLE_EIGHTH);
        db.execSQL(JrTable.INSERT_Jr_TABLE_NINETH);
        db.execSQL(JrTable.INSERT_Jr_TABLE_TENTH);
        db.execSQL(JrTable.INSERT_Jr_TABLE_ELEVENTH);
        db.execSQL(JrTable.INSERT_Jr_TABLE_TWELFTH);
        db.execSQL(JrTable.INSERT_Jr_TABLE_THIRDTEENH);
        db.execSQL(JrTable.INSERT_Jr_TABLE_FOURTEENTH);
        db.execSQL(JrTable.INSERT_Jr_TABLE_FIFTEENTH);
    }

    private static void insertJa(SQLiteDatabase db) {
        db.execSQL(JaTable.INSERT_Ja_TABLE);
        db.execSQL(JaTable.INSERT_Ja_TABLE_SECOND);
        db.execSQL(JaTable.INSERT_Ja_TABLE_THIRD);
        db.execSQL(JaTable.INSERT_Ja_TABLE_FOURTH);
        db.execSQL(JaTable.INSERT_Ja_TABLE_FIFTH);
        db.execSQL(JaTable.INSERT_Ja_TABLE_SIXTH);
        db.execSQL(JaTable.INSERT_Ja_TABLE_SEVENTH);
        db.execSQL(JaTable.INSERT_Ja_TABLE_EIGHTH);
        db.execSQL(JaTable.INSERT_Ja_TABLE_NINETH);
        db.execSQL(JaTable.INSERT_Ja_TABLE_TENTH);
        db.execSQL(JaTable.INSERT_Ja_TABLE_ELEVENTH);
        db.execSQL(JaTable.INSERT_Ja_TABLE_TWELFTH);
        db.execSQL(JaTable.INSERT_Ja_TABLE_THIRDTEENH);
        db.execSQL(JaTable.INSERT_Ja_TABLE_FOURTEENTH);
        db.execSQL(JaTable.INSERT_Ja_TABLE_FIFTEENTH);
    }

    private static void insertJw(SQLiteDatabase db) {
        db.execSQL(JwTable.INSERT_Jw_TABLE);
        db.execSQL(JwTable.INSERT_Jw_TABLE_SECOND);
        db.execSQL(JwTable.INSERT_Jw_TABLE_THIRD);
        db.execSQL(JwTable.INSERT_Jw_TABLE_FOURTH);
        db.execSQL(JwTable.INSERT_Jw_TABLE_FIFTH);
        db.execSQL(JwTable.INSERT_Jw_TABLE_SIXTH);
    }

    private static void insertSRFs(SQLiteDatabase db) {
        db.execSQL(SRFTable.INSERT_SRF_TABLE);
        db.execSQL(SRFTable.INSERT_SRF_TABLE_SECOND);
        db.execSQL(SRFTable.INSERT_SRF_TABLE_THIRD);
        db.execSQL(SRFTable.INSERT_SRF_TABLE_FOURTH);
        db.execSQL(SRFTable.INSERT_SRF_TABLE_FIFTH);
        db.execSQL(SRFTable.INSERT_SRF_TABLE_SIXTH);
        db.execSQL(SRFTable.INSERT_SRF_TABLE_SEVENTH);
        db.execSQL(SRFTable.INSERT_SRF_TABLE_EIGHTH);
        db.execSQL(SRFTable.INSERT_SRF_TABLE_NINETH);
        db.execSQL(SRFTable.INSERT_SRF_TABLE_TENTH);
        db.execSQL(SRFTable.INSERT_SRF_TABLE_ELEVENTH);
        db.execSQL(SRFTable.INSERT_SRF_TABLE_TWELFTH);
        db.execSQL(SRFTable.INSERT_SRF_TABLE_THIRDTEENH);
        db.execSQL(SRFTable.INSERT_SRF_TABLE_FOURTEENTH);
        db.execSQL(SRFTable.INSERT_SRF_TABLE_FIFTEENTH);
    }

    private static void insertSpacings(SQLiteDatabase db) {
        db.execSQL(SpacingTable.INSERT_SPACING_TABLE);
        db.execSQL(SpacingTable.INSERT_SPACING_TABLE_SECOND);
        db.execSQL(SpacingTable.INSERT_SPACING_TABLE_THIRD);
        db.execSQL(SpacingTable.INSERT_SPACING_TABLE_FOURTH);
        db.execSQL(SpacingTable.INSERT_SPACING_TABLE_FIFTH);
    }

    private static void insertConditions(SQLiteDatabase db) {
        db.execSQL(ConditionTable.INSERT_CONDITION_TABLE);
        db.execSQL(ConditionTable.INSERT_CONDITION_TABLE_SECOND);
        db.execSQL(ConditionTable.INSERT_CONDITION_TABLE_THIRD);
        db.execSQL(ConditionTable.INSERT_CONDITION_TABLE_FOURTH);
        db.execSQL(ConditionTable.INSERT_CONDITION_TABLE_FIFTH);
    }

    private static void insertStrengths(SQLiteDatabase db) {
        db.execSQL(StrengthTable.INSERT_STRENGTH_TABLE);
        db.execSQL(StrengthTable.INSERT_STRENGTH_TABLE_SECOND);
        db.execSQL(StrengthTable.INSERT_STRENGTH_TABLE_THIRD);
        db.execSQL(StrengthTable.INSERT_STRENGTH_TABLE_FOURTH);
        db.execSQL(StrengthTable.INSERT_STRENGTH_TABLE_FIFTH);
        db.execSQL(StrengthTable.INSERT_STRENGTH_TABLE_SIXTH);
        db.execSQL(StrengthTable.INSERT_STRENGTH_TABLE_SEVENTH);
        db.execSQL(StrengthTable.INSERT_STRENGTH_TABLE_EIGHTH);
        db.execSQL(StrengthTable.INSERT_STRENGTH_TABLE_NINETH);
        db.execSQL(StrengthTable.INSERT_STRENGTH_TABLE_TENTH);
        db.execSQL(StrengthTable.INSERT_STRENGTH_TABLE_ELEVENTH);
    }

    private static void insertGroundwaters(SQLiteDatabase db) {
        db.execSQL(GroundwaterTable.INSERT_GROUNDWATER_TABLE);
        db.execSQL(GroundwaterTable.INSERT_GROUNDWATER_TABLE_SECOND);
        db.execSQL(GroundwaterTable.INSERT_GROUNDWATER_TABLE_THIRD);
        db.execSQL(GroundwaterTable.INSERT_GROUNDWATER_TABLE_FOURTH);
        db.execSQL(GroundwaterTable.INSERT_GROUNDWATER_TABLE_FIFTH);
        db.execSQL(GroundwaterTable.INSERT_GROUNDWATER_TABLE_SIXTH);
        db.execSQL(GroundwaterTable.INSERT_GROUNDWATER_TABLE_SEVENTH);
        db.execSQL(GroundwaterTable.INSERT_GROUNDWATER_TABLE_EIGHTH);
        db.execSQL(GroundwaterTable.INSERT_GROUNDWATER_TABLE_NINETH);
        db.execSQL(GroundwaterTable.INSERT_GROUNDWATER_TABLE_TENTH);
        db.execSQL(GroundwaterTable.INSERT_GROUNDWATER_TABLE_ELEVENTH);
        db.execSQL(GroundwaterTable.INSERT_GROUNDWATER_TABLE_TWELFTH);
        db.execSQL(GroundwaterTable.INSERT_GROUNDWATER_TABLE_THIRDTEENH);
        db.execSQL(GroundwaterTable.INSERT_GROUNDWATER_TABLE_FOURTEENTH);
        db.execSQL(GroundwaterTable.INSERT_GROUNDWATER_TABLE_FIFTEENTH);
    }

    private static void insertWeatherings(SQLiteDatabase db) {
        db.execSQL(WeatheringTable.INSERT_WEATHERING_TABLE);
        db.execSQL(WeatheringTable.INSERT_WEATHERING_TABLE_SECOND);
        db.execSQL(WeatheringTable.INSERT_WEATHERING_TABLE_THIRD);
        db.execSQL(WeatheringTable.INSERT_WEATHERING_TABLE_FOURTH);
        db.execSQL(WeatheringTable.INSERT_WEATHERING_TABLE_FIFTH);
    }

    private static void insertInfillings(SQLiteDatabase db) {
        db.execSQL(InfillingTable.INSERT_INFILLING_TABLE);
        db.execSQL(InfillingTable.INSERT_INFILLING_TABLE_SECOND);
        db.execSQL(InfillingTable.INSERT_INFILLING_TABLE_THIRD);
        db.execSQL(InfillingTable.INSERT_INFILLING_TABLE_FOURTH);
        db.execSQL(InfillingTable.INSERT_INFILLING_TABLE_FIFTH);
    }

    private static void insertRoughness(SQLiteDatabase db) {
        db.execSQL(RoughnessTable.INSERT_ROUGHNESS_TABLE);
        db.execSQL(RoughnessTable.INSERT_ROUGHNESS_TABLE_SECOND);
        db.execSQL(RoughnessTable.INSERT_ROUGHNESS_TABLE_THIRD);
        db.execSQL(RoughnessTable.INSERT_ROUGHNESS_TABLE_FOURTH);
        db.execSQL(RoughnessTable.INSERT_ROUGHNESS_TABLE_FIFTH);
    }

    private static void insertPersistences(SQLiteDatabase db) {
        db.execSQL(PersistenceTable.INSERT_PERSISTENCE_TABLE);
        db.execSQL(PersistenceTable.INSERT_PERSISTENCE_TABLE_SECOND);
        db.execSQL(PersistenceTable.INSERT_PERSISTENCE_TABLE_THIRD);
        db.execSQL(PersistenceTable.INSERT_PERSISTENCE_TABLE_FOURTH);
        db.execSQL(PersistenceTable.INSERT_PERSISTENCE_TABLE_FIFTH);
    }

    private static void insertApertures(SQLiteDatabase db) {
        db.execSQL(ApertureTable.INSERT_APERTURE_TABLE);
        db.execSQL(ApertureTable.INSERT_APERTURE_TABLE_SECOND);
        db.execSQL(ApertureTable.INSERT_APERTURE_TABLE_THIRD);
        db.execSQL(ApertureTable.INSERT_APERTURE_TABLE_FOURTH);
        db.execSQL(ApertureTable.INSERT_APERTURE_TABLE_FIFTH);
    }

    private static void insertOrientations(SQLiteDatabase db) {
        db.execSQL(OrientationTable.INSERT_ORIENTATION_TABLE);
        db.execSQL(OrientationTable.INSERT_ORIENTATION_TABLE_SECOND);
        db.execSQL(OrientationTable.INSERT_ORIENTATION_TABLE_THIRD);
        db.execSQL(OrientationTable.INSERT_ORIENTATION_TABLE_FOURTH);
        db.execSQL(OrientationTable.INSERT_ORIENTATION_TABLE_FIFTH);
        db.execSQL(OrientationTable.INSERT_ORIENTATION_TABLE_SIXTH);
        db.execSQL(OrientationTable.INSERT_ORIENTATION_TABLE_SEVENTH);
        db.execSQL(OrientationTable.INSERT_ORIENTATION_TABLE_EIGHTH);
        db.execSQL(OrientationTable.INSERT_ORIENTATION_TABLE_NINETH);
        db.execSQL(OrientationTable.INSERT_ORIENTATION_TABLE_TENTH);
        db.execSQL(OrientationTable.INSERT_ORIENTATION_TABLE_ELEVENTH);
        db.execSQL(OrientationTable.INSERT_ORIENTATION_TABLE_TWELFTH);
        db.execSQL(OrientationTable.INSERT_ORIENTATION_TABLE_THIRDTEENH);
        db.execSQL(OrientationTable.INSERT_ORIENTATION_TABLE_FOURTEENTH);
    }

    private static void insertESR(SQLiteDatabase db) {
        db.execSQL(ESRTable.INSERT_ESR_TABLE);
        db.execSQL(ESRTable.INSERT_ESR_TABLE_SECOND);
        db.execSQL(ESRTable.INSERT_ESR_TABLE_THIRD);
        db.execSQL(ESRTable.INSERT_ESR_TABLE_FOURTH);
        db.execSQL(ESRTable.INSERT_ESR_TABLE_FIFTH);
        db.execSQL(ESRTable.INSERT_ESR_TABLE_SIXTH);
        db.execSQL(ESRTable.INSERT_ESR_TABLE_SEVENTH);
        db.execSQL(ESRTable.INSERT_ESR_TABLE_EIGHTH);
    }

    private static void insertIndexGroups(SQLiteDatabase db) {
        db.execSQL(MappexIndexGroupsTable.INSERT_GROUPS_TABLE);
        db.execSQL(MappexIndexGroupsTable.INSERT_GROUPS_TABLE_SECOND);
        db.execSQL(MappexIndexGroupsTable.INSERT_GROUPS_TABLE_THIRD);
        db.execSQL(MappexIndexGroupsTable.INSERT_GROUPS_TABLE_FOURTH);
        db.execSQL(MappexIndexGroupsTable.INSERT_GROUPS_TABLE_FIFTH);
        db.execSQL(MappexIndexGroupsTable.INSERT_GROUPS_TABLE_SIXTH);
        db.execSQL(MappexIndexGroupsTable.INSERT_GROUPS_TABLE_SEVENTH);
        db.execSQL(MappexIndexGroupsTable.INSERT_GROUPS_TABLE_EIGTH);
        db.execSQL(MappexIndexGroupsTable.INSERT_GROUPS_TABLE_NINETH);
        db.execSQL(MappexIndexGroupsTable.INSERT_GROUPS_TABLE_TENTH);
        db.execSQL(MappexIndexGroupsTable.INSERT_GROUPS_TABLE_ELEVENTH);
        db.execSQL(MappexIndexGroupsTable.INSERT_GROUPS_TABLE_TWELFTH);
        db.execSQL(MappexIndexGroupsTable.INSERT_GROUPS_TABLE_THIRDTEENTH);
        db.execSQL(MappexIndexGroupsTable.INSERT_GROUPS_TABLE_FORTHTEENTH);
        db.execSQL(MappexIndexGroupsTable.INSERT_GROUPS_TABLE_FIFTEENTH);
        db.execSQL(MappexIndexGroupsTable.INSERT_GROUPS_TABLE_SIXTEENTH);
        db.execSQL(MappexIndexGroupsTable.INSERT_GROUPS_TABLE_SEVENTEENTH);
        db.execSQL(MappexIndexGroupsTable.INSERT_GROUPS_TABLE_EIGHTEENTH);
        db.execSQL(MappexIndexGroupsTable.INSERT_GROUPS_TABLE_NINETEENTH);
        db.execSQL(MappexIndexGroupsTable.INSERT_GROUPS_TABLE_TWENTIETH);
    }

    private static void insertIndexes(SQLiteDatabase db) {
        db.execSQL(MappedIndexTable.INSERT_INDEXES_TABLE);
        db.execSQL(MappedIndexTable.INSERT_INDEXES_TABLE_SECOND);
        db.execSQL(MappedIndexTable.INSERT_INDEXES_TABLE_THIRD);
        db.execSQL(MappedIndexTable.INSERT_INDEXES_TABLE_FOURTH);
        db.execSQL(MappedIndexTable.INSERT_INDEXES_TABLE_FIFTH);
        db.execSQL(MappedIndexTable.INSERT_INDEXES_TABLE_SIXTH);
        db.execSQL(MappedIndexTable.INSERT_INDEXES_TABLE_SEVENTTH);
        db.execSQL(MappedIndexTable.INSERT_INDEXES_TABLE_EIGTH);
        db.execSQL(MappedIndexTable.INSERT_INDEXES_TABLE_NINETH);
        db.execSQL(MappedIndexTable.INSERT_INDEXES_TABLE_TENTH);
        db.execSQL(MappedIndexTable.INSERT_INDEXES_TABLE_ELEVENTH);
        db.execSQL(MappedIndexTable.INSERT_INDEXES_TABLE_TWELFTH);
        db.execSQL(MappedIndexTable.INSERT_INDEXES_TABLE_THIRDTEENTH);
        db.execSQL(MappedIndexTable.INSERT_INDEXES_TABLE_FOURTEENTH);
        db.execSQL(MappedIndexTable.INSERT_INDEXES_TABLE_FIFTEENTH);
        db.execSQL(MappedIndexTable.INSERT_INDEXES_TABLE_SIXTEENTH);
        db.execSQL(MappedIndexTable.INSERT_INDEXES_TABLE_SEVENTEENTH);
    }

    private static void insertFractureTypes(SQLiteDatabase db) {
        db.execSQL(FractureTypeTable.INSERT_FRACTURES_TABLE);
        db.execSQL(FractureTypeTable.INSERT_FRACTURES_TABLE_SECOND);
        db.execSQL(FractureTypeTable.INSERT_FRACTURES_TABLE_THIRD);
        db.execSQL(FractureTypeTable.INSERT_FRACTURES_TABLE_FOURTH);
        db.execSQL(FractureTypeTable.INSERT_FRACTURES_TABLE_FIFTH);
        db.execSQL(FractureTypeTable.INSERT_FRACTURES_TABLE_SIXTH);
    }

    private static void insertShapes(SQLiteDatabase db) {
        db.execSQL(DiscontinuityShapeTable.INSERT_SHAPES_TABLE);
        db.execSQL(DiscontinuityShapeTable.INSERT_SHAPES_TABLE_SECOND);
        db.execSQL(DiscontinuityShapeTable.INSERT_SHAPES_TABLE_THIRD);
    }

    private static void insertWaters(SQLiteDatabase db) {
        db.execSQL(DiscontinuityWaterTable.INSERT_WATERS_TABLE);
        db.execSQL(DiscontinuityWaterTable.INSERT_WATERS_TABLE_SECOND);
        db.execSQL(DiscontinuityWaterTable.INSERT_WATERS_TABLE_THIRD);
        db.execSQL(DiscontinuityWaterTable.INSERT_WATERS_TABLE_FOURTH);
        db.execSQL(DiscontinuityWaterTable.INSERT_WATERS_TABLE_FIFTH);
    }

    private static void insertRelevances(SQLiteDatabase db) {
        db.execSQL(DiscontinuityRelevanceTable.INSERT_RELEVANCES_TABLE);
        db.execSQL(DiscontinuityRelevanceTable.INSERT_RELEVANCES_TABLE_SECOND);
    }

    private static void insertDiscontinuityTypes(SQLiteDatabase db) {
        db.execSQL(DiscontinuityTypeTable.INSERT_DISCONTINUITIES_TABLE);
        db.execSQL(DiscontinuityTypeTable.INSERT_DISCONTINUITIES_TABLE_SECOND);
        db.execSQL(DiscontinuityTypeTable.INSERT_DISCONTINUITIES_TABLE_THIRD);
        db.execSQL(DiscontinuityTypeTable.INSERT_DISCONTINUITIES_TABLE_FOURTH);
    }

    private static void insertShotcreteTypes(SQLiteDatabase db) {
        db.execSQL(ShotcreteTypeTable.INSERT_SHOTCRETES_TABLE);
        db.execSQL(ShotcreteTypeTable.INSERT_SHOTCRETES_TABLE_SECOND);
        db.execSQL(ShotcreteTypeTable.INSERT_SHOTCRETES_TABLE_THIRD);
    }

    private static void insertMeshTypes(SQLiteDatabase db) {
        db.execSQL(MeshTypeTable.INSERT_MESHES_TABLE);
        db.execSQL(MeshTypeTable.INSERT_MESHES_TABLE_SECOND);
    }

    private static void insertCoverages(SQLiteDatabase db) {
        db.execSQL(CoverageTable.INSERT_COVERAGES_TABLE);
        db.execSQL(CoverageTable.INSERT_COVERAGES_TABLE_SECOND);
        db.execSQL(CoverageTable.INSERT_COVERAGES_TABLE_THIRD);
        db.execSQL(CoverageTable.INSERT_COVERAGES_TABLE_FOURTH);
    }

    private static void insertBoltTypes(SQLiteDatabase db) {
        db.execSQL(BoltTypeTable.INSERT_BOLTS_TABLE);
        db.execSQL(BoltTypeTable.INSERT_BOLTS_TABLE_SECOND);
        db.execSQL(BoltTypeTable.INSERT_BOLTS_TABLE_THIRD);
        db.execSQL(BoltTypeTable.INSERT_BOLTS_TABLE_FOURTH);
    }

    private static void insertArchs(SQLiteDatabase db) {
        db.execSQL(ArchTypeTable.INSERT_ARCHS_TABLE);
        db.execSQL(ArchTypeTable.INSERT_ARCHS_TABLE_SECOND);
        db.execSQL(ArchTypeTable.INSERT_ARCHS_TABLE_THIRD);
    }

    private static void insertMethods(SQLiteDatabase db) {
        db.execSQL(ExcavationMethodTable.INSERT_METHODS_TABLE);
        db.execSQL(ExcavationMethodTable.INSERT_METHODS_TABLE_SECOND);
        db.execSQL(ExcavationMethodTable.INSERT_METHODS_TABLE_THIRD);
        db.execSQL(ExcavationMethodTable.INSERT_METHODS_TABLE_FOURTH);
    }

    private static void insertSections(SQLiteDatabase db) {
        db.execSQL(ExcavationSectionTable.INSERT_SECTIONS_TABLE);
        db.execSQL(ExcavationSectionTable.INSERT_SECTIONS_TABLE_SECOND);
        db.execSQL(ExcavationSectionTable.INSERT_SECTIONS_TABLE_THIRD);
        db.execSQL(ExcavationSectionTable.INSERT_SECTIONS_TABLE_FOURTH);
        db.execSQL(ExcavationSectionTable.INSERT_SECTIONS_TABLE_FIFTH);
        db.execSQL(ExcavationSectionTable.INSERT_SECTIONS_TABLE_SIXTH);
    }

    private static void insertFaces(SQLiteDatabase db) {
        db.execSQL(TunnelFaceTable.INSERT_FACES_TABLE);
        db.execSQL(TunnelFaceTable.INSERT_FACES_TABLE_SECOND);
        db.execSQL(TunnelFaceTable.INSERT_FACES_TABLE_THIRD);
        db.execSQL(TunnelFaceTable.INSERT_FACES_TABLE_FOURTH);
        db.execSQL(TunnelFaceTable.INSERT_FACES_TABLE_FIFTH);
        db.execSQL(TunnelFaceTable.INSERT_FACES_TABLE_SIXTH);
        db.execSQL(TunnelFaceTable.INSERT_FACES_TABLE_SEVETH);
    }

    private static void insertTunnels(SQLiteDatabase db) {
        db.execSQL(TunnelTable.INSERT_TUNNELS_TABLE);
        db.execSQL(TunnelTable.INSERT_TUNNELS_TABLE_SECOND);
        db.execSQL(TunnelTable.INSERT_TUNNELS_TABLE_THIRD);
        db.execSQL(TunnelTable.INSERT_TUNNELS_TABLE_FOURTH);
        db.execSQL(TunnelTable.INSERT_TUNNELS_TABLE_FIFTH);
    }

    private static void insertProjects(SQLiteDatabase db) {
        db.execSQL(ExcavationProjectTable.INSERT_PROJECTS_TABLE);
        db.execSQL(ExcavationProjectTable.INSERT_PROJECTS_TABLE_SECOND);
    }

    private static void insertRoles(SQLiteDatabase db) {
        db.execSQL(RoleTable.INSERT_ROLES_TABLE);
        db.execSQL(RoleTable.INSERT_ROLES_TABLE_SECOND);
        db.execSQL(RoleTable.INSERT_ROLES_TABLE_THIRD);
    }

    private static void insertUser(SQLiteDatabase db) {
        db.execSQL(UserTable.INSERT_USERS_TABLE);
        db.execSQL(UserTable.INSERT_USERS_TABLE_SECOND);
        db.execSQL(UserTable.INSERT_USERS_TABLE_THIRD);
    }

    private static void insertClients(SQLiteDatabase db) {
        db.execSQL(ClientTable.INSERT_CLIENTS_TABLE);
        db.execSQL(ClientTable.INSERT_CLIENTS_TABLE_SECOND);
    }

}