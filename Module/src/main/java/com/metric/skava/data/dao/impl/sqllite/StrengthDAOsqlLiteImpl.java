package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.calculator.rmr.model.StrengthOfRock;
import com.metric.skava.data.dao.LocalStrengthDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.helper.MappedIndexInstanceBuilder4SqlLite;
import com.metric.skava.data.dao.impl.sqllite.table.StrengthTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/18/14.
 */
public class StrengthDAOsqlLiteImpl extends SqlLiteBaseIdentifiableEntityDAO<StrengthOfRock> implements LocalStrengthDAO {

    private Context mContext;
    private MappedIndexInstanceBuilder4SqlLite mappedIndexInstaceBuilder;

    public Context getContext() {
        return mContext;
    }

    public StrengthDAOsqlLiteImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        mContext = context;
        mappedIndexInstaceBuilder = new MappedIndexInstanceBuilder4SqlLite(mContext);
    }


    @Override
    public StrengthOfRock getStrength(String groupCode, String code) throws DAOException {
        String[] names = new String[]{StrengthTable.INDEX_CODE_COLUMN, StrengthTable.GROUP_CODE_COLUMN, StrengthTable.CODE_COLUMN};
        String[] values = new String[]{StrengthOfRock.INDEX_CODE, groupCode, code};
        Cursor cursor = getRecordsFilteredByColumns(StrengthTable.MAPPED_INDEX_DATABASE_TABLE, names, values, null);
        List<StrengthOfRock> list = assemblePersistentEntities(cursor);
        if (list.isEmpty()) {
            throw new DAOException("Entity not found. [Index Code : " + StrengthOfRock.INDEX_CODE + ", Group Code: " + groupCode + ", Code: " + code + " ]");
        }
        if (list.size() > 1) {
            throw new DAOException("Multiple records for same code. [Index Code : " + StrengthOfRock.INDEX_CODE + ", Group Code: " + groupCode + ", Code: " + code + " ]");
        }
        cursor.close();
        return list.get(0);
    }

    @Override
    public StrengthOfRock getStrengthByUniqueCode(String code) throws DAOException {
        return getPersistentEntityByCandidateKey(StrengthTable.MAPPED_INDEX_DATABASE_TABLE, StrengthTable.CODE_COLUMN, code);
    }

    @Override
    protected List<StrengthOfRock> assemblePersistentEntities(Cursor cursor) throws DAOException {
        List<StrengthOfRock> list = new ArrayList<StrengthOfRock>();
        while (cursor.moveToNext()) {
            StrengthOfRock newInstance = mappedIndexInstaceBuilder.buildStrengthFromCursorRecord(cursor);
            list.add(newInstance);
        }
        return list;
    }


    @Override
    public List<StrengthOfRock> getAllStrengths(StrengthOfRock.Group group) throws DAOException {
        Cursor cursor = getRecordsFilteredByColumn(StrengthTable.MAPPED_INDEX_DATABASE_TABLE, StrengthTable.GROUP_CODE_COLUMN, group.name(), null);
        List<StrengthOfRock> list = assemblePersistentEntities(cursor);
        cursor.close();
        return list;
    }


    @Override
    public void saveStrength(StrengthOfRock newStrenght) throws DAOException {
        savePersistentEntity(StrengthTable.MAPPED_INDEX_DATABASE_TABLE, newStrenght);
    }

    @Override
    protected void savePersistentEntity(String tableName, StrengthOfRock newSkavaEntity) throws DAOException {
        //  LocalIndexDAO indexDAO = getDAOFactory().getLocalIndexDAO();
        //  Index index = indexDAO.getIndexByCode(Spacing.INDEX_CODE);
        //  Test if this is to map the indexes and grop codes on Fabian model is necessary
        //  String indexCode = index.getCode();
        String indexCode = StrengthOfRock.INDEX_CODE;

        String[] colNames = {StrengthTable.INDEX_CODE_COLUMN,
                StrengthTable.GROUP_CODE_COLUMN,
                StrengthTable.CODE_COLUMN,
                StrengthTable.KEY_COLUMN,
                StrengthTable.SHORT_DESCRIPTION_COLUMN,
                StrengthTable.DESCRIPTION_COLUMN,
                StrengthTable.VALUE_COLUMN};

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
    public boolean deleteStrength(String indexCode, String groupCode, String code) {
        String[] colNames = {StrengthTable.INDEX_CODE_COLUMN,
                StrengthTable.GROUP_CODE_COLUMN,
                StrengthTable.CODE_COLUMN};
        Object[] colValues = {
                indexCode,
                groupCode,
                code};
        int howMany = deletePersistentEntitiesFilteredByColumns(StrengthTable.MAPPED_INDEX_DATABASE_TABLE, colNames, colValues);
        return (howMany == 1);
    }

    @Override
    public int deleteAllStrengths() {
        return deleteAllPersistentEntities(StrengthTable.MAPPED_INDEX_DATABASE_TABLE);
    }

    @Override
    public Long countStrengths() {
        return countRecords(StrengthTable.MAPPED_INDEX_DATABASE_TABLE);
    }


}
