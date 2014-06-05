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
import com.metric.skava.data.dao.LocalAssessmentDAO;
import com.metric.skava.data.dao.LocalDiscontinuityFamilyDAO;
import com.metric.skava.data.dao.LocalQCalculationDAO;
import com.metric.skava.data.dao.LocalRMRCalculationDAO;
import com.metric.skava.data.dao.LocalSupportRecommendationDAO;
import com.metric.skava.data.dao.LocalTunnelFaceDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.helper.AssessmentBuilder4SqlLite;
import com.metric.skava.data.dao.impl.sqllite.table.AssessmentTable;
import com.metric.skava.data.dao.impl.sqllite.table.ExternalResourcesTable;
import com.metric.skava.discontinuities.model.DiscontinuityFamily;
import com.metric.skava.instructions.model.SupportRecommendation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/18/14.
 */
public class AssessmentDAOsqlLiteImpl extends SqlLiteBaseIdentifiableEntityDAO<Assessment> implements LocalAssessmentDAO {

    private LocalTunnelFaceDAO mLocalTunnelFaceDAO;
    private LocalDiscontinuityFamilyDAO mLocalDiscontinuityFamilyDAO;
    private LocalQCalculationDAO mLocalQCalculationDAO;
    private LocalRMRCalculationDAO mLocalRMRCalculationDAO;
    private LocalSupportRecommendationDAO mLocalSupportRecommendationDAO;



    private AssessmentBuilder4SqlLite assessmentBuilder;

    public AssessmentDAOsqlLiteImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        assessmentBuilder = new AssessmentBuilder4SqlLite(mContext, skavaContext);
        mLocalTunnelFaceDAO = getDAOFactory().getLocalTunnelFaceDAO();
        mLocalDiscontinuityFamilyDAO = getDAOFactory().getLocalDiscontinuityFamilyDAO();
        mLocalQCalculationDAO = getDAOFactory().getLocalQCalculationDAO();
        mLocalRMRCalculationDAO = getDAOFactory().getLocalRMRCalculationDAO();
        mLocalSupportRecommendationDAO = getDAOFactory().getLocalSupportRecommendationDAO();
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

            SupportRecommendation recomendation = mLocalSupportRecommendationDAO.getSupportRecommendation(assessmentCode);
            reconstructedAssessment.setRecomendation(recomendation);

            List<Uri> resourceList = getResourcesByAssessmentCode(assessmentCode);
            reconstructedAssessment.setPictureUriList(resourceList);

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
                    AssessmentTable.INITIAL_CHAINAGE_COLUMN,
                    AssessmentTable.FINAL_CHAINAGE_COLUMN,
                    AssessmentTable.REFERENCE_CHAINAGE_COLUMN,
                    AssessmentTable.ADVANCE_COLUMN,
                    AssessmentTable.ACCUM_ADVANCE_COLUMN,
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
                    DateDataFormat.formatDateTimeAsLong(newSkavaEntity.getDateTime()),
                    SkavaUtils.isUndefined(newSkavaEntity.getSection()) ? null : newSkavaEntity.getSection().getCode(),
                    SkavaUtils.isUndefined(newSkavaEntity.getMethod()) ? null : newSkavaEntity.getMethod().getCode(),
                    newSkavaEntity.getInitialPeg(),
                    newSkavaEntity.getFinalPeg(),
                    newSkavaEntity.getReferenceChainage(),
                    newSkavaEntity.getCurrentAdvance(),
                    newSkavaEntity.getAccummAdvance(),
                    newSkavaEntity.getOrientation(),
                    newSkavaEntity.getSlope(),
                    SkavaUtils.isUndefined(newSkavaEntity.getFractureType()) ? null : newSkavaEntity.getFractureType().getCode(),
                    newSkavaEntity.getBlockSize(),
                    newSkavaEntity.getNumberOfJoints(),
                    newSkavaEntity.getOutcropDescription(),
                    newSkavaEntity.getRockSampleIdentification(),
                    newSkavaEntity.getSentToCloud()
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
                AssessmentTable.INITIAL_CHAINAGE_COLUMN,
                AssessmentTable.FINAL_CHAINAGE_COLUMN,
                AssessmentTable.REFERENCE_CHAINAGE_COLUMN,
                AssessmentTable.ADVANCE_COLUMN,
                AssessmentTable.ACCUM_ADVANCE_COLUMN,
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
                DateDataFormat.formatDateTimeAsLong(newSkavaEntity.getDateTime()),
                SkavaUtils.isUndefined(newSkavaEntity.getSection()) ? null : newSkavaEntity.getSection().getCode(),
                SkavaUtils.isUndefined(newSkavaEntity.getMethod()) ? null : newSkavaEntity.getMethod().getCode(),
                newSkavaEntity.getInitialPeg(),
                newSkavaEntity.getFinalPeg(),
                newSkavaEntity.getReferenceChainage(),
                newSkavaEntity.getCurrentAdvance(),
                newSkavaEntity.getAccummAdvance(),
                newSkavaEntity.getOrientation(),
                newSkavaEntity.getSlope(),
                SkavaUtils.isUndefined(newSkavaEntity.getFractureType()) ? null : newSkavaEntity.getFractureType().getCode(),
                newSkavaEntity.getBlockSize(),
                newSkavaEntity.getNumberOfJoints(),
                newSkavaEntity.getOutcropDescription(),
                newSkavaEntity.getRockSampleIdentification(),
                newSkavaEntity.getSentToCloud()
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
//        if (qCalculation.isComplete()){
            mLocalQCalculationDAO.saveQCalculation(newSkavaEntity.getCode(), qCalculation);
//        }

        // Save the related RMR Calculation
        RMR_Calculation rmrCalculation = newSkavaEntity.getRmrCalculation();
//        if (rmrCalculation.isComplete()){
            mLocalRMRCalculationDAO.saveRMRCalculation(newSkavaEntity.getCode(), rmrCalculation);
//        }

        //Save the related recommendation
        SupportRecommendation recommendation = newSkavaEntity.getRecomendation();
        mLocalSupportRecommendationDAO.saveSupportRecommendation(newSkavaEntity.getCode(), recommendation);


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
        return deleteIdentifiableEntity(AssessmentTable.ASSESSMENT_DATABASE_TABLE, code);
    }


    @Override
    public int deleteAllAssessments() {
        return deleteAllPersistentEntities(AssessmentTable.ASSESSMENT_DATABASE_TABLE);
    }


}
