package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.calculator.rmr.model.Groundwater;
import com.metric.skava.data.dao.LocalGroundwaterDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.helper.MappedIndexInstanceBuilder4SqlLite;
import com.metric.skava.data.dao.impl.sqllite.table.GroundwaterTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/18/14.
 */
public class GroundwaterDAOsqlLiteImpl extends SqlLiteBaseIdentifiableEntityDAO<Groundwater> implements LocalGroundwaterDAO {


    private MappedIndexInstanceBuilder4SqlLite mappedIndexInstaceBuilder;

    public Context getContext() {
        return mContext;
    }

    public GroundwaterDAOsqlLiteImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        mappedIndexInstaceBuilder = new MappedIndexInstanceBuilder4SqlLite(mContext);
    }


    @Override
    protected List<Groundwater> assemblePersistentEntities(Cursor cursor) throws DAOException {
        List<Groundwater> list = new ArrayList<Groundwater>();
        while (cursor.moveToNext()) {
            Groundwater newInstance = mappedIndexInstaceBuilder.buildGroundwaterFromCursorRecord(cursor);
            list.add(newInstance);
        }
        return list;
    }

    @Override
    public Groundwater getGroundwater(String groupCode, String code) throws DAOException {
        String[] names = new String[]{GroundwaterTable.INDEX_CODE_COLUMN, GroundwaterTable.GROUP_CODE_COLUMN, GroundwaterTable.CODE_COLUMN};
        String[] values = new String[]{Groundwater.INDEX_CODE, groupCode, code};
        Cursor cursor = getRecordsFilteredByColumns(GroundwaterTable.MAPPED_INDEX_DATABASE_TABLE, names, values, null);
        List<Groundwater> list = assambleGroundwaters(cursor);
        if (list.isEmpty()) {
            throw new DAOException("Entity not found. [Index Code : " + Groundwater.INDEX_CODE + ", Group Code: " + groupCode + ", Code: " + code + " ]");
        }
        if (list.size() > 1) {
            throw new DAOException("Multiple records for same code. [Index Code : " + Groundwater.INDEX_CODE + ", Group Code: " + groupCode + ", Code: " + code + " ]");
        }
        cursor.close();
        return list.get(0);
    }

    @Override
    public Groundwater getGroundwaterByUniqueCode(String infillingCode) throws DAOException {
        String[] names = new String[]{GroundwaterTable.INDEX_CODE_COLUMN, GroundwaterTable.CODE_COLUMN};
        String[] values = new String[]{Groundwater.INDEX_CODE, infillingCode};
        Cursor cursor = getRecordsFilteredByColumns(GroundwaterTable.MAPPED_INDEX_DATABASE_TABLE, names, values, null);
        List<Groundwater> list = assemblePersistentEntities(cursor);
        if (list.isEmpty()) {
            throw new DAOException("Entity not found. [Groundwater Code : " + infillingCode + " ]");
        }
        if (list.size() > 1) {
            throw new DAOException("Multiple records for same code. [Index Code : " + infillingCode + " ]");
        }
        cursor.close();
        return list.get(0);
    }



    protected List<Groundwater> assambleGroundwaters(Cursor cursor) throws DAOException {
        List<Groundwater> list = new ArrayList<Groundwater>();
        while (cursor.moveToNext()) {
            Groundwater newInstance = mappedIndexInstaceBuilder.buildGroundwaterFromCursorRecord(cursor);
            list.add(newInstance);
        }
        return list;
    }


    @Override
    public List<Groundwater> getAllGroundwaters(Groundwater.Group group) throws DAOException {
        Cursor cursor = getRecordsFilteredByColumn(GroundwaterTable.MAPPED_INDEX_DATABASE_TABLE, GroundwaterTable.GROUP_CODE_COLUMN, group.name(), null);
        List<Groundwater> list = assambleGroundwaters(cursor);
        cursor.close();
        return list;
    }


    @Override
    public void saveGroundwater(Groundwater newGroundwater) throws DAOException {
        savePersistentEntity(GroundwaterTable.MAPPED_INDEX_DATABASE_TABLE, newGroundwater);
    }

    @Override
    protected void savePersistentEntity(String tableName, Groundwater newSkavaEntity) throws DAOException {
        //  LocalIndexDAO indexDAO = getDAOFactory().getLocalIndexDAO();
        //  Index index = indexDAO.getIndexByCode(Spacing.INDEX_CODE);
        //  Test if this is to map the indexes and grop codes on Fabian model is necessary
        //  String indexCode = index.getCode();
        String indexCode = Groundwater.INDEX_CODE;

        String[] colNames = {GroundwaterTable.INDEX_CODE_COLUMN,
                GroundwaterTable.GROUP_CODE_COLUMN,
                GroundwaterTable.CODE_COLUMN,
                GroundwaterTable.KEY_COLUMN,
                GroundwaterTable.SHORT_DESCRIPTION_COLUMN,
                GroundwaterTable.DESCRIPTION_COLUMN,
                GroundwaterTable.VALUE_COLUMN};

        Object[] colValues = {
                indexCode,
                newSkavaEntity.getGroupName(),
                newSkavaEntity.getCode(),
                newSkavaEntity.getKey(),
                newSkavaEntity.getShortDescription(),
                newSkavaEntity.getDescription(),
                newSkavaEntity.getValue()
        };
        saveRecord(tableName, colNames, colValues);
    }

    @Override
    public boolean deleteGroundwater(String indexCode, String groupCode, String code) {
        String[] colNames = {GroundwaterTable.INDEX_CODE_COLUMN,
                GroundwaterTable.GROUP_CODE_COLUMN,
                GroundwaterTable.CODE_COLUMN};
        Object[] colValues = {
                indexCode,
                groupCode,
                code};
        int howMany = deletePersistentEntitiesFilteredByColumns(GroundwaterTable.MAPPED_INDEX_DATABASE_TABLE, colNames, colValues);
        return (howMany == 1);
    }

    @Override
    public int deleteAllGroundwaters() {
        return deleteAllPersistentEntities(GroundwaterTable.MAPPED_INDEX_DATABASE_TABLE);
    }


}
