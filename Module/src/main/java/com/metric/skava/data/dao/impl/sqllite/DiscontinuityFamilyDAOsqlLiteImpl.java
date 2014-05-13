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
import com.metric.skava.data.dao.DAOFactory;
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

    private DAOFactory daoFactory;


    public DiscontinuityFamilyDAOsqlLiteImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);

        daoFactory = DAOFactory.getInstance(context, skavaContext);
    }


    @Override
    protected List<DiscontinuityFamily> assemblePersistentEntities(Cursor cursor) throws DAOException {
        List<DiscontinuityFamily> list = new ArrayList<DiscontinuityFamily>();

        while (cursor.moveToNext()) {
            DiscontinuityFamily newDiscontinuityFamily = new DiscontinuityFamily();
            newDiscontinuityFamily.setNumber(CursorUtils.getInt(DiscontinuityFamilyTable.NUMBER_COLUMN, cursor));

            String discontinuityTypeCode = CursorUtils.getString(DiscontinuityFamilyTable.TYPE_CODE_COLUMN, cursor);
            if (discontinuityTypeCode != null) {
                DiscontinuityType discontinuityType = daoFactory.getLocalDiscontinuityTypeDAO().getDiscontinuityTypeByCode(discontinuityTypeCode);
                newDiscontinuityFamily.setType(discontinuityType);
            }

            String discontinuityRelevanceCode = CursorUtils.getString(DiscontinuityFamilyTable.RELEVANCE_CODE_COLUMN, cursor);
            if (discontinuityRelevanceCode != null) {
                DiscontinuityRelevance discontinuityRelevance = daoFactory.getLocalDiscontinuityRelevanceDAO().getDiscontinuityRelevanceByCode(discontinuityRelevanceCode);
                newDiscontinuityFamily.setRelevance(discontinuityRelevance);
            }

            newDiscontinuityFamily.setDipDegrees((short) CursorUtils.getInt(DiscontinuityFamilyTable.DIPDEGREES_CODE_COLUMN, cursor));
            newDiscontinuityFamily.setDipDirDegrees((short) CursorUtils.getInt(DiscontinuityFamilyTable.DIPDIRDEGREES_CODE_COLUMN, cursor));

            String discontinuityShapeCode = CursorUtils.getString(DiscontinuityFamilyTable.SHAPE_CODE_COLUMN, cursor);
            if (discontinuityShapeCode != null) {
                DiscontinuityShape discontinuityShape = daoFactory.getLocalDiscontinuityShapeDAO().getDiscontinuityShapeByCode(discontinuityShapeCode);
                newDiscontinuityFamily.setShape(discontinuityShape);
            }


            String discontinuitySpacingCode = CursorUtils.getString(DiscontinuityFamilyTable.SPACING_CODE_COLUMN, cursor);
            if (discontinuitySpacingCode != null) {
                Spacing discontinuitySpacing = daoFactory.getLocalSpacingDAO().getSpacingByUniqueCode(discontinuitySpacingCode);
                newDiscontinuityFamily.setSpacing(discontinuitySpacing);
            }

            String roughnessCode = CursorUtils.getString(DiscontinuityFamilyTable.ROUGHNESS_CODE_COLUMN, cursor);
            if (roughnessCode != null) {
                Roughness roughness = daoFactory.getLocalRoughnessDAO().getRoughnessByUniqueCode(roughnessCode);
                newDiscontinuityFamily.setRoughness(roughness);
            }

            String weatheringCode = CursorUtils.getString(DiscontinuityFamilyTable.WEATHERING_CODE_COLUMN, cursor);
            if (weatheringCode != null) {
                Weathering weathering = daoFactory.getLocalWeatheringDAO().getWeatheringByUniqueCode(weatheringCode);
                newDiscontinuityFamily.setWeathering(weathering);
            }

            String discontinuityWaterCode = CursorUtils.getString(DiscontinuityFamilyTable.DISCONTINUITYWATER_CODE_COLUMN, cursor);
            if (discontinuityWaterCode != null) {
                DiscontinuityWater discontinuityWater = daoFactory.getLocalDiscontinuityWaterDAO().getDiscontinuityWaterByCode(discontinuityWaterCode);
                newDiscontinuityFamily.setWater(discontinuityWater);
            }

            String persistenceCode = CursorUtils.getString(DiscontinuityFamilyTable.PERSISTENCE_CODE_COLUMN, cursor);
            if (persistenceCode != null) {
                Persistence persistence = daoFactory.getLocalPersistenceDAO().getPersistenceByUniqueCode(persistenceCode);
                newDiscontinuityFamily.setPersistence(persistence);
            }

            String apertureCode = CursorUtils.getString(DiscontinuityFamilyTable.APERTURE_CODE_COLUMN, cursor);
            if (apertureCode != null) {
                Aperture aperture = daoFactory.getLocalApertureDAO().getApertureByUniqueCode(apertureCode);
                newDiscontinuityFamily.setAperture(aperture);
            }

            String infillingCode = CursorUtils.getString(DiscontinuityFamilyTable.INFILLING_CODE_COLUMN, cursor);
            if (infillingCode != null) {
                Infilling infilling = daoFactory.getLocalInfillingDAO().getInfillingByUniqueCode(infillingCode);
                newDiscontinuityFamily.setInfilling(infilling);
            }

            String jaCode = CursorUtils.getString(DiscontinuityFamilyTable.JA_CODE_COLUMN, cursor);
            if (jaCode != null) {
                Ja ja = daoFactory.getLocalJaDAO().getJaByUniqueCode(jaCode);
                newDiscontinuityFamily.setJa(ja);
            }

            String jrCode = CursorUtils.getString(DiscontinuityFamilyTable.JR_CODE_COLUMN, cursor);
            if (jrCode != null) {
                Jr jr = daoFactory.getLocalJrDAO().getJrByUniqueCode(jrCode);
                newDiscontinuityFamily.setJr(jr);
            }

            list.add(newDiscontinuityFamily);
        }
        return list;
    }


    @Override
    public List<DiscontinuityFamily> getDiscontinuityFamilies(String assessmentCode) throws DAOException {
        List<DiscontinuityFamily> discontinuitySystem;
        Cursor cursor = getRecordsFilteredByColumn(DiscontinuityFamilyTable.DISCONTINUITY_FAMILY_DATABASE_TABLE, DiscontinuityFamilyTable.ASSESSMENT_CODE_COLUMN, assessmentCode, null);
        discontinuitySystem = assemblePersistentEntities(cursor);
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
                    DiscontinuityFamilyTable.GLOBAL_KEY_ID,
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
                    newDiscontinuityFamily.get_id(),
                    assessmentCode,
                    newDiscontinuityFamily.getNumber(),
                    SkavaUtils.isUndefined(newDiscontinuityFamily.getType()) ? null : newDiscontinuityFamily.getType().getCode(),
                    SkavaUtils.isUndefined(newDiscontinuityFamily.getRelevance()) ? null : newDiscontinuityFamily.getRelevance().getCode(),
                    newDiscontinuityFamily.getDipDegrees(),
                    newDiscontinuityFamily.getDipDirDegrees(),
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
