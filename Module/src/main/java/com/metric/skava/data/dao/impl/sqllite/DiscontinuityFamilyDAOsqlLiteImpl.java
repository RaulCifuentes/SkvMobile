package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.data.dao.LocalDiscontinuityFamilyDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.table.DiscontinuityFamilyTable;
import com.metric.skava.discontinuities.model.DiscontinuityFamily;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/14/14.
 */
public class DiscontinuityFamilyDAOsqlLiteImpl extends SqlLiteBasePersistentEntityDAO<DiscontinuityFamily> implements LocalDiscontinuityFamilyDAO {



    public DiscontinuityFamilyDAOsqlLiteImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);

    }


    @Override
    protected List<DiscontinuityFamily> assemblePersistentEntities(Cursor cursor) throws DAOException {
        List<DiscontinuityFamily> list = new ArrayList<DiscontinuityFamily>();
        while (cursor.moveToNext()) {


            DiscontinuityFamily newInstance = new DiscontinuityFamily();
            list.add(newInstance);
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
                    newDiscontinuityFamily.getType().getCode(),
                    newDiscontinuityFamily.getRelevance().getCode(),
                    newDiscontinuityFamily.getDipDegrees(),
                    newDiscontinuityFamily.getDipDirDegrees(),
                    newDiscontinuityFamily.getShape().getCode(),
                    newDiscontinuityFamily.getSpacing().getKey(),
                    newDiscontinuityFamily.getRoughness().getKey(),
                    newDiscontinuityFamily.getWeathering().getKey(),
                    newDiscontinuityFamily.getWater().getCode(),
                    newDiscontinuityFamily.getPersistence().getKey(),
                    newDiscontinuityFamily.getAperture().getKey(),
                    newDiscontinuityFamily.getInfilling().getKey(),
                    newDiscontinuityFamily.getJa().getKey(),
                    newDiscontinuityFamily.getJr().getKey()
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
