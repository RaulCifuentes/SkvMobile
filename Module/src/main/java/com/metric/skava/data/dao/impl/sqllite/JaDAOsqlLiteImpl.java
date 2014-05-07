package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.calculator.barton.model.Ja;
import com.metric.skava.data.dao.LocalJaDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.helper.MappedIndexInstanceBuilder4SqlLite;
import com.metric.skava.data.dao.impl.sqllite.table.JaTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/18/14.
 */
public class JaDAOsqlLiteImpl extends SqlLiteBaseIdentifiableEntityDAO<Ja> implements LocalJaDAO {

    private Context mContext;
    private MappedIndexInstanceBuilder4SqlLite mappedIndexInstaceBuilder;

    public Context getContext() {
        return mContext;
    }

    public JaDAOsqlLiteImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        mContext = context;
        mappedIndexInstaceBuilder = new MappedIndexInstanceBuilder4SqlLite(mContext);
    }


    @Override
    public Ja getJaByUniqueCode(String code) throws DAOException {
        return getPersistentEntityByCandidateKey(JaTable.MAPPED_INDEX_DATABASE_TABLE, JaTable.CODE_COLUMN, code);
    }


    @Override
    public Ja getJa(String groupCode, String code) throws DAOException {
        String[] names = new String[]{JaTable.INDEX_CODE_COLUMN, JaTable.GROUP_CODE_COLUMN, JaTable.CODE_COLUMN};
        String[] values = new String[]{Ja.INDEX_CODE, groupCode, code};
        Cursor cursor = getRecordsFilteredByColumns(JaTable.MAPPED_INDEX_DATABASE_TABLE, names , values, null );
        List<Ja> list = assemblePersistentEntities(cursor);
        if (list.isEmpty()) {
            throw new DAOException("Entity not found. [Index Code : " + Ja.INDEX_CODE + ", Group Code: "+ groupCode + ", Code: " + code + " ]");
        }
        if (list.size() > 1) {
            throw new DAOException("Multiple records for same code. [Index Code : " + Ja.INDEX_CODE + ", Group Code: "+ groupCode + ", Code: " + code + " ]");
        }
        cursor.close();
        return list.get(0);
    }


    @Override
    protected List<Ja> assemblePersistentEntities(Cursor cursor) throws DAOException {
        List<Ja> list = new ArrayList<Ja>();
        while (cursor.moveToNext()) {
            Ja newInstance = mappedIndexInstaceBuilder.buildJaFromCursorRecord(cursor);
            list.add(newInstance);
        }
        return list;
    }


    @Override
    public List<Ja> getAllJas(Ja.Group group) throws DAOException {
        Cursor cursor = getRecordsFilteredByColumn(JaTable.MAPPED_INDEX_DATABASE_TABLE, JaTable.GROUP_CODE_COLUMN, group.name(), null);
        List<Ja> list = assemblePersistentEntities(cursor);
        cursor.close();
        return list;
    }




    @Override
    public void saveJa(Ja newJa) throws DAOException {
        savePersistentEntity(JaTable.MAPPED_INDEX_DATABASE_TABLE, newJa);
    }

    @Override
    protected void savePersistentEntity(String tableName, Ja newSkavaEntity) throws DAOException {
        //  LocalIndexDAO indexDAO = getDAOFactory().getLocalIndexDAO();
        //  Index index = indexDAO.getIndexByCode(Spacing.INDEX_CODE);
        //  Test if this is to map the indexes and grop codes on Fabian model is necessary
        //  String indexCode = index.getCode();
        String indexCode = Ja.INDEX_CODE;

        String[] colNames = {JaTable.INDEX_CODE_COLUMN,
                JaTable.GROUP_CODE_COLUMN,
                JaTable.CODE_COLUMN,
                JaTable.KEY_COLUMN,
                JaTable.SHORT_DESCRIPTION_COLUMN,
                JaTable.DESCRIPTION_COLUMN,
                JaTable.VALUE_COLUMN};

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
    public boolean deleteJa(String indexCode, String groupCode, String code) {
        String[] colNames = {JaTable.INDEX_CODE_COLUMN,
                JaTable.GROUP_CODE_COLUMN,
                JaTable.CODE_COLUMN};
        Object[] colValues = {
                indexCode,
                groupCode,
                code};
        int howMany = deletePersistentEntitiesFilteredByColumns(JaTable.MAPPED_INDEX_DATABASE_TABLE, colNames, colValues);
        return (howMany == 1);
    }

    @Override
    public int deleteAllJas() {
        return deleteAllPersistentEntities(JaTable.MAPPED_INDEX_DATABASE_TABLE);
    }


}
