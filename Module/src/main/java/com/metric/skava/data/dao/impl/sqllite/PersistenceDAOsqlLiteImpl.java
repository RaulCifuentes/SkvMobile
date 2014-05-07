package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.calculator.rmr.model.Persistence;
import com.metric.skava.data.dao.LocalPersistenceDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.helper.MappedIndexInstanceBuilder4SqlLite;
import com.metric.skava.data.dao.impl.sqllite.table.PersistenceTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/18/14.
 */
public class PersistenceDAOsqlLiteImpl extends SqlLiteBaseIdentifiableEntityDAO<Persistence> implements LocalPersistenceDAO {

    private MappedIndexInstanceBuilder4SqlLite mappedIndexInstaceBuilder;


    public PersistenceDAOsqlLiteImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        mappedIndexInstaceBuilder = new MappedIndexInstanceBuilder4SqlLite(mContext);
    }


    @Override
    protected List<Persistence> assemblePersistentEntities(Cursor cursor) throws DAOException {
        List<Persistence> list = new ArrayList<Persistence>();
        while (cursor.moveToNext()) {
            Persistence newInstance = mappedIndexInstaceBuilder.buildPersistenceFromCursorRecord(cursor);
            list.add(newInstance);
        }
        return list;
    }


    @Override
    public Persistence getPersistence(String groupCode, String code) throws DAOException {
        String[] names = new String[]{PersistenceTable.INDEX_CODE_COLUMN, PersistenceTable.GROUP_CODE_COLUMN, PersistenceTable.CODE_COLUMN};
        String[] values = new String[]{Persistence.INDEX_CODE, groupCode, code};
        Cursor cursor = getRecordsFilteredByColumns(PersistenceTable.MAPPED_INDEX_DATABASE_TABLE, names , values, null );
        List<Persistence> list = assemblePersistentEntities(cursor);
        if (list.isEmpty()) {
            throw new DAOException("Entity not found. [Index Code : " + Persistence.INDEX_CODE + ", Group Code: "+ groupCode + ", Code: " + code + " ]");
        }
        if (list.size() > 1) {
            throw new DAOException("Multiple records for same code. [Index Code : " + Persistence.INDEX_CODE + ", Group Code: "+ groupCode + ", Code: " + code + " ]");
        }
        cursor.close();
        return list.get(0);
    }

    @Override
    public Persistence getPersistenceByUniqueCode(String persistenceCode) throws DAOException {
        String[] names = new String[]{PersistenceTable.INDEX_CODE_COLUMN, PersistenceTable.CODE_COLUMN};
        String[] values = new String[]{Persistence.INDEX_CODE, persistenceCode};
        Cursor cursor = getRecordsFilteredByColumns(PersistenceTable.MAPPED_INDEX_DATABASE_TABLE, names, values, null);
        List<Persistence> list = assemblePersistentEntities(cursor);
        if (list.isEmpty()) {
            throw new DAOException("Entity not found. [Persistence Code : " + persistenceCode + " ]");
        }
        if (list.size() > 1) {
            throw new DAOException("Multiple records for same code. [Index Code : " + persistenceCode + " ]");
        }
        cursor.close();
        return list.get(0);
    }


    @Override
    public List<Persistence> getAllPersistences() throws DAOException {
        Cursor cursor = getAllRecords(PersistenceTable.MAPPED_INDEX_DATABASE_TABLE);
        List<Persistence> list = assemblePersistentEntities(cursor);
        cursor.close();
        return list;
    }


    @Override
    public void savePersistence(Persistence newPersistence) throws DAOException {
        savePersistentEntity(PersistenceTable.MAPPED_INDEX_DATABASE_TABLE, newPersistence);
    }

    @Override
    protected void savePersistentEntity(String tableName, Persistence newSkavaEntity) throws DAOException {
        //  Test if this is to map the indexes and grop codes on Fabian model is necessary
        //  LocalIndexDAO indexDAO = getDAOFactory().getLocalIndexDAO();
        //  Index index = indexDAO.getIndexByCode(Spacing.INDEX_CODE);
        //  String indexCode = index.getCode();
        String indexCode = Persistence.INDEX_CODE;

        String[] colNames = {PersistenceTable.INDEX_CODE_COLUMN,
                PersistenceTable.GROUP_CODE_COLUMN,
                PersistenceTable.CODE_COLUMN,
                PersistenceTable.KEY_COLUMN,
                PersistenceTable.SHORT_DESCRIPTION_COLUMN,
                PersistenceTable.DESCRIPTION_COLUMN,
                PersistenceTable.VALUE_COLUMN};

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
    public boolean deletePersistence(String groupCode, String code) {
        String[] colNames = {PersistenceTable.INDEX_CODE_COLUMN,
                PersistenceTable.GROUP_CODE_COLUMN,
                PersistenceTable.CODE_COLUMN};
        Object[] colValues = {
                Persistence.INDEX_CODE,
                groupCode,
                code};
        int howMany = deletePersistentEntitiesFilteredByColumns(PersistenceTable.MAPPED_INDEX_DATABASE_TABLE, colNames, colValues);
        return (howMany == 1);
    }

    @Override
    public int deleteAllPersistences() {
        return deleteAllPersistentEntities(PersistenceTable.MAPPED_INDEX_DATABASE_TABLE);
    }


}
