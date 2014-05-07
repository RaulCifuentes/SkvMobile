package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.data.dao.LocalEsrDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.helper.MappedIndexInstanceBuilder4SqlLite;
import com.metric.skava.data.dao.impl.sqllite.table.ESRTable;
import com.metric.skava.rocksupport.model.ESR;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/18/14.
 */
public class EsrDAOsqlLiteImpl extends SqlLiteBaseIdentifiableEntityDAO<ESR> implements LocalEsrDAO {

    private Context mContext;
    private MappedIndexInstanceBuilder4SqlLite mappedIndexInstaceBuilder;

    public Context getContext() {
        return mContext;
    }

    public EsrDAOsqlLiteImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        mContext = context;
        mappedIndexInstaceBuilder = new MappedIndexInstanceBuilder4SqlLite(mContext);
    }

    @Override
    public ESR getESR(String groupCode, String code) throws DAOException {
        String[] names = new String[]{ESRTable.INDEX_CODE_COLUMN, ESRTable.GROUP_CODE_COLUMN, ESRTable.CODE_COLUMN};
        String[] values = new String[]{ESR.INDEX_CODE, groupCode, code};
        Cursor cursor = getRecordsFilteredByColumns(ESRTable.MAPPED_INDEX_DATABASE_TABLE, names, values, null);
        List<ESR> list = assemblePersistentEntities(cursor);
        if (list.isEmpty()) {
            throw new DAOException("Entity not found. [ESR Code : " + code + " ]");
        }
        if (list.size() > 1) {
            throw new DAOException("Multiple records for same code. [Index Code : " + code + " ]");
        }
        cursor.close();
        return list.get(0);
    }

    @Override
    public ESR getESRByUniqueCode(String code) throws DAOException {
        String[] names = new String[]{ESRTable.INDEX_CODE_COLUMN, ESRTable.CODE_COLUMN};
        String[] values = new String[]{ESR.INDEX_CODE, code};
        Cursor cursor = getRecordsFilteredByColumns(ESRTable.MAPPED_INDEX_DATABASE_TABLE, names, values, null);
        List<ESR> list = assemblePersistentEntities(cursor);
        if (list.isEmpty()) {
            throw new DAOException("Entity not found. [ESR Code : " + code + " ]");
        }
        if (list.size() > 1) {
            throw new DAOException("Multiple records for same code. [Index Code : " + code + " ]");
        }
        cursor.close();
        return list.get(0);
    }


    @Override
    public List<ESR> getAllESRs(ESR.Group group) throws DAOException {
        Cursor cursor = getRecordsFilteredByColumn(ESRTable.MAPPED_INDEX_DATABASE_TABLE, ESRTable.GROUP_CODE_COLUMN, group.name(), null);
        List<ESR> list = assemblePersistentEntities(cursor);
        cursor.close();
        return list;
    }

    @Override
    public void saveESR(ESR newESR) throws DAOException {
        savePersistentEntity(ESRTable.MAPPED_INDEX_DATABASE_TABLE, newESR);
    }

    @Override
    protected void savePersistentEntity(String tableName, ESR newSkavaEntity) throws DAOException {

        String[] colNames = {ESRTable.INDEX_CODE_COLUMN,
                ESRTable.GROUP_CODE_COLUMN,
                ESRTable.CODE_COLUMN,
                ESRTable.KEY_COLUMN,
                ESRTable.SHORT_DESCRIPTION_COLUMN,
                ESRTable.DESCRIPTION_COLUMN,
                ESRTable.VALUE_COLUMN};

        Object[] colValues = {
                ESR.INDEX_CODE,
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
    protected List<ESR> assemblePersistentEntities(Cursor cursor) throws DAOException {
        List<ESR> list = new ArrayList<ESR>();
        while (cursor.moveToNext()) {
            ESR newInstance = mappedIndexInstaceBuilder.buildESRFromCursorRecord(cursor);
            list.add(newInstance);
        }
        return list;
    }


    @Override
    public boolean deleteESR(String groupCode, String code) {
        String[] colNames = {ESRTable.INDEX_CODE_COLUMN,
                ESRTable.GROUP_CODE_COLUMN,
                ESRTable.CODE_COLUMN};
        Object[] colValues = {
                ESR.INDEX_CODE,
                groupCode,
                code};
        int howMany = deletePersistentEntitiesFilteredByColumns(ESRTable.MAPPED_INDEX_DATABASE_TABLE, colNames, colValues);
        return (howMany == 1);
    }

    @Override
    public int deleteAllESRs() {
        return deleteAllPersistentEntities(ESRTable.MAPPED_INDEX_DATABASE_TABLE);
    }


}
