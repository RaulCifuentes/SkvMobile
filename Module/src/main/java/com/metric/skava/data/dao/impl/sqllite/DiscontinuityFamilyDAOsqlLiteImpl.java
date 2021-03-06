package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.database.utils.CursorUtils;
import com.metric.skava.app.util.SkavaUtils;
import com.metric.skava.calculator.barton.model.Ja;
import com.metric.skava.calculator.barton.model.Jr;
import com.metric.skava.calculator.rmr.model.Aperture;
import com.metric.skava.calculator.rmr.model.Infilling;
import com.metric.skava.calculator.rmr.model.Persistence;
import com.metric.skava.calculator.rmr.model.Roughness;
import com.metric.skava.calculator.rmr.model.Spacing;
import com.metric.skava.calculator.rmr.model.Weathering;
import com.metric.skava.data.dao.LocalDiscontinuityFamilyDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.table.DiscontinuityFamilyTable;
import com.metric.skava.discontinuities.model.DiscontinuityFamily;
import com.metric.skava.discontinuities.model.DiscontinuityRelevance;
import com.metric.skava.discontinuities.model.DiscontinuityShape;
import com.metric.skava.discontinuities.model.DiscontinuityType;
import com.metric.skava.discontinuities.model.DiscontinuityWater;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/14/14.
 */
public class DiscontinuityFamilyDAOsqlLiteImpl extends SqlLiteBasePersistentEntityDAO<DiscontinuityFamily> implements LocalDiscontinuityFamilyDAO {


    public DiscontinuityFamilyDAOsqlLiteImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
    }



//    protected List<DiscontinuityFamily> assemblePersistentEntities(Cursor cursor) throws DAOException {
//        //Discontinuity System
//        List<DiscontinuityFamily> list = new ArrayList<DiscontinuityFamily>();
//        //add as many families as the cursor retrieve, the others will remain null
//        for(int i=0; i < cursor.getCount(); i++){
//            DiscontinuityFamily df = new DiscontinuityFamily();
//            list.add(df);
//        }
//        while (cursor.moveToNext()) {
//            DiscontinuityFamily newDiscontinuityFamily = new DiscontinuityFamily();
//            newDiscontinuityFamily.setNumber(CursorUtils.getInt(DiscontinuityFamilyTable.NUMBER_COLUMN, cursor));
//
//            String discontinuityTypeCode = CursorUtils.getString(DiscontinuityFamilyTable.TYPE_CODE_COLUMN, cursor);
//            if (discontinuityTypeCode != null) {
//                DiscontinuityType discontinuityType = getDAOFactory().getLocalDiscontinuityTypeDAO().getDiscontinuityTypeByCode(discontinuityTypeCode);
//                newDiscontinuityFamily.setType(discontinuityType);
//            }
//
//            String discontinuityRelevanceCode = CursorUtils.getString(DiscontinuityFamilyTable.RELEVANCE_CODE_COLUMN, cursor);
//            if (discontinuityRelevanceCode != null) {
//                DiscontinuityRelevance discontinuityRelevance = getDAOFactory().getLocalDiscontinuityRelevanceDAO().getDiscontinuityRelevanceByCode(discontinuityRelevanceCode);
//                newDiscontinuityFamily.setRelevance(discontinuityRelevance);
//            }
//
//            newDiscontinuityFamily.setDipDegrees((short) CursorUtils.getInt(DiscontinuityFamilyTable.DIPDEGREES_CODE_COLUMN, cursor));
//            newDiscontinuityFamily.setDipDirDegrees((short) CursorUtils.getInt(DiscontinuityFamilyTable.DIPDIRDEGREES_CODE_COLUMN, cursor));
//
//            String discontinuityShapeCode = CursorUtils.getString(DiscontinuityFamilyTable.SHAPE_CODE_COLUMN, cursor);
//            if (discontinuityShapeCode != null) {
//                DiscontinuityShape discontinuityShape = getDAOFactory().getLocalDiscontinuityShapeDAO().getDiscontinuityShapeByCode(discontinuityShapeCode);
//                newDiscontinuityFamily.setShape(discontinuityShape);
//            }
//
//            String discontinuitySpacingCode = CursorUtils.getString(DiscontinuityFamilyTable.SPACING_CODE_COLUMN, cursor);
//            if (discontinuitySpacingCode != null) {
//                Spacing discontinuitySpacing = getDAOFactory().getLocalSpacingDAO().getSpacingByUniqueCode(discontinuitySpacingCode);
//                newDiscontinuityFamily.setSpacing(discontinuitySpacing);
//            }
//
//            String roughnessCode = CursorUtils.getString(DiscontinuityFamilyTable.ROUGHNESS_CODE_COLUMN, cursor);
//            if (roughnessCode != null) {
//                Roughness roughness = getDAOFactory().getLocalRoughnessDAO().getRoughnessByUniqueCode(roughnessCode);
//                newDiscontinuityFamily.setRoughness(roughness);
//            }
//
//            String weatheringCode = CursorUtils.getString(DiscontinuityFamilyTable.WEATHERING_CODE_COLUMN, cursor);
//            if (weatheringCode != null) {
//                Weathering weathering = getDAOFactory().getLocalWeatheringDAO().getWeatheringByUniqueCode(weatheringCode);
//                newDiscontinuityFamily.setWeathering(weathering);
//            }
//
//            String discontinuityWaterCode = CursorUtils.getString(DiscontinuityFamilyTable.DISCONTINUITYWATER_CODE_COLUMN, cursor);
//            if (discontinuityWaterCode != null) {
//                DiscontinuityWater discontinuityWater = getDAOFactory().getLocalDiscontinuityWaterDAO().getDiscontinuityWaterByCode(discontinuityWaterCode);
//                newDiscontinuityFamily.setWater(discontinuityWater);
//            }
//
//            String persistenceCode = CursorUtils.getString(DiscontinuityFamilyTable.PERSISTENCE_CODE_COLUMN, cursor);
//            if (persistenceCode != null) {
//                Persistence persistence = getDAOFactory().getLocalPersistenceDAO().getPersistenceByUniqueCode(persistenceCode);
//                newDiscontinuityFamily.setPersistence(persistence);
//            }
//
//            String apertureCode = CursorUtils.getString(DiscontinuityFamilyTable.APERTURE_CODE_COLUMN, cursor);
//            if (apertureCode != null) {
//                Aperture aperture = getDAOFactory().getLocalApertureDAO().getApertureByUniqueCode(apertureCode);
//                newDiscontinuityFamily.setAperture(aperture);
//            }
//
//            String infillingCode = CursorUtils.getString(DiscontinuityFamilyTable.INFILLING_CODE_COLUMN, cursor);
//            if (infillingCode != null) {
//                Infilling infilling = getDAOFactory().getLocalInfillingDAO().getInfillingByUniqueCode(infillingCode);
//                newDiscontinuityFamily.setInfilling(infilling);
//            }
//
//            String jaCode = CursorUtils.getString(DiscontinuityFamilyTable.JA_CODE_COLUMN, cursor);
//            if (jaCode != null) {
//                Ja ja = getDAOFactory().getLocalJaDAO().getJaByUniqueCode(jaCode);
//                newDiscontinuityFamily.setJa(ja);
//            }
//
//            String jrCode = CursorUtils.getString(DiscontinuityFamilyTable.JR_CODE_COLUMN, cursor);
//            if (jrCode != null) {
//                Jr jr = getDAOFactory().getLocalJrDAO().getJrByUniqueCode(jrCode);
//                newDiscontinuityFamily.setJr(jr);
//            }
//
//            list.set(newDiscontinuityFamily.getNumber(), newDiscontinuityFamily);
//
//            //complete the set of 7 discontinuites, with empty ones if there no definition
//            for(int j=list.size(); j < 7; j++){
//                DiscontinuityFamily df = new DiscontinuityFamily();
//                df.setNumber(j);
//                list.add(df);
//            }
//        }
//        return list;
//    }


    @Override
    protected List<DiscontinuityFamily> assemblePersistentEntities(Cursor cursor) throws DAOException {
        //Discontinuity System
        List<DiscontinuityFamily> list = new ArrayList<DiscontinuityFamily>();
        for(int i=0; i < 7; i++){
            DiscontinuityFamily df = new DiscontinuityFamily();
            df.setNumber(i);
            list.add(df);
        }

        while (cursor.moveToNext()) {
            DiscontinuityFamily newDiscontinuityFamily = new DiscontinuityFamily();
            newDiscontinuityFamily.setNumber(CursorUtils.getInt(DiscontinuityFamilyTable.NUMBER_COLUMN, cursor));

            String discontinuityTypeCode = CursorUtils.getString(DiscontinuityFamilyTable.TYPE_CODE_COLUMN, cursor);
            if (discontinuityTypeCode != null) {
                DiscontinuityType discontinuityType = getDAOFactory().getLocalDiscontinuityTypeDAO().getDiscontinuityTypeByCode(discontinuityTypeCode);
                newDiscontinuityFamily.setType(discontinuityType);
            }

            String discontinuityRelevanceCode = CursorUtils.getString(DiscontinuityFamilyTable.RELEVANCE_CODE_COLUMN, cursor);
            if (discontinuityRelevanceCode != null) {
                DiscontinuityRelevance discontinuityRelevance = getDAOFactory().getLocalDiscontinuityRelevanceDAO().getDiscontinuityRelevanceByCode(discontinuityRelevanceCode);
                newDiscontinuityFamily.setRelevance(discontinuityRelevance);
            }

            newDiscontinuityFamily.setDipDegrees((short) CursorUtils.getInt(DiscontinuityFamilyTable.DIPDEGREES_CODE_COLUMN, cursor));
            newDiscontinuityFamily.setDipDirDegrees((short) CursorUtils.getInt(DiscontinuityFamilyTable.DIPDIRDEGREES_CODE_COLUMN, cursor));

            String discontinuityShapeCode = CursorUtils.getString(DiscontinuityFamilyTable.SHAPE_CODE_COLUMN, cursor);
            if (discontinuityShapeCode != null) {
                DiscontinuityShape discontinuityShape = getDAOFactory().getLocalDiscontinuityShapeDAO().getDiscontinuityShapeByCode(discontinuityShapeCode);
                newDiscontinuityFamily.setShape(discontinuityShape);
            }

            String discontinuitySpacingCode = CursorUtils.getString(DiscontinuityFamilyTable.SPACING_CODE_COLUMN, cursor);
            if (discontinuitySpacingCode != null) {
                Spacing discontinuitySpacing = getDAOFactory().getLocalSpacingDAO().getSpacingByUniqueCode(discontinuitySpacingCode);
                newDiscontinuityFamily.setSpacing(discontinuitySpacing);
            }

            String roughnessCode = CursorUtils.getString(DiscontinuityFamilyTable.ROUGHNESS_CODE_COLUMN, cursor);
            if (roughnessCode != null) {
                Roughness roughness = getDAOFactory().getLocalRoughnessDAO().getRoughnessByUniqueCode(roughnessCode);
                newDiscontinuityFamily.setRoughness(roughness);
            }

            String weatheringCode = CursorUtils.getString(DiscontinuityFamilyTable.WEATHERING_CODE_COLUMN, cursor);
            if (weatheringCode != null) {
                Weathering weathering = getDAOFactory().getLocalWeatheringDAO().getWeatheringByUniqueCode(weatheringCode);
                newDiscontinuityFamily.setWeathering(weathering);
            }

            String discontinuityWaterCode = CursorUtils.getString(DiscontinuityFamilyTable.DISCONTINUITYWATER_CODE_COLUMN, cursor);
            if (discontinuityWaterCode != null) {
                DiscontinuityWater discontinuityWater = getDAOFactory().getLocalDiscontinuityWaterDAO().getDiscontinuityWaterByCode(discontinuityWaterCode);
                newDiscontinuityFamily.setWater(discontinuityWater);
            }

            String persistenceCode = CursorUtils.getString(DiscontinuityFamilyTable.PERSISTENCE_CODE_COLUMN, cursor);
            if (persistenceCode != null) {
                Persistence persistence = getDAOFactory().getLocalPersistenceDAO().getPersistenceByUniqueCode(persistenceCode);
                newDiscontinuityFamily.setPersistence(persistence);
            }

            String apertureCode = CursorUtils.getString(DiscontinuityFamilyTable.APERTURE_CODE_COLUMN, cursor);
            if (apertureCode != null) {
                Aperture aperture = getDAOFactory().getLocalApertureDAO().getApertureByUniqueCode(apertureCode);
                newDiscontinuityFamily.setAperture(aperture);
            }

            String infillingCode = CursorUtils.getString(DiscontinuityFamilyTable.INFILLING_CODE_COLUMN, cursor);
            if (infillingCode != null) {
                Infilling infilling = getDAOFactory().getLocalInfillingDAO().getInfillingByUniqueCode(infillingCode);
                newDiscontinuityFamily.setInfilling(infilling);
            }

            String jaCode = CursorUtils.getString(DiscontinuityFamilyTable.JA_CODE_COLUMN, cursor);
            if (jaCode != null) {
                Ja ja = getDAOFactory().getLocalJaDAO().getJaByUniqueCode(jaCode);
                newDiscontinuityFamily.setJa(ja);
            }

            String jrCode = CursorUtils.getString(DiscontinuityFamilyTable.JR_CODE_COLUMN, cursor);
            if (jrCode != null) {
                Jr jr = getDAOFactory().getLocalJrDAO().getJrByUniqueCode(jrCode);
                newDiscontinuityFamily.setJr(jr);
            }
            //Java list is zero-index based, discontinuity number is also zero based
            list.set(newDiscontinuityFamily.getNumber(), newDiscontinuityFamily);

        }

        return list;
    }


    @Override
    public List<DiscontinuityFamily> getDiscontinuityFamilies(String assessmentCode) throws DAOException {
        List<DiscontinuityFamily> discontinuitySystem;
        Cursor cursor = getRecordsFilteredByColumn(DiscontinuityFamilyTable.DISCONTINUITY_FAMILY_DATABASE_TABLE, DiscontinuityFamilyTable.ASSESSMENT_CODE_COLUMN, assessmentCode, DiscontinuityFamilyTable.NUMBER_COLUMN);
        discontinuitySystem = assemblePersistentEntities(cursor);
        cursor.close();
        return discontinuitySystem;

    }

    @Override
    public void saveDiscontinuityFamily(String assessmentCode, DiscontinuityFamily newDiscontinuityFamily) throws DAOException {
        savePersistentEntity(DiscontinuityFamilyTable.DISCONTINUITY_FAMILY_DATABASE_TABLE, assessmentCode, newDiscontinuityFamily);
    }


    @Override
    protected void savePersistentEntity(String tableName, DiscontinuityFamily newSkavaEntity) throws DAOException {
        savePersistentEntity(tableName, null, newSkavaEntity);
    }

    protected void savePersistentEntity(String tableName, String assessmentCode, DiscontinuityFamily newDiscontinuityFamily) throws DAOException {
        if (newDiscontinuityFamily != null) {
            String[] names = new String[]{
//                    DiscontinuityFamilyTable.GLOBAL_KEY_ID,
                    DiscontinuityFamilyTable.ASSESSMENT_CODE_COLUMN,
                    DiscontinuityFamilyTable.NUMBER_COLUMN,
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

            Object[] values = new Object[]{
//                    newDiscontinuityFamily.get_id(),
                    assessmentCode,
                    newDiscontinuityFamily.getNumber(),
                    SkavaUtils.isUndefined(newDiscontinuityFamily.getType()) ? null : newDiscontinuityFamily.getType().getCode(),
                    SkavaUtils.isUndefined(newDiscontinuityFamily.getRelevance()) ? null : newDiscontinuityFamily.getRelevance().getCode(),
                    newDiscontinuityFamily.getDipDirDegrees(),
                    newDiscontinuityFamily.getDipDegrees(),
                    SkavaUtils.isUndefined(newDiscontinuityFamily.getShape()) ? null :newDiscontinuityFamily.getShape().getCode(),
                    SkavaUtils.isUndefined(newDiscontinuityFamily.getSpacing()) ? null :newDiscontinuityFamily.getSpacing().getCode(),
                    SkavaUtils.isUndefined(newDiscontinuityFamily.getRoughness()) ? null :newDiscontinuityFamily.getRoughness().getCode(),
                    SkavaUtils.isUndefined(newDiscontinuityFamily.getWeathering()) ? null :newDiscontinuityFamily.getWeathering().getCode(),
                    SkavaUtils.isUndefined(newDiscontinuityFamily.getWater()) ? null :newDiscontinuityFamily.getWater().getCode(),
                    SkavaUtils.isUndefined(newDiscontinuityFamily.getPersistence()) ? null :newDiscontinuityFamily.getPersistence().getCode(),
                    SkavaUtils.isUndefined(newDiscontinuityFamily.getAperture()) ? null :newDiscontinuityFamily.getAperture().getCode(),
                    SkavaUtils.isUndefined(newDiscontinuityFamily.getInfilling()) ? null :newDiscontinuityFamily.getInfilling().getCode(),
                    SkavaUtils.isUndefined(newDiscontinuityFamily.getJa()) ? null :newDiscontinuityFamily.getJa().getCode(),
                    SkavaUtils.isUndefined(newDiscontinuityFamily.getJr()) ? null :newDiscontinuityFamily.getJr().getCode()
            };

            Long newDiscontinuityFamilyId = saveRecord(tableName, names, values);
            newDiscontinuityFamily.set_id(newDiscontinuityFamilyId);
        }
    }


    @Override
    public boolean deleteDiscontinuityFamily(String assessmentCode, Integer number) {
        return false;
    }

    @Override
    public int deleteAllDiscontinuityFamilies(String assessmentCode) throws DAOException {
        return deletePersistentEntitiesFilteredByColumn(DiscontinuityFamilyTable.DISCONTINUITY_FAMILY_DATABASE_TABLE, DiscontinuityFamilyTable.ASSESSMENT_CODE_COLUMN, assessmentCode);
    }

    @Override
    public int deleteAllDiscontinuitiesFamilies() throws DAOException {
        return deleteAllPersistentEntities(DiscontinuityFamilyTable.DISCONTINUITY_FAMILY_DATABASE_TABLE);
    }


}
