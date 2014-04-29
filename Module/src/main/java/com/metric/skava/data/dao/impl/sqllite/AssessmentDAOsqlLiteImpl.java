package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.metric.skava.app.database.utils.CursorUtils;
import com.metric.skava.app.model.Assessment;
import com.metric.skava.app.model.TunnelFace;
import com.metric.skava.app.model.User;
import com.metric.skava.app.util.DateDataFormat;
import com.metric.skava.calculator.barton.model.Q_Calculation;
import com.metric.skava.calculator.rmr.model.RMR_Calculation;
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.data.dao.LocalAssessmentDAO;
import com.metric.skava.data.dao.LocalPermissionDAO;
import com.metric.skava.data.dao.LocalTunnelFaceDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.helper.AssessmentBuilder4SqlLite;
import com.metric.skava.data.dao.impl.sqllite.table.AssessmentTable;
import com.metric.skava.data.dao.impl.sqllite.table.DiscontinuityFamilyTable;
import com.metric.skava.data.dao.impl.sqllite.table.ExternalResourcesTable;
import com.metric.skava.data.dao.impl.sqllite.table.QCalculationTable;
import com.metric.skava.data.dao.impl.sqllite.table.RMRCalculationTable;
import com.metric.skava.data.dao.impl.sqllite.table.SupportRecomendationTable;
import com.metric.skava.discontinuities.model.DiscontinuityFamily;
import com.metric.skava.instructions.model.ArchType;
import com.metric.skava.instructions.model.BoltType;
import com.metric.skava.instructions.model.Coverage;
import com.metric.skava.instructions.model.MeshType;
import com.metric.skava.instructions.model.ShotcreteType;
import com.metric.skava.instructions.model.SupportRecomendation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/18/14.
 */
public class AssessmentDAOsqlLiteImpl extends SqlLiteBaseIdentifiableEntityDAO<Assessment> implements LocalAssessmentDAO {

    private Context mContext;
    private LocalPermissionDAO mLocalPermissionDAO;
    private LocalTunnelFaceDAO mLocalTunnelFaceDAO;
    private AssessmentBuilder4SqlLite assessmentBuilder;

    public Context getContext() {
        return mContext;
    }

    public AssessmentDAOsqlLiteImpl(Context context) throws DAOException {
        super(context);
        mContext = context;
        assessmentBuilder = new AssessmentBuilder4SqlLite(mContext);
        mLocalPermissionDAO = DAOFactory.getInstance(context).getLocalPermissionDAO(DAOFactory.Flavour.SQLLITE);
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
            Assessment newInstance = assessmentBuilder.buildAssessmentFromCursorRecord(cursor);
            //Now the associated support recommendation, Q and RMR calculations, Discontinuity Systems, etc
            // TODO: Uncomment when fixed
//            SupportRecomendation recomendation = getRecommendationByAssessmentCode(newInstance.getCode());
//            newInstance.setRecomendation(recomendation);
//            Q_Calculation qCalculation = getQCalculationByAssessmentCode(newInstance.getCode());
//            newInstance.setQCalculation(qCalculation);
//            newInstance.setRmrCalculation(rmrCalculation)
//            List<Uri> resourceList = getResourcesByAssessmentCode(newInstance.getCode());
  //          newInstance.setPictureUriList(resourceList);
            list.add(newInstance);
        }
        return list;
    }

    private List<Uri> getResourcesByAssessmentCode(String code) {
        Cursor cursor = getRecordsFilteredByColumn(ExternalResourcesTable.EXTERNAL_RESOURCES_DATABASE_TABLE, ExternalResourcesTable.ASSESSMENT_CODE_COLUMN, code, null);
        List<Uri> list = assembleResourceList(cursor);
        return list;
    }

    private List<Uri> assembleResourceList(Cursor cursor) {
        return null;
    }

    private Q_Calculation getQCalculationByAssessmentCode(String code) {
        Cursor cursor = getRecordsFilteredByColumn(QCalculationTable.Q_CALCULATION_DATABASE_TABLE, QCalculationTable.ASSESSMENT_CODE_COLUMN, code, null);
        Q_Calculation calculation = assembleCalculation(cursor);
        return calculation;
    }

    private Q_Calculation assembleCalculation(Cursor cursor) {
        return null;
    }

    private SupportRecomendation getRecommendationByAssessmentCode(String code) throws DAOException {
        Cursor cursor = getRecordsFilteredByColumn(SupportRecomendationTable.RECOMENDATION_DATABASE_TABLE, QCalculationTable.ASSESSMENT_CODE_COLUMN, code, null);
        SupportRecomendation recomendation = assembleSupportRecommendation(cursor);
        return recomendation;
    }

    private SupportRecomendation assembleSupportRecommendation(Cursor cursor) throws DAOException {
        List<SupportRecomendation> resultList = new ArrayList<SupportRecomendation>();
        DAOFactory daoFactory = DAOFactory.getInstance(mContext);
        while (cursor.moveToNext()) {
            String assessment = CursorUtils.getString(SupportRecomendationTable.ASSESSMENT_CODE_COLUMN, cursor);

            String archTypeCode = CursorUtils.getString(SupportRecomendationTable.ARCH_TYPE_CODE_COLUMN, cursor);
            ArchType archType = daoFactory.getArchTypeDAO().getArchTypeByCode(archTypeCode);

            String boltTypeCode = CursorUtils.getString(SupportRecomendationTable.BOLT_TYPE_CODE_COLUMN, cursor);
            BoltType boltType = daoFactory.getBoltTypeDAO().getBoltTypeByCode(boltTypeCode);

            Double boltDiameter = CursorUtils.getDouble(SupportRecomendationTable.BOLT_DIAMETER_COLUMN, cursor);
            Double boltLength = CursorUtils.getDouble(SupportRecomendationTable.BOLT_LENGTH_COLUMN, cursor);

            String coverageCode = CursorUtils.getString(SupportRecomendationTable.COVERAGE_CODE_COLUMN, cursor);
            Coverage coverage = daoFactory.getCoverageDAO().getCoverageByCode(coverageCode);

            String meshTypeCode = CursorUtils.getString(SupportRecomendationTable.MESH_TYPE_CODE_COLUMN, cursor);
            MeshType meshType = daoFactory.getMeshTypeDAO().getMeshTypeByCode(meshTypeCode);

            String shotcreteTypeCode = CursorUtils.getString(SupportRecomendationTable.SHOTCRETE_TYPE_CODE_COLUMN, cursor);
            ShotcreteType shotcreteType = daoFactory.getShotcreteTypeDAO().getShotcreteTypeByCode(shotcreteTypeCode);

            Double separation = CursorUtils.getDouble(SupportRecomendationTable.SEPARATION_COLUMN, cursor);
            Double thickness = CursorUtils.getDouble(SupportRecomendationTable.THICKNESS_COLUMN, cursor);
            String observations = CursorUtils.getString(SupportRecomendationTable.OBSERVATIONS_COLUMN, cursor);


            SupportRecomendation newInstance = new SupportRecomendation();

            newInstance.setArchType(archType);
            newInstance.setBoltDiameter(boltDiameter);
            newInstance.setBoltLength(boltLength);
            newInstance.setBoltType(boltType);
            newInstance.setCoverage(coverage);
            newInstance.setMeshType(meshType);
            newInstance.setObservations(observations);
            newInstance.setSeparation(separation);
            newInstance.setShotcreteType(shotcreteType);
            newInstance.setThickness(thickness);
            resultList.add(newInstance);
        }
        //TODO This should be one and only one Check is not null and stuff
        if (!resultList.isEmpty()){
            return resultList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public void send(Assessment assessment) throws DAOException {
        savePersistentEntity(AssessmentTable.ASSESSMENT_DATABASE_TABLE, assessment);
    }


    @Override
    public void saveDraft(Assessment assessment) throws DAOException {
        savePersistentEntity(AssessmentTable.ASSESSMENT_DATABASE_TABLE, assessment);
    }

    @Override
    protected void savePersistentEntity(java.lang.String tableName, Assessment newSkavaEntity) throws DAOException {
        String[] names = new String[]{
                AssessmentTable.GLOBAL_KEY_ID,
                AssessmentTable.CODE_COLUMN,
                AssessmentTable.INTERNAL_CODE_COLUMN,
                AssessmentTable.GEOLOGIST_CODE_COLUMN,
                AssessmentTable.TUNEL_FACE_CODE_COLUMN,
                AssessmentTable.DATE_COLUMN,
                AssessmentTable.EXCAVATION_SECTION_CODE_COLUMN,
                AssessmentTable.EXCAVATION_METHOD_CODE_COLUMN,
                AssessmentTable.PK_INITIAL_COLUMN,
                AssessmentTable.PK_FINAL_COLUMN,
                AssessmentTable.ADVANCE_ACUMM_COLUMN,
                AssessmentTable.ORIENTATION_COLUMN,
                AssessmentTable.SLOPE_COLUMN,
                AssessmentTable.FRACTURE_TYPE_CODE_COLUMN,
                AssessmentTable.BLOCKS_SIZE_COLUMN,
                AssessmentTable.NUMBER_JOINTS_COLUMN,
                AssessmentTable.OUTCROP_COLUMN
        };

        Object[] values = new Object[]{
                newSkavaEntity.get_id(),
                newSkavaEntity.getCode(),
                newSkavaEntity.getInternalCode(),
                newSkavaEntity.getGeologist().getCode(),
                newSkavaEntity.getFace().getCode(),
                DateDataFormat.formatDateAsLong(newSkavaEntity.getDate()),
                newSkavaEntity.getSection().getCode(),
                newSkavaEntity.getMethod().getCode(),
                newSkavaEntity.getInitialPeg(),
                newSkavaEntity.getFinalPeg(),
                newSkavaEntity.getAccummAdvance(),
                newSkavaEntity.getOrientation(),
                newSkavaEntity.getSlope(),
                newSkavaEntity.getFractureType() != null ? newSkavaEntity.getFractureType().getCode() : null,
                newSkavaEntity.getBlockSize(),
                newSkavaEntity.getNumberOfJoints(),
                newSkavaEntity.getOutcropDescription()
        };

        Long assesmentId = saveRecord(tableName, names, values);
        newSkavaEntity.set_id(assesmentId);

        //Save the related recommendation
        String[] recommendationNames = new String[]{
                SupportRecomendationTable.ASSESSMENT_CODE_COLUMN,
                SupportRecomendationTable.BOLT_TYPE_CODE_COLUMN,
                SupportRecomendationTable.BOLT_DIAMETER_COLUMN,
                SupportRecomendationTable.BOLT_LENGTH_COLUMN,
                SupportRecomendationTable.SHOTCRETE_TYPE_CODE_COLUMN,
                SupportRecomendationTable.THICKNESS_COLUMN,
                SupportRecomendationTable.MESH_TYPE_CODE_COLUMN,
                SupportRecomendationTable.COVERAGE_CODE_COLUMN,
                SupportRecomendationTable.ARCH_TYPE_CODE_COLUMN,
                SupportRecomendationTable.SEPARATION_COLUMN
        };
//        SupportRecomendation recomendation = newSkavaEntity.getRecomendation();
//        Object[] recomendationValues = new Object[]{
//                newSkavaEntity.getCode(),
//                recomendation.getBoltType().getCode(),
//                recomendation.getBoltDiameter(),
//                recomendation.getBoltLength(),
//                recomendation.getShotcreteType().getCode(),
//                recomendation.getThickness(),
//                recomendation.getMeshType().getCode(),
//                recomendation.getCoverage(),
//                recomendation.getArchType().getCode(),
//                recomendation.getSeparation()
//        };
//        saveRecord(SupportRecomendationTable.RECOMENDATION_DATABASE_TABLE, recommendationNames, recomendationValues);

        // Save the related Q Calculation
        Q_Calculation qCalculation = newSkavaEntity.getQCalculation();
        if (qCalculation != null) {
            String[] qCalculationNames = new String[]{
                    QCalculationTable.GLOBAL_KEY_ID,
                    QCalculationTable.ASSESSMENT_CODE_COLUMN,
                    QCalculationTable.RQD_COLUMN,
                    QCalculationTable.Jn_CODE_COLUMN,
                    QCalculationTable.Jr_CODE_COLUMN,
                    QCalculationTable.Ja_CODE_COLUMN,
                    QCalculationTable.Jw_CODE_COLUMN,
                    QCalculationTable.SRF_CODE_COLUMN,
                    QCalculationTable.Q_COLUMN
            };
            Object[] qCalculationValues = new Object[]{
                    qCalculation.get_id(),
                    newSkavaEntity.getCode(),
                    qCalculation.getRqd().getValue(),
                    qCalculation.getJn().getKey(),
                    qCalculation.getJr().getKey(),
                    qCalculation.getJa().getKey(),
                    qCalculation.getJw().getKey(),
                    qCalculation.getSrf().getKey(),
                    qCalculation.getQResult().getQBarton(),
            };
            Long qCalculationId = saveRecord(QCalculationTable.Q_CALCULATION_DATABASE_TABLE, qCalculationNames, qCalculationValues);
            qCalculation.set_id(qCalculationId);
        }

        RMR_Calculation rmrCalculation = newSkavaEntity.getRmrCalculation();
        if (rmrCalculation != null) {
            //Save the related RMR Calculation
            String[] rmrCalculationNames = new String[]{
                    RMRCalculationTable.GLOBAL_KEY_ID,
                    RMRCalculationTable.ASSESSMENT_CODE_COLUMN,
                    RMRCalculationTable.STRENGTHOFROCK_CODE_COLUMN,
                    RMRCalculationTable.RQD_RMR_CODE_COLUMN,
                    RMRCalculationTable.SPACING_CODE_COLUMN,
                    RMRCalculationTable.PERSISTENCE_CODE_COLUMN,
                    RMRCalculationTable.APERTURE_CODE_COLUMN,
                    RMRCalculationTable.ROUGHNESS_CODE_COLUMN,
                    RMRCalculationTable.INFILLING_CODE_COLUMN,
                    RMRCalculationTable.WEATHERING_CODE_COLUMN,
                    RMRCalculationTable.GROUNDWATER_CODE_COLUMN
            };

            Object[] rmrCalculationValues = new Object[]{
                    rmrCalculation.get_id(),
                    newSkavaEntity.getCode(),
                    rmrCalculation.getStrengthOfRock().getKey(),
                    rmrCalculation.getRqd().getKey(),
                    rmrCalculation.getSpacing().getKey(),
                    rmrCalculation.getPersistence().getKey(),
                    rmrCalculation.getAperture().getKey(),
                    rmrCalculation.getRoughness().getKey(),
                    rmrCalculation.getInfilling().getKey(),
                    rmrCalculation.getWeathering().getKey(),
                    rmrCalculation.getGroundwater().getKey()
            };
            Long rmrCalculationId = saveRecord(RMRCalculationTable.RMR_CALCULATION_DATABASE_TABLE, rmrCalculationNames, rmrCalculationValues);
            rmrCalculation.set_id(rmrCalculationId);
        }

        // Save Discontinuity Families
        List<DiscontinuityFamily> discontinuitySystem = newSkavaEntity.getDiscontinuitySystem();
        if (discontinuitySystem != null) {
            for(DiscontinuityFamily df: discontinuitySystem) {
                // TODO: Cambiar la segunda condicion por algo que se parezca al Null Pattern
                if (df == null || !df.isComplete()) {
                    continue;
                }

                String[] discontinuitySystemNames = new String[]{
                        DiscontinuityFamilyTable.GLOBAL_KEY_ID,
                        DiscontinuityFamilyTable.ASSESSMENT_CODE_COLUMN,
                        DiscontinuityFamilyTable.NUMBER_CODE_COLUMN,
                        DiscontinuityFamilyTable.TYPE_CODE_COLUMN,
                        DiscontinuityFamilyTable.RELEVANCE_CODE_COLUMN,
                        DiscontinuityFamilyTable.DIPDIRDEGREES_CODE_COLUMN,
                        DiscontinuityFamilyTable.DIPDEGREES_CODE_COLUMN,
                        DiscontinuityFamilyTable.SHAPE_CODE_COLUMN,
                        DiscontinuityFamilyTable.SPACING_CODE_COLUMN,
                        DiscontinuityFamilyTable.ROUGHNESS_CODE_COLUMN,
                        DiscontinuityFamilyTable.WEATHERING_CODE_COLUMN,
                        DiscontinuityFamilyTable.DISCONTINUITYWATER_CODE_COLUMN,
                        DiscontinuityFamilyTable.PERSISTENCE_CODE_COLUMN,
                        DiscontinuityFamilyTable.APERTURE_CODE_COLUMN,
                        DiscontinuityFamilyTable.INFILLING_CODE_COLUMN,
                        DiscontinuityFamilyTable.JA_CODE_COLUMN,
                        DiscontinuityFamilyTable.JR_CODE_COLUMN
                };

                Object[] discontinuitySystemValues = new Object[]{
                        df.get_id(),
                        newSkavaEntity.getCode(),
                        df.getNumber(),
                        df.getType().getCode(),
                        df.getRelevance().getCode(),
                        df.getDipDegrees(),
                        df.getDipDirDegrees(),
                        df.getShape().getCode(),
                        df.getSpacing().getKey(),
                        df.getRoughness().getKey(),
                        df.getWeathering().getKey(),
                        df.getWater().getCode(),
                        df.getPersistence().getKey(),
                        df.getAperture().getKey(),
                        df.getInfilling().getKey(),
                        df.getJa().getKey(),
                        df.getJr().getKey()
                };

                Long dfId = saveRecord(DiscontinuityFamilyTable.DISCONTINUITY_FAMILY_DATABASE_TABLE, discontinuitySystemNames, discontinuitySystemValues);
                df.set_id(dfId);
            }
        }

        //Save the related pictures urls
        List<Uri> pictureList = newSkavaEntity.getPictureUriList();
        for (Uri uri : pictureList) {
            if (null == uri) {
                continue;
            }
            String[] resourcesNames = new String[]{
                    ExternalResourcesTable.RESOURCE_TYPE_COLUMN,
                    ExternalResourcesTable.RESOURCE_URL_COLUMN
            };
            String[] resourcesValues = new String[]{
                    "PICTURE",
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
