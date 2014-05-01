package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.calculator.rmr.model.Infilling;
import com.metric.skava.calculator.rmr.model.Roughness;
import com.metric.skava.data.dao.LocalInfillingDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.helper.MappedIndexInstanceBuilder4SqlLite;
import com.metric.skava.data.dao.impl.sqllite.table.InfillingTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/18/14.
 */
public class InfillingDAOsqlLiteImpl extends SqlLiteBaseIdentifiableEntityDAO<Infilling> implements LocalInfillingDAO {

    private Context mContext;
    private MappedIndexInstanceBuilder4SqlLite mappedIndexInstaceBuilder;

    public Context getContext() {
        return mContext;
    }

    public InfillingDAOsqlLiteImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        mContext = context;
        mappedIndexInstaceBuilder = new MappedIndexInstanceBuilder4SqlLite(mContext);
    }


    @Override
    public Infilling getInfilling(String indexCode, String groupCode, String code) throws DAOException {
        String[] names = new String[]{InfillingTable.INDEX_CODE_COLUMN, InfillingTable.GROUP_CODE_COLUMN, InfillingTable.CODE_COLUMN};
        String[] values = new String[]{indexCode, groupCode, code};
        Cursor cursor = getRecordsFilteredByColumns(InfillingTable.MAPPED_INDEX_DATABASE_TABLE, names , values, null );
        List<Infilling> list = assemblePersistentEntities(cursor);
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
    protected List<Infilling> assemblePersistentEntities(Cursor cursor) throws DAOException {
        List<Infilling> list = new ArrayList<Infilling>();
        while (cursor.moveToNext()) {
            Infilling newInstance = mappedIndexInstaceBuilder.buildInfillingFromCursorRecord(cursor);
            list.add(newInstance);
        }
        return list;
    }

    @Override
    public List<Infilling> getAllInfillings() throws DAOException {
        Cursor cursor = getAllRecords(InfillingTable.MAPPED_INDEX_DATABASE_TABLE);
        List<Infilling> list = assemblePersistentEntities(cursor);
        cursor.close();
        return list;
    }


    @Override
    public void saveInfilling(Infilling newInfilling) throws DAOException {
        savePersistentEntity(InfillingTable.MAPPED_INDEX_DATABASE_TABLE, newInfilling);
    }

    @Override
    protected void savePersistentEntity(String tableName, Infilling newSkavaEntity) throws DAOException {
        //  LocalIndexDAO indexDAO = getDAOFactory().getLocalIndexDAO();
        //  Index index = indexDAO.getIndexByCode(Spacing.INDEX_CODE);
        //  Test if this is to map the indexes and grop codes on Fabian model is necessary
        //  String indexCode = index.getCode();
        String indexCode = Infilling.INDEX_CODE;

        String[] colNames = {InfillingTable.INDEX_CODE_COLUMN,
                InfillingTable.GROUP_CODE_COLUMN,
                InfillingTable.CODE_COLUMN,
                InfillingTable.KEY_COLUMN,
                InfillingTable.SHORT_DESCRIPTION_COLUMN,
                InfillingTable.DESCRIPTION_COLUMN,
                InfillingTable.VALUE_COLUMN};

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
    public boolean deleteInfilling(String indexCode, String groupCode, String code) {
        String[] colNames = {InfillingTable.INDEX_CODE_COLUMN,
                InfillingTable.GROUP_CODE_COLUMN,
                InfillingTable.CODE_COLUMN};
        Object[] colValues = {
                indexCode,
                groupCode,
                code};
        int howMany = deletePersistentEntitiesFilteredByColumns(InfillingTable.MAPPED_INDEX_DATABASE_TABLE, colNames, colValues);
        return (howMany == 1);
    }

    @Override
    public int deleteAllInfillings() {
        return deleteAllPersistentEntities(InfillingTable.MAPPED_INDEX_DATABASE_TABLE);
    }


}
