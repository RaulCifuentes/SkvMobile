package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.calculator.barton.model.Jw;
import com.metric.skava.data.dao.LocalJwDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.helper.MappedIndexInstanceBuilder4SqlLite;
import com.metric.skava.data.dao.impl.sqllite.table.JwTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/18/14.
 */
public class JwDAOsqlLiteImpl extends SqlLiteBaseIdentifiableEntityDAO<Jw> implements LocalJwDAO {

    private MappedIndexInstanceBuilder4SqlLite mappedIndexInstaceBuilder;


    public JwDAOsqlLiteImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        mappedIndexInstaceBuilder = new MappedIndexInstanceBuilder4SqlLite(mContext);
    }

    @Override
    public Jw getJwByUniqueCode(String code) throws DAOException {
        return getPersistentEntityByCandidateKey(JwTable.MAPPED_INDEX_DATABASE_TABLE, JwTable.CODE_COLUMN, code);
    }


    @Override
    public Jw getJw(String groupCode, String code) throws DAOException {
        String[] names = new String[]{JwTable.INDEX_CODE_COLUMN, JwTable.GROUP_CODE_COLUMN, JwTable.CODE_COLUMN};
        String[] values = new String[]{Jw.INDEX_CODE, groupCode, code};
        Cursor cursor = getRecordsFilteredByColumns(JwTable.MAPPED_INDEX_DATABASE_TABLE, names , values, null );
        List<Jw> list = assemblePersistentEntities(cursor);
        if (list.isEmpty()) {
            throw new DAOException("Entity not found. [Index Code : " + Jw.INDEX_CODE + ", Group Code: "+ groupCode + ", Code: " + code + " ]");
        }
        if (list.size() > 1) {
            throw new DAOException("Multiple records for same code. [Index Code : " + Jw.INDEX_CODE + ", Group Code: "+ groupCode + ", Code: " + code + " ]");
        }
        cursor.close();
        return list.get(0);
    }


    @Override
    protected List<Jw> assemblePersistentEntities(Cursor cursor) throws DAOException {
        List<Jw> list = new ArrayList<Jw>();
        while (cursor.moveToNext()) {
            Jw newInstance = mappedIndexInstaceBuilder.buildJwFromCursorRecord(cursor);
            list.add(newInstance);
        }
        return list;
    }


    @Override
    public List<Jw> getAllJws() throws DAOException {
        Cursor cursor = getAllRecords(JwTable.MAPPED_INDEX_DATABASE_TABLE);
        List<Jw> list = assemblePersistentEntities(cursor);
        cursor.close();
        return list;
    }


    @Override
    public void saveJw(Jw newJw) throws DAOException {
        savePersistentEntity(JwTable.MAPPED_INDEX_DATABASE_TABLE, newJw);
    }

    @Override
    protected void savePersistentEntity(String tableName, Jw newSkavaEntity) throws DAOException {
        //  LocalIndexDAO indexDAO = getDAOFactory().getLocalIndexDAO();
        //  Index index = indexDAO.getIndexByCode(Spacing.INDEX_CODE);
        //  Test if this is to map the indexes and grop codes on Fabian model is necessary
        //  String indexCode = index.getCode();
        String indexCode = Jw.INDEX_CODE;

        String[] colNames = {JwTable.INDEX_CODE_COLUMN,
                JwTable.GROUP_CODE_COLUMN,
                JwTable.CODE_COLUMN,
                JwTable.KEY_COLUMN,
                JwTable.SHORT_DESCRIPTION_COLUMN,
                JwTable.DESCRIPTION_COLUMN,
                JwTable.VALUE_COLUMN};

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
    public boolean deleteJw(String indexCode, String groupCode, String code) {
        String[] colNames = {JwTable.INDEX_CODE_COLUMN,
                JwTable.GROUP_CODE_COLUMN,
                JwTable.CODE_COLUMN};
        Object[] colValues = {
                indexCode,
                groupCode,
                code};
        int howMany = deletePersistentEntitiesFilteredByColumns(JwTable.MAPPED_INDEX_DATABASE_TABLE, colNames, colValues);
        return (howMany == 1);
    }

    @Override
    public int deleteAllJws() {
        return deleteAllPersistentEntities(JwTable.MAPPED_INDEX_DATABASE_TABLE);
    }

    @Override
    public Long countJw() {
        return countRecords(JwTable.MAPPED_INDEX_DATABASE_TABLE);
    }

}
