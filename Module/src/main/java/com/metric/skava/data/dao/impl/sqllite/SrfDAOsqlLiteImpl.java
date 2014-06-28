package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.calculator.barton.model.SRF;
import com.metric.skava.data.dao.LocalSrfDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.helper.MappedIndexInstanceBuilder4SqlLite;
import com.metric.skava.data.dao.impl.sqllite.table.SRFTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/18/14.
 */
public class SrfDAOsqlLiteImpl extends SqlLiteBaseIdentifiableEntityDAO<SRF> implements LocalSrfDAO {

    private Context mContext;
    private MappedIndexInstanceBuilder4SqlLite mappedIndexInstaceBuilder;

    public Context getContext() {
        return mContext;
    }

    public SrfDAOsqlLiteImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        mContext = context;
        mappedIndexInstaceBuilder = new MappedIndexInstanceBuilder4SqlLite(mContext);
    }


    @Override
    public SRF getSrfByUniqueCode(String code) throws DAOException {
        return getPersistentEntityByCandidateKey(SRFTable.MAPPED_INDEX_DATABASE_TABLE, SRFTable.CODE_COLUMN, code);
    }


    @Override
    public SRF getSrf(String groupCode, String code) throws DAOException {
        String[] names = new String[]{SRFTable.INDEX_CODE_COLUMN, SRFTable.GROUP_CODE_COLUMN, SRFTable.CODE_COLUMN};
        String[] values = new String[]{SRF.INDEX_CODE, groupCode, code};
        Cursor cursor = getRecordsFilteredByColumns(SRFTable.MAPPED_INDEX_DATABASE_TABLE, names, values, null);
        List<SRF> list = assemblePersistentEntities(cursor);
        if (list.isEmpty()) {
            throw new DAOException("Entity not found. [Index Code : " + SRF.INDEX_CODE + ", Group Code: " + groupCode + ", Code: " + code + " ]");
        }
        if (list.size() > 1) {
            throw new DAOException("Multiple records for same code. [Index Code : " + SRF.INDEX_CODE + ", Group Code: " + groupCode + ", Code: " + code + " ]");
        }
        cursor.close();
        return list.get(0);
    }



    @Override
    protected List<SRF> assemblePersistentEntities(Cursor cursor) throws DAOException {
        List<SRF> list = new ArrayList<SRF>();
        while (cursor.moveToNext()) {
            SRF newInstance = mappedIndexInstaceBuilder.buildSrfFromCursorRecord(cursor);
            list.add(newInstance);
        }
        return list;
    }



    @Override
    public List<SRF> getAllSrfs(SRF.Group group) throws DAOException {
        Cursor cursor = getRecordsFilteredByColumn(SRFTable.MAPPED_INDEX_DATABASE_TABLE, SRFTable.GROUP_CODE_COLUMN, group.name(), null);
        List<SRF> list = assemblePersistentEntities(cursor);
        cursor.close();
        return list;
    }


    @Override
    public void saveSrf(SRF newSRF) throws DAOException {
        savePersistentEntity(SRFTable.MAPPED_INDEX_DATABASE_TABLE, newSRF);
    }

    @Override
    protected void savePersistentEntity(String tableName, SRF newSkavaEntity) throws DAOException {
        //  LocalIndexDAO indexDAO = getDAOFactory().getLocalIndexDAO();
        //  Index index = indexDAO.getIndexByCode(Spacing.INDEX_CODE);
        //  Test if this is to map the indexes and grop codes on Fabian model is necessary
        //  String indexCode = index.getCode();
        String indexCode = SRF.INDEX_CODE;

        String[] colNames = {SRFTable.INDEX_CODE_COLUMN,
                SRFTable.GROUP_CODE_COLUMN,
                SRFTable.CODE_COLUMN,
                SRFTable.KEY_COLUMN,
                SRFTable.SHORT_DESCRIPTION_COLUMN,
                SRFTable.DESCRIPTION_COLUMN,
                SRFTable.VALUE_COLUMN};

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
    public boolean deleteSrf(String groupCode, String code) {
        String[] colNames = {SRFTable.INDEX_CODE_COLUMN,
                SRFTable.GROUP_CODE_COLUMN,
                SRFTable.CODE_COLUMN};
        Object[] colValues = {
                SRF.INDEX_CODE,
                groupCode,
                code};
        int howMany = deletePersistentEntitiesFilteredByColumns(SRFTable.MAPPED_INDEX_DATABASE_TABLE, colNames, colValues);
        return (howMany == 1);
    }

    @Override
    public int deleteAllSrfs() {
        return deleteAllPersistentEntities(SRFTable.MAPPED_INDEX_DATABASE_TABLE);
    }

    @Override
    public Long countSRF() {
        return countRecords(SRFTable.MAPPED_INDEX_DATABASE_TABLE);
    }


}
