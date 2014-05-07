package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.calculator.barton.model.Jr;
import com.metric.skava.data.dao.LocalJrDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.helper.MappedIndexInstanceBuilder4SqlLite;
import com.metric.skava.data.dao.impl.sqllite.table.JrTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/18/14.
 */
public class JrDAOsqlLiteImpl extends SqlLiteBaseIdentifiableEntityDAO<Jr> implements LocalJrDAO {

    private MappedIndexInstanceBuilder4SqlLite mappedIndexInstaceBuilder;


    public JrDAOsqlLiteImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        mContext = context;
        mappedIndexInstaceBuilder = new MappedIndexInstanceBuilder4SqlLite(mContext);
    }

    @Override
    public Jr getJrByUniqueCode(String code) throws DAOException {
        return getPersistentEntityByCandidateKey(JrTable.MAPPED_INDEX_DATABASE_TABLE, JrTable.CODE_COLUMN, code);
    }


    @Override
    public Jr getJr( String groupCode, String code) throws DAOException {
        String[] names = new String[]{JrTable.INDEX_CODE_COLUMN, JrTable.GROUP_CODE_COLUMN, JrTable.CODE_COLUMN};
        String[] values = new String[]{Jr.INDEX_CODE, groupCode, code};
        Cursor cursor = getRecordsFilteredByColumns(JrTable.MAPPED_INDEX_DATABASE_TABLE, names , values, null );
        List<Jr> list = assemblePersistentEntities(cursor);
        if (list.isEmpty()) {
            throw new DAOException("Entity not found. [Index Code : " + Jr.INDEX_CODE + ", Group Code: "+ groupCode + ", Code: " + code + " ]");
        }
        if (list.size() > 1) {
            throw new DAOException("Multiple records for same code. [Index Code : " + Jr.INDEX_CODE + ", Group Code: "+ groupCode + ", Code: " + code + " ]");
        }
        cursor.close();
        return list.get(0);
    }


    @Override
    protected List<Jr> assemblePersistentEntities(Cursor cursor) throws DAOException {
        List<Jr> list = new ArrayList<Jr>();
        while (cursor.moveToNext()) {
            Jr newInstance = mappedIndexInstaceBuilder.buildJrFromCursorRecord(cursor);
            list.add(newInstance);
        }
        return list;
    }


    @Override
    public List<Jr> getAllJrs(Jr.Group group) throws DAOException {
        Cursor cursor = getRecordsFilteredByColumn(JrTable.MAPPED_INDEX_DATABASE_TABLE, JrTable.GROUP_CODE_COLUMN, group.name(), null);
        List<Jr> list = assemblePersistentEntities(cursor);
        cursor.close();
        return list;
    }


    @Override
    public void saveJr(Jr newJr) throws DAOException {
        savePersistentEntity(JrTable.MAPPED_INDEX_DATABASE_TABLE, newJr);
    }

    @Override
    protected void savePersistentEntity(String tableName, Jr newSkavaEntity) throws DAOException {
        //  LocalIndexDAO indexDAO = getDAOFactory().getLocalIndexDAO();
        //  Index index = indexDAO.getIndexByCode(Spacing.INDEX_CODE);
        //  Test if this is to map the indexes and grop codes on Fabian model is necessary
        //  String indexCode = index.getCode();
        String indexCode = Jr.INDEX_CODE;

        String[] colNames = {JrTable.INDEX_CODE_COLUMN,
                JrTable.GROUP_CODE_COLUMN,
                JrTable.CODE_COLUMN,
                JrTable.KEY_COLUMN,
                JrTable.SHORT_DESCRIPTION_COLUMN,
                JrTable.DESCRIPTION_COLUMN,
                JrTable.VALUE_COLUMN};

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
    public boolean deleteJr(String indexCode, String groupCode, String code) {
        String[] colNames = {JrTable.INDEX_CODE_COLUMN,
                JrTable.GROUP_CODE_COLUMN,
                JrTable.CODE_COLUMN};
        Object[] colValues = {
                indexCode,
                groupCode,
                code};
        int howMany = deletePersistentEntitiesFilteredByColumns(JrTable.MAPPED_INDEX_DATABASE_TABLE, colNames, colValues);
        return (howMany == 1);
    }

    @Override
    public int deleteAllJrs() {
        return deleteAllPersistentEntities(JrTable.MAPPED_INDEX_DATABASE_TABLE);
    }


}
