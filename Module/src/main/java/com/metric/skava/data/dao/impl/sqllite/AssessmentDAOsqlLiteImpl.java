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
import com.metric.skava.pictures.model.SkavaPicture;
import com.metric.skava.pictures.util.SkavaPictureFilesUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    public List<Assessment> getAllAssessments(String environment, String orderBy) throws DAOException {
        Cursor cursor = getRecordsFilteredByColumn(AssessmentTable.ASSESSMENT_DATABASE_TABLE, AssessmentTable.ENVIRONMENT_COLUMN, environment, orderBy);
        List<Assessment> list = assemblePersistentEntities(cursor);
        cursor.close();
        return list;
    }

    @Override
    public Assessment getAssessment(String code) throws DAOException {
        Cursor cursor = getRecordsFilteredByColumn(AssessmentTable.ASSESSMENT_DATABASE_TABLE, AssessmentTable.CODE_COLUMN, code, null);
        List<Assessment> list = assemblePersistentEntities(cursor);
        if (list.isEmpty()) {
            throw new DAOException("Assessment not found. [Code : " + code + "]");
        }
        if (list.size() > 1) {
            throw new DAOException("Multiple assessment records for same code. [Code : " + code + "]");
        }
        cursor.close();
        return list.get(0);
    }


    public boolean exists(String assessmentCode) throws DAOException{
        Cursor cursor = getRecordsFilteredByColumn(AssessmentTable.ASSESSMENT_DATABASE_TABLE, AssessmentTable.CODE_COLUMN, assessmentCode, null);
        int howMany = cursor.getCount();
        return (howMany > 0);
    }

    @Override
    public Assessment getPreviousAssessment(String assessmentCode) throws DAOException {
        Assessment referenceAssessment = getAssessment(assessmentCode);
        String environment = referenceAssessment.getEnvironment();
        TunnelFace face = referenceAssessment.getFace();
        Double initialPeg = referenceAssessment.getInitialPeg();
        //find the previous assessment (last with same face but previous chainage
        List<Assessment> listAssessments = getAssessmentsByTunnelFace(environment, face);
        //find the nearestFinalPeg (from the set of the other assessments) to the initialPeg of the reference assessment
        //first I'm gonna create a Map with assessmentCode<finalPegs>
        Map<String, Double> finalPegsMap = new HashMap<String, Double>();
        Map<String, Double> initialPegsMap = new HashMap<String, Double>();
        for (Assessment currAssessment : listAssessments) {
            initialPegsMap.put(currAssessment.getCode(), currAssessment.getInitialPeg());
            finalPegsMap.put(currAssessment.getCode(), currAssessment.getFinalPeg());
        }
        String codeNearestAsssessment = null;
        Double closestInitial = closest(initialPeg, initialPegsMap.values().toArray(new Double[]{}));
        Double closestFinal = closest(initialPeg, finalPegsMap.values().toArray(new Double[]{}));
        Double winner = closest(initialPeg, closestInitial, closestFinal);
        if (initialPegsMap.containsValue(winner)) {
            Set<Map.Entry<String, Double>> entrySet = initialPegsMap.entrySet();
            for (Map.Entry<String, Double> stringDoubleEntry : entrySet) {
                String key = stringDoubleEntry.getKey();
                Double value = stringDoubleEntry.getValue();
                if (value.equals(winner)) {
                    codeNearestAsssessment = key;
                    break;
                }
            }
        }
        return getAssessment(codeNearestAsssessment);
    }

    public static double closest(double find, Double... values){
        double closest = values[0];
        double distance = Math.abs(closest - find);
        for(double i: values) {
            double distanceI = Math.abs(i - find);
            if(distance > distanceI) {
                closest = i;
                distance = distanceI;
            }
        }
        return closest;
    }


    @Override
    public List<Assessment> getAssessmentsByUser(String environment, User user) throws DAOException {
        List<Assessment> allAssessments = new ArrayList<Assessment>();
        List<TunnelFace> facesGranted = mLocalTunnelFaceDAO.getTunnelFacesByUser(environment, user);
        //find the last five active assessment for each of those faces
        for (TunnelFace grantedFace : facesGranted) {
            List<Assessment> grantedFaceAssessments = getAssessmentsByTunnelFace(environment, grantedFace);
            Collections.sort(grantedFaceAssessments, new Comparator<Assessment>() {
                @Override
                public int compare(Assessment lhs, Assessment rhs) {
                    return Long.compare(rhs.getDateTime().getTime().getTime(), lhs.getDateTime().getTime().getTime());
                }
            });
            allAssessments.addAll(grantedFaceAssessments);
        }
        return allAssessments;
    }

    @Override
    public List<Assessment> getAssessmentsByTunnelFace(String environment, TunnelFace face) throws DAOException {
        String[] names = new String[]{AssessmentTable.ENVIRONMENT_COLUMN ,AssessmentTable.TUNEL_FACE_CODE_COLUMN};
        String[] values = new String[]{environment, face.getCode()};
        Cursor cursor = getRecordsFilteredByColumns(AssessmentTable.ASSESSMENT_DATABASE_TABLE, names, values, AssessmentTable.DATE_COLUMN);
        List<Assessment> list = assemblePersistentEntities(cursor);
        cursor.close();
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

            List<SkavaPicture> resourceList = getPicturesByAssessmentCode(assessmentCode);
            reconstructedAssessment.setPicturesList(resourceList);

            Uri expandedView = getExpandedTunnelViewByAssessmentCode(assessmentCode);
            if (expandedView != null) {
                reconstructedAssessment.setTunnelExpandedView(expandedView);
            }

            list.add(reconstructedAssessment);
        }
        return list;
    }

    private Uri getExpandedTunnelViewByAssessmentCode(String code) {
        String[] columns = new String[] {ExternalResourcesTable.ASSESSMENT_CODE_COLUMN, ExternalResourcesTable.RESOURCE_TAG_COLUMN} ;
        Object[] values = new Object[] {code, SkavaPicture.PictureTag.EXPANDED_TUNNEL.name()};
        Cursor cursor = getRecordsFilteredByColumns(ExternalResourcesTable.EXTERNAL_RESOURCES_DATABASE_TABLE, columns, values, null);
        Uri expandedTunnel = null;
        while (cursor.moveToNext()) {
            String uriString = CursorUtils.getString(ExternalResourcesTable.RESOURCE_URL_COLUMN, cursor);
            expandedTunnel = Uri.parse(uriString);
        }
        cursor.close();
        return expandedTunnel;
    }

    private List<SkavaPicture> getPicturesByAssessmentCode(String code) {
        Cursor cursor = getRecordsFilteredByColumn(ExternalResourcesTable.EXTERNAL_RESOURCES_DATABASE_TABLE, ExternalResourcesTable.ASSESSMENT_CODE_COLUMN, code, ExternalResourcesTable.RESOURCE_ORDINAL);
        List<SkavaPicture> list = assemblePicturesList(cursor);
        cursor.close();
        return list;
    }


    private List<SkavaPicture> assemblePicturesList(Cursor cursor) {
        List<SkavaPicture> result = new ArrayList<SkavaPicture>(5);
        result.add(null); //0
        result.add(null);
        result.add(null); //2
        result.add(null);
        result.add(null); //4
        result.add(null);
        result.add(null); //6
        result.add(null);
        while (cursor.moveToNext()) {
            String uriString = CursorUtils.getString(ExternalResourcesTable.RESOURCE_URL_COLUMN, cursor);
            String pictureTagAsString = CursorUtils.getString(ExternalResourcesTable.RESOURCE_TAG_COLUMN, cursor);
            SkavaPicture.PictureTag pictureTag = SkavaPicture.PictureTag.valueOf(pictureTagAsString);
            Integer index = CursorUtils.getInt(ExternalResourcesTable.RESOURCE_ORDINAL, cursor);
            Uri pictureLocation = Uri.parse(uriString);
            SkavaPicture picture = new SkavaPicture(pictureTag, pictureLocation, index==0);
            int resourceIndex = 0;
            switch (pictureTag){
                case FACE:
                    resourceIndex = 0;
                    break;
                case LEFT:
                    resourceIndex = 2;
                    break;
                case RIGHT:
                    resourceIndex = 4;
                    break;
                case ROOF:
                    resourceIndex = 6;
                    break;
                case EXTRA:
                    resourceIndex = 8;
                    break;
                case EXPANDED_TUNNEL:
                    continue;
            }
            resourceIndex += index;
            if (resourceIndex < 8) {
                result.set(resourceIndex, picture);
            } else {
                result.add(picture);
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
                    AssessmentTable.ENVIRONMENT_COLUMN,
                    AssessmentTable.CODE_COLUMN,
                    AssessmentTable.DEVICE_ID_COLUMN,
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
                    AssessmentTable.DATA_SENT_STATUS_COLUMN,
                    AssessmentTable.FILES_SENT_STATUS_COLUMN,
                    AssessmentTable.SAVING_STATUS_COLUMN
            };

            Object[] values = new Object[]{
                    newSkavaEntity.getEnvironment(),
                    newSkavaEntity.getCode(),
                    newSkavaEntity.getOriginatorDeviceID(),
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
                    newSkavaEntity.getDataSentStatus(),
                    newSkavaEntity.getPicsSentStatus(),
                    newSkavaEntity.getSavedStatus()
            };
            Long assesmentId = saveRecord(AssessmentTable.ASSESSMENT_DATABASE_TABLE, names, values);
            newSkavaEntity.set_id(assesmentId);
        }
    }

    @Override
    protected void savePersistentEntity(java.lang.String tableName, Assessment newSkavaEntity) throws DAOException {
        String[] names = new String[]{
                AssessmentTable.ENVIRONMENT_COLUMN,
                AssessmentTable.CODE_COLUMN,
                AssessmentTable.DEVICE_ID_COLUMN,
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
                AssessmentTable.DATA_SENT_STATUS_COLUMN,
                AssessmentTable.FILES_SENT_STATUS_COLUMN,
                AssessmentTable.SAVING_STATUS_COLUMN
        };

        Object[] values = new Object[]{
                newSkavaEntity.getEnvironment(),
                newSkavaEntity.getCode(),
                newSkavaEntity.getOriginatorDeviceID(),
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
                newSkavaEntity.getDataSentStatus(),
                newSkavaEntity.getPicsSentStatus(),
                newSkavaEntity.getSavedStatus()
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
        SupportRecommendation recommendation = newSkavaEntity.getRecomendation();
        mLocalSupportRecommendationDAO.saveSupportRecommendation(newSkavaEntity.getCode(), recommendation);

        //Save the related pictures urls
        List<SkavaPicture> pictureList = newSkavaEntity.getPicturesList();
        for (Integer index = 0; index < pictureList.size(); index++) {
            SkavaPicture currPicture = pictureList.get(index);
            if (null == currPicture || currPicture.getPictureLocation() == null) {
                continue;
            }
            String[] resourcesNames = new String[]{
                    ExternalResourcesTable.ASSESSMENT_CODE_COLUMN,
                    ExternalResourcesTable.RESOURCE_TYPE_COLUMN,
                    ExternalResourcesTable.RESOURCE_TAG_COLUMN,
                    ExternalResourcesTable.RESOURCE_ORDINAL,
                    ExternalResourcesTable.RESOURCE_URL_COLUMN
            };
            Object[] resourcesValues = new Object[]{
                    newSkavaEntity.getCode(),
                    ExternalResourcesTable.PICTURE_RESOURCE_TYPE,
                    currPicture.getPictureTag(),
                    currPicture.isOriginal()? 0:1,
                    //currPicture.getPictureLocation().getPath() is not enough,
                    // it losses the Uri scheme part ( file:/// in most our cases)
                    // so toString is better here
                    currPicture.getPictureLocation().toString()
            };
            saveRecord(ExternalResourcesTable.EXTERNAL_RESOURCES_DATABASE_TABLE, resourcesNames, resourcesValues);
        }

        //Save the tunnel expanded view if any
        Uri expandedView = newSkavaEntity.getTunnelExpandedView();
        if (expandedView != null) {
            String[] resourcesNames = new String[]{
                    ExternalResourcesTable.ASSESSMENT_CODE_COLUMN,
                    ExternalResourcesTable.RESOURCE_TYPE_COLUMN,
                    ExternalResourcesTable.RESOURCE_TAG_COLUMN,
                    ExternalResourcesTable.RESOURCE_ORDINAL,
                    ExternalResourcesTable.RESOURCE_URL_COLUMN
            };
            Object[] resourcesValues = new Object[]{
                    newSkavaEntity.getCode(),
                    ExternalResourcesTable.PICTURE_RESOURCE_TYPE,
                    SkavaPicture.PictureTag.EXPANDED_TUNNEL,
                    0, //not used in this case
                    expandedView.toString()
            };
            saveRecord(ExternalResourcesTable.EXTERNAL_RESOURCES_DATABASE_TABLE, resourcesNames, resourcesValues);
        }

    }

    @Override
    public boolean deleteAssessment(String code) throws DAOException {
        SkavaPictureFilesUtils filesUtils = new SkavaPictureFilesUtils(mContext);
        File skavaPictureStorageDir = filesUtils.getSkavaPicturesFolder();
        File assessmentPictureFolder = new File(skavaPictureStorageDir, code);
        filesUtils.deleteRecursively(assessmentPictureFolder);

        boolean deletedQ = mLocalQCalculationDAO.deleteQCalculation(code);
        boolean deletedRMR = mLocalRMRCalculationDAO.deleteRMRCalculation(code);
        int discontinuities = mLocalDiscontinuityFamilyDAO.deleteAllDiscontinuityFamilies(code);
        boolean deletedRecommendations = mLocalSupportRecommendationDAO.deleteSupportRecommendation(code);
        int picResources = deletePersistentEntitiesFilteredByColumn(ExternalResourcesTable.EXTERNAL_RESOURCES_DATABASE_TABLE, ExternalResourcesTable.ASSESSMENT_CODE_COLUMN, code);
        boolean deletedAssessment = deleteIdentifiableEntity(AssessmentTable.ASSESSMENT_DATABASE_TABLE, code);

        return deletedAssessment && deletedQ && deletedRMR && deletedRecommendations && (discontinuities != -1) && (picResources != -1);

    }


    @Override
    public int deleteAllAssessments(String environment) throws DAOException {
        // It's not enough to delete the records on Assessment table. That's why
        // deleteAllPersistentEntities(AssessmentTable.ASSESSMENT_DATABASE_TABLE)
        // isn't used. Instead of it, iterate on assessment records and call deletion
        // so pictures and everything related will also be removed
        Cursor cursor = getRecordsFilteredByColumn(AssessmentTable.ASSESSMENT_DATABASE_TABLE, AssessmentTable.ENVIRONMENT_COLUMN, environment, null);
        int i = 0;
        while (cursor.moveToNext()) {
            String assessmentCode = CursorUtils.getString(AssessmentTable.CODE_COLUMN, cursor);
            deleteAssessment(assessmentCode);
            i++;
        }
        return i;
    }


}
