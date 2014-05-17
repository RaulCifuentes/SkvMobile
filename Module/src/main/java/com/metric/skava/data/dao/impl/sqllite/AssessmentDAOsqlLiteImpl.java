package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.database.utils.CursorUtils;
import com.metric.skava.app.model.Assessment;
import com.metric.skava.app.model.TunnelFace;
import com.metric.skava.app.model.User;
import com.metric.skava.app.util.DateDataFormat;
import com.metric.skava.app.util.SkavaUtils;
import com.metric.skava.calculator.barton.model.Q_Calculation;
import com.metric.skava.calculator.rmr.model.RMR_Calculation;
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.data.dao.LocalAssessmentDAO;
import com.metric.skava.data.dao.LocalDiscontinuityFamilyDAO;
import com.metric.skava.data.dao.LocalQCalculationDAO;
import com.metric.skava.data.dao.LocalRMRCalculationDAO;
import com.metric.skava.data.dao.LocalTunnelFaceDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.helper.AssessmentBuilder4SqlLite;
import com.metric.skava.data.dao.impl.sqllite.table.AssessmentTable;
import com.metric.skava.data.dao.impl.sqllite.table.ExternalResourcesTable;
import com.metric.skava.data.dao.impl.sqllite.table.SupportRecomendationTable;
import com.metric.skava.discontinuities.model.DiscontinuityFamily;
import com.metric.skava.instructions.model.ArchType;
import com.metric.skava.instructions.model.BoltType;
import com.metric.skava.instructions.model.Coverage;
import com.metric.skava.instructions.model.MeshType;
import com.metric.skava.instructions.model.ShotcreteType;
import com.metric.skava.instructions.model.SupportPattern;
import com.metric.skava.instructions.model.SupportPatternType;
import com.metric.skava.instructions.model.SupportRecomendation;
import com.metric.skava.rocksupport.model.SupportRequirement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/18/14.
 */
public class AssessmentDAOsqlLiteImpl extends SqlLiteBaseIdentifiableEntityDAO<Assessment> implements LocalAssessmentDAO {

    //    private LocalPermissionDAO mLocalPermissionDAO;
    private LocalTunnelFaceDAO mLocalTunnelFaceDAO;
    private LocalDiscontinuityFamilyDAO mLocalDiscontinuityFamilyDAO;
    private LocalQCalculationDAO mLocalQCalculationDAO;
    private LocalRMRCalculationDAO mLocalRMRCalculationDAO;


    private AssessmentBuilder4SqlLite assessmentBuilder;

    public AssessmentDAOsqlLiteImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        assessmentBuilder = new AssessmentBuilder4SqlLite(mContext, skavaContext);
        //        mLocalPermissionDAO = getDAOFactory().getLocalPermissionDAO();
        mLocalTunnelFaceDAO = getDAOFactory().getLocalTunnelFaceDAO();
        mLocalDiscontinuityFamilyDAO = getDAOFactory().getLocalDiscontinuityFamilyDAO();
        mLocalQCalculationDAO = getDAOFactory().getLocalQCalculationDAO();
        mLocalRMRCalculationDAO = getDAOFactory().getLocalRMRCalculationDAO();
    }

    @Override
    public List<Assessment> getAllAssessments() throws DAOException {
        Cursor cursor = getAllRecords(AssessmentTable.ASSESSMENT_DATABASE_TABLE);
        List<Assessment> list = assemblePersistentEntities(cursor);
        cursor.close();
        return list;
    }

    @Override
    public Assessment getAssessment(String code) throws DAOException {
        Cursor cursor = getRecordsFilteredByColumn(AssessmentTable.ASSESSMENT_DATABASE_TABLE, AssessmentTable.CODE_COLUMN, code, null);
        List<Assessment> list = assemblePersistentEntities(cursor);
        if (list.isEmpty()) {
            throw new DAOException("Entity not found. [Code : " + code + "]");
        }
        if (list.size() > 1) {
            throw new DAOException("Multiple records for same code. [Code : " + code + "]");
        }
        cursor.close();
        return list.get(0);
    }

    @Override
    public Assessment getAssessmentByInternalCode(String internalCode) throws DAOException {
        Cursor cursor = getRecordsFilteredByColumn(AssessmentTable.ASSESSMENT_DATABASE_TABLE, AssessmentTable.INTERNAL_CODE_COLUMN, internalCode, null);
        List<Assessment> list = assemblePersistentEntities(cursor);
        if (list.isEmpty()) {
            throw new DAOException("Entity not found. [Internal Code : " + internalCode + "]");
        }
        if (list.size() > 1) {
            throw new DAOException("Multiple records for same code. [Internal Code : " + internalCode + "]");
        }
        cursor.close();
        return list.get(0);
    }

    @Override
    public List<Assessment> getAssessmentsByUser(User user) throws DAOException {
        List<Assessment> allAssessments = new ArrayList<Assessment>();
        List<TunnelFace> facesGranted = mLocalTunnelFaceDAO.getTunnelFacesByUser(user);
        //find the last five active assessment for each of those faces
        for (TunnelFace grantedFace : facesGranted) {
            List<Assessment> grantedFaceAssessments = getAssessmentsByTunnelFace(grantedFace);
            allAssessments.addAll(grantedFaceAssessments);
        }
        return allAssessments;
    }

    @Override
    public List<Assessment> getAssessmentsByTunnelFace(TunnelFace face) throws DAOException {
        Cursor cursor = getRecordsFilteredByColumn(AssessmentTable.ASSESSMENT_DATABASE_TABLE, AssessmentTable.TUNEL_FACE_CODE_COLUMN, face.getCode(), null);
        List<Assessment> list = assemblePersistentEntities(cursor);
        return list;
    }


    @Override
    protected List<Assessment> assemblePersistentEntities(Cursor cursor) throws DAOException {
        List<Assessment> list = new ArrayList<Assessment>();
        while (cursor.moveToNext()) {
            //First the barebones assessment data
            Assessment reconstructedAssessment = assessmentBuilder.buildBareAssessmentFromCursorRecord(cursor);
            //Now the associated support recommendation, Q and RMR calculations, Discontinuity Systems, etc

            String assessmentCode = reconstructedAssessment.getCode();

            List<DiscontinuityFamily> discontinuitySystem = mLocalDiscontinuityFamilyDAO.getDiscontinuityFamilies(assessmentCode);
            reconstructedAssessment.setDiscontinuitySystem(discontinuitySystem);

            Q_Calculation qCalculation = mLocalQCalculationDAO.getQCalculation(assessmentCode);
            reconstructedAssessment.setQCalculation(qCalculation);

            RMR_Calculation rmrCalculation = mLocalRMRCalculationDAO.getRMRCalculation(assessmentCode);
            reconstructedAssessment.setRmrCalculation(rmrCalculation);

            List<Uri> resourceList = getResourcesByAssessmentCode(assessmentCode);
            reconstructedAssessment.setPictureUriList(resourceList);

            SupportRecomendation recomendation = getRecommendationByAssessmentCode(assessmentCode);
            reconstructedAssessment.setRecomendation(recomendation);

            list.add(reconstructedAssessment);
        }
        return list;
    }

    private List<Uri> getResourcesByAssessmentCode(String code) {
        Cursor cursor = getRecordsFilteredByColumn(ExternalResourcesTable.EXTERNAL_RESOURCES_DATABASE_TABLE, ExternalResourcesTable.ASSESSMENT_CODE_COLUMN, code, ExternalResourcesTable.RESOURCE_ORDINAL);
        List<Uri> list = assembleResourceList(cursor);
        return list;
    }


    private List<Uri> assembleResourceList(Cursor cursor) {
        List<Uri> result = new ArrayList<Uri>(5);
        result.add(null);
        result.add(null);
        result.add(null);
        result.add(null);
        while (cursor.moveToNext()) {
            Integer resourceIndex = CursorUtils.getInt(ExternalResourcesTable.RESOURCE_ORDINAL, cursor);
            String uriString = CursorUtils.getString(ExternalResourcesTable.RESOURCE_URL_COLUMN, cursor);
            Uri uri = Uri.parse(uriString);
            if (resourceIndex < 3) {
                result.set(resourceIndex, uri);
            } else {
                result.add(resourceIndex, uri);
            }
        }
        return result;
    }


    private SupportRecomendation getRecommendationByAssessmentCode(String code) throws DAOException {
        Cursor cursor = getRecordsFilteredByColumn(SupportRecomendationTable.RECOMENDATION_DATABASE_TABLE, SupportRecomendationTable.ASSESSMENT_CODE_COLUMN, code, null);
        SupportRecomendation recomendation = assembleSupportRecommendation(cursor);
        return recomendation;
    }

    private SupportRecomendation assembleSupportRecommendation(Cursor cursor) throws DAOException {
        List<SupportRecomendation> resultList = new ArrayList<SupportRecomendation>();
        DAOFactory daoFactory = getDAOFactory();
        while (cursor.moveToNext()) {
            String assessment = CursorUtils.getString(SupportRecomendationTable.ASSESSMENT_CODE_COLUMN, cursor);

            String requirementCode = CursorUtils.getString(SupportRecomendationTable.SUPPORT_REQUIREMENT_BASE_CODE_COLUMN, cursor);
            SupportRequirement requirement = daoFactory.getLocalSupportRequirementDAO().getSupportRequirement(requirementCode);

            String patternTypeCode = CursorUtils.getString(SupportRecomendationTable.ROOF_PATTERN_TYPE_CODE_COLUMN, cursor);
            Double distanceX = CursorUtils.getDouble(SupportRecomendationTable.ROOF_PATTERN_DX_COLUMN, cursor);
            Double distanceY = CursorUtils.getDouble(SupportRecomendationTable.ROOF_PATTERN_DY_COLUMN, cursor);
            SupportPatternType type = daoFactory.getLocalSupportPatternTypeDAO().getSupportPatternTypeByUniqueCode(patternTypeCode);
            SupportPattern roofPattern = new SupportPattern(type, distanceX, distanceY);

            patternTypeCode = CursorUtils.getString(SupportRecomendationTable.WALL_PATTERN_TYPE_CODE_COLUMN, cursor);
            distanceX = CursorUtils.getDouble(SupportRecomendationTable.WALL_PATTERN_DX_COLUMN, cursor);
            distanceY = CursorUtils.getDouble(SupportRecomendationTable.WALL_PATTERN_DY_COLUMN, cursor);
            type = daoFactory.getLocalSupportPatternTypeDAO().getSupportPatternTypeByUniqueCode(patternTypeCode);
            SupportPattern wallPattern = new SupportPattern(type, distanceX, distanceY);

            String boltTypeCode = CursorUtils.getString(SupportRecomendationTable.BOLT_TYPE_CODE_COLUMN, cursor);
            BoltType boltType = daoFactory.getLocalBoltTypeDAO().getBoltTypeByCode(boltTypeCode);

            Double boltDiameter = CursorUtils.getDouble(SupportRecomendationTable.BOLT_DIAMETER_COLUMN, cursor);
            Double boltLength = CursorUtils.getDouble(SupportRecomendationTable.BOLT_LENGTH_COLUMN, cursor);

            String coverageCode = CursorUtils.getString(SupportRecomendationTable.COVERAGE_CODE_COLUMN, cursor);
            Coverage coverage = daoFactory.getLocalCoverageDAO().getCoverageByCode(coverageCode);

            String meshTypeCode = CursorUtils.getString(SupportRecomendationTable.MESH_TYPE_CODE_COLUMN, cursor);
            MeshType meshType = daoFactory.getLocalMeshTypeDAO().getMeshTypeByCode(meshTypeCode);

            String shotcreteTypeCode = CursorUtils.getString(SupportRecomendationTable.SHOTCRETE_TYPE_CODE_COLUMN, cursor);
            ShotcreteType shotcreteType = daoFactory.getLocalShotcreteTypeDAO().getShotcreteTypeByCode(shotcreteTypeCode);

            String archTypeCode = CursorUtils.getString(SupportRecomendationTable.ARCH_TYPE_CODE_COLUMN, cursor);
            ArchType archType = daoFactory.getLocalArchTypeDAO().getArchTypeByCode(archTypeCode);

            Double separation = CursorUtils.getDouble(SupportRecomendationTable.SEPARATION_COLUMN, cursor);
            Double thickness = CursorUtils.getDouble(SupportRecomendationTable.THICKNESS_COLUMN, cursor);
            String observations = CursorUtils.getString(SupportRecomendationTable.OBSERVATIONS_COLUMN, cursor);

            SupportRecomendation newInstance = new SupportRecomendation();
            newInstance.setArchType(archType);
            newInstance.setBoltType(boltType);
            newInstance.setCoverage(coverage);
            newInstance.setRoofPattern(roofPattern);
            newInstance.setWallPattern(wallPattern);
            newInstance.setRequirement(requirement);
            newInstance.setBoltDiameter(boltDiameter);
            newInstance.setBoltLength(boltLength);
            newInstance.setMeshType(meshType);
            newInstance.setObservations(observations);
            newInstance.setSeparation(separation);
            newInstance.setShotcreteType(shotcreteType);
            newInstance.setThickness(thickness);
            resultList.add(newInstance);
        }
        //TODO This should be one and only one Check is not null and stuff
        if (!resultList.isEmpty()) {
            return resultList.get(0);
        } else {
            return null;
        }
    }


    @Override
    public void saveAssessment(Assessment assessment) throws DAOException {
        savePersistentEntity(AssessmentTable.ASSESSMENT_DATABASE_TABLE, assessment);
    }

    public void updateAssessment(Assessment newSkavaEntity, boolean includeRelations) throws DAOException {

        if (includeRelations) {
            saveAssessment(newSkavaEntity);
        } else {
            String[] names = new String[]{
                    AssessmentTable.CODE_COLUMN,
                    AssessmentTable.INTERNAL_CODE_COLUMN,
                    AssessmentTable.GEOLOGIST_CODE_COLUMN,
                    AssessmentTable.TUNEL_FACE_CODE_COLUMN,
                    AssessmentTable.DATE_COLUMN,
                    AssessmentTable.EXCAVATION_SECTION_CODE_COLUMN,
                    AssessmentTable.EXCAVATION_METHOD_CODE_COLUMN,
                    AssessmentTable.PK_INITIAL_COLUMN,
                    AssessmentTable.PK_FINAL_COLUMN,
                    AssessmentTable.ADVANCE_COLUMN,
                    AssessmentTable.ORIENTATION_COLUMN,
                    AssessmentTable.SLOPE_COLUMN,
                    AssessmentTable.FRACTURE_TYPE_CODE_COLUMN,
                    AssessmentTable.BLOCKS_SIZE_COLUMN,
                    AssessmentTable.NUMBER_JOINTS_COLUMN,
                    AssessmentTable.OUTCROP_COLUMN,
                    AssessmentTable.ROCK_SAMPLE_IDENTIFICATION_COLUMN,
                    AssessmentTable.SENT_TO_CLOUD_COLUMN
            };

            Object[] values = new Object[]{
                    newSkavaEntity.getCode(),
                    newSkavaEntity.getInternalCode(),
                    SkavaUtils.isUndefined(newSkavaEntity.getGeologist()) ? null : newSkavaEntity.getGeologist().getCode(),
                    SkavaUtils.isUndefined(newSkavaEntity.getFace()) ? null : newSkavaEntity.getFace().getCode(),
                    DateDataFormat.formatDateAsLong(newSkavaEntity.getDate()),
                    SkavaUtils.isUndefined(newSkavaEntity.getSection()) ? null : newSkavaEntity.getSection().getCode(),
                    SkavaUtils.isUndefined(newSkavaEntity.getMethod()) ? null : newSkavaEntity.getMethod().getCode(),
                    newSkavaEntity.getInitialPeg(),
                    newSkavaEntity.getFinalPeg(),
                    newSkavaEntity.getCurrentAdvance(),
                    newSkavaEntity.getOrientation(),
                    newSkavaEntity.getSlope(),
                    SkavaUtils.isUndefined(newSkavaEntity.getFractureType()) ? null : newSkavaEntity.getFractureType().getCode(),
                    newSkavaEntity.getBlockSize(),
                    newSkavaEntity.getNumberOfJoints(),
                    newSkavaEntity.getOutcropDescription(),
                    newSkavaEntity.getRockSampleIdentification(),
                    newSkavaEntity.isSentToCloud() ? 1 : 0
            };
            Long assesmentId = saveRecord(AssessmentTable.ASSESSMENT_DATABASE_TABLE, names, values);
            newSkavaEntity.set_id(assesmentId);
        }
    }

    @Override
    protected void savePersistentEntity(java.lang.String tableName, Assessment newSkavaEntity) throws DAOException {
        String[] names = new String[]{
                AssessmentTable.CODE_COLUMN,
                AssessmentTable.INTERNAL_CODE_COLUMN,
                AssessmentTable.GEOLOGIST_CODE_COLUMN,
                AssessmentTable.TUNEL_FACE_CODE_COLUMN,
                AssessmentTable.DATE_COLUMN,
                AssessmentTable.EXCAVATION_SECTION_CODE_COLUMN,
                AssessmentTable.EXCAVATION_METHOD_CODE_COLUMN,
                AssessmentTable.PK_INITIAL_COLUMN,
                AssessmentTable.PK_FINAL_COLUMN,
                AssessmentTable.ADVANCE_COLUMN,
                AssessmentTable.ORIENTATION_COLUMN,
                AssessmentTable.SLOPE_COLUMN,
                AssessmentTable.FRACTURE_TYPE_CODE_COLUMN,
                AssessmentTable.BLOCKS_SIZE_COLUMN,
                AssessmentTable.NUMBER_JOINTS_COLUMN,
                AssessmentTable.OUTCROP_COLUMN,
                AssessmentTable.ROCK_SAMPLE_IDENTIFICATION_COLUMN,
                AssessmentTable.SENT_TO_CLOUD_COLUMN
        };

        Object[] values = new Object[]{
                newSkavaEntity.getCode(),
                newSkavaEntity.getInternalCode(),
                SkavaUtils.isUndefined(newSkavaEntity.getGeologist()) ? null : newSkavaEntity.getGeologist().getCode(),
                SkavaUtils.isUndefined(newSkavaEntity.getFace()) ? null : newSkavaEntity.getFace().getCode(),
                DateDataFormat.formatDateAsLong(newSkavaEntity.getDate()),
                SkavaUtils.isUndefined(newSkavaEntity.getSection()) ? null : newSkavaEntity.getSection().getCode(),
                SkavaUtils.isUndefined(newSkavaEntity.getMethod()) ? null : newSkavaEntity.getMethod().getCode(),
                newSkavaEntity.getInitialPeg(),
                newSkavaEntity.getFinalPeg(),
                newSkavaEntity.getCurrentAdvance(),
                newSkavaEntity.getOrientation(),
                newSkavaEntity.getSlope(),
                SkavaUtils.isUndefined(newSkavaEntity.getFractureType()) ? null : newSkavaEntity.getFractureType().getCode(),
                newSkavaEntity.getBlockSize(),
                newSkavaEntity.getNumberOfJoints(),
                newSkavaEntity.getOutcropDescription(),
                newSkavaEntity.getRockSampleIdentification(),
                newSkavaEntity.isSentToCloud() ? 1 : 0
        };

        Long assesmentId = saveRecord(tableName, names, values);
        newSkavaEntity.set_id(assesmentId);

        // Save Discontinuity Families
        List<DiscontinuityFamily> discontinuitySystem = newSkavaEntity.getDiscontinuitySystem();
        if (discontinuitySystem != null) {
            for (DiscontinuityFamily currentFamily : discontinuitySystem) {
                if (currentFamily == null || !currentFamily.hasSelectedAnything()) {
                    continue;
                }
                mLocalDiscontinuityFamilyDAO.saveDiscontinuityFamily(newSkavaEntity.getCode(), currentFamily);
            }
        }

        // Save the related Q Calculation
        Q_Calculation qCalculation = newSkavaEntity.getQCalculation();
        mLocalQCalculationDAO.saveQCalculation(newSkavaEntity.getCode(), qCalculation);

        // Save the related RMR Calculation
        RMR_Calculation rmrCalculation = newSkavaEntity.getRmrCalculation();
        mLocalRMRCalculationDAO.saveRMRCalculation(newSkavaEntity.getCode(), rmrCalculation);


        //Save the related recommendation

        SupportRecomendation recomendation = newSkavaEntity.getRecomendation();
        if (recomendation != null && recomendation.hasSelectedAnything()) {
            String[] recommendationNames = new String[]{
                    SupportRecomendationTable.ASSESSMENT_CODE_COLUMN,
                    SupportRecomendationTable.SUPPORT_REQUIREMENT_BASE_CODE_COLUMN,
                    SupportRecomendationTable.BOLT_TYPE_CODE_COLUMN,
                    SupportRecomendationTable.BOLT_DIAMETER_COLUMN,
                    SupportRecomendationTable.BOLT_LENGTH_COLUMN,
                    SupportRecomendationTable.ROOF_PATTERN_TYPE_CODE_COLUMN,
                    SupportRecomendationTable.ROOF_PATTERN_DX_COLUMN,
                    SupportRecomendationTable.ROOF_PATTERN_DY_COLUMN,
                    SupportRecomendationTable.WALL_PATTERN_TYPE_CODE_COLUMN,
                    SupportRecomendationTable.WALL_PATTERN_DX_COLUMN,
                    SupportRecomendationTable.WALL_PATTERN_DY_COLUMN,
                    SupportRecomendationTable.SHOTCRETE_TYPE_CODE_COLUMN,
                    SupportRecomendationTable.THICKNESS_COLUMN,
                    SupportRecomendationTable.MESH_TYPE_CODE_COLUMN,
                    SupportRecomendationTable.COVERAGE_CODE_COLUMN,
                    SupportRecomendationTable.ARCH_TYPE_CODE_COLUMN,
                    SupportRecomendationTable.SEPARATION_COLUMN,
                    SupportRecomendationTable.OBSERVATIONS_COLUMN
            };
            Object[] recomendationValues = new Object[]{
                    newSkavaEntity.getCode(),
                    recomendation.getRequirementBase() != null ? recomendation.getRequirementBase().getCode() : null,
                    recomendation.getBoltType() != null ? recomendation.getBoltType().getCode() : null,
                    recomendation.getBoltDiameter(),
                    recomendation.getBoltLength(),
                    recomendation.getRoofPattern() != null ? recomendation.getRoofPattern().getType().getCode() : null,
                    recomendation.getRoofPattern() != null ? recomendation.getRoofPattern().getDistanceX() : null,
                    recomendation.getRoofPattern() != null ? recomendation.getRoofPattern().getDistanceY() : null,
                    recomendation.getWallPattern() != null ? recomendation.getWallPattern().getType().getCode() : null,
                    recomendation.getWallPattern() != null ? recomendation.getWallPattern().getDistanceX() : null,
                    recomendation.getWallPattern() != null ? recomendation.getWallPattern().getDistanceY() : null,
                    recomendation.getShotcreteType() != null ? recomendation.getShotcreteType().getCode() : null,
                    recomendation.getThickness(),
                    recomendation.getMeshType() != null ? recomendation.getMeshType().getCode() : null,
                    recomendation.getCoverage() != null ? recomendation.getCoverage().getCode() : null,
                    recomendation.getArchType() != null ? recomendation.getArchType().getCode() : null,
                    recomendation.getSeparation(),
                    recomendation.getObservations()
            };
            saveRecord(SupportRecomendationTable.RECOMENDATION_DATABASE_TABLE, recommendationNames, recomendationValues);
        }

        //Save the related pictures urls
        List<Uri> pictureList = newSkavaEntity.getPictureUriList();
        for (Integer index = 0; index < pictureList.size(); index++) {
            Uri uri = pictureList.get(index);
            if (null == uri) {
                continue;
            }
            String[] resourcesNames = new String[]{
                    ExternalResourcesTable.ASSESSMENT_CODE_COLUMN,
                    ExternalResourcesTable.RESOURCE_TYPE_COLUMN,
                    ExternalResourcesTable.RESOURCE_ORDINAL,
                    ExternalResourcesTable.RESOURCE_URL_COLUMN
            };
            Object[] resourcesValues = new Object[]{
                    newSkavaEntity.getCode(),
                    "PICTURE",
                    index,
                    uri.getPath()
            };
            saveRecord(ExternalResourcesTable.EXTERNAL_RESOURCES_DATABASE_TABLE, resourcesNames, resourcesValues);
        }

    }

    @Override
    public boolean deleteAssessment(String code) {
        return false;
    }
}
