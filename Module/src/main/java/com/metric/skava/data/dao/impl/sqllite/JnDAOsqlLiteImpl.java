package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.calculator.barton.model.Jn;
import com.metric.skava.data.dao.LocalJnDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.helper.MappedIndexInstanceBuilder4SqlLite;
import com.metric.skava.data.dao.impl.sqllite.table.JnTable;
import com.metric.skava.data.dao.impl.sqllite.table.JrTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/18/14.
 */
public class JnDAOsqlLiteImpl extends SqlLiteBaseIdentifiableEntityDAO<Jn> implements LocalJnDAO {

    private Context mContext;
    private MappedIndexInstanceBuilder4SqlLite mappedIndexInstaceBuilder;

    public Context getContext() {
        return mContext;
    }

    public JnDAOsqlLiteImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        mContext = context;
        mappedIndexInstaceBuilder = new MappedIndexInstanceBuilder4SqlLite(mContext);
    }

    @Override
    public Jn getJn(String indexCode, String groupCode, String code) throws DAOException {
        String[] names = new String[]{JnTable.INDEX_CODE_COLUMN, JnTable.GROUP_CODE_COLUMN, JnTable.CODE_COLUMN};
        String[] values = new String[]{indexCode, groupCode, code};
        Cursor cursor = getRecordsFilteredByColumns(JnTable.MAPPED_INDEX_DATABASE_TABLE, names , values, null );
        List<Jn> list = assemblePersistentEntities(cursor);
        if (list.isEmpty()) {
            throw new DAOException("Entity not found. [Index Code : " + indexCode + ", Group Code: "+ groupCode + ", Code: " + code + " ]");
        }
        if (list.size() > 1) {
            throw new DAOException("Multiple records for same code. [Index Code : " + indexCode + ", Group Code: "+ groupCode + ", Code: " + code + " ]");
        }
        cursor.close();
        return list.get(0);
    }


    @Override
    protected List<Jn> assemblePersistentEntities(Cursor cursor) throws DAOException {
        List<Jn> list = new ArrayList<Jn>();
        while (cursor.moveToNext()) {
            Jn newInstance = mappedIndexInstaceBuilder.buildJnFromCursorRecord(cursor);
            list.add(newInstance);
        }
        return list;
    }


    @Override
    public List<Jn> getAllJns() throws DAOException {
        Cursor cursor = getAllRecords(JnTable.MAPPED_INDEX_DATABASE_TABLE);
        List<Jn> list = assemblePersistentEntities(cursor);
        cursor.close();
        return list;
    }


    @Override
    public void saveJn(Jn newJn) throws DAOException {
        savePersistentEntity(JrTable.MAPPED_INDEX_DATABASE_TABLE, newJn);
    }

    @Override
    protected void savePersistentEntity(String tableName, Jn newSkavaEntity) throws DAOException {
        //  LocalIndexDAO indexDAO = getDAOFactory().getLocalIndexDAO();
        //  Index index = indexDAO.getIndexByCode(Spacing.INDEX_CODE);
        //  Test if this is to map the indexes and grop codes on Fabian model is necessary
        //  String indexCode = index.getCode();
        String indexCode = Jn.INDEX_CODE;

        String[] colNames = {JnTable.INDEX_CODE_COLUMN,
                JnTable.GROUP_CODE_COLUMN,
                JnTable.CODE_COLUMN,
                JnTable.KEY_COLUMN,
                JnTable.SHORT_DESCRIPTION_COLUMN,
                JnTable.DESCRIPTION_COLUMN,
                JnTable.VALUE_COLUMN};

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
    public boolean deleteJn(String indexCode, String groupCode, String code) {
        String[] colNames = {JnTable.INDEX_CODE_COLUMN,
                JnTable.GROUP_CODE_COLUMN,
                JnTable.CODE_COLUMN};
        Object[] colValues = {
                indexCode,
                groupCode,
                code};
        int howMany = deletePersistentEntitiesFilteredByColumns(JnTable.MAPPED_INDEX_DATABASE_TABLE, colNames, colValues);
        return (howMany == 1);
    }


    @Override
    public int deleteAllJns() {
        return deleteAllPersistentEntities(JnTable.MAPPED_INDEX_DATABASE_TABLE);
    }


}
