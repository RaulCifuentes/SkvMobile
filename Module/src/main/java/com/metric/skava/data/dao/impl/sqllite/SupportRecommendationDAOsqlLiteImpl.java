package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.database.utils.CursorUtils;
import com.metric.skava.app.util.SkavaUtils;
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.data.dao.LocalSupportRecommendationDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.table.SupportRecommendationTable;
import com.metric.skava.instructions.model.ArchType;
import com.metric.skava.instructions.model.BoltType;
import com.metric.skava.instructions.model.Coverage;
import com.metric.skava.instructions.model.MeshType;
import com.metric.skava.instructions.model.ShotcreteType;
import com.metric.skava.instructions.model.SupportPattern;
import com.metric.skava.instructions.model.SupportPatternType;
import com.metric.skava.instructions.model.SupportRecommendation;
import com.metric.skava.rocksupport.model.SupportRequirement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/14/14.
 */
public class SupportRecommendationDAOsqlLiteImpl extends SqlLiteBasePersistentEntityDAO<SupportRecommendation> implements LocalSupportRecommendationDAO {


    public SupportRecommendationDAOsqlLiteImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);

    }

    @Override
    protected List<SupportRecommendation> assemblePersistentEntities(Cursor cursor) throws DAOException {
        List<SupportRecommendation> resultList = new ArrayList<SupportRecommendation>();
        DAOFactory daoFactory = getDAOFactory();
        while (cursor.moveToNext()) {
            String assessment = CursorUtils.getString(SupportRecommendationTable.ASSESSMENT_CODE_COLUMN, cursor);

            String requirementCode = CursorUtils.getString(SupportRecommendationTable.SUPPORT_REQUIREMENT_BASE_CODE_COLUMN, cursor);
            SupportRequirement baseRequirement = null;
            if (requirementCode != null) {
                baseRequirement = daoFactory.getLocalSupportRequirementDAO().getSupportRequirement(requirementCode);
            }

            String patternTypeCode = CursorUtils.getString(SupportRecommendationTable.ROOF_PATTERN_TYPE_CODE_COLUMN, cursor);
            Double distanceX = CursorUtils.getDouble(SupportRecommendationTable.ROOF_PATTERN_DX_COLUMN, cursor);
            Double distanceY = CursorUtils.getDouble(SupportRecommendationTable.ROOF_PATTERN_DY_COLUMN, cursor);
            SupportPatternType type = daoFactory.getLocalSupportPatternTypeDAO().getSupportPatternTypeByUniqueCode(patternTypeCode);
            SupportPattern roofPattern = new SupportPattern(type, distanceX, distanceY);

            patternTypeCode = CursorUtils.getString(SupportRecommendationTable.WALL_PATTERN_TYPE_CODE_COLUMN, cursor);
            distanceX = CursorUtils.getDouble(SupportRecommendationTable.WALL_PATTERN_DX_COLUMN, cursor);
            distanceY = CursorUtils.getDouble(SupportRecommendationTable.WALL_PATTERN_DY_COLUMN, cursor);
            type = daoFactory.getLocalSupportPatternTypeDAO().getSupportPatternTypeByUniqueCode(patternTypeCode);
            SupportPattern wallPattern = new SupportPattern(type, distanceX, distanceY);

            String boltTypeCode = CursorUtils.getString(SupportRecommendationTable.BOLT_TYPE_CODE_COLUMN, cursor);
            BoltType boltType = daoFactory.getLocalBoltTypeDAO().getBoltTypeByCode(boltTypeCode);

            Double boltDiameter = CursorUtils.getDouble(SupportRecommendationTable.BOLT_DIAMETER_COLUMN, cursor);
            Double boltLength = CursorUtils.getDouble(SupportRecommendationTable.BOLT_LENGTH_COLUMN, cursor);

            String coverageCode = CursorUtils.getString(SupportRecommendationTable.COVERAGE_CODE_COLUMN, cursor);
            Coverage coverage = daoFactory.getLocalCoverageDAO().getCoverageByCode(coverageCode);

            String meshTypeCode = CursorUtils.getString(SupportRecommendationTable.MESH_TYPE_CODE_COLUMN, cursor);
            MeshType meshType = daoFactory.getLocalMeshTypeDAO().getMeshTypeByCode(meshTypeCode);

            String shotcreteTypeCode = CursorUtils.getString(SupportRecommendationTable.SHOTCRETE_TYPE_CODE_COLUMN, cursor);
            ShotcreteType shotcreteType = daoFactory.getLocalShotcreteTypeDAO().getShotcreteTypeByCode(shotcreteTypeCode);

            String archTypeCode = CursorUtils.getString(SupportRecommendationTable.ARCH_TYPE_CODE_COLUMN, cursor);
            ArchType archType = daoFactory.getLocalArchTypeDAO().getArchTypeByCode(archTypeCode);

            Double separation = CursorUtils.getDouble(SupportRecommendationTable.SEPARATION_COLUMN, cursor);
            Double thickness = CursorUtils.getDouble(SupportRecommendationTable.THICKNESS_COLUMN, cursor);
            String observations = CursorUtils.getString(SupportRecommendationTable.OBSERVATIONS_COLUMN, cursor);

            SupportRecommendation newInstance = new SupportRecommendation();
            newInstance.setArchType(archType);
            newInstance.setBoltType(boltType);
            newInstance.setCoverage(coverage);
            newInstance.setRoofPattern(roofPattern);
            newInstance.setWallPattern(wallPattern);
            newInstance.setRequirement(baseRequirement);
            newInstance.setBoltDiameter(boltDiameter);
            newInstance.setBoltLength(boltLength);
            newInstance.setMeshType(meshType);
            newInstance.setObservations(observations);
            newInstance.setSeparation(separation);
            newInstance.setShotcreteType(shotcreteType);
            newInstance.setThickness(thickness);

            resultList.add(newInstance);
        }
        return resultList;
    }


    @Override
    public SupportRecommendation getSupportRecommendation(String assessmentCode) throws DAOException {
        Cursor cursor = getRecordsFilteredByColumn(SupportRecommendationTable.RECOMENDATION_DATABASE_TABLE, SupportRecommendationTable.ASSESSMENT_CODE_COLUMN, assessmentCode, null);
        List<SupportRecommendation> list = assemblePersistentEntities(cursor);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public void saveSupportRecommendation(String assessmentCode, SupportRecommendation newSupportRecommendation) throws DAOException {
        savePersistentEntity(SupportRecommendationTable.RECOMENDATION_DATABASE_TABLE, assessmentCode, newSupportRecommendation);
    }

    @Override
    protected void savePersistentEntity(String tableName, SupportRecommendation newSkavaEntity) throws DAOException {
        savePersistentEntity(tableName, null, newSkavaEntity);
    }

    protected void savePersistentEntity(String tableName, String assessmentCode, SupportRecommendation recommendation) throws DAOException {
        if (recommendation != null && recommendation.hasSelectedAnything()) {
            String[] recommendationNames = new String[]{
                    SupportRecommendationTable.ASSESSMENT_CODE_COLUMN,
                    SupportRecommendationTable.SUPPORT_REQUIREMENT_BASE_CODE_COLUMN,
                    SupportRecommendationTable.BOLT_TYPE_CODE_COLUMN,
                    SupportRecommendationTable.BOLT_DIAMETER_COLUMN,
                    SupportRecommendationTable.BOLT_LENGTH_COLUMN,
                    SupportRecommendationTable.ROOF_PATTERN_TYPE_CODE_COLUMN,
                    SupportRecommendationTable.ROOF_PATTERN_DX_COLUMN,
                    SupportRecommendationTable.ROOF_PATTERN_DY_COLUMN,
                    SupportRecommendationTable.WALL_PATTERN_TYPE_CODE_COLUMN,
                    SupportRecommendationTable.WALL_PATTERN_DX_COLUMN,
                    SupportRecommendationTable.WALL_PATTERN_DY_COLUMN,
                    SupportRecommendationTable.SHOTCRETE_TYPE_CODE_COLUMN,
                    SupportRecommendationTable.THICKNESS_COLUMN,
                    SupportRecommendationTable.MESH_TYPE_CODE_COLUMN,
                    SupportRecommendationTable.COVERAGE_CODE_COLUMN,
                    SupportRecommendationTable.ARCH_TYPE_CODE_COLUMN,
                    SupportRecommendationTable.SEPARATION_COLUMN,
                    SupportRecommendationTable.OBSERVATIONS_COLUMN
            };

            Object[] recommendationValues = new Object[]{
                    assessmentCode,
                    recommendation.getRequirementBase() != null ? recommendation.getRequirementBase().getCode() : null,
                    SkavaUtils.isDefined(recommendation.getBoltType()) ? recommendation.getBoltType().getCode() : null,
                    recommendation.getBoltDiameter(),
                    recommendation.getBoltLength(),
                    recommendation.getRoofPattern() != null ? SkavaUtils.isDefined(recommendation.getRoofPattern().getType())? recommendation.getRoofPattern().getType().getCode() : null : null,
                    recommendation.getRoofPattern() != null ? recommendation.getRoofPattern().getDistanceX() : null,
                    recommendation.getRoofPattern() != null ? recommendation.getRoofPattern().getDistanceY() : null,
                    recommendation.getWallPattern() != null ? SkavaUtils.isDefined(recommendation.getWallPattern().getType())? recommendation.getWallPattern().getType().getCode() : null : null,
                    recommendation.getWallPattern() != null ? recommendation.getWallPattern().getDistanceX() : null,
                    recommendation.getWallPattern() != null ? recommendation.getWallPattern().getDistanceY() : null,
                    SkavaUtils.isDefined(recommendation.getShotcreteType()) ? recommendation.getShotcreteType().getCode() : null,
                    recommendation.getThickness(),
                    SkavaUtils.isDefined(recommendation.getMeshType()) ? recommendation.getMeshType().getCode() : null,
                    SkavaUtils.isDefined(recommendation.getCoverage()) ? recommendation.getCoverage().getCode() : null,
                    SkavaUtils.isDefined(recommendation.getArchType()) ? recommendation.getArchType().getCode() : null,
                    recommendation.getSeparation(),
                    recommendation.getObservations()
            };
            Long supportRecommendationId = saveRecord(tableName, recommendationNames, recommendationValues);
            recommendation.set_id(supportRecommendationId);
        }
    }


    @Override
    public boolean deleteSupportRecommendation(String assessmentCode) {
        int numDeleted = deletePersistentEntitiesFilteredByColumn(SupportRecommendationTable.RECOMENDATION_DATABASE_TABLE, SupportRecommendationTable.ASSESSMENT_CODE_COLUMN, assessmentCode);
        return numDeleted != -1;
    }


    @Override
    public int deleteAllSupportRecommendations() throws DAOException {
        return deleteAllPersistentEntities(SupportRecommendationTable.RECOMENDATION_DATABASE_TABLE);
    }


}
